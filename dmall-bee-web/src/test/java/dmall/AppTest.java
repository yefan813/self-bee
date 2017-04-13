package dmall;

import com.dmall.bee.AppEngineNew;
import com.dmall.bee.domain.AppInfo;
import com.dmall.bee.domain.DmallService;
import com.google.common.collect.Lists;

/**
 * Unit test for simple App.
 */
public class AppTest {

    public static void main(String[] args) throws Exception {
        AppInfo appInfo = new AppInfo();
        appInfo.setUrl("jdbc:MySql://192.168.8.235:3306/dmall_wms_print?characterEncoding=UTF-8");
        appInfo.setUser("dev");
        appInfo.setPwd("111111");
        appInfo.setArtifactId("dmall-print");
        appInfo.setDataAccessMode("PLANdatabase");
        appInfo.setDepServers(Lists.newArrayList(DmallService.ERP,DmallService.OOP,DmallService.DMC,DmallService.DSF,DmallService.DMG));
        
        //FileOutputStream out = new FileOutputStream(new File("1.zip"));
        AppEngineNew.doGenerator(appInfo);
    }
}
