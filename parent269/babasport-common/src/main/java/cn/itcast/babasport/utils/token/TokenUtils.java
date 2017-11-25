package cn.itcast.babasport.utils.token;

import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TokenUtils {
   public static String getCSESSIONID(HttpServletRequest request,HttpServletResponse response){
	   //判断cookie中是否有令牌信息
	   Cookie[] cookies=request.getCookies();
	   if(cookies!=null && cookies.length>0){
		   for (Cookie cookie : cookies) {
			if("CSESSIONID".equals(cookie.getName())){
				return cookie.getValue();
			}
		}
	   }
	   
	 //第一次生成令牌信息
	   String CSESSIONID=UUID.randomUUID().toString().replace("-", "");
	   Cookie cookie = new Cookie("CSESSIONID",CSESSIONID);
	   cookie.setMaxAge(60*60);
	   cookie.setPath("/");
	   response.addCookie(cookie);
	   return CSESSIONID;
   }
}
