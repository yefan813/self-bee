package com.dmall.bee;

import com.dmall.bee.domain.AppInfo;
import com.dmall.bee.domain.DmallService;
import org.springframework.beans.BeanUtils;

import java.io.File;
import java.util.List;
import java.util.UUID;

public class GenerateAssistant extends AppInfo {
	private final static String LOG_LEVEL = "log.level";
	private final static String LOG_LAYOUT = "log.layout";

	private final static String PROJECT_ENCODING = "project.encoding";
	private final static String PROJECT_AUTHOR = "project.author";
	private final static String PROJECT_VERSION = "project.version";
	
	private final static String PATH_PROJECT = "project.path";


	private UUID uuid;

	public GenerateAssistant(AppInfo info){
        uuid = UUID.randomUUID();
        BeanUtils.copyProperties(info,this);
    }

	/**
	 * 是否删除原项目
	 */
	private final static String DELETE_OLD_PROJECT = "delete.old.project";
	
	/**
	 * 获取日志级别
	 * @return
	 */
	public  String getLogLevel(){
	    return AppSetting.getConf(LOG_LEVEL);
	}
	
	/**
     * 获取日志输出方式
     * @return
     */
    public String getLogLayout(){
    	return AppSetting.getConf(LOG_LAYOUT);
    }

    /**
     * 获取项目artifactId
     * @return
     */
    public String getProjectArtifactId(){
        return artifactId;
    }
    /**
     * 获取项目编码方式
     * @return
     */
    public String getProjectEncoding(){
        return AppSetting.getConf(PROJECT_ENCODING);
    }
    
    /**
     * 获取项目作者
     * @return
     */
    public String getProjectAuthor(){
        return AppSetting.getConf(PROJECT_AUTHOR);
    }

    /**
     * 获取包GroupId
     * @return
     */
    public String getPackageGroupId(){
        return groupId;
    }
    
    /**
     * 获取包名称
     * @return
     */
    public String getPackageName(){
        return groupId +"." + getNameByArtifactId();
    }

    public String getArtifactPackName(){
        return getNameByArtifactId();
    }
    private String getNameByArtifactId(){
        // dmall-print
        if(artifactId.indexOf("-")!=-1){
            String str=artifactId;
            str=str.replace("-",".");
            if(str.startsWith("dmall.")){
                str=str.substring(6);
            }
            return str;
        }else{
            return artifactId;
        }
    }
    /**
     * 获取项目版本
     * @return
     */
    public String getProjectVersion(){
        return AppSetting.getConf(PROJECT_VERSION);
    }
    
    /**
     * 获取项目路径
     * @return
     */
    public String getProjectPath(){
//        return AppSetting.getConf(PATH_PROJECT)+File.separator+uuid.toString();
        return "/Users/yefan/Desktop/aa";
    }
    
    /**
     * 获取mapper路径
     * @return
     */
    public String getProjectMapperPath(){
        StringBuffer path = new StringBuffer(getProjectPath().trim());
        if (!"/".equals(path.substring(path.length()-1))) { // 如果最后一个路径没有'/'，则添加'/'
            path.append("/");
        }
        path.append(artifactId).append("/");
        path.append(artifactId).append("-dao/src/main/resources/sqlMapper/");
        return path.toString();
    }
    
    /**
     * 获取分层路径
     * @return
     */
    private String getProjectLayerPath(String layer){
        StringBuffer path = new StringBuffer(getProjectPath().trim());
        if (!"/".equals(path.substring(path.length()-1))) { // 如果最后一个路径没有'/'，则添加'/'
        	path.append("/");
        }
        path.append(artifactId).append("/");
        path.append(artifactId).append("-").append(layer);
        path.append("/src/main/java/");
        return path.toString();
    }
    
    /**
     * 获取dao路径
     * @return
     */
    public String getProjectDaoPath(){
        return getProjectLayerPath("dao");
    }
    
    /**
     * 获取service路径
     * @return
     */
    public String getProjectServicePath(){
        return getProjectLayerPath("service");
    }
    
    /**
     * 获取domain路径
     * @return
     */
    public String getProjectDomainPath(){
        return getProjectLayerPath(getEntityModule());
    }
    
    /**
     * 获取web路径
     * @return
     */
    public String getProjectWebPath(){
        return getProjectLayerPath("web");
    }
    
    /**
     * 获取vm路径
     * @return
     */
    public String getProjectVMPath(){
        StringBuffer path = new StringBuffer(getProjectPath().trim());
        if (!"/".equals(path.substring(path.length()-1))) { // 如果最后一个路径没有'/'，则添加'/'
            path.append("/");
        }
        path.append(artifactId).append("/");
        path.append(artifactId).append("-web/src/main/webapp/WEB-INF/vm/");
        return path.toString();
    }

    public boolean isDepServers(DmallService en){
        List<DmallService> list=this.getDepServers();
        for(DmallService d : list){
            if(d.getArtifactId().equals(en.getArtifactId())){
                return true;
            }
        }
        return false;
    }
    public boolean isDmc(){
        return isDepServers(DmallService.DMC);
    }

    /**
     * 是否删除原项目文件
     * @return
     */
    public boolean isDeleteOldProject(){
    	String str = AppSetting.getConf(DELETE_OLD_PROJECT);
        return "true".equals(str);
    }

    /**
     * 获取数据库提供商
     * @return
     */
    public String getDbProvider(){
        return provider;
    }

    /**
     * 获取数据库目录
     * @return
     */
    public String getDbCatalog(){
        return null;
    }

    /**
     * 获取数据库表匹配
     * @return
     */
    public String getDbTablePattern(){
        return null;
    }

    /**
     * 获取数据库驱动
     * @return
     */
    public String getDbDriver(){
        return driver;
    }

    /**
     * 获取数据库连接字符串
     * @return
     */
    public String getDbUrl(){
        return url;
    }

    /**
     * 获取数据库用户
     * @return
     */
    public String getDbUser(){
        return user;
    }

    /**
     * 获取数据库密码
     * @return
     */
    public String getDbPwd(){
        return pwd;
    }

}
