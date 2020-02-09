package myservlet.control;

import mybean.data.Test_Bean; //引入Javabean模型

import java.awt.Container;
import java.awt.Font;
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

import java.util.*;

public class Teacher_Begin_Servlet extends HttpServlet{
   public void init(ServletConfig config) throws ServletException{
      super.init(config);
   }
   public void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
	  /*
	  JFrame jFrame = new JFrame();
	  jFrame.setSize(400, 100);
	  Toolkit kit = Toolkit.getDefaultToolkit();             //定义工具包
	  Dimension screenSize = kit.getScreenSize();            //获取屏幕的尺寸
	  int screenWidth = screenSize.width;                    //获取屏幕的宽
	  int screenHeight = screenSize.height;                  //获取屏幕的高
	  jFrame.setLocation(screenWidth/2-200, screenHeight/2-50);
	  jFrame.setVisible(true);
	  Container container = jFrame.getContentPane();
	  JLabel jLabel = new JLabel("正在加载，请稍后");
	  jLabel.setFont(new Font("",1,30));
	  container.add(jLabel);
	  */
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
      
      String verifyCode = testBean.getVerifyCode();
      String vercode = request.getParameter("vercode");
      if(!vercode.equalsIgnoreCase(verifyCode)) {
    	  testBean.setBackNews("验证码错误");
     	  RequestDispatcher dispatcher = request.getRequestDispatcher("Index.jsp");
     	  dispatcher.forward(request, response);
          return;
      }
      
      String id=request.getParameter("id");
      if(id==null||id.length()==0) {
    	  testBean.setBackNews("请输入编号和密码");
     	  RequestDispatcher dispatcher = request.getRequestDispatcher("Index.jsp");
     	  dispatcher.forward(request, response);
          notify(request,response,"请输入编号和密码");
          return;
      }
      String password = request.getParameter("password");
      String encodingPassword = Encoding.md5(password);
      
      try {
      	testBean.setId(id);       
      	Connection con;
      	Statement sql; 
      	ResultSet rs, rs1, rs2, rs3, rsID;
      	
        String uri="jdbc:mysql://127.0.0.1/licence?user=root&password=&characterEncoding=gb2312";
        con=DriverManager.getConnection(uri);
        sql=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
              
        //判断是否有资格
        rsID= sql.executeQuery("SELECT * FROM teacher WHERE id='"+id+"'");
        if(rsID.next()) {	//有此id，判断password
        	String correctPassword=rsID.getString("password");
        	testBean.setName(rsID.getString("name"));
        	if(encodingPassword.equals(correctPassword)==false) {	//password错误
        		testBean.setBackNews("编号或密码错误");
        		RequestDispatcher dispatcher = request.getRequestDispatcher("Index.jsp");
           	 	dispatcher.forward(request, response);
                return;
        	} else {
        		testBean.setIsTeacher(true);
        		testBean.setIsStudent(false);
        	}
        }
        else {	//无此id
        	testBean.setBackNews("用户不存在");
        	RequestDispatcher dispatcher = request.getRequestDispatcher("Index.jsp");
       	 	dispatcher.forward(request, response);
            return;
		}
        testBean.setBackNews("");
        response.sendRedirect("CheckRegister.jsp");
      } catch (Exception e) {
    	  notify(request,response,e.toString());
	  }
      
      //jFrame.dispose();
   }
   public  void  doGet(HttpServletRequest request,HttpServletResponse response)
           throws ServletException,IOException{
       doPost(request,response);
   }
   public void notify(HttpServletRequest request,HttpServletResponse response,String backNews) {
        response.setContentType("text/html;charset=GB2312");
        try {
           PrintWriter out=response.getWriter();
           out.println("<html><body>");
           out.println("<h2>"+backNews+"</h2>") ;
           out.println("<a href =Enter.jsp>返回</a>");
           out.println("</body></html>");
        } catch(IOException exp){} 
    }
}
        
