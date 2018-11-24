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
		 * ʵ���ų�Ȩ�޷��ʿ��Ƶ���Դ
		 */
		//��ȡ��ǰ������Դ��
		String path = httpRequest.getServletPath();
		//�жϷ��ʵ���Դ����Ŷ����Ҫ������
		
		
		if(path.endsWith("user.s") || path.endsWith("login.jsp")){

		// �Ѿ���½
		// ����ҵ�����ִ�� ���������� doFilter
			chain.doFilter(request, response);
			return;
		}
		if(httpRequest.getSession().getAttribute("loginedUser")!=null){
			chain.doFilter(request, response);
		
		}else{
			//δ��¼����ת��¼ҳ
			request.setAttribute("msg", "���ȵ�¼ϵͳ");
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
