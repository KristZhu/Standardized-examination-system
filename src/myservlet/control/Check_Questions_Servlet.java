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

public class Check_Questions_Servlet extends HttpServlet{
   public void init(ServletConfig config) throws ServletException{
      super.init(config);
      try {  Class.forName("com.mysql.jdbc.Driver");
      }
      catch(Exception e){} 
   }
   public void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException {
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
      request.setCharacterEncoding("gb2312");
      String dataBase= request.getParameter("dataBase");
      String tableName= request.getParameter("chapter");
      String difficulty=request.getParameter("difficulty");
      String type=request.getParameter("type");
      HttpSession session=request.getSession(true); 
      Test_Bean testBean=null;
      try{ 
           testBean=(Test_Bean)session.getAttribute("testBean");
           if(testBean==null){
              testBean=new Test_Bean();             //����Javabean����
              session.setAttribute("testBean",testBean);
           }
      }
      catch(Exception exp){
           testBean=new Test_Bean();  
           session.setAttribute("testBean",testBean);
      } 
      Connection con=null; 
      String uri="jdbc:mysql://127.0.0.1/"+dataBase+"?"+"user=root&password=&characterEncoding=gb2312";
      try{ 
          con=DriverManager.getConnection(uri);
          Statement sql=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
          ResultSet rs;
          if(difficulty.equals("0")&&type.equals("0")) {
        	  rs=sql.executeQuery("SELECT * FROM "+tableName);
          } else if(difficulty.equals("0")) {
        	  rs=sql.executeQuery("SELECT * FROM "+tableName+" WHERE type = "+type);
          } else if(type.equals("0")) {
        	  rs=sql.executeQuery("SELECT * FROM "+tableName+" WHERE difficulty = "+difficulty);
          } else {
        	  rs=sql.executeQuery("SELECT * FROM "+tableName+" WHERE difficulty = "+difficulty+" AND type = "+type);
          }
          String kemu = Integer.parseInt(tableName.substring(7))<10?"1":"4";
          String s = "";
          if(tableName.equals("chapter1")) s="��Ŀһ��1������";
    	  if(tableName.equals("chapter2")) s="��Ŀһ��2������";
    	  if(tableName.equals("chapter3")) s="��Ŀһ��3������";
    	  if(tableName.equals("chapter4")) s="��Ŀһ��4������";
    	  if(tableName.equals("chapter11")) s="��Ŀ�ĵ�1������";
    	  if(tableName.equals("chapter12")) s="��Ŀ�ĵ�2������";
    	  if(tableName.equals("chapter13")) s="��Ŀ�ĵ�3������";
    	  if(tableName.equals("chapter14")) s="��Ŀ�ĵ�4������";
    	  if(tableName.equals("chapter15")) s="��Ŀ�ĵ�5������";
    	  if(tableName.equals("chapter16")) s="��Ŀ�ĵ�6������";
    	  if(tableName.equals("chapter17")) s="��Ŀ�ĵ�7������";
    	  if(difficulty.equals("1")) s=s+"��";
    	  if(difficulty.equals("2")) s=s+"�е�";
    	  if(difficulty.equals("3")) s=s+"����";
    	  if(type.equals("1")) s=s+"��ѡ";
    	  if(type.equals("2")) s=s+"�ж�";
    	  if(type.equals("3")) s=s+"��ѡ";
    	  s=s+"��Ŀ";
    	  testBean.setShowQuestionsMess(s);
          
          ResultSetMetaData metaData = rs.getMetaData();
          int columnCount = metaData.getColumnCount();    //�õ������������
          String []columnName = new String[columnCount];
          for(int i=0;i<columnName.length;i++) {
             columnName[i] = metaData.getColumnName(i+1);  //�õ�����
          }
          testBean.setColumnName(columnName);              //����Javabean����ģ��
          rs.last();
          int rowNumber=rs.getRow();                       //�õ���¼��
          String [][] tableRecord=testBean.getTableRecord();
          tableRecord = new String[rowNumber][columnCount];
          rs.beforeFirst();
          int i=0;
          while(rs.next()){
            for(int k=0;k<columnCount;k++) 
              tableRecord[i][k] = rs.getString(k+1);
              i++; 
          }
          testBean.setTableRecord(tableRecord);              //����Javabean����ģ��
          con.close();
          response.sendRedirect("CheckShow.jsp");  //�ض���
     }
     catch(SQLException e){
          System.out.println(e);
     }  
      
     //jFrame.dispose();
   }
   public  void  doGet(HttpServletRequest request,HttpServletResponse response)
           throws ServletException,IOException{
       doPost(request,response);
   }
   public void fail(HttpServletRequest request,HttpServletResponse response,String backNews) {
        response.setContentType("text/html;charset=GB2312");
        try {
           PrintWriter out=response.getWriter();
           out.println("<html><body>");
           out.println("<h2>"+backNews+"</h2>") ;
           out.println("����");
           out.println("<a href =Example7_8.jsp>������ȷ��Ϣ</a>");
           out.println("</body></html>");
        }
        catch(IOException exp) { } 
  }
}