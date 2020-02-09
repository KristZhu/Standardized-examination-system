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

int kemu1Chapter1 = testBean.getKemu1Chapter1();
kemu1Chapter1 = kemu1Chapter1%1000;
int kemu1Chapter2 = testBean.getKemu1Chapter2();
kemu1Chapter2 = kemu1Chapter2%1000;
int kemu1Chapter3 = testBean.getKemu1Chapter3();
kemu1Chapter3 = kemu1Chapter3%1000;
int kemu1Chapter4 = testBean.getKemu1Chapter4();
kemu1Chapter4 = kemu1Chapter4%1000;

int kemu1Chapter1All = testBean.getKemu1Chapter1All();
kemu1Chapter1All = kemu1Chapter1All%1000;
int kemu1Chapter2All = testBean.getKemu1Chapter2All();
kemu1Chapter2All = kemu1Chapter2All%1000;
int kemu1Chapter3All = testBean.getKemu1Chapter3All();
kemu1Chapter3All = kemu1Chapter3All%1000;
int kemu1Chapter4All = testBean.getKemu1Chapter4All();
kemu1Chapter4All = kemu1Chapter4All%1000;

testBean.setTimeCounterMin(0);
testBean.setTimeCounterSec(0);
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
        	<a href="#" class="selected"><img src="images/student/kemu1.png"/></a>
        </div>
        	<div class="class_2">
        		<a href="" class="selected"><img src="images/student/zhangjielianxi.png" /></a>
        	</div>
        	<div class="class_2">
        		<a href="SetKemu1RandomPractise.jsp"><img src="images/student/suijilianxi.png" /></a>
        	</div>
            <div class="class_2">
        		<a href="SetKemu1Test.jsp"><img src="images/student/canjiakaoshi.png" /></a>
        	</div>
        <div class="class_1">
        	<a href="Kemu4.jsp"><img src="images/student/kemu4.png"/></a>
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
                <li class="selected"><a href="">科目一</a></li>
                <li><a href="SetKemu4ChapterPractise.jsp">科目四</a></li>
        	</ul>
    	</div>
        <div class="navi_right"></div>
        <div class="cleaner"></div><!--navi-->
        
        <div id="tab_middle">
        	<div class="set">
    <% 
     String chapter = request.getParameter("chapter");  
     if(chapter!=null&&chapter.length()>=1){
         testBean.setChapter(chapter);
     }
    %>
    
    <div class="height"></div>
    <Form action="" method=post name=form>
    	<input type="hidden" value="<%=testBean.getId()%>" name ="id">
       	请选择需要练习的章节：
       	<br /><input type="radio" value="chapter1" name="chapter" />第 1 章 道路交通安全法律、法规和规章
       	<br /><input type="radio" value="chapter2" name="chapter" />第 2 章 交通信号
       	<br /><input type="radio" value="chapter3" name="chapter" />第 3 章 安全行车、文明驾驶基础知识
       	<br /><input type="radio" value="chapter4" name="chapter" />第 4 章 机动车驾驶操作相关基础知识
       	<div style="height:0px;"></div>
       	<br /><div class="submit"><input type="submit" class="inputSubmit" value="" name="tijiao" /></div>
    </Form>
    <div class="others">
        <!--<div class="align"></div>-->                            
        <div class="other"><a href="Kemu1.jsp"><img src="images/student/quitButton.png" /></a></div>
    </div>
    <div class="cleaner"></div>
    <div class="height"></div>
    
  <%! int ta; %>
  <%
  	testBean.setTimeCounterMin(0);
  	testBean.setTimeCounterSec(0);
  	String pleaseChooseChapter = testBean.getPleaseChooseChapter();
  	boolean valid = true;
  	if(chapter==null||chapter.length()==0){
  		if(pleaseChooseChapter==null||pleaseChooseChapter.length()==0){
  			%> <jsp:setProperty name="testBean" property="pleaseChooseChapter" value="请选择练习章节" /> <%
  		}
  		valid=false;
  	} 
  	if(valid){
  		%> <jsp:setProperty name="testBean" property="pleaseChooseChapter" value="" /> <div id="setCorrectMess"> <%
  		if(chapter.equals("chapter1")){
  			%>你练习的是第 1 章 道路交通安全法律、法规和规章<br /><%
  			if(kemu1Chapter1<kemu1Chapter1All){
  				%>将从第<%=kemu1Chapter1%1000+1 %>题开始练习<br /><%
  			} else{
  				%>已练习完毕所有题目，将从第 1 题开始练习<br /><%
  			}
  		} else if(chapter.equals("chapter2")){
  			%>你练习的是第 2 章 交通信号<br /><%
  			if(kemu1Chapter2<kemu1Chapter2All){
  				%>将从第<%=kemu1Chapter2%1000+1 %>题开始练习<br /><%
  			} else{
  				%>已练习完毕所有题目，将从第 1 题开始练习<br /><%
  			}
  		} else if(chapter.equals("chapter3")){
  			%>你练习的是第 3 章 安全行车、文明驾驶基础知识<br /><%
  			if(kemu1Chapter3<kemu1Chapter3All){
  				%>将从第<%=kemu1Chapter3%1000+1 %>题开始练习<br /><%
  			} else{
  				%>已练习完毕所有题目，将从第 1 题开始练习<br /><%
  			}
  		} else if(chapter.equals("chapter4")){
  			%>你练习的是第 4 章 机动车驾驶操作相关基础知识<br /><%
  			if(kemu1Chapter4<kemu1Chapter4All){
  				%>将从第<%=kemu1Chapter4%1000+1 %>题开始练习<br /><%
  			} else{
  				%>已练习完毕所有题目，将从第 1 题开始练习<br /><%
  			}
  		} 
 %>
  		</div>设置成功，请点击开始，进入练习<br />
  				
        <div class="height"></div>
  		<form action="chapterPractiseServlet" method="post" name=form id="start">
    		<input type="hidden" value="<%=testBean.getId()%>" name ="id">
    		<div class="submitStart"><input type="submit" class="inputSubmit" value="" name="tijiao" onclick="Loading();" /></div>
    	</form>
        <div class="align"></div>
        <div class="cleaner"></div>
  <%
  	}
  %>
            </div>
            
<div id="chooseChapter" style="color:#fff"><jsp:getProperty name="testBean" property="pleaseChooseChapter" /></div>
<script language="javascript">
	var a = document.getElementById("chooseChapter").innerText;
	if(a=="请设置练习难度") a="请选择练习章节";
	var b = document.getElementById("setCorrectMess").innerText;
	function Notify(){
		if(a.length!=0&&a!="null"){
			alert(a);
		} else{
			if(confirm(b)){
				document.getElementById("start").submit();
				document.getElementById("login").style.display='block';
				document.getElementById("content").style.opacity='0.5';
			}
		}
	} 
</script>            
<jsp:setProperty name="testBean" property="pleaseChooseChapter" value="" />

            <div class="practiseInfo">
            	<div class="practiseInfoTop"></div>
                <div class="itemPractiseInfo">
                	<div class="chapterInfo">
                    	<div class="doc"><img src="images/teacher/doc.png" /></div>
                        <div class="chapterInfoContent" id="chapter1">
                        	第1章 道路交通安全法律、法规和规章
                            <br />共计<div id="kemu1Chapter1All" style="display:inline;"><%=kemu1Chapter1All %></div>题 已练习到第<div id="kemu1Chapter1" style="display:inline;"><%=kemu1Chapter1 %></div>题
                        </div>
                        <div class="pie">                        
                        	<section class="zzsc-container" style="margin:0px; padding:0px;">
								<figure id="pie" data-behavior="pie-chart"></figure>
							</section>       
                        </div>
                        <div class="cleaner"></div>
                        
                        <div style="height:10px;"></div>
                        <div class="doc"><img src="images/teacher/doc.png" /></div>
                        <div class="chapterInfoContent" id="chapter2">
                        	第2章 交通信号
                            <br />共计<div id="kemu1Chapter2All" style="display:inline;"><%=kemu1Chapter2All %></div>题 已练习到第<div id="kemu1Chapter2" style="display:inline;"><%=kemu1Chapter2 %></div>题
                        </div>
                        <div class="pie">                        
                        	<section class="zzsc-container" style="margin:0px; padding:0px;">
								<figure id="pie2" data-behavior="pie-chart2"></figure>
							</section>       
                        </div>
                        <div class="cleaner"></div>
                        
                        <div style="height:10px;"></div>
                        <div class="doc"><img src="images/teacher/doc.png" /></div>
                        <div class="chapterInfoContent" id="chapter3">
                        	第3章 安全行车、文明驾驶基础知识
                            <br />共计<div id="kemu1Chapter3All" style="display:inline;"><%=kemu1Chapter3All %></div>题 已练习到第<div id="kemu1Chapter3" style="display:inline;"><%=kemu1Chapter3 %></div>题
                        </div>
                        <div class="pie">                        
                        	<section class="zzsc-container" style="margin:0px; padding:0px;">
								<figure id="pie3" data-behavior="pie-chart3"></figure>
							</section>       
                        </div>
                        <div class="cleaner"></div>
                        
                        <div style="height:10px;"></div>
                        <div class="doc"><img src="images/teacher/doc.png" /></div>
                        <div class="chapterInfoContent" id="chapter4">
                        	第4章 机动车驾驶操作相关基础知识
                            <br />共计<div id="kemu1Chapter4All" style="display:inline;"><%=kemu1Chapter4All %></div>题 已练习到第<div id="kemu1Chapter4" style="display:inline;"><%=kemu1Chapter4 %></div>题
                        </div>
                        <div class="pie">                        
                        	<section class="zzsc-container" style="margin:0px; padding:0px;">
								<figure id="pie4" data-behavior="pie-chart4"></figure>
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
			var chapter1 = function() {
				var kemu1Chapter1 = document.getElementById("kemu1Chapter1").innerHTML;
				var kemu1Chapter1All = document.getElementById("kemu1Chapter1All").innerHTML;
				return Math.floor((kemu1Chapter1/kemu1Chapter1All)*100);
				//return Math.floor((Math.random() * 100) + 1)
			}							
			$('*[data-behavior="pie-chart"]').each(function() {
				$(this).svgPie({
					percentage: chapter1()
				});							
			});
			
			var chapter2 = function() {
				var kemu1Chapter2 = document.getElementById("kemu1Chapter2").innerHTML;
				var kemu1Chapter2All = document.getElementById("kemu1Chapter2All").innerHTML;
				return Math.floor((kemu1Chapter2/kemu1Chapter2All)*100);
			}							
			$('*[data-behavior="pie-chart2"]').each(function() {
				$(this).svgPie({
					percentage: chapter2()
				});							
			});
			
			var chapter3 = function() {
				var kemu1Chapter3 = document.getElementById("kemu1Chapter3").innerHTML;
				var kemu1Chapter3All = document.getElementById("kemu1Chapter3All").innerHTML;
				return Math.floor((kemu1Chapter3/kemu1Chapter3All)*100);
			}							
			$('*[data-behavior="pie-chart3"]').each(function() {
				$(this).svgPie({
					percentage: chapter3()
				});							
			});
			
			var chapter4 = function() {
				var kemu1Chapter4 = document.getElementById("kemu1Chapter4").innerHTML;
				var kemu1Chapter4All = document.getElementById("kemu1Chapter4All").innerHTML;
				return Math.floor((kemu1Chapter4/kemu1Chapter4All)*100);
			}							
			$('*[data-behavior="pie-chart4"]').each(function() {
				$(this).svgPie({
					percentage: chapter4()
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
</div>

<div class="cleaner"></div>
<div class="height"></div>

<div id="login" style="width:610px; font-size:50px; position:relative; top:-700px; margin-left:-305px; left:50%; display:none;">
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
	System.out.println("SetKemu1ChapterPractise.jsp  "+e);
	testBean.setBackNews("系统异常，请重新登陆");
	response.sendRedirect("Index.jsp");
}
%>
</Html> 
