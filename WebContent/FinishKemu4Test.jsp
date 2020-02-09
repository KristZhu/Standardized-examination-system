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

<body>
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
			<div class="testInfo">
            	<div class="testInfoTop"></div>
                <div class="itemTestInfo">
                	<div class="itemTestInfoContent">
<%
						float score = testBean.getScore();
						if(score<90){
%>
			    			本次考试成绩：<%=score %>。<font style="color:red;">不合格！</font><br /><br />
			    			又一位马路杀手从此诞生了w(゜Д゜)w
<%
						} else{
%>
							本次考试成绩：<%=score %>。合格。<br />
							<font style="font-size:19px;"><br />恭喜你，已完成所有考试，可前往7号窗口领取驾照。<br /><br />
							暨南大学交通委员会友情提醒：<br />道路千万条，安全第一条，行车不规范，亲人两行泪。</font>
<%					
						}

						Connection con;
						Statement sql;
						try{
							String uri = "jdbc:mysql://127.0.0.1/licence?"+"user=root&password=&characterEncoding=gb2312";
							con=DriverManager.getConnection(uri);
							sql=con.createStatement();
							sql.executeUpdate("DELETE FROM test");							
							String id = testBean.getId();
							sql.executeUpdate("UPDATE student SET scoreKemu4 = " + score + " WHERE id = " + id);
						} catch(Exception e){
							System.out.println("FinishTest.jsp "+e);
						}	
%> 	
		        		<div class="cleaner"></div>
		            </div>
                </div>
                <div class="bottom"></div>
            </div>
            <div class="others" style="float:left;">
		        <div class="align"></div>                          
		        <div class="other"><a href="Kemu4.jsp"><img src="images/student/quitButton.png" /></a></div>
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

<div class="footer">
	<p>地址：中国深圳市南山区华侨城暨南大学深圳校区&nbsp;&nbsp;&nbsp;&nbsp;邮编：518000</p>
    <p>Email:krist.zhuzikai@gmail.com</p>
	<p>Copyright &copy;2018-2019 电子商务工作室 All rights reserved</p>
</div>
  
</div>

</body>
</html>