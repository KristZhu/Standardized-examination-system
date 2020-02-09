package mybean.data;

public class Test_Bean {
   String[][] allQuestions;	 //代替原来rs和test数据库
   String[][] allQuestionsE;
   String[][] allQuestionsN;
   String[][] allQuestionsH;
   String id;            //存放考号
   String name;          //存放姓名
   String password;		 //存放密码
   String backNews;		 //存放错误信息
   float lastScoreKemu1; //存放上次科目1的分数（若无则为-1）
   float lastScoreKemu4; //存放上次科目4的分数（若无则为-1）
   float score=0;        //存放分数
   String questions;     //存放题目
   int number=0;         //存放题号 5
   int alreadyNumber;	 //上次练到 1004
   int newAlreadyNumber; //现在练到
   int originNumber;	 //原始编号 1005
   int testAmount;       //题目数量
   String kemu;
   String chapter;		 //所选择章节
   String pleaseChooseChapter;	//章节或难度错误信息
   String easyPercentage;
   String normalPercentage;
   String hardPercentage;
   boolean percentageError=false;	//难度百分比存在瞎写
   int kemu1Chapter1;	 //科目1章节1已完成练习数量
   int kemu1Chapter2;
   int kemu1Chapter3;
   int kemu1Chapter4;
   int kemu1Chapter1All;	 //科目1章节1全部数量
   int kemu1Chapter2All;
   int kemu1Chapter3All;
   int kemu1Chapter4All;
   int kemu4Chapter1;	 //科目4章节1已完成练习数量
   int kemu4Chapter2;
   int kemu4Chapter3;
   int kemu4Chapter4;
   int kemu4Chapter5;
   int kemu4Chapter6;
   int kemu4Chapter7;
   int kemu4Chapter1All;	 //科目4章节1全部数量
   int kemu4Chapter2All;
   int kemu4Chapter3All;
   int kemu4Chapter4All;
   int kemu4Chapter5All;
   int kemu4Chapter6All;
   int kemu4Chapter7All;
   int pictureAmount, wordAmount;//存放各题型量
   String choiceA,choiceB,choiceC,choiceD;//存放选择
   String image;         //题目的示意图的图像文件名
   String answer;        //存放用户给出的答案
   String correctAnswer; //存放正确答案
   String difficulty;	 //难度
   String mess="";       //存放提示信息 
   String type;	 		 //存放题型
   int timeCounterMin;	 //倒计时
   int timeCounterSec;	 //倒计时
   String finishChapter; //本章节练习完毕消息
   boolean autoSubmit=false;	 //自动提交答案
   String []columnName;	 //老师列名
   String [][]tableRecord = null;	//老师查询记录
   String addFailMess;
   String newQuestionNumber;	//添加题目新序号
   String addQuestionDataBase;
   String addQuestionTableName;
   String addQuestionContent;
   String addQuestionChoiceA;
   String addQuestionChoiceB;
   String addQuestionChoiceC;
   String addQuestionChoiceD;
   String addQuestionPic;
   String addQuestionAnswer;
   String addQuestionDifficulty;
   String addQuestionType;
   int pageSize=10;                     //每页显示的记录数
   int totalPages=1;                   //分页后的总页数
   int currentPage =1;              //当前显示页 
   String showQuestionsMess;	//当前显示第一章选择题
   String changeQuestionNumber;
   String changeQuestionContent;
   String changeQuestionChoiceA;
   String changeQuestionChoiceB;
   String changeQuestionChoiceC;
   String changeQuestionChoiceD;
   String changeQuestionPic;
   String changeQuestionAnswer;
   String changeQuestionDifficulty;
   String changeQuestionType;
   String changePicNumber;
   String randomPractiseKemu;
   boolean isStudent=false;	//是否允许进入学生界面
   boolean isTeacher=false;
   String registerBackNews;
   String auditRegisterBackNews;
   String registerId="";
   String registerName="";
   String registerIdNumber="";
   String registerPassword="";
   String retestBackNews;
   String verifyCode;
   String messageMess;
   
   public String[][] getAllQuestions(){
	   return allQuestions;
   } public void setAllQuestions(String [][]s) {
	   allQuestions=s;
   }
   public String[][] getAllQuestionsE(){
	   return allQuestionsE;
   } public void setAllQuestionsE(String [][]s) {
	   allQuestionsE=s;
   }
   public String[][] getAllQuestionsN(){
	   return allQuestionsN;
   } public void setAllQuestionsN(String [][]s) {
	   allQuestionsN=s;
   }
   public String[][] getAllQuestionsH(){
	   return allQuestionsH;
   } public void setAllQuestionsH(String [][]s) {
	   allQuestionsH=s;
   }
   public String getCorrectAnswer() {
      return correctAnswer;
   } public void setCorrectAnswer(String s){
      correctAnswer =s;
   }
   public void setId(String s){
      id=s;
   } public String getId(){
      return id;
   }
   public void setName(String s) {
	  name=s;
   } public String getName() {
	  return name;
   }
   public void setPassword(String s) {
	  password=s;
   } public String getPassword() {
	  return password;
   }
   public void setBackNews(String s) {
	   backNews=s;
   }  public String getBackNews() {
	   return backNews;
   }
   public void setLastScroeKemu1(float s){
	   lastScoreKemu1=s;
   }  public float getLastScoreKemu1() {
	   return lastScoreKemu1;
   }
   public void setLastScoreKemu4(float s){
	   lastScoreKemu4=s;
   }  public float getLastScoreKemu4() {
	   return lastScoreKemu4;
   }
   public void setScore(float s) {
      score = s;
   } public float getScore() {
      return score;
   }
   public void setQuestions(String s){
      questions=s;
   } public String getQuestions(){
      return questions;
   }
   public void setNumber(int s){
      number=s;
   } public int getNumber(){
      return number;
   }
   public void setAlreadyNumber(int s){
	   alreadyNumber=s;
   } public int getAlreadyNumber(){
	   return alreadyNumber;
   }
   public void setNewAlreadyNumber(int s){
	   newAlreadyNumber=s;
   } public int getNewAlreadyNumber(){
	   return newAlreadyNumber;
   }
   public void setOriginNumber(int s){
	  originNumber=s;
   } public int getOriginNumber(){
	  return originNumber;
   }
   public void setTestAmount(int s){
	  testAmount=s;
   } public int getTestAmount(){
	  return testAmount;
   }
   public void setKemu(String s) {
	   kemu=s;
   } public String getKemu() {
	   return kemu;
   }
   public void setChapter(String s) {
	   chapter=s;
   }  public String getChapter() {
	   return chapter;
   }
   public void setPleaseChooseChapter(String s) {
	   pleaseChooseChapter=s;
   } public String getPleaseChooseChapter() {
	   return pleaseChooseChapter;
   }
   public void setEasyPercentage(String s) {
	   easyPercentage=s;
   } public String getEasyPercentage() {
	   return easyPercentage;
   }
   public void setNormalPercentage(String s) {
	   normalPercentage=s;
   } public String getNormalPercentage() {
	   return normalPercentage;
   }
   public void setHardPercentage(String s) {
	   hardPercentage=s;
   } public String getHardPercentage() {
	   return hardPercentage;
   }
   public void setPercentageError(boolean s) {
	   percentageError=s;
   } public boolean getPercentageError() {
	   return percentageError;
   }
   public void setKemu1Chapter1(int s) {
	   kemu1Chapter1=s;
   }  public int getKemu1Chapter1() {
	   return kemu1Chapter1;
   }
   public void setKemu1Chapter2(int s) {
	   kemu1Chapter2=s;
   }  public int getKemu1Chapter2() {
	   return kemu1Chapter2;
   }
   public void setKemu1Chapter3(int s) {
	   kemu1Chapter3=s;
   }  public int getKemu1Chapter3() {
	   return kemu1Chapter3;
   }
   public void setKemu1Chapter4(int s) {
	   kemu1Chapter4=s;
   }  public int getKemu1Chapter4() {
	   return kemu1Chapter4;
   }
   public void setKemu4Chapter1(int s) {
	   kemu4Chapter1=s;
   }  public int getKemu4Chapter1() {
	   return kemu4Chapter1;
   }
   public void setKemu4Chapter2(int s) {
	   kemu4Chapter2=s;
   }  public int getKemu4Chapter2() {
	   return kemu4Chapter2;
   }
   public void setKemu4Chapter3(int s) {
	   kemu4Chapter3=s;
   }  public int getKemu4Chapter3() {
	   return kemu4Chapter3;
   }
   public void setKemu4Chapter4(int s) {
	   kemu4Chapter4=s;
   }  public int getKemu4Chapter4() {
	   return kemu4Chapter4;
   }
   public void setKemu4Chapter5(int s) {
	   kemu4Chapter5=s;
   }  public int getKemu4Chapter5() {
	   return kemu4Chapter5;
   }
   public void setKemu4Chapter6(int s) {
	   kemu4Chapter6=s;
   }  public int getKemu4Chapter6() {
	   return kemu4Chapter6;
   }
   public void setKemu4Chapter7(int s) {
	   kemu4Chapter7=s;
   }  public int getKemu4Chapter7() {
	   return kemu4Chapter7;
   }
   public void setKemu1Chapter1All(int s) {
	   kemu1Chapter1All=s;
   }  public int getKemu1Chapter1All() {
	   return kemu1Chapter1All;
   }
   public void setKemu1Chapter2All(int s) {
	   kemu1Chapter2All=s;
   }  public int getKemu1Chapter2All() {
	   return kemu1Chapter2All;
   }
   public void setKemu1Chapter3All(int s) {
	   kemu1Chapter3All=s;
   }  public int getKemu1Chapter3All() {
	   return kemu1Chapter3All;
   }
   public void setKemu1Chapter4All(int s) {
	   kemu1Chapter4All=s;
   }  public int getKemu1Chapter4All() {
	   return kemu1Chapter4All;
   }
   public void setKemu4Chapter1All(int s) {
	   kemu4Chapter1All=s;
   }  public int getKemu4Chapter1All() {
	   return kemu4Chapter1All;
   }
   public void setKemu4Chapter2All(int s) {
	   kemu4Chapter2All=s;
   }  public int getKemu4Chapter2All() {
	   return kemu4Chapter2All;
   }
   public void setKemu4Chapter3All(int s) {
	   kemu4Chapter3All=s;
   }  public int getKemu4Chapter3All() {
	   return kemu4Chapter3All;
   }
   public void setKemu4Chapter4All(int s) {
	   kemu4Chapter4All=s;
   }  public int getKemu4Chapter4All() {
	   return kemu4Chapter4All;
   }
   public void setKemu4Chapter5All(int s) {
	   kemu4Chapter5All=s;
   }  public int getKemu4Chapter5All() {
	   return kemu4Chapter5All;
   }
   public void setKemu4Chapter6All(int s) {
	   kemu4Chapter6All=s;
   }  public int getKemu4Chapter6All() {
	   return kemu4Chapter6All;
   }
   public void setKemu4Chapter7All(int s) {
	   kemu4Chapter7All=s;
   }  public int getKemu4Chapter7All() {
	   return kemu4Chapter7All;
   }
   public void setWordAmount(int s) {
	  wordAmount=s;
   } public int getWordAmount() {
	  return wordAmount;
   }
   public void setPictureAmount(int s) {
	  pictureAmount=s;
   } public int getPictureAmount() {
	  return pictureAmount;
   }   
   public void setChoiceA(String s){
      choiceA=s;
   } public String getChoiceA(){
      return choiceA;
   }
    public void setChoiceB(String s){
      choiceB=s;
   } public String getChoiceB(){
      return choiceB;
   }
    public void setChoiceC(String s){
      choiceC=s;
   } public String getChoiceC(){
      return choiceC;
   }
   public void setChoiceD(String s){
      choiceD=s;
   } public String getChoiceD(){
      return choiceD;
   }
   public void setImage(String s){
      image=s;
   } public String getImage(){
      return image;
   } 
   public void setAnswer(String s){
      answer=s;
   } public String getAnswer(){
      return answer;
   }
   public void setDifficulty(String s) {
	   difficulty=s;
   } public String getDifficulty() {
	   return difficulty;
   }
   public void setMess(String s){
      mess=s;
   } public String getMess(){
      return mess;
   }
   public void setType(String s) {
	  type=s;
   } public String getType() {
	  return type;
   }
   public void setTimeCounterMin(int s) {
	   timeCounterMin=s;
   } public int getTimeCounterMin() {
	   return timeCounterMin;
   }
   public void setTimeCounterSec(int s) {
	   timeCounterSec=s;
   } public int getTimeCounterSec() {
	   return timeCounterSec;
   }
   public void setFinishChapter(String s) {
	   finishChapter=s;
   } public String getFinishChapter() {
	   return finishChapter;
   }
   public void setAutoSubmit(boolean s) {
	   autoSubmit=s;
   } public boolean getAutoSubmit() {
	   return autoSubmit;
   }
   public void setTableRecord(String [][]s) {
	   tableRecord=s;
   } public String[][] getTableRecord(){
	   return tableRecord;
   }
   public void setColumnName(String []s) {
	   columnName=s;
   } public String[] getColumnName() {
	   return columnName;
   }
   public void setAddFailMess(String s) {
	   addFailMess=s;
   } public String getAddFailMess() {
	   return addFailMess;
   }
   public void setNewQuestionNumber(String s) {
	   newQuestionNumber=s;
   } public String getNewQuestionNumber() {
	   return newQuestionNumber;
   }
   public void setAddQuestionDataBase(String s) {
	   addQuestionDataBase=s;
   } public String getAddQuestionDataBase() {
	   return addQuestionDataBase;
   }
   public void setAddQuestionTableName(String s) {
	   addQuestionTableName=s;
   } public String getAddQuestionTableName() {
	   return addQuestionTableName;
   }
   public void setAddQuestionContent(String s) {
	   addQuestionContent=s;
   } public String getAddQuestionContent() {
	   return addQuestionContent;
   }
   public void setAddQuestionChoiceA(String s) {
	   addQuestionChoiceA=s;
   } public String getAddQuestionChoiceA() {
	   return addQuestionChoiceA;
   }
   public void setAddQuestionChoiceB(String s) {
	   addQuestionChoiceB=s;
   } public String getAddQuestionChoiceB() {
	   return addQuestionChoiceB;
   }
   public void setAddQuestionChoiceC(String s) {
	   addQuestionChoiceC=s;
   } public String getAddQuestionChoiceC() {
	   return addQuestionChoiceC;
   }
   public void setAddQuestionChoiceD(String s) {
	   addQuestionChoiceD=s;
   } public String getAddQuestionChoiceD() {
	   return addQuestionChoiceD;
   }
   public void setAddQuestionPic(String s) {
	   addQuestionPic=s;
   } public String getAddQuestionPic() {
	   return addQuestionPic;
   }
   public void setAddQuestionAnswer(String s) {
	   addQuestionAnswer=s;
   } public String getAddQuestionAnswer() {
	   return addQuestionAnswer;
   }
   public void setAddQuestionDifficulty(String s) {
	   addQuestionDifficulty=s;
   } public String getAddQuestionDifficulty() {
	   return addQuestionDifficulty;
   }
   public void setAddQuestionType(String s) {
	   addQuestionType=s;
   } public String getAddQuestionType() {
	   return addQuestionType;
   }
   public void setPageSize(int size){
	   pageSize=size;
   } public int getPageSize(){
	   return pageSize;
   } 
   public int getTotalPages(){
	   return totalPages;
   } public void setTotalPages(int n){
	   totalPages=n; 
   }
   public void setCurrentPage(int n){
       currentPage =n;
   } public int getCurrentPage(){
	   return currentPage ;
   }
   public void setShowQuestionsMess(String s) {
	   showQuestionsMess=s;
   } public String getShowQuestionsMess() {
	   return showQuestionsMess;
   }
   public void setChangeQuestionNumber(String s) {
	   changeQuestionNumber=s;
   } public String getChangeQuestionNumber() {
	   return changeQuestionNumber;
   }
   public void setChangeQuestionContent(String s) {
	   changeQuestionContent=s;
   } public String getChangeQuestionContent() {
	   return changeQuestionContent;
   }
   public void setChangeQuestionChoiceA(String s) {
	   changeQuestionChoiceA=s;
   } public String getChangeQuestionChoiceA() {
	   return changeQuestionChoiceA;
   }
   public void setChangeQuestionChoiceB(String s) {
	   changeQuestionChoiceB=s;
   } public String getChangeQuestionChoiceB() {
	   return changeQuestionChoiceB;
   }
   public void setChangeQuestionChoiceC(String s) {
	   changeQuestionChoiceC=s;
   } public String getChangeQuestionChoiceC() {
	   return changeQuestionChoiceC;
   }
   public void setChangeQuestionChoiceD(String s) {
	   changeQuestionChoiceD=s;
   } public String getChangeQuestionChoiceD() {
	   return changeQuestionChoiceD;
   }
   public void setChangeQuestionPic(String s) {
	   changeQuestionPic=s;
   } public String getChangeQuestionPic() {
	   return changeQuestionPic;
   }
   public void setChangeQuestionAnswer(String s) {
	   changeQuestionAnswer=s;
   } public String getChangeQuestionAnswer() {
	   return changeQuestionAnswer;
   }
   public void setChangeQuestionDifficulty(String s) {
	   changeQuestionDifficulty=s;
   } public String getChangeQuestionDifficulty() {
	   return changeQuestionDifficulty;
   }
   public void setChangeQuestionType(String s) {
	   changeQuestionType=s;
   } public String getChangeQuestionType() {
	   return changeQuestionType;
   }
   public void setChangePicNumber(String s) {
	   changePicNumber=s;
   } public String getChangePicNumber() {
	   return changePicNumber;
   }
   public void setRandomPractiseKemu(String s) {
	   randomPractiseKemu=s;
   } public String getRandomPractiseKemu() {
	   return randomPractiseKemu;
   }
   public void setIsStudent(boolean s) {
	   isStudent=s;
   } public boolean getIsStudent() {
	   return isStudent;
   }
   public void setIsTeacher(boolean s) {
	   isTeacher=s;
   } public boolean getIsTeacher() {
	   return isTeacher;
   }
   public void setRegisterBackNews(String s) {
	   registerBackNews=s;
   }  public String getRegisterBackNews() {
	   return registerBackNews;
   }
   public void setAuditRegisterBackNews(String s) {
	   auditRegisterBackNews=s;
   }  public String getAuditRegisterBackNews() {
	   return auditRegisterBackNews;
   }
   public void setRegisterId(String s) {
	   registerId=s;
   } public String getRegisterId() {
	   return registerId;
   }
   public void setRegisterName(String s) {
	   registerName=s;
   }  public String getRegisterName() {
	   return registerName;
   }
   public void setRegisterIdNumber(String s) {
	   registerIdNumber=s;
   } public String getRegisterIdNumber() {
	   return registerIdNumber;
   }
   public void setRegisterPassword(String s) {
	   registerPassword=s;
   } public String getRegisterPassword() {
	   return registerPassword;
   }
   public void setRetestBackNews(String s) {
	   retestBackNews=s;
   } public String getRetestBackNews() {
	   return retestBackNews;
   }
   public void setVerifyCode(String s) {
	   verifyCode=s;
   } public String getVerifyCode() {
	   return verifyCode;
   }
   public void setMessageMess(String s) {
	   messageMess=s;
   } public String getMessageMess() {
	   return messageMess;
   }
}
