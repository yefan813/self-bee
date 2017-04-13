package com.dmall.bee.factory;


import com.dmall.bee.GenerateAssistant;
import com.dmall.bee.domain.AppInfo;
import com.dmall.bee.domain.Table;

/**
 * 业务层
 */
public class ServiceGenerator extends CodeFactory {
    private boolean generatorImpl;
    public ServiceGenerator(AppInfo info, GenerateAssistant assist) {
        super(info,assist);
    }

    public String getPath() {
        //return Constant.getProjectServicePath(info.getArtifactId());
    	return assist.getProjectServicePath();
    }

    public String getPackage() {
        if (generatorImpl) {
            //return Constant.getPackageName(info.getArtifactId()) + ".service.impl";
        	return assist.getPackageName() + ".service.impl"; 
        }
        //return Constant.getPackageName(info.getArtifactId()) + ".service";
        return assist.getPackageName() + ".service";
    }

    public String getTemplate() {
        String path = "plan/" + assist.getDataAccessMode() + "/";
        if (generatorImpl) {
            return path + "service-impl.vm";
        }
        return path + "service.vm";
    }

    public String getModelName() {
        if (generatorImpl) {
            return "ServiceImpl";
        }
        return "Service";
    }

    public String getExtName() {
        return getModelName() + ".java";
    }
    
    public void setExtData(Table table) {
        if (generatorImpl) {// 实现接口
            data.put("packageExt", ".impl");
            data.put("importExt", "import " + /*Constant.getPackageName(info.getArtifactId())*/ assist.getPackageName() + ".service." + table.getClassName()
                    + "Service;");
            data.put("classNameExt", "Impl");
            data.put("interExt", " implements " + table.getClassName() + "Service");
        }
        super.setExtData(table);
    }

    public void generator() {
        if (PLAN_DATABASE.equalsIgnoreCase(assist.getDataAccessMode())) {
            super.generator();
        } else {
            super.generator();
            generatorImpl = true;
            super.generator();
        }
    }


    
}
