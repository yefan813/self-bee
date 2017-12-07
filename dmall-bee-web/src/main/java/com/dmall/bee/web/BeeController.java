package com.dmall.bee.web;

import com.dmall.bee.AppEngineNew;
import com.dmall.bee.domain.AppInfo;
import com.dmall.bee.domain.RemoteResult;
import com.dmall.bee.util.MetaDataUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.rmi.Remote;

@Controller
public class BeeController extends BaseController {

	private static final Logger BASE_LOG = LoggerFactory.getLogger(BeeController.class);


	/**
	 * 主页面
	 * 
	 * @return
	 */
	@RequestMapping(value={"","/bee"} ,method = { RequestMethod.POST, RequestMethod.GET })
	public String index(ModelMap model) {
//		model.put("DmallService", DmallService.values());
		return "index";
	}
	
	
	/**
	 * 
	 * @return
	 */
	@RequestMapping(value="/bee/create",method = { RequestMethod.POST, RequestMethod.GET })
	public void create(AppInfo appInfo,
			@RequestParam(value="tableNames[]", required=false)String[] tableNames,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setHeader("Content-type", "application/octet-stream;charset=UTF-8");  
		//response.setContentType("application/octet-stream;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
	    response.setHeader("Content-disposition", "attachment;filename="
	        + appInfo.getArtifactId() + ".zip");
	    
	    if(tableNames != null && tableNames.length > 0){
	    	appInfo.setSelectedTables(tableNames);
	    	appInfo.setAllTables(2);
	    }
	    OutputStream os = response.getOutputStream();
	    AppEngineNew.generator(appInfo, os);
	    os.flush();
	    os.close();
	}
	
	/**
	 *验证数据库
	 * 
	 * @return
	 */
	@RequestMapping(value="/bee/validDb",method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public RemoteResult del(AppInfo info) {
			try {
				MetaDataUtil.getTables(info);
				return RemoteResult.success(info.getTables());
			} catch (Exception e) {
				BASE_LOG.error("验证失败", e);
				return RemoteResult.failure("999","验证失败:"+e.getMessage());
			}
	}
}
