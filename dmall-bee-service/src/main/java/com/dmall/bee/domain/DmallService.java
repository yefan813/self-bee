package com.dmall.bee.domain;

/**
 * @author: huangjiang Date: 2016/5/12 Time: 17:42
 */
public enum DmallService {
    ERP("com.dmall","dmall-erp-dubbo-service","1.0.81.RELEASE"),
    OOP("com.dmall","oop-api-client","1.2.7"),
    DMALL_LOG("com.dmall","dmall-log","8.0"),
    DMC("com.dmall.monitor","dmc-sdk","0.0.3-SNAPSHOT"),
    DMG("com.dmall.dmg","dmg-sdk","3.0.1"),
    DSF("com.dmall.dsf","dsf-sdk","2.5.7");

    private String groupId;
    private String artifactId;
    private String version;

    private DmallService(String groupId,String artifactId,String version) {
        this.groupId = groupId;
        this.artifactId = artifactId;
        this.version = version;
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

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
