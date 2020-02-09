package myservlet.control;

import mybean.data.Test_Bean; //引入Javabean模型
import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;

public class Auto_Submit_Servlet extends HttpServlet{
   public void init(ServletConfig config) throws ServletException{
      super.init(config);
   }
   public void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
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
      try {
    	  boolean autoSubmit=testBean.getAutoSubmit();
          if(autoSubmit) {
        	  testBean.setAutoSubmit(false);
          } else {
        	  testBean.setAutoSubmit(true);  
          }
          /*
          String timeCounterMin = request.getParameter("timeCounterMin");
          int min = Integer.parseInt(timeCounterMin);
          String timeCounterSec = request.getParameter("timeCounterSec");
          int sec = Integer.parseInt(timeCounterSec);
          testBean.setTimeCounterMin(min);
          testBean.setTimeCounterSec(sec);
          */
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
      } catch (Exception e) {
    	  System.out.println(e);
      }      
      String whereToGo = request.getParameter("whereToGo");
      if(whereToGo.equals("chapter")) {
    	  response.sendRedirect("ChapterPractise.jsp");
      } else if(whereToGo.equals("random")) {
    	  response.sendRedirect("RandomPractise.jsp");
      } else if(whereToGo.equals("kemu1Test")) {
    	  response.sendRedirect("Kemu1Test.jsp");
      } else if(whereToGo.equals("kemu4Test")) {
    	  response.sendRedirect("Kemu4Test.jsp");
      }
   }
   public  void  doGet(HttpServletRequest request,HttpServletResponse response)
           throws ServletException,IOException{
       doPost(request,response);
   }
}