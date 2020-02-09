<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>驾照标准化考试系统</title>
<link href="style/begin.css" rel="stylesheet" type="text/css" />
<link href="style/common.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="css/zzsc-demo.css">
<link rel="stylesheet" href="css/jquery-pie-loader.css">
<jsp:useBean id="testBean" class="mybean.data.Test_Bean" scope="session"/>
</head>

<%
try{
boolean isStudent = testBean.getIsStudent();
if(!isStudent){
	response.sendRedirect("Index.jsp");
}
String id = testBean.getId();

testBean.setAllQuestions(null);

Connection con;
Statement sql;
ResultSet rs;
try{
	String uri="jdbc:mysql://127.0.0.1/licence?"+"user=root&password=&characterEncoding=gb2312";
    con=DriverManager.getConnection(uri);
    sql=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
	rs = sql.executeQuery("SELECT * FROM student WHERE id = "+id);
	rs.next();
	float lastScoreKemu4=rs.getFloat("scoreKemu4");
	testBean.setLastScoreKemu4(lastScoreKemu4);
} catch(Exception e){
	System.out.println("SetKemu4Test.jsp "+e);
}

testBean.setTimeCounterMin(45);
testBean.setTimeCounterSec(0);
testBean.setNumber(0);
testBean.setScore(0);
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
        <div class="class_1">
        	<a href="Kemu1.jsp"><img src="images/student/kemu1.png"/></a>
        </div>
        	<div class="class_2">
        		<a href="SetKemu1ChapterPractise.jsp"><img src="images/student/zhangjielianxi.png" /></a>
        	</div>
        	<div class="class_2">
        		<a href="SetKemu1RandomPractise.jsp"><img src="images/student/suijilianxi.png" /></a>
        	</div>
            <div class="class_2">
        		<a href="SetKemu1Test.jsp"><img src="images/student/canjiakaoshi.png" /></a>
        	</div>
        <div class="class_1">
        	<a href="#" class="selected"><img src="images/student/kemu4.png"/></a>
        </div>
        	<div class="class_2">
        		<a href="SetKemu4ChapterPractise.jsp"><img src="images/student/zhangjielianxi.png" /></a>
        	</div>
        	<div class="class_2">
        		<a href="SetKemu4RandomPractise.jsp"><img src="images/student/suijilianxi.png" /></a>
        	</div>
            <div class="class_2">
        		<a href="" class="selected"><img src="images/student/canjiakaoshi.png" /></a>
        	</div>
        <div class="class_1">
        	<a href="More.jsp"><img src="images/student/gengduo.png"/></a>
        </div>
	</div>	<!--left-->
    
        <div id="middle">
    	<div class="navi_left"></div>
		<div class="navi">
       		<ul>
                <li><a href="SetKemu1Test.jsp">科目一</a></li>
                <li class="selected"><a href="">科目四</a></li>
        	</ul>
    	</div>
        <div class="navi_right"></div>
        <div class="cleaner"></div><!--navi-->
        
        <div id="tab_middle">
        	<div class="set">    
		    	<div class="height"></div>
<%
				float lastScoreKemu1 = testBean.getLastScoreKemu1();
				float lastScoreKemu4 = testBean.getLastScoreKemu4();
				if(lastScoreKemu1<90){
%>
					<a href="SetKemu1Test.jsp">科目一考试</a>通过后才可参加科目四考试。<br />
					<div class="height"></div>
					<div class="submitStart"><input type="submit" class="inputSubmit" value="" name="tijiao" onclick="Kemu1First();" /></div>
<%					
				} else{
				if(lastScoreKemu4<0){
%>
			    	请于5月20日前参加科目四考试。<br />
			    	考试限时30分钟，每题2分，共50题，90分合格。<br />
			    	考试每做一题系统自动计算分数，不得修改。<br />
			    	考试一旦开始，中途不得暂停，不得重复参加考试。<br />
			    	请点击开始，进入考试。<br />		  				
			        <div class="height"></div>
			  		<form action="testServlet" method="post" name=form id="start" onsubmit="return BeginTest();">
			    		<input type="hidden" value="<%=testBean.getId()%>" name ="id">
			    		<input type="hidden" value="4" name="kemu">
			    		<div class="submitStart"><input type="submit" class="inputSubmit" value="" name="tijiao" /></div>
			    	</form>
<%
				} else if(lastScoreKemu4<90){
%>
			    	你已参加过科目四考试。<br />
			    	成绩：<%=lastScoreKemu4 %>。<font style="color:red;">不合格！</font><br />
			    	请联系老师申请重新参加考试。
			    	<div class="height"></div>
			    	<div class="submitStart"><input type="submit" class="inputSubmit" value="" name="tijiao" onclick="NoMoreTestFail();" /></div>
<%
				} else{
%>
					你已参加过科目四考试，不得重复参加。<br />
			    	成绩：<%=lastScoreKemu4 %>。合格。
			    	<div class="height"></div>
			    	<div class="submitStart"><input type="submit" class="inputSubmit" value="" name="tijiao" onclick="NoMoreTest();" /></div>
<%					
				}
				}
%> 	
		    	<div class="others">
		        	<!--<div class="align"></div>-->                            
		        	<div class="other"><a href="Kemu4.jsp"><img src="images/student/quitButton.png" /></a></div>
		    	</div>
		        <div class="align"></div>
		        <div class="cleaner"></div>
            </div>
        </div><!--tab_middle-->
	</div><!--middle -->
	            
<script>
function BeginTest(){
	var a = confirm("你即将开始科目四考试。考试限时30分钟，一旦开始，中途不得暂停，不得重复参加考试。是否确认开始考试？");
	if(!a) {
		alert("请于5月20日前参加科目一考试。");
	} else {
		document.getElementById("login").style.display='block';
		document.getElementById("content").style.opacity='0.5';
	} 
	return a;
}
function NoMoreTest(){
	alert("你已参加过科目四考试，不得重复参加。");
}
function NoMoreTestFail(){
	alert("你已参加过科目四考试，未及格，请联系老师申请重新参加考试。");
}
function Kemu1First(){
	alert("请先参加科目一考试，通过后方可参加科目四考试。");
}
</script>	
	
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
                                    学号：<jsp:getProperty property="id" name="testBean"/>
                </div>
                <div class="cleaner"></div>
            </div>
        	<div class="bottom"></div>
        </div>
    </div>
</div>

<div class="cleaner"></div>
<div class="height"></div>

<div id="chooseChapter" style="color:#fff"><jsp:getProperty name="testBean" property="pleaseChooseChapter" /></div>
<script language="javascript">
	var a = document.getElementById("chooseChapter").innerText;
	function Notify(){
		if(a=="系统繁忙，请稍后再试"){
			alert(a);
		}
	} 
</script>            
<jsp:setProperty name="testBean" property="pleaseChooseChapter" value="" />

<div id="login" style="width:610px; font-size:50px; position:relative; top:-500px; margin-left:-305px; left:50%; display:none;">
	<img src="images/index/loading.png" />
</div>
<script>
function Loading(){
	document.getElementById("login").style.display='block';
	document.getElementById("content").style.opacity='0.5';
}
</script>

<div class="footer">
	<p>地址：中国深圳市南山区华侨城暨南大学深圳校区&nbsp;&nbsp;&nbsp;&nbsp;邮编：518000</p>
    <p>Email:krist.zhuzikai@gmail.com</p>
	<p>Copyright &copy;2018-2019 电子商务工作室 All rights reserved</p>
</div>
  
</div>
</Body>
<%
} catch(Exception e){
	System.out.println("SetKemu4Test.jsp  "+e);
	testBean.setBackNews("系统异常，请重新登陆");
	response.sendRedirect("Index.jsp");
}
%>
</Html> 
