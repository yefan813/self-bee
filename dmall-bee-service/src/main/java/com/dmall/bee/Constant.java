package com.dmall.bee;


/**
 * 常量配置
 * 
 */
public class Constant {

	private final static String LOG_LEVEL = "log.level";
	private final static String LOG_LAYOUT = "log.layout";

	private final static String PROJECT_ENCODING = "project.encoding";
	private final static String PROJECT_AUTHOR = "project.author";
	private final static String PROJECT_VERSION = "project.version";
	
	private final static String PATH_PROJECT = "project.path";
	
	private final static String GROUP_ID = "com.dmall";
	
	/**
	 * 是否删除原项目
	 */
	private final static String DELETE_OLD_PROJECT = "delete.old.project";
	
	/**
	 * 获取日志级别
	 * @return
	 */
	public static String getLogLevel(){
	    return AppSetting.getConf(LOG_LEVEL);
	}
	
	/**
     * 获取日志输出方式
     * @return
     */
    public static String getLogLayout(){
    	return AppSetting.getConf(LOG_LAYOUT);
    }
    
    /**
     * 获取项目编码方式
     * @return
     */
    public static String getProjectEncoding(){
        return AppSetting.getConf(PROJECT_ENCODING);
    }
    
    /**
     * 获取项目作者
     * @return
     */
    public static String getProjectAuthor(){
        return AppSetting.getConf(PROJECT_AUTHOR);
    }

    /**
     * 获取包GroupId
     * @return
     */
    public static String getPackageGroupId(){
        return GROUP_ID;
    }
    
    /**
     * 获取包名称
     * @return
     */
    public static String getPackageName(String artifactId){
        return GROUP_ID +"." + artifactId;
    }
    /**
     * 获取项目版本
     * @return
     */
    public static String getProjectVersion(){
        return AppSetting.getConf(PROJECT_VERSION);
    }
    
    /**
     * 获取项目路径
     * @return
     */
    public static String getProjectPath(){
        return AppSetting.getConf(PATH_PROJECT);
    }


    
    /**
     * 获取mapper路径
     * @return
     */
    public static String getProjectMapperPath(String artifactId){
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
    private static String getProjectLayerPath(String layer, String artifactId){
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
    public static String getProjectDaoPath(String artifactId){
        return getProjectLayerPath("dao",artifactId);
    }
    
    /**
     * 获取service路径
     * @return
     */
    public static String getProjectServicePath(String artifactId){
        return getProjectLayerPath("service", artifactId);
    }
    
    /**
     * 获取domain路径
     * @return
     */
    public static String getProjectDomainPath(String artifactId){
        return getProjectLayerPath("domain", artifactId);
    }
    
    /**
     * 获取web路径
     * @return
     */
    public static String getProjectWebPath(String artifactId){
        return getProjectLayerPath("web", artifactId);
    }
    
    /**
     * 获取vm路径
     * @return
     */
    public static String getProjectVMPath(String artifactId){
        StringBuffer path = new StringBuffer(getProjectPath().trim());
        if (!"/".equals(path.substring(path.length()-1))) { // 如果最后一个路径没有'/'，则添加'/'
            path.append("/");
        }
        path.append(artifactId).append("/");
        path.append(artifactId).append("-web/src/main/webapp/WEB-INF/vm/");
        return path.toString();
    }
  
    /**
     * 是否删除原项目文件
     * @return
     */
    public static boolean isDeleteOldProject(){
    	String str = AppSetting.getConf(DELETE_OLD_PROJECT);
        return "true".equals(str);
    }
    
}
