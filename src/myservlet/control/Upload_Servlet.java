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
     
    // 上传文件存储目录
    private static final String UPLOAD_DIRECTORY = "upload";
 
    // 上传配置
    private static final int MEMORY_THRESHOLD   = 1024 * 1024 * 3;  // 3MB
    private static final int MAX_FILE_SIZE      = 1024 * 1024 * 40; // 40MB
    private static final int MAX_REQUEST_SIZE   = 1024 * 1024 * 50; // 50MB
 
    /**
     * 上传数据及保存文件
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
    	
    	// 检测是否为多媒体上传
        if (!ServletFileUpload.isMultipartContent(request)) {
            // 如果不是则停止
            PrintWriter writer = response.getWriter();
            writer.println("Error: 表单必须包含 enctype=multipart/form-data");
            writer.flush();
            return;
        }
 
        // 配置上传参数
        DiskFileItemFactory factory = new DiskFileItemFactory();
        // 设置内存临界值 - 超过后将产生临时文件并存储于临时目录中
        factory.setSizeThreshold(MEMORY_THRESHOLD);
        // 设置临时存储目录
        factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
 
        ServletFileUpload upload = new ServletFileUpload(factory);
         
        // 设置最大文件上传值
        upload.setFileSizeMax(MAX_FILE_SIZE);
         
        // 设置最大请求值 (包含文件和表单数据)
        upload.setSizeMax(MAX_REQUEST_SIZE);
        
        // 中文处理
        upload.setHeaderEncoding("UTF-8"); 

        // 构造临时路径来存储上传的文件
        // 这个路径相对当前应用的目录
        String uploadPath = getServletContext().getRealPath("/") + File.separator + UPLOAD_DIRECTORY;
       
         
        // 如果目录不存在则创建
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }
 
        try {
            // 解析请求的内容提取文件数据
            @SuppressWarnings("unchecked")
            List<FileItem> formItems = upload.parseRequest(request);
 
            if (formItems != null && formItems.size() > 0) {
                // 迭代表单数据
                for (FileItem item : formItems) {
                    // 处理不在表单中的字段
                    if (!item.isFormField()) {
                    	String name = testBean.getNewQuestionNumber();
                        //String fileName = new File(item.getName()).getName();
                    	String fileName = name+".png";
                        String filePath = uploadPath + File.separator + fileName;
                        File storeFile = new File(filePath);
                        // 在控制台输出文件的上传路径
                        System.out.println(filePath);
                        // 保存文件到硬盘
                        item.write(storeFile);
                        request.setAttribute("message", "文件上传成功!");
                        
                        
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
                       	 testBean.setAddFailMess("内容为空，添加失败");
                       	 RequestDispatcher dispatcher = request.getRequestDispatcher("AddKemu1MultipleChoice.jsp");
                       	 dispatcher.forward(request, response);
                        } else if(choiceA==null||choiceA.length()==0) {
                       	 testBean.setAddFailMess("A选项为空，添加失败");
                       	 RequestDispatcher dispatcher = request.getRequestDispatcher("AddKemu1MultipleChoice.jsp");
                       	 dispatcher.forward(request, response);
                        } else if(choiceB==null||choiceB.length()==0) {
                       	 testBean.setAddFailMess("B选项为空，添加失败");
                       	 RequestDispatcher dispatcher = request.getRequestDispatcher("AddKemu1MultipleChoice.jsp");
                       	 dispatcher.forward(request, response);
                        } else if((choiceC==null||choiceC.length()==0)&&type.equals("1")) {
                       	 testBean.setAddFailMess("C选项为空，添加失败");
                       	 RequestDispatcher dispatcher = request.getRequestDispatcher("AddKemu1MultipleChoice.jsp");
                       	 dispatcher.forward(request, response);
                        } else if((choiceD==null||choiceD.length()==0)&&type.equals("1")) {
                       	 testBean.setAddFailMess("D选项为空，添加失败");
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
            request.setAttribute("message", "错误信息: " + ex.getMessage());
            getServletContext().getRequestDispatcher("/message.jsp").forward(request, response);
        }
        // 跳转到 message.jsp
        
        String tableName = testBean.getAddQuestionTableName();
        String type = testBean.getAddQuestionType();
        String kemu = Integer.parseInt(tableName.substring(7))<10?"1":"4";
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
        	} else if(type.equals("2")){
        		RequestDispatcher dispatcher=request.getRequestDispatcher("AddKemu4Judge.jsp");
                dispatcher.forward(request,response);               //转发 
        	} else {
                RequestDispatcher dispatcher=request.getRequestDispatcher("AddKemu4Checkbox.jsp");
                dispatcher.forward(request,response);               //转发 
        	}
        }
        
        //jFrame.dispose();
    }
}