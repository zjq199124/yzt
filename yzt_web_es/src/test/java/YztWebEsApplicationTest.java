import com.maizhiyu.yzt.YztWebEsApplication;
import com.maizhiyu.yzt.entity.SysMultimedia;
import com.maizhiyu.yzt.service.SysMultimediaService;
import com.maizhiyu.yzt.utils.ossKit.AliOssUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest(classes = YztWebEsApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)//@RunWith(SpringJUnit4ClassRunner.class)
@Slf4j
public class YztWebEsApplicationTest {

    @Resource
    AliOssUtil aliOssUtil;
    @Resource
    SysMultimediaService sysMultimediaService;

    @Test
    public void getFile(){
        SysMultimedia sysMultimedia=sysMultimediaService.getById(123);
        String cc=sysMultimediaService.getFileUrlByid(123);
        String filePath=sysMultimedia.getFilePath();
        String url=aliOssUtil.generatePresignedUrl(filePath);
        log.info(url);
    }

}
