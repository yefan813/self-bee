package com.dmall.bee.factory;


import java.io.File;
import java.io.IOException;
import java.util.List;

import com.dmall.bee.GenerateAssistant;
import com.dmall.bee.domain.AppInfo;
import com.dmall.bee.domain.Table;
import com.dmall.bee.util.VelocityTools;

/**
 * VM模板
 */
public class VMGenerator extends CodeFactory {

    public VMGenerator(AppInfo info, GenerateAssistant assist) {
        super(info,assist);
    }

    public String getPath() {
        //return Constant.getProjectVMPath(info.getArtifactId());
    	return assist.getProjectVMPath();
    }

    public String getPackage() {
        return "";
    }

//    public String getTemplate() {
//        return "velocity-list.vm,velocity-edit.vm,velocity-view.vm";
//    }


    @Override
    public String getTemplate() {
        return "";
    }

    public String getModelName() {
        return "Velocity页面";
    }

    public String getExtName() {
        return ".vm";
    }

    public void generator() {
        List<Table> list = info.getTables();
        if (list.size() <= 0) {
            return;
        }
        File file = new File(getPath() + "/" + getPackage().replace(".", "/"));
        if (!file.exists() || !file.isDirectory()) {
            file.mkdirs();
        }
        data.put("info", info);// 程序信息
        for (Table table : list) {
            data.put("table", table);// 当前表
            String fp = null;
            try {
                for (String tpl : getTemplate().split(",")) {
                    String fn = tpl.split("-")[1];
                    fp = VelocityTools.getVarName(table.getName());
                    LOGGER.info("正在生成" + getModelName() + "对象...[" + fp + "/" + fn + "]");
                    File vmFile = new File(file.getAbsolutePath() + "/" + fp);
                    if (!vmFile.exists() || !vmFile.isDirectory()) {
                        vmFile.mkdirs();
                    }
                    fp = fp + "/" + fn;
                    String classPath = vmFile.getAbsolutePath() + "/" + fn;
                    
                    writeFile(tpl,classPath,data);
                }
            } catch (IOException e) {
                LOGGER.error(getModelName() + "对象[" + fp + "]创建失败", e);
            }
        }
        LOGGER.info(getModelName() + "对象输出路径:" + file.getAbsolutePath());
    }
}
