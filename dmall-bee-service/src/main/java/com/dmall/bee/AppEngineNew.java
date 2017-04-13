package com.dmall.bee;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dmall.bee.domain.AppInfo;
import com.dmall.bee.factory.CodeFactory;
import com.dmall.bee.util.MetaDataUtil;
import com.dmall.bee.util.ZipUtil;

/**
 * SpringMVC代码生成工具
 */
public class AppEngineNew {
    private static final Logger LOGGER = LoggerFactory.getLogger(AppEngineNew.class);

    public static void generator(AppInfo appInfo, OutputStream out) {
        // 初始化LOG4J
    	
    	FileInputStream in = null;
        try {
        	
            GenerateAssistant assist = doGenerator(appInfo);

            String orgProjectPath = assist.getProjectPath() + File.separator + appInfo.getArtifactId();
            LOGGER.info("开始压缩。。。");
            
            ZipUtil.zip(orgProjectPath, assist.getProjectPath(), appInfo.getArtifactId()+ ".zip");
            LOGGER.info("压缩完成！");

            File zipFile = new File(assist.getProjectPath(), appInfo.getArtifactId() + ".zip");
            in = new FileInputStream(zipFile);
            byte[] b = new byte[2048];
            while(in.read(b) > 0){
            	 out.write(b);
            }
           /* Logger.getRootLogger().info("准备删除项目：" + orgProjectPath);
            boolean result = FileUtils.deleteNotDir(orgProjectFile);
            Logger.getRootLogger().info("删除原路径[" + orgProjectPath + "]结果：" + result);*/
            
            
        } catch (Exception e) {
            LOGGER.error("生成项目时发生错误！");
            e.printStackTrace();
        }finally {
			try{
				in.close();
			}catch(Exception e){
				
			}
		}
    }

    public static GenerateAssistant doGenerator(AppInfo appInfo) throws Exception {
        long startTime = System.currentTimeMillis();

        LOGGER.info("开始生产框架....");

        // 应用配置
        MetaDataUtil.getTables(appInfo);

        GenerateAssistant assist = new GenerateAssistant(appInfo);
        List<CodeFactory> list = CodeFactory.getCodeFactory(appInfo, assist);
        for (CodeFactory factory : list) {
            factory.generator();
        }

        long cost = System.currentTimeMillis() - startTime;
        LOGGER.info("生成框架完毕,耗时:[" + cost + "]毫秒");
        
        return assist;
    }
}
