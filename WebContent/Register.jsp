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
try{
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
	<div class="studentRegister">
		<div style="height:70px;"></div>
    	<Form action="registerServlet" method="post" id="registerForm" onsubmit="return Submit();" >
        	<div class="input">
                             学<font style="color:white">学号</font>号：<input id="id" type=text name="id" value="<%=testBean.getRegisterId() %>" size=20 ><br />
                             姓<font style="color:white">姓名</font>名：<input id="name" type=text name="name" value="<%=testBean.getRegisterName() %>" size=20 ><br />
                             身份证号：<input id="idNumber" type=text name="idNumber" value="<%=testBean.getRegisterIdNumber() %>" size=20 ><br />
                             密<font style="color:white">密码</font>码：<input id="password" type="password" name="password" size="20"><br />
                             确认密码：<input id="rePassword" type="password" name="rePassword" size="20"><br />
            	验&nbsp;证&nbsp;码&nbsp;：<input type="text" name="vercode" size="9"/>
                        <a href="#" mce_href="#" onclick="refresh()"><img alt="" src="authImg" mce_src="authImg" id="authImg" align="absmiddle" height=28px></a>
            </div>
            <div class="submit" style="float:left;">
        		<input class="inputSubmit" type=submit name="sub" value="" style="background:url(images/student/register.png) no-repeat;">
            </div>
            <div class="submit" style="float:left; margin-left:200px;">
        		<a href="Index.jsp"><img src="images/student/quitButton.png" /></a>        		
            </div>
            <div class="cleaner"></div>
		</Form>
    </div>
    <div class="cleaner"></div>
     
<div id="backNews" style="color:#fff"><jsp:getProperty name="testBean" property="registerBackNews" /></div>  
<jsp:setProperty name="testBean" property="registerBackNews" value="" />
<script>
	var a=document.getElementById("backNews").innerText;
	function Notify(){
		if(a.length!=0&&a!="null"){
			alert(a);
			if(a=="注册成功，请等待老师审核"){
				window.location.href="Index.jsp";
			}
		}
	}

	function refresh(){
		document.getElementById("authImg").src="authImg?now="+new Date();//使用时间作为参数避免浏览器从缓存取图片
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
<%=testBean.getVerifyCode() %>
</body>
<%
} catch(Exception e){
	System.out.println("Register.jsp  "+e);
	testBean.setBackNews("系统异常，请重新登陆");
	response.sendRedirect("Index.jsp");
}
%>
</html>