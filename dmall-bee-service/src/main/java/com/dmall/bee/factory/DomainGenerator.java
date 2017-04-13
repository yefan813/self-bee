package com.dmall.bee.factory;

import com.dmall.bee.GenerateAssistant;
import com.dmall.bee.domain.AppInfo;
import com.dmall.bee.util.VelocityTools;

/**
 * POJO实体类
 */
public class DomainGenerator extends CodeFactory {

    public DomainGenerator(AppInfo info, GenerateAssistant assist) {
        super(info,assist);
    }

    public String getPath() {
    	return assist.getProjectDomainPath();
    }

    public String getPackage() {
    	return assist.getPackageName() + "." + assist.getEntityModule();
    }

    public String getTemplate() {
        String path = "plan/" + assist.getDataAccessMode() + "/";
        return path + "domain.vm";
    }

    public String getModelName() {
        return VelocityTools.upperFirstLetter(assist.getEntityModule());
    }

    public String getExtName() {
        return ".java";
    }

}
