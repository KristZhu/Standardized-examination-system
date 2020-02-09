package myservlet.control;

import mybean.data.Test_Bean;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JLabel;
 

/**
 * Servlet implementation class UploadServlet
 */
@WebServlet("/UploadServlet")
public class Upload_Servlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
     
    // �ϴ��ļ��洢Ŀ¼
    private static final String UPLOAD_DIRECTORY = "upload";
 
    // �ϴ�����
    private static final int MEMORY_THRESHOLD   = 1024 * 1024 * 3;  // 3MB
    private static final int MAX_FILE_SIZE      = 1024 * 1024 * 40; // 40MB
    private static final int MAX_REQUEST_SIZE   = 1024 * 1024 * 50; // 50MB
 
    /**
     * �ϴ����ݼ������ļ�
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
    	
    	// ����Ƿ�Ϊ��ý���ϴ�
        if (!ServletFileUpload.isMultipartContent(request)) {
            // ���������ֹͣ
            PrintWriter writer = response.getWriter();
            writer.println("Error: ��������� enctype=multipart/form-data");
            writer.flush();
            return;
        }
 
        // �����ϴ�����
        DiskFileItemFactory factory = new DiskFileItemFactory();
        // �����ڴ��ٽ�ֵ - �����󽫲�����ʱ�ļ����洢����ʱĿ¼��
        factory.setSizeThreshold(MEMORY_THRESHOLD);
        // ������ʱ�洢Ŀ¼
        factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
 
        ServletFileUpload upload = new ServletFileUpload(factory);
         
        // ��������ļ��ϴ�ֵ
        upload.setFileSizeMax(MAX_FILE_SIZE);
         
        // �����������ֵ (�����ļ��ͱ�����)
        upload.setSizeMax(MAX_REQUEST_SIZE);
        
        // ���Ĵ���
        upload.setHeaderEncoding("UTF-8"); 

        // ������ʱ·�����洢�ϴ����ļ�
        // ���·����Ե�ǰӦ�õ�Ŀ¼
        String uploadPath = getServletContext().getRealPath("/") + File.separator + UPLOAD_DIRECTORY;
       
         
        // ���Ŀ¼�������򴴽�
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }
 
        try {
            // ���������������ȡ�ļ�����
            @SuppressWarnings("unchecked")
            List<FileItem> formItems = upload.parseRequest(request);
 
            if (formItems != null && formItems.size() > 0) {
                // ����������
                for (FileItem item : formItems) {
                    // �����ڱ��е��ֶ�
                    if (!item.isFormField()) {
                    	String name = testBean.getNewQuestionNumber();
                        //String fileName = new File(item.getName()).getName();
                    	String fileName = name+".png";
                        String filePath = uploadPath + File.separator + fileName;
                        File storeFile = new File(filePath);
                        // �ڿ���̨����ļ����ϴ�·��
                        System.out.println(filePath);
                        // �����ļ���Ӳ��
                        item.write(storeFile);
                        request.setAttribute("message", "�ļ��ϴ��ɹ�!");
                        
                        
                        try{  Class.forName("com.mysql.jdbc.Driver");
                        } catch(Exception e){} 
                        
                        request.setCharacterEncoding("utf8");
                        
                        //String id = request.getParameter("id");
                        //testBean.setId(id);
                        
                        String dataBase = testBean.getAddQuestionDataBase();
                        String tableName = testBean.getAddQuestionTableName();
                        String content = testBean.getAddQuestionContent();
                        String choiceA = testBean.getAddQuestionChoiceA();   
                        String choiceB = testBean.getAddQuestionChoiceB();
                        String choiceC = testBean.getAddQuestionChoiceC();
                        String choiceD = testBean.getAddQuestionChoiceD();
                        String pic = testBean.getAddQuestionPic();
                        String answer = testBean.getAddQuestionAnswer();
                        String difficulty = testBean.getAddQuestionDifficulty();
                        String type = testBean.getAddQuestionType();
                       
                        /*
                        if(content==null||content.length()==0) {
                       	 testBean.setAddFailMess("����Ϊ�գ����ʧ��");
                       	 RequestDispatcher dispatcher = request.getRequestDispatcher("AddKemu1MultipleChoice.jsp");
                       	 dispatcher.forward(request, response);
                        } else if(choiceA==null||choiceA.length()==0) {
                       	 testBean.setAddFailMess("Aѡ��Ϊ�գ����ʧ��");
                       	 RequestDispatcher dispatcher = request.getRequestDispatcher("AddKemu1MultipleChoice.jsp");
                       	 dispatcher.forward(request, response);
                        } else if(choiceB==null||choiceB.length()==0) {
                       	 testBean.setAddFailMess("Bѡ��Ϊ�գ����ʧ��");
                       	 RequestDispatcher dispatcher = request.getRequestDispatcher("AddKemu1MultipleChoice.jsp");
                       	 dispatcher.forward(request, response);
                        } else if((choiceC==null||choiceC.length()==0)&&type.equals("1")) {
                       	 testBean.setAddFailMess("Cѡ��Ϊ�գ����ʧ��");
                       	 RequestDispatcher dispatcher = request.getRequestDispatcher("AddKemu1MultipleChoice.jsp");
                       	 dispatcher.forward(request, response);
                        } else if((choiceD==null||choiceD.length()==0)&&type.equals("1")) {
                       	 testBean.setAddFailMess("Dѡ��Ϊ�գ����ʧ��");
                       	 RequestDispatcher dispatcher = request.getRequestDispatcher("AddKemu1MultipleChoice.jsp");
                       	 dispatcher.forward(request, response);
                        } else {
                        */
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
                             sql.executeUpdate("INSERT INTO "+tableName+" VALUES('"+number+"','"+content+"','"+choiceA+"','"+choiceB+"','"+choiceC+"','"+choiceD+"','"+pic+"','"+answer+"','"+difficulty+"','"+type+"')");
                             con.close();
                        }
                        catch(SQLException e){
                        	 testBean.setAddFailMess(e.getMessage());
                          	 RequestDispatcher dispatcher = request.getRequestDispatcher("AddKemu1MultipleChoice.jsp");
                          	 dispatcher.forward(request, response);
                        }
                        //}
                        
                    }
                }
            }
        } catch (Exception ex) {
            request.setAttribute("message", "������Ϣ: " + ex.getMessage());
            getServletContext().getRequestDispatcher("/message.jsp").forward(request, response);
        }
        // ��ת�� message.jsp
        
        String tableName = testBean.getAddQuestionTableName();
        String type = testBean.getAddQuestionType();
        String kemu = Integer.parseInt(tableName.substring(7))<10?"1":"4";
        if(kemu.equals("1")){
        	if(type.equals("1")) {
                RequestDispatcher dispatcher=request.getRequestDispatcher("AddKemu1MultipleChoice.jsp");
                dispatcher.forward(request,response);               //ת�� 
        	} else {
                RequestDispatcher dispatcher=request.getRequestDispatcher("AddKemu1Judge.jsp");
                dispatcher.forward(request,response);               //ת�� 
        	}
        } else {
        	if(type.equals("1")) {
                RequestDispatcher dispatcher=request.getRequestDispatcher("AddKemu4MultipleChoice.jsp");
                dispatcher.forward(request,response);               //ת�� 
        	} else if(type.equals("2")){
        		RequestDispatcher dispatcher=request.getRequestDispatcher("AddKemu4Judge.jsp");
                dispatcher.forward(request,response);               //ת�� 
        	} else {
                RequestDispatcher dispatcher=request.getRequestDispatcher("AddKemu4Checkbox.jsp");
                dispatcher.forward(request,response);               //ת�� 
        	}
        }
        
        //jFrame.dispose();
    }
}