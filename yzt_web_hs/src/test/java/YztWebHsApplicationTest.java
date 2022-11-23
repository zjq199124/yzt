import com.maizhiyu.yzt.YztWebHsApplication;
import com.maizhiyu.yzt.utils.ossKit.AliOssUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

@SpringBootTest(classes = YztWebHsApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)//@RunWith(SpringJUnit4ClassRunner.class)
@Slf4j
public class YztWebHsApplicationTest {
    @Resource
    AliOssUtil aliOssUtil;

    /**
     * oss 文件上传测试
     */
    @Test
    public void uploadTest() {
        File file=new File("C:\\Users\\10198\\Desktop\\工作文件\\曹迪_红外热健康中医报告2022-07-14-13.11.11.pdf");
        File file2=new File("C:\\Users\\10198\\Desktop\\工作文件\\曹迪_红外热健康中医报告2022-07-14-13.11.11.pdf");
        String a=aliOssUtil.uploadFile("pdf/",file,true);
        String c=aliOssUtil.uploadFile("pdf/",file,false);
        try {
            String b=aliOssUtil.uploadInputStream(new FileInputStream(file),"test/",file2.getName(),true);
            String d=aliOssUtil.uploadInputStream(new FileInputStream(file2),"test/",file2.getName(),false);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        System.out.println(a);
        System.out.println(c);
        String b = aliOssUtil.generatePresignedUrl("曹迪_红外热健康中医报告2022-07-14-13.11.11.pdf");
        String d = aliOssUtil.generatePresignedUrl("曹迪_红外热健康中医报告2022-07-14-13.11.11.pdf");
    }

    @Test
    public void getOSSFile(){
        String a = aliOssUtil.generatePresignedUrl("曹迪_红外热健康中医报告2022-07-14-13.11.11.pdf");
        System.out.println(a);
    }
}
