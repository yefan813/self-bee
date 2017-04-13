package com.dmall.bee.factory;

import com.dmall.bee.GenerateAssistant;
import com.dmall.bee.domain.AppInfo;

/**
 * mybatis映射文件
 */
public class MapperGenerator extends CodeFactory {

    public MapperGenerator(AppInfo info, GenerateAssistant assist) {
        super(info,assist );
    }

    public String getPath() {
    	return assist.getProjectMapperPath();
    }

    public String getPackage() {
        return "";
    }

    public String getTemplate() {
        String path = "plan/" + assist.getDataAccessMode() + "/";
        return path + "mapper.vm";
    }

    public String getModelName() {
        return "Mapper";
    }

    public String getExtName() {
        return ".xml";
    }
}
