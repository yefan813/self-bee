package com.dmall.bee.factory;


import com.dmall.bee.GenerateAssistant;
import com.dmall.bee.domain.AppInfo;

/**
 * Controller
 */
public class ControllerGenerator extends CodeFactory {

	public ControllerGenerator(AppInfo info, GenerateAssistant assist) {
		super(info, assist);
	}

	public String getPath() {
		return assist.getProjectWebPath();
	}

	public String getPackage() {
		return assist.getPackageName() + ".web.controller";
	}

	public String getTemplate() {
		String path = "plan/" + assist.getDataAccessMode() + "/";
		return path + "controller.vm";
	}

	public String getModelName() {
		return "Controller";
	}

	public String getExtName() {
		return getModelName() + ".java";
	}
}
