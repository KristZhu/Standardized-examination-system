package myservlet.control;

import mybean.data.Test_Bean; 
import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Register_Servlet extends HttpServlet{
   public void init(ServletConfig config) throws ServletException{
       super.init(config);
   }
   public void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
	  /*
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
	  */
	  Test_Bean testBean=null;
	  HttpSession session=request.getSession(true);
	  try{
	      testBean=(Test_Bean)session.getAttribute("testBean");
	      if(testBean==null){
	          testBean=new Test_Bean();            //����Javabean����
	          session.setAttribute("testBean",testBean);
	      }
	  }
	  catch(Exception exp){
	      testBean=new Test_Bean();              //����Javabean����
	      session.setAttribute("testBean",testBean);
	  } 
      try {
          Class.forName("com.mysql.jdbc.Driver");
      }
      catch(Exception e){ }  
      request.setCharacterEncoding("utf8");
      String id=request.getParameter("id");
      String name=request.getParameter("name");
      String number=request.getParameter("idNumber");
      String password=request.getParameter("password");
      String rePassword=request.getParameter("rePassword");
      testBean.setRegisterId(id);
      testBean.setRegisterName(name);
      testBean.setRegisterIdNumber(number);
      testBean.setRegisterPassword(password);
      String verifyCode = testBean.getVerifyCode();
      String vercode = request.getParameter("vercode");
      if(!vercode.equalsIgnoreCase(verifyCode)) {
    	  testBean.setRegisterBackNews("��֤�����");
     	  RequestDispatcher dispatcher = request.getRequestDispatcher("Register.jsp");
     	  dispatcher.forward(request, response);
          return;
      }
      if(id==null||id.length()==0) {
     	 testBean.setRegisterBackNews("������ѧ��");
     	 RequestDispatcher dispatcher = request.getRequestDispatcher("Register.jsp");
     	 dispatcher.forward(request, response);
         return;
      } else if(name==null||name.length()==0) {
      	 testBean.setRegisterBackNews("����������");
      	 RequestDispatcher dispatcher = request.getRequestDispatcher("Register.jsp");
      	 dispatcher.forward(request, response);
         return;
      } else if(number==null||number.length()==0) {
       	 testBean.setRegisterBackNews("���������֤��");
       	 RequestDispatcher dispatcher = request.getRequestDispatcher("Register.jsp");
       	 dispatcher.forward(request, response);
         return;
      } else if(password==null||password.length()==0) {
         testBean.setRegisterBackNews("����������");
         RequestDispatcher dispatcher = request.getRequestDispatcher("Register.jsp");
         dispatcher.forward(request, response);
         return;
      } else if(rePassword==null||rePassword.length()==0) {
       	 testBean.setRegisterBackNews("��ȷ������");
       	 RequestDispatcher dispatcher = request.getRequestDispatcher("Register.jsp");
       	 dispatcher.forward(request, response);
         return;
      } else if(!password.equals(rePassword)) {
       	 testBean.setRegisterBackNews("�����������벻һ��");
       	 RequestDispatcher dispatcher = request.getRequestDispatcher("Register.jsp");
       	 dispatcher.forward(request, response);
         return;
      } else {
    	  String encodingPassword = Encoding.md5(password);
	      String condition = "INSERT INTO register VALUES ("+"'"+id+"','"+name+"','"+number+"','"+encodingPassword+"','0')";
	      Connection con;
	      Statement sql, sql2; 
	      ResultSet rs, rs2;
	      try { 
	          String uri="jdbc:mysql://127.0.0.1/licence?"+"user=root&password=&characterEncoding=gb2312";
	          con=DriverManager.getConnection(uri);
	          sql=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
	          sql2=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
	          rs = sql2.executeQuery("SELECT * FROM student WHERE id = " + id);	          
	          if(rs.next()) {
	        	  testBean.setRegisterBackNews("ע��ʧ�ܣ������ظ�ע��");
	              //jFrame.dispose();
		          RequestDispatcher dispatcher = request.getRequestDispatcher("Register.jsp");
		          dispatcher.forward(request, response);
		          return;
	          } else {
	        	  rs2 = sql2.executeQuery("SELECT * FROM register WHERE id = " + id);
	        	  if(rs2.next()) {
		        	  testBean.setRegisterBackNews("ע��ʧ�ܣ���Ϣ�����");
		              //jFrame.dispose();
			          RequestDispatcher dispatcher = request.getRequestDispatcher("Register.jsp");
			          dispatcher.forward(request, response);
			          return;
		          }
	          }
	          sql.executeUpdate(condition);
	      }
	      catch(SQLException e){
	          System.out.println("Register "+e);
	          testBean.setRegisterBackNews("ע��ʧ��");
	          //jFrame.dispose();
	          RequestDispatcher dispatcher = request.getRequestDispatcher("Register.jsp");
	          dispatcher.forward(request, response);
	          return;
	      }
	      testBean.setRegisterBackNews("ע��ɹ�����ȴ���ʦ���");
	      //jFrame.dispose();
	      RequestDispatcher dispatcher = request.getRequestDispatcher("Register.jsp");
	      dispatcher.forward(request, response);
	      return;
      }
   }
   public  void  doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
       doPost(request,response);
   }
   public void fail(HttpServletRequest request,HttpServletResponse response,String backNews) {
        response.setContentType("text/html;charset=GB2312");
        try {
            PrintWriter out=response.getWriter();
            out.println("<html><body>");
            out.println("<h2>"+backNews+"</h2>") ;
            out.println("<a href =Register.jsp>��������</a>");
            out.println("</body></html>");
        }
        catch(IOException exp)  {  } 
   }
   public void notify(HttpServletRequest request,HttpServletResponse response,String backNews) {
       response.setContentType("text/html;charset=GB2312");
       try {
           PrintWriter out=response.getWriter();
           out.println("<html><body>");
           out.println("<h2 align=center>"+backNews);
           out.println("<a href =Enter.jsp>����</a></h2>");
           out.println("</body></html>");
       }
       catch(IOException exp)  {  } 
  }
}