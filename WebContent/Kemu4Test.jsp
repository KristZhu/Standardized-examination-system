<%@page import="javax.servlet.jsp.tagext.TryCatchFinally"%>
<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>驾照标准化考试系统</title>
<link href="style/practise.css" rel="stylesheet" type="text/css" />
<link href="style/common.css" rel="stylesheet" type="text/css" />
<jsp:useBean id="testBean" class="mybean.data.Test_Bean" scope="session"/>
</head>

<%
try{
boolean isStudent = testBean.getIsStudent();
float lastScoreKemu4 = testBean.getLastScoreKemu4();
if(lastScoreKemu4>=0){
	if(isStudent) response.sendRedirect("SetKemu4Test.jsp");
	else response.sendRedirect("Index.jsp");
}

int originNumber = testBean.getOriginNumber();
int number = testBean.getNumber();
String correctAnswer = testBean.getCorrectAnswer().toUpperCase();
String type = testBean.getType();

if(number<=1){
	Connection con;
	Statement sql;
	try{
		String uri = "jdbc:mysql://127.0.0.1/licence?"+"user=root&password=&characterEncoding=gb2312";
		con=DriverManager.getConnection(uri);
		sql=con.createStatement();							
		String id = testBean.getId();
		sql.executeUpdate("UPDATE student SET scoreKemu4 = 0 WHERE id = " + id);
	} catch(Exception e){
		System.out.println("Kemu4Test.jsp "+e);
	}	
}

String tempTimeCounter;
int timeCounterMin, timeCounterSec;	//点击确定的
int tempTimeMin = testBean.getTimeCounterMin();	//点击下一题的
int tempTimeSec = testBean.getTimeCounterSec();
tempTimeCounter = request.getParameter("timeCounterMin");
try{
	timeCounterMin = Integer.parseInt(tempTimeCounter);
} catch(Exception exp){
	timeCounterMin=30;
}                    
tempTimeCounter = request.getParameter("timeCounterSec");
try{
	timeCounterSec = Integer.parseInt(tempTimeCounter);
} catch(Exception exp){
	timeCounterSec=0;
}
if(timeCounterMin==0&&timeCounterSec==0){
	timeCounterMin=30;
}
if(tempTimeMin==0&&tempTimeSec==0){
	tempTimeMin=30;
}
if(tempTimeMin*60+tempTimeSec<=timeCounterMin*60+timeCounterSec){
	testBean.setTimeCounterMin(tempTimeMin);
	testBean.setTimeCounterSec(tempTimeSec);
} else{
	testBean.setTimeCounterMin(timeCounterMin);
	testBean.setTimeCounterSec(timeCounterSec);
}

String studentAnswer = "";
studentAnswer = request.getParameter("R");
if(studentAnswer!=null&&studentAnswer.length()>=1){
    testBean.setAnswer(studentAnswer.trim());
} else{
	studentAnswer="";
}
String []studentAns;
try{
	studentAns=request.getParameterValues("R2");
	if(studentAns!=null&&studentAns.length>=1){
		studentAnswer=studentAns[0];
		for(int i=1; i<studentAns.length; i++){
			studentAnswer=studentAnswer+","+studentAns[i];
		}
	}
} catch(Exception e){	
}

boolean autoSubmit = testBean.getAutoSubmit();
%>
<body onload="startTimer();" onload="Notify();">
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
        	<a href="" class="selected"><img src="images/student/kemu1.png"/></a>
        </div>
        	<div class="class_2">
        		<a href=""><img src="images/student/zhangjielianxi.png" /></a>
        	</div>
        	<div class="class_2">
        		<a href=""><img src="images/student/suijilianxi.png" /></a>
        	</div>
            <div class="class_2">
        		<a href="" class="selected"><img src="images/student/canjiakaoshi.png" /></a>
        	</div>
        <div class="class_1">
        	<a href=""><img src="images/student/kemu4.png"/></a>
        </div>
        	<div class="class_2">
        		<a href=""><img src="images/student/zhangjielianxi.png" /></a>
        	</div>
        	<div class="class_2">
        		<a href=""><img src="images/student/suijilianxi.png" /></a>
        	</div>
            <div class="class_2">
        		<a href=""><img src="images/student/canjiakaoshi.png" /></a>
        	</div>
        <div class="class_1">
        	<a href=""><img src="images/student/gengduo.png"/></a>
        </div>
	</div>
	<p>
	  <!--left-->
	</p>
	<p>&nbsp; </p>
	<div id="middle">
    	<div id="practiseQuestion">
        	<div id="question">
            	<div class="questionTop"></div>
            	<div class="itemQuestion">
                    <div class="itemQuestionWord">
                    	<%=number %> / 50
                    	<%if(type.equals("3")){ %>（多选）<%} %><jsp:getProperty name="testBean" property="questions"/></td>
                    </div>
<%  					
                    String pic=testBean.getImage();
                    int i;
                    try{
                    	i = Integer.parseInt(pic);
                    } catch(Exception exp){
                    	i=0;
                    }
     				if(i==1) { 
%>
	                    <div class="itemQuestionImg">  					
	                        <img src="upload/<%=originNumber %>.png" align="middle"/>   
	                    </div>                                       
<%  				
					} else{
%>  					
                    	<img style="height:0px;"/>                                          
<%  				
					}
%>                
                </div>
                <div class="itemAllChoice">
                    <Form id="choose" action="" method=post name="choose">
                    	<input type="hidden" id="hiddenTimeCounterMin" name="timeCounterMin">
                    	<input type="hidden" id="hiddenTimeCounterSec" name="timeCounterSec">
                    	<input type="hidden" value="<%=testBean.getId()%>" name ="id">
<%					
						if(studentAnswer.length()>0){//已做完
							Connection con;
							Statement sql;
							String id = testBean.getId();        				
						
							if(!type.equals("3")){

							if(correctAnswer.equalsIgnoreCase("A")){
								if(studentAnswer.equalsIgnoreCase("A")){
%>                      		
									<input type="radio" name="R" checked value=A>
<%
								}else{
%>
	                            	<input type="radio" name="R" disabled="disabled" value=A>
<%
								}
%>
	                            <div class="itemChoiceRight">A <jsp:getProperty name="testBean" property="choiceA"/></div><br />
<%
							} else{
								if(studentAnswer.equalsIgnoreCase("A")){
%>                         		
	                        		<input type="radio" name="R" checked value=A>
		                            <div class="itemChoiceWrong">A <jsp:getProperty name="testBean" property="choiceA"/></div><br />
<%							
								} else{
%>                         		
	                        		<input type="radio" name="R" disabled="disabled" value=A>
		                            <div class="itemChoice">A <jsp:getProperty name="testBean" property="choiceA"/></div><br />
<%								
								}
							}

							if(correctAnswer.equalsIgnoreCase("B")){
								if(studentAnswer.equalsIgnoreCase("B")){
%>                      		
									<input type="radio" name="R" checked value=B>
<%
								}else{
%>
	                            	<input type="radio" name="R" disabled="disabled" value=B>
<%
								}
%>
	                            <div class="itemChoiceRight">B <jsp:getProperty name="testBean" property="choiceB"/></div><br />
<%
							} else{
								if(studentAnswer.equalsIgnoreCase("B")){
%>                         		
	                        		<input type="radio" name="R" checked value=B>
		                            <div class="itemChoiceWrong">B <jsp:getProperty name="testBean" property="choiceB"/></div><br />
<%							
								} else{
%>                         		
	                        		<input type="radio" name="R" disabled="disabled" value=B>
		                            <div class="itemChoice">B <jsp:getProperty name="testBean" property="choiceB"/></div><br />
<%								
								}
							}

                        	if(type.equals("1")){

							if(correctAnswer.equalsIgnoreCase("C")){
								if(studentAnswer.equalsIgnoreCase("C")){
%>                      		
									<input type="radio" name="R" checked value=C>
<%
								}else{
%>
	                            	<input type="radio" name="R" disabled="disabled" value=C>
<%
								}
%>
	                            <div class="itemChoiceRight">C <jsp:getProperty name="testBean" property="choiceC"/></div><br />
<%
							} else{
								if(studentAnswer.equalsIgnoreCase("C")){
%>                         	
	                        		<input type="radio" name="R" checked value=C>
		                            <div class="itemChoiceWrong">C <jsp:getProperty name="testBean" property="choiceC"/></div><br />
<%							
								} else{
%>                         		
	                        		<input type="radio" name="R" disabled="disabled" value=C>
		                            <div class="itemChoice">C <jsp:getProperty name="testBean" property="choiceC"/></div><br />
<%								
								}
							}
						
							if(correctAnswer.equalsIgnoreCase("D")){
								if(studentAnswer.equalsIgnoreCase("D")){
%>                      		
									<input type="radio" name="R" checked value=D>
<%
								}else{
%>
	                            	<input type="radio" name="R" disabled="disabled" value=D>
<%
								}
%>
	                            <div class="itemChoiceRight">D <jsp:getProperty name="testBean" property="choiceD"/></div><br />
<%
							} else{
								if(studentAnswer.equalsIgnoreCase("D")){
%>                         		
	                        		<input type="radio" name="R" checked value=D>
		                            <div class="itemChoiceWrong">D <jsp:getProperty name="testBean" property="choiceD"/></div><br />
<%							
								} else{
%>                         		
	                        		<input type="radio" name="R" disabled="disabled" value=D>
		                            <div class="itemChoice">D <jsp:getProperty name="testBean" property="choiceD"/></div><br />
<%								
								}
							}

                        	}
                      	
							} else{	//type=3

							if(correctAnswer.contains("A")||correctAnswer.contains("a")){
								if(studentAnswer.contains("A")||studentAnswer.contains("a")){
%>
									<input type="checkbox" name="R2" checked value=A>
<%									
								} else{
%>
									<input type="checkbox" name="R2" disabled="disabled" value=A>
<%											
								}
%>
								<div class="itemChoiceRight">A <jsp:getProperty name="testBean" property="choiceA"/></div><br />
<%
							} else{
								if(studentAnswer.contains("A")||studentAnswer.contains("a")){
%>
	                        		<input type="checkbox" name="R2" checked value=A>
		                            <div class="itemChoiceWrong">A <jsp:getProperty name="testBean" property="choiceA"/></div><br />
<%
								} else{
%>
	                        		<input type="checkbox" name="R2" disabled="disabled" value=A>
		                            <div class="itemChoice">A <jsp:getProperty name="testBean" property="choiceA"/></div><br />
<%									
								}
							}

							if(correctAnswer.contains("B")||correctAnswer.contains("b")){
								if(studentAnswer.contains("B")||studentAnswer.contains("b")){
%>
									<input type="checkbox" name="R2" checked value=B>
<%									
								} else{
%>
									<input type="checkbox" name="R2" disabled="disabled" value=B>
<%											
								}
%>
								<div class="itemChoiceRight">B <jsp:getProperty name="testBean" property="choiceB"/></div><br />
<%
							} else{
								if(studentAnswer.contains("B")||studentAnswer.contains("b")){
%>
	                        		<input type="checkbox" name="R2" checked value=B>
		                            <div class="itemChoiceWrong">B <jsp:getProperty name="testBean" property="choiceB"/></div><br />
<%
								} else{
%>
	                        		<input type="checkbox" name="R2" disabled="disabled" value=B>
		                            <div class="itemChoice">B <jsp:getProperty name="testBean" property="choiceB"/></div><br />
<%									
								}
							}

							if(correctAnswer.contains("C")||correctAnswer.contains("c")){
								if(studentAnswer.contains("C")||studentAnswer.contains("c")){
%>
									<input type="checkbox" name="R2" checked value=C>
<%									
								} else{
%>
									<input type="checkbox" name="R2" disabled="disabled" value=C>
<%											
								}
%>
								<div class="itemChoiceRight">C <jsp:getProperty name="testBean" property="choiceC"/></div><br />
<%
							} else{
								if(studentAnswer.contains("C")||studentAnswer.contains("c")){
%>
	                        		<input type="checkbox" name="R2" checked value=C>
		                            <div class="itemChoiceWrong">C <jsp:getProperty name="testBean" property="choiceC"/></div><br />
<%
								} else{
%>
	                        		<input type="checkbox" name="R2" disabled="disabled" value=C>
		                            <div class="itemChoice">C <jsp:getProperty name="testBean" property="choiceC"/></div><br />
<%									
								}
							}

							if(correctAnswer.contains("D")||correctAnswer.contains("d")){
								if(studentAnswer.contains("D")||studentAnswer.contains("d")){
%>
									<input type="checkbox" name="R2" checked value=D>
<%									
								} else{
%>
									<input type="checkbox" name="R2" disabled="disabled" value=D>
<%											
								}
%>
								<div class="itemChoiceRight">D <jsp:getProperty name="testBean" property="choiceD"/></div><br />
<%
							} else{
								if(studentAnswer.contains("D")||studentAnswer.contains("d")){
%>
	                        		<input type="checkbox" name="R2" checked value=D>
		                            <div class="itemChoiceWrong">D <jsp:getProperty name="testBean" property="choiceD"/></div><br />
<%
								} else{
%>
	                        		<input type="checkbox" name="R2" disabled="disabled" value=D>
		                            <div class="itemChoice">D <jsp:getProperty name="testBean" property="choiceD"/></div><br />
<%									
								}
							}

							}
							
						} else{	//还没做
							if(!type.equals("3")){
							if(autoSubmit){
%>   						
								<input type="radio" name="R" value=A onclick="Choose();">
	                            <div class="itemChoice">A <jsp:getProperty name="testBean" property="choiceA"/></div><br />
	                        	<input type="radio" name="R" value=B onclick="Choose();">
	                            <div class="itemChoice">B <jsp:getProperty name="testBean" property="choiceB"/></div><br />
<%
                        		if(type.equals("1")){
%>
		                        	<input type="radio" name="R" value=C onclick="Choose();">
		                            <div class="itemChoice">C <jsp:getProperty name="testBean" property="choiceC"/></div><br />
		                        	<input type="radio" name="R" value=D onclick="Choose();">
		                            <div class="itemChoice">D <jsp:getProperty name="testBean" property="choiceD"/></div><br />
<%
                        		}

							} else{
%>								
								<input type="radio" name="R" value=A>
	                            <div class="itemChoice">A <jsp:getProperty name="testBean" property="choiceA"/></div><br />                
	                        	<input type="radio" name="R" value=B>
	                            <div class="itemChoice">B <jsp:getProperty name="testBean" property="choiceB"/></div><br />
<%
	                        	if(type.equals("1")){
%>
		                        	<input type="radio" name="R" value=C>
		                            <div class="itemChoice">C <jsp:getProperty name="testBean" property="choiceC"/></div><br />
		                        	<input type="radio" name="R" value=D>
		                            <div class="itemChoice">D <jsp:getProperty name="testBean" property="choiceD"/></div><br />
<%	
								}

							}
							
							} else{	//type=3
%>
								<input type="checkbox" name="R2" value=A>
                            	<div class="itemChoice">A <jsp:getProperty name="testBean" property="choiceA"/></div><br />
                        		<input type="checkbox" name="R2" value=B>
                        		<div class="itemChoice">B <jsp:getProperty name="testBean" property="choiceB"/></div><br />
                            	<input type="checkbox" name="R2" value=C>
		                        <div class="itemChoice">C <jsp:getProperty name="testBean" property="choiceC"/></div><br />
		                        <input type="checkbox" name="R2" value=D>
		                        <div class="itemChoice">D <jsp:getProperty name="testBean" property="choiceD"/></div><br />
<%								
							}
						}


						if(studentAnswer==null||studentAnswer.length()==0){
%>
                        	<div class="submit"><input type="submit" class="inputSubmit" value="" name="tijiao" /></div>
                    	</Form>
<%
                        } else{
%>
                    	</Form>
                    	<div class="submit"><input type="submit" value="" name="tijiao" onclick="NoMoreSubmit();" /></div>
<%
                        }
                    
						if(studentAnswer==null||studentAnswer.length()==0){
%>                    
	                    	<div class="submitNext"><input type="submit" value="" name="xiayiti" onclick="NoNext();" /></div>
<%
                        } else if(number<50){
%>
							<form id="next" action="testServlet" method=post name="formNext">
		                    	<input type="hidden" id="hiddenTimeCounterMin2" name="timeCounterMin">
		                    	<input type="hidden" id="hiddenTimeCounterSec2" name="timeCounterSec">
		                    	<input type="hidden" value="<%=testBean.getId()%>" name ="id">
		                    	<input type="hidden" value="4" name="kemu">
								<div class="submitNext"><input type="submit" class="inputSubmit" value="" name="xiayiti" /></div>
							</form>
<%
                        } else{
                        	testBean.setFinishChapter("本次考试已结束");
%>
							<jsp:setProperty name="testBean" value="本次考试已结束" property="finishChapter" />
							<div class="submitNext"><input type="submit" class="inputSubmit" value="" name="xiayiti" onclick="Notify();" /></div>
<%                        	
                        }
%>

                        <div class="others">
                        	<div class="align"></div>                            
                            <div class="other"><a href="FinishKemu4Test.jsp" id="submitTest" onclick="return DoFinish();"><img src="images/student/finishTestButton.png" /></a></div>
                        </div>                 
                        
                        <div class="cleaner"></div>
                        <div id="autoSubmit">
                        	<form id="autoSubmit" action="autoSubmitServlet" method=post name="autoSubmit">
                        		<input type="hidden" name="whereToGo" value="kemu4Test" />
                        		<input type="hidden" id="hiddenTimeCounterMin2" name="timeCounterMin">
		                    	<input type="hidden" id="hiddenTimeCounterSec2" name="timeCounterSec">
                    			<input type="hidden" value="<%=testBean.getId()%>" name ="id">
<%								
								if(studentAnswer.length()>0){
									if(autoSubmit){
%>
										<input class="autoSubmitTrue" type="submit" name="autoSubmit" disabled="disabled" value=""/>
<%										
									} else{
%>
										<input class="autoSubmitFalse" type="submit" name="autoSubmit" disabled="disabled" value=""/>
<%										
									}
								} else{
									if(autoSubmit){
%>
										<input class="autoSubmitTrue" type="submit" name="autoSubmit" value=""/>
<%										
									} else{
%>
										<input class="autoSubmitFalse" type="submit" name="autoSubmit" value=""/>
<%										
									}
								}
%>								
								<div id="autoSubmitWord">自动确认（对多选题无效）</div>
                        	</form>
                        </div>
                </div>
<script language="javascript">
function NoMoreSubmit(){
	if(confirm("是否进入下一题？")){
		next.submit();
	}
}
function NoNext(){
	alert("请先选择答案，并点击确认");
}
function Choose(){
	choose.submit();
}
function autoSubmit(){
	autoSubmit.submit();
}
function DoFinish(){
	return confirm("交卷后不可更改，未作答题目不得分。是否确认交卷？");
}
</script>
            	<div class="bottom"></div>
            </div>
        </div><!--p/e Question-->
        
        <div id="tip">
        	<div class="tipTop"></div>
            <div class="itemTip">
<%
            	int c = originNumber/1000;
            	if(c==1) %>科目一 第 1 章 道路交通安全法律、法规和规章<%
            	if(c==2) %>科目一 第 2 章 交通信号<%
            	if(c==3) %>科目一 第 3 章 安全行车、文明驾驶基础知识<%
            	if(c==4) %>科目一 第 4 章 机动车驾驶操作相关基础知识<%
            	if(c==11) %>科目四 第 1 章 违法行为综合判断与案例分析<%
            	if(c==12) %>科目四 第 2 章 安全行车常识<%
            	if(c==13) %>科目四 第 3 章 常见交通标志、标线和交通手势辨识<%
            	if(c==14) %>科目四 第 4 章 驾驶职业道德和文明驾驶常识<%
            	if(c==15) %>科目四 第 5 章 恶劣气候和复杂道路条件下驾驶常识<%
            	if(c==16) %>科目四 第 6 章 紧急情况下避险常识<%
            	if(c==17) %>科目四 第 7 章 交通事故救护及常见危化品处置常识<%
%>
                <br /><div style="text-align: center; display:inline;">难度：
<%
            	String difficulty = testBean.getDifficulty();
            	if(difficulty.equals("1")) %><img src="images/student/hard1.png" style="height:20px;" /><%
            	if(difficulty.equals("2")) %><img src="images/student/hard2.png" style="height:20px;" /><%
            	if(difficulty.equals("3")) %><img src="images/student/hard3.png" style="height:20px;" /><%
%>
                </div><br />
<%
				if(studentAnswer==null||studentAnswer.length()==0){
					if(type.equals("3")){
						%>多选题，请选择至少两个你认为正确的答案<%
					} else if(type.equals("2")){
						%>判断题，请选择你认为正确的答案<%
					} else{
						%>选择题，请选择你认为正确的答案<%
					}
				} else{
					%>你的答案是 <%=studentAnswer %>；正确答案是 <%=correctAnswer %><%
					if(correctAnswer.equalsIgnoreCase(studentAnswer)){
						float s = testBean.getScore();
						testBean.setScore(s+2);
						%>。回答正确。<%
					} else{
						%>。<div style="display:inline; color:red;">回答错误。</div><%
					}
				}
%>
            </div>
            <div class="bottom"></div>
        </div>
	</div><!--middle -->
    
    <div id="right">
    	<div id="info">
        	<div class="infoTop"></div>
            <div class="itemInfo">
            	<div class="itemInfoLeft">
                	<div id="photo">
                    	<img src="image/photo/student/default.png" height="100" width="80" />
                    </div>
                    <div id="kemu">
                    	科目四
                    </div>
                </div>
                <div class="itemInfoRightTest">
                	姓名：<jsp:getProperty name="testBean" property="name" /><br />
                	学号：<jsp:getProperty name="testBean" property="id" /><br />
                                    考试时间：30分钟<br />
                                    当前分数：<font style="color:red"><jsp:getProperty name="testBean" property="score" /></font>
                </div>
                <div class="cleaner"></div>
            </div>
        	<div class="bottom"></div>
        </div>
        <div id="time">
        	<div class="timeTop"></div>
            <div class="itemTime">     
    	
<%
			int min = testBean.getTimeCounterMin();
			int sec = testBean.getTimeCounterSec();
			String min2, sec2;
			if(min<10) min2="0"+min;
				else min2=""+min;
			if(sec<10) sec2="0"+sec;
				else sec2=""+sec; 
			if(min==0&&sec==0) min2="30";	
			//如果要做完题后停止计时，见practise该部分
%>		
			<div id="timetext"><%=min2 %>:<%=sec2 %></div>
			<div id="timetext2" style="display:none;"></div>

			<div id="lastTimeCounterMin" style="display:none;"><%=testBean.getTimeCounterMin() %></div>
			<div id="lastTimeCounterSec" style="display:none;"><%=testBean.getTimeCounterSec() %></div>

<script>
	var minute=30;	
	minute=Number(document.getElementById('lastTimeCounterMin').innerHTML);
	var second=0;
	second=Number(document.getElementById('lastTimeCounterSec').innerHTML);
	if(minute==0&&second==0) minute=30;	
	var init;  
	function startTimer(){
    	init=setInterval(timer,1000);
    	if(document.getElementByID('stopTimer').innerHTML!="0"){
    		return;
    	}
    }
	function timer(){
		second=second-1;
		if(second<0){
			second=59;
			minute=minute-1;
		}
		if(second<0) second2="00";
		else if(second<10) second2="0"+second;
		else second2=second;
		if(minute<0) minute2="00";
		else if(minute<10) minute2="0"+minute;
		else minute2=minute;
		document.getElementById('timetext').innerHTML=minute2+':'+second2;
		document.getElementById('hiddenTimeCounterMin').value=minute;
		document.getElementById('hiddenTimeCounterSec').value=second;
		document.getElementById('hiddenTimeCounterMin2').value=minute;
		document.getElementById('hiddenTimeCounterSec2').value=second;
		document.getElementById('hiddenTimeCounterMin3').value=minute;
		document.getElementById('hiddenTimeCounterSec3').value=second;
		if(second==0&&minute==0){
			alert("考试时间结束");
			var href=document.getElementById("submitTest").href;
			window.location.href=href;
		}
	}
</script> 
            
            	<div class="timeButton">
                	<button type="button" onclick="hideOrShowTimer();" id="timeButton">隐藏时间</button>
                    <input type="hidden" value="1" id="hideOrShow" />
                </div>
                
<script>
	function hideOrShowTimer(){		
		if(document.getElementById("hideOrShow").value==1){
			document.getElementById("timeButton").innerHTML="显示时间";
			document.getElementById("hideOrShow").value=0;	
			document.getElementById("timetext").style.visibility="hidden";
			document.getElementById("timetext2").style.visibility="hidden";		
		} else{
			document.getElementById("timeButton").innerHTML="隐藏时间";
			document.getElementById("hideOrShow").value=1;
			document.getElementById("timetext").style.visibility="visible";
			document.getElementById("timetext2").style.visibility="visible";		
		}
	}
</script>
                
            </div>
            <div class="bottom"></div>
        </div>
        <div id="number">
        	
        </div>
    </div><!--right -->
</div><!--content -->
<div class="cleaner"></div>
<div class="height"></div>

<div id="finishChapter" style="color:#fff"><jsp:getProperty name="testBean" property="finishChapter" /></div>
<script language="javascript">
	var a = document.getElementById("finishChapter").innerText;
	function Notify(){
		if(a.length!=0&&a!="null"){
			alert(a);
			var href=document.getElementById("submitTest").href;
			window.location.href=href;
		}
	}
</script>

<div class="footer">
	<p>地址：中国深圳市南山区华侨城暨南大学深圳校区&nbsp;&nbsp;&nbsp;&nbsp;邮编：518000</p>
    <p>Email:krist.zhuzikai@gmail.com</p>
	<p>Copyright &copy;2018-2019 电子商务工作室 All rights reserved</p>
</div>
<!--wrapper -->
</div>

</body>
<%
} catch(Exception e){
	System.out.println("Kemu4Test.jsp  "+e);
	testBean.setBackNews("系统异常，请重新登陆");
	response.sendRedirect("Index.jsp");
}
%>
</html>