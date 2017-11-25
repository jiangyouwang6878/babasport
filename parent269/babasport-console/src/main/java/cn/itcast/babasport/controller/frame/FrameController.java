package cn.itcast.babasport.controller.frame;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/frame")
public class FrameController {
  /**
   * 跳转到product_main页面
   */
	@RequestMapping("/product_main.do")
	public String product_main(){
		return "frame/product_main";
	}
	
  /**
   * 跳转到product_left页面
   */
	@RequestMapping("/product_left.do")
	public String product_left(){
		return "frame/product_left";
	}
	/**
	 * 
	* @Title: ad_main
	* @Description: 跳转到主广告页面
	* @param @return    
	* @return String    
	* @throws
	 */
	@RequestMapping("/ad_main")
	public String ad_main(){
		return "frame/ad_main";
	}
	@RequestMapping("/ad_left")
	public String ad_left(){
		return "frame/ad_left";
	}
}
