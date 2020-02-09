package myservlet.control;

import mybean.data.Test_Bean; //��������2�е�Javabeanģ��
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

public class Change_Questions_Servlet extends HttpServlet{
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
	 } catch(Exception exp){
	    testBean=new Test_Bean();              //����Javabean����
	    session.setAttribute("testBean",testBean);
	 } 
     try{  Class.forName("com.mysql.jdbc.Driver");
     } catch(Exception e){} 
     
     boolean valid = true;
     
     request.setCharacterEncoding("utf8");
     
     String id = request.getParameter("id");
     testBean.setId(id);
     
     testBean.setAddFailMess("");
     
     String number = request.getParameter("number");
     String dataBase = request.getParameter("dataBase");
     String tableName = request.getParameter("chapter");
     String content=request.getParameter("content");
     String choiceA=request.getParameter("choiceA");     
     String choiceB=request.getParameter("choiceB");
     String choiceC=request.getParameter("choiceC");
     String choiceD=request.getParameter("choiceD");
     String pic=request.getParameter("pic");
     String changePic=request.getParameter("changePic");
     String difficulty=request.getParameter("difficulty");
     String type=request.getParameter("type");
     
     String kemu = Integer.parseInt(tableName.substring(7))<10?"1":"4";
     
     String answer="";
     String []ans=request.getParameterValues("answer");
     try {
		if(type.equals("3")) {
			if(ans.length<=1){
       		 	testBean.setAddFailMess("���������٣��޸�ʧ��");
       		 	valid=false;
       		 	RequestDispatcher dispatcher = request.getRequestDispatcher("ChangeQuestion.jsp");
       		 	dispatcher.forward(request, response);
       	 	} else {
       	 		answer=ans[0];
       	 		for(int i=1; i<ans.length; i++) {
       	 			answer=answer+","+ans[i];
       	 		}
       	 	}
		} else {
			if(ans.length>1) {
				testBean.setAddFailMess("���������࣬�޸�ʧ��");
       		 	valid=false;
       		 	RequestDispatcher dispatcher = request.getRequestDispatcher("ChangeQuestion.jsp");
    		 	dispatcher.forward(request, response);
			} else {
				answer=ans[0];
				if(answer.equalsIgnoreCase("d")&&type.equals("2")) {
					testBean.setAddFailMess("�ж����ΪD���޸�ʧ��");
					RequestDispatcher dispatcher = request.getRequestDispatcher("ChangeQuestion.jsp");
	       		 	dispatcher.forward(request, response);
				} else if(answer.equalsIgnoreCase("c")&&type.equals("2")) {
					testBean.setAddFailMess("�ж����ΪC���޸�ʧ��");
					RequestDispatcher dispatcher = request.getRequestDispatcher("ChangeQuestion.jsp");
	       		 	dispatcher.forward(request, response);
				}
			}
		}
     } catch (Exception e) {
    	 testBean.setAddFailMess("��Ϊ�գ��޸�ʧ��");
		 valid=false;
		 response.sendRedirect("ChangeQuestion.jsp");
     }
    		 
     /*
 	 testBean.setChangeQuestionNumber(number);
 	 testBean.setChangeQuestionContent(content);
 	 testBean.setChangeQuestionChoiceA(choiceA);
 	 testBean.setChangeQuestionChoiceB(choiceB);
 	 testBean.setChangeQuestionChoiceC(choiceC);
 	 testBean.setChangeQuestionChoiceD(choiceD);
 	 testBean.setChangeQuestionPic(pic);
 	 testBean.setChangeQuestionAnswer(answer);
 	 testBean.setChangeQuestionDifficulty(difficulty);
 	 testBean.setChangeQuestionType(type);
     */
    		 
     if(content==null||content.length()==0) {
    	 testBean.setAddFailMess("����Ϊ�գ��޸�ʧ��");
    	 valid=false;
    	 RequestDispatcher dispatcher = request.getRequestDispatcher("ChangeQuestion.jsp");
		 dispatcher.forward(request, response);
     } else if(choiceA==null||choiceA.length()==0) {
    	 testBean.setAddFailMess("Aѡ��Ϊ�գ��޸�ʧ��");
    	 valid=false;
    	 RequestDispatcher dispatcher = request.getRequestDispatcher("ChangeQuestion.jsp");
		 dispatcher.forward(request, response);
     } else if(choiceB==null||choiceB.length()==0) {
    	 testBean.setAddFailMess("Bѡ��Ϊ�գ��޸�ʧ��");
    	 valid=false;
    	 RequestDispatcher dispatcher = request.getRequestDispatcher("ChangeQuestion.jsp");
		 dispatcher.forward(request, response);
     } else if((choiceC==null||choiceC.length()==0)&&(!type.equals("2"))) {
    	 testBean.setAddFailMess("Cѡ��Ϊ�գ��޸�ʧ��");
    	 valid=false;
    	 RequestDispatcher dispatcher = request.getRequestDispatcher("ChangeQuestion.jsp");
		 dispatcher.forward(request, response);
     } else if((choiceD==null||choiceD.length()==0)&&(!type.equals("2"))) {
    	 testBean.setAddFailMess("Dѡ��Ϊ�գ��޸�ʧ��");
    	 valid=false;
    	 RequestDispatcher dispatcher = request.getRequestDispatcher("ChangeQuestion.jsp");
		 dispatcher.forward(request, response);
     } else if(choiceD.length()>0&&type.equals("2")) {
    	 testBean.setAddFailMess("�ж���Dѡ��ǿգ��޸�ʧ��");
    	 valid=false;
    	 RequestDispatcher dispatcher = request.getRequestDispatcher("ChangeQuestion.jsp");
		 dispatcher.forward(request, response);
     } else if(choiceC.length()>0&&type.equals("2")) {
    	 testBean.setAddFailMess("�ж���Cѡ��ǿգ��޸�ʧ��");
    	 valid=false;
    	 RequestDispatcher dispatcher = request.getRequestDispatcher("ChangeQuestion.jsp");
		 dispatcher.forward(request, response);
     } else {
     }
     
     if(valid) {
	     if(type.equals("2")) {
	    	 choiceC="0";    
	    	 choiceD="0";
	     }
	     
	     Connection con;
	     Statement sql; 
	     ResultSet rs;
	     try{ 
	          String uri="jdbc:mysql://127.0.0.1/"+dataBase+"?"+"user=root&password=&characterEncoding=gb2312";
	          con=DriverManager.getConnection(uri);
	          sql=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
	          
	          /*
	          rs=sql.executeQuery("SELECT * FROM "+tableName);
	          ResultSetMetaData metaData = rs.getMetaData();
	          int columnCount = metaData.getColumnCount();       //�õ������������
	          String []columnName = new String[columnCount];
	          for(int i=0;i<columnName.length;i++) {
	             columnName[i] = metaData.getColumnName(i+1);    //�õ�����
	          }
	          resultBean.setColumnName(columnName);             //����Javabean����ģ��
	          rs.last();
	          int rowNumber=rs.getRow();                        //�õ���¼��
	          String [][] tableRecord=resultBean.getTableRecord();
	          tableRecord = new String[rowNumber][columnCount];
	          rs.beforeFirst();
	          int i=0;
	          while(rs.next()){
	            for(int k=0;k<columnCount;k++) 
	              tableRecord[i][k] = rs.getString(k+1);
	              i++; 
	          }
	          resultBean.setTableRecord(tableRecord);             //����Javabean����ģ��
	          */
	          
	          //testBean.setAddFailMess("��ӳɹ�����������");
	          
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
	          if(changePic.equals("remain1")||changePic.equals("remain0")) {
	        	  sql.executeUpdate("UPDATE " + tableName + " SET content = '" + content + "', a = '" + choiceA + "', b = '" + choiceB + "', c = '" + choiceC + "', d = '" + choiceD + "', answer = '" + answer + "', difficulty = '" + difficulty + "', type = '" + type+ "' WHERE number = '" + number + "'");
	        	  testBean.setChangeQuestionNumber("");
	        	  testBean.setChangeQuestionContent("");
	        	  testBean.setChangeQuestionChoiceA("");
	        	  testBean.setChangeQuestionChoiceB("");
	        	  testBean.setChangeQuestionChoiceC("");
	        	  testBean.setChangeQuestionChoiceD("");
	        	  testBean.setChangeQuestionPic("");
	        	  testBean.setChangeQuestionAnswer("");
	        	  testBean.setChangeQuestionDifficulty("");
	        	  testBean.setChangeQuestionType("");
	        	  if(kemu.equals("1")){
	              	  RequestDispatcher dispatcher=request.getRequestDispatcher("CheckKemu1.jsp");
	                  dispatcher.forward(request,response);               //ת��  
	              } else {
	            	  RequestDispatcher dispatcher=request.getRequestDispatcher("CheckKemu4.jsp");
	                  dispatcher.forward(request,response);               //ת��  
	              }
	          } else if(changePic.equals("delete")){
	        	  sql.executeUpdate("UPDATE " + tableName + " SET content = '" + content + "', a = '" + choiceA + "', b = '" + choiceB + "', c = '" + choiceC + "', d = '" + choiceD + "', pic = '0', answer = '" + answer + "', difficulty = '" + difficulty + "', type = '" + type+ "' WHERE number = '" + number + "'");
	        	  testBean.setChangeQuestionNumber("");
	        	  testBean.setChangeQuestionContent("");
	        	  testBean.setChangeQuestionChoiceA("");
	        	  testBean.setChangeQuestionChoiceB("");
	        	  testBean.setChangeQuestionChoiceC("");
	        	  testBean.setChangeQuestionChoiceD("");
	        	  testBean.setChangeQuestionPic("");
	        	  testBean.setChangeQuestionAnswer("");
	        	  testBean.setChangeQuestionDifficulty("");
	        	  testBean.setChangeQuestionType("");
	        	  if(kemu.equals("1")){
	              	  RequestDispatcher dispatcher=request.getRequestDispatcher("CheckKemu1.jsp");
	                  dispatcher.forward(request,response);               //ת��  
	              } else {
	            	  RequestDispatcher dispatcher=request.getRequestDispatcher("CheckKemu4.jsp");
	                  dispatcher.forward(request,response);               //ת��  
	              }
	          } else {
	        	  testBean.setChangePicNumber(number);
	        	  RequestDispatcher dispatcher=request.getRequestDispatcher("ChangePic.jsp");
	              dispatcher.forward(request,response);               //ת��  
	          }
	          con.close();
	     }
	     catch(SQLException e){
	    	  System.out.println(e);
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
	          testBean=new Test_Bean();          //����Javabean����
	          request.setAttribute("testBean",testBean);
	      }
	  } catch(Exception exp){
	      testBean=new Test_Bean();              //����Javabean����
	      request.setAttribute("testBean",testBean);
	  } 
	  testBean.setAddFailMess(backNews+" ���ʧ��");
	  try {
		  response.sendRedirect("Index.jsp");
	  } catch (Exception e) {}
	  
	  //������ӽ���
/*
        response.setContentType("text/html;charset=GB2312");
        try {
         PrintWriter out=response.getWriter();
         out.println("<html><body>");
         out.println("<h2>"+backNews+"</h2>") ;
         out.println("����");
         out.println("<a href =Example7_4.jsp>�����¼</a>");
         out.println("</body></html>");
        }
        catch(IOException exp){} 
*/
    }
}