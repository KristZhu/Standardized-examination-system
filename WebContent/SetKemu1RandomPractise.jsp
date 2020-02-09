<%@page import="javax.servlet.jsp.tagext.TryCatchFinally" %>
<%@page import="java.text.NumberFormat" %>
<%@page import="java.text.ParseException" %>
<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>驾照标准化考试系统</title>
<link href="style/begin.css" rel="stylesheet" type="text/css" />
<link href="style/common.css" rel="stylesheet" type="text/css" />
<link href="style/slider.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js2/jquery.js"></script>
<script type="text/javascript" src="js2/jsapi.js"></script>
<script type="text/javascript" src="js2/corechart.js"></script>		
<script type="text/javascript" src="js2/jquery.gvChart-1.0.1.min.js"></script>
<script type="text/javascript" src="js2/jquery.ba-resize.min.js"></script>
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

testBean.setTimeCounterMin(0);
testBean.setTimeCounterSec(0);
testBean.setScore(0);
testBean.setNumber(0);
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
        		<a href="SetKemu1ChapterPractise.jsp"><img src="images/student/zhangjielianxi.png" /></a>
        	</div>
        	<div class="class_2">
        		<a href="#" class="selected"><img src="images/student/suijilianxi.png" /></a>
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
                <li><a href="SetKemu4RandomPractise.jsp">科目四</a></li>
        	</ul>
    	</div>
        <div class="navi_right"></div>
        <div class="cleaner"></div><!--navi-->
        
        <div id="tab_middle">
        	<div class="set">
<% 
     boolean maybePercentageCorrect1=false, maybePercentageCorrect2=false, maybePercentageCorrect3=false;
     String easyPercentage = request.getParameter("easyPercentage");  
     if(easyPercentage!=null&&easyPercentage.length()>=1&&easyPercentage.contains("%")){
         try{
        	 Float.parseFloat(easyPercentage.substring(0, easyPercentage.indexOf("%")));
        	 maybePercentageCorrect1=true;
         } catch(Exception e){
        	 easyPercentage="0%";
        	 testBean.setPercentageError(true);	//a%
         }
     } else if(easyPercentage!=null&&easyPercentage.length()>=1){
    	 try{
    		 Float.parseFloat(easyPercentage);
    		 maybePercentageCorrect1=true;
    		 easyPercentage=easyPercentage+"%";
    	 } catch(Exception e){
	    	 easyPercentage="0%";
	    	 testBean.setPercentageError(true);	//a
    	 }
     } else{
    	 easyPercentage="0%";	//null
     }
     testBean.setEasyPercentage(easyPercentage);
     
     String normalPercentage = request.getParameter("normalPercentage");  
     if(normalPercentage!=null&&normalPercentage.length()>=1&&normalPercentage.contains("%")){
    	 try{
        	 Float.parseFloat(normalPercentage.substring(0, normalPercentage.indexOf("%")));
        	 maybePercentageCorrect2=true;
         } catch(Exception e){
        	 normalPercentage="0%";
        	 testBean.setPercentageError(true);
         }
     } else if(normalPercentage!=null&&normalPercentage.length()>=1){
    	 try{
    		 Float.parseFloat(normalPercentage);
    		 maybePercentageCorrect2=true;
    		 normalPercentage=normalPercentage+"%";
    	 } catch(Exception e){
    		 normalPercentage="0%";
        	 testBean.setPercentageError(true);
    	 }    	 
     } else{
    	 normalPercentage="0%";
     }
     testBean.setNormalPercentage(normalPercentage);
     
     String hardPercentage = request.getParameter("hardPercentage");  
     if(hardPercentage!=null&&hardPercentage.length()>=1&&hardPercentage.contains("%")){
    	 try{
        	 Float.parseFloat(hardPercentage.substring(0, hardPercentage.indexOf("%")));
        	 maybePercentageCorrect3=true;
         } catch(Exception e){
        	 hardPercentage="0%";
        	 testBean.setPercentageError(true);
         }
     } else if(hardPercentage!=null&&hardPercentage.length()>=1){
    	 try{
    		 Float.parseFloat(hardPercentage);
    		 maybePercentageCorrect3=true;
    		 hardPercentage=hardPercentage+"%";
    	 } catch(Exception e){
	    	 hardPercentage="0%";
	    	 testBean.setPercentageError(true);
    	 }
     } else{
    	 hardPercentage="0%";
     }
     testBean.setHardPercentage(hardPercentage);
     if(maybePercentageCorrect1&&maybePercentageCorrect2&&maybePercentageCorrect3) testBean.setPercentageError(false);
%>

    <Form action="" method=post name=form id="setHardness">
      <div style="height:60px; padding-top:20px;">请设置练习难度：<br />（注：正式考试时，各难度题型占比均为1/3）</div>      	
      <div style="float:left; padding-top:5px;">
              简单题占比：
        <input type="text" id="easyPercentage" value=<%=easyPercentage %> name="easyPercentage" size="5" onfocus="selectTextE()" onchange=" message1()" />
      </div>
      <div style="float:right;">
        <div id="bg_bar1" style="width:255px;">
       	    <div id="bg_old1"></div>        
            <div id="touch_bar1" style="width:50px;"></div>
        </div>    
      </div>
      <div class="cleaner"></div> 
      <div style="float:left; padding-top:5px;">  
              中等题占比：
        <input type="text" id="normalPercentage" value=<%=normalPercentage %> name="normalPercentage" size="5" onfocus="selectTextN()" onchange=" message2()" />
      </div> 
      <div style="float:right;">
        <div id="bg_bar2" style="width:255px;">
       	    <div id="bg_old2"></div>        
            <div id="touch_bar2" style="width:50px;"></div>
        </div>  
      </div>
      <div class="cleaner"></div> 
      <div style="float:left; padding-top:5px;">     
              较难题占比：
        <input type="text" id="hardPercentage" value=<%=hardPercentage %> name="hardPercentage" size="5" onfocus="selectTextH()" onchange=" message3()" />
      </div>
      <div style="float:right;">
        <div id="bg_bar3" style="width:255px;">
       	    <div id="bg_old3"></div>        
            <div id="touch_bar3" style="width:50px;"></div>
        </div>
      </div>
       
<script>
var isTouch = false; //标志位，是否点击
var startX = 0; //鼠标点击的偏移量，实现平滑移动的保证
(function() {
    var touch_bar1 = document.getElementById("touch_bar1");    
	var bg_bar1 = document.getElementById("bg_bar1");    
	var bg_new1 = document.getElementById("bg_new1");    /*     为滑块绑定鼠标移动事件    当鼠标在滑块上移动时，使滑块在水平方向上跟随移动    是否移动要根据标志位检测滑块是否被点击    */    
	touch_bar1.onmousemove = function(ev) {        
	    var ev = ev || event;        // console.log(ev.clientX);
		if (ev.clientX - startX > 0 &&
            ev.clientX - startX < bg_bar1.offsetWidth - touch_bar1.offsetWidth &&
            isTouch) {
            touch_bar1.style.left = ev.clientX - startX + "px";
			document.getElementById("easyPercentage").value = ( ev.clientX - startX ) / 2 + "%";
        }    
	}    /*    设置滑块点击事件,改变标志位，并重置偏移量    */    
	touch_bar1.onmousedown = function(ev) { 
		if(touch_bar1.style.left != "0px"){
			touch_bar1.style.left = "0px";
			document.getElementById("easyPercentage").value = "0%";
		}
	    isTouch = true;        
		startX = event.clientX;    
	}    /*    设置滑块鼠标点击取消事件,改变标志位，重置偏移量    */    
	touch_bar1.onmouseup = function(ev) {        
	    isTouch = false;        
		startX = 0;        
		//touch_bar1.style.left = "0px";      
	}    /*    设置滑块鼠标移出事件,改变标志位，并重置偏移量    */    
	touch_bar1.onmouseout = function(ev) {   
	}     //重置偏移量  
    touch_bar1.style.left = "0px"; 
})();
function message1(){
	var a = parseFloat(document.getElementById("easyPercentage").value);
	touch_bar1.style.left = a*2 + "px";
}
(function() {
    var touch_bar2 = document.getElementById("touch_bar2");    
	var bg_bar2 = document.getElementById("bg_bar2");    
	var bg_new2 = document.getElementById("bg_new2");    /*     为滑块绑定鼠标移动事件    当鼠标在滑块上移动时，使滑块在水平方向上跟随移动    是否移动要根据标志位检测滑块是否被点击    */    
	touch_bar2.onmousemove = function(ev) {        
	    var ev = ev || event;        // console.log(ev.clientX);
		if (ev.clientX - startX > 0 &&
            ev.clientX - startX < bg_bar2.offsetWidth - touch_bar2.offsetWidth &&
            isTouch) {
            touch_bar2.style.left = ev.clientX - startX + "px";
			document.getElementById("normalPercentage").value = ( ev.clientX - startX ) / 2 + "%";
        }    
	}    /*    设置滑块点击事件,改变标志位，并重置偏移量    */    
	touch_bar2.onmousedown = function(ev) { 
		if(touch_bar2.style.left != "0px"){
			touch_bar2.style.left = "0px";
			document.getElementById("normalPercentage").value = "0%";
		}
	    isTouch = true;        
		startX = event.clientX;    
	}    /*    设置滑块鼠标点击取消事件,改变标志位，重置偏移量    */    
	touch_bar2.onmouseup = function(ev) {        
	    isTouch = false;        
		startX = 0;        
		//touch_bar2.style.left = "0px";      
	}    /*    设置滑块鼠标移出事件,改变标志位，并重置偏移量    */    
	touch_bar2.onmouseout = function(ev) {   
	}     //重置偏移量  
    touch_bar2.style.left = "0px"; 
})();
function message2(){
	var a = parseFloat(document.getElementById("normalPercentage").value);
	touch_bar2.style.left = a*2 + "px";	
}
(function() {
    var touch_bar3 = document.getElementById("touch_bar3");    
	var bg_bar3 = document.getElementById("bg_bar3");    
	var bg_new3 = document.getElementById("bg_new3");    /*     为滑块绑定鼠标移动事件    当鼠标在滑块上移动时，使滑块在水平方向上跟随移动    是否移动要根据标志位检测滑块是否被点击    */    
	touch_bar3.onmousemove = function(ev) {        
	    var ev = ev || event;        // console.log(ev.clientX);
		if (ev.clientX - startX > 0 &&
            ev.clientX - startX < bg_bar3.offsetWidth - touch_bar3.offsetWidth &&
            isTouch) {
            touch_bar3.style.left = ev.clientX - startX + "px";
			document.getElementById("hardPercentage").value = ( ev.clientX - startX ) / 2 + "%";
        }    
	}    /*    设置滑块点击事件,改变标志位，并重置偏移量    */    
	touch_bar3.onmousedown = function(ev) { 
		if(touch_bar3.style.left != "0px"){
			touch_bar3.style.left = "0px";
			document.getElementById("hardPercentage").value = "0%";
		}
	    isTouch = true;        
		startX = event.clientX;    
	}    /*    设置滑块鼠标点击取消事件,改变标志位，重置偏移量    */    
	touch_bar3.onmouseup = function(ev) {        
	    isTouch = false;        
		startX = 0;        
		//touch_bar3.style.left = "0px";      
	}    /*    设置滑块鼠标移出事件,改变标志位，并重置偏移量    */    
	touch_bar3.onmouseout = function(ev) {   
	}     //重置偏移量  
    touch_bar3.style.left = "0px"; 
})();
function message3(){
	var a = parseFloat(document.getElementById("hardPercentage").value);
	touch_bar3.style.left = a*2 + "px";	
}
</script>
       
       <div style="height:25px;"></div>
       <br /><div class="submit"><input type="submit" class="inputSubmit" value="" name="tijiao" /></div>
    </Form>
    <div class="others">
        <!--<div class="align"></div>-->                            
        <div class="other"><a href="Kemu1.jsp"><img src="images/student/quitButton.png" /></a></div>
    </div>
    <div class="cleaner"></div>
    <div class="height"></div>

<script type="text/javascript">
	function selectTextE(){
		var user1=document.getElementById("easyPercentage");
		user1.select();
	}
	function selectTextN(){
		var user2=document.getElementById("normalPercentage");
		user2.select();
	}
	function selectTextH(){
		var user3=document.getElementById("hardPercentage");
		user3.select();
	}
</script>
   
  <%! int ta; %>
  <%
  	testBean.setTimeCounterMin(0);
  	testBean.setTimeCounterSec(0);
  	boolean valid = true;
  	if(easyPercentage.equals("0%")&&normalPercentage.equals("0%")&&hardPercentage.equals("0%")&&testBean.getPercentageError()==false){
  		%> <jsp:setProperty name="testBean" property="pleaseChooseChapter" value="请设置练习难度" /> <%
  		valid=false;
  	} else if(testBean.getPercentageError()){
  		valid=false;
	  	%> <jsp:setProperty name="testBean" property="pleaseChooseChapter" value="请正确设置练习难度" /> <%
  	} else{
  	  	try{
  	  		float easyP = Float.parseFloat(easyPercentage.substring(0, easyPercentage.indexOf("%")));
  	  		float normalP = Float.parseFloat(normalPercentage.substring(0, normalPercentage.indexOf("%")));
  	  		float hardP = Float.parseFloat(hardPercentage.substring(0, hardPercentage.indexOf("%")));
  	  		if(easyP+normalP+hardP<99.5 || easyP+normalP+hardP>100.5){
  	  			valid=false;
  	  	  		%> <jsp:setProperty name="testBean" property="pleaseChooseChapter" value="各难度题型占比总和应为100%" /> <%
  	  		}
  	  	} catch(Exception e){} 
  	} 	
  	if(valid){
  		testBean.setRandomPractiseKemu("1");
%> 
		<jsp:setProperty name="testBean" property="pleaseChooseChapter" value="" />
		<br />你将练习：<br />
		<div class="pie2" style="margin:0 auto; position:relative;">
		
			<script type="text/javascript">
			gvChartInit();
			$(document).ready(function(){
					$('#myTable1').gvChart({
						chartType: 'PieChart',
						gvSettings: {
						vAxis: {title: 'No of players'},
						hAxis: {title: 'Month'},
						width: 500,
						height: 300
					}
				});
			});
			</script>

			<table id='myTable1'>
				<thead>
					<tr>
						<th></th>
						<th>简单题</th>
						<th>中等题</th>
						<th>较难题</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<th>1</th>
						<td><%=Float.parseFloat(easyPercentage.substring(0, easyPercentage.indexOf("%")))/100 %></td>
						<td><%=Float.parseFloat(normalPercentage.substring(0, normalPercentage.indexOf("%")))/100 %></td>
						<td><%=Float.parseFloat(hardPercentage.substring(0, hardPercentage.indexOf("%")))/100 %></td>
						</tr>
				</tbody>
			</table> 
		
		</div>
		简单题：<%=easyPercentage %>；
		中等题：<%=normalPercentage %>；
		较难题：<%=hardPercentage %><br />
  		设置成功，请点击开始，进入练习<br />
        <div class="height"></div>
  		<form action="randomPractiseServlet" method="post" name=form>
    		<input type="hidden" value="<%=testBean.getId()%>" name ="id">
    		<div class="submitStart"><input type="submit" class="inputSubmit" value="" name="tijiao" onclick="Loading()"/></div>
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
	function Notify(){
		if(a.length!=0&&a!="null") alert(a);
	}
</script>

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
	System.out.println("SetKemu1RandomPractise.jsp  "+e);
	testBean.setBackNews("系统异常，请重新登陆");
	response.sendRedirect("Index.jsp");
}
%>
</Html> 
