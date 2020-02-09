package myservlet.control;

import mybean.data.Test_Bean;
import java.sql.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Retest_Servlet extends HttpServlet{
	public void init(ServletConfig config) throws ServletException{
		super.init(config);
		try {  Class.forName("com.mysql.jdbc.Driver");
		} catch(Exception e){} 
	}
	public void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException {
		JFrame jFrame = new JFrame();
		jFrame.setSize(400, 100);
		Toolkit kit = Toolkit.getDefaultToolkit();             //���幤�߰�
		Dimension screenSize = kit.getScreenSize();            //��ȡ��Ļ�ĳߴ�
		int screenWidth = screenSize.width;                    //��ȡ��Ļ�Ŀ�
		int screenHeight = screenSize.height;                  //��ȡ��Ļ�ĸ�
		jFrame.setLocation(screenWidth/2-200, screenHeight/2-50);
		jFrame.setVisible(true);
		Container container = jFrame.getContentPane();
		JLabel jLabel = new JLabel("���ڼ��أ����Ժ�");
		jLabel.setFont(new Font("",1,30));
		container.add(jLabel);
		
		request.setCharacterEncoding("utf-8");
	    HttpSession session=request.getSession(true); 
	    Test_Bean testBean=null;
	    try{ 
	        testBean=(Test_Bean)session.getAttribute("testBean");
	        if(testBean==null){
	           testBean=new Test_Bean();             //����Javabean����
	           session.setAttribute("testBean",testBean);
	        }
	    } catch(Exception exp){
	        testBean=new Test_Bean();  
	        session.setAttribute("testBean",testBean);
	    }
	    
	    String id = request.getParameter("id");
	    String name = request.getParameter("name");
	    String kemu = request.getParameter("kemu");
	    
	    try {
	        String uri="jdbc:mysql://127.0.0.1/licence?"+"user=root&password=&characterEncoding=gb2312";
	        Connection con=DriverManager.getConnection(uri);
	        Statement sql=con.createStatement();
	        ResultSet rs;
	        if(kemu.equals("1")) {	//1
		    	sql.executeUpdate("UPDATE student SET scoreKemu1 = '-1' WHERE id = " + id);
		    	testBean.setRetestBackNews("�������¿��Գɹ�");
		    	jFrame.dispose();
		    	response.sendRedirect("CheckScore.jsp");
		    	return;
		    } else {	//4
		    	sql.executeUpdate("UPDATE student SET scoreKemu4 = '-1' WHERE id = " + id);
		    	testBean.setRetestBackNews("�������¿��Գɹ�");
		    	jFrame.dispose();
		    	response.sendRedirect("CheckScore.jsp");
		    	return;
		    }
		} catch (Exception e) {
			System.out.println(e);
			testBean.setAuditRegisterBackNews("�������¿���ʧ��");
			jFrame.dispose();
			response.sendRedirect("CheckScore.jsp");
	    	return;
		}	    
	}
	public  void  doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
		doPost(request,response);
	}
}