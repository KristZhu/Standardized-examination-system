package myservlet.control;

import mybean.data.Test_Bean; //引入例子2中的Javabean模型
import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.Calendar;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Add_Questions_Servlet extends HttpServlet{
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
     try{  Class.forName("com.mysql.jdbc.Driver");
     } catch(Exception e){} 
     
     boolean valid = true;
     
     request.setCharacterEncoding("utf8");
     
     String id = request.getParameter("id");
     testBean.setId(id);
     
     String dataBase = request.getParameter("dataBase");
     String tableName = request.getParameter("chapter");
     String content=request.getParameter("content");
     String choiceA=request.getParameter("choiceA");     
     String choiceB=request.getParameter("choiceB");
     String choiceC=request.getParameter("choiceC");
     String choiceD=request.getParameter("choiceD");
     String pic=request.getParameter("pic");
     String difficulty=request.getParameter("difficulty");
     String type=request.getParameter("type");
     
     String kemu = Integer.parseInt(tableName.substring(7))<10?"1":"4";
     String failGoTo = "AddKemu"+kemu;
     if(type.equals("3")) failGoTo=failGoTo+"Checkbox.jsp";
     else if(type.equals("2")) failGoTo=failGoTo+"Judge.jsp";
     else failGoTo=failGoTo+"MultipleChoice.jsp";
     
     String answer="";
     if(!type.equals("3")) {
         answer=request.getParameter("answer");
     } else {
    	 String ans[]=request.getParameterValues("answer");
    	 try {
    		 if(ans.length<=1){
        		 testBean.setAddFailMess("答案数量过少，添加失败");
        		 valid=false;
            	 RequestDispatcher dispatcher = request.getRequestDispatcher(failGoTo);
            	 dispatcher.forward(request, response);
        	 } else {
        		 answer=ans[0];
        		 for(int i=1; i<ans.length; i++) {
        			 answer=answer+","+ans[i];
        		 }
        	 }
		 } catch (Exception e) {
			 testBean.setAddFailMess("答案为空，添加失败");
    		 valid=false;
        	 RequestDispatcher dispatcher = request.getRequestDispatcher(failGoTo);
        	 dispatcher.forward(request, response);
		 }
     }
     
     if(content==null||content.length()==0) {
    	 testBean.setAddFailMess("内容为空，添加失败");
    	 valid=false;
    	 RequestDispatcher dispatcher = request.getRequestDispatcher(failGoTo);
    	 dispatcher.forward(request, response);
     } else if(choiceA==null||choiceA.length()==0) {
    	 testBean.setAddFailMess("A选项为空，添加失败");
    	 valid=false;
    	 RequestDispatcher dispatcher = request.getRequestDispatcher(failGoTo);
    	 dispatcher.forward(request, response);
     } else if(choiceB==null||choiceB.length()==0) {
    	 testBean.setAddFailMess("B选项为空，添加失败");
    	 valid=false;
    	 RequestDispatcher dispatcher = request.getRequestDispatcher(failGoTo);
    	 dispatcher.forward(request, response);
     } else if((choiceC==null||choiceC.length()==0)&&(!type.equals("2"))) {
    	 testBean.setAddFailMess("C选项为空，添加失败");
    	 valid=false;
    	 RequestDispatcher dispatcher = request.getRequestDispatcher(failGoTo);
    	 dispatcher.forward(request, response);
     } else if((choiceD==null||choiceD.length()==0)&&(!type.equals("2"))) {
    	 testBean.setAddFailMess("D选项为空，添加失败");
    	 valid=false;
    	 RequestDispatcher dispatcher = request.getRequestDispatcher(failGoTo);
    	 dispatcher.forward(request, response);
     } else {
     }
     
     if(valid) {
	     if((choiceC==null||choiceC.length()==0)&&type.equals("2")) choiceC="0";     
	     if((choiceD==null||choiceD.length()==0)&&type.equals("2")) choiceD="0";
	     
	     Connection con;
	     Statement sql; 
	     ResultSet rs;
	     try{ 
	          String uri="jdbc:mysql://127.0.0.1/"+dataBase+"?"+"user=root&password=&characterEncoding=gb2312";
	          con=DriverManager.getConnection(uri);
	          sql=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
	          rs=sql.executeQuery("SELECT * FROM "+tableName);
	          rs.last();
	          String number = rs.getInt(1)+1+"";
	          testBean.setNewQuestionNumber(number);
	          
	          /*
	          rs=sql.executeQuery("SELECT * FROM "+tableName);
	          ResultSetMetaData metaData = rs.getMetaData();
	          int columnCount = metaData.getColumnCount();       //得到结果集的列数
	          String []columnName = new String[columnCount];
	          for(int i=0;i<columnName.length;i++) {
	             columnName[i] = metaData.getColumnName(i+1);    //得到列名
	          }
	          resultBean.setColumnName(columnName);             //更新Javabean数据模型
	          rs.last();
	          int rowNumber=rs.getRow();                        //得到记录数
	          String [][] tableRecord=resultBean.getTableRecord();
	          tableRecord = new String[rowNumber][columnCount];
	          rs.beforeFirst();
	          int i=0;
	          while(rs.next()){
	            for(int k=0;k<columnCount;k++) 
	              tableRecord[i][k] = rs.getString(k+1);
	              i++; 
	          }
	          resultBean.setTableRecord(tableRecord);             //更新Javabean数据模型
	          */
	          
	          //testBean.setAddFailMess("添加成功，请继续添加");
	          
	          //sql.executeUpdate("INSERT INTO "+tableName+" VALUES('"+number+"','"+content+"','"+choiceA+"','"+choiceB+"','"+choiceC+"','"+choiceD+"','"+pic+"','"+answer+"','"+difficulty+"','"+type+"')");
	          testBean.setAddQuestionDataBase(dataBase);
	    	  testBean.setAddQuestionTableName(tableName);
	    	  testBean.setAddQuestionContent(content);
	    	  testBean.setAddQuestionChoiceA(choiceA);
	    	  testBean.setAddQuestionChoiceB(choiceB);
	    	  testBean.setAddQuestionChoiceC(choiceC);
	    	  testBean.setAddQuestionChoiceD(choiceD);
	    	  testBean.setAddQuestionAnswer(answer);
	    	  testBean.setAddQuestionPic(pic);
	    	  testBean.setAddQuestionDifficulty(difficulty);
	    	  testBean.setAddQuestionType(type);
	    	  testBean.setAddFailMess("");
	          if(pic.equals("0")) {
	        	  sql.executeUpdate("INSERT INTO "+tableName+" VALUES('"+number+"','"+content+"','"+choiceA+"','"+choiceB+"','"+choiceC+"','"+choiceD+"','"+pic+"','"+answer+"','"+difficulty+"','"+type+"')");
	              if(kemu.equals("1")){
	        	  	if(type.equals("1")) {
	              	  RequestDispatcher dispatcher=request.getRequestDispatcher("AddKemu1MultipleChoice.jsp");
	                  dispatcher.forward(request,response);               //转发  
	        	  	} else {
	              	  RequestDispatcher dispatcher=request.getRequestDispatcher("AddKemu1Judge.jsp");
	                  dispatcher.forward(request,response);               //转发  
	        	  	}
	              } else {
	            	  if(type.equals("1")) {
	                  	  RequestDispatcher dispatcher=request.getRequestDispatcher("AddKemu4MultipleChoice.jsp");
	                      dispatcher.forward(request,response);               //转发  
	            	  	} else if(type.equals("2")) {
	                  	  RequestDispatcher dispatcher=request.getRequestDispatcher("AddKemu4Judge.jsp");
	                      dispatcher.forward(request,response);               //转发  
	            	  	} else {
	            	  		RequestDispatcher dispatcher=request.getRequestDispatcher("AddKemu4Checkbox.jsp");
	                        dispatcher.forward(request,response);               //转发  
	            	  	}
	              }
	          } else {
	        	  RequestDispatcher dispatcher=request.getRequestDispatcher("UploadPic.jsp");
	              dispatcher.forward(request,response);               //转发  
	          }
	          con.close();
	     } catch(SQLException e){
	          fail(request,response,"");
	     }  
     }
     
     //jFrame.dispose();
   }
   public  void  doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
       doPost(request,response);
   }
   public void fail(HttpServletRequest request,HttpServletResponse response,String backNews) {
	  Test_Bean testBean=null;
	  try{  testBean=(Test_Bean)request.getAttribute("testBean");
	      if(testBean==null){
	          testBean=new Test_Bean();          //创建Javabean对象
	          request.setAttribute("testBean",testBean);
	      }
	  } catch(Exception exp){
	      testBean=new Test_Bean();              //创建Javabean对象
	      request.setAttribute("testBean",testBean);
	  } 
	  testBean.setAddFailMess(backNews+" 添加失败");
	  try {
		  response.sendRedirect("AddKemu1MultipleChoice.jsp");
	  } catch (Exception e) {}
	  
	  //跳回添加界面
/*
        response.setContentType("text/html;charset=GB2312");
        try {
         PrintWriter out=response.getWriter();
         out.println("<html><body>");
         out.println("<h2>"+backNews+"</h2>") ;
         out.println("返回");
         out.println("<a href =Example7_4.jsp>输入记录</a>");
         out.println("</body></html>");
        }
        catch(IOException exp){} 
*/
    }
}
