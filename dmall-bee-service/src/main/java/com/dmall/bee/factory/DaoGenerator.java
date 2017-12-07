package com.dmall.bee.factory;


import com.dmall.bee.GenerateAssistant;
import com.dmall.bee.domain.AppInfo;
import com.dmall.bee.domain.Table;

/**
 * DAO层
 */
public class DaoGenerator extends CodeFactory {

    private boolean generatorImpl;

    public DaoGenerator(AppInfo info, GenerateAssistant assist) {
        super(info, assist);
    }

    public String getPath() {
        return assist.getProjectDaoPath();
    }

    public String getPackage() {
        if (generatorImpl) {
            return assist.getPackageName() + ".dao.impl";
        }
        return assist.getPackageName() + ".dao";
    }

    public String getTemplate() {
        String path = "plan/" + assist.getDataAccessMode() + "/";
        if (generatorImpl) {
            return path + "dao-impl.vm";
        }
        return path + "dao.vm";
    }

    public String getModelName() {
        if (generatorImpl) {
            return "DaoImpl";
        }
        return "Dao";
    }

    public String getExtName() {
        return getModelName() + ".java";
    }

    public void setExtData(Table table) {
        if (generatorImpl) {// 实现接口
            data.put("packageExt", ".impl");
            data.put("importExt", "import " + /*Constant.getPackageName(info.getArtifactId())*/assist.getPackageName() + ".dao." + table.getClassName()
                    + "Dao;");
            data.put("classNameExt", "Impl");
            data.put("interExt", " implements " + table.getClassName() + "Dao");
        }
        super.setExtData(table);
    }

    public void generator() {
        if (PLAN_DATABASE.equalsIgnoreCase(assist.getDataAccessMode())) {
            super.generator();
        } else if(PLAN_NBCOMMON.equalsIgnoreCase(assist.getDataAccessMode())) {
            super.generator();
            generatorImpl = true;
            super.generator();
        }else if(PLAN_FISH.equalsIgnoreCase(assist.getDataAccessMode())){
            //TODO
            super.generator();
            generatorImpl = true;
            super.generator();
        }
    }
}
