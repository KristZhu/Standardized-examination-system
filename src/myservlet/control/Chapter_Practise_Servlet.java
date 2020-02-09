package myservlet.control;

import mybean.data.Test_Bean; //����Javabeanģ��
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

import org.apache.tomcat.util.log.SystemLogHandler;

import java.util.*;

public class Chapter_Practise_Servlet extends HttpServlet{
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
      try{
        Class.forName("com.mysql.jdbc.Driver");
      }
      catch(Exception e){} 
      request.setCharacterEncoding("gb2312");
      String id=request.getParameter("id");
      String password=request.getParameter("password");
      
      try {
      	testBean.setId(id);       
      	Connection con;
      	Statement sql, sql2; 
      	ResultSet rs, rs1, rs2, rs3, rsID;
      	
        String uri="jdbc:mysql://127.0.0.1/licence?"+"user=root&password=&characterEncoding=gb2312";
        con=DriverManager.getConnection(uri);
        sql=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
        sql2=con.createStatement();

      	String chapter = testBean.getChapter();
        
        //rs=sql.executeQuery("SELECT * FROM test"); //��ȡ������
        //if(rs.next()==false) {	//�������
      	if(testBean.getAllQuestions()==null||testBean.getAllQuestions().length==0) {
      	 int notBusyTry;
      	 for(notBusyTry=0; notBusyTry<16; notBusyTry++) {	//����16��
      	  try {
        	int sleepTime = notBusyTry*100+(int) Math.pow(2, (int)(notBusyTry*Math.random()));
        	System.out.println(id+"  ChapterPractise  "+chapter+"  "+notBusyTry+"  "+sleepTime);
        	Thread.sleep(sleepTime); 
          } catch(InterruptedException ex) {
            System.out.println("sleepError");
          }
      	  rs=sql.executeQuery("SELECT * FROM test"); 
          if(rs.next()==false) {	//�����һ�����Ѿ�������ɾ����
        	sql.executeUpdate("INSERT INTO test VALUES('0','0','0','0','0','0','0','0','0','0')");	//ռ�����ݿ�
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
            int alreadyNumber = 0;
            if(chapter.equals("chapter1")) alreadyNumber = testBean.getKemu1Chapter1();
            if(chapter.equals("chapter2")) alreadyNumber = testBean.getKemu1Chapter2();
            if(chapter.equals("chapter3")) alreadyNumber = testBean.getKemu1Chapter3();
            if(chapter.equals("chapter4")) alreadyNumber = testBean.getKemu1Chapter4();
            if(chapter.equals("chapter11")) alreadyNumber = testBean.getKemu4Chapter1();
            if(chapter.equals("chapter12")) alreadyNumber = testBean.getKemu4Chapter2();
            if(chapter.equals("chapter13")) alreadyNumber = testBean.getKemu4Chapter3();
            if(chapter.equals("chapter14")) alreadyNumber = testBean.getKemu4Chapter4();
            if(chapter.equals("chapter15")) alreadyNumber = testBean.getKemu4Chapter5();
            if(chapter.equals("chapter16")) alreadyNumber = testBean.getKemu4Chapter6();
            if(chapter.equals("chapter17")) alreadyNumber = testBean.getKemu4Chapter7();
            testBean.setAlreadyNumber(alreadyNumber);
            //int newAlreadyAmount = alreadyNumber+testAmount;
          	String tempSelectChapter = "SELECT * FROM " + chapter + " WHERE number > " + alreadyNumber;// + " AND number <= " + newAlreadyAmount;
          	rs1 = sql.executeQuery(tempSelectChapter);
            rs1.last();
            int recordAmount = rs1.getRow();
            if(recordAmount<=0) {	//������
            	tempSelectChapter = "SELECT * FROM " + chapter;
            	rs1 = sql.executeQuery(tempSelectChapter);
                rs1.last();
                recordAmount = rs1.getRow();
                if(chapter.equals("chapter1")) {
                	alreadyNumber = 1000;
                	testBean.setAlreadyNumber(alreadyNumber);
                } if(chapter.equals("chapter2")) {
                	alreadyNumber = 2000;
                	testBean.setAlreadyNumber(alreadyNumber);
                } if(chapter.equals("chapter3")) {
                	alreadyNumber = 3000;
                	testBean.setAlreadyNumber(alreadyNumber);
                } if(chapter.equals("chapter4")) {
                	alreadyNumber = 4000;
                	testBean.setAlreadyNumber(alreadyNumber);
                } if(chapter.equals("chapter11")) {
                	alreadyNumber = 11000;
                	testBean.setAlreadyNumber(alreadyNumber);
                } if(chapter.equals("chapter12")) {
                	alreadyNumber = 12000;
                	testBean.setAlreadyNumber(alreadyNumber);
                } if(chapter.equals("chapter13")) {
                	alreadyNumber = 13000;
                	testBean.setAlreadyNumber(alreadyNumber);
                } if(chapter.equals("chapter14")) {
                	alreadyNumber = 14000;
                	testBean.setAlreadyNumber(alreadyNumber);
                } if(chapter.equals("chapter15")) {
                	alreadyNumber = 15000;
                	testBean.setAlreadyNumber(alreadyNumber);
                } if(chapter.equals("chapter16")) {
                	alreadyNumber = 16000;
                	testBean.setAlreadyNumber(alreadyNumber);
                } if(chapter.equals("chapter17")) {
                	alreadyNumber = 17000;
                	testBean.setAlreadyNumber(alreadyNumber);
                }
            }
            for(int i=1; i<=recordAmount; i++) {
            	rs1.absolute(i);
            	as11.add(rs1.getString(1).trim());//���
            	as12.add(rs1.getString(2).trim());//��Ŀ
            	as13.add(rs1.getString(3).trim());//a
            	as14.add(rs1.getString(4).trim());//b
            	as15.add(rs1.getString(5).trim());//c
            	as16.add(rs1.getString(6).trim());//d
            	as17.add(rs1.getString(7).trim());//pic
            	as18.add(rs1.getString(8).trim());//��
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
            
            rs=sql.executeQuery("SELECT * FROM test");
            rs.last();    
            int testAmount=rs.getRow()-1;                 //�õ���¼��
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
            	}
            }
            testBean.setAllQuestions(allQ);
            sql2.executeUpdate("DELETE FROM test");
            break;
          }
      	 }
      	 if(notBusyTry>=16) {
      		//jFrame.dispose();
	      	testBean.setPleaseChooseChapter("ϵͳ��æ�����Ժ�����");
	      	if(chapter.substring(7).length()==1) response.sendRedirect("SetKemu1ChapterPractise.jsp");
	      	else response.sendRedirect("SetKemu4ChapterPractise.jsp");
	      	return;
      	 }
        }
      	
      	String[][] allQuestions = testBean.getAllQuestions();
      	/*
      	for(int i=0; i<allQuestions.length; i++) {
      		for(int j=0; j<allQuestions[i].length; j++) {
      			System.out.print(allQuestions[i][j]+"  ");
      		}
      		System.out.println("");
      	}
        */
      	
        //rs.last();    
        //int recordAmount=rs.getRow();                 //�õ���¼��
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
           index=list.get(0);
           list.remove(0);           
           session.setAttribute("list",list);
           int number=testBean.getNumber();
           if(number<recordAmount) {
                //�����ȡ��һ��Ŀ��
              number++;
              testBean.setNumber(number); //���              
              int alreadyNumber = testBean.getAlreadyNumber();
              //int tempNumber = Integer.parseInt(rs.getString(1));
              int tempNumber = Integer.parseInt(allQuestions[recordAmount-1][0]);              
              int n = recordAmount+alreadyNumber-tempNumber+number;	
              //rs.absolute(n);
              String[] nowQuestion = allQuestions[n-1];
              testBean.setOriginNumber(n+alreadyNumber);
              
              testBean.setFinishChapter("");
              /*
              testBean.setQuestions(rs.getString(2));          //��Ŀ����
              testBean.setChoiceA(rs.getString(3));            //��Ŀ��ѡ��a
              testBean.setChoiceB(rs.getString(4));            //��Ŀ��ѡ��b
              testBean.setChoiceC(rs.getString(5));            //��Ŀ��ѡ��c
              testBean.setChoiceD(rs.getString(6));            //��Ŀ��ѡ��d
              testBean.setImage(rs.getString(7));              //��Ŀ��ʾ��ͼ�Ƿ����
              testBean.setCorrectAnswer(rs.getString(8).trim());//��Ŀ�Ĵ�
              testBean.setDifficulty(rs.getString(9));		   //��Ŀ���Ѷ�
              testBean.setType(rs.getString(10).trim());	   //��Ŀ������
              testBean.setMess("�����ǵ�"+number+"�⣬�Ѷ�Ϊ"+rs.getString(9));
              */
              testBean.setQuestions(nowQuestion[1]);		  //��Ŀ����
              testBean.setChoiceA(nowQuestion[2]);            //��Ŀ��ѡ��a
              testBean.setChoiceB(nowQuestion[3]);            //��Ŀ��ѡ��b
              testBean.setChoiceC(nowQuestion[4]);            //��Ŀ��ѡ��c
              testBean.setChoiceD(nowQuestion[5]);            //��Ŀ��ѡ��d
              testBean.setImage(nowQuestion[6]);              //��Ŀ��ʾ��ͼ�Ƿ����
              testBean.setCorrectAnswer(nowQuestion[7]);//��Ŀ�Ĵ�
              testBean.setDifficulty(nowQuestion[8]);		   //��Ŀ���Ѷ�
              testBean.setType(nowQuestion[9]);	   //��Ŀ������
              con.close(); 
              response.sendRedirect("ChapterPractise.jsp");
              return;
          } else {
             testBean.setMess("�����������������鿴����");
             int oldAlreadyNumber = testBean.getAlreadyNumber();
			 int tempSetNewAlready = oldAlreadyNumber+recordAmount;
			 testBean.setAlreadyNumber(tempSetNewAlready);
             response.sendRedirect("ChapterPractise.jsp");
             testBean.setFinishChapter("���½�����ϰ��ϣ������˳�");
             testBean.setPleaseChooseChapter("pleaseChooseChapter");
             return;
          }
       } else {
          testBean.setMess("���½�����ϰ���");
          response.sendRedirect("setKemu1ChapterPractise.jsp");
          return;
       }
     }
     catch(SQLException e){
         System.out.println("RandomPractiseServlet  "+e);
         testBean.setBackNews("ϵͳ�쳣�������µ�½");
     	 response.sendRedirect("Index.jsp");
     	 return;
     }
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
           out.println("<a href =Enter.jsp>����</a></h2>");
           out.println("</body></html>");
        }
        catch(IOException exp){} 
    }
}