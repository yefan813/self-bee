package dmall;

import com.dmall.bee.AppEngineNew;
import com.dmall.bee.domain.AppInfo;
import com.dmall.bee.domain.DmallService;
import com.dmall.bee.factory.CodeFactory;
import com.google.common.collect.Lists;

/**
 * Unit test for simple App.
 */
public class AppTest {

    public static void main(String[] args) throws Exception {
        AppInfo appInfo = new AppInfo();
        appInfo.setUrl("jdbc:MySql://127.0.0.1:3306/fish?characterEncoding=UTF-8");
        appInfo.setUser("root");
        appInfo.setPwd("root");
        appInfo.setArtifactId("start");
        appInfo.setDataAccessMode(CodeFactory.PLAN_FISH);

        //FileOutputStream out = new FileOutputStream(new File("1.zip"));
        AppEngineNew.doGenerator(appInfo);
    }
}
