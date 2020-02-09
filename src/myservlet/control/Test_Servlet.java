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

public class Test_Servlet extends HttpServlet{
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
      String id=request.getParameter("id");
      testBean.setId(id); 
      //String password=request.getParameter("password");
      //if(id.length()==0 || password.length()==0) response.sendRedirect("Index.jsp");
      
      //如果要做完题后停止计时，删除下面内容
      String timeCounterMin = request.getParameter("timeCounterMin");
      int min,sec;
      try {
    	  min = Integer.parseInt(timeCounterMin);
	  } catch (Exception e) {
		  min=0;
	  }      
      String timeCounterSec = request.getParameter("timeCounterSec");
      try {
    	  sec = Integer.parseInt(timeCounterSec);
      } catch (Exception e) {
    	  sec=0;
      }
      if(min!=0||sec!=0) {
    	  testBean.setTimeCounterMin(min);
    	  testBean.setTimeCounterSec(sec);
      }      
      
      try {      
      	Connection con;
      	Statement sql, sql2; 
      	ResultSet rs, rs1E, rs2E, rs3E, rs4E, rs5E, rs6E, rs7E, rs1N, rs2N, rs3N, rs4N, rs5N, rs6N, rs7N, rs1H, rs2H, rs3H, rs4H, rs5H, rs6H, rs7H, rsID;
      	
        String uri="jdbc:mysql://127.0.0.1/licence?"+"user=root&password=&characterEncoding=gb2312";
        con=DriverManager.getConnection(uri);
        sql=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
        sql2=con.createStatement();

        String kemu = request.getParameter("kemu");
        testBean.setKemu(kemu);
        
        //rs=sql.executeQuery("SELECT * FROM test"); //提取所有题
        //if(rs.next()==false) {	//如果无题
        if(testBean.getAllQuestions()==null||testBean.getAllQuestions().length==0) {
          int notBusyTry;
          for(notBusyTry=0; notBusyTry<16; notBusyTry++) {	//尝试16次
        	try {
        		int sleepTime = notBusyTry*100+(int) Math.pow(2, (int)(notBusyTry*Math.random()));
        		System.out.println(id+"  test  "+kemu+"  "+notBusyTry+"  "+sleepTime);
        		Thread.sleep(sleepTime); 
            } catch(InterruptedException ex) {
            	System.out.println("sleepError");
            } 
            rs=sql.executeQuery("SELECT * FROM test"); 
            if(rs.next()==false) {	//如果上一个人已经抽完题删除了
              sql.executeUpdate("INSERT INTO test VALUES('0','0','0','0','0','0','0','0','0','0')");
	          if(kemu.equals("1")){
	        	int kemu1Chapter1All = testBean.getKemu1Chapter1All();
	        	int kemu1Chapter2All = testBean.getKemu1Chapter2All();
	        	int kemu1Chapter3All = testBean.getKemu1Chapter3All();
	        	int kemu1Chapter4All = testBean.getKemu1Chapter4All();
	        	int kemu1All = kemu1Chapter1All + kemu1Chapter2All + kemu1Chapter3All + kemu1Chapter4All;
	        	int kemu1Chapter1TestAmount = (int)(100.0/kemu1All*kemu1Chapter1All+0.5);
	        	int kemu1Chapter2TestAmount = (int)(100.0/kemu1All*kemu1Chapter2All+0.5);
	        	int kemu1Chapter3TestAmount = (int)(100.0/kemu1All*kemu1Chapter3All+0.5);
	        	int kemu1Chapter4TestAmount = 100 - kemu1Chapter1TestAmount - kemu1Chapter2TestAmount - kemu1Chapter3TestAmount;
	        	int kemu1Chapter1TestEasyAmount = (int) (kemu1Chapter1TestAmount/3.0+0.5);
	        	int kemu1Chapter1TestHardAmount = (int) (kemu1Chapter1TestAmount/3.0+0.5);
	        	int kemu1Chapter1TestNormalAmount = kemu1Chapter1TestAmount - kemu1Chapter1TestEasyAmount - kemu1Chapter1TestHardAmount;
	        	int kemu1Chapter2TestEasyAmount = (int) (kemu1Chapter2TestAmount/3.0+0.5);
	        	int kemu1Chapter2TestHardAmount = (int) (kemu1Chapter2TestAmount/3.0+0.5);
	        	int kemu1Chapter2TestNormalAmount = kemu1Chapter2TestAmount - kemu1Chapter2TestEasyAmount - kemu1Chapter2TestHardAmount;
	        	int kemu1Chapter3TestEasyAmount = (int) (kemu1Chapter3TestAmount/3.0+0.5);
	        	int kemu1Chapter3TestHardAmount = (int) (kemu1Chapter3TestAmount/3.0+0.5);
	        	int kemu1Chapter3TestNormalAmount = kemu1Chapter3TestAmount - kemu1Chapter3TestEasyAmount - kemu1Chapter3TestHardAmount;      	
	        	int kemu1Chapter4TestEasyAmount = (int) (kemu1Chapter4TestAmount/3.0+0.5);
	        	int kemu1Chapter4TestHardAmount = (int) (kemu1Chapter4TestAmount/3.0+0.5);
	        	int kemu1Chapter4TestNormalAmount = kemu1Chapter4TestAmount - kemu1Chapter4TestEasyAmount - kemu1Chapter4TestHardAmount;      	
	        	int m0=-1, index0=-1;
	        	
	        	//chapter1
	          	ArrayList<String> as1E1=new ArrayList<String>();
	          	ArrayList<String> as1E2=new ArrayList<String>();
	          	ArrayList<String> as1E3=new ArrayList<String>();
	          	ArrayList<String> as1E4=new ArrayList<String>();
	          	ArrayList<String> as1E5=new ArrayList<String>();
	          	ArrayList<String> as1E6=new ArrayList<String>();
	          	ArrayList<String> as1E7=new ArrayList<String>();
	          	ArrayList<String> as1E8=new ArrayList<String>();
	          	ArrayList<String> as1E9=new ArrayList<String>();
	          	ArrayList<String> as1EA=new ArrayList<String>();          	
	          	rs1E = sql.executeQuery("SELECT * FROM chapter1 WHERE difficulty = 1");
	          	rs1E.last();
	          	int kemu1Chapter1TotalEasyAmount = rs1E.getRow();          	
	          	LinkedList<Integer> kemu1Chapter1EasyList = new LinkedList<Integer>();
	            for(int i=1; i<=kemu1Chapter1TotalEasyAmount; i++) {
	            	kemu1Chapter1EasyList.add(i);
	            }
	            for(int i=1; i<=kemu1Chapter1TestEasyAmount; i++){
	            	m0 = (int)(Math.random()*kemu1Chapter1EasyList.size());
	            	index0 = kemu1Chapter1EasyList.get(m0);
	            	kemu1Chapter1EasyList.remove(m0);
	            	rs1E.absolute(index0);
	            	as1E1.add(rs1E.getString(1).trim());//题号
	            	as1E2.add(rs1E.getString(2).trim());//题目
	            	as1E3.add(rs1E.getString(3).trim());//a
	            	as1E4.add(rs1E.getString(4).trim());//b
	            	as1E5.add(rs1E.getString(5).trim());//c
	            	as1E6.add(rs1E.getString(6).trim());//d
	            	as1E7.add(rs1E.getString(7).trim());//pic
	            	as1E8.add(rs1E.getString(8).trim());//答案
	            	as1E9.add(rs1E.getString(9).trim());//difficulty
	            	as1EA.add(rs1E.getString(10).trim());//type
	            }
	            for (int i = 0; i < as1E1.size(); i++) {
	            	String execute = "INSERT INTO test VALUES('";
	            	execute+=as1E1.get(i)+"','";
	            	execute+=as1E2.get(i)+"','";
	            	execute+=as1E3.get(i)+"','";
	            	execute+=as1E4.get(i)+"','";
	            	execute+=as1E5.get(i)+"','";
	            	execute+=as1E6.get(i)+"','";
	            	execute+=as1E7.get(i)+"','";
	            	execute+=as1E8.get(i)+"','";
	            	execute+=as1E9.get(i)+"','";
	            	execute+=as1EA.get(i)+"')";
	            	sql.executeUpdate(execute);
	            }
	            
	            ArrayList<String> as1N1=new ArrayList<String>();
	          	ArrayList<String> as1N2=new ArrayList<String>();
	          	ArrayList<String> as1N3=new ArrayList<String>();
	          	ArrayList<String> as1N4=new ArrayList<String>();
	          	ArrayList<String> as1N5=new ArrayList<String>();
	          	ArrayList<String> as1N6=new ArrayList<String>();
	          	ArrayList<String> as1N7=new ArrayList<String>();
	          	ArrayList<String> as1N8=new ArrayList<String>();
	          	ArrayList<String> as1N9=new ArrayList<String>();
	          	ArrayList<String> as1NA=new ArrayList<String>();          	
	          	rs1N = sql.executeQuery("SELECT * FROM chapter1 WHERE difficulty = 2");
	          	rs1N.last();
	          	int kemu1Chapter1TotalNormalAmount = rs1N.getRow();          	
	          	LinkedList<Integer> kemu1Chapter1NormalList = new LinkedList<Integer>();
	            for(int i=1; i<=kemu1Chapter1TotalNormalAmount; i++) {
	            	kemu1Chapter1NormalList.add(i);
	            }
	            for(int i=1; i<=kemu1Chapter1TestNormalAmount; i++){
	            	m0 = (int)(Math.random()*kemu1Chapter1NormalList.size());
	            	index0 = kemu1Chapter1NormalList.get(m0);
	            	kemu1Chapter1NormalList.remove(m0);
	            	rs1N.absolute(index0);
	            	as1N1.add(rs1N.getString(1).trim());//题号
	            	as1N2.add(rs1N.getString(2).trim());//题目
	            	as1N3.add(rs1N.getString(3).trim());//a
	            	as1N4.add(rs1N.getString(4).trim());//b
	            	as1N5.add(rs1N.getString(5).trim());//c
	            	as1N6.add(rs1N.getString(6).trim());//d
	            	as1N7.add(rs1N.getString(7).trim());//pic
	            	as1N8.add(rs1N.getString(8).trim());//答案
	            	as1N9.add(rs1N.getString(9).trim());//difficulty
	            	as1NA.add(rs1N.getString(10).trim());//type
	            }
	            for (int i = 0; i < as1N1.size(); i++) {
	            	String execute = "INSERT INTO test VALUES('";
	            	execute+=as1N1.get(i)+"','";
	            	execute+=as1N2.get(i)+"','";
	            	execute+=as1N3.get(i)+"','";
	            	execute+=as1N4.get(i)+"','";
	            	execute+=as1N5.get(i)+"','";
	            	execute+=as1N6.get(i)+"','";
	            	execute+=as1N7.get(i)+"','";
	            	execute+=as1N8.get(i)+"','";
	            	execute+=as1N9.get(i)+"','";
	            	execute+=as1NA.get(i)+"')";
	            	sql.executeUpdate(execute);
	            }
	            
	            ArrayList<String> as1H1=new ArrayList<String>();
	          	ArrayList<String> as1H2=new ArrayList<String>();
	          	ArrayList<String> as1H3=new ArrayList<String>();
	          	ArrayList<String> as1H4=new ArrayList<String>();
	          	ArrayList<String> as1H5=new ArrayList<String>();
	          	ArrayList<String> as1H6=new ArrayList<String>();
	          	ArrayList<String> as1H7=new ArrayList<String>();
	          	ArrayList<String> as1H8=new ArrayList<String>();
	          	ArrayList<String> as1H9=new ArrayList<String>();
	          	ArrayList<String> as1HA=new ArrayList<String>();          	
	          	rs1H = sql.executeQuery("SELECT * FROM chapter1 WHERE difficulty = 3");
	          	rs1H.last();
	          	int kemu1Chapter1TotalHardAmount = rs1H.getRow();          	
	          	LinkedList<Integer> kemu1Chapter1HardList = new LinkedList<Integer>();
	            for(int i=1; i<=kemu1Chapter1TotalHardAmount; i++) {
	            	kemu1Chapter1HardList.add(i);
	            }
	            for(int i=1; i<=kemu1Chapter1TestHardAmount; i++){
	            	m0 = (int)(Math.random()*kemu1Chapter1HardList.size());
	            	index0 = kemu1Chapter1HardList.get(m0);
	            	kemu1Chapter1HardList.remove(m0);
	            	rs1H.absolute(index0);
	            	as1H1.add(rs1H.getString(1).trim());//题号
	            	as1H2.add(rs1H.getString(2).trim());//题目
	            	as1H3.add(rs1H.getString(3).trim());//a
	            	as1H4.add(rs1H.getString(4).trim());//b
	            	as1H5.add(rs1H.getString(5).trim());//c
	            	as1H6.add(rs1H.getString(6).trim());//d
	            	as1H7.add(rs1H.getString(7).trim());//pic
	            	as1H8.add(rs1H.getString(8).trim());//答案
	            	as1H9.add(rs1H.getString(9).trim());//difficulty
	            	as1HA.add(rs1H.getString(10).trim());//type
	            }
	            for (int i = 0; i < as1H1.size(); i++) {
	            	String execute = "INSERT INTO test VALUES('";
	            	execute+=as1H1.get(i)+"','";
	            	execute+=as1H2.get(i)+"','";
	            	execute+=as1H3.get(i)+"','";
	            	execute+=as1H4.get(i)+"','";
	            	execute+=as1H5.get(i)+"','";
	            	execute+=as1H6.get(i)+"','";
	            	execute+=as1H7.get(i)+"','";
	            	execute+=as1H8.get(i)+"','";
	            	execute+=as1H9.get(i)+"','";
	            	execute+=as1HA.get(i)+"')";
	            	sql.executeUpdate(execute);
	            }
	            
	            //chapter2
	          	ArrayList<String> as2E1=new ArrayList<String>();
	          	ArrayList<String> as2E2=new ArrayList<String>();
	          	ArrayList<String> as2E3=new ArrayList<String>();
	          	ArrayList<String> as2E4=new ArrayList<String>();
	          	ArrayList<String> as2E5=new ArrayList<String>();
	          	ArrayList<String> as2E6=new ArrayList<String>();
	          	ArrayList<String> as2E7=new ArrayList<String>();
	          	ArrayList<String> as2E8=new ArrayList<String>();
	          	ArrayList<String> as2E9=new ArrayList<String>();
	          	ArrayList<String> as2EA=new ArrayList<String>();          	
	          	rs2E = sql.executeQuery("SELECT * FROM chapter2 WHERE difficulty = 1");
	          	rs2E.last();
	          	int kemu1Chapter2TotalEasyAmount = rs2E.getRow();          	
	          	LinkedList<Integer> kemu1Chapter2EasyList = new LinkedList<Integer>();
	            for(int i=1; i<=kemu1Chapter2TotalEasyAmount; i++) {
	            	kemu1Chapter2EasyList.add(i);
	            }
	            for(int i=1; i<=kemu1Chapter2TestEasyAmount; i++){
	            	m0 = (int)(Math.random()*kemu1Chapter2EasyList.size());
	            	index0 = kemu1Chapter2EasyList.get(m0);
	            	kemu1Chapter2EasyList.remove(m0);
	            	rs2E.absolute(index0);
	            	as2E1.add(rs2E.getString(1).trim());//题号
	            	as2E2.add(rs2E.getString(2).trim());//题目
	            	as2E3.add(rs2E.getString(3).trim());//a
	            	as2E4.add(rs2E.getString(4).trim());//b
	            	as2E5.add(rs2E.getString(5).trim());//c
	            	as2E6.add(rs2E.getString(6).trim());//d
	            	as2E7.add(rs2E.getString(7).trim());//pic
	            	as2E8.add(rs2E.getString(8).trim());//答案
	            	as2E9.add(rs2E.getString(9).trim());//difficulty
	            	as2EA.add(rs2E.getString(10).trim());//type
	            }
	            for (int i = 0; i < as2E1.size(); i++) {
	            	String execute = "INSERT INTO test VALUES('";
	            	execute+=as2E1.get(i)+"','";
	            	execute+=as2E2.get(i)+"','";
	            	execute+=as2E3.get(i)+"','";
	            	execute+=as2E4.get(i)+"','";
	            	execute+=as2E5.get(i)+"','";
	            	execute+=as2E6.get(i)+"','";
	            	execute+=as2E7.get(i)+"','";
	            	execute+=as2E8.get(i)+"','";
	            	execute+=as2E9.get(i)+"','";
	            	execute+=as2EA.get(i)+"')";
	            	sql.executeUpdate(execute);
	            }
	            
	            ArrayList<String> as2N1=new ArrayList<String>();
	          	ArrayList<String> as2N2=new ArrayList<String>();
	          	ArrayList<String> as2N3=new ArrayList<String>();
	          	ArrayList<String> as2N4=new ArrayList<String>();
	          	ArrayList<String> as2N5=new ArrayList<String>();
	          	ArrayList<String> as2N6=new ArrayList<String>();
	          	ArrayList<String> as2N7=new ArrayList<String>();
	          	ArrayList<String> as2N8=new ArrayList<String>();
	          	ArrayList<String> as2N9=new ArrayList<String>();
	          	ArrayList<String> as2NA=new ArrayList<String>();          	
	          	rs2N = sql.executeQuery("SELECT * FROM chapter2 WHERE difficulty = 2");
	          	rs2N.last();
	          	int kemu1Chapter2TotalNormalAmount = rs2N.getRow();          	
	          	LinkedList<Integer> kemu1Chapter2NormalList = new LinkedList<Integer>();
	            for(int i=1; i<=kemu1Chapter2TotalNormalAmount; i++) {
	            	kemu1Chapter2NormalList.add(i);
	            }
	            for(int i=1; i<=kemu1Chapter2TestNormalAmount; i++){
	            	m0 = (int)(Math.random()*kemu1Chapter2NormalList.size());
	            	index0 = kemu1Chapter2NormalList.get(m0);
	            	kemu1Chapter2NormalList.remove(m0);
	            	rs2N.absolute(index0);
	            	as2N1.add(rs2N.getString(1).trim());//题号
	            	as2N2.add(rs2N.getString(2).trim());//题目
	            	as2N3.add(rs2N.getString(3).trim());//a
	            	as2N4.add(rs2N.getString(4).trim());//b
	            	as2N5.add(rs2N.getString(5).trim());//c
	            	as2N6.add(rs2N.getString(6).trim());//d
	            	as2N7.add(rs2N.getString(7).trim());//pic
	            	as2N8.add(rs2N.getString(8).trim());//答案
	            	as2N9.add(rs2N.getString(9).trim());//difficulty
	            	as2NA.add(rs2N.getString(10).trim());//type
	            }
	            for (int i = 0; i < as2N1.size(); i++) {
	            	String execute = "INSERT INTO test VALUES('";
	            	execute+=as2N1.get(i)+"','";
	            	execute+=as2N2.get(i)+"','";
	            	execute+=as2N3.get(i)+"','";
	            	execute+=as2N4.get(i)+"','";
	            	execute+=as2N5.get(i)+"','";
	            	execute+=as2N6.get(i)+"','";
	            	execute+=as2N7.get(i)+"','";
	            	execute+=as2N8.get(i)+"','";
	            	execute+=as2N9.get(i)+"','";
	            	execute+=as2NA.get(i)+"')";
	            	sql.executeUpdate(execute);
	            }
	            
	            ArrayList<String> as2H1=new ArrayList<String>();
	          	ArrayList<String> as2H2=new ArrayList<String>();
	          	ArrayList<String> as2H3=new ArrayList<String>();
	          	ArrayList<String> as2H4=new ArrayList<String>();
	          	ArrayList<String> as2H5=new ArrayList<String>();
	          	ArrayList<String> as2H6=new ArrayList<String>();
	          	ArrayList<String> as2H7=new ArrayList<String>();
	          	ArrayList<String> as2H8=new ArrayList<String>();
	          	ArrayList<String> as2H9=new ArrayList<String>();
	          	ArrayList<String> as2HA=new ArrayList<String>();          	
	          	rs2H = sql.executeQuery("SELECT * FROM chapter2 WHERE difficulty = 3");
	          	rs2H.last();
	          	int kemu1Chapter2TotalHardAmount = rs2H.getRow();          	
	          	LinkedList<Integer> kemu1Chapter2HardList = new LinkedList<Integer>();
	            for(int i=1; i<=kemu1Chapter2TotalHardAmount; i++) {
	            	kemu1Chapter2HardList.add(i);
	            }
	            for(int i=1; i<=kemu1Chapter2TestHardAmount; i++){
	            	m0 = (int)(Math.random()*kemu1Chapter2HardList.size());
	            	index0 = kemu1Chapter2HardList.get(m0);
	            	kemu1Chapter2HardList.remove(m0);
	            	rs2H.absolute(index0);
	            	as2H1.add(rs2H.getString(1).trim());//题号
	            	as2H2.add(rs2H.getString(2).trim());//题目
	            	as2H3.add(rs2H.getString(3).trim());//a
	            	as2H4.add(rs2H.getString(4).trim());//b
	            	as2H5.add(rs2H.getString(5).trim());//c
	            	as2H6.add(rs2H.getString(6).trim());//d
	            	as2H7.add(rs2H.getString(7).trim());//pic
	            	as2H8.add(rs2H.getString(8).trim());//答案
	            	as2H9.add(rs2H.getString(9).trim());//difficulty
	            	as2HA.add(rs2H.getString(10).trim());//type
	            }
	            for (int i = 0; i < as2H1.size(); i++) {
	            	String execute = "INSERT INTO test VALUES('";
	            	execute+=as2H1.get(i)+"','";
	            	execute+=as2H2.get(i)+"','";
	            	execute+=as2H3.get(i)+"','";
	            	execute+=as2H4.get(i)+"','";
	            	execute+=as2H5.get(i)+"','";
	            	execute+=as2H6.get(i)+"','";
	            	execute+=as2H7.get(i)+"','";
	            	execute+=as2H8.get(i)+"','";
	            	execute+=as2H9.get(i)+"','";
	            	execute+=as2HA.get(i)+"')";
	            	sql.executeUpdate(execute);
	            }
	            
	        	//chapter3
	          	ArrayList<String> as3E1=new ArrayList<String>();
	          	ArrayList<String> as3E2=new ArrayList<String>();
	          	ArrayList<String> as3E3=new ArrayList<String>();
	          	ArrayList<String> as3E4=new ArrayList<String>();
	          	ArrayList<String> as3E5=new ArrayList<String>();
	          	ArrayList<String> as3E6=new ArrayList<String>();
	          	ArrayList<String> as3E7=new ArrayList<String>();
	          	ArrayList<String> as3E8=new ArrayList<String>();
	          	ArrayList<String> as3E9=new ArrayList<String>();
	          	ArrayList<String> as3EA=new ArrayList<String>();          	
	          	rs3E = sql.executeQuery("SELECT * FROM chapter3 WHERE difficulty = 1");
	          	rs3E.last();
	          	int kemu1Chapter3TotalEasyAmount = rs3E.getRow();          	
	          	LinkedList<Integer> kemu1Chapter3EasyList = new LinkedList<Integer>();
	            for(int i=1; i<=kemu1Chapter3TotalEasyAmount; i++) {
	            	kemu1Chapter3EasyList.add(i);
	            }
	            for(int i=1; i<=kemu1Chapter3TestEasyAmount; i++){
	            	m0 = (int)(Math.random()*kemu1Chapter3EasyList.size());
	            	index0 = kemu1Chapter3EasyList.get(m0);
	            	kemu1Chapter3EasyList.remove(m0);
	            	rs3E.absolute(index0);
	            	as3E1.add(rs3E.getString(1).trim());//题号
	            	as3E2.add(rs3E.getString(2).trim());//题目
	            	as3E3.add(rs3E.getString(3).trim());//a
	            	as3E4.add(rs3E.getString(4).trim());//b
	            	as3E5.add(rs3E.getString(5).trim());//c
	            	as3E6.add(rs3E.getString(6).trim());//d
	            	as3E7.add(rs3E.getString(7).trim());//pic
	            	as3E8.add(rs3E.getString(8).trim());//答案
	            	as3E9.add(rs3E.getString(9).trim());//difficulty
	            	as3EA.add(rs3E.getString(10).trim());//type
	            }
	            for (int i = 0; i < as3E1.size(); i++) {
	            	String execute = "INSERT INTO test VALUES('";
	            	execute+=as3E1.get(i)+"','";
	            	execute+=as3E2.get(i)+"','";
	            	execute+=as3E3.get(i)+"','";
	            	execute+=as3E4.get(i)+"','";
	            	execute+=as3E5.get(i)+"','";
	            	execute+=as3E6.get(i)+"','";
	            	execute+=as3E7.get(i)+"','";
	            	execute+=as3E8.get(i)+"','";
	            	execute+=as3E9.get(i)+"','";
	            	execute+=as3EA.get(i)+"')";
	            	sql.executeUpdate(execute);
	            }
	            
	            ArrayList<String> as3N1=new ArrayList<String>();
	          	ArrayList<String> as3N2=new ArrayList<String>();
	          	ArrayList<String> as3N3=new ArrayList<String>();
	          	ArrayList<String> as3N4=new ArrayList<String>();
	          	ArrayList<String> as3N5=new ArrayList<String>();
	          	ArrayList<String> as3N6=new ArrayList<String>();
	          	ArrayList<String> as3N7=new ArrayList<String>();
	          	ArrayList<String> as3N8=new ArrayList<String>();
	          	ArrayList<String> as3N9=new ArrayList<String>();
	          	ArrayList<String> as3NA=new ArrayList<String>();          	
	          	rs3N = sql.executeQuery("SELECT * FROM chapter3 WHERE difficulty = 2");
	          	rs3N.last();
	          	int kemu1Chapter3TotalNormalAmount = rs3N.getRow();          	
	          	LinkedList<Integer> kemu1Chapter3NormalList = new LinkedList<Integer>();
	            for(int i=1; i<=kemu1Chapter3TotalNormalAmount; i++) {
	            	kemu1Chapter3NormalList.add(i);
	            }
	            for(int i=1; i<=kemu1Chapter3TestNormalAmount; i++){
	            	m0 = (int)(Math.random()*kemu1Chapter3NormalList.size());
	            	index0 = kemu1Chapter3NormalList.get(m0);
	            	kemu1Chapter3NormalList.remove(m0);
	            	rs3N.absolute(index0);
	            	as3N1.add(rs3N.getString(1).trim());//题号
	            	as3N2.add(rs3N.getString(2).trim());//题目
	            	as3N3.add(rs3N.getString(3).trim());//a
	            	as3N4.add(rs3N.getString(4).trim());//b
	            	as3N5.add(rs3N.getString(5).trim());//c
	            	as3N6.add(rs3N.getString(6).trim());//d
	            	as3N7.add(rs3N.getString(7).trim());//pic
	            	as3N8.add(rs3N.getString(8).trim());//答案
	            	as3N9.add(rs3N.getString(9).trim());//difficulty
	            	as3NA.add(rs3N.getString(10).trim());//type
	            }
	            for (int i = 0; i < as3N1.size(); i++) {
	            	String execute = "INSERT INTO test VALUES('";
	            	execute+=as3N1.get(i)+"','";
	            	execute+=as3N2.get(i)+"','";
	            	execute+=as3N3.get(i)+"','";
	            	execute+=as3N4.get(i)+"','";
	            	execute+=as3N5.get(i)+"','";
	            	execute+=as3N6.get(i)+"','";
	            	execute+=as3N7.get(i)+"','";
	            	execute+=as3N8.get(i)+"','";
	            	execute+=as3N9.get(i)+"','";
	            	execute+=as3NA.get(i)+"')";
	            	sql.executeUpdate(execute);
	            }
	            
	            ArrayList<String> as3H1=new ArrayList<String>();
	          	ArrayList<String> as3H2=new ArrayList<String>();
	          	ArrayList<String> as3H3=new ArrayList<String>();
	          	ArrayList<String> as3H4=new ArrayList<String>();
	          	ArrayList<String> as3H5=new ArrayList<String>();
	          	ArrayList<String> as3H6=new ArrayList<String>();
	          	ArrayList<String> as3H7=new ArrayList<String>();
	          	ArrayList<String> as3H8=new ArrayList<String>();
	          	ArrayList<String> as3H9=new ArrayList<String>();
	          	ArrayList<String> as3HA=new ArrayList<String>();          	
	          	rs3H = sql.executeQuery("SELECT * FROM chapter3 WHERE difficulty = 3");
	          	rs3H.last();
	          	int kemu1Chapter3TotalHardAmount = rs3H.getRow();          	
	          	LinkedList<Integer> kemu1Chapter3HardList = new LinkedList<Integer>();
	            for(int i=1; i<=kemu1Chapter3TotalHardAmount; i++) {
	            	kemu1Chapter3HardList.add(i);
	            }
	            for(int i=1; i<=kemu1Chapter3TestHardAmount; i++){
	            	m0 = (int)(Math.random()*kemu1Chapter3HardList.size());
	            	index0 = kemu1Chapter3HardList.get(m0);
	            	kemu1Chapter3HardList.remove(m0);
	            	rs3H.absolute(index0);
	            	as3H1.add(rs3H.getString(1).trim());//题号
	            	as3H2.add(rs3H.getString(2).trim());//题目
	            	as3H3.add(rs3H.getString(3).trim());//a
	            	as3H4.add(rs3H.getString(4).trim());//b
	            	as3H5.add(rs3H.getString(5).trim());//c
	            	as3H6.add(rs3H.getString(6).trim());//d
	            	as3H7.add(rs3H.getString(7).trim());//pic
	            	as3H8.add(rs3H.getString(8).trim());//答案
	            	as3H9.add(rs3H.getString(9).trim());//difficulty
	            	as3HA.add(rs3H.getString(10).trim());//type
	            }
	            for (int i = 0; i < as3H1.size(); i++) {
	            	String execute = "INSERT INTO test VALUES('";
	            	execute+=as3H1.get(i)+"','";
	            	execute+=as3H2.get(i)+"','";
	            	execute+=as3H3.get(i)+"','";
	            	execute+=as3H4.get(i)+"','";
	            	execute+=as3H5.get(i)+"','";
	            	execute+=as3H6.get(i)+"','";
	            	execute+=as3H7.get(i)+"','";
	            	execute+=as3H8.get(i)+"','";
	            	execute+=as3H9.get(i)+"','";
	            	execute+=as3HA.get(i)+"')";
	            	sql.executeUpdate(execute);
	            }
	            
	            //chapter4
	          	ArrayList<String> as4E1=new ArrayList<String>();
	          	ArrayList<String> as4E2=new ArrayList<String>();
	          	ArrayList<String> as4E3=new ArrayList<String>();
	          	ArrayList<String> as4E4=new ArrayList<String>();
	          	ArrayList<String> as4E5=new ArrayList<String>();
	          	ArrayList<String> as4E6=new ArrayList<String>();
	          	ArrayList<String> as4E7=new ArrayList<String>();
	          	ArrayList<String> as4E8=new ArrayList<String>();
	          	ArrayList<String> as4E9=new ArrayList<String>();
	          	ArrayList<String> as4EA=new ArrayList<String>();          	
	          	rs4E = sql.executeQuery("SELECT * FROM chapter4 WHERE difficulty = 1");
	          	rs4E.last();
	          	int kemu1Chapter4TotalEasyAmount = rs4E.getRow();
	          	LinkedList<Integer> kemu1Chapter4EasyList = new LinkedList<Integer>();
	            for(int i=1; i<=kemu1Chapter4TotalEasyAmount; i++) {
	            	kemu1Chapter4EasyList.add(i);
	            }
	            for(int i=1; i<=kemu1Chapter4TestEasyAmount; i++){
	            	m0 = (int)(Math.random()*kemu1Chapter4EasyList.size());
	            	index0 = kemu1Chapter4EasyList.get(m0);
	            	kemu1Chapter4EasyList.remove(m0);
	            	rs4E.absolute(index0);
	            	as4E1.add(rs4E.getString(1).trim());//题号
	            	as4E2.add(rs4E.getString(2).trim());//题目
	            	as4E3.add(rs4E.getString(3).trim());//a
	            	as4E4.add(rs4E.getString(4).trim());//b
	            	as4E5.add(rs4E.getString(5).trim());//c
	            	as4E6.add(rs4E.getString(6).trim());//d
	            	as4E7.add(rs4E.getString(7).trim());//pic
	            	as4E8.add(rs4E.getString(8).trim());//答案
	            	as4E9.add(rs4E.getString(9).trim());//difficulty
	            	as4EA.add(rs4E.getString(10).trim());//type
	            }
	            for (int i = 0; i < as4E1.size(); i++) {
	            	String execute = "INSERT INTO test VALUES('";
	            	execute+=as4E1.get(i)+"','";
	            	execute+=as4E2.get(i)+"','";
	            	execute+=as4E3.get(i)+"','";
	            	execute+=as4E4.get(i)+"','";
	            	execute+=as4E5.get(i)+"','";
	            	execute+=as4E6.get(i)+"','";
	            	execute+=as4E7.get(i)+"','";
	            	execute+=as4E8.get(i)+"','";
	            	execute+=as4E9.get(i)+"','";
	            	execute+=as4EA.get(i)+"')";
	            	sql.executeUpdate(execute);
	            }
	            
	            ArrayList<String> as4N1=new ArrayList<String>();
	          	ArrayList<String> as4N2=new ArrayList<String>();
	          	ArrayList<String> as4N3=new ArrayList<String>();
	          	ArrayList<String> as4N4=new ArrayList<String>();
	          	ArrayList<String> as4N5=new ArrayList<String>();
	          	ArrayList<String> as4N6=new ArrayList<String>();
	          	ArrayList<String> as4N7=new ArrayList<String>();
	          	ArrayList<String> as4N8=new ArrayList<String>();
	          	ArrayList<String> as4N9=new ArrayList<String>();
	          	ArrayList<String> as4NA=new ArrayList<String>();          	
	          	rs4N = sql.executeQuery("SELECT * FROM chapter4 WHERE difficulty = 2");
	          	rs4N.last();
	          	int kemu1Chapter4TotalNormalAmount = rs4N.getRow();          	
	          	LinkedList<Integer> kemu1Chapter4NormalList = new LinkedList<Integer>();
	            for(int i=1; i<=kemu1Chapter4TotalNormalAmount; i++) {
	            	kemu1Chapter4NormalList.add(i);
	            }
	            for(int i=1; i<=kemu1Chapter4TestNormalAmount; i++){
	            	m0 = (int)(Math.random()*kemu1Chapter4NormalList.size());
	            	index0 = kemu1Chapter4NormalList.get(m0);
	            	kemu1Chapter4NormalList.remove(m0);
	            	rs4N.absolute(index0);
	            	as4N1.add(rs4N.getString(1).trim());//题号
	            	as4N2.add(rs4N.getString(2).trim());//题目
	            	as4N3.add(rs4N.getString(3).trim());//a
	            	as4N4.add(rs4N.getString(4).trim());//b
	            	as4N5.add(rs4N.getString(5).trim());//c
	            	as4N6.add(rs4N.getString(6).trim());//d
	            	as4N7.add(rs4N.getString(7).trim());//pic
	            	as4N8.add(rs4N.getString(8).trim());//答案
	            	as4N9.add(rs4N.getString(9).trim());//difficulty
	            	as4NA.add(rs4N.getString(10).trim());//type
	            }
	            for (int i = 0; i < as4N1.size(); i++) {
	            	String execute = "INSERT INTO test VALUES('";
	            	execute+=as4N1.get(i)+"','";
	            	execute+=as4N2.get(i)+"','";
	            	execute+=as4N3.get(i)+"','";
	            	execute+=as4N4.get(i)+"','";
	            	execute+=as4N5.get(i)+"','";
	            	execute+=as4N6.get(i)+"','";
	            	execute+=as4N7.get(i)+"','";
	            	execute+=as4N8.get(i)+"','";
	            	execute+=as4N9.get(i)+"','";
	            	execute+=as4NA.get(i)+"')";
	            	sql.executeUpdate(execute);
	            }
	            
	            ArrayList<String> as4H1=new ArrayList<String>();
	          	ArrayList<String> as4H2=new ArrayList<String>();
	          	ArrayList<String> as4H3=new ArrayList<String>();
	          	ArrayList<String> as4H4=new ArrayList<String>();
	          	ArrayList<String> as4H5=new ArrayList<String>();
	          	ArrayList<String> as4H6=new ArrayList<String>();
	          	ArrayList<String> as4H7=new ArrayList<String>();
	          	ArrayList<String> as4H8=new ArrayList<String>();
	          	ArrayList<String> as4H9=new ArrayList<String>();
	          	ArrayList<String> as4HA=new ArrayList<String>();          	
	          	rs4H = sql.executeQuery("SELECT * FROM chapter4 WHERE difficulty = 3");
	          	rs4H.last();
	          	int kemu1Chapter4TotalHardAmount = rs4H.getRow();          	
	          	LinkedList<Integer> kemu1Chapter4HardList = new LinkedList<Integer>();
	            for(int i=1; i<=kemu1Chapter4TotalHardAmount; i++) {
	            	kemu1Chapter4HardList.add(i);
	            }
	            for(int i=1; i<=kemu1Chapter4TestHardAmount; i++){
	            	m0 = (int)(Math.random()*kemu1Chapter4HardList.size());
	            	index0 = kemu1Chapter4HardList.get(m0);
	            	kemu1Chapter4HardList.remove(m0);
	            	rs4H.absolute(index0);
	            	as4H1.add(rs4H.getString(1).trim());//题号
	            	as4H2.add(rs4H.getString(2).trim());//题目
	            	as4H3.add(rs4H.getString(3).trim());//a
	            	as4H4.add(rs4H.getString(4).trim());//b
	            	as4H5.add(rs4H.getString(5).trim());//c
	            	as4H6.add(rs4H.getString(6).trim());//d
	            	as4H7.add(rs4H.getString(7).trim());//pic
	            	as4H8.add(rs4H.getString(8).trim());//答案
	            	as4H9.add(rs4H.getString(9).trim());//difficulty
	            	as4HA.add(rs4H.getString(10).trim());//type
	            }
	            for (int i = 0; i < as4H1.size(); i++) {
	            	String execute = "INSERT INTO test VALUES('";
	            	execute+=as4H1.get(i)+"','";
	            	execute+=as4H2.get(i)+"','";
	            	execute+=as4H3.get(i)+"','";
	            	execute+=as4H4.get(i)+"','";
	            	execute+=as4H5.get(i)+"','";
	            	execute+=as4H6.get(i)+"','";
	            	execute+=as4H7.get(i)+"','";
	            	execute+=as4H8.get(i)+"','";
	            	execute+=as4H9.get(i)+"','";
	            	execute+=as4HA.get(i)+"')";
	            	sql.executeUpdate(execute);
	            }
	
	            
	          } else {	//kemu4
	        	int kemu4Chapter1All = testBean.getKemu4Chapter1All();
	          	int kemu4Chapter2All = testBean.getKemu4Chapter2All();
	          	int kemu4Chapter3All = testBean.getKemu4Chapter3All();
	          	int kemu4Chapter4All = testBean.getKemu4Chapter4All();
	          	int kemu4Chapter5All = testBean.getKemu4Chapter5All();
	          	int kemu4Chapter6All = testBean.getKemu4Chapter6All();
	          	int kemu4Chapter7All = testBean.getKemu4Chapter7All();
	          	int kemu4All = kemu4Chapter1All + kemu4Chapter2All + kemu4Chapter3All + kemu4Chapter4All + kemu4Chapter5All + kemu4Chapter6All + kemu4Chapter7All;
	          	int kemu4Chapter1TestAmount = (int)(50.0/kemu4All*kemu4Chapter1All+0.5);
	          	int kemu4Chapter2TestAmount = (int)(50.0/kemu4All*kemu4Chapter2All+0.5);
	          	int kemu4Chapter3TestAmount = (int)(50.0/kemu4All*kemu4Chapter3All+0.5);
	          	int kemu4Chapter4TestAmount = (int)(50.0/kemu4All*kemu4Chapter4All+0.5);
	          	int kemu4Chapter5TestAmount = (int)(50.0/kemu4All*kemu4Chapter5All+0.5);
	          	int kemu4Chapter6TestAmount = (int)(50.0/kemu4All*kemu4Chapter6All+0.5);
	          	int kemu4Chapter7TestAmount = 50 - kemu4Chapter1TestAmount - kemu4Chapter2TestAmount - kemu4Chapter3TestAmount - kemu4Chapter4TestAmount - kemu4Chapter5TestAmount - kemu4Chapter6TestAmount;
	          	int kemu4Chapter1TestEasyAmount = (int) (kemu4Chapter1TestAmount/3.0+0.5);
	          	int kemu4Chapter1TestHardAmount = (int) (kemu4Chapter1TestAmount/3.0+0.5);
	          	int kemu4Chapter1TestNormalAmount = kemu4Chapter1TestAmount - kemu4Chapter1TestEasyAmount - kemu4Chapter1TestHardAmount;
	          	int kemu4Chapter2TestEasyAmount = (int) (kemu4Chapter2TestAmount/3.0+0.5);
	          	int kemu4Chapter2TestHardAmount = (int) (kemu4Chapter2TestAmount/3.0+0.5);
	          	int kemu4Chapter2TestNormalAmount = kemu4Chapter2TestAmount - kemu4Chapter2TestEasyAmount - kemu4Chapter2TestHardAmount;
	          	int kemu4Chapter3TestEasyAmount = (int) (kemu4Chapter3TestAmount/3.0+0.5);
	          	int kemu4Chapter3TestHardAmount = (int) (kemu4Chapter3TestAmount/3.0+0.5);
	          	int kemu4Chapter3TestNormalAmount = kemu4Chapter3TestAmount - kemu4Chapter3TestEasyAmount - kemu4Chapter3TestHardAmount;      	
	          	int kemu4Chapter4TestEasyAmount = (int) (kemu4Chapter4TestAmount/3.0+0.5);
	          	int kemu4Chapter4TestHardAmount = (int) (kemu4Chapter4TestAmount/3.0+0.5);
	          	int kemu4Chapter4TestNormalAmount = kemu4Chapter4TestAmount - kemu4Chapter4TestEasyAmount - kemu4Chapter4TestHardAmount;      	
	          	int kemu4Chapter5TestEasyAmount = (int) (kemu4Chapter5TestAmount/3.0+0.5);
	          	int kemu4Chapter5TestHardAmount = (int) (kemu4Chapter5TestAmount/3.0+0.5);
	          	int kemu4Chapter5TestNormalAmount = kemu4Chapter5TestAmount - kemu4Chapter5TestEasyAmount - kemu4Chapter5TestHardAmount;
	          	int kemu4Chapter6TestEasyAmount = (int) (kemu4Chapter6TestAmount/3.0+0.5);
	          	int kemu4Chapter6TestHardAmount = (int) (kemu4Chapter6TestAmount/3.0+0.5);
	          	int kemu4Chapter6TestNormalAmount = kemu4Chapter6TestAmount - kemu4Chapter6TestEasyAmount - kemu4Chapter6TestHardAmount;      	
	          	int kemu4Chapter7TestEasyAmount = (int) (kemu4Chapter7TestAmount/3.0+0.5);
	          	int kemu4Chapter7TestHardAmount = (int) (kemu4Chapter7TestAmount/3.0+0.5);
	          	int kemu4Chapter7TestNormalAmount = kemu4Chapter7TestAmount - kemu4Chapter7TestEasyAmount - kemu4Chapter7TestHardAmount; 
	          	int m0=-1, index0=-1;
	          	
		        //chapter1
		        ArrayList<String> as1E1=new ArrayList<String>();
		        ArrayList<String> as1E2=new ArrayList<String>();
		        ArrayList<String> as1E3=new ArrayList<String>();
		        ArrayList<String> as1E4=new ArrayList<String>();
		        ArrayList<String> as1E5=new ArrayList<String>();
		        ArrayList<String> as1E6=new ArrayList<String>();
		        ArrayList<String> as1E7=new ArrayList<String>();
		        ArrayList<String> as1E8=new ArrayList<String>();
		        ArrayList<String> as1E9=new ArrayList<String>();
		        ArrayList<String> as1EA=new ArrayList<String>();          	
		        rs1E = sql.executeQuery("SELECT * FROM chapter11 WHERE difficulty = 1");
		        rs1E.last();
		        int kemu4Chapter1TotalEasyAmount = rs1E.getRow();          	
		        LinkedList<Integer> kemu4Chapter1EasyList = new LinkedList<Integer>();
	            for(int i=1; i<=kemu4Chapter1TotalEasyAmount; i++) {
	              	kemu4Chapter1EasyList.add(i);
	            }
	            for(int i=1; i<=kemu4Chapter1TestEasyAmount; i++){
	              	m0 = (int)(Math.random()*kemu4Chapter1EasyList.size());
	              	index0 = kemu4Chapter1EasyList.get(m0);
	              	kemu4Chapter1EasyList.remove(m0);
	              	rs1E.absolute(index0);
	              	as1E1.add(rs1E.getString(1).trim());//题号
	              	as1E2.add(rs1E.getString(2).trim());//题目
	              	as1E3.add(rs1E.getString(3).trim());//a
	              	as1E4.add(rs1E.getString(4).trim());//b
	              	as1E5.add(rs1E.getString(5).trim());//c
	              	as1E6.add(rs1E.getString(6).trim());//d
	              	as1E7.add(rs1E.getString(7).trim());//pic
	              	as1E8.add(rs1E.getString(8).trim());//答案
	              	as1E9.add(rs1E.getString(9).trim());//difficulty
	              	as1EA.add(rs1E.getString(10).trim());//type
	            }
	            for (int i = 0; i < as1E1.size(); i++) {
	              	String execute = "INSERT INTO test VALUES('";
	              	execute+=as1E1.get(i)+"','";
	              	execute+=as1E2.get(i)+"','";
	              	execute+=as1E3.get(i)+"','";
	              	execute+=as1E4.get(i)+"','";
	              	execute+=as1E5.get(i)+"','";
	              	execute+=as1E6.get(i)+"','";
	              	execute+=as1E7.get(i)+"','";
	              	execute+=as1E8.get(i)+"','";
	              	execute+=as1E9.get(i)+"','";
	              	execute+=as1EA.get(i)+"')";
	              	sql.executeUpdate(execute);
	            }
	              
	            ArrayList<String> as1N1=new ArrayList<String>();
	            ArrayList<String> as1N2=new ArrayList<String>();
	            ArrayList<String> as1N3=new ArrayList<String>();
	            ArrayList<String> as1N4=new ArrayList<String>();
	            ArrayList<String> as1N5=new ArrayList<String>();
	            ArrayList<String> as1N6=new ArrayList<String>();
	            ArrayList<String> as1N7=new ArrayList<String>();
	            ArrayList<String> as1N8=new ArrayList<String>();
	            ArrayList<String> as1N9=new ArrayList<String>();
	            ArrayList<String> as1NA=new ArrayList<String>();          	
	            rs1N = sql.executeQuery("SELECT * FROM chapter11 WHERE difficulty = 2");
	            rs1N.last();
	            int kemu4Chapter1TotalNormalAmount = rs1N.getRow();          	
	            LinkedList<Integer> kemu4Chapter1NormalList = new LinkedList<Integer>();
	            for(int i=1; i<=kemu4Chapter1TotalNormalAmount; i++) {
	              	kemu4Chapter1NormalList.add(i);
	            }
	            for(int i=1; i<=kemu4Chapter1TestNormalAmount; i++){
	              	m0 = (int)(Math.random()*kemu4Chapter1NormalList.size());
	              	index0 = kemu4Chapter1NormalList.get(m0);
	              	kemu4Chapter1NormalList.remove(m0);
	              	rs1N.absolute(index0);
	              	as1N1.add(rs1N.getString(1).trim());//题号
	              	as1N2.add(rs1N.getString(2).trim());//题目
	              	as1N3.add(rs1N.getString(3).trim());//a
	              	as1N4.add(rs1N.getString(4).trim());//b
	              	as1N5.add(rs1N.getString(5).trim());//c
	              	as1N6.add(rs1N.getString(6).trim());//d
	              	as1N7.add(rs1N.getString(7).trim());//pic
	              	as1N8.add(rs1N.getString(8).trim());//答案
	              	as1N9.add(rs1N.getString(9).trim());//difficulty
	              	as1NA.add(rs1N.getString(10).trim());//type
	            }
	            for (int i = 0; i < as1N1.size(); i++) {
	              	String execute = "INSERT INTO test VALUES('";
	              	execute+=as1N1.get(i)+"','";
	              	execute+=as1N2.get(i)+"','";
	              	execute+=as1N3.get(i)+"','";
	              	execute+=as1N4.get(i)+"','";
	              	execute+=as1N5.get(i)+"','";
	              	execute+=as1N6.get(i)+"','";
	              	execute+=as1N7.get(i)+"','";
	              	execute+=as1N8.get(i)+"','";
	              	execute+=as1N9.get(i)+"','";
	              	execute+=as1NA.get(i)+"')";
	              	sql.executeUpdate(execute);
	            }
	              
	            ArrayList<String> as1H1=new ArrayList<String>();
	            ArrayList<String> as1H2=new ArrayList<String>();
	            ArrayList<String> as1H3=new ArrayList<String>();
	            ArrayList<String> as1H4=new ArrayList<String>();
	            ArrayList<String> as1H5=new ArrayList<String>();
	            ArrayList<String> as1H6=new ArrayList<String>();
	            ArrayList<String> as1H7=new ArrayList<String>();
	            ArrayList<String> as1H8=new ArrayList<String>();
	            ArrayList<String> as1H9=new ArrayList<String>();
	            ArrayList<String> as1HA=new ArrayList<String>();          	
	            rs1H = sql.executeQuery("SELECT * FROM chapter11 WHERE difficulty = 3");
	            rs1H.last();
	            int kemu4Chapter1TotalHardAmount = rs1H.getRow();          	
	            LinkedList<Integer> kemu4Chapter1HardList = new LinkedList<Integer>();
	            for(int i=1; i<=kemu4Chapter1TotalHardAmount; i++) {
	              	kemu4Chapter1HardList.add(i);
	            }
	            for(int i=1; i<=kemu4Chapter1TestHardAmount; i++){
	              	m0 = (int)(Math.random()*kemu4Chapter1HardList.size());
	              	index0 = kemu4Chapter1HardList.get(m0);
	              	kemu4Chapter1HardList.remove(m0);
	              	rs1H.absolute(index0);
	              	as1H1.add(rs1H.getString(1).trim());//题号
	              	as1H2.add(rs1H.getString(2).trim());//题目
	              	as1H3.add(rs1H.getString(3).trim());//a
	              	as1H4.add(rs1H.getString(4).trim());//b
	              	as1H5.add(rs1H.getString(5).trim());//c
	              	as1H6.add(rs1H.getString(6).trim());//d
	              	as1H7.add(rs1H.getString(7).trim());//pic
	              	as1H8.add(rs1H.getString(8).trim());//答案
	              	as1H9.add(rs1H.getString(9).trim());//difficulty
	              	as1HA.add(rs1H.getString(10).trim());//type
	            }
	            for (int i = 0; i < as1H1.size(); i++) {
	              	String execute = "INSERT INTO test VALUES('";
	              	execute+=as1H1.get(i)+"','";
	              	execute+=as1H2.get(i)+"','";
	              	execute+=as1H3.get(i)+"','";
	              	execute+=as1H4.get(i)+"','";
	              	execute+=as1H5.get(i)+"','";
	              	execute+=as1H6.get(i)+"','";
	              	execute+=as1H7.get(i)+"','";
	              	execute+=as1H8.get(i)+"','";
	              	execute+=as1H9.get(i)+"','";
	              	execute+=as1HA.get(i)+"')";
	              	sql.executeUpdate(execute);
	            }
	              
	            //chapter2
	            ArrayList<String> as2E1=new ArrayList<String>();
	            ArrayList<String> as2E2=new ArrayList<String>();
	            ArrayList<String> as2E3=new ArrayList<String>();
	            ArrayList<String> as2E4=new ArrayList<String>();
	            ArrayList<String> as2E5=new ArrayList<String>();
	            ArrayList<String> as2E6=new ArrayList<String>();
	            ArrayList<String> as2E7=new ArrayList<String>();
	            ArrayList<String> as2E8=new ArrayList<String>();
	            ArrayList<String> as2E9=new ArrayList<String>();
	            ArrayList<String> as2EA=new ArrayList<String>();          	
	            rs2E = sql.executeQuery("SELECT * FROM chapter12 WHERE difficulty = 1");
	            rs2E.last();
	            int kemu4Chapter2TotalEasyAmount = rs2E.getRow();          	
	            LinkedList<Integer> kemu4Chapter2EasyList = new LinkedList<Integer>();
	            for(int i=1; i<=kemu4Chapter2TotalEasyAmount; i++) {
	              	kemu4Chapter2EasyList.add(i);
	            }
	            for(int i=1; i<=kemu4Chapter2TestEasyAmount; i++){
	              	m0 = (int)(Math.random()*kemu4Chapter2EasyList.size());
	              	index0 = kemu4Chapter2EasyList.get(m0);
	              	kemu4Chapter2EasyList.remove(m0);
	              	rs2E.absolute(index0);
	              	as2E1.add(rs2E.getString(1).trim());//题号
	              	as2E2.add(rs2E.getString(2).trim());//题目
	              	as2E3.add(rs2E.getString(3).trim());//a
	              	as2E4.add(rs2E.getString(4).trim());//b
	              	as2E5.add(rs2E.getString(5).trim());//c
	              	as2E6.add(rs2E.getString(6).trim());//d
	              	as2E7.add(rs2E.getString(7).trim());//pic
	              	as2E8.add(rs2E.getString(8).trim());//答案
	              	as2E9.add(rs2E.getString(9).trim());//difficulty
	              	as2EA.add(rs2E.getString(10).trim());//type
	            }
	            for (int i = 0; i < as2E1.size(); i++) {
	              	String execute = "INSERT INTO test VALUES('";
	              	execute+=as2E1.get(i)+"','";
	              	execute+=as2E2.get(i)+"','";
	              	execute+=as2E3.get(i)+"','";
	              	execute+=as2E4.get(i)+"','";
	              	execute+=as2E5.get(i)+"','";
	              	execute+=as2E6.get(i)+"','";
	              	execute+=as2E7.get(i)+"','";
	              	execute+=as2E8.get(i)+"','";
	              	execute+=as2E9.get(i)+"','";
	              	execute+=as2EA.get(i)+"')";
	              	sql.executeUpdate(execute);
	            }
	              
	            ArrayList<String> as2N1=new ArrayList<String>();
	            ArrayList<String> as2N2=new ArrayList<String>();
	            ArrayList<String> as2N3=new ArrayList<String>();
	            ArrayList<String> as2N4=new ArrayList<String>();
	            ArrayList<String> as2N5=new ArrayList<String>();
	            ArrayList<String> as2N6=new ArrayList<String>();
	            ArrayList<String> as2N7=new ArrayList<String>();
	            ArrayList<String> as2N8=new ArrayList<String>();
	            ArrayList<String> as2N9=new ArrayList<String>();
	            ArrayList<String> as2NA=new ArrayList<String>();          	
	            rs2N = sql.executeQuery("SELECT * FROM chapter12 WHERE difficulty = 2");
	            rs2N.last();
	            int kemu4Chapter2TotalNormalAmount = rs2N.getRow();          	
	            LinkedList<Integer> kemu4Chapter2NormalList = new LinkedList<Integer>();
	            for(int i=1; i<=kemu4Chapter2TotalNormalAmount; i++) {
	              	kemu4Chapter2NormalList.add(i);
	            }
	            for(int i=1; i<=kemu4Chapter2TestNormalAmount; i++){
	              	m0 = (int)(Math.random()*kemu4Chapter2NormalList.size());
	              	index0 = kemu4Chapter2NormalList.get(m0);
	              	kemu4Chapter2NormalList.remove(m0);
	              	rs2N.absolute(index0);
	              	as2N1.add(rs2N.getString(1).trim());//题号
	              	as2N2.add(rs2N.getString(2).trim());//题目
	              	as2N3.add(rs2N.getString(3).trim());//a
	              	as2N4.add(rs2N.getString(4).trim());//b
	              	as2N5.add(rs2N.getString(5).trim());//c
	              	as2N6.add(rs2N.getString(6).trim());//d
	              	as2N7.add(rs2N.getString(7).trim());//pic
	              	as2N8.add(rs2N.getString(8).trim());//答案
	              	as2N9.add(rs2N.getString(9).trim());//difficulty
	              	as2NA.add(rs2N.getString(10).trim());//type
	            }
	            for (int i = 0; i < as2N1.size(); i++) {
	              	String execute = "INSERT INTO test VALUES('";
	              	execute+=as2N1.get(i)+"','";
	              	execute+=as2N2.get(i)+"','";
	              	execute+=as2N3.get(i)+"','";
	              	execute+=as2N4.get(i)+"','";
	              	execute+=as2N5.get(i)+"','";
	              	execute+=as2N6.get(i)+"','";
	              	execute+=as2N7.get(i)+"','";
	              	execute+=as2N8.get(i)+"','";
	              	execute+=as2N9.get(i)+"','";
	              	execute+=as2NA.get(i)+"')";
	              	sql.executeUpdate(execute);
	            }
	              
	            ArrayList<String> as2H1=new ArrayList<String>();
	            ArrayList<String> as2H2=new ArrayList<String>();
	            ArrayList<String> as2H3=new ArrayList<String>();
	            ArrayList<String> as2H4=new ArrayList<String>();
	            ArrayList<String> as2H5=new ArrayList<String>();
	            ArrayList<String> as2H6=new ArrayList<String>();
	            ArrayList<String> as2H7=new ArrayList<String>();
	            ArrayList<String> as2H8=new ArrayList<String>();
	            ArrayList<String> as2H9=new ArrayList<String>();
	            ArrayList<String> as2HA=new ArrayList<String>();          	
	            rs2H = sql.executeQuery("SELECT * FROM chapter12 WHERE difficulty = 3");
	            rs2H.last();
	            int kemu4Chapter2TotalHardAmount = rs2H.getRow();          	
	            LinkedList<Integer> kemu4Chapter2HardList = new LinkedList<Integer>();
	            for(int i=1; i<=kemu4Chapter2TotalHardAmount; i++) {
	              	kemu4Chapter2HardList.add(i);
	            }
	            for(int i=1; i<=kemu4Chapter2TestHardAmount; i++){
	              	m0 = (int)(Math.random()*kemu4Chapter2HardList.size());
	              	index0 = kemu4Chapter2HardList.get(m0);
	              	kemu4Chapter2HardList.remove(m0);
	              	rs2H.absolute(index0);
	              	as2H1.add(rs2H.getString(1).trim());//题号
	              	as2H2.add(rs2H.getString(2).trim());//题目
	              	as2H3.add(rs2H.getString(3).trim());//a
	              	as2H4.add(rs2H.getString(4).trim());//b
	              	as2H5.add(rs2H.getString(5).trim());//c
	              	as2H6.add(rs2H.getString(6).trim());//d
	              	as2H7.add(rs2H.getString(7).trim());//pic
	              	as2H8.add(rs2H.getString(8).trim());//答案
	              	as2H9.add(rs2H.getString(9).trim());//difficulty
	              	as2HA.add(rs2H.getString(10).trim());//type
	            }
	            for (int i = 0; i < as2H1.size(); i++) {
	              	String execute = "INSERT INTO test VALUES('";
	              	execute+=as2H1.get(i)+"','";
	              	execute+=as2H2.get(i)+"','";
	              	execute+=as2H3.get(i)+"','";
	              	execute+=as2H4.get(i)+"','";
	              	execute+=as2H5.get(i)+"','";
	              	execute+=as2H6.get(i)+"','";
	              	execute+=as2H7.get(i)+"','";
	              	execute+=as2H8.get(i)+"','";
	              	execute+=as2H9.get(i)+"','";
	              	execute+=as2HA.get(i)+"')";
	              	sql.executeUpdate(execute);
	            }
	              
	          	//chapter3
	            ArrayList<String> as3E1=new ArrayList<String>();
	            ArrayList<String> as3E2=new ArrayList<String>();
	            ArrayList<String> as3E3=new ArrayList<String>();
	            ArrayList<String> as3E4=new ArrayList<String>();
	            ArrayList<String> as3E5=new ArrayList<String>();
	            ArrayList<String> as3E6=new ArrayList<String>();
	            ArrayList<String> as3E7=new ArrayList<String>();
	            ArrayList<String> as3E8=new ArrayList<String>();
	            ArrayList<String> as3E9=new ArrayList<String>();
	            ArrayList<String> as3EA=new ArrayList<String>();          	
	            rs3E = sql.executeQuery("SELECT * FROM chapter13 WHERE difficulty = 1");
	            rs3E.last();
	            int kemu4Chapter3TotalEasyAmount = rs3E.getRow();          	
	            LinkedList<Integer> kemu4Chapter3EasyList = new LinkedList<Integer>();
	            for(int i=1; i<=kemu4Chapter3TotalEasyAmount; i++) {
	              	kemu4Chapter3EasyList.add(i);
	            }
	            for(int i=1; i<=kemu4Chapter3TestEasyAmount; i++){
	              	m0 = (int)(Math.random()*kemu4Chapter3EasyList.size());
	              	index0 = kemu4Chapter3EasyList.get(m0);
	              	kemu4Chapter3EasyList.remove(m0);
	              	rs3E.absolute(index0);
	              	as3E1.add(rs3E.getString(1).trim());//题号
	              	as3E2.add(rs3E.getString(2).trim());//题目
	              	as3E3.add(rs3E.getString(3).trim());//a
	              	as3E4.add(rs3E.getString(4).trim());//b
	              	as3E5.add(rs3E.getString(5).trim());//c
	              	as3E6.add(rs3E.getString(6).trim());//d
	              	as3E7.add(rs3E.getString(7).trim());//pic
	              	as3E8.add(rs3E.getString(8).trim());//答案
	              	as3E9.add(rs3E.getString(9).trim());//difficulty
	              	as3EA.add(rs3E.getString(10).trim());//type
	            }
	            for (int i = 0; i < as3E1.size(); i++) {
	              	String execute = "INSERT INTO test VALUES('";
	              	execute+=as3E1.get(i)+"','";
	              	execute+=as3E2.get(i)+"','";
	              	execute+=as3E3.get(i)+"','";
	              	execute+=as3E4.get(i)+"','";
	              	execute+=as3E5.get(i)+"','";
	              	execute+=as3E6.get(i)+"','";
	              	execute+=as3E7.get(i)+"','";
	              	execute+=as3E8.get(i)+"','";
	              	execute+=as3E9.get(i)+"','";
	              	execute+=as3EA.get(i)+"')";
	              	sql.executeUpdate(execute);
	            }
	              
	            ArrayList<String> as3N1=new ArrayList<String>();
	            ArrayList<String> as3N2=new ArrayList<String>();
	            ArrayList<String> as3N3=new ArrayList<String>();
	            ArrayList<String> as3N4=new ArrayList<String>();
	            ArrayList<String> as3N5=new ArrayList<String>();
	            ArrayList<String> as3N6=new ArrayList<String>();
	            ArrayList<String> as3N7=new ArrayList<String>();
	            ArrayList<String> as3N8=new ArrayList<String>();
	            ArrayList<String> as3N9=new ArrayList<String>();
	            ArrayList<String> as3NA=new ArrayList<String>();          	
	            rs3N = sql.executeQuery("SELECT * FROM chapter13 WHERE difficulty = 2");
	            rs3N.last();
	            int kemu4Chapter3TotalNormalAmount = rs3N.getRow();          	
	            LinkedList<Integer> kemu4Chapter3NormalList = new LinkedList<Integer>();
	            for(int i=1; i<=kemu4Chapter3TotalNormalAmount; i++) {
	              	kemu4Chapter3NormalList.add(i);
	            }
	            for(int i=1; i<=kemu4Chapter3TestNormalAmount; i++){
	              	m0 = (int)(Math.random()*kemu4Chapter3NormalList.size());
	              	index0 = kemu4Chapter3NormalList.get(m0);
	              	kemu4Chapter3NormalList.remove(m0);
	              	rs3N.absolute(index0);
	              	as3N1.add(rs3N.getString(1).trim());//题号
	              	as3N2.add(rs3N.getString(2).trim());//题目
	              	as3N3.add(rs3N.getString(3).trim());//a
	              	as3N4.add(rs3N.getString(4).trim());//b
	              	as3N5.add(rs3N.getString(5).trim());//c
	              	as3N6.add(rs3N.getString(6).trim());//d
	              	as3N7.add(rs3N.getString(7).trim());//pic
	              	as3N8.add(rs3N.getString(8).trim());//答案
	              	as3N9.add(rs3N.getString(9).trim());//difficulty
	              	as3NA.add(rs3N.getString(10).trim());//type
	            }
	            for (int i = 0; i < as3N1.size(); i++) {
	              	String execute = "INSERT INTO test VALUES('";
	              	execute+=as3N1.get(i)+"','";
	              	execute+=as3N2.get(i)+"','";
	              	execute+=as3N3.get(i)+"','";
	              	execute+=as3N4.get(i)+"','";
	              	execute+=as3N5.get(i)+"','";
	              	execute+=as3N6.get(i)+"','";
	              	execute+=as3N7.get(i)+"','";
	              	execute+=as3N8.get(i)+"','";
	              	execute+=as3N9.get(i)+"','";
	              	execute+=as3NA.get(i)+"')";
	              	sql.executeUpdate(execute);
	            }
	              
	            ArrayList<String> as3H1=new ArrayList<String>();
	            ArrayList<String> as3H2=new ArrayList<String>();
	            ArrayList<String> as3H3=new ArrayList<String>();
	            ArrayList<String> as3H4=new ArrayList<String>();
	            ArrayList<String> as3H5=new ArrayList<String>();
	            ArrayList<String> as3H6=new ArrayList<String>();
	            ArrayList<String> as3H7=new ArrayList<String>();
	            ArrayList<String> as3H8=new ArrayList<String>();
	            ArrayList<String> as3H9=new ArrayList<String>();
	            ArrayList<String> as3HA=new ArrayList<String>();          	
	            rs3H = sql.executeQuery("SELECT * FROM chapter13 WHERE difficulty = 3");
	            rs3H.last();
	            int kemu4Chapter3TotalHardAmount = rs3H.getRow();          	
	            LinkedList<Integer> kemu4Chapter3HardList = new LinkedList<Integer>();
	            for(int i=1; i<=kemu4Chapter3TotalHardAmount; i++) {
	              	kemu4Chapter3HardList.add(i);
	            }
	            for(int i=1; i<=kemu4Chapter3TestHardAmount; i++){
	              	m0 = (int)(Math.random()*kemu4Chapter3HardList.size());
	              	index0 = kemu4Chapter3HardList.get(m0);
	              	kemu4Chapter3HardList.remove(m0);
	              	rs3H.absolute(index0);
	              	as3H1.add(rs3H.getString(1).trim());//题号
	              	as3H2.add(rs3H.getString(2).trim());//题目
	              	as3H3.add(rs3H.getString(3).trim());//a
	              	as3H4.add(rs3H.getString(4).trim());//b
	              	as3H5.add(rs3H.getString(5).trim());//c
	              	as3H6.add(rs3H.getString(6).trim());//d
	              	as3H7.add(rs3H.getString(7).trim());//pic
	              	as3H8.add(rs3H.getString(8).trim());//答案
	              	as3H9.add(rs3H.getString(9).trim());//difficulty
	              	as3HA.add(rs3H.getString(10).trim());//type
	            }
	            for (int i = 0; i < as3H1.size(); i++) {
	              	String execute = "INSERT INTO test VALUES('";
	              	execute+=as3H1.get(i)+"','";
	              	execute+=as3H2.get(i)+"','";
	              	execute+=as3H3.get(i)+"','";
	              	execute+=as3H4.get(i)+"','";
	              	execute+=as3H5.get(i)+"','";
	              	execute+=as3H6.get(i)+"','";
	              	execute+=as3H7.get(i)+"','";
	              	execute+=as3H8.get(i)+"','";
	              	execute+=as3H9.get(i)+"','";
	              	execute+=as3HA.get(i)+"')";
	              	sql.executeUpdate(execute);
	            }
	              
	            //chapter4
	            ArrayList<String> as4E1=new ArrayList<String>();
	            ArrayList<String> as4E2=new ArrayList<String>();
	            ArrayList<String> as4E3=new ArrayList<String>();
	            ArrayList<String> as4E4=new ArrayList<String>();
	            ArrayList<String> as4E5=new ArrayList<String>();
	            ArrayList<String> as4E6=new ArrayList<String>();
	            ArrayList<String> as4E7=new ArrayList<String>();
	            ArrayList<String> as4E8=new ArrayList<String>();
	            ArrayList<String> as4E9=new ArrayList<String>();
	            ArrayList<String> as4EA=new ArrayList<String>();          	
	            rs4E = sql.executeQuery("SELECT * FROM chapter14 WHERE difficulty = 1");
	            rs4E.last();
	            int kemu4Chapter4TotalEasyAmount = rs4E.getRow();
	            LinkedList<Integer> kemu4Chapter4EasyList = new LinkedList<Integer>();
	            for(int i=1; i<=kemu4Chapter4TotalEasyAmount; i++) {
	              	kemu4Chapter4EasyList.add(i);
	            }
	            for(int i=1; i<=kemu4Chapter4TestEasyAmount; i++){
	              	m0 = (int)(Math.random()*kemu4Chapter4EasyList.size());
	              	index0 = kemu4Chapter4EasyList.get(m0);
	              	kemu4Chapter4EasyList.remove(m0);
	              	rs4E.absolute(index0);
	              	as4E1.add(rs4E.getString(1).trim());//题号
	              	as4E2.add(rs4E.getString(2).trim());//题目
	              	as4E3.add(rs4E.getString(3).trim());//a
	              	as4E4.add(rs4E.getString(4).trim());//b
	              	as4E5.add(rs4E.getString(5).trim());//c
	              	as4E6.add(rs4E.getString(6).trim());//d
	              	as4E7.add(rs4E.getString(7).trim());//pic
	              	as4E8.add(rs4E.getString(8).trim());//答案
	              	as4E9.add(rs4E.getString(9).trim());//difficulty
	              	as4EA.add(rs4E.getString(10).trim());//type
	            }
	            for (int i = 0; i < as4E1.size(); i++) {
	              	String execute = "INSERT INTO test VALUES('";
	              	execute+=as4E1.get(i)+"','";
	              	execute+=as4E2.get(i)+"','";
	              	execute+=as4E3.get(i)+"','";
	              	execute+=as4E4.get(i)+"','";
	              	execute+=as4E5.get(i)+"','";
	              	execute+=as4E6.get(i)+"','";
	              	execute+=as4E7.get(i)+"','";
	              	execute+=as4E8.get(i)+"','";
	              	execute+=as4E9.get(i)+"','";
	              	execute+=as4EA.get(i)+"')";
	              	sql.executeUpdate(execute);
	            }
	              
	            ArrayList<String> as4N1=new ArrayList<String>();
	            ArrayList<String> as4N2=new ArrayList<String>();
	            ArrayList<String> as4N3=new ArrayList<String>();
	            ArrayList<String> as4N4=new ArrayList<String>();
	            ArrayList<String> as4N5=new ArrayList<String>();
	            ArrayList<String> as4N6=new ArrayList<String>();
	            ArrayList<String> as4N7=new ArrayList<String>();
	            ArrayList<String> as4N8=new ArrayList<String>();
	            ArrayList<String> as4N9=new ArrayList<String>();
	            ArrayList<String> as4NA=new ArrayList<String>();          	
	            rs4N = sql.executeQuery("SELECT * FROM chapter14 WHERE difficulty = 2");
	            rs4N.last();
	            int kemu4Chapter4TotalNormalAmount = rs4N.getRow();          	
	            LinkedList<Integer> kemu4Chapter4NormalList = new LinkedList<Integer>();
	            for(int i=1; i<=kemu4Chapter4TotalNormalAmount; i++) {
	              	kemu4Chapter4NormalList.add(i);
	            }
	            for(int i=1; i<=kemu4Chapter4TestNormalAmount; i++){
	              	m0 = (int)(Math.random()*kemu4Chapter4NormalList.size());
	              	index0 = kemu4Chapter4NormalList.get(m0);
	              	kemu4Chapter4NormalList.remove(m0);
	              	rs4N.absolute(index0);
	              	as4N1.add(rs4N.getString(1).trim());//题号
	              	as4N2.add(rs4N.getString(2).trim());//题目
	              	as4N3.add(rs4N.getString(3).trim());//a
	              	as4N4.add(rs4N.getString(4).trim());//b
	              	as4N5.add(rs4N.getString(5).trim());//c
	              	as4N6.add(rs4N.getString(6).trim());//d
	              	as4N7.add(rs4N.getString(7).trim());//pic
	              	as4N8.add(rs4N.getString(8).trim());//答案
	              	as4N9.add(rs4N.getString(9).trim());//difficulty
	              	as4NA.add(rs4N.getString(10).trim());//type
	            }
	            for (int i = 0; i < as4N1.size(); i++) {
	              	String execute = "INSERT INTO test VALUES('";
	              	execute+=as4N1.get(i)+"','";
	              	execute+=as4N2.get(i)+"','";
	              	execute+=as4N3.get(i)+"','";
	              	execute+=as4N4.get(i)+"','";
	              	execute+=as4N5.get(i)+"','";
	              	execute+=as4N6.get(i)+"','";
	              	execute+=as4N7.get(i)+"','";
	              	execute+=as4N8.get(i)+"','";
	              	execute+=as4N9.get(i)+"','";
	              	execute+=as4NA.get(i)+"')";
	              	sql.executeUpdate(execute);
	            }
	              
	            ArrayList<String> as4H1=new ArrayList<String>();
	            ArrayList<String> as4H2=new ArrayList<String>();
	            ArrayList<String> as4H3=new ArrayList<String>();
	            ArrayList<String> as4H4=new ArrayList<String>();
	            ArrayList<String> as4H5=new ArrayList<String>();
	            ArrayList<String> as4H6=new ArrayList<String>();
	            ArrayList<String> as4H7=new ArrayList<String>();
	            ArrayList<String> as4H8=new ArrayList<String>();
	            ArrayList<String> as4H9=new ArrayList<String>();
	            ArrayList<String> as4HA=new ArrayList<String>();          	
	            rs4H = sql.executeQuery("SELECT * FROM chapter14 WHERE difficulty = 3");
	            rs4H.last();
	            int kemu4Chapter4TotalHardAmount = rs4H.getRow();          	
	            LinkedList<Integer> kemu4Chapter4HardList = new LinkedList<Integer>();
	            for(int i=1; i<=kemu4Chapter4TotalHardAmount; i++) {
	              	kemu4Chapter4HardList.add(i);
	            }
	            for(int i=1; i<=kemu4Chapter4TestHardAmount; i++){
	              	m0 = (int)(Math.random()*kemu4Chapter4HardList.size());
	              	index0 = kemu4Chapter4HardList.get(m0);
	              	kemu4Chapter4HardList.remove(m0);
	              	rs4H.absolute(index0);
	              	as4H1.add(rs4H.getString(1).trim());//题号
	              	as4H2.add(rs4H.getString(2).trim());//题目
	              	as4H3.add(rs4H.getString(3).trim());//a
	              	as4H4.add(rs4H.getString(4).trim());//b
	              	as4H5.add(rs4H.getString(5).trim());//c
	              	as4H6.add(rs4H.getString(6).trim());//d
	              	as4H7.add(rs4H.getString(7).trim());//pic
	              	as4H8.add(rs4H.getString(8).trim());//答案
	              	as4H9.add(rs4H.getString(9).trim());//difficulty
	              	as4HA.add(rs4H.getString(10).trim());//type
	            }
	            for (int i = 0; i < as4H1.size(); i++) {
	              	String execute = "INSERT INTO test VALUES('";
	              	execute+=as4H1.get(i)+"','";
	              	execute+=as4H2.get(i)+"','";
	              	execute+=as4H3.get(i)+"','";
	              	execute+=as4H4.get(i)+"','";
	              	execute+=as4H5.get(i)+"','";
	              	execute+=as4H6.get(i)+"','";
	              	execute+=as4H7.get(i)+"','";
	              	execute+=as4H8.get(i)+"','";
	              	execute+=as4H9.get(i)+"','";
	              	execute+=as4HA.get(i)+"')";
	              	sql.executeUpdate(execute);
	            }
	            
	            //chapter5
	            ArrayList<String> as5E1=new ArrayList<String>();
	            ArrayList<String> as5E2=new ArrayList<String>();
	            ArrayList<String> as5E3=new ArrayList<String>();
	            ArrayList<String> as5E4=new ArrayList<String>();
	            ArrayList<String> as5E5=new ArrayList<String>();
	            ArrayList<String> as5E6=new ArrayList<String>();
	            ArrayList<String> as5E7=new ArrayList<String>();
	            ArrayList<String> as5E8=new ArrayList<String>();
	            ArrayList<String> as5E9=new ArrayList<String>();
	            ArrayList<String> as5EA=new ArrayList<String>();          	
	            rs5E = sql.executeQuery("SELECT * FROM chapter15 WHERE difficulty = 1");
	            rs5E.last();
	            int kemu4Chapter5TotalEasyAmount = rs5E.getRow();          	
	            LinkedList<Integer> kemu4Chapter5EasyList = new LinkedList<Integer>();
	            for(int i=1; i<=kemu4Chapter5TotalEasyAmount; i++) {
	              	kemu4Chapter5EasyList.add(i);
	            }
	            for(int i=1; i<=kemu4Chapter5TestEasyAmount; i++){
	              	m0 = (int)(Math.random()*kemu4Chapter5EasyList.size());
	              	index0 = kemu4Chapter5EasyList.get(m0);
	              	kemu4Chapter5EasyList.remove(m0);
	              	rs5E.absolute(index0);
	              	as5E1.add(rs5E.getString(1).trim());//题号
	              	as5E2.add(rs5E.getString(2).trim());//题目
	              	as5E3.add(rs5E.getString(3).trim());//a
	              	as5E4.add(rs5E.getString(4).trim());//b
	              	as5E5.add(rs5E.getString(5).trim());//c
	              	as5E6.add(rs5E.getString(6).trim());//d
	              	as5E7.add(rs5E.getString(7).trim());//pic
	              	as5E8.add(rs5E.getString(8).trim());//答案
	              	as5E9.add(rs5E.getString(9).trim());//difficulty
	              	as5EA.add(rs5E.getString(10).trim());//type
	            }
	            for (int i = 0; i < as5E1.size(); i++) {
	              	String execute = "INSERT INTO test VALUES('";
	              	execute+=as5E1.get(i)+"','";
	              	execute+=as5E2.get(i)+"','";
	              	execute+=as5E3.get(i)+"','";
	              	execute+=as5E4.get(i)+"','";
	              	execute+=as5E5.get(i)+"','";
	              	execute+=as5E6.get(i)+"','";
	              	execute+=as5E7.get(i)+"','";
	              	execute+=as5E8.get(i)+"','";
	              	execute+=as5E9.get(i)+"','";
	              	execute+=as5EA.get(i)+"')";
	              	sql.executeUpdate(execute);
	            }
	              
	            ArrayList<String> as5N1=new ArrayList<String>();
	            ArrayList<String> as5N2=new ArrayList<String>();
	            ArrayList<String> as5N3=new ArrayList<String>();
	            ArrayList<String> as5N4=new ArrayList<String>();
	            ArrayList<String> as5N5=new ArrayList<String>();
	            ArrayList<String> as5N6=new ArrayList<String>();
	            ArrayList<String> as5N7=new ArrayList<String>();
	            ArrayList<String> as5N8=new ArrayList<String>();
	            ArrayList<String> as5N9=new ArrayList<String>();
	            ArrayList<String> as5NA=new ArrayList<String>();          	
	            rs5N = sql.executeQuery("SELECT * FROM chapter15 WHERE difficulty = 2");
	            rs5N.last();
	            int kemu4Chapter5TotalNormalAmount = rs5N.getRow();          	
	            LinkedList<Integer> kemu4Chapter5NormalList = new LinkedList<Integer>();
	            for(int i=1; i<=kemu4Chapter5TotalNormalAmount; i++) {
	              	kemu4Chapter5NormalList.add(i);
	            }
	            for(int i=1; i<=kemu4Chapter5TestNormalAmount; i++){
	              	m0 = (int)(Math.random()*kemu4Chapter5NormalList.size());
	              	index0 = kemu4Chapter5NormalList.get(m0);
	              	kemu4Chapter5NormalList.remove(m0);
	              	rs5N.absolute(index0);
	              	as5N1.add(rs5N.getString(1).trim());//题号
	              	as5N2.add(rs5N.getString(2).trim());//题目
	              	as5N3.add(rs5N.getString(3).trim());//a
	              	as5N4.add(rs5N.getString(4).trim());//b
	              	as5N5.add(rs5N.getString(5).trim());//c
	              	as5N6.add(rs5N.getString(6).trim());//d
	              	as5N7.add(rs5N.getString(7).trim());//pic
	              	as5N8.add(rs5N.getString(8).trim());//答案
	              	as5N9.add(rs5N.getString(9).trim());//difficulty
	              	as5NA.add(rs5N.getString(10).trim());//type
	            }
	            for (int i = 0; i < as5N1.size(); i++) {
	              	String execute = "INSERT INTO test VALUES('";
	              	execute+=as5N1.get(i)+"','";
	              	execute+=as5N2.get(i)+"','";
	              	execute+=as5N3.get(i)+"','";
	              	execute+=as5N4.get(i)+"','";
	              	execute+=as5N5.get(i)+"','";
	              	execute+=as5N6.get(i)+"','";
	              	execute+=as5N7.get(i)+"','";
	              	execute+=as5N8.get(i)+"','";
	              	execute+=as5N9.get(i)+"','";
	              	execute+=as5NA.get(i)+"')";
	              	sql.executeUpdate(execute);
	            }
	              
	            ArrayList<String> as5H1=new ArrayList<String>();
	            ArrayList<String> as5H2=new ArrayList<String>();
	            ArrayList<String> as5H3=new ArrayList<String>();
	            ArrayList<String> as5H4=new ArrayList<String>();
	            ArrayList<String> as5H5=new ArrayList<String>();
	            ArrayList<String> as5H6=new ArrayList<String>();
	            ArrayList<String> as5H7=new ArrayList<String>();
	            ArrayList<String> as5H8=new ArrayList<String>();
	            ArrayList<String> as5H9=new ArrayList<String>();
	            ArrayList<String> as5HA=new ArrayList<String>();          	
	            rs5H = sql.executeQuery("SELECT * FROM chapter15 WHERE difficulty = 3");
	            rs5H.last();
	            int kemu4Chapter5TotalHardAmount = rs5H.getRow();          	
	            LinkedList<Integer> kemu4Chapter5HardList = new LinkedList<Integer>();
	            for(int i=1; i<=kemu4Chapter5TotalHardAmount; i++) {
	              	kemu4Chapter5HardList.add(i);
	            }
	            for(int i=1; i<=kemu4Chapter5TestHardAmount; i++){
	              	m0 = (int)(Math.random()*kemu4Chapter5HardList.size());
	              	index0 = kemu4Chapter5HardList.get(m0);
	              	kemu4Chapter5HardList.remove(m0);
	              	rs5H.absolute(index0);
	              	as5H1.add(rs5H.getString(1).trim());//题号
	              	as5H2.add(rs5H.getString(2).trim());//题目
	              	as5H3.add(rs5H.getString(3).trim());//a
	              	as5H4.add(rs5H.getString(4).trim());//b
	              	as5H5.add(rs5H.getString(5).trim());//c
	              	as5H6.add(rs5H.getString(6).trim());//d
	              	as5H7.add(rs5H.getString(7).trim());//pic
	              	as5H8.add(rs5H.getString(8).trim());//答案
	              	as5H9.add(rs5H.getString(9).trim());//difficulty
	              	as5HA.add(rs5H.getString(10).trim());//type
	            }
	            for (int i = 0; i < as5H1.size(); i++) {
	              	String execute = "INSERT INTO test VALUES('";
	              	execute+=as5H1.get(i)+"','";
	              	execute+=as5H2.get(i)+"','";
	              	execute+=as5H3.get(i)+"','";
	              	execute+=as5H4.get(i)+"','";
	              	execute+=as5H5.get(i)+"','";
	              	execute+=as5H6.get(i)+"','";
	              	execute+=as5H7.get(i)+"','";
	              	execute+=as5H8.get(i)+"','";
	              	execute+=as5H9.get(i)+"','";
	              	execute+=as5HA.get(i)+"')";
	              	sql.executeUpdate(execute);
	            }
	              
	          	//chapter6
	            ArrayList<String> as6E1=new ArrayList<String>();
	            ArrayList<String> as6E2=new ArrayList<String>();
	            ArrayList<String> as6E3=new ArrayList<String>();
	            ArrayList<String> as6E4=new ArrayList<String>();
	            ArrayList<String> as6E5=new ArrayList<String>();
	            ArrayList<String> as6E6=new ArrayList<String>();
	            ArrayList<String> as6E7=new ArrayList<String>();
	            ArrayList<String> as6E8=new ArrayList<String>();
	            ArrayList<String> as6E9=new ArrayList<String>();
	            ArrayList<String> as6EA=new ArrayList<String>();          	
	            rs6E = sql.executeQuery("SELECT * FROM chapter16 WHERE difficulty = 1");
	            rs6E.last();
	            int kemu4Chapter6TotalEasyAmount = rs6E.getRow();          	
	            LinkedList<Integer> kemu4Chapter6EasyList = new LinkedList<Integer>();
	            for(int i=1; i<=kemu4Chapter6TotalEasyAmount; i++) {
	              	kemu4Chapter6EasyList.add(i);
	            }
	            for(int i=1; i<=kemu4Chapter6TestEasyAmount; i++){
	              	m0 = (int)(Math.random()*kemu4Chapter6EasyList.size());
	              	index0 = kemu4Chapter6EasyList.get(m0);
	              	kemu4Chapter6EasyList.remove(m0);
	              	rs6E.absolute(index0);
	              	as6E1.add(rs6E.getString(1).trim());//题号
	              	as6E2.add(rs6E.getString(2).trim());//题目
	              	as6E3.add(rs6E.getString(3).trim());//a
	              	as6E4.add(rs6E.getString(4).trim());//b
	              	as6E5.add(rs6E.getString(5).trim());//c
	              	as6E6.add(rs6E.getString(6).trim());//d
	              	as6E7.add(rs6E.getString(7).trim());//pic
	              	as6E8.add(rs6E.getString(8).trim());//答案
	              	as6E9.add(rs6E.getString(9).trim());//difficulty
	              	as6EA.add(rs6E.getString(10).trim());//type
	            }
	            for (int i = 0; i < as6E1.size(); i++) {
	              	String execute = "INSERT INTO test VALUES('";
	              	execute+=as6E1.get(i)+"','";
	              	execute+=as6E2.get(i)+"','";
	              	execute+=as6E3.get(i)+"','";
	              	execute+=as6E4.get(i)+"','";
	              	execute+=as6E5.get(i)+"','";
	              	execute+=as6E6.get(i)+"','";
	              	execute+=as6E7.get(i)+"','";
	              	execute+=as6E8.get(i)+"','";
	              	execute+=as6E9.get(i)+"','";
	              	execute+=as6EA.get(i)+"')";
	              	sql.executeUpdate(execute);
	            }
	              
	            ArrayList<String> as6N1=new ArrayList<String>();
	            ArrayList<String> as6N2=new ArrayList<String>();
	            ArrayList<String> as6N3=new ArrayList<String>();
	            ArrayList<String> as6N4=new ArrayList<String>();
	            ArrayList<String> as6N5=new ArrayList<String>();
	            ArrayList<String> as6N6=new ArrayList<String>();
	            ArrayList<String> as6N7=new ArrayList<String>();
	            ArrayList<String> as6N8=new ArrayList<String>();
	            ArrayList<String> as6N9=new ArrayList<String>();
	            ArrayList<String> as6NA=new ArrayList<String>();          	
	            rs6N = sql.executeQuery("SELECT * FROM chapter16 WHERE difficulty = 2");
	            rs6N.last();
	            int kemu4Chapter6TotalNormalAmount = rs6N.getRow();          	
	            LinkedList<Integer> kemu4Chapter6NormalList = new LinkedList<Integer>();
	            for(int i=1; i<=kemu4Chapter6TotalNormalAmount; i++) {
	              	kemu4Chapter6NormalList.add(i);
	            }
	            for(int i=1; i<=kemu4Chapter6TestNormalAmount; i++){
	              	m0 = (int)(Math.random()*kemu4Chapter6NormalList.size());
	              	index0 = kemu4Chapter6NormalList.get(m0);
	              	kemu4Chapter6NormalList.remove(m0);
	              	rs6N.absolute(index0);
	              	as6N1.add(rs6N.getString(1).trim());//题号
	              	as6N2.add(rs6N.getString(2).trim());//题目
	              	as6N3.add(rs6N.getString(3).trim());//a
	              	as6N4.add(rs6N.getString(4).trim());//b
	              	as6N5.add(rs6N.getString(5).trim());//c
	              	as6N6.add(rs6N.getString(6).trim());//d
	              	as6N7.add(rs6N.getString(7).trim());//pic
	              	as6N8.add(rs6N.getString(8).trim());//答案
	              	as6N9.add(rs6N.getString(9).trim());//difficulty
	              	as6NA.add(rs6N.getString(10).trim());//type
	            }
	            for (int i = 0; i < as6N1.size(); i++) {
	              	String execute = "INSERT INTO test VALUES('";
	              	execute+=as6N1.get(i)+"','";
	              	execute+=as6N2.get(i)+"','";
	              	execute+=as6N3.get(i)+"','";
	              	execute+=as6N4.get(i)+"','";
	              	execute+=as6N5.get(i)+"','";
	              	execute+=as6N6.get(i)+"','";
	              	execute+=as6N7.get(i)+"','";
	              	execute+=as6N8.get(i)+"','";
	              	execute+=as6N9.get(i)+"','";
	              	execute+=as6NA.get(i)+"')";
	              	sql.executeUpdate(execute);
	            }
	              
	            ArrayList<String> as6H1=new ArrayList<String>();
	            ArrayList<String> as6H2=new ArrayList<String>();
	            ArrayList<String> as6H3=new ArrayList<String>();
	            ArrayList<String> as6H4=new ArrayList<String>();
	            ArrayList<String> as6H5=new ArrayList<String>();
	            ArrayList<String> as6H6=new ArrayList<String>();
	            ArrayList<String> as6H7=new ArrayList<String>();
	            ArrayList<String> as6H8=new ArrayList<String>();
	            ArrayList<String> as6H9=new ArrayList<String>();
	            ArrayList<String> as6HA=new ArrayList<String>();          	
	            rs6H = sql.executeQuery("SELECT * FROM chapter16 WHERE difficulty = 3");
	            rs6H.last();
	            int kemu4Chapter6TotalHardAmount = rs6H.getRow();          	
	            LinkedList<Integer> kemu4Chapter6HardList = new LinkedList<Integer>();
	            for(int i=1; i<=kemu4Chapter6TotalHardAmount; i++) {
	              	kemu4Chapter6HardList.add(i);
	            }
	            for(int i=1; i<=kemu4Chapter6TestHardAmount; i++){
	              	m0 = (int)(Math.random()*kemu4Chapter6HardList.size());
	              	index0 = kemu4Chapter6HardList.get(m0);
	              	kemu4Chapter6HardList.remove(m0);
	              	rs6H.absolute(index0);
	              	as6H1.add(rs6H.getString(1).trim());//题号
	              	as6H2.add(rs6H.getString(2).trim());//题目
	              	as6H3.add(rs6H.getString(3).trim());//a
	              	as6H4.add(rs6H.getString(4).trim());//b
	              	as6H5.add(rs6H.getString(5).trim());//c
	              	as6H6.add(rs6H.getString(6).trim());//d
	              	as6H7.add(rs6H.getString(7).trim());//pic
	              	as6H8.add(rs6H.getString(8).trim());//答案
	              	as6H9.add(rs6H.getString(9).trim());//difficulty
	              	as6HA.add(rs6H.getString(10).trim());//type
	            }
	            for (int i = 0; i < as6H1.size(); i++) {
	              	String execute = "INSERT INTO test VALUES('";
	              	execute+=as6H1.get(i)+"','";
	              	execute+=as6H2.get(i)+"','";
	              	execute+=as6H3.get(i)+"','";
	              	execute+=as6H4.get(i)+"','";
	              	execute+=as6H5.get(i)+"','";
	              	execute+=as6H6.get(i)+"','";
	              	execute+=as6H7.get(i)+"','";
	              	execute+=as6H8.get(i)+"','";
	              	execute+=as6H9.get(i)+"','";
	              	execute+=as6HA.get(i)+"')";
	              	sql.executeUpdate(execute);
	            }
	              
	            //chapter7
	            ArrayList<String> as7E1=new ArrayList<String>();
	            ArrayList<String> as7E2=new ArrayList<String>();
	            ArrayList<String> as7E3=new ArrayList<String>();
	            ArrayList<String> as7E4=new ArrayList<String>();
	            ArrayList<String> as7E5=new ArrayList<String>();
	            ArrayList<String> as7E6=new ArrayList<String>();
	            ArrayList<String> as7E7=new ArrayList<String>();
	            ArrayList<String> as7E8=new ArrayList<String>();
	            ArrayList<String> as7E9=new ArrayList<String>();
	            ArrayList<String> as7EA=new ArrayList<String>();          	
	            rs7E = sql.executeQuery("SELECT * FROM chapter17 WHERE difficulty = 1");
	            rs7E.last();
	            int kemu4Chapter7TotalEasyAmount = rs7E.getRow();
	            LinkedList<Integer> kemu4Chapter7EasyList = new LinkedList<Integer>();
	            for(int i=1; i<=kemu4Chapter7TotalEasyAmount; i++) {
	              	kemu4Chapter7EasyList.add(i);
	            }
	            for(int i=1; i<=kemu4Chapter7TestEasyAmount; i++){
	              	m0 = (int)(Math.random()*kemu4Chapter7EasyList.size());
	              	index0 = kemu4Chapter7EasyList.get(m0);
	              	kemu4Chapter7EasyList.remove(m0);
	              	rs7E.absolute(index0);
	              	as7E1.add(rs7E.getString(1).trim());//题号
	              	as7E2.add(rs7E.getString(2).trim());//题目
	              	as7E3.add(rs7E.getString(3).trim());//a
	              	as7E4.add(rs7E.getString(4).trim());//b
	              	as7E5.add(rs7E.getString(5).trim());//c
	              	as7E6.add(rs7E.getString(6).trim());//d
	              	as7E7.add(rs7E.getString(7).trim());//pic
	              	as7E8.add(rs7E.getString(8).trim());//答案
	              	as7E9.add(rs7E.getString(9).trim());//difficulty
	              	as7EA.add(rs7E.getString(10).trim());//type
	            }
	            for (int i = 0; i < as7E1.size(); i++) {
	              	String execute = "INSERT INTO test VALUES('";
	              	execute+=as7E1.get(i)+"','";
	              	execute+=as7E2.get(i)+"','";
	              	execute+=as7E3.get(i)+"','";
	              	execute+=as7E4.get(i)+"','";
	              	execute+=as7E5.get(i)+"','";
	              	execute+=as7E6.get(i)+"','";
	              	execute+=as7E7.get(i)+"','";
	              	execute+=as7E8.get(i)+"','";
	              	execute+=as7E9.get(i)+"','";
	              	execute+=as7EA.get(i)+"')";
	              	sql.executeUpdate(execute);
	            }
	              
	            ArrayList<String> as7N1=new ArrayList<String>();
	            ArrayList<String> as7N2=new ArrayList<String>();
	            ArrayList<String> as7N3=new ArrayList<String>();
	            ArrayList<String> as7N4=new ArrayList<String>();
	            ArrayList<String> as7N5=new ArrayList<String>();
	            ArrayList<String> as7N6=new ArrayList<String>();
	            ArrayList<String> as7N7=new ArrayList<String>();
	            ArrayList<String> as7N8=new ArrayList<String>();
	            ArrayList<String> as7N9=new ArrayList<String>();
	            ArrayList<String> as7NA=new ArrayList<String>();          	
	            rs7N = sql.executeQuery("SELECT * FROM chapter17 WHERE difficulty = 2");
	            rs7N.last();
	            int kemu4Chapter7TotalNormalAmount = rs7N.getRow();          	
	            LinkedList<Integer> kemu4Chapter7NormalList = new LinkedList<Integer>();
	            for(int i=1; i<=kemu4Chapter7TotalNormalAmount; i++) {
	              	kemu4Chapter7NormalList.add(i);
	            }
	            for(int i=1; i<=kemu4Chapter7TestNormalAmount; i++){
	              	m0 = (int)(Math.random()*kemu4Chapter7NormalList.size());
	              	index0 = kemu4Chapter7NormalList.get(m0);
	              	kemu4Chapter7NormalList.remove(m0);
	              	rs7N.absolute(index0);
	              	as7N1.add(rs7N.getString(1).trim());//题号
	              	as7N2.add(rs7N.getString(2).trim());//题目
	              	as7N3.add(rs7N.getString(3).trim());//a
	              	as7N4.add(rs7N.getString(4).trim());//b
	              	as7N5.add(rs7N.getString(5).trim());//c
	              	as7N6.add(rs7N.getString(6).trim());//d
	              	as7N7.add(rs7N.getString(7).trim());//pic
	              	as7N8.add(rs7N.getString(8).trim());//答案
	              	as7N9.add(rs7N.getString(9).trim());//difficulty
	              	as7NA.add(rs7N.getString(10).trim());//type
	            }
	            for (int i = 0; i < as7N1.size(); i++) {
	              	String execute = "INSERT INTO test VALUES('";
	              	execute+=as7N1.get(i)+"','";
	              	execute+=as7N2.get(i)+"','";
	              	execute+=as7N3.get(i)+"','";
	              	execute+=as7N4.get(i)+"','";
	              	execute+=as7N5.get(i)+"','";
	              	execute+=as7N6.get(i)+"','";
	              	execute+=as7N7.get(i)+"','";
	              	execute+=as7N8.get(i)+"','";
	              	execute+=as7N9.get(i)+"','";
	              	execute+=as7NA.get(i)+"')";
	              	sql.executeUpdate(execute);
	            }
	              
	            ArrayList<String> as7H1=new ArrayList<String>();
	            ArrayList<String> as7H2=new ArrayList<String>();
	            ArrayList<String> as7H3=new ArrayList<String>();
	            ArrayList<String> as7H4=new ArrayList<String>();
	            ArrayList<String> as7H5=new ArrayList<String>();
	            ArrayList<String> as7H6=new ArrayList<String>();
	            ArrayList<String> as7H7=new ArrayList<String>();
	            ArrayList<String> as7H8=new ArrayList<String>();
	            ArrayList<String> as7H9=new ArrayList<String>();
	            ArrayList<String> as7HA=new ArrayList<String>();          	
	            rs7H = sql.executeQuery("SELECT * FROM chapter17 WHERE difficulty = 3");
	            rs7H.last();
	            int kemu4Chapter7TotalHardAmount = rs7H.getRow();          	
	            LinkedList<Integer> kemu4Chapter7HardList = new LinkedList<Integer>();
	            for(int i=1; i<=kemu4Chapter7TotalHardAmount; i++) {
	              	kemu4Chapter7HardList.add(i);
	            }
	            for(int i=1; i<=kemu4Chapter7TestHardAmount; i++){
	              	m0 = (int)(Math.random()*kemu4Chapter7HardList.size());
	              	index0 = kemu4Chapter7HardList.get(m0);
	              	kemu4Chapter7HardList.remove(m0);
	              	rs7H.absolute(index0);
	              	as7H1.add(rs7H.getString(1).trim());//题号
	              	as7H2.add(rs7H.getString(2).trim());//题目
	              	as7H3.add(rs7H.getString(3).trim());//a
	              	as7H4.add(rs7H.getString(4).trim());//b
	              	as7H5.add(rs7H.getString(5).trim());//c
	              	as7H6.add(rs7H.getString(6).trim());//d
	              	as7H7.add(rs7H.getString(7).trim());//pic
	              	as7H8.add(rs7H.getString(8).trim());//答案
	              	as7H9.add(rs7H.getString(9).trim());//difficulty
	              	as7HA.add(rs7H.getString(10).trim());//type
	            }
	            for (int i = 0; i < as7H1.size(); i++) {
	              	String execute = "INSERT INTO test VALUES('";
	              	execute+=as7H1.get(i)+"','";
	              	execute+=as7H2.get(i)+"','";
	              	execute+=as7H3.get(i)+"','";
	              	execute+=as7H4.get(i)+"','";
	              	execute+=as7H5.get(i)+"','";
	              	execute+=as7H6.get(i)+"','";
	              	execute+=as7H7.get(i)+"','";
	              	execute+=as7H8.get(i)+"','";
	              	execute+=as7H9.get(i)+"','";
	              	execute+=as7HA.get(i)+"')";
	              	sql.executeUpdate(execute);
	            }
	          
	          }

              System.out.println(id+"参加考试，题目如下：");
              rs=sql.executeQuery("SELECT * FROM test");
              rs.last();    
              int testAmount=rs.getRow()-1;                 //得到记录数
              String[][] allQ = new String[testAmount][10];          	
              for(int i=0; i<testAmount; i++) {
            	rs.absolute(i+2);
            	String []question = new String[10];
            	question[0]=rs.getString(1);
            	question[1]=rs.getString(2);
            	question[2]=rs.getString(3);
            	question[3]=rs.getString(4);
            	question[4]=rs.getString(5);
            	question[5]=rs.getString(6);
            	question[6]=rs.getString(7);
            	question[7]=rs.getString(8);
            	question[8]=rs.getString(9);
            	question[9]=rs.getString(10);
            	for(int j=0; j<10; j++) {
            		allQ[i][j]=question[j];
            		System.out.print(allQ[i][j]+"  ");
            	}
            	System.out.println("");
              }
              testBean.setAllQuestions(allQ);
              sql2.executeUpdate("DELETE FROM test");
              break;
              
            }
          }
          
          if(notBusyTry>=16) {
        	  //jFrame.dispose();
	 	      testBean.setPleaseChooseChapter("系统繁忙，请稍后再试");
	 	      if(kemu.equals("1")) response.sendRedirect("SetKemu1Test.jsp");
	 	      else response.sendRedirect("SetKemu4Test.jsp");
	 	      return;
	      }
            
        }
        
        if(sql.executeQuery("SELECT * FROM test").next() || testBean.getAllQuestions()==null||testBean.getAllQuestions().length==0) {
        	  //jFrame.dispose();
	 	      testBean.setPleaseChooseChapter("系统繁忙，请稍后再试");
	 	      if(kemu.equals("1")) response.sendRedirect("SetKemu1Test.jsp");
	 	      else response.sendRedirect("SetKemu4Test.jsp");
	 	      return;
	    }
        String[][] allQuestions = testBean.getAllQuestions();
        
        //rs.last();    
        //int recordAmount=rs.getRow();                 //得到记录数
        int recordAmount = allQuestions.length;
        LinkedList<Integer> list=(LinkedList<Integer>)session.getAttribute("list");
        if(list==null||list.size()==0){
           list = new LinkedList<Integer>();
           for(int i=1;i<=recordAmount;i++) {
             list.add(i);
           }
           session.setAttribute("list",list); 
        }
        int m= -1;
        int index=-1;
        if (list.size()>=1) {
        /* 
           m= (int)(Math.random()*list.size());
           index=list.get(m);
           list.remove(m);
         */
           index=list.get(0);
           list.remove(0);
           
           session.setAttribute("list",list);
           int number=testBean.getNumber();
           if(number<recordAmount) {
               //首先判断上一题是否正确，给出分数：
/*           String studentAnswer=testBean.getAnswer();
             if(studentAnswer!=null&&studentAnswer.length()>=1) {
                 if(studentAnswer.equalsIgnoreCase(testBean.getCorrectAnswer())){
                    float score= testBean.getScore();
                    score+=(100.0/testAmount);
                    testBean.setScore(score);
                 }
             } 
*/                //随机抽取下一题目：
              number++;
              testBean.setNumber(number); //题号
              //rs.absolute(index); //随机抽取题目
              int index2;
              if(kemu.equals("1")) {
            	  index2=(index-1+100)%100;
              } else {
            	  index2=(index-1+50)%50;
              }
              String[] nowQuestion = allQuestions[index2];
              
              testBean.setFinishChapter("");
              String originNumberString = nowQuestion[0];
              int originNumber;
              try {
            	  originNumber = Integer.parseInt(originNumberString);
              } catch (Exception e) {
            	  originNumber=0;
            	  if(kemu.equals("1")) nowQuestion[0]="1499";
            	  else nowQuestion[0]="11499";
            	  nowQuestion[1]="两辆机动车发生轻微碰擦事故后，为保证理赔，必须等保险公司人员到场鉴定后才能撤离现场。";
            	  nowQuestion[2]="正确";
            	  nowQuestion[3]="错误";
            	  nowQuestion[4]="0";
            	  nowQuestion[5]="0";
            	  nowQuestion[6]="0";
            	  nowQuestion[7]="b";
            	  nowQuestion[8]="3";
            	  nowQuestion[9]="2";
              }              
              testBean.setOriginNumber(originNumber);
              testBean.setQuestions(nowQuestion[1]);		  //题目内容
              testBean.setChoiceA(nowQuestion[2]);            //题目的选择a
              testBean.setChoiceB(nowQuestion[3]);            //题目的选择b
              testBean.setChoiceC(nowQuestion[4]);            //题目的选择c
              testBean.setChoiceD(nowQuestion[5]);            //题目的选择d
              testBean.setImage(nowQuestion[6]);              //题目的示意图是否存在
              testBean.setCorrectAnswer(nowQuestion[7]);//题目的答案
              testBean.setDifficulty(nowQuestion[8]);		   //题目的难度
              testBean.setType(nowQuestion[9]);	   //题目的题型
              //testBean.setMess("现在是第"+number+"题，难度为"+rs.getString(9));
              con.close(); 
              if(kemu.equals("1")) {
            	  response.sendRedirect("Kemu1Test.jsp");
            	  return;
              } else {
            	  response.sendRedirect("Kemu4Test.jsp");
            	  return;
              }              
          } else {
             testBean.setMess("答题结束，单击交卷查看分数");
/*           String studentAnswer=testBean.getAnswer();         //判断最后一题
             if (studentAnswer!=null&&studentAnswer.length()>=1) {
                if (studentAnswer.equalsIgnoreCase(testBean.getCorrectAnswer())){
                   float score= testBean.getScore();
                   score+=(100.0/testAmount);
                   testBean.setScore(score);
                }
             }
*/           int oldAlreadyNumber = testBean.getAlreadyNumber();
//           testBean.setAlreadyNumber(oldAlreadyNumber+testAmount);
			 int tempSetNewAlready = oldAlreadyNumber+recordAmount;
			 testBean.setAlreadyNumber(tempSetNewAlready);
			 /*
			 con=DriverManager.getConnection(uri);
             sql=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
             if(chapter.equals("chapter1")) sql.executeUpdate("UPDATE student SET kemu1Chapter1 = " + tempSetNewAlready + " WHERE id = " + id);
             if(chapter.equals("chapter2")) sql.executeUpdate("UPDATE student SET kemu1Chapter2 = " + tempSetNewAlready + " WHERE id = " + id);
             if(chapter.equals("chapter3")) sql.executeUpdate("UPDATE student SET kemu1Chapter3 = " + tempSetNewAlready + " WHERE id = " + id);
             if(chapter.equals("chapter4")) sql.executeUpdate("UPDATE student SET kemu1Chapter4 = " + tempSetNewAlready + " WHERE id = " + id);
             */
/*             sql.executeUpdate("DELETE FROM test");
			 
             testBean.setAnswer(null);
             testBean.setNumber(0);
             testBean.setQuestions(null);
             testBean.setChoiceA(null);  
             testBean.setChoiceB(null);  
             testBean.setChoiceC(null);  
             testBean.setChoiceD(null);  
             testBean.setImage(null);
*/
             testBean.setFinishChapter("本章节已练习完毕，请点击退出");
             testBean.setPleaseChooseChapter("pleaseChooseChapter");
			 if(kemu.equals("1")) {
           	  	response.sendRedirect("Kemu1Test.jsp");
           	  	return;
             } else {
           	  	response.sendRedirect("Kemu4Test.jsp");
           	  	return;
             }
          }
       } else {
          testBean.setMess("本章节已练习完毕");
          System.out.println("abc");
          response.sendRedirect("setKemu1Test.jsp");
          return;
       }
          //response.sendRedirect("Examination.jsp");
     }
     catch(SQLException e){
         System.out.println("RandomPractiseServlet  "+e);
         testBean.setBackNews("系统异常，请重新登陆");
     	 response.sendRedirect("Index.jsp");
     	 return;
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
           out.println("<h2 align=center>"+backNews);
           out.println("<a href =Enter.jsp>返回</a></h2>");
           out.println("</body></html>");
        }
        catch(IOException exp){} 
    }
}