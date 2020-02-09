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

public class Random_Practise_Servlet extends HttpServlet{
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
      String kemu = testBean.getRandomPractiseKemu();
      String id=request.getParameter("id");
      String password=request.getParameter("password");
      //if(id.length()==0 || password.length()==0) response.sendRedirect("Index.jsp");
      
      try {
      	testBean.setId(id);       
      	Connection con;
      	Statement sql, sql2; 
      	ResultSet rsE, rsN, rsH, rsE2, rsN2, rsH2, rs1, rs2, rs3, rs4, rs5, rs6, rs7, rs;
      	
        String uri="jdbc:mysql://127.0.0.1/licence?"+"user=root&password=&characterEncoding=gb2312";
        con=DriverManager.getConnection(uri);
        sql=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
        sql2=con.createStatement();

        String tempE = testBean.getEasyPercentage();
        double easyPercentage = Double.parseDouble(tempE.substring(0, tempE.indexOf("%")));
        String tempN = testBean.getNormalPercentage();
        double normalPercentage = Double.parseDouble(tempN.substring(0, tempN.indexOf("%")));
        String tempH = testBean.getHardPercentage();
        double hardPercentage = Double.parseDouble(tempH.substring(0, tempH.indexOf("%")));
        
        //rs0=sql.executeQuery("SELECT * FROM test"); 
        //if(rs0.next()==false) {	//如果无题
        if(testBean.getAllQuestions()==null||testBean.getAllQuestions().length==0) {
         int notBusyTry;
         for(notBusyTry=0; notBusyTry<16; notBusyTry++) {	//尝试16次
          try {
           	 int sleepTime = notBusyTry*100+(int) Math.pow(2, (int)(notBusyTry*Math.random()));
           	 System.out.println(id+"  RandomPractise  "+kemu+"  "+notBusyTry+"  "+sleepTime);
           	 Thread.sleep(sleepTime); 
          } catch(InterruptedException ex) {
             System.out.println("sleepError");
          }
          rs=sql.executeQuery("SELECT * FROM test"); 
          if(rs.next()==false) {	//如果上一个人已经抽完题删除了
        	  sql.executeUpdate("INSERT INTO test VALUES('0','0','0','0','0','0','0','0','0','0')");
	          if(kemu.equals("1")){
	          	int m0=-1, index0=-1;
	          	ArrayList<String> as11=new ArrayList<String>();
	          	ArrayList<String> as12=new ArrayList<String>();
	          	ArrayList<String> as13=new ArrayList<String>();
	          	ArrayList<String> as14=new ArrayList<String>();
	          	ArrayList<String> as15=new ArrayList<String>();
	          	ArrayList<String> as16=new ArrayList<String>();
	          	ArrayList<String> as17=new ArrayList<String>();
	          	ArrayList<String> as18=new ArrayList<String>();
	          	ArrayList<String> as19=new ArrayList<String>();
	          	ArrayList<String> as1A=new ArrayList<String>();
	          	rs1 = sql.executeQuery("SELECT * FROM chapter1");
	            rs1.last();
	            int recordAmount = rs1.getRow();
	            for(int i=1; i<=recordAmount; i++) {
	            	rs1.absolute(i);
	            	as11.add(rs1.getString(1).trim());//题号
	            	as12.add(rs1.getString(2).trim());//题目
	            	as13.add(rs1.getString(3).trim());//a
	            	as14.add(rs1.getString(4).trim());//b
	            	as15.add(rs1.getString(5).trim());//c
	            	as16.add(rs1.getString(6).trim());//d
	            	as17.add(rs1.getString(7).trim());//pic
	            	as18.add(rs1.getString(8).trim());//答案
	            	as19.add(rs1.getString(9).trim());//difficulty
	            	as1A.add(rs1.getString(10).trim());//type 
	            }
	            for (int i = 0; i < recordAmount; i++) {
	            	String execute = "INSERT INTO test VALUES('";
	            	execute+=as11.get(i)+"','";
	            	execute+=as12.get(i)+"','";
	            	execute+=as13.get(i)+"','";
	            	execute+=as14.get(i)+"','";
	            	execute+=as15.get(i)+"','";
	            	execute+=as16.get(i)+"','";
	            	execute+=as17.get(i)+"','";
	            	execute+=as18.get(i)+"','";
	            	execute+=as19.get(i)+"','";
	            	execute+=as1A.get(i)+"')";
	            	sql.executeUpdate(execute);
	    		}
	            
	            ArrayList<String> as21=new ArrayList<String>();
	          	ArrayList<String> as22=new ArrayList<String>();
	          	ArrayList<String> as23=new ArrayList<String>();
	          	ArrayList<String> as24=new ArrayList<String>();
	          	ArrayList<String> as25=new ArrayList<String>();
	          	ArrayList<String> as26=new ArrayList<String>();
	          	ArrayList<String> as27=new ArrayList<String>();
	          	ArrayList<String> as28=new ArrayList<String>();
	          	ArrayList<String> as29=new ArrayList<String>();
	          	ArrayList<String> as2A=new ArrayList<String>();
	          	rs2 = sql.executeQuery("SELECT * FROM chapter2");
	            rs2.last();
	            recordAmount = rs2.getRow();
	            for(int i=1; i<=recordAmount; i++) {
	            	rs2.absolute(i);
	            	as21.add(rs2.getString(1).trim());//题号
	            	as22.add(rs2.getString(2).trim());//题目
	            	as23.add(rs2.getString(3).trim());//a
	            	as24.add(rs2.getString(4).trim());//b
	            	as25.add(rs2.getString(5).trim());//c
	            	as26.add(rs2.getString(6).trim());//d
	            	as27.add(rs2.getString(7).trim());//pic
	            	as28.add(rs2.getString(8).trim());//答案
	            	as29.add(rs2.getString(9).trim());//difficulty
	            	as2A.add(rs2.getString(10).trim());//type 
	            }
	            for (int i = 0; i < recordAmount; i++) {
	            	String execute = "INSERT INTO test VALUES('";
	            	execute+=as21.get(i)+"','";
	            	execute+=as22.get(i)+"','";
	            	execute+=as23.get(i)+"','";
	            	execute+=as24.get(i)+"','";
	            	execute+=as25.get(i)+"','";
	            	execute+=as26.get(i)+"','";
	            	execute+=as27.get(i)+"','";
	            	execute+=as28.get(i)+"','";
	            	execute+=as29.get(i)+"','";
	            	execute+=as2A.get(i)+"')";
	            	sql.executeUpdate(execute);
	    		}
	            
	            ArrayList<String> as31=new ArrayList<String>();
	          	ArrayList<String> as32=new ArrayList<String>();
	          	ArrayList<String> as33=new ArrayList<String>();
	          	ArrayList<String> as34=new ArrayList<String>();
	          	ArrayList<String> as35=new ArrayList<String>();
	          	ArrayList<String> as36=new ArrayList<String>();
	          	ArrayList<String> as37=new ArrayList<String>();
	          	ArrayList<String> as38=new ArrayList<String>();
	          	ArrayList<String> as39=new ArrayList<String>();
	          	ArrayList<String> as3A=new ArrayList<String>();
	          	rs3 = sql.executeQuery("SELECT * FROM chapter3");
	            rs3.last();
	            recordAmount = rs3.getRow();
	            for(int i=1; i<=recordAmount; i++) {
	            	rs3.absolute(i);
	            	as31.add(rs3.getString(1).trim());//题号
	            	as32.add(rs3.getString(2).trim());//题目
	            	as33.add(rs3.getString(3).trim());//a
	            	as34.add(rs3.getString(4).trim());//b
	            	as35.add(rs3.getString(5).trim());//c
	            	as36.add(rs3.getString(6).trim());//d
	            	as37.add(rs3.getString(7).trim());//pic
	            	as38.add(rs3.getString(8).trim());//答案
	            	as39.add(rs3.getString(9).trim());//difficulty
	            	as3A.add(rs3.getString(10).trim());//type 
	            }
	            for (int i = 0; i < recordAmount; i++) {
	            	String execute = "INSERT INTO test VALUES('";
	            	execute+=as31.get(i)+"','";
	            	execute+=as32.get(i)+"','";
	            	execute+=as33.get(i)+"','";
	            	execute+=as34.get(i)+"','";
	            	execute+=as35.get(i)+"','";
	            	execute+=as36.get(i)+"','";
	            	execute+=as37.get(i)+"','";
	            	execute+=as38.get(i)+"','";
	            	execute+=as39.get(i)+"','";
	            	execute+=as3A.get(i)+"')";
	            	sql.executeUpdate(execute);
	    		}
	            
	            ArrayList<String> as41=new ArrayList<String>();
	          	ArrayList<String> as42=new ArrayList<String>();
	          	ArrayList<String> as43=new ArrayList<String>();
	          	ArrayList<String> as44=new ArrayList<String>();
	          	ArrayList<String> as45=new ArrayList<String>();
	          	ArrayList<String> as46=new ArrayList<String>();
	          	ArrayList<String> as47=new ArrayList<String>();
	          	ArrayList<String> as48=new ArrayList<String>();
	          	ArrayList<String> as49=new ArrayList<String>();
	          	ArrayList<String> as4A=new ArrayList<String>();
	          	rs4 = sql.executeQuery("SELECT * FROM chapter4");
	            rs4.last();
	            recordAmount = rs4.getRow();
	            for(int i=1; i<=recordAmount; i++) {
	            	rs4.absolute(i);
	            	as41.add(rs4.getString(1).trim());//题号
	            	as42.add(rs4.getString(2).trim());//题目
	            	as43.add(rs4.getString(3).trim());//a
	            	as44.add(rs4.getString(4).trim());//b
	            	as45.add(rs4.getString(5).trim());//c
	            	as46.add(rs4.getString(6).trim());//d
	            	as47.add(rs4.getString(7).trim());//pic
	            	as48.add(rs4.getString(8).trim());//答案
	            	as49.add(rs4.getString(9).trim());//difficulty
	            	as4A.add(rs4.getString(10).trim());//type 
	            }
	            for (int i = 0; i < recordAmount; i++) {
	            	String execute = "INSERT INTO test VALUES('";
	            	execute+=as41.get(i)+"','";
	            	execute+=as42.get(i)+"','";
	            	execute+=as43.get(i)+"','";
	            	execute+=as44.get(i)+"','";
	            	execute+=as45.get(i)+"','";
	            	execute+=as46.get(i)+"','";
	            	execute+=as47.get(i)+"','";
	            	execute+=as48.get(i)+"','";
	            	execute+=as49.get(i)+"','";
	            	execute+=as4A.get(i)+"')";
	            	sql.executeUpdate(execute);
	    		}
	          } else {	//kemu4
	        	int m0=-1, index0=-1;
	            ArrayList<String> as11=new ArrayList<String>();
	            ArrayList<String> as12=new ArrayList<String>();
	            ArrayList<String> as13=new ArrayList<String>();
	            ArrayList<String> as14=new ArrayList<String>();
	            ArrayList<String> as15=new ArrayList<String>();
	            ArrayList<String> as16=new ArrayList<String>();
	            ArrayList<String> as17=new ArrayList<String>();
	            ArrayList<String> as18=new ArrayList<String>();
	            ArrayList<String> as19=new ArrayList<String>();
	            ArrayList<String> as1A=new ArrayList<String>();
	            rs1 = sql.executeQuery("SELECT * FROM chapter11");
	            rs1.last();
	            int recordAmount = rs1.getRow();
	            for(int i=1; i<=recordAmount; i++) {
	              	rs1.absolute(i);
	              	as11.add(rs1.getString(1).trim());//题号
	              	as12.add(rs1.getString(2).trim());//题目
	              	as13.add(rs1.getString(3).trim());//a
	              	as14.add(rs1.getString(4).trim());//b
	              	as15.add(rs1.getString(5).trim());//c
	              	as16.add(rs1.getString(6).trim());//d
	              	as17.add(rs1.getString(7).trim());//pic
	              	as18.add(rs1.getString(8).trim());//答案
	              	as19.add(rs1.getString(9).trim());//difficulty
	              	as1A.add(rs1.getString(10).trim());//type 
	            }
	            for (int i = 0; i < recordAmount; i++) {
	            	String execute = "INSERT INTO test VALUES('";
	            	execute+=as11.get(i)+"','";
	            	execute+=as12.get(i)+"','";
	            	execute+=as13.get(i)+"','";
	            	execute+=as14.get(i)+"','";
	            	execute+=as15.get(i)+"','";
	            	execute+=as16.get(i)+"','";
	            	execute+=as17.get(i)+"','";
	            	execute+=as18.get(i)+"','";
	            	execute+=as19.get(i)+"','";
	            	execute+=as1A.get(i)+"')";
	            	sql.executeUpdate(execute);
	      		}
	              
	            ArrayList<String> as21=new ArrayList<String>();
	            ArrayList<String> as22=new ArrayList<String>();
	            ArrayList<String> as23=new ArrayList<String>();
	            ArrayList<String> as24=new ArrayList<String>();
	            ArrayList<String> as25=new ArrayList<String>();
	            ArrayList<String> as26=new ArrayList<String>();
	            ArrayList<String> as27=new ArrayList<String>();
	            ArrayList<String> as28=new ArrayList<String>();
	            ArrayList<String> as29=new ArrayList<String>();
	            ArrayList<String> as2A=new ArrayList<String>();
	            rs2 = sql.executeQuery("SELECT * FROM chapter12");
	            rs2.last();
	            recordAmount = rs2.getRow();
	            for(int i=1; i<=recordAmount; i++) {
	              	rs2.absolute(i);
	              	as21.add(rs2.getString(1).trim());//题号
	              	as22.add(rs2.getString(2).trim());//题目
	              	as23.add(rs2.getString(3).trim());//a
	              	as24.add(rs2.getString(4).trim());//b
	              	as25.add(rs2.getString(5).trim());//c
	              	as26.add(rs2.getString(6).trim());//d
	              	as27.add(rs2.getString(7).trim());//pic
	              	as28.add(rs2.getString(8).trim());//答案
	              	as29.add(rs2.getString(9).trim());//difficulty
	              	as2A.add(rs2.getString(10).trim());//type 
	            }
	            for (int i = 0; i < recordAmount; i++) {
	            	String execute = "INSERT INTO test VALUES('";
	            	execute+=as21.get(i)+"','";
	            	execute+=as22.get(i)+"','";
	            	execute+=as23.get(i)+"','";
	            	execute+=as24.get(i)+"','";
	            	execute+=as25.get(i)+"','";
	            	execute+=as26.get(i)+"','";
	            	execute+=as27.get(i)+"','";
	            	execute+=as28.get(i)+"','";
	            	execute+=as29.get(i)+"','";
	            	execute+=as2A.get(i)+"')";
	            	sql.executeUpdate(execute);
	      		}
	              
	            ArrayList<String> as31=new ArrayList<String>();
	            ArrayList<String> as32=new ArrayList<String>();
	            ArrayList<String> as33=new ArrayList<String>();
	            ArrayList<String> as34=new ArrayList<String>();
	            ArrayList<String> as35=new ArrayList<String>();
	            ArrayList<String> as36=new ArrayList<String>();
	            ArrayList<String> as37=new ArrayList<String>();
	            ArrayList<String> as38=new ArrayList<String>();
	            ArrayList<String> as39=new ArrayList<String>();
	            ArrayList<String> as3A=new ArrayList<String>();
	            rs3 = sql.executeQuery("SELECT * FROM chapter13");
	            rs3.last();
	            recordAmount = rs3.getRow();
	            for(int i=1; i<=recordAmount; i++) {
	              	rs3.absolute(i);
	              	as31.add(rs3.getString(1).trim());//题号
	              	as32.add(rs3.getString(2).trim());//题目
	              	as33.add(rs3.getString(3).trim());//a
	              	as34.add(rs3.getString(4).trim());//b
	              	as35.add(rs3.getString(5).trim());//c
	              	as36.add(rs3.getString(6).trim());//d
	              	as37.add(rs3.getString(7).trim());//pic
	              	as38.add(rs3.getString(8).trim());//答案
	              	as39.add(rs3.getString(9).trim());//difficulty
	              	as3A.add(rs3.getString(10).trim());//type 
	            }
	            for (int i = 0; i < recordAmount; i++) {
	            	String execute = "INSERT INTO test VALUES('";
	            	execute+=as31.get(i)+"','";
	            	execute+=as32.get(i)+"','";
	            	execute+=as33.get(i)+"','";
	            	execute+=as34.get(i)+"','";
	            	execute+=as35.get(i)+"','";
	            	execute+=as36.get(i)+"','";
	            	execute+=as37.get(i)+"','";
	            	execute+=as38.get(i)+"','";
	            	execute+=as39.get(i)+"','";
	            	execute+=as3A.get(i)+"')";
	            	sql.executeUpdate(execute);
	            }
	              
	            ArrayList<String> as41=new ArrayList<String>();
	            ArrayList<String> as42=new ArrayList<String>();
	            ArrayList<String> as43=new ArrayList<String>();
	            ArrayList<String> as44=new ArrayList<String>();
	            ArrayList<String> as45=new ArrayList<String>();
	            ArrayList<String> as46=new ArrayList<String>();
	            ArrayList<String> as47=new ArrayList<String>();
	            ArrayList<String> as48=new ArrayList<String>();
	            ArrayList<String> as49=new ArrayList<String>();
	            ArrayList<String> as4A=new ArrayList<String>();
	            rs4 = sql.executeQuery("SELECT * FROM chapter14");
	            rs4.last();
	            recordAmount = rs4.getRow();
	            for(int i=1; i<=recordAmount; i++) {
	              	rs4.absolute(i);
	              	as41.add(rs4.getString(1).trim());//题号
	              	as42.add(rs4.getString(2).trim());//题目
	              	as43.add(rs4.getString(3).trim());//a
	              	as44.add(rs4.getString(4).trim());//b
	              	as45.add(rs4.getString(5).trim());//c
	              	as46.add(rs4.getString(6).trim());//d
	              	as47.add(rs4.getString(7).trim());//pic
	              	as48.add(rs4.getString(8).trim());//答案
	              	as49.add(rs4.getString(9).trim());//difficulty
	              	as4A.add(rs4.getString(10).trim());//type 
	            }
	            for (int i = 0; i < recordAmount; i++) {
	            	String execute = "INSERT INTO test VALUES('";
	            	execute+=as41.get(i)+"','";
	            	execute+=as42.get(i)+"','";
	            	execute+=as43.get(i)+"','";
	            	execute+=as44.get(i)+"','";
	            	execute+=as45.get(i)+"','";
	            	execute+=as46.get(i)+"','";
	            	execute+=as47.get(i)+"','";
	            	execute+=as48.get(i)+"','";
	            	execute+=as49.get(i)+"','";
	            	execute+=as4A.get(i)+"')";
	            	sql.executeUpdate(execute);
	            }
	            
	            ArrayList<String> as51=new ArrayList<String>();
	            ArrayList<String> as52=new ArrayList<String>();
	            ArrayList<String> as53=new ArrayList<String>();
	            ArrayList<String> as54=new ArrayList<String>();
	            ArrayList<String> as55=new ArrayList<String>();
	            ArrayList<String> as56=new ArrayList<String>();
	            ArrayList<String> as57=new ArrayList<String>();
	            ArrayList<String> as58=new ArrayList<String>();
	            ArrayList<String> as59=new ArrayList<String>();
	            ArrayList<String> as5A=new ArrayList<String>();
	            rs5 = sql.executeQuery("SELECT * FROM chapter15");
	            rs5.last();
	            recordAmount = rs5.getRow();
	            for(int i=1; i<=recordAmount; i++) {
	              	rs5.absolute(i);
	              	as51.add(rs5.getString(1).trim());//题号
	              	as52.add(rs5.getString(2).trim());//题目
	              	as53.add(rs5.getString(3).trim());//a
	              	as54.add(rs5.getString(4).trim());//b
	              	as55.add(rs5.getString(5).trim());//c
	              	as56.add(rs5.getString(6).trim());//d
	              	as57.add(rs5.getString(7).trim());//pic
	              	as58.add(rs5.getString(8).trim());//答案
	              	as59.add(rs5.getString(9).trim());//difficulty
	              	as5A.add(rs5.getString(10).trim());//type 
	            }
	            for (int i = 0; i < recordAmount; i++) {
	            	String execute = "INSERT INTO test VALUES('";
	            	execute+=as51.get(i)+"','";
	            	execute+=as52.get(i)+"','";
	            	execute+=as53.get(i)+"','";
	            	execute+=as54.get(i)+"','";
	            	execute+=as55.get(i)+"','";
	            	execute+=as56.get(i)+"','";
	            	execute+=as57.get(i)+"','";
	            	execute+=as58.get(i)+"','";
	            	execute+=as59.get(i)+"','";
	            	execute+=as5A.get(i)+"')";
	            	sql.executeUpdate(execute);
	            }
	            
	            ArrayList<String> as61=new ArrayList<String>();
	            ArrayList<String> as62=new ArrayList<String>();
	            ArrayList<String> as63=new ArrayList<String>();
	            ArrayList<String> as64=new ArrayList<String>();
	            ArrayList<String> as65=new ArrayList<String>();
	            ArrayList<String> as66=new ArrayList<String>();
	            ArrayList<String> as67=new ArrayList<String>();
	            ArrayList<String> as68=new ArrayList<String>();
	            ArrayList<String> as69=new ArrayList<String>();
	            ArrayList<String> as6A=new ArrayList<String>();
	            rs6 = sql.executeQuery("SELECT * FROM chapter16");
	            rs6.last();
	            recordAmount = rs6.getRow();
	            for(int i=1; i<=recordAmount; i++) {
	              	rs6.absolute(i);
	              	as61.add(rs6.getString(1).trim());//题号
	              	as62.add(rs6.getString(2).trim());//题目
	              	as63.add(rs6.getString(3).trim());//a
	              	as64.add(rs6.getString(4).trim());//b
	              	as65.add(rs6.getString(5).trim());//c
	              	as66.add(rs6.getString(6).trim());//d
	              	as67.add(rs6.getString(7).trim());//pic
	              	as68.add(rs6.getString(8).trim());//答案
	              	as69.add(rs6.getString(9).trim());//difficulty
	              	as6A.add(rs6.getString(10).trim());//type 
	            }
	            for (int i = 0; i < recordAmount; i++) {
	            	String execute = "INSERT INTO test VALUES('";
	            	execute+=as61.get(i)+"','";
	            	execute+=as62.get(i)+"','";
	            	execute+=as63.get(i)+"','";
	            	execute+=as64.get(i)+"','";
	            	execute+=as65.get(i)+"','";
	            	execute+=as66.get(i)+"','";
	            	execute+=as67.get(i)+"','";
	            	execute+=as68.get(i)+"','";
	            	execute+=as69.get(i)+"','";
	            	execute+=as6A.get(i)+"')";
	            	sql.executeUpdate(execute);
	            }
	            
	            ArrayList<String> as71=new ArrayList<String>();
	            ArrayList<String> as72=new ArrayList<String>();
	            ArrayList<String> as73=new ArrayList<String>();
	            ArrayList<String> as74=new ArrayList<String>();
	            ArrayList<String> as75=new ArrayList<String>();
	            ArrayList<String> as76=new ArrayList<String>();
	            ArrayList<String> as77=new ArrayList<String>();
	            ArrayList<String> as78=new ArrayList<String>();
	            ArrayList<String> as79=new ArrayList<String>();
	            ArrayList<String> as7A=new ArrayList<String>();
	            rs7 = sql.executeQuery("SELECT * FROM chapter17");
	            rs7.last();
	            recordAmount = rs7.getRow();
	            for(int i=1; i<=recordAmount; i++) {
	              	rs7.absolute(i);
	              	as71.add(rs7.getString(1).trim());//题号
	              	as72.add(rs7.getString(2).trim());//题目
	              	as73.add(rs7.getString(3).trim());//a
	              	as74.add(rs7.getString(4).trim());//b
	              	as75.add(rs7.getString(5).trim());//c
	              	as76.add(rs7.getString(6).trim());//d
	              	as77.add(rs7.getString(7).trim());//pic
	              	as78.add(rs7.getString(8).trim());//答案
	              	as79.add(rs7.getString(9).trim());//difficulty
	              	as7A.add(rs7.getString(10).trim());//type 
	            }
	            for (int i = 0; i < recordAmount; i++) {
	            	String execute = "INSERT INTO test VALUES('";
	            	execute+=as71.get(i)+"','";
	            	execute+=as72.get(i)+"','";
	            	execute+=as73.get(i)+"','";
	            	execute+=as74.get(i)+"','";
	            	execute+=as75.get(i)+"','";
	            	execute+=as76.get(i)+"','";
	            	execute+=as77.get(i)+"','";
	            	execute+=as78.get(i)+"','";
	            	execute+=as79.get(i)+"','";
	            	execute+=as7A.get(i)+"')";
	            	sql.executeUpdate(execute);
	            }
	          }
          
	          rs=sql.executeQuery("SELECT * FROM test");
	          rs.last();    
	          int testAmount=rs.getRow();                 //得到记录数
	          String[][] allQ = new String[testAmount][10];          	
	          for(int i=0; i<testAmount; i++) {
	          	rs.absolute(i+1);
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
	          	}
	          }
	          testBean.setAllQuestions(allQ);
	          
	          rsE=sql.executeQuery("SELECT * FROM test WHERE difficulty = 1");
	          rsE.last();    
	          int easyAmount = rsE.getRow();	//简单题总数
	          String[][] allQE = new String[easyAmount][10];          	
	          for(int i=0; i<easyAmount; i++) {
	          	rsE.absolute(i+1);
		        String []question = new String[10];
		        question[0]=rsE.getString(1);
		        question[1]=rsE.getString(2);
		        question[2]=rsE.getString(3);
		        question[3]=rsE.getString(4);
		        question[4]=rsE.getString(5);
		        question[5]=rsE.getString(6);
		        question[6]=rsE.getString(7);
		        question[7]=rsE.getString(8);
		        question[8]=rsE.getString(9);
		        question[9]=rsE.getString(10);
	          	for(int j=0; j<10; j++) {
	          		allQE[i][j]=question[j];
	          	}
	          }
	          testBean.setAllQuestionsE(allQE);
	          
	          rsN=sql.executeQuery("SELECT * FROM test WHERE difficulty = 2");
	          rsN.last();   
	          int normalAmount = rsN.getRow();	//中等题总数
	          String[][] allQN = new String[normalAmount][10];          	
	          for(int i=0; i<normalAmount; i++) {
	          	rsN.absolute(i+1);
		        String []question = new String[10];
		        question[0]=rsN.getString(1);
		        question[1]=rsN.getString(2);
		        question[2]=rsN.getString(3);
		        question[3]=rsN.getString(4);
		        question[4]=rsN.getString(5);
		        question[5]=rsN.getString(6);
		        question[6]=rsN.getString(7);
		        question[7]=rsN.getString(8);
		        question[8]=rsN.getString(9);
		        question[9]=rsN.getString(10);
	          	for(int j=0; j<10; j++) {
	          		allQN[i][j]=question[j];
	          	}
	          }
	          testBean.setAllQuestionsN(allQN);
	          
	          rsH=sql.executeQuery("SELECT * FROM test WHERE difficulty = 3");
	          rsH.last();   
	          int hardAmount = rsH.getRow();	//较难题总数
	          String[][] allQH = new String[hardAmount][10];          	
	          for(int i=0; i<hardAmount; i++) {
	          	rsH.absolute(i+1);
		        String []question = new String[10];
		        question[0]=rsH.getString(1);
		        question[1]=rsH.getString(2);
		        question[2]=rsH.getString(3);
		        question[3]=rsH.getString(4);
		        question[4]=rsH.getString(5);
		        question[5]=rsH.getString(6);
		        question[6]=rsH.getString(7);
		        question[7]=rsH.getString(8);
		        question[8]=rsH.getString(9);
		        question[9]=rsH.getString(10);
	          	for(int j=0; j<10; j++) {
	          		allQH[i][j]=question[j];
	          	}
	          }
	          testBean.setAllQuestionsH(allQH);
	          
	          sql2.executeUpdate("DELETE FROM test");
	          break;
           }
         }   
         if(notBusyTry>=16) {
	 	      testBean.setPleaseChooseChapter("系统繁忙，请稍后再试");
	 	      if(kemu.equals("1")) response.sendRedirect("SetKemu1RandomPractise.jsp");
	 	      else response.sendRedirect("SetKemu4RandomPractise.jsp");
	 	      return;
	      }
        }
        
        String[][] allQuestions = testBean.getAllQuestions();
        String[][] allQuestionsE = testBean.getAllQuestionsE();
        String[][] allQuestionsN = testBean.getAllQuestionsN();
        String[][] allQuestionsH = testBean.getAllQuestionsH();
        int recordAmount = allQuestions.length;
        int recordEasyAmount = allQuestionsE.length;
        int recordNormalAmount = allQuestionsN.length;
        int recordHardAmount = allQuestionsH.length;
        
        //删除：testAmount = Math.min(recordAmount,testAmount);
        LinkedList<Integer> listE=(LinkedList<Integer>)session.getAttribute("listE");
        if(listE==null||listE.size()==0){
           listE = new LinkedList<Integer>();
           for(int i=1;i<=recordEasyAmount;i++) {
             listE.add(i);
           }
           session.setAttribute("listE",listE); 
        }
        LinkedList<Integer> listN=(LinkedList<Integer>)session.getAttribute("listN");
        if(listN==null||listN.size()==0){
           listN = new LinkedList<Integer>();
           for(int i=1;i<=recordNormalAmount;i++) {
             listN.add(i);
           }
           session.setAttribute("listN",listN); 
        }
        LinkedList<Integer> listH=(LinkedList<Integer>)session.getAttribute("listH");
        if(listH==null||listH.size()==0){
           listH = new LinkedList<Integer>();
           for(int i=1;i<=recordHardAmount;i++) {
             listH.add(i);
           }
           session.setAttribute("listH",listH); 
        }
        
        int number=testBean.getNumber();
        number++;
        testBean.setNumber(number); //题号
        int m= -1;
        int index=-1;
        double randNum = Math.random()*100;
        
        if(randNum>=0 && randNum<easyPercentage) {	//如果抽到简单题
        	if(listE.size()>=1) {	//如果为0在前面初始化的时候会重新初始化 这样设计也好 不会出bug
        		m= (int)(Math.random()*listE.size());
                index=listE.get(m);
                listE.remove(m);
                session.setAttribute("listE",listE);
                //rsE2=sql.executeQuery("SELECT * FROM test WHERE difficulty = 1");
                //rsE2.absolute(index);
                String[] nowQuestion = allQuestionsE[index-1];
                
                testBean.setFinishChapter("");	//或许可以改成finishThisDifficulty
                /*
                testBean.setOriginNumber(rsE2.getInt(1));
                testBean.setQuestions(rsE2.getString(2));          //题目内容
                testBean.setChoiceA(rsE2.getString(3));            //题目的选择a
                testBean.setChoiceB(rsE2.getString(4));            //题目的选择b
                testBean.setChoiceC(rsE2.getString(5));            //题目的选择c
                testBean.setChoiceD(rsE2.getString(6));            //题目的选择d
                testBean.setImage(rsE2.getString(7));              //题目的示意图是否存在
                testBean.setCorrectAnswer(rsE2.getString(8).trim());//题目的答案
                testBean.setDifficulty(rsE2.getString(9));		   //题目的难度
                testBean.setType(rsE2.getString(10).trim());	   //题目的题型
                testBean.setMess("现在是第"+number+"题，难度为"+rsE2.getString(9));
                */
                testBean.setOriginNumber(Integer.parseInt(nowQuestion[0]));
                testBean.setQuestions(nowQuestion[1]);		    //题目内容
                testBean.setChoiceA(nowQuestion[2]);            //题目的选择a
                testBean.setChoiceB(nowQuestion[3]);            //题目的选择b
                testBean.setChoiceC(nowQuestion[4]);            //题目的选择c
                testBean.setChoiceD(nowQuestion[5]);            //题目的选择d
                testBean.setImage(nowQuestion[6]);              //题目的示意图是否存在
                testBean.setCorrectAnswer(nowQuestion[7]);		//题目的答案
                testBean.setDifficulty(nowQuestion[8]);		    //题目的难度
                testBean.setType(nowQuestion[9]);	   			//题目的题型
                con.close(); 
                //jFrame.dispose();
                response.sendRedirect("RandomPractise.jsp");
                return;
        	} else {
        		testBean.setPleaseChooseChapter("随机抽到的简单难度已没有题目了");
        		//jFrame.dispose();
                response.sendRedirect("SetKemu1RandomPractise.jsp");
                return;
        	}
        	
        } else if(randNum>=easyPercentage && randNum<easyPercentage+normalPercentage) {	//中等题
        	if(listN.size()>=1) {
        		m= (int)(Math.random()*listN.size());
                index=listN.get(m);
                listN.remove(m);
                session.setAttribute("listN",listN);
                String[] nowQuestion = allQuestionsN[index-1];
                
                testBean.setFinishChapter("");	//或许可以改成finishThisDifficulty
                testBean.setOriginNumber(Integer.parseInt(nowQuestion[0]));
                testBean.setQuestions(nowQuestion[1]);		    //题目内容
                testBean.setChoiceA(nowQuestion[2]);            //题目的选择a
                testBean.setChoiceB(nowQuestion[3]);            //题目的选择b
                testBean.setChoiceC(nowQuestion[4]);            //题目的选择c
                testBean.setChoiceD(nowQuestion[5]);            //题目的选择d
                testBean.setImage(nowQuestion[6]);              //题目的示意图是否存在
                testBean.setCorrectAnswer(nowQuestion[7]);		//题目的答案
                testBean.setDifficulty(nowQuestion[8]);		    //题目的难度
                testBean.setType(nowQuestion[9]);	   			//题目的题型
                con.close(); 
                response.sendRedirect("RandomPractise.jsp");
                return;
        	} else {
        		testBean.setPleaseChooseChapter("随机抽到的中等难度已没有题目了");
        	    //jFrame.dispose();
                response.sendRedirect("SetKemu1RandomPractise.jsp");
                return;
        	}
        	
        } else{// if(randNum>=easyPercentage+normalPercentage && randNum<easyPercentage+normalPercentage+hardPercentage) {	//难题
        	if(listH.size()>=1) {
        		m= (int)(Math.random()*listH.size());
                index=listH.get(m);
                listH.remove(m);
                session.setAttribute("listH",listH);
                String[] nowQuestion = allQuestionsH[index-1];
                
                testBean.setFinishChapter("");	//或许可以改成finishThisDifficulty
                testBean.setOriginNumber(Integer.parseInt(nowQuestion[0]));
                testBean.setQuestions(nowQuestion[1]);		    //题目内容
                testBean.setChoiceA(nowQuestion[2]);            //题目的选择a
                testBean.setChoiceB(nowQuestion[3]);            //题目的选择b
                testBean.setChoiceC(nowQuestion[4]);            //题目的选择c
                testBean.setChoiceD(nowQuestion[5]);            //题目的选择d
                testBean.setImage(nowQuestion[6]);              //题目的示意图是否存在
                testBean.setCorrectAnswer(nowQuestion[7]);		//题目的答案
                testBean.setDifficulty(nowQuestion[8]);		    //题目的难度
                testBean.setType(nowQuestion[9]);	   			//题目的题型
                con.close(); 
                response.sendRedirect("RandomPractise.jsp");
                return;
        	} else {
        		testBean.setPleaseChooseChapter("随机抽到的较难难度已没有题目了");
        	    //jFrame.dispose();
                response.sendRedirect("SetKemu1RandomPractise.jsp");
                return;
        	}
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