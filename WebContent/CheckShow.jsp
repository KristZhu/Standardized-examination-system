<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<jsp:useBean id="testBean" class="mybean.data.Test_Bean" scope="session"/>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>驾照标准化考试系统</title>
<link href="style/common.css" rel="stylesheet" type="text/css" />
</head>

<%
try{
boolean isTeacher = testBean.getIsTeacher();
if(!isTeacher){
	response.sendRedirect("Index.jsp");
}

testBean.setAddFailMess("");
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
%>

<Body>
<div id="wrapper">
    <div id="top">
    	<jsp:getProperty property="name" name="testBean"/>，你好！
    	<a href="Index.jsp"><input id="log" class="inputSubmit" type="submit" value="登出" name="logout"></a>
    </div>
<div class="cleaner"></div>

<div id="header">
	<div id="logo"></div>
	<div id="head_right">
    	<div class="search">
        	<div>
            	<form>
            	<select name="option">
                    <option value="全部">全部</option>
                    <option value="科目一">科目一</option>
                    <option value="科目四">科目四</option>
                </select>
				</form>
            </div>
            <div>
            	<table><tr>
                <form action="http://www.baidu.com/s" method="get">
            	<td>
                	<input name="wd" type="text" onfocus="if(this.value=='请输入关键字'){this.value=''}" onblur="if(this.value==''){this.value='请输入关键字'; this.color}" value="请输入关键字" size="17">
            	</td>
            	<td>            
                	<button class="inputSubmit" type="submit" formtarget="_blank"><img src="images/student/search_button.png" /></button>
            	</td>
            	</form>
                </tr></table>
            </div>
        </div><!--search -->
    </div><!--right-->    
</div><!--head -->
<div class="cleaner"></div>

<%
String [][] table=testBean.getTableRecord();
int totalRecord = table.length;
int pageSize=testBean.getPageSize();  //每页显示的记录数
String ps = request.getParameter("pageSize");
if(ps!=null&&ps.length()>0){
	pageSize = Integer.parseInt(ps);
	testBean.setPageSize(pageSize);
}
int currentPage = testBean.getCurrentPage();
int totalPages = testBean.getTotalPages();
if (totalRecord%pageSize==0) totalPages = totalRecord/pageSize;//总页数
	else totalPages = totalRecord/pageSize+1;
testBean.setTotalPages(totalPages);
%>

	<center>
      <div style="font-size:22px;">
      	<jsp:setProperty name="testBean" property="currentPage" param="currentPage" />
      	当前显示的内容是：<jsp:getProperty name="testBean" property="showQuestionsMess" /> 
		共有<%=totalRecord %>题
      </div>
      <div style="height:10px;"></div>
      <table border=1>
      	<tr>
      		<td align="center" style="word-break:keep-all;">编号</td>
      		<td align="center" style="word-break:keep-all;">内容</td>
      		<td align="center" style="word-break:keep-all;">A选项</td>
      		<td align="center" style="word-break:keep-all;">B选项</td>
      		<td align="center" style="word-break:keep-all;">C选项</td>
      		<td align="center" style="word-break:keep-all;">D选项</td>
      		<td align="center" style="word-break:keep-all;">图片</td>
      		<td align="center" style="word-break:keep-all;">答案</td>
      		<td align="center" style="word-break:keep-all;">难度</td>
      		<td align="center" style="word-break:keep-all;">题型</td>
      		<td></td>
      	</tr>
<%
        if (totalPages>=1) {
           if(testBean.getCurrentPage()<1)
              testBean.setCurrentPage(testBean.getTotalPages());
           if(testBean.getCurrentPage()>testBean.getTotalPages())
              testBean.setCurrentPage(1);
           int index=(testBean.getCurrentPage()-1)*pageSize;
           int start=index;  //table的currentPage页起始位置
           for (int i=index;i<pageSize+index;i++) { 
               if (i==totalRecord) break;
%>
		<tr>
			<td align="center"><%=Integer.parseInt(table[i][0])%1000 %></td>
			<td><%=table[i][1] %></td>
			<td><%=table[i][2] %></td>
			<td><%=table[i][3] %></td>
			<td>
<%				if(!table[i][4].equals("0")){ %><%=table[i][4] %><% }
%>			</td>
			<td>
<%				if(!table[i][5].equals("0")){ %><%=table[i][5] %><% }
%>			</td>
			<td align="center">
<%				if(table[i][6].equals("1")){ %><img src="upload/<%=table[i][0] %>.png" align="middle" style="max-height:40px;max-width:80px;"/><%}
%>			</td>
			<td align="center"><%=table[i][7].toUpperCase() %></td>
			<td align="center">
<%				if(table[i][8].equals("1")){ %><img src="images/student/hard1.png" style="height:15px;" /><% }
				if(table[i][8].equals("2")){ %><img src="images/student/hard2.png" style="height:15px;" /><% }
				if(table[i][8].equals("3")){ %><img src="images/student/hard3.png" style="height:15px;" /><% }
%>			</td>
			<td align="center">
<%				if(table[i][9].equals("1")){ %>单选<% }
				if(table[i][9].equals("2")){ %>判断<% }
				if(table[i][9].equals("3")){ %>多选<% }
%>			</td>
			<td>
				<form id="changeQuestion" action="ChangeQuestion.jsp">
					<input type="hidden" value=<%=table[i][0] %> name="number" />
					<input type="hidden" value=<%=table[i][1] %> name="content" />
					<input type="hidden" value=<%=table[i][2] %> name="choiceA" />
					<input type="hidden" value=<%=table[i][3] %> name="choiceB" />
					<input type="hidden" value=<%=table[i][4] %> name="choiceC" />
					<input type="hidden" value=<%=table[i][5] %> name="choiceD" />
					<input type="hidden" value=<%=table[i][6] %> name="pic" />
					<input type="hidden" value=<%=table[i][7] %> name="answer" />
					<input type="hidden" value=<%=table[i][8] %> name="difficulty" />
					<input type="hidden" value=<%=table[i][9] %> name="type" />
					<input type='submit' value='修改' />
				</form>
			</td>
		</tr>
<%
           }
        }
%>
     </table>

<%
	currentPage=testBean.getCurrentPage();
	totalPages=testBean.getTotalPages();
%>
   <div style="margin-top:10px; float:left; width:900px;">
   <table>
      <tr>
<%
	if(currentPage>1){
%>
          <td>  
          	<Form action="" method=post>
              <input type=hidden name="currentPage" value="<%=currentPage-1 %>">
              <input type=submit name="g" value="<上一页">&nbsp;&nbsp;&nbsp;&nbsp;
            </Form>
          </td>
<%
	}
	for(int i=1; i<currentPage; i++){
%>
          <td>
             <Form action="" method=post>
               <input type=hidden value=<%=i %> name="currentPage">
               <input type=submit name="g" value=<%=i %>>
             </Form>
          </td>
<%
	}
%>
          <td>
          	 &nbsp;<font color=blue><%=currentPage %></font>&nbsp;
          </td>
<%	
	for(int i=currentPage+1; i<=totalPages; i++){
%>
    <td>
       <Form action="" method=post>
         <input type=hidden value=<%=i %> name="currentPage">
         <input type=submit name="g" value=<%=i %>>
       </Form>
    </td>
<%
	}
	if(currentPage<totalPages){
%>
          <td>  
          	<Form action="" method=post>
              <input type=hidden name="currentPage" value="<%=currentPage+1 %>">
              &nbsp;&nbsp;&nbsp;&nbsp;<input type=submit name="g" value="下一页>">
            </Form>
          </td>
<%
	}
%>          
      </tr>
    </table>
    <table>
      <tr>
          <td> 
          	<Form action="" method=post>
             	每页显示数量：
             	<input type=text name="pageSize" value=<%=pageSize %> size=1>
             	<input type=submit name="g" value="确定">
             </Form>
          </td>
       </tr>
    </table>
    </div>
    </center>
    <div style="float:right;">
    	禁止删除
<%if(testBean.getShowQuestionsMess().contains("科目四")){%><br /><a href="CheckKemu4.jsp"><input style="font-size:20px;" type="submit" value="退出" /></a>
<%} else{ %><br /><a href="CheckKemu1.jsp"><input style="font-size:20px;" type="submit" value="退出" /></a><%} %>
    </div>
    <div class="cleaner"></div>
    <br /><br />
    
<div class="footer">
	<p>地址：中国深圳市南山区华侨城暨南大学深圳校区&nbsp;&nbsp;&nbsp;&nbsp;邮编：518000</p>
    <p>Email:krist.zhuzikai@gmail.com</p>
	<p>Copyright &copy;2018-2019 电子商务工作室 All rights reserved</p>
</div>
</div>
</Body>
<%
} catch(Exception e){
	System.out.println("CheckShow.jsp  "+e);
	testBean.setBackNews("系统异常，请重新登陆");
	response.sendRedirect("Index.jsp");
}
%>
</Html>
