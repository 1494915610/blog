package blog;

import java.io.IOException;
import java.util.ArrayList;



import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.User;
import bz.BizException;
import bz.UserBiz;
import dao.DBHelper;




/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/login.s")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
     
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//���ܲ���
		String username = request.getParameter("username");
		String userpwd = request.getParameter("userpwd");
		
		UserBiz ubiz = new UserBiz();
		/*Map<String,String> user =null;*/
		User user = null;
		
		try{
			user = ubiz.login(username, userpwd);
		}catch(BizException e){
			e.printStackTrace();
			request.setAttribute("msg", e.getMessage());
			request.getRequestDispatcher("login.jsp").forward(request, response);
			return;
		}
		
		
		//��ѯ���ݿ��ж��û��Ƿ����
		/*DBHelper dbhelper = new DBHelper();
		List<Object> params = new ArrayList<Object>();
		params.add(username);
		params.add(userpwd);
		List<Map<String,String>> ret = dbhelper.find("select * from user where account = ? and pwd = ?",params);
		*/
		if(user == null){
			request.setAttribute("msg", "�û��������������");
			
			//������ʾ
			/*response.sendRedirect("login.jsp?error=yes");*/
			
			request.getRequestDispatcher("login.jsp").forward(request, response);
		}else{
			request.getSession().setAttribute("loginedUser", user);
			response.sendRedirect("index.jsp");
		}
		
	}


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
