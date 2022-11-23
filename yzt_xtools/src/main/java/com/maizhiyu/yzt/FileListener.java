package com.maizhiyu.yzt;

import com.google.gson.Gson;
import com.maizhiyu.yzt.entity.BuCheck;
import com.maizhiyu.yzt.result.Result;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.HiddenFileFilter;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.*;
import java.util.concurrent.TimeUnit;


public class FileListener extends FileAlterationListenerAdaptor {

    private static Logger log = LoggerFactory.getLogger(FileListener.class);
    /**
     *HTTP 网络请求框架的封装
     */
    private Retrofit retrofit;

    private YztApi yztapi;


    public FileListener(String baseurl) {
        retrofit = new Retrofit.Builder()
                .baseUrl(baseurl)
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .build();
        yztapi = retrofit.create(YztApi.class);
    }


    public void onStart(FileAlterationObserver observer) {
        log.info("文件监听开始............");
        super.onStart(observer);
    }


    public void onStop(FileAlterationObserver observer) {
        log.info("文件监听结束............");
        super.onStop(observer);
    }


    public void onFileCreate(File file) {

        // 获取文件名
        String fileName = file.getName();
        String fullName = file.getAbsolutePath();

        System.out.println("文件名称：" + fileName);
        System.out.println("文件路径：" + fullName);

        // 获取字段信息
        String[] strs = fileName.split("_|\\.");
        if (strs.length < 4) {
            System.out.println("文件名解析错误");
            return;
        }
        String patientName = strs[0];
        String patientPhone = strs[1];
        String type = strs[2];

        // 处理业务逻辑
        try {
            // 上传文件
            Result result = doUpload(file);
            String fname = result.getData().toString();

            // 准备数据
            BuCheck check = new BuCheck();
            check.setType(Integer.parseInt(type));
            check.setFname(fname);
            check.setPatientName(patientName);
            check.setPatientPhone(patientPhone);

            // 上传信息
            doAddCheck(check);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private Result doUpload(File file) throws IOException {
        // 准备数据
        RequestBody body = RequestBody.create(MediaType.parse("application/pdf"), file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), body);
        // 准备接口
        Call<Result> call = yztapi.upload(part);
        // 执行接口
        Response<Result> response = call.execute();
        // 判断结果
        Result result = response.body();
        if (result.getCode() == 0) {
            System.out.println("上传文件成功：" + result);
        } else {
            throw new RuntimeException("上传文件失败：" + result);
        }
        // 返回结果
        return result;
    }


    private Result doAddCheck(BuCheck check) throws IOException {
        // 准备接口
        Call<Result> call = yztapi.addCheck(check);
        // 执行接口
        Response<Result> response = call.execute();
        // 判断结果
        Result result = response.body();
        System.out.println(result);
        if (result.getCode() == 0) {
            System.out.println("上传信息成功：" + result);
        } else {
            throw new RuntimeException("上传信息失败：" + result);
        }
        // 返回结果
        return result;
    }


    public static void main(String[] args) throws Exception {

        // 参数判断
        if (args.length != 2) {
            System.out.println(">>> 请指定上传地址和监听路径！");
            System.out.println(">>> 格式：java -jar xxx.jar url dir");
            System.out.println(">>> 示例：java -jar xxx.jar https://ypt.yztyun.com/hs/api/ D:\\\\data");
            System.out.println(">>> 注意：url最后必须有一个斜杠'/'");
            return;
        }

        // 解析参数
        String url = args[0];
        String root = args[1];

        System.out.println("上传地址：" + url);
        System.out.println("监听路径：" + root);

        // 设置间隔时间
        long interval = TimeUnit.SECONDS.toMillis(1);

        // 创建过滤器
        IOFileFilter directories = FileFilterUtils.and(
                // 只监听文件夹、目录
                FileFilterUtils.directoryFileFilter(),
                // 此过滤器接受File隐藏的
                HiddenFileFilter.VISIBLE);
        IOFileFilter fileFilter = FileFilterUtils.and(
                // 只监听文件
                FileFilterUtils.fileFileFilter(),
                // 只监听文件后缀为zip的文件
                FileFilterUtils.suffixFileFilter(".pdf"));
        IOFileFilter filter = FileFilterUtils.or(directories, fileFilter);

        // 创建监听器
        FileListener listener = new FileListener(url);

        // 创建观察者
        FileAlterationObserver observer = new FileAlterationObserver(new File(root), filter);
        observer.addListener(listener);

        // 创建主监听器
        FileAlterationMonitor monitor = new FileAlterationMonitor(interval, observer);

        // 开始监控
        monitor.start();
    }
}