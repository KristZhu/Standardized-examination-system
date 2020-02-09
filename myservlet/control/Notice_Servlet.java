package myservlet.control;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mybean.data.Test_Bean;

public class Notice_Servlet extends HttpServlet{
	   public void init(ServletConfig config) throws ServletException{
	      super.init(config);
	   }
	   public void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
		   Test_Bean testBean=null;
		   HttpSession session=request.getSession(true);
		   try{
			   testBean=(Test_Bean)session.getAttribute("testBean");
		       if(testBean==null){
		           testBean=new Test_Bean();            //创建Javabean对象
		           session.setAttribute("testBean",testBean);
		       }
		   } catch(Exception exp){
		       testBean=new Test_Bean();              //创建Javabean对象
		       session.setAttribute("testBean",testBean);
		   } 
		   try{
		       Class.forName("com.mysql.jdbc.Driver");
		   } catch(Exception e){} 
		   request.setCharacterEncoding("gb2312");
		   String isDelete = request.getParameter("isDelete");
		   String isNew = request.getParameter("isNew");
		   String id = request.getParameter("id");
		   String name = request.getParameter("name");
		   String content = request.getParameter("content");
		   String deleteNumber = request.getParameter("deleteNumber");
		   Connection con;
		   Statement sql; 
		   ResultSet rs;
		   String uri="jdbc:mysql://127.0.0.1/licence?"+"user=root&password=&characterEncoding=gb2312";
		   if(isDelete.equals("true")) {	//删除
			   try {
				   con=DriverManager.getConnection(uri);
				   sql=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
				   sql.executeUpdate("DELETE FROM notice WHERE number = "+deleteNumber);
				   testBean.setMessageMess("删除成功");
				   response.sendRedirect("TeacherMore.jsp");
				   return;
				} catch (Exception e) {
				   testBean.setMessageMess("删除失败");
				   System.out.println("Message_Servlet.jsp  "+e);
				   response.sendRedirect("TeacherMore.jsp");
				   return;
				}	 		   
		   }
		   if(isNew.equals("true")) {	//公告
			   try {				   
			       con=DriverManager.getConnection(uri);
			       sql=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			       rs=sql.executeQuery("SELECT * FROM notice");
			       int sequence;
			       if(rs.next()) {
			    	   rs.last();
				       String sequence0 = rs.getString(1);
				       sequence = Integer.parseInt(sequence0)+1;
			       } else {
			    	   sequence = 1;
			       }			       
			       sql.executeUpdate("INSERT INTO notice VALUES ('"+sequence+"','"+content+"','"+id+"','"+name+"')");
			       testBean.setMessageMess("添加成功");
			       response.sendRedirect("TeacherMore.jsp");
			       return;
			   } catch (Exception e) {
				   testBean.setMessageMess("添加失败");
				   System.out.println("Message_Servlet.jsp  "+e);
				   response.sendRedirect("TeacherMore.jsp");
			       return;
			   }
		   }
	   }
	   public  void  doGet(HttpServletRequest request,HttpServletResponse response)
	           throws ServletException,IOException{
	       doPost(request,response);
	   }
}