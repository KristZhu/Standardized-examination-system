package myservlet.control;

import mybean.data.Test_Bean;
import java.sql.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class Audit_Register_Servlet extends HttpServlet{
	public void init(ServletConfig config) throws ServletException{
		super.init(config);
		try {  Class.forName("com.mysql.jdbc.Driver");
		} catch(Exception e){} 
	}
	public void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException {
		request.setCharacterEncoding("utf-8");
	    HttpSession session=request.getSession(true); 
	    Test_Bean testBean=null;
	    try{ 
	        testBean=(Test_Bean)session.getAttribute("testBean");
	        if(testBean==null){
	           testBean=new Test_Bean();             //创建Javabean对象
	           session.setAttribute("testBean",testBean);
	        }
	    } catch(Exception exp){
	        testBean=new Test_Bean();  
	        session.setAttribute("testBean",testBean);
	    }
	    
	    String id = request.getParameter("id");
	    String name = request.getParameter("name");
	    String idNumber = request.getParameter("idNumber");
	    String password = request.getParameter("password");
	    String outcome = request.getParameter("outcome");
	    
	    try {
	        String uri="jdbc:mysql://127.0.0.1/licence?"+"user=root&password=&characterEncoding=gb2312";
	        Connection con=DriverManager.getConnection(uri);
	        Statement sql=con.createStatement();
	        Statement sql2=con.createStatement();
	        ResultSet rs;
	        if(outcome.equals("1")) {	//通过
		    	sql.executeUpdate("INSERT INTO student values('"+id+"','"+password+"','"+name+"',-1,-1,1000,2000,3000,4000,11000,12000,13000,14000,15000,16000,17000)");
		    	sql2.executeUpdate("DELETE FROM register WHERE id = "+id);
		    	testBean.setAuditRegisterBackNews("审核通过成功");
		    	response.sendRedirect("CheckRegister.jsp");
		    	return;
		    } else {	//拒绝
		    	sql.executeUpdate("UPDATE register SET audit = 1 WHERE id = "+id);
		    	testBean.setAuditRegisterBackNews("审核拒绝成功");
		    	response.sendRedirect("CheckRegister.jsp");
		    	return;
		    }
		} catch (Exception e) {
			System.out.println(e);
			testBean.setAuditRegisterBackNews("审核失败");
			response.sendRedirect("CheckRegister.jsp");
	    	return;
		}	    
	}
	public  void  doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
		doPost(request,response);
	}
}