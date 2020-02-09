<%@page import="javax.servlet.jsp.tagext.TryCatchFinally"%>
<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>驾照标准化考试系统</title>
<link href="style/more.css" rel="stylesheet" type="text/css" />
<link href="style/common.css" rel="stylesheet" type="text/css" />
<jsp:useBean id="testBean" class="mybean.data.Test_Bean" scope="session"/>
</head>

<%
try{
boolean isTeacher = testBean.getIsTeacher();
if(!isTeacher){
	response.sendRedirect("Index.jsp");
}
%>

<body onload="Notify();">
<div id="wrapper">
<div id="top">
	<jsp:getProperty property="name" name="testBean"/>，你好！
    <a href="Index.jsp"><input id="log" type="submit" class="inputSubmit" value="登出" name="logout"></a>
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
                	<button type="submit" class="inputSubmit" formtarget="_blank"><img src="images/student/search_button.png" /></button>
            	</td>
            	</form>
                </tr></table>
            </div>
        </div><!--search -->
    </div><!--right-->    
</div><!--head -->
<div class="cleaner"></div>

<div id="content">
	<div id="left">
        <div class="class_1" style="width:110px">
        	<a href="" style="width:110px"><img src="images/teacher/xueshengguanli.png"/></a>
        </div>
        	<div class="class_2">
        		<a href="CheckRegister.jsp"><img src="images/teacher/shenhezhuce.png" /></a>
        	</div>
        	<div class="class_2">
        		<a href="CheckScore.jsp"><img src="images/teacher/chakanchengji.png" /></a>
        	</div>
        <div class="class_1" style="width:110px">
        	<a href="" style="width:110px"><img src="images/teacher/shezhitimu.png"/></a>
        </div>
        	<div class="class_2">
        		<a href="AddKemu1MultipleChoice.jsp"><img src="images/teacher/tianjiatimu.png" /></a>
        	</div>
        	<div class="class_2">
        		<a href="CheckKemu1.jsp"><img src="images/teacher/chakantimu.png" /></a>
        	</div>
        <div class="class_1">
        	<a href="#" class="selected"><img src="images/student/gengduo.png"/></a>
        </div>
	</div>
	<p>
	  <!--left-->
	</p>
	<p>&nbsp; </p>
	<div id="middle">
        <div id="notice">
           	<div class="noticeTop"></div>
           	<div class="itemNotice">
<%
try{
	String uri="jdbc:mysql://127.0.0.1/licence?"+"user=root&password=&characterEncoding=gb2312";
	Connection con=DriverManager.getConnection(uri);
    Statement sql=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
    ResultSet rs;
    rs=sql.executeQuery("SELECT * FROM notice");
    int noticeNumber;
    try{
    	rs.last();
    	noticeNumber = rs.getRow();
    } catch(Exception e){
    	noticeNumber = 0;
    }
    int totalPage = noticeNumber%2==0 ? noticeNumber/2 : noticeNumber/2+1;
    if(noticeNumber>=1){
    		String nowPage2 = request.getParameter("page");
    		int nowPage;
    		try{
    			nowPage=Integer.parseInt(nowPage2);
    		} catch(Exception e){
    			nowPage=1;
    		}
    		int startNumber = noticeNumber-nowPage*2+2;
    		int endNumber = noticeNumber-nowPage*2+1;
    		//rs=sql.executeQuery("SELECT * FROM notice WHERE number <= " + startNumber + " AND number >= " + endNumber);
    		for(int i=startNumber; i>=endNumber; i--){
    			try{
    	    		rs.absolute(i);
    	    		String teacher = rs.getString(4);
    	    		String id = rs.getString(3);
    	    		String content = rs.getString(2);
    	    		String number = rs.getString(1);
%>
    	    		<div class="doc"><img src="images/teacher/doc.png" /></div>
    	    		<div id="noticeInfo"><%=teacher %> 发表公告：
    	    			<div style="float:right;">
    	    		    	<form action="noticeServlet" style="display:inline;">
    	    		    		<input type="hidden" name="isDelete" value="true" />
    	    		    		<input type="hidden" name="isNew" value="true" />
    	    		    		<input type="hidden" name="deleteNumber" value=<%=number %> />
    	    					<input style="margin:0px;" type="submit" value="删除" />
    	    				</form>
    	    			</div>
    	    			<div class="cleaner"></div>
	    				<div id="noticeContent">&nbsp;&nbsp;&nbsp;<%=content %><br /></div>
	    			</div>
<%
    			} catch(Exception e){
    				continue;
    			}
    		}

    		if(nowPage>1){
%>
				<div style="float:left; margin-left:50px;">
	    	    	<Form action="">
	    	        	<input type=hidden name="page" value="<%=nowPage-1 %>">
	    	            <input type=submit value="<上一页">&nbsp;&nbsp;&nbsp;&nbsp;
	    	        </Form>
	    	     </div>   
<%
    		}
			if(nowPage<totalPage){
%>
				<div style="float:right; margin-right:100px;">
				  	<td>
						<form action="">
							<input type="hidden" value=<%=nowPage+1 %> name="page" />
             				&nbsp;&nbsp;&nbsp;&nbsp;<input type=submit value="下一页>">
						</form>
				  	</td>
				</div>
<%
			}
%>
			<div class="cleaner"></div>
<%
    } else{
    	%><div id="noticeInfo">暂无公告</div><%
    }
} catch(Exception e){
	System.out.println("More.jsp  "+e);
}
%>
                <div class="addNotice" style="margin-left:30px; margin-top:20px;">
                	<div style="height:30px; font-size:20px;">发表新公告</div>
	            	<form action="noticeServlet">
	            		<input type="hidden" name="id" value=<%=testBean.getId() %> />
	            		<input type="hidden" name="name" value=<%=testBean.getName() %> />
	            		<input type="hidden" name="isNew" value="true" />
	            		<input type="hidden" name="isDelete" value="false" />
	            		<textarea name="content" rows="3" style="width:420px; font-size:20px;"></textarea>
	            		<input type="submit" value="提交" style="float:right; margin-right:50px;" />
	            	</form>
	            	<div class="cleaner"></div>
                </div>
                </div>
            	<div class="bottom"></div>
        </div><!--p/e Question-->
        
        <div id="message">
        	<div class="messageTop"></div>
            <div class="itemMessage">
<%
try{
	String uri="jdbc:mysql://127.0.0.1/licence?"+"user=root&password=&characterEncoding=gb2312";
	Connection con=DriverManager.getConnection(uri);
    Statement sql=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
    ResultSet rs;
    rs=sql.executeQuery("SELECT * FROM message");
    int messageNumber;
    try{
    	rs.last();
    	messageNumber = rs.getRow();
    } catch(Exception e){
    	messageNumber = 0;
    }
    int totalMessagePage = messageNumber%5==0 ? messageNumber/5 : messageNumber/5+1;
    if(messageNumber>=1){
    		String nowMessagePage2 = request.getParameter("messagePage");
    		int nowMessagePage;
    		try{
    			nowMessagePage=Integer.parseInt(nowMessagePage2);
    		} catch(Exception e){
    			nowMessagePage=1;
    		}
    		int startNumber = messageNumber-nowMessagePage*5+5;
    		int endNumber = messageNumber-nowMessagePage*5+1;
    		//rs=sql.executeQuery("SELECT * FROM notice WHERE number <= " + startNumber + " AND number >= " + endNumber);
    		String[] number = new String[messageNumber];
    		String[] user = new String[messageNumber];
    		String[] content = new String[messageNumber];
    		String[] role = new String[messageNumber];
    		for(int i=startNumber; i>=endNumber; i--){
    			if(i<1) continue;
    			try{
    	    		rs.absolute(i);
    	    		number[i-1] = rs.getString(1);
    	    		user[i-1] = rs.getString(4);
    	    		content[i-1] = rs.getString(2);
    	    		role[i-1] = rs.getString(5).equals("1") ? "学生" : "教师";
    			} catch(Exception e){
    				System.out.println("1 "+e);
    			}
    		}
    		for(int i=startNumber; i>=endNumber; i--){
    			if(i<1) continue;
    			try{
%>
    	    		<div class="doc"><img src="images/teacher/doc.png" /></div>
    	    		<div id="noticeInfo"><%=user[i-1] %> （<%=role[i-1] %>） 发表留言：
    	    			<div style="float:right;">
    	    				<form action="messageServlet" style="display:inline;">
    	    					<input type="hidden" name="delete" value="true" />
    	    					<input type="hidden" name="deleteNumber" value=<%=number[i-1] %> />
    	    					<input style="margin:0px;" type="submit" value="删除" />
    	    				</form>
    	    				<form action="" style="display:inline;">
    	    					<input type="hidden" name="messageNumber" value=<%=number[i-1] %> />
    	    					<input type="hidden" name="messageUser" value=<%=user[i-1] %> />
    	    					<input style="margin:0px;" type="submit" value="回复" />
    	    				</form>
    	    			</div>
	    	    		<div class="cleaner"></div>
	    				<div id="noticeContent">&nbsp;&nbsp;&nbsp;<%=content[i-1] %><br /></div>
	    			</div>
<%
					try{
						ResultSet rs2 = sql.executeQuery("SELECT * FROM messageFollow WHERE belongNumber = " + number[i-1]);
						rs2.afterLast();
						while(rs2.previous()){
							String followNumber = rs2.getString(1);
							String followUser = rs2.getString(5);
							String followContent = rs2.getString(3);
							String followRole = rs2.getString(6).equals("1") ? "学生" : "教师";
							String followSequence = rs2.getString(2);
%>
	    	    			<div class="doc" style="padding-left:20px;"><img src="images/teacher/line.png" /></div>
	    	    			<div id="noticeInfo"><%=followUser %> （<%=followRole %>） 回复留言：
		    					<div style="float:right;">
    	    						<form action="messageServlet" style="display:inline;">
    	    							<input type="hidden" name="delete" value="true" />
    	    							<input type="hidden" name="deleteFollowNumber" value=<%=followNumber %> />
    	    							<input type="hidden" name="deleteSequence" value=<%=followSequence %> />
    	    							<input style="margin:0px;" type="submit" value="删除" />
    	    						</form>
    	    					</div>
	    	    				<div class="cleaner"></div>
		    					<div id="noticeContent" style="padding-left:20px; width:400px;">&nbsp;&nbsp;&nbsp;<%=followContent %><br /></div>
		    				</div>
<%
						}
					} catch(Exception e){
						System.out.println("2 "+e);
					}
%>
					<div style="height:10px;"></div>
					<div style="height:10px; border-top:1px solid #999; width:450px; margin-left:20px;"></div>
<%
    			} catch(Exception e){
    				System.out.println("3 "+e);
    				continue;
    			}
    		}

    		if(nowMessagePage>1){
%>
				<div style="float:left; margin-left:50px;">
	    	    	<Form action="">
	    	        	<input type=hidden name="messagePage" value="<%=nowMessagePage-1 %>">
	    	            <input type=submit value="<上一页">&nbsp;&nbsp;&nbsp;&nbsp;
	    	        </Form>
	    	     </div>   
<%
    		}
			if(nowMessagePage<totalMessagePage){
%>
				<div style="float:right; margin-right:100px;">
				  	<td>
						<form action="">
							<input type="hidden" value=<%=nowMessagePage+1 %> name="messagePage" />
             				&nbsp;&nbsp;&nbsp;&nbsp;<input type=submit value="下一页>">
						</form>
				  	</td>
				</div>
<%
			}
%>
			<div class="cleaner"></div>
<%
    } else{
    	%><div id="noticeInfo">暂无留言</div><%
    }
} catch(Exception e){
	System.out.println("More.jsp  "+e);
}
%>
	            <a name="new"></a>
	            <div class="messageReply" style="margin-left:30px; margin-top:20px;">
	            	<div style="height:30px; font-size:20px;">
<%
						String messageNumber = "";
						String messageUser = "";
						boolean isReply = false;
						try{
							messageNumber = request.getParameter("messageNumber");
							messageUser = request.getParameter("messageUser");
							if(messageNumber.length()>0){
								isReply=true;
								%>回复 <%=messageUser %> 的留言<div id="newOrReply" style="display:none;">0</div><%
							} 
						} catch(Exception e){
							%>发表留言<div id="newOrReply" style="display:none;">1</div><%
						}
%>
	            	</div>
	            	<form action="messageServlet">
	            		<input type="hidden" name="delete" value="false" />
	            		<input type="hidden" name="isReply" value=<%=isReply %> />
	            		<input type="hidden" name="name" value=<%=testBean.getName() %> />
	            		<input type="hidden" name="id" value=<%=testBean.getId() %> />
	            		<input type="hidden" name="messageNumber" value=<%=messageNumber %> />
	            		<input type="hidden" name="messageUser" value=<%=messageUser %> />
	            		<input type="hidden" name="role" value="2" />
	            		<textarea name="content" rows="3" style="width:420px; font-size:20px;"></textarea>
	            		<input type="submit" value="提交" style="float:right; margin-right:50px;" />
	            	</form>
	            	<div class="cleaner"></div>
	            </div>
            </div>
            <div class="bottom"></div>
        </div>
	</div><!--middle -->
    
    <div id="right">
    	<div id="info">
        	<div class="infoTop"></div>
            <div class="itemInfo">
            	<div class="itemInfoLeft">
                	<div id="photo">
                    	<img src="image/photo/student/default.png" height="100" width="80" />
                    </div>
                </div>
                <div class="itemInfoRight">
                	姓名：<jsp:getProperty name="testBean" property="name" /><br />
                	编号：<jsp:getProperty name="testBean" property="id" />
                </div>
                <div class="cleaner"></div>
            </div>
        	<div class="bottom"></div>
        </div>

        <div id="number">
        	
        </div>
    </div><!--right -->
</div><!--content -->
<div class="cleaner"></div>
<div class="height"></div>

<div id="backNews" style="color:#fff"><jsp:getProperty name="testBean" property="messageMess" /></div>  
<script>
var a=document.getElementById("backNews").innerText;
var b=document.getElementById("newOrReply").innerText;
function Notify(){
	if(a.length!=0&&a!="null")	alert(a);
	if(b=="0") window.location.href="#new";
}
</script>
<jsp:setProperty name="testBean" property="messageMess" value="" />

<div class="footer">
	<p>地址：中国深圳市南山区华侨城暨南大学深圳校区&nbsp;&nbsp;&nbsp;&nbsp;邮编：518000</p>
    <p>Email:krist.zhuzikai@gmail.com</p>
	<p>Copyright &copy;2018-2019 电子商务工作室 All rights reserved</p>
</div>
<!--wrapper -->
</div>

</body>
<%
} catch(Exception e){
	System.out.println("More.jsp  "+e);
	testBean.setBackNews("系统异常，请重新登陆");
	response.sendRedirect("Index.jsp");
}
%>
</html>