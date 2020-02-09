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
        	<a href="Kemu1.jsp" class="selected"><img src="images/student/kemu1.png"/></a>
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
        		<a href="" class="selected"><img src="images/student/zhangjielianxi.png" /></a>
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
                <li><a href="SetKemu1ChapterPractise.jsp">科目一</a></li>
                <li class="selected"><a href="">科目四</a></li>
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
       	<br /><input type="radio" value="chapter11" name="chapter" />第 1 章 违法行为综合判断与案例分析
       	<br /><input type="radio" value="chapter12" name="chapter" />第 2 章 安全行车常识
       	<br /><input type="radio" value="chapter13" name="chapter" />第 3 章 常见交通标志、标线和交通手势辨识
       	<br /><input type="radio" value="chapter14" name="chapter" />第 4 章 驾驶职业道德和文明驾驶常识
       	<br /><input type="radio" value="chapter15" name="chapter" />第 5 章 恶劣气候和复杂道路条件下驾驶常识
       	<br /><input type="radio" value="chapter16" name="chapter" />第 6 章 紧急情况下避险常识
       	<br /><input type="radio" value="chapter17" name="chapter" />第 7 章 交通事故救护及常见危化品处置常识
       	<div style="height:0px;"></div>
       	<br /><div class="submit"><input type="submit" class="inputSubmit" value="" name="tijiao" /></div>
    </Form>
    <div class="others">
        <!--<div class="align"></div>-->                            
        <div class="other"><a href="Kemu4.jsp"><img src="images/student/quitButton.png" /></a></div>
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
  		if(chapter.equals("chapter11")){
  			%>你练习的是第1章 违法行为综合判断与案例分析<br /><%
  			if(kemu4Chapter1<kemu4Chapter1All){
  				%>将从第<%=kemu4Chapter1%1000+1 %>题开始练习<br /><%
  			} else{
  				%>已练习完毕所有题目，将从第 1 题开始练习<br /><%
  			}
  		} else if(chapter.equals("chapter12")){
  			%>你练习的是第2章 安全行车常识<br /><%
  			if(kemu4Chapter2<kemu4Chapter2All){
  				%>将从第<%=kemu4Chapter2%1000+1 %>题开始练习<br /><%
  			} else{
  				%>已练习完毕所有题目，将从第 1 题开始练习<br /><%
  			}
  		} else if(chapter.equals("chapter13")){
  			%>你练习的是第3章 常见交通标志、标线和交通手势辨识<br /><%
  			if(kemu4Chapter3<kemu4Chapter3All){
  				%>将从第<%=kemu4Chapter3%1000+1 %>题开始练习<br /><%
  			} else{
  				%>已练习完毕所有题目，将从第 1 题开始练习<br /><%
  			}
  		} else if(chapter.equals("chapter14")){
  			%>你练习的是第4章 驾驶职业道德和文明驾驶常识<br /><%
  			if(kemu4Chapter4<kemu4Chapter4All){
  				%>将从第<%=kemu4Chapter4%1000+1 %>题开始练习<br /><%
  			} else{
  				%>已练习完毕所有题目，将从第 1 题开始练习<br /><%
  			}
  		} else if(chapter.equals("chapter15")){
  			%>你练习的是第5章 恶劣气候和复杂道路条件下驾驶常识<br /><%
  			if(kemu4Chapter5<kemu4Chapter5All){
  				%>将从第<%=kemu4Chapter5%1000+1 %>题开始练习<br /><%
  			} else{
  				%>已练习完毕所有题目，将从第 1 题开始练习<br /><%
  			}
  		} else if(chapter.equals("chapter16")){
  			%>你练习的是第6章 紧急情况下避险常识<br /><%
  			if(kemu4Chapter6<kemu4Chapter6All){
  				%>将从第<%=kemu4Chapter6%1000+1 %>题开始练习<br /><%
  			} else{
  				%>已练习完毕所有题目，将从第 1 题开始练习<br /><%
  			}
  		} else if(chapter.equals("chapter17")){
  			%>你练习的是第7章 交通事故救护及常见危化品处置常识<br /><%
  			if(kemu4Chapter7<kemu4Chapter7All){
  				%>将从第<%=kemu4Chapter7%1000+1 %>题开始练习<br /><%
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
	var b = document.getElementById("setCorrectMess").innerText;
	if(a=="请设置练习难度") a="请选择练习章节";
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
</div>

<div class="cleaner"></div>
<div class="height"></div>

<div id="login" style="width:610px; font-size:50px; position:relative; top:-1000px; margin-left:-305px; left:50%; display:none;">
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
	System.out.println("SetKemu4ChapterPractise.jsp  "+e);
	testBean.setBackNews("系统异常，请重新登陆");
	response.sendRedirect("Index.jsp");
}
%>
</Html> 
