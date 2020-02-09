package myservlet.control;

import mybean.data.Test_Bean; //引入Javabean模型 
import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JLabel;
import org.omg.CORBA.RepositoryIdHelper;

public class Begin_Servlet extends HttpServlet{
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
	  
	  response.setContentType("text/html;charset=GB2312");
	  try {
		  PrintWriter out = response.getWriter();
		  out.println("<html><body>");
		  out.println("正在加载，请稍后");
		  out.println("</body></html>");
	  } catch (IOException e) {
		  System.out.println("ChapterPractiseServlet   "+e);
	  }
	  */
	  Test_Bean testBean=null;
      HttpSession session=request.getSession(true);
      try{
        testBean=(Test_Bean)session.getAttribute("testBean");
        if(testBean==null){
           testBean=new Test_Bean();            //创建Javabean对象
           session.setAttribute("testBean",testBean);
        }
      }
      catch(Exception exp){
        testBean=new Test_Bean();              //创建Javabean对象
        session.setAttribute("testBean",testBean);
      } 
      try{
        Class.forName("com.mysql.jdbc.Driver");
      }
      catch(Exception e){} 
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
    	 //jFrame.dispose();
    	 testBean.setBackNews("请输入学号和密码");
    	 RequestDispatcher dispatcher = request.getRequestDispatcher("Index.jsp");
    	 dispatcher.forward(request, response);
         //notify(request,response,"请输入学号和密码");
         return;
      }
      String password = request.getParameter("password");
      String encodingPassword = Encoding.md5(password);
      
      try {
      	testBean.setId(id);       
      	Connection con;
      	Statement sql; 
      	ResultSet rsID, rsRegister, rsAll;
      	
        String uri="jdbc:mysql://127.0.0.1/licence?"+"user=root&password=&characterEncoding=gb2312";
        con=DriverManager.getConnection(uri);
        sql=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
              
        rsRegister = sql.executeQuery("SELECT * FROM register WHERE id='"+id+"'");
        if(rsRegister.next()) {
        	if(rsRegister.getString(5).equals("0")) {
        		//jFrame.dispose();
        		testBean.setBackNews("注册信息待审核");
        		RequestDispatcher dispatcher = request.getRequestDispatcher("Index.jsp");
           	 	dispatcher.forward(request, response);
        	} else {
        		//jFrame.dispose();
        		testBean.setBackNews("已被拒绝注册");
        		RequestDispatcher dispatcher = request.getRequestDispatcher("Index.jsp");
           	 	dispatcher.forward(request, response);
        	}
        } else {
          rsID = sql.executeQuery("SELECT * FROM student WHERE id='"+id+"'");
          if(rsID.next()) {	//有此id，判断password
        	String correctPassword=rsID.getString("password");
        	testBean.setName(rsID.getString("name"));
        	
        	if(encodingPassword.equals(correctPassword)==false) {	//password错误
        		//jFrame.dispose();
        		testBean.setBackNews("学号或密码错误");
        		RequestDispatcher dispatcher = request.getRequestDispatcher("Index.jsp");
           	 	dispatcher.forward(request, response);
        		//notify(request,response,"学号或密码错误");
                return;
        	} else {	//通过      		
        		testBean.setIsStudent(true);
        		testBean.setIsTeacher(false);
        		System.out.println(id+" login");
        		
        		float lastScoreKemu1=rsID.getFloat("scoreKemu1");
        		testBean.setLastScroeKemu1(lastScoreKemu1); 
        		float lastScoreKemu4=rsID.getFloat("scoreKemu4");
        		testBean.setLastScoreKemu4(lastScoreKemu4);
        		
        		int kemu1Chapter1 = rsID.getInt("kemu1Chapter1");
        		testBean.setKemu1Chapter1(kemu1Chapter1);
        		int kemu1Chapter2 = rsID.getInt("kemu1Chapter2");
        		testBean.setKemu1Chapter2(kemu1Chapter2);
        		int kemu1Chapter3 = rsID.getInt("kemu1Chapter3");
        		testBean.setKemu1Chapter3(kemu1Chapter3);
        		int kemu1Chapter4 = rsID.getInt("kemu1Chapter4");
        		testBean.setKemu1Chapter4(kemu1Chapter4);
        		
        		int kemu4Chapter1 = rsID.getInt("kemu4Chapter1");
        		testBean.setKemu4Chapter1(kemu4Chapter1);
        		int kemu4Chapter2 = rsID.getInt("kemu4Chapter2");
        		testBean.setKemu4Chapter2(kemu4Chapter2);
        		int kemu4Chapter3 = rsID.getInt("kemu4Chapter3");
        		testBean.setKemu4Chapter3(kemu4Chapter3);
        		int kemu4Chapter4 = rsID.getInt("kemu4Chapter4");
        		testBean.setKemu4Chapter4(kemu4Chapter4);
        		int kemu4Chapter5 = rsID.getInt("kemu4Chapter5");
        		testBean.setKemu4Chapter5(kemu4Chapter5);
        		int kemu4Chapter6 = rsID.getInt("kemu4Chapter6");
        		testBean.setKemu4Chapter6(kemu4Chapter6);
        		int kemu4Chapter7 = rsID.getInt("kemu4Chapter7");
        		testBean.setKemu4Chapter7(kemu4Chapter7);
        		
                rsAll = sql.executeQuery("SELECT * FROM chapter1");
                rsAll.last();
                int kemu1Chapter1All = rsAll.getRow();
                testBean.setKemu1Chapter1All(kemu1Chapter1All);
                rsAll = sql.executeQuery("SELECT * FROM chapter2");
                rsAll.last();
                int kemu1Chapter2All = rsAll.getRow();
                testBean.setKemu1Chapter2All(kemu1Chapter2All);
                rsAll = sql.executeQuery("SELECT * FROM chapter3");
                rsAll.last();
                int kemu1Chapter3All = rsAll.getRow();
                testBean.setKemu1Chapter3All(kemu1Chapter3All);
                rsAll = sql.executeQuery("SELECT * FROM chapter4");
                rsAll.last();
                int kemu1Chapter4All = rsAll.getRow();
                testBean.setKemu1Chapter4All(kemu1Chapter4All);
                
                rsAll = sql.executeQuery("SELECT * FROM chapter11");
                rsAll.last();
                int kemu4Chapter1All = rsAll.getRow();
                testBean.setKemu4Chapter1All(kemu4Chapter1All);
                rsAll = sql.executeQuery("SELECT * FROM chapter12");
                rsAll.last();
                int kemu4Chapter2All = rsAll.getRow();
                testBean.setKemu4Chapter2All(kemu4Chapter2All);
                rsAll = sql.executeQuery("SELECT * FROM chapter13");
                rsAll.last();
                int kemu4Chapter3All = rsAll.getRow();
                testBean.setKemu4Chapter3All(kemu4Chapter3All);
                rsAll = sql.executeQuery("SELECT * FROM chapter14");
                rsAll.last();
                int kemu4Chapter4All = rsAll.getRow();
                testBean.setKemu4Chapter4All(kemu4Chapter4All);
                rsAll = sql.executeQuery("SELECT * FROM chapter15");
                rsAll.last();
                int kemu4Chapter5All = rsAll.getRow();
                testBean.setKemu4Chapter5All(kemu4Chapter5All);
                rsAll = sql.executeQuery("SELECT * FROM chapter16");
                rsAll.last();
                int kemu4Chapter6All = rsAll.getRow();
                testBean.setKemu4Chapter6All(kemu4Chapter6All);
                rsAll = sql.executeQuery("SELECT * FROM chapter17");
                rsAll.last();
                int kemu4Chapter7All = rsAll.getRow();
                testBean.setKemu4Chapter7All(kemu4Chapter7All);

        	/*	if(lastScore!=-1) {	//已有分数
        			notify2(request,response,"注意：每位考生只能参加一次考试",id);
                    return;
        		}
        	*/
        	}
          } else {	//无此id
        	testBean.setBackNews("用户不存在");
        	RequestDispatcher dispatcher = request.getRequestDispatcher("Index.jsp");
       	 	dispatcher.forward(request, response);
        	//notify(request,response,"用户不存在");
            return;
		  }
        }
        testBean.setBackNews("");
        response.sendRedirect("Kemu1.jsp");
        int ta = testBean.getTestAmount();       
      }
      catch (Exception e) {
    	  System.out.println(e);
	  }      
      
      //jFrame.dispose();
   }
   public  void  doGet(HttpServletRequest request,HttpServletResponse response)
           throws ServletException,IOException{
       doPost(request,response);
   }
/*   public void notify(HttpServletRequest request,HttpServletResponse response,String backNews) {
        response.setContentType("text/html;charset=GB2312");
        try {
           PrintWriter out=response.getWriter();
           out.println("<html><body>");
           out.println("<h2 align=center>"+backNews);
           out.println("<a href =Enter.jsp>返回</a></h2>");
           out.println("</body></html>");
        }
        catch(IOException exp){} 
    }
*/
	public void notify2(HttpServletRequest request,HttpServletResponse response,String backNews,String id) {
       response.setContentType("text/html;charset=GB2312");
       try {
          PrintWriter out=response.getWriter();
          out.println("<html><body>");
          out.println("<h2 align=center>"+backNews);
          out.println("<a href =Enter.jsp>返回</a></h2>");
          out.println("<h2 align=center>学号"+id+"</h2>");
          out.println("<Form action=\"studentCheckServlet?dataBase=licence?tableName=student\" method=\"post\" >");
          out.println("<input type=hidden value= "+id+" name =\"studentID\">");
          out.println("<h2 align=center><input type=submit name=\"sub\" value=\"查看成绩\"></h2>");
          out.println("</Form>");
          out.println("</body></html>");
       }
       catch(IOException exp){
    	   System.out.println("error");
       } 
   }
}
    
