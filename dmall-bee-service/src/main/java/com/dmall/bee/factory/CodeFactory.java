package com.dmall.bee.factory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dmall.bee.GenerateAssistant;
import com.dmall.bee.domain.AppInfo;
import com.dmall.bee.domain.Table;
import com.dmall.bee.util.VelocityUtil;

/**
 * 代码工厂
 */
public abstract class CodeFactory {
    public final static String PLAN_NBCOMMON = "PLANnbcommon";
    public final static String PLAN_DATABASE = "PLANdatabase";

    protected final Logger LOGGER = LoggerFactory.getLogger(getClass());

    protected AppInfo info;
    
    protected GenerateAssistant assist;

    protected Map<String, Object> data = new HashMap<String, Object>();

    public CodeFactory(AppInfo info, GenerateAssistant assist) {
        this.info = info;
        this.assist = assist;
    }

    /**
     * 获取存放路径
     * @return
     */
    public abstract String getPath();

    /**
     * 获取包名称
     * @return
     */
    public abstract String getPackage();

    /**
     * 获取模板
     * @return
     */
    public abstract String getTemplate();

    /**
     * 获取对象名称
     * @return
     */
    public abstract String getModelName();

    /**
     * 获取扩展名
     * @return
     */
    public abstract String getExtName();

    /**
     * 设置扩展属性
     */
    public void setExtData(Table table) {
        data.put("table", table);// 当前表
    }

    /**
     * 执行
     */
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
        data.put("constant", assist);
        for (Table table : list) {
            setExtData(table);
            String clzName = table.getClassName();
            try {
                LOGGER.info("正在生成" + getModelName() + "对象...[" + clzName + getExtName() + "]");
                String classPath = file.getAbsolutePath() + File.separator + clzName;
                
                writeFile(getTemplate(),classPath + getExtName(),data);
            } catch (IOException e) {
                LOGGER.error(getModelName() + "对象[" + clzName + getExtName() + "]创建失败", e);
            }
        }
        LOGGER.info(getModelName() + "对象输出路径:" + file.getAbsolutePath());
    }

    public static List<CodeFactory> getCodeFactory(AppInfo info, GenerateAssistant assist) {
        List<CodeFactory> list = new ArrayList<CodeFactory>();
        list.add(new ProjectGenerator(info,assist));
        list.add(new DaoGenerator(info,assist));
        list.add(new MapperGenerator(info,assist));
        list.add(new ServiceGenerator(info,assist));
        list.add(new DomainGenerator(info,assist));
        list.add(new ControllerGenerator(info,assist));
        //list.add(new VMGenerator(info,assist));
        return list;
    }
    
    protected void writeFile(String tplPath, String destPath, Map<String,Object> data) throws IOException {
    	 FileOutputStream os = new FileOutputStream(destPath);
    	 Writer writer = new OutputStreamWriter(os, Charset.forName("UTF-8"));
         VelocityUtil.getTpl(tplPath, data, writer);
         writer.close();
	}
}
