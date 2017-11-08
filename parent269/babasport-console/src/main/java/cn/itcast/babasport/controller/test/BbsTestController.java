package cn.itcast.babasport.controller.test;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.itcast.babasport.pojo.test.BbsTest;
import cn.itcast.babasport.service.test.BbsTestService;

/** 
* @author jia 作者 E-mail: 184639633@qq.com
* @version 创建时间：2017年11月8日 下午2:30:29 
* 类说明 
*/
@Controller
public class BbsTestController {
	@Resource
	private BbsTestService bbsTestService;
//    @RequestMapping("/test.do")
//	public String test(){
//		return "test";
//	}
	
	@RequestMapping("/test.do")
	public String test(){
		List<BbsTest> list=bbsTestService.selectBbsTests();
		System.out.println("list size:"+list.size());
		return "test";
	}
}
