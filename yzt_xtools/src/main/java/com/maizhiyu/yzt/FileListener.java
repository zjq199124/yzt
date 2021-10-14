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

//    private static String baseurl = "https://ypt.yztyun.com/hs/api";
    private static String baseurl = "http://localhost:8084/hs/api/";

    private Retrofit retrofit;
    private YztApi yztapi;


    public FileListener() {
        retrofit = new Retrofit.Builder()
                .baseUrl(baseurl)
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .build();
        yztapi = retrofit.create(YztApi.class);
    }


    public void onStart(FileAlterationObserver observer) {
        super.onStart(observer);
    }


    public void onStop(FileAlterationObserver observer) {
        super.onStop(observer);
    }


    public void onFileCreate(File file) {

        // 获取文件名
        String fileName = file.getName();
        String fullName = file.getAbsolutePath();

        System.out.println("### " + fileName);
        System.out.println("### " + fullName);

        // 获取字段信息
        String[] strs = fileName.split("_|\\.");
        Long patientId = Long.parseLong(strs[0]);

        // 处理业务逻辑
        try {
            // 上传文件
            Result result = doUpload(file);
            String fname = result.getData().toString();

            // 准备数据
            BuCheck check = new BuCheck();
            check.setType(1);
            check.setFname(fname);
            check.setOutpatientId(patientId);

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
        if (result.getCode() == 0) {
            System.out.println("上传信息成功：" + result);
        } else {
            throw new RuntimeException("上传信息失败：" + result);
        }
        // 返回结果
        return result;
    }


//    private void doAddCheck1(BuCheck check) throws IOException {
//
//        RequestConfig config = RequestConfig.custom().setSocketTimeout(10000).setConnectTimeout(10000).build();
//        StringEntity entity = new StringEntity(JSON.toJSONString(check), "UTF-8");
//
//        String url = baseurl + "/check/addCheck";
//        HttpPost httpPost = new HttpPost(url);
//
//        httpPost.setConfig(config);
//        httpPost.setEntity(entity);
//        httpPost.addHeader("Content-Type", "application/json");
//
//        // 创建请求
//        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
//
//        // 发送请求
//        HttpResponse httpResponse = httpClient.execute(httpPost);
//
//        // 接收响应
//        HttpEntity httpEntity = httpResponse.getEntity();
//
//        // 解析响应
//        String res = EntityUtils.toString(httpEntity, "UTF-8");
//        System.out.println(res);
//    }
//
//    private void doUpload2(String fname, String fileName) throws IOException {
//        InputStream inputStream = new FileInputStream(fname);
//
//        MultipartEntityBuilder builder = MultipartEntityBuilder.create();// 新建builder对象
//
//        String name = "file";
//        builder.addBinaryBody(name, inputStream, ContentType.create("multipart/form-data"), fileName);
//
//        String url = baseurl + "/file/upload";
//        HttpPost httpPost = new HttpPost(url);
//
//        HttpEntity entity = builder.build();// 生成entity
//        httpPost.setEntity(entity);         // 设置entity
//
//        // 创建请求
//        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
//
//        // 发送请求
//        HttpResponse httpResponse = httpClient.execute(httpPost);
//
//        // 接收响应
//        HttpEntity httpEntity = httpResponse.getEntity();
//
//        // 解析响应
//        String res = EntityUtils.toString(httpEntity, "UTF-8");
//        System.out.println(res);
//
////        Iterator<String> keys = params.keys();// 遍历 params 参数和值
////        MultipartEntityBuilder builder = MultipartEntityBuilder.create();// 新建builder对象
////        while (keys.hasNext()) {
////            String key = keys.next();
////            String value = params.getString(key);
////            if (value.equals("file")) {
////                builder.addBinaryBody(key, inputStream, ContentType.create("multipart/form-data"), fileName);// 设置流参数
////            } else {
////                StringBody body = new StringBody(value, ContentType.create("text/plain", Consts.UTF_8));// 设置普通参数
////                builder.addPart(key, body);
////            }
////        }
////        HttpEntity entity = builder.build();// 生成entity
////        httpPost.setEntity(entity);// 设置 entity
//    }


    public static void main(String[] args) throws Exception{

//        if (args.length == 0) {
//            System.out.println("请指定监听路径！");
//            System.out.println("java -jar xxx.jar directory");
//            return;
//        }
//
//        String root = args[0];
        String root = ".";
        System.out.println("开始监听路径：" + root);
        // String rootDir = "D:\\apache-tomcat-7.0.78";

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
        FileListener listener = new FileListener();

        // 创建观察者
        FileAlterationObserver observer = new FileAlterationObserver(new File(root), filter);
        observer.addListener(listener);

        // 创建主监听器
        FileAlterationMonitor monitor = new FileAlterationMonitor(interval, observer);

        // 开始监控
        monitor.start();


    }

}