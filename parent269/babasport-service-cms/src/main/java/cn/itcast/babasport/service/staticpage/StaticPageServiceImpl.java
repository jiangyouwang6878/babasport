package cn.itcast.babasport.service.staticpage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;

import javax.servlet.ServletContext;

import org.springframework.stereotype.Service;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class StaticPageServiceImpl implements StaticPageService,ServletContextAware {
	private Configuration configuration;
    private ServletContext servletContext;
     // 通过注入FreeMarkerConfigurer对象：获取Configuration、指定模板的位置
 	public void setFreeMarkerConfigurer(FreeMarkerConfigurer freeMarkerConfigurer) {
 		this.configuration = freeMarkerConfigurer.getConfiguration();
 	}

	@Override
	public void setServletContext(ServletContext servletContext) {
		// TODO Auto-generated method stub
		this.servletContext=servletContext;
	}
	/*
	 * (非 Javadoc) 
	* <p>Title: index</p> 
	* <p>Description: 生成静态页面入口</p> 
	* @param id
	* @param rootMap 
	* @see cn.itcast.babasport.service.staticpage.StaticPageService#index(java.lang.String, java.util.Map)
	 */
	@Override
	public void index(String id, Map<String, Object> rootMap) {
		// TODO Auto-generated method stub
		try {
			//生成文件保存的真实路径
			String pathname="/html/product/"+id+".html";
			String path=servletContext.getRealPath(pathname);
			System.out.println(path);
			File file=new File(path);
			File parentFile=file.getParentFile();
			if(!parentFile.exists()){
				parentFile.mkdirs();
			}
			Template template=configuration.getTemplate("product.html");
			Writer out=new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
			//处理
			template.process(rootMap, out);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
