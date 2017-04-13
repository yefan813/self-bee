package com.dmall.bee.factory;

import com.dmall.bee.GenerateAssistant;
import com.dmall.bee.domain.AppInfo;
import com.dmall.bee.util.VelocityUtil;
import org.springframework.util.FileCopyUtils;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * 项目创建
 */
public class ProjectGenerator extends CodeFactory {

    public ProjectGenerator(AppInfo info, GenerateAssistant assist) {
        super(info, assist);
    }

    public String getPath() {
        return assist.getProjectPath();
    }

    public String getPackage() {
        return assist.getPackageName();
    }

    public String getTemplate() {
        return ",common,domain,dao,service,web";
    }

    public String getModelName() {
        return "Project";
    }

    public String getExtName() {
        return null;
    }

    public void generator() {
        File file = null;
        try {
            data.put("info", info);// 程序信息
            data.put("constant", assist);
            String[] tps = getTemplate().split(",");
            data.put("template", tps);
            for (String model : tps) {
                // 父/子模块根路径
                String ph = info.getArtifactId()
                        + ("".equals(model) ? "" : "/" + info.getArtifactId() + "-" + srcToDesModel(model));
                file = new File(getPath() + "/" + ph);
                if (!file.exists() || !file.isDirectory()) {
                    file.mkdirs();
                }
                if (!"".equals(model)) {
                    createDirs(file, model);
                    createConfig(file, model);
                    createPages(file, model);
                    createJavaCode(file, model);
                    model = model + "-";
                }
                LOGGER.info("正在生成" + getModelName() + "对象...[" + ph + "/pom.xml]");
                writeFile("project/pom/" + model + "pom.vm", file.getAbsolutePath() + "/pom.xml", data);
            }

        } catch (IOException e) {
            if (file != null) {
                LOGGER.error(getModelName() + "对象[" + file.getAbsolutePath() + "/pom.xml]创建失败", e);
            }
        }
    }

    /**
     * 创建配置文件
     * @param file
     * @param model
     */
    private void createConfig(File file, String model) {
        Map<String, String> list = new HashMap<String, String>();
        String path = file.getAbsolutePath() + "/src/main/resources/";
        if ("dao".equals(model)) {

            list.put("dao-env.vm", path + File.separator+ "env" + File.separator + "dev.properties");

            if (PLAN_DATABASE.equals(info.getDataAccessMode())) {
                list.put("spring-dao-database.vm", path + "spring-dao.xml");
                list.put("database-core.vm", path + "/sqlMapper/core.xml");
            } else if (PLAN_NBCOMMON.equals(info.getDataAccessMode())) {
                list.put("spring-dao-nbcommon.vm", path + "spring-dao.xml");
                list.put("spring-sqlmap-config.vm", path + "sqlmap-config.xml");
            }
        } else if ("service".equals(model)) {
            list.put("spring-service.vm", path + "spring-service.xml");
        } else if ("web".equals(model)) {
            list.put("web.vm", file.getAbsolutePath() + "/src/main/webapp/WEB-INF/web.xml");
            list.put("gitignore.vm", assist.getProjectPath()+"/"+info.getArtifactId()+ "/.gitignore");
            list.put("spring-all.vm", path + "spring-all.xml");
            list.put("spring-mvc.vm", path + "spring-mvc.xml");
            list.put("spring-dsf-all.vm", path + "spring-dsf-all.xml");
            list.put("spring-dsf-erp-client.vm", path + "spring-dsf-erp-client.xml");
            list.put("spring-dmg.vm", path + "spring-dmg.xml");
            list.put("logback.vm", path + "logback.xml");
            list.put("web-env.vm", path + File.separator+ "env" + File.separator +  "dev.properties");
        } else if ("rpc".equals(model)) {
            list.put("spring-rpc.vm", path + "spring-rpc.xml");
        } else if ("worker".equals(model)) {
            list.put("spring-worker.vm", path + "spring-worker.xml");
        }

        for (Map.Entry<String, String> entry : list.entrySet()) {
            String p = "";
            try {
                File cf = new File(entry.getValue());
                File cDir = cf.getParentFile();
                if (!cDir.exists() || !cDir.isDirectory()) {
                    cDir.mkdirs();
                }
                p = cf.getAbsolutePath().replace(file.getAbsolutePath(), "").replace("\\", "/");
                LOGGER.info("正在生成" + getModelName() + "对象...[" + info.getArtifactId() + p + "]");
                writeFile("project/config/" + entry.getKey(), entry.getValue(), data);
            } catch (Exception e) {
                LOGGER.error(getModelName() + "对象[" + info.getArtifactId() + p + "]创建失败", e);
            }
        }
    }

    /**
     * 创建页面文件，只针对web模块使用
     * @param file
     * @param model
     */
    private void createPages(File file, String model) {
        if (!"web".equals(model)) {
            return;
        }
        String dir = VelocityUtil.getDir();
        //String des = Constant.getProjectVMPath(info.getArtifactId());
        String des = assist.getProjectVMPath();
        copyRes("/pages", new File(dir + "/project/pages"), new File(des), null);
        //des = getPath() + "/" + info.getArtifactId() + "/" + info.getArtifactId()
        //        + "-web/src/main/webapp/WEB-INF/statics";
        //copyRes("/statics", new File(dir + "/project/statics"), new File(des), null);
        des = getPath() + "/" + info.getArtifactId() + "/" + info.getArtifactId()
        + "-web/src/main/webapp/assets";
        copyRes("/assets", new File(dir + "/project/assets"), new File(des), null);
    }

    private void copyRes(String vm, File source, File desFile, String ext) {
        try {
            if (source.exists() && source.isFile()) {
                File cDir = desFile.getParentFile();
                if (!cDir.exists() || !cDir.isDirectory()) {
                    cDir.mkdirs();
                }
                if (vm.endsWith(".vm")) {
                    if (ext != null && !"".equals(ext)) {
                        desFile = new File(desFile.getAbsolutePath().replace(".vm", ext));
                    }
                    writeFile("/project" + vm, desFile.getAbsolutePath(), data);
                    LOGGER.info("正在生成" + getModelName() + "对象...[" + desFile.getAbsolutePath() + "]");
                } else {
                    LOGGER.info("正在拷贝" + getModelName() + "对象...[" + desFile.getAbsolutePath() + "]");
                    FileCopyUtils.copy(source, desFile);
                   
                }
            } else if (source.exists() && source.isDirectory()) {
                for (File dir : source.listFiles()) {
                    File newDesFile = new File(desFile.getAbsolutePath() + "/" + dir.getName());
                    if(dir.getName().startsWith("PLAN")) {
                        if(!dir.getName().equals(assist.getDataAccessMode())) {
                            continue;
                        } else {
                            newDesFile = desFile;
                        }
                    }
                    copyRes(vm + "/" + dir.getName(), dir, newDesFile,
                            ext);
                }
            }
           
        } catch (Exception e) {
            LOGGER.error(getModelName() + "对象[" + desFile.getAbsolutePath() + "]创建失败", e);
        }
    }

    /**
     * 创建Java公共代码
     * @param file
     * @param model
     */
    private void createJavaCode(File file, String model) {
        String dir = VelocityUtil.getDir();
        String groupId = assist.getPackageGroupId().replace(".", "/");
        String artifactId = info.getArtifactId();
        String desModel = srcToDesModel(model);
        String packageName=assist.getArtifactPackName();
        packageName=packageName.replace(".","/");
        String des = String.format("%s/%s/%s-%s/src/main/java/%s/%s/%s", getPath(), artifactId, artifactId, desModel, groupId, packageName, desModel);
//        String des = getPath() + "/" + artifactId + "/" + artifactId + "-" + desModel + "/src/main/java/" + groupId + "/" + artifactId + "/" + desModel;
        copyRes("/java/" + model, new File(dir + "/project/java/" + model), new File(des), ".java");
    }

    /**
     * 创建项目目录结构
     * @param file
     * @param model
     */
    private void createDirs(File file, String model) {
        File other = new File(file.getAbsolutePath() + "/src/main/java");
        if (!other.exists() || !other.isDirectory()) {
            other.mkdirs();
        }
        other = new File(file.getAbsolutePath() + "/src/main/resources");
        if (!other.exists() || !other.isDirectory()) {
            other.mkdirs();
        }
        other = new File(file.getAbsolutePath() + "/src/test/java");
        if (!other.exists() || !other.isDirectory()) {
            other.mkdirs();
        }
        other = new File(file.getAbsolutePath() + "/src/test/resources");
        if (!other.exists() || !other.isDirectory()) {
            other.mkdirs();
        }
    }

    private String srcToDesModel(String model) {
        if ("domain".equals(model)) {
            return assist.getEntityModule();
        }
        return model;
    }
    
	public static long copy(File srcFile, File outFile) throws IOException {
		FileInputStream fis = null;
		DataInputStream dis = null;
		FileOutputStream fos = null;
		DataOutputStream out = null;
		long size = 0;
		try {
			fis = new FileInputStream(srcFile);
			dis = new DataInputStream(fis);
			fos = new FileOutputStream(outFile);
			out = new DataOutputStream(fos);
			int temp;
			byte[] b = new byte[2048];
			while ((temp = dis.read(b)) != -1) {
				fos.write(b,0,temp);
				size += temp;
			}
		} catch (FileNotFoundException ex) {
			throw ex;
		} finally {
			if (fis != null)
				fis.close();
			if (out != null)
				out.close();
		}
		return size;
	}
	
	public static void main(String[] args) throws IOException {
		/*copy(new File("E:\\works\\dms\\dmall-emp\\dmall-emp-manage\\src\\main\\webapp\\assets\\font\\fontawesome-webfont.ttf"), 
				new File("E:\\fontawesome-webfont.ttf"));*/
		FileCopyUtils.copy(new File("E:\\works\\dms\\dmall-bee\\dmall-bee-web\\src\\main\\resources\\vm\\project\\assets\\font\\fontawesome-webfont.ttf"), 
				new File("E:\\web\\temp\\93e69c27-2383-460b-bf3e-253331b63594\\dm-print\\dm-print-web\\src\\main\\webapp\\assets\\font\\fontawesome-webfont.ttf"));
	}
}
