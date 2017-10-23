package com.dmall.bee.web.utils;

import org.springframework.web.servlet.view.AbstractUrlBasedView;
import org.springframework.web.servlet.view.velocity.VelocityLayoutViewResolver;

import java.util.Map;

public class CusVelocityLayoutViewResolver extends VelocityLayoutViewResolver {

	/**
     * 存放公用url的map
     */
    private Map<String, UrlUtil> velocityUrl;


    /**
     * 页面工具类
     */
    private Map<String, Object> velocityTools;

    @SuppressWarnings("rawtypes")
	protected Class requiredViewClass() {
        return CusVelocityLayoutView.class;
    }

    /**
     * 构建视图
     * @param viewName
     * @return
     * @throws Exception
     */
    protected AbstractUrlBasedView buildView(String viewName) throws Exception {
        CusVelocityLayoutView view = (CusVelocityLayoutView) super.buildView(viewName);
        if(this.velocityUrl != null && velocityUrl.size() > 0){
            view.setVelocityUrl(velocityUrl);
        }
        if(this.velocityTools != null && velocityTools.size() > 0){
            view.setVelocityTools(velocityTools);
        }
        return view;
    }

    public void setVelocityUrl(Map<String, UrlUtil> velocityUrl) {
        this.velocityUrl = velocityUrl;
    }

    public void setVelocityTools(Map<String, Object> velocityTools) {
        this.velocityTools = velocityTools;
    }
}

