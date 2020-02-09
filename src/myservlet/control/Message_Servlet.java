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

public class Message_Servlet extends HttpServlet{
	   public void init(ServletConfig config) throws ServletException{
	      super.init(config);
	   }
	   public void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
		   Test_Bean testBean=null;
		   HttpSession session=request.getSession(true);
		   try{
			   testBean=(Test_Bean)session.getAttribute("testBean");
		       if(testBean==null){
		           testBean=new Test_Bean();            //����Javabean����
		           session.setAttribute("testBean",testBean);
		       }
		   } catch(Exception exp){
		       testBean=new Test_Bean();              //����Javabean����
		       session.setAttribute("testBean",testBean);
		   } 
		   try{
		       Class.forName("com.mysql.jdbc.Driver");
		   } catch(Exception e){} 
		   request.setCharacterEncoding("gb2312");
		   String isDelete = request.getParameter("delete");
		   String isReply = request.getParameter("isReply");
		   String id = request.getParameter("id");
		   String name = request.getParameter("name");
		   String belongNumber = request.getParameter("messageNumber");
		   String content = request.getParameter("content");
		   String role = request.getParameter("role");
		   Connection con;
		   Statement sql; 
		   ResultSet rs;
		   String uri="jdbc:mysql://127.0.0.1/licence?"+"user=root&password=&characterEncoding=gb2312";
		   if(isDelete.equals("true")) {	//ɾ��
			   String deleteNumber = request.getParameter("deleteNumber");
			   if(deleteNumber!=null) {
				   try {	//ɾ����ʼ����
					   con=DriverManager.getConnection(uri);
				       sql=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
				       sql.executeUpdate("DELETE FROM message WHERE number = "+deleteNumber);
				       sql.executeUpdate("DELETE FROM messagefollow WHERE belongNumber = "+deleteNumber);
				       testBean.setMessageMess("ɾ���ɹ�");
				       response.sendRedirect("TeacherMore.jsp");
				       return;
				   } catch (Exception e) {
					   testBean.setMessageMess("ɾ��ʧ��");
					   System.out.println("Message_Servlet.jsp  "+e);
					   response.sendRedirect("TeacherMore.jsp");
				       return;
				   }	
			   } else {	//ɾ���ظ�����
				   String deleteFollowNumber = request.getParameter("deleteFollowNumber");
				   String deleteSequence = request.getParameter("deleteSequence");
				   try {
					   con=DriverManager.getConnection(uri);
				       sql=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
				       sql.executeUpdate("DELETE FROM messagefollow WHERE belongNumber = "+deleteFollowNumber+" AND belongSequence = "+deleteSequence);
				       testBean.setMessageMess("ɾ���ɹ�");
				       response.sendRedirect("TeacherMore.jsp");
				       return;
				   } catch (Exception e2) {
					   testBean.setMessageMess("ɾ��ʧ��");
					   System.out.println("Message_Servlet.jsp  "+e2);
					   response.sendRedirect("TeacherMore.jsp");
				       return;
				   }
			   }
			   		   
		   } else if(isReply.equals("true")) {	//�ظ�
			   try {				   
			       con=DriverManager.getConnection(uri);
			       sql=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			       rs=sql.executeQuery("SELECT * FROM messagefollow WHERE belongNumber = " + belongNumber);
			       int sequence;
			       if(rs.next()) {
			    	   rs.last();
				       String sequence0 = rs.getString(2);
				       sequence = Integer.parseInt(sequence0)+1;
			       } else {
			    	   sequence = 1;
			       }			       
			       sql.executeUpdate("INSERT INTO messagefollow VALUES ('"+belongNumber+"','"+sequence+"','"+content+"','"+id+"','"+name+"','"+role+"')");
			       testBean.setMessageMess("�ظ��ɹ�");
			       if(role.equals("1")) response.sendRedirect("More.jsp");
			       else response.sendRedirect("TeacherMore.jsp");
			       return;
			   } catch (Exception e) {
				   testBean.setMessageMess("�ظ�ʧ��");
				   System.out.println("Message_Servlet.jsp  "+e);
				   if(role.equals("1")) response.sendRedirect("More.jsp");
			       else response.sendRedirect("TeacherMore.jsp");
			       return;
			   }
		   } else { 	//����
			   try {				   
			       con=DriverManager.getConnection(uri);
			       sql=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			       rs=sql.executeQuery("SELECT * FROM message");
			       int sequence;
			       if(rs.next()) {
			    	   rs.last();
				       String sequence0 = rs.getString(1);
				       sequence = Integer.parseInt(sequence0)+1;
			       } else {
			    	   sequence = 1;
			       }
			       sql.executeUpdate("INSERT INTO message VALUES ('"+sequence+"','"+content+"','"+id+"','"+name+"','"+role+"')");
			       testBean.setMessageMess("����ɹ�");
			       if(role.equals("1")) response.sendRedirect("More.jsp");
			       else response.sendRedirect("TeacherMore.jsp");
			       return;
			   } catch (Exception e) {
				   testBean.setMessageMess("����ʧ��");
				   System.out.println("Message_Servlet.jsp  "+e);
				   if(role.equals("1")) response.sendRedirect("More.jsp");
			       else response.sendRedirect("TeacherMore.jsp");
			       return;
			   }
		   }
	   }
	   public  void  doGet(HttpServletRequest request,HttpServletResponse response)
	           throws ServletException,IOException{
	       doPost(request,response);
	   }
}