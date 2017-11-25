package cn.itcast.babasport.controller.login;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Hex;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.itcast.babasport.pojo.user.Buyer;
import cn.itcast.babasport.service.buyer.BuyerService;
import cn.itcast.babasport.service.sessionprovider.SessionProvider;
import cn.itcast.babasport.utils.token.TokenUtils;

@Controller
public class LoginController {
   @Resource
   private BuyerService buyerService;
   @Resource
   private SessionProvider sessionProvider;
	/**
	 * 
	 * @Title: login
	 * @Description: 跳转到登录页面
	 * @param @return    
	 * @return String    
	 * @throws
	 */
   @RequestMapping(value="/login.aspx",method={RequestMethod.GET})
   public String login(){
	   return "login";
   }
   /**
    * 
    * @Title: login
    * @Description: 验证用户名密码
    * @param @param username
    * @param @param password
    * @param @param ReturnUrl
    * @param @param request
    * @param @param response
    * @param @param model
    * @param @return    
    * @return String    
    * @throws
    */
   @RequestMapping(value="/login.aspx",method={RequestMethod.POST})
   public String login(String username,String password,String ReturnUrl,HttpServletRequest request,HttpServletResponse response,Model model){
	   if(username!=null && !"".equals(username)){
		   Buyer buyer=buyerService.selectBuyerByUserName(username);
		   if(buyer!=null){
			   if(password!=null && !"".equals(password)){
				   if(buyer.getPassword().equals(encodePassword(password))){
					   sessionProvider.setAttributeForUserName(TokenUtils.getCSESSIONID(request, response),username);
					   return "redirect:"+ReturnUrl;
				   }else{
					   model.addAttribute("error", "密码不正确");
				   }
			   }else{
				   model.addAttribute("error", "密码不能为空");
			   }
		   }else{
			   model.addAttribute("error", "用户名不正确");
		   }
	   }else{
		   model.addAttribute("error", "用户名不能为空"); 
	   }
	   return "login";
   }
    /**
     * 
     * @Title: isLogin
     * @Description: 判断是否登录
     * @param @param callback
     * @param @param request
     * @param @param response
     * @param @return
     * @param @throws IOException    
     * @return MappingJacksonValue    
     * @throws
     */
    @RequestMapping("/isLogin.aspx")
	@ResponseBody
	public MappingJacksonValue isLogin(String callback, HttpServletRequest request, HttpServletResponse response) throws IOException{
		// 判断用户是否登录
		String flag = "0"; // 未登录
		String username = sessionProvider.getAttributeForUserName(TokenUtils.getCSESSIONID(request, response));
		if(username != null){ // 已登录
			flag = "1";
		}
		// 响应结果
//		JSONObject jsonObject = new JSONObject();
//		jsonObject.put("flag", flag);
//		response.setContentType("application/json;charset=UTF-8");
//		response.getWriter().write(jsonObject.toString());
		// 在springmvc中去封装回调函数的对象MappingJacksonValue
		MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(flag);
		mappingJacksonValue.setJsonpFunction(callback); // 将回调函数进行封装
		return mappingJacksonValue;
	}
	
	/**
	 * 密码加密。规则： MD5 + 十六进制   spring：盐值
	 * @param password
	 * @return
	 */
	public String encodePassword(String password){
		
		String algorithm = "MD5";
		char[] encodeHex  = null;
		try {
			MessageDigest instance = MessageDigest.getInstance(algorithm);
			// MD5加密后密文
			byte[] digest = instance.digest(password.getBytes());
			// 十六进制再加密一次
			encodeHex = Hex.encodeHex(digest);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return new String(encodeHex);
	}
}
