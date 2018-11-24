package blog;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

/**
 * Servlet Filter implementation class LoginFiter
 */
@WebFilter(urlPatterns={"*.jsp","*.s"})
public class LoginFilter implements Filter {

  
	public void destroy() {
		
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		/*
		 * 
		 * 实现排除权限访问控制的资源
		 */
		//获取当前访问资源名
		String path = httpRequest.getServletPath();
		//判断访问的资源名是哦夫需要被拦截
		
		
		if(path.endsWith("user.s") || path.endsWith("login.jsp")){

		// 已经登陆
		// 正常业务必须执行 过滤器链的 doFilter
			chain.doFilter(request, response);
			return;
		}
		if(httpRequest.getSession().getAttribute("loginedUser")!=null){
			chain.doFilter(request, response);
		
		}else{
			//未登录，跳转登录页
			request.setAttribute("msg", "请先登录系统");
			request.getRequestDispatcher("login.jsp").forward(request, response);
		}
		
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
