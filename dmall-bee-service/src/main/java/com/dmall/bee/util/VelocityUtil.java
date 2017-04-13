package com.dmall.bee.util;

import java.io.File;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URL;
import java.util.Map;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dmall.bee.Constant;

/**
 * Velocity引擎
 */
public class VelocityUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(VelocityUtil.class);

    private static final VelocityEngine ve;
    static {
        ve = new VelocityEngine();
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        URL url = loader.getResource("vm");
        File file = new File(url.getFile());
        ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "file");
        ve.setProperty(RuntimeConstants.FILE_RESOURCE_LOADER_PATH, file.getAbsolutePath());
        ve.setProperty(RuntimeConstants.FILE_RESOURCE_LOADER_CACHE, "true");
        ve.setProperty(RuntimeConstants.RUNTIME_LOG_LOGSYSTEM_CLASS, "org.apache.velocity.runtime.log.NullLogChute");
        try {
			ve.init();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    public static String getDir() {
        return (String) ve.getProperty(RuntimeConstants.FILE_RESOURCE_LOADER_PATH);
    }
    
    public static boolean checkExists(String vm) {
        return ve.resourceExists(vm);
    }
    
    public static String getTpl(String view) {
        return getTpl(view, null);
    }

    public static String getTpl(String view, Map<String, Object> value) {
        StringWriter content = new StringWriter();
        getTpl(view, value, content);
        return content.toString();
    }

    public static void getTpl(String view, Map<String, Object> value, Writer writer) {
        try {
            if (value != null) {
                value.put("tools", VelocityTools.class);
               // value.put("constant", Constant.class);
            }
            Template tpl = ve.getTemplate(view, Constant.getProjectEncoding());
            tpl.merge(new VelocityContext(value), writer);
            writer.flush();
        } catch (Exception e) {
            LOGGER.error("输出模板" + view + "异常", e);
        }
    }
}
