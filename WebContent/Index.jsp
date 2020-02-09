<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<jsp:useBean id="testBean" class="mybean.data.Test_Bean" scope="session"/>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>驾照标准化考试系统</title>
<link href="style/index.css" rel="stylesheet" type="text/css" />
<link href="style/common.css" rel="stylesheet" type="text/css" />
</head>

<%
testBean.setIsStudent(false);
testBean.setIsTeacher(false);
testBean.setId("");
%>

<body onload="Notify();">
<div id="wrapper">
    <div id="top">
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

<div class="content">
	<div class="studentLogin">
		<div style="height:50px;"></div>
    	<Form action="beginTestServlet" method="post" >
        	<div class="input">
                             学<font style="color:white">学</font>号：<input type=text name="id" size=16 ><br />
                             密<font style="color:white">密</font>码：<input type="password" name="password" size="16"><br />
                             验证码：<input type="text" name="vercode" size="5"/>
                        <a href="#" mce_href="#" onclick="refresh()"><img alt="" src="authImg" mce_src="authImg" id="authImg" align="absmiddle" height=28px></a>
            </div>
            <div class="submit" style="margin-top:-12px; float:left;">
        		<input class="inputSubmit" type=submit name="sub" value="" onclick="Loading()">
            </div>
		</Form>
            <div class="submit" style="margin-top:-12px; float:right; margin-right:100px;">
        		<a href="Register.jsp"><input class="inputSubmit" type=submit name="sub" value="" style="background:url(images/student/register.png) no-repeat;"></a>
            </div>
    </div>
    <div class="teacherLogin">
    	<div style="height:50px;"></div>
    	<Form action="beginTeacherServlet" method="post" >
        	<div class="input">
                             编<font style="color:white">编</font>号：<input type=text name="id" size=16 ><br />
                             密<font style="color:white">密</font>码：<input type="password" name="password" size="16"><br />
                             验证码：<input type="text" name="vercode" size="5"/>
                        <a href="#" mce_href="#" onclick="refresh()"><img alt="" src="authImg" mce_src="authImg" id="authImg2" align="absmiddle" height=28px></a>
            </div>
            <div class="submit" style="margin-top:-12px; float:left;">
        		<input class="inputSubmit" type=submit name="sub" value="" onclick="Loading()">
            </div>
    	</Form>
    </div>
    <div class="cleaner"></div>
     
<div id="backNews" style="color:#fff"><jsp:getProperty name="testBean" property="backNews" /></div>  
<jsp:setProperty name="testBean" property="backNews" value="" />

<script>
	var a=document.getElementById("backNews").innerText;
	function Notify(){
		if(a.length!=0&&a!="null")	alert(a);
	}
	
	//禁止刷新
	document.onkeydown = function (e) {
		var ev = window.event || e;
		var code = ev.keyCode || ev.which;
		if (code == 116) {
			ev.keyCode ? ev.keyCode = 0 : ev.which = 0;
			cancelBubble = true;
			return false;
		}
	}
	document.oncontextmenu=function(){
		return false;
	}
</script>  

</div>

<div class="footer">
	<p>地址：中国深圳市南山区华侨城暨南大学深圳校区&nbsp;&nbsp;&nbsp;&nbsp;邮编：518000</p>
    <p>Email:krist.zhuzikai@gmail.com</p>
	<p>Copyright &copy;2018-2019 电子商务工作室 All rights reserved</p>
</div>
<!--wrapper -->
</div>

<div id="login" style="width:610px; font-size:50px; position:relative; top:-500px; margin-left:-305px; left:50%; display:none;">
	<img src="images/index/loading.png" />
</div>
<script>
function Loading(){
	document.getElementById("login").style.display='block';
	document.getElementById("content").style.opacity='0.5';
}
</script>

<script type="text/javascript">
function refresh() {
	document.getElementById("authImg").src="authImg?now="+new Date();//使用时间作为参数避免浏览器从缓存取图片
	document.getElementById("authImg2").src="authImg?now="+new Date();//使用时间作为参数避免浏览器从缓存取图片
}
</script>

</body>
</html>