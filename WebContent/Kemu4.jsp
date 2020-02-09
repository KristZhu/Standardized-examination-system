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
	System.out.println("Kemu4.jsp "+e);
}
%>

<body>
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
        		<a href="SetKemu4Test.jsp"><img src="images/student/canjiakaoshi.png" /></a>
        	</div>
        <div class="class_1">
        	<a href="More.jsp"><img src="images/student/gengduo.png"/></a>
        </div>
	</div>	<!--left-->
    
    <div id="middle">
    	<div class="navi_left"></div>
		<div class="navi">
       		<ul>
                <li><a href="Kemu1.jsp">科目一</a></li>
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
						float lastScoreKemu1 = testBean.getLastScoreKemu1();						
						float lastScoreKemu4 = testBean.getLastScoreKemu4();
						if(lastScoreKemu1<90){
%>
							<a href="SetKemu1Test.jsp">科目一考试</a>通过后才可参加科目四考试。
<%					
						} else{
						if(lastScoreKemu4<0){
%>
							请于5月20日前参加<a href="SetKemu4Test.jsp">科目四考试</a>。
<%
						} else{
							if(lastScoreKemu4<90){
%>
								科目四成绩：<%=lastScoreKemu4 %>。
								<div style="color:red; display:inline;">不合格！</div>
								<br />请联系老师重新报名参加考试。
<%
							} else{
%>
								科目四成绩：<%=lastScoreKemu4 %>。合格。
<%								
							}
						}
						}
%>							
                    </div>
                </div>
                <div class="bottom"></div>
            </div>

<%
			int kemu4Chapter1 = testBean.getKemu4Chapter1();
			kemu4Chapter1 = kemu4Chapter1%1000;
			int kemu4Chapter2 = testBean.getKemu4Chapter2();
			kemu4Chapter2 = kemu4Chapter2%1000;
			int kemu4Chapter3 = testBean.getKemu4Chapter3();
			kemu4Chapter3 = kemu4Chapter3%1000;
			int kemu4Chapter4 = testBean.getKemu4Chapter4();
			kemu4Chapter4 = kemu4Chapter4%1000;
			int kemu4Chapter5 = testBean.getKemu4Chapter5();
			kemu4Chapter5 = kemu4Chapter5%1000;
			int kemu4Chapter6 = testBean.getKemu4Chapter6();
			kemu4Chapter6 = kemu4Chapter6%1000;
			int kemu4Chapter7 = testBean.getKemu4Chapter7();
			kemu4Chapter7 = kemu4Chapter7%1000;
			
			int kemu4Chapter1All = testBean.getKemu4Chapter1All();
			kemu4Chapter1All = kemu4Chapter1All%1000;
			int kemu4Chapter2All = testBean.getKemu4Chapter2All();
			kemu4Chapter2All = kemu4Chapter2All%1000;
			int kemu4Chapter3All = testBean.getKemu4Chapter3All();
			kemu4Chapter3All = kemu4Chapter3All%1000;
			int kemu4Chapter4All = testBean.getKemu4Chapter4All();
			kemu4Chapter4All = kemu4Chapter4All%1000;
			int kemu4Chapter5All = testBean.getKemu4Chapter5All();
			kemu4Chapter5All = kemu4Chapter5All%1000;
			int kemu4Chapter6All = testBean.getKemu4Chapter6All();
			kemu4Chapter6All = kemu4Chapter6All%1000;
			int kemu4Chapter7All = testBean.getKemu4Chapter7All();
			kemu4Chapter7All = kemu4Chapter7All%1000;
%>
            <div class="practiseInfo">
            	<div class="practiseInfoTop"></div>
                <div class="itemPractiseInfo">
                	<div class="chapterInfo">
                    	<div class="doc"><img src="images/teacher/doc.png" /></div>
                        <div class="chapterInfoContent" style="font-size:16px;" id="chapter11">
                        	第1章 违法行为综合判断与案例分析
                            <br />共计<div id="kemu4Chapter1All" style="display:inline;"><%=kemu4Chapter1All %></div>题 已练习到第<div id="kemu4Chapter1" style="display:inline;"><%=kemu4Chapter1 %></div>题
                        </div>
                        <div class="pie">                        
                        	<section class="zzsc-container" style="margin:0px; padding:0px;">
								<figure id="pie" data-behavior="pie-chart"></figure>
							</section>       
                        </div>
                        <div class="cleaner"></div>
                        
                        <div style="height:10px;"></div>
                        <div class="doc"><img src="images/teacher/doc.png" /></div>
                        <div class="chapterInfoContent" style="font-size:16px;" id="chapter12">
                        	第2章 安全行车常识
                            <br />共计<div id="kemu4Chapter2All" style="display:inline;"><%=kemu4Chapter2All %></div>题 已练习到第<div id="kemu4Chapter2" style="display:inline;"><%=kemu4Chapter2 %></div>题
                        </div>
                        <div class="pie">                        
                        	<section class="zzsc-container" style="margin:0px; padding:0px;">
								<figure id="pie2" data-behavior="pie-chart2"></figure>
							</section>       
                        </div>
                        <div class="cleaner"></div>
                        
                        <div style="height:10px;"></div>
                        <div class="doc"><img src="images/teacher/doc.png" /></div>
                        <div class="chapterInfoContent" style="font-size:16px;" id="chapter13">
                        	第3章 常见交通标志、标线和交通手势辨识
                            <br />共计<div id="kemu4Chapter3All" style="display:inline;"><%=kemu4Chapter3All %></div>题 已练习到第<div id="kemu4Chapter3" style="display:inline;"><%=kemu4Chapter3 %></div>题
                        </div>
                        <div class="pie">                        
                        	<section class="zzsc-container" style="margin:0px; padding:0px;">
								<figure id="pie3" data-behavior="pie-chart3"></figure>
							</section>       
                        </div>
                        <div class="cleaner"></div>
                        
                        <div style="height:10px;"></div>
                        <div class="doc"><img src="images/teacher/doc.png" /></div>
                        <div class="chapterInfoContent" style="font-size:16px;" id="chapter14">
                        	第4章 驾驶职业道德和文明驾驶常识
                            <br />共计<div id="kemu4Chapter4All" style="display:inline;"><%=kemu4Chapter4All %></div>题 已练习到第<div id="kemu4Chapter4" style="display:inline;"><%=kemu4Chapter4 %></div>题
                        </div>
                        <div class="pie">                        
                        	<section class="zzsc-container" style="margin:0px; padding:0px;">
								<figure id="pie4" data-behavior="pie-chart4"></figure>
							</section>       
                        </div>
                        <div class="cleaner"></div>
                        
                        <div style="height:10px;"></div>
                        <div class="doc"><img src="images/teacher/doc.png" /></div>
                        <div class="chapterInfoContent" style="font-size:16px;" id="chapter15">
                        	第5章 恶劣气候和复杂道路条件下驾驶常识
                            <br />共计<div id="kemu4Chapter5All" style="display:inline;"><%=kemu4Chapter5All %></div>题 已练习到第<div id="kemu4Chapter5" style="display:inline;"><%=kemu4Chapter5 %></div>题
                        </div>
                        <div class="pie">                        
                        	<section class="zzsc-container" style="margin:0px; padding:0px;">
								<figure id="pie5" data-behavior="pie-chart5"></figure>
							</section>       
                        </div>
                        <div class="cleaner"></div>
                        
                        <div style="height:10px;"></div>
                        <div class="doc"><img src="images/teacher/doc.png" /></div>
                        <div class="chapterInfoContent" style="font-size:16px;" id="chapter16">
                        	第6章 紧急情况下避险常识
                            <br />共计<div id="kemu4Chapter6All" style="display:inline;"><%=kemu4Chapter6All %></div>题 已练习到第<div id="kemu4Chapter6" style="display:inline;"><%=kemu4Chapter6 %></div>题
                        </div>
                        <div class="pie">                        
                        	<section class="zzsc-container" style="margin:0px; padding:0px;">
								<figure id="pie6" data-behavior="pie-chart6"></figure>
							</section>       
                        </div>
                        <div class="cleaner"></div>
                        
                        <div style="height:10px;"></div>
                        <div class="doc"><img src="images/teacher/doc.png" /></div>
                        <div class="chapterInfoContent" style="font-size:16px;" id="chapter17">
                        	第7章 交通事故救护及常见危化品处置常识
                            <br />共计<div id="kemu4Chapter7All" style="display:inline;"><%=kemu4Chapter7All %></div>题 已练习到第<div id="kemu4Chapter7" style="display:inline;"><%=kemu4Chapter7 %></div>题
                        </div>
                        <div class="pie">                        
                        	<section class="zzsc-container" style="margin:0px; padding:0px;">
								<figure id="pie7" data-behavior="pie-chart7"></figure>
							</section>       
                        </div>
                        <div class="cleaner"></div>
                    </div>
                </div>
                <div class="bottom"></div>
            </div>
        </div><!--tab_middle-->
	</div><!--middle -->
	
	<script src="js/jquery-2.1.1.min.js" type="text/javascript"></script>
	<script src="js/jquery-pie-loader.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			var chapter11 = function() {
				var kemu4Chapter1 = document.getElementById("kemu4Chapter1").innerHTML;
				var kemu4Chapter1All = document.getElementById("kemu4Chapter1All").innerHTML;
				return Math.floor((kemu4Chapter1/kemu4Chapter1All)*100);
			}							
			$('*[data-behavior="pie-chart"]').each(function() {
				$(this).svgPie({
					percentage: chapter11()
				});							
			});
			
			var chapter12 = function() {
				var kemu4Chapter2 = document.getElementById("kemu4Chapter2").innerHTML;
				var kemu4Chapter2All = document.getElementById("kemu4Chapter2All").innerHTML;
				return Math.floor((kemu4Chapter2/kemu4Chapter2All)*100);
			}							
			$('*[data-behavior="pie-chart2"]').each(function() {
				$(this).svgPie({
					percentage: chapter12()
				});							
			});
			
			var chapter13 = function() {
				var kemu4Chapter3 = document.getElementById("kemu4Chapter3").innerHTML;
				var kemu4Chapter3All = document.getElementById("kemu4Chapter3All").innerHTML;
				return Math.floor((kemu4Chapter3/kemu4Chapter3All)*100);
			}							
			$('*[data-behavior="pie-chart3"]').each(function() {
				$(this).svgPie({
					percentage: chapter13()
				});							
			});
			
			var chapter14 = function() {
				var kemu4Chapter4 = document.getElementById("kemu4Chapter4").innerHTML;
				var kemu4Chapter4All = document.getElementById("kemu4Chapter4All").innerHTML;
				return Math.floor((kemu4Chapter4/kemu4Chapter4All)*100);
			}							
			$('*[data-behavior="pie-chart4"]').each(function() {
				$(this).svgPie({
					percentage: chapter14()
				});							
			});
			
			var chapter15 = function() {
				var kemu4Chapter5 = document.getElementById("kemu4Chapter5").innerHTML;
				var kemu4Chapter5All = document.getElementById("kemu4Chapter5All").innerHTML;
				return Math.floor((kemu4Chapter5/kemu4Chapter5All)*100);
			}							
			$('*[data-behavior="pie-chart5"]').each(function() {
				$(this).svgPie({
					percentage: chapter15()
				});							
			});
			
			var chapter16 = function() {
				var kemu4Chapter6 = document.getElementById("kemu4Chapter6").innerHTML;
				var kemu4Chapter6All = document.getElementById("kemu4Chapter6All").innerHTML;
				return Math.floor((kemu4Chapter6/kemu4Chapter6All)*100);
			}							
			$('*[data-behavior="pie-chart6"]').each(function() {
				$(this).svgPie({
					percentage: chapter16()
				});							
			});
			
			var chapter17 = function() {
				var kemu4Chapter7 = document.getElementById("kemu4Chapter7").innerHTML;
				var kemu4Chapter7All = document.getElementById("kemu4Chapter7All").innerHTML;
				return Math.floor((kemu4Chapter7/kemu4Chapter7All)*100);
			}							
			$('*[data-behavior="pie-chart7"]').each(function() {
				$(this).svgPie({
					percentage: chapter17()
				});							
			});
		});
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
	System.out.println("Kemu4.jsp  "+e);
	testBean.setBackNews("系统异常，请重新登陆");
	response.sendRedirect("Index.jsp");
}
%>
</Html> 
