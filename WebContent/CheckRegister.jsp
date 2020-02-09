<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>驾照标准化考试系统</title>
<link href="style/check.css" rel="stylesheet" type="text/css" />
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
        	<a href="" style="width:110px" class="selected"><img src="images/teacher/xueshengguanli.png"/></a>
        </div>
        	<div class="class_2">
        		<a href="" class="selected"><img src="images/teacher/shenhezhuce.png" /></a>
        	</div>
        	<div class="class_2">
        		<a href="CheckScore.jsp"><img src="images/teacher/chakanchengji.png" /></a>
        	</div>
        <div class="class_1" style="width:110px">
        	<a href="AddKemu1MultipleChoice.jsp" style="width:110px"><img src="images/teacher/shezhitimu.png"/></a>
        </div>
        	<div class="class_2">
        		<a href="AddKemu1MultipleChoice.jsp"><img src="images/teacher/tianjiatimu.png" /></a>
        	</div>
        	<div class="class_2">
        		<a href="CheckKemu1.jsp"><img src="images/teacher/chakantimu.png" /></a>
        	</div>
        <div class="class_1">
        	<a href="TeacherMore.jsp"><img src="images/student/gengduo.png"/></a>
        </div>
	</div>	<!--left-->
    
    <div id="middle">
    	<div id="registerInfo">
    		<div class="registerInfoTop"></div>
    		<div class="itemRegisterInfo">
    			<div class="registerInfoContent">
<%
boolean haveRegister = false;
int registerAmount=0;
try{
	String uri="jdbc:mysql://127.0.0.1/licence?"+"user=root&password=&characterEncoding=gb2312";
	Connection con=DriverManager.getConnection(uri);
    Statement sql=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
    ResultSet rs;
    rs=sql.executeQuery("SELECT * FROM register WHERE audit = 0");
    try{
        rs.last();
        registerAmount=rs.getRow();
    } catch(Exception e){
    	registerAmount=0;
    }
    if(registerAmount>0){
    	String [][]registerInfo = new String[registerAmount][5];
        for(int i=0; i<registerAmount; i++){
%>
			<div class="doc"><img src="images/teacher/doc.png" /></div>
			<div class="eachRegisterInfo">
<%
        		rs.absolute(i+1);
        		registerInfo[i][0]=rs.getString(1);
        		%>学<font style="color:white;">学号</font>号：<%=registerInfo[i][0] %><br /><%
        		registerInfo[i][1]=rs.getString(2);
        		%>姓<font style="color:white;">姓名</font>名：<%=registerInfo[i][1] %><br /><%
        		registerInfo[i][2]=rs.getString(3);
        		%>身份证号：<%=registerInfo[i][2] %><br /><%
        		registerInfo[i][3]=rs.getString(4);
        		registerInfo[i][4]=rs.getString(5);
%>
			</div>
			<div class="eachRegisterInfoAudit">
				<form action="auditRegisterServlet" method="post" >
					<input type="hidden" value=<%=registerInfo[i][0] %> name="id">
					<input type="hidden" value=<%=registerInfo[i][1] %> name="name">
					<input type="hidden" value=<%=registerInfo[i][2] %> name="number">
					<input type="hidden" value=<%=registerInfo[i][3] %> name="password">
					<input type="hidden" value=1 name="outcome">
					<input type="submit" value="通过">
				</form>
				<div style="height:15px;"></div>
				<form action="auditRegisterServlet" method="post" >
					<input type="hidden" value=<%=registerInfo[i][0] %> name="id">
					<input type="hidden" value=<%=registerInfo[i][1] %> name="name">
					<input type="hidden" value=<%=registerInfo[i][2] %> name="number">
					<input type="hidden" value=<%=registerInfo[i][3] %> name="password">
					<input type="hidden" value=2 name="outcome">
					<input type="submit" value="拒绝">
				</form>
			</div>
			<div class="cleaner"></div>
			<div style="height:15px;"></div>
			<div style="height:15px; border-top:1px solid #999;"></div>
<%
        }
        haveRegister=true;
    } else{
    	%>无待审核注册信息<%
    }
} catch(Exception e){
	System.out.println("CheckRegister.jsp "+e);
}
%>
    			</div>
    		</div>
    		<div class="bottom"></div>
    	</div>
	</div><!--middle -->

    <div id="right">
    	<div style="height:30px;"></div>
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
    
<div id="backNews" style="color:#fff"><jsp:getProperty name="testBean" property="auditRegisterBackNews" /></div>  
<script>
	var a=document.getElementById("backNews").innerText;
	function Notify(){
		if(a.length!=0&&a!="null")	alert(a);
	}
</script>
<jsp:setProperty name="testBean" property="auditRegisterBackNews" value="" />
    
</div>

<div class="cleaner"></div>
<div style="height:20px;"></div>

<div class="footer">
	<p>地址：中国深圳市南山区华侨城暨南大学深圳校区&nbsp;&nbsp;&nbsp;&nbsp;邮编：518000</p>
    <p>Email:krist.zhuzikai@gmail.com</p>
	<p>Copyright &copy;2018-2019 电子商务工作室 All rights reserved</p>
</div>
  
</div>
</Body>
<%
} catch(Exception e){
	System.out.println("CheckKemu1.jsp  "+e);
	testBean.setBackNews("系统异常，请重新登陆");
	response.sendRedirect("Index.jsp");
}
%>
</Html> 
