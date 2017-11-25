package cn.itcast.babasport.interceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import cn.itcast.babasport.service.sessionprovider.SessionProvider;
import cn.itcast.babasport.utils.token.TokenUtils;
/**
 * 
 * @ClassName: LoginInterceptor
 * @Description: 登录拦截器
 * @author 
 * @date 2017年11月22日 下午3:47:27
 *
 */
public class LoginInterceptor implements HandlerInterceptor {
    @Resource
    private SessionProvider sessionProvider;
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// TODO Auto-generated method stub
		String username=sessionProvider.getAttributeForUserName(TokenUtils.getCSESSIONID(request, response));
		if(username==null){
			response.sendRedirect("http://localhost:8067/login.aspx?ReturnUrl=http://localhost:8087");
			return false;
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub

	}

}
