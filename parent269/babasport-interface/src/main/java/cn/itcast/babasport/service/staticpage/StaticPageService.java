package cn.itcast.babasport.service.staticpage;

import java.util.Map;

public interface StaticPageService {
	/**
	 * 
	* @Title: index
	* @Description: 生成静态页入口
	* @param @param id 作为静态页名称
	* @param @param rootMap   静态页需要的数据 
	* @return void    
	* @throws
	 */
  public void index(String id,Map<String,Object> rootMap);
}
