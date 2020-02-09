<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>驾照标准化考试系统</title>
<link href="style/set.css" rel="stylesheet" type="text/css" />
<link href="style/common.css" rel="stylesheet" type="text/css" />
<jsp:useBean id="testBean" class="mybean.data.Test_Bean" scope="session"/>
</head>

<%
try{
boolean isTeacher = testBean.getIsTeacher();
if(!isTeacher){
	response.sendRedirect("Index.jsp");
}

String dataBase = testBean.getAddQuestionDataBase();
String tableName = testBean.getAddQuestionTableName();
String type = testBean.getAddQuestionType();
String content = testBean.getAddQuestionContent();
String choiceA = testBean.getAddQuestionChoiceA();
String choiceB = testBean.getAddQuestionChoiceB();
String choiceC = testBean.getAddQuestionChoiceC();
String choiceD = testBean.getAddQuestionChoiceD();
String answer = testBean.getAddQuestionAnswer();
String pic = testBean.getAddQuestionPic();
String difficulty = testBean.getAddQuestionDifficulty();
String kemu = Integer.parseInt(tableName.substring(7))<10?"1":"4";
%>

<body onload="Notify();">
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
        	<a href="#" class="selected" style="width:110px"><img src="images/teacher/shezhitimu.png"/></a>
        </div>
        	<div class="class_2">
        		<a href="" class="selected"><img src="images/teacher/tianjiatimu.png" /></a>
        	</div>
        	<div class="class_2">
        		<a href="CheckKemu1.jsp"><img src="images/teacher/chakantimu.png" /></a>
        	</div>
        <div class="class_1">
        	<a href="TeacherMore.jsp"><img src="images/student/gengduo.png"/></a>
        </div>
	</div>	<!--left-->
   
    <div id="middle">
    	<div class="navi_left"></div>
		<div class="navi">
       		<ul>
       			<%if(kemu.equals("1")){ %>
                <li class="selected"><a href="">科目一</a></li>
                <li><a href="Kemu4.jsp">科目四</a></li>
                <%} else{ %>
                <li><a href="Kemu1.jsp">科目一</a></li>
                <li class="selected"><a href="">科目四</a></li>
                <%} %>
        	</ul>
    	</div>
        <div class="navi_right"></div>
        <div class="cleaner"></div><!--navi-->
        
        <div id="tab_middle">
        	<div class="addQuestion">
            	<div class="addQuestionTop"></div>
                <div class="itemAddQuestion">
                	<div class="itemAddQuestionContent">
                		<form method="post" action="uploadServlet" enctype="multipart/form-data">
                			<input type="hidden" name="dataBase" value=<%=dataBase %> />
                			<input type="hidden" name="tableName" value=<%=tableName %> />
                			<input type="hidden" name="content" value=<%=content %> />
                			<input type="hidden" name="choiceA" value=<%=choiceA %> />
                			<input type="hidden" name="choiceB" value=<%=choiceB %> />
                			<input type="hidden" name="choiceC" value=<%=choiceC %> />
                			<input type="hidden" name="choiceD" value=<%=choiceD %> />
                			<input type="hidden" name="answer" value=<%=answer %> />
                			<input type="hidden" name="difficulty" value=<%=difficulty %> />
                			<input type="hidden" name="type" value=<%=type %> />
                			<table class="addMultipleChoice" style="border-spacing:0px 0px;">
                				<tr>
                					<td style="word-break:keep-all;">题型：</td>
                					<td>
			                			<%if(type.equals("1")){ %>选择<%} %>
			                			<%if(type.equals("2")){ %>判断<%} %>
			                			<%if(type.equals("3")){ %>多选<%} %>
			                		</td>
			                	</tr>
			                	<tr>
                					<td>章节：</td>
                					<td style="font-size:18px;">
			                			<%if(tableName.equals("chapter1")){ %>第1章 道路交通安全法律、法规和规章<%} %>
			                			<%if(tableName.equals("chapter2")){ %>第2章 交通信号<%} %>
			                			<%if(tableName.equals("chapter3")){ %>第3章 安全行车、文明驾驶基础知识<%} %>
			                			<%if(tableName.equals("chapter4")){ %>第4章 机动车驾驶操作相关基础知识<%} %>
			                			<%if(tableName.equals("chapter11")){ %>第1章 违法行为综合判断与案例分析<%} %>
			                			<%if(tableName.equals("chapter12")){ %>第2章 安全行车常识<%} %>
			                			<%if(tableName.equals("chapter13")){ %>第3章 常见交通标志、标线和交通手势辨识<%} %>
			                			<%if(tableName.equals("chapter14")){ %>第4章 驾驶职业道德和文明驾驶常识<%} %>
			                			<%if(tableName.equals("chapter15")){ %>第5章 恶劣气候和复杂道路条件下驾驶常识<%} %>
			                			<%if(tableName.equals("chapter16")){ %>第6章 紧急情况下避险常识<%} %>
			                			<%if(tableName.equals("chapter17")){ %>第7章 交通事故救护及常见危化品处置常识<%} %>
			                		</td>
			                	</tr>
			                	<tr>
                					<td>内容：</td>
                					<td><%=content %></td>
                				</tr>
                				<tr>
                					<td style="word-break:keep-all;">A选项：</td>
                					<td><%=choiceA %></td>
                				</tr>
                				<tr>
                					<td>B选项：</td>
                					<td><%=choiceB %></td>
                				</tr>
                			<%if(type.equals("1")){ %>
                				<tr>
                					<td>C选项：</td>
                					<td><%=choiceC %></td>
                				</tr>
                				<tr>
                					<td style="word-break:keep-all;">D选项：</td>
                					<td><%=choiceD %></td>
                				</tr>
                			<%} %>
                				<tr>
                					<td>答案：</td>
                					<td><%=answer.toUpperCase() %></td>
                				</tr>
                				<tr>
                					<td>难度：</td>
                					<td>
			                			<%if(difficulty.equals("1")){ %><img src="images/student/hard1.png" style="height:20px;" /><%} %>
									    <%if(difficulty.equals("2")){ %><img src="images/student/hard2.png" style="height:20px;" /><%} %>
									    <%if(difficulty.equals("3")){ %><img src="images/student/hard3.png" style="height:20px;" /><%} %>   
						    		</td>
						    	</tr>
						    </table>
						    <br />选择图片
						    <input type="file" name="uploadFile" />
						    <br/><br/>
						    <div class="submit"><input type="submit" class="inputSubmit" value="" name="tijiao" /></div>
						</form>
                		<div class="others">
        					<!--<div class="align"></div>-->                            
       						<div class="other"><a href="AddKemu1MultipleChoice.jsp"><img src="images/student/quitButton.png" /></a></div>
    					</div>
    					<div class="cleaner"></div>	
                    </div>
                </div>
                <div class="bottom"></div>
            </div>
        </div><!--tab_middle-->
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
                	姓名：<jsp:getProperty property="name" name="testBean"/><br />
                                    编号：<jsp:getProperty property="id" name="testBean"/>
                </div>
                <div class="cleaner"></div>
            </div>
        	<div class="bottom"></div>
        </div>
    </div>
    
<div id="backNews" style="color:#fff"><jsp:getProperty name="testBean" property="addFailMess" /></div>  
<script>
	var a=document.getElementById("backNews").innerText;
	function Notify(){
		if(a.length!=0&&a!="null") alert(a);
	}
</script>

<!--
    <div id="indexMain">
    	<jsp:setProperty name="testBean" property="score" value="0"/>
    	<jsp:setProperty name="testBean" property="number" value="0"/>
    	<Form action="readTestServlet"  method="post" >
       		<br>考号：<jsp:getProperty property="id" name="testBean"/>
       		<br>姓名：<jsp:getProperty property="name" name="testBean"/>
       		<br><jsp:getProperty property="lastScoreKemu1" name="testBean"/>
       		<br><jsp:getProperty property="lastScoreKemu4" name="testBean"/>
       		<br><input type="hidden" value="<%=testBean.getId()%>" name ="id">
    	</Form>
    </div>
-->
    
</div>
<!--
  <div class="all">
   <div class="top"><img src="image/style/top.jpg"></div>
   <div class="b"><div class="left">
    <h3 align="center"><br><br>当前为学生界面</h3>
   </div>
   <div class="main">
    <jsp:setProperty name="testBean" property="score" value="0"/>
    <jsp:setProperty name="testBean" property="number" value="0"/>
    <Form action="readTestServlet"  method="post" >
       <br>考号：<jsp:getProperty property="id" name="testBean"/>
       <br>姓名：<jsp:getProperty property="name" name="testBean"/>
       <br><input  class="inputSubmit" type=submit name="sub" value="确认信息并开始考试">
       <br><a href="Enter.jsp"><input  class="inputSubmit" type="submit" value="返回"/></a>
       <br><input type="hidden" value="<%=testBean.getId()%>" name ="id">
    </Form>
   </div>
  </div>
  </div>
-->

<div class="cleaner"></div>
<div class="height"></div>

<div class="footer">
	<p>地址：中国深圳市南山区华侨城暨南大学深圳校区&nbsp;&nbsp;&nbsp;&nbsp;邮编：518000</p>
    <p>Email:krist.zhuzikai@gmail.com</p>
	<p>Copyright &copy;2018-2019 电子商务工作室 All rights reserved</p>
</div>
  
</div>
</Body>
<%
} catch(Exception e){
	System.out.println("UploadPic.jsp  "+e);
	testBean.setBackNews("系统异常，请重新登陆");
	response.sendRedirect("Index.jsp");
}
%>
</Html> 
