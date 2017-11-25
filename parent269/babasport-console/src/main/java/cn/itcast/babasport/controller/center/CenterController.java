package cn.itcast.babasport.controller.center;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/center")
public class CenterController {
    /*
     *后台系统主页
     * */
	@RequestMapping("/index.do")
	public String index(){
		return "index";
	}
	
	/**
	 * 跳转到top页面
	 */
	@RequestMapping("/top.do")
	public String top(){
		return "top";
	}
	
	/**
	 * 跳转到main页面
	 */
	@RequestMapping("/main.do")
	public String main(){
		return "main";
	}
	
	/**
	 * 跳转到left页面
	 */
	@RequestMapping("/left.do")
	public String left(){
		return "left";
	}
	
	/**
	 * 跳转到right页面
	 */
	@RequestMapping("/right.do")
	public String right(){
		return "right";
	}
	
}
