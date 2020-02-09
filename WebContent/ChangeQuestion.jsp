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

String number = "";	
String content = "";
String choiceA = "";
String choiceB = "";
String choiceC = "";
String choiceD = "";
String pic = "";
String answer = "";
String difficulty = "";
String type = "";
number = testBean.getChangeQuestionNumber();	
content = testBean.getChangeQuestionContent();
choiceA = testBean.getChangeQuestionChoiceA();
choiceB = testBean.getChangeQuestionChoiceB();
choiceC = testBean.getChangeQuestionChoiceC();
choiceD = testBean.getChangeQuestionChoiceD();
pic = testBean.getChangeQuestionPic();
answer = testBean.getChangeQuestionAnswer();
difficulty = testBean.getChangeQuestionDifficulty();
type = testBean.getChangeQuestionType();
if(number==null||number.length()==0){
	number = request.getParameter("number");	
	testBean.setChangeQuestionNumber(number);
}
if(content==null||content.length()==0){
	content = request.getParameter("content");
	testBean.setChangeQuestionContent(content);
}
if(choiceA==null||choiceA.length()==0){
	choiceA = request.getParameter("choiceA");
	testBean.setChangeQuestionChoiceA(choiceA);
}
if(choiceB==null||choiceB.length()==0){
	choiceB = request.getParameter("choiceB");
	testBean.setChangeQuestionChoiceB(choiceB);	
}
if(choiceC==null||choiceC.length()==0){
	choiceC = request.getParameter("choiceC");
	testBean.setChangeQuestionChoiceC(choiceC);
}
if(choiceD==null||choiceD.length()==0){
	choiceD = request.getParameter("choiceD");
	testBean.setChangeQuestionChoiceD(choiceD);
}
if(pic==null||pic.length()==0){
	pic = request.getParameter("pic");
	testBean.setChangeQuestionPic(pic);
}
if(answer==null||answer.length()==0){
	answer = request.getParameter("answer");
	testBean.setChangeQuestionAnswer(answer);
}
if(difficulty==null||difficulty.length()==0){
	difficulty = request.getParameter("difficulty");
	testBean.setChangeQuestionDifficulty(difficulty);
}
if(type==null||type.length()==0){
	type = request.getParameter("type");
	testBean.setChangeQuestionType(type);
}
String kemu = Integer.parseInt(number)/1000<10?"1":"4";
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
        		<a href="AddKemu1MultipleChoice.jsp"><img src="images/teacher/tianjiatimu.png" /></a>
        	</div>
        	<div class="class_2">
        		<a href="" class="selected"><img src="images/teacher/chakantimu.png" /></a>
        	</div>
        <div class="class_1">
        	<a href="TeacherMore.jsp"><img src="images/student/gengduo.png"/></a>
        </div>
	</div>	<!--left-->
    
    <div id="middle">
    	<div class="navi_left"></div>
		<div class="navi">
<%
			if(kemu.equals("1")){
%>
       		<ul>
                <li class="selected"><a href="">科目一</a></li>
                <li><a href="CheckKemu4.jsp">科目四</a></li>
        	</ul>
<%
			} else{
%>
	       	<ul>
                <li><a href="CheckKemu1.jsp">科目一</a></li>
                <li class="selected"><a href="">科目四</a></li>
        	</ul>
<%				
			}
%>
    	</div>
        <div class="navi_right"></div>
        <div class="cleaner"></div><!--navi-->
        
        <div id="tab_middle">
        	<div class="changeQuestion">
            	<div class="changeQuestionTop"></div>
                <div class="itemChangeQuestion">
                	<div class="itemChangeQuestionContent">
                		<form action="changeQuestionsServlet?dataBase=licence" method=post onsubmit="return Submit();">
                			<input type="hidden" name="id" value=<%=testBean.getId()%> />
                			<input type="hidden" name="number" value=<%=number %> />
                			<table class="addMultipleChoice">
                				<tr>
                					<td>题型：</td>
                					<td>
                					<%if(type.equals("2")){ %>
                						<input type="radio" name="type" value="1" />单选
                						<input type="radio" name="type" value="2" checked />判断
                						<input type="radio" name="type" value="3" />多选
                					<%} else if(type.equals("1")){ %>
                						<input type="radio" name="type" value="1" checked />单选
                						<input type="radio" name="type" value="2" />判断
                						<input type="radio" name="type" value="3" />多选
                					<%} else{ %>
                						<input type="radio" name="type" value="1" />单选
                						<input type="radio" name="type" value="2" />判断
                						<input type="radio" name="type" value="3" checked />多选
                					<%} %>
                					</td>
                				</tr>
                				<tr>
                					<td>章节：</td>
                					<td style="font-size:18px;">
<%
										String chapter = Integer.parseInt(number)/1000+"";
										if(chapter==null||chapter.length()==0) chapter="0";
										if(chapter.equals("1")){ %>第1章 道路交通安全法律、法规和规章<%
										} else if(chapter.equals("2")){ %>第2章 交通信号<%
										} else if(chapter.equals("3")){ %>第3章 安全行车、文明驾驶基础知识<%
										} else if(chapter.equals("4")){ %>第4章 机动车驾驶操作相关基础知识<%
										} else if(chapter.equals("11")){ %>第1章 违法行为综合判断与案例分析<%
										} else if(chapter.equals("12")){ %>第2章 安全行车常识<%
										} else if(chapter.equals("13")){ %>第3章 常见交通标志、标线和交通手势辨识<%
										} else if(chapter.equals("14")){ %>第4章 驾驶职业道德和文明驾驶常识<%
										} else if(chapter.equals("15")){ %>第5章 恶劣气候和复杂道路条件下驾驶常识<%
										} else if(chapter.equals("16")){ %>第6章 紧急情况下避险常识<%
										} else if(chapter.equals("17")){ %>第7章 交通事故救护及常见危化品处置常识<%
										} else{ %>error<%
										}
%>
										<br />（不允许修改所在科目和章节）
										<input type="hidden" name="chapter" value=<%="chapter"+chapter %> />
              						</td>
                				</tr>
                				<tr>
                					<td>内容：</td>
                					<td><textarea id="content" name="content" rows="3" style="width:330px; font-size:20px;"><%=content %></textarea></td>
                				</tr>
                				<tr>
                					<td>A选项：</td>
                					<td><input id="choiceA" style="font-size:20px;" type="text" name="choiceA" value=<%=choiceA %> /></td>
                				</tr>
                				<tr>
                					<td>B选项：</td>
                					<td><input id="choiceB" style="font-size:20px;" type="text" name="choiceB" value=<%=choiceB %> /></td>
                				</tr>
                				<tr>
                					<td>C选项：</td>
                					<%if(!choiceC.equals("0")){ %><td><input id="choiceC" style="font-size:20px;" type="text" name="choiceC" value=<%=choiceC %> /></td>
                					<%} else{ %><td><input id="choiceC" style="font-size:20px;" type="text" name="choiceC" /></td>
                					<%} %>
                				</tr>
                				<tr>
                					<td>D选项：</td>
                					<%if(!choiceD.equals("0")){ %><td><input id="choiceD" style="font-size:20px;" type="text" name="choiceD" value=<%=choiceD %> /></td>
                					<%} else{ %><td><input id="choiceD" style="font-size:20px;" type="text" name="choiceD" /></td>
                					<%} %>
                				</tr>
                				<%if(pic.equals("1")){ %>
                				<tr>
                					<td>图片：</td>
                					<td>
                						<img src="upload/<%=number %>.png" align="middle" style="max-height:150px;max-width:300px;"/><br />
                						<input type="radio" name="changePic" value="remain1" checked />不变
                						<input type="radio" name="changePic" value="delete" />删除
                						<input type="radio" name="changePic" value="change" />修改
                						<br /><font style="font-size:18px;">（如需修改，先点确认提交，再重新上传图片）</font>
                					</td>
                				</tr>
                				<%} else{ %>
                				<tr>
                					<td>图片：</td>
                					<td>
                						<input type="radio" name="changePic" value="remain0" checked />不变
                						<input type="radio" name="changePic" value="add" />增加
                						<br /><font style="font-size:18px;">（如需增加，先点确认提交，再重新上传图片）</font>
                					</td>
                				</tr>
                				<%} %>
                				<tr>
                					<td>答案：</td>
                					<td>
                					<%if(answer.contains("a")||answer.contains("A")){ %>
                						<input type="checkbox" name="answer" value="a" checked />A
                					<%} else { %>
                						<input type="checkbox" name="answer" value="a" />A
                					<%} 
                					if(answer.contains("b")||answer.contains("B")){ %>
            							<input type="checkbox" name="answer" value="b" checked />B
            						<%} else { %>
            							<input type="checkbox" name="answer" value="b" />B
            						<%} 
                					if(answer.contains("c")||answer.contains("C")){ %>
        								<input type="checkbox" name="answer" value="c" checked />C
        							<%} else { %>
        								<input type="checkbox" name="answer" value="c" />C
        							<%} 
            						if(answer.contains("d")||answer.contains("D")){ %>
    									<input type="checkbox" name="answer" value="d" checked />D
    								<%} else { %>
    									<input type="checkbox" name="answer" value="d" />D
    								<%} %>
                					</td>
                				</tr>
                				<tr>
                					<td>难度：</td>
                					<td>
                					<%if(difficulty.equalsIgnoreCase("3")){ %>
                						<input type="radio" name="difficulty" value="1" /><img src="images/student/hard1.png" style="height:20px;" />
                						<input type="radio" name="difficulty" value="2" /><img src="images/student/hard2.png" style="height:20px;" />
                						<input type="radio" name="difficulty" value="3" checked /><img src="images/student/hard3.png" style="height:20px;" />
                					<%} else if(difficulty.equalsIgnoreCase("2")){ %>
                						<input type="radio" name="difficulty" value="1" /><img src="images/student/hard1.png" style="height:20px;" />
                						<input type="radio" name="difficulty" value="2" checked /><img src="images/student/hard2.png" style="height:20px;" />
                						<input type="radio" name="difficulty" value="3" /><img src="images/student/hard3.png" style="height:20px;" />
                					<%} else{ %>
                						<input type="radio" name="difficulty" value="1" checked /><img src="images/student/hard1.png" style="height:20px;" />
                						<input type="radio" name="difficulty" value="2" /><img src="images/student/hard2.png" style="height:20px;" />
                						<input type="radio" name="difficulty" value="3" /><img src="images/student/hard3.png" style="height:20px;" />
                					<%} %>
                					</td>
                				</tr>
                			</table>
                			<br /><div class="submit"><input type="submit" class="inputSubmit" value="" name="tijiao" /></div>
                		</form>
<script>
function Submit(){
	return confirm("修改后不可撤销，是否确认？");	
}
</script>
                		<div class="others">
        					<!--<div class="align"></div>-->                            
       						<div class="other"><a href="CheckShow.jsp"><img src="images/student/quitButton.png" /></a></div>
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

</div>

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
	System.out.println("ChangeQuestion.jsp  "+e);
	testBean.setBackNews("系统异常，请重新登陆");
	response.sendRedirect("Index.jsp");
}
%>
</Html> 
