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
                <li><a href="AddKemu1Judge.jsp">科目一</a></li>
                <li class="selected"><a href="">科目四</a></li>
        	</ul>
    	</div>
        <div class="navi_right"></div>
        <div class="cleaner"></div><!--navi-->
        
        <div id="tab_middle">
        	<div class="addQuestion">
            	<div class="addQuestionTop"></div>
                <div class="itemAddQuestion">
                	<div class="itemAddQuestionContent">
                		<div id="changeType">
	                		<a href="AddKemu4MultipleChoice.jsp"><input class="unselectedType" type="submit" name="" value="" /></a><div id="changeTypeWord">单选</div>
	                		<a href=""><input class="selectedType" type="submit" name="" value="" /></a><div id="changeTypeWord">判断</div>
	                		<a href="AddKemu4Checkbox.jsp"><input class="unselectedType" type="submit" name="" value="" /></a><div id="changeTypeWord">多选</div>
	                	</div>
                		<form action="addQuestionsServlet?dataBase=licence" method=post onsubmit="return Submit();">
                			<input type="hidden" name="type" value="2" />
                			<input type="hidden" name="id" value=<%=testBean.getId()%> />
                			<table class="addMultipleChoice">
                				<tr>
                					<td>章节：</td>
                					<td style="font-size:18px;">
<%
	String chapter = testBean.getAddQuestionTableName();
	if(chapter==null||chapter.length()==0) chapter="0";
	if(chapter.equals("chapter12")){
%>
                						<input type="radio" name="chapter" value=chapter11 />第1章 违法行为综合判断与案例分析<br />
                						<input type="radio" name="chapter" value=chapter12 checked />第2章 安全行车常识<br />
                						<input type="radio" name="chapter" value=chapter13 />第3章 常见交通标志、标线和交通手势辨识<br />
                						<input type="radio" name="chapter" value=chapter14 />第4章 驾驶职业道德和文明驾驶常识<br />
                						<input type="radio" name="chapter" value=chapter15 />第5章 恶劣气候和复杂道路条件下驾驶常识<br />
                						<input type="radio" name="chapter" value=chapter16 />第6章 紧急情况下避险常识<br />
                						<input type="radio" name="chapter" value=chapter17 />第7章 交通事故救护及常见危化品处置常识<br />
<%
	} else if(chapter.equals("chapter13")){
%>
                						<input type="radio" name="chapter" value=chapter11 />第1章 违法行为综合判断与案例分析<br />
                						<input type="radio" name="chapter" value=chapter12 />第2章 安全行车常识<br />
                						<input type="radio" name="chapter" value=chapter13 checked />第3章 常见交通标志、标线和交通手势辨识<br />
                						<input type="radio" name="chapter" value=chapter14 />第4章 驾驶职业道德和文明驾驶常识<br />
                						<input type="radio" name="chapter" value=chapter15 />第5章 恶劣气候和复杂道路条件下驾驶常识<br />
                						<input type="radio" name="chapter" value=chapter16 />第6章 紧急情况下避险常识<br />
                						<input type="radio" name="chapter" value=chapter17 />第7章 交通事故救护及常见危化品处置常识<br />
<%
	} else if(chapter.equals("chapter14")){
%>
                						<input type="radio" name="chapter" value=chapter11 />第1章 违法行为综合判断与案例分析<br />
                						<input type="radio" name="chapter" value=chapter12 />第2章 安全行车常识<br />
                						<input type="radio" name="chapter" value=chapter13 />第3章 常见交通标志、标线和交通手势辨识<br />
                						<input type="radio" name="chapter" value=chapter14 checked />第4章 驾驶职业道德和文明驾驶常识<br />
                						<input type="radio" name="chapter" value=chapter15 />第5章 恶劣气候和复杂道路条件下驾驶常识<br />
                						<input type="radio" name="chapter" value=chapter16 />第6章 紧急情况下避险常识<br />
                						<input type="radio" name="chapter" value=chapter17 />第7章 交通事故救护及常见危化品处置常识<br />
<%
	} else if(chapter.equals("chapter15")){
%>
                						<input type="radio" name="chapter" value=chapter11 />第1章 违法行为综合判断与案例分析<br />
                						<input type="radio" name="chapter" value=chapter12 />第2章 安全行车常识<br />
                						<input type="radio" name="chapter" value=chapter13 />第3章 常见交通标志、标线和交通手势辨识<br />
                						<input type="radio" name="chapter" value=chapter14 />第4章 驾驶职业道德和文明驾驶常识<br />
                						<input type="radio" name="chapter" value=chapter15 checked />第5章 恶劣气候和复杂道路条件下驾驶常识<br />
                						<input type="radio" name="chapter" value=chapter16 />第6章 紧急情况下避险常识<br />
                						<input type="radio" name="chapter" value=chapter17 />第7章 交通事故救护及常见危化品处置常识<br />
<%
	} else if(chapter.equals("chapter16")){
%>
                						<input type="radio" name="chapter" value=chapter11 />第1章 违法行为综合判断与案例分析<br />
                						<input type="radio" name="chapter" value=chapter12 />第2章 安全行车常识<br />
                						<input type="radio" name="chapter" value=chapter13 />第3章 常见交通标志、标线和交通手势辨识<br />
                						<input type="radio" name="chapter" value=chapter14 />第4章 驾驶职业道德和文明驾驶常识<br />
                						<input type="radio" name="chapter" value=chapter15 />第5章 恶劣气候和复杂道路条件下驾驶常识<br />
                						<input type="radio" name="chapter" value=chapter16 checked />第6章 紧急情况下避险常识<br />
                						<input type="radio" name="chapter" value=chapter17 />第7章 交通事故救护及常见危化品处置常识<br />
<%
	} else if(chapter.equals("chapter17")){
%>
                						<input type="radio" name="chapter" value=chapter11 />第1章 违法行为综合判断与案例分析<br />
                						<input type="radio" name="chapter" value=chapter12 />第2章 安全行车常识<br />
                						<input type="radio" name="chapter" value=chapter13 />第3章 常见交通标志、标线和交通手势辨识<br />
                						<input type="radio" name="chapter" value=chapter14 />第4章 驾驶职业道德和文明驾驶常识<br />
                						<input type="radio" name="chapter" value=chapter15 />第5章 恶劣气候和复杂道路条件下驾驶常识<br />
                						<input type="radio" name="chapter" value=chapter16 />第6章 紧急情况下避险常识<br />
                						<input type="radio" name="chapter" value=chapter17 checked />第7章 交通事故救护及常见危化品处置常识<br />
<%
	} else{
%>
                						<input type="radio" name="chapter" value=chapter11 checked />第1章 违法行为综合判断与案例分析<br />
                						<input type="radio" name="chapter" value=chapter12 />第2章 安全行车常识<br />
                						<input type="radio" name="chapter" value=chapter13 />第3章 常见交通标志、标线和交通手势辨识<br />
                						<input type="radio" name="chapter" value=chapter14 />第4章 驾驶职业道德和文明驾驶常识<br />
                						<input type="radio" name="chapter" value=chapter15 />第5章 恶劣气候和复杂道路条件下驾驶常识<br />
                						<input type="radio" name="chapter" value=chapter16 />第6章 紧急情况下避险常识<br />
                						<input type="radio" name="chapter" value=chapter17 />第7章 交通事故救护及常见危化品处置常识<br />
<%
	}
%>
              						</td>
                				</tr>
                				<tr>
                					<td>内容：</td>
                					<td><textarea name="content" rows="3" style="width:330px; font-size:20px;"></textarea></td>
                				</tr>
                				<tr>
                					<td>A选项：</td>
                					<td><input type="text" name="choiceA" value="正确" /></td>
                				</tr>
                				<tr>
                					<td>B选项：</td>
                					<td><input type="text" name="choiceB" value="错误" /></td>
                				</tr>
                				<input type="hidden" name="choiceC" value="0" />
                				<input type="hidden" name="choiceD" value="0" />
                				<tr>
                					<td>图片：</td>
                					<td>
                						<input type="radio" name="pic" value=0 checked />无
                						<input type="radio" name="pic" value=1 />有<font style="font-size:18px;">（先点确认提交，再上传图片）</font>
                					</td>
                				</tr>
                				<tr>
                					<td>答案：</td>
                					<td>
                						<input type="radio" name="answer" value="a" checked />A
                						<input type="radio" name="answer" value="b" />B
                					</td>
                				</tr>
                				<tr>
                					<td>难度：</td>
                					<td>
                						<input type="radio" name="difficulty" value="1" checked /><img src="images/student/hard1.png" style="height:20px;" />
                						<input type="radio" name="difficulty" value="2" /><img src="images/student/hard2.png" style="height:20px;" />
                						<input type="radio" name="difficulty" value="3" /><img src="images/student/hard3.png" style="height:20px;" />
                					</td>
                				</tr>
                			</table>
                			<br /><div class="submit"><input type="submit" class="inputSubmit" value="" name="tijiao" /></div>
                		</form>
<script>
function Submit(){
	return confirm("录入后只可修改，不可删除，是否确认？");	
}
</script>
                		<div class="others">
        					<!--<div class="align"></div>-->                            
       						<div class="other"><a href="Index.jsp"><img src="images/student/quitButton.png" /></a></div>
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
		if(a.length!=0&&a!="null")	alert(a);
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
	System.out.println("AddKemu4Judge.jsp  "+e);
	testBean.setBackNews("系统异常，请重新登陆");
	response.sendRedirect("Index.jsp");
}
%>
</Html> 
