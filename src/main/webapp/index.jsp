<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script src="jquery-3.2.1.js"></script>
<script type="text/javascript">
 function sendMessage(){
	 var message = " <xml> "
					 +" <ToUserName><![CDATA[公众号]]></ToUserName> "
					 +" <FromUserName><![CDATA[粉丝号]]></FromUserName> "
					 +" <CreateTime>1460537339</CreateTime> "
					 +" <MsgType><![CDATA[text]]></MsgType> "
					 +" <Content><![CDATA[欢迎开启公众号开发者模式]]></Content> "
					 +" <MsgId>6272960105994287618</MsgId> "
					 +" </xml>";
	
	$.post("<%=request.getContextPath() %>/WeiChat.do",message,function(data){
		alert(data);
	});
 }
 function saveAndReadToken(){
	 $.post("<%=request.getContextPath() %>/saveAndReadToken.do",{},function(data){
			alert(data);
		});
 }
</script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

	<button onclick="sendMessage()">点击发送消息</button>
	<button onclick="saveAndReadToken()">点击发送消息</button>
	<a href="<%=request.getContextPath() %>/saveAndReadToken.do">fasong</a>
	
</body>
</html>