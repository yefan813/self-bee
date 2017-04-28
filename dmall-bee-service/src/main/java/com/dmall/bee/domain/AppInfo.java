package com.dmall.bee.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据库信息
 * @author yefan
 * @since
 */
public class AppInfo {

    protected String groupId = "com.app";

    protected String artifactId;

    protected String author = "bee";

    protected String driver = "com.mysql.jdbc.Driver";

    protected String url;

    protected String user;

    protected String pwd;

    protected String dbName;

    protected String provider = "MYSQL";

    protected String[] selectedTables = new String[]{};

    protected List<DmallService> depServers;

    protected Integer allTables; //1:默认生成所有表， 2：使用选择的表

    protected List<Table> tables;

    protected String dataAccessMode = "PLANnbcommon";//数据访问模式 [nbcommon模式、database模式、cfish模式]

    protected String entityModule = "domain";//pojo、domain、vo、dto、javabean

    public List<DmallService> getDepServers() {
        return depServers;
    }

    public void setDepServers(List<DmallService> depServers) {
        this.depServers = depServers;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getArtifactId() {
        return artifactId;
    }

    public void setArtifactId(String artifactId) {
        this.artifactId = artifactId;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCmd() {
    	return "$";
    }
    
    public String getCmd2() {
    	return "#";
    }
    
    public String[] getType() {
        return new String[] { "TABLE" };
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUser() {
        return user;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public List<Table> getTables() {
        if (tables == null) {
            tables = new ArrayList<Table>();
        }
        return tables;
    }

    public void addTable(Table table) {
        getTables().add(table);
    }

    public String toString() {
        StringBuffer str = new StringBuffer();
        for (Table table : getTables()) {
            str.append(table.toString()).append("\r\n");
        }
        return str.toString();
    }

	public String[] getSelectedTables() {
		return selectedTables;
	}

	public void setSelectedTables(String[] selectedTables) {
		this.selectedTables = selectedTables;
	}

	public Integer getAllTables() {
		return allTables;
	}

	public void setAllTables(Integer allTables) {
		this.allTables = allTables;
	}

    public String getDataAccessMode() {
        return dataAccessMode;
    }

    public void setDataAccessMode(String dataAccessMode) {
        this.dataAccessMode = dataAccessMode;
    }

    public String getEntityModule() {
        return entityModule;
    }

    public void setEntityModule(String entityModule) {
        this.entityModule = entityModule;
    }
}
