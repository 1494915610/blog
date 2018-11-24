package blog;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;

import bean.User;
import bz.BizException;
import bz.UserBiz;
import dao.BeanUtils;

/**
 * 用户 Servlet 包含登陆，注册，查询，退出，忘记密码，使用op字段标识业务操作类型
 */
@WebServlet("/user.s")
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String op = request.getParameter("op");
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		if("login".equals(op)){
			login(request,response);
		}else if("query".equals(op)){
			query(request,response);
		}else if("add".equals(op)){
			add(request,response);
		}else if("find".equals(op)){
			find(request,response);
		}else if("save".equals(op)){
			save(request,response);
		}
	}

	private void save(HttpServletRequest request, HttpServletResponse response) throws IOException ,ServletException{
		response.setCharacterEncoding("UTF-8");
		User user =BeanUtils.asBean(request, User.class);
		UserBiz ubiz = new UserBiz();
		String msg;
		try {
			ubiz.save(user);
			msg = "用户信息保存成功！";
		} catch (BizException e) {
			e.printStackTrace();
			msg = e.getMessage();
		}
		
		response.getWriter().append(msg);
	}

	private void find(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("utf-8");
		String id = request.getParameter("id");
		UserBiz ubiz = new UserBiz();
		User user = ubiz.findById(id);
		
		String userString = JSON.toJSONString(user);
		response.getWriter().append(userString);
	}

	private void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		UserBiz ubiz = new UserBiz();
		User user = BeanUtils.asBean(request,User.class);
		
		try{
			ubiz.add(user);
			
		}catch(BizException e){
			e.printStackTrace();
			request.setAttribute("msg", e.getMessage());
		}finally{
			query(request,response);
		}
	}

	private void query(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		UserBiz ubiz = new UserBiz();
		User user = BeanUtils.asBean(request, User.class);
		request.setAttribute("userList", ubiz.find(user));
		request.getRequestDispatcher("manage-user.jsp").forward(request, response);
	}

	

	private void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
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
		
		if(user == null){
			request.setAttribute("msg", "用户名或者密码错误");
			
			//弹窗提示
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
