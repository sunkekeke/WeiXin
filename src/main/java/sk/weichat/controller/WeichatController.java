package sk.weichat.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.DocumentException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import sk.weichat.entity.AccessToken;
import sk.weichat.utils.AccessTokenUtil;
import sk.weichat.utils.CheckUtil;
import sk.weichat.utils.MessageUtil;

@Controller

public class WeichatController {
	
	@RequestMapping(value="/WeiChat.do",method=RequestMethod.GET,produces="text/html;charset=UTF-8")
	@ResponseBody
	public String WeiChatServlet(@RequestParam String signature,@RequestParam String timestamp,@RequestParam String nonce,@RequestParam String echostr){
		
		if(CheckUtil.checkSignature(signature, timestamp, nonce)){
			return echostr;
		}else{
			return null;
		}
		
	}
	
	@RequestMapping(value="/WeiChat.do",method=RequestMethod.POST,produces="text/html;charset=UTF-8")
	@ResponseBody
	public String replayMessage(HttpServletRequest request) throws IOException{
		try {
			Map<String,String> map = MessageUtil.xmlToMap(request);
			String fromUserName = map.get("FromUserName");
			String toUserName= map.get("ToUserName");
			String msgType = map.get("MsgType");
			String content = map.get("Content");
			String message = "";
			if(MessageUtil.MESSAGE_TEXT.equals(msgType)){
				if("1".equals(content)){
					message = MessageUtil.initText(fromUserName, toUserName, MessageUtil.firstText());
				}else if("2".equals(content)){
					message = MessageUtil.initText(fromUserName, toUserName, MessageUtil.secondText());
				}else if("3".equals(content)){
					message = MessageUtil.initText(fromUserName, toUserName, MessageUtil.thirdText());
				}else if("？".equals(content)||"?".equals(content)){
					message = MessageUtil.initText(fromUserName, toUserName, MessageUtil.menuText());
				}else if("4".equals(content)||"baidu".equals(content)||"百度".equals(content)){
					message = MessageUtil.initNewsMessage(fromUserName, toUserName);
				}else if("5".equals(content)){
					message = MessageUtil.initImageMessage(fromUserName, toUserName);
				}else{
					message = MessageUtil.initText(fromUserName, toUserName, "你发送的消息是："+content);
				}
				return message;
			}else if(MessageUtil.MESSAGE_EVENT.equals(msgType)){
				String eventType = map.get("Event"); 
				if(MessageUtil.MESSAGE_SUBSCRIBE.equals(eventType)){
					message = MessageUtil.initText(fromUserName, toUserName, MessageUtil.menuText());
				}
			}
			return message;
		} catch (IOException e) {
			e.printStackTrace();
			return "error";
		} catch (DocumentException e) {
			e.printStackTrace();
			return "error";
		}
	}
	
	@RequestMapping(value="/saveAndReadToken.do")
	@ResponseBody
	public String test(HttpServletRequest request) throws IOException, ClassNotFoundException{
		//AccessToken token = getAccessToken();
		//	File file = new File("http://superaera.bceapp.com/file/");
			AccessToken token = new AccessToken();
			token.setAccess_token("aaaaaaaaaaaa");
			token.setExpires_in(111111111);
			AccessTokenUtil.saveAccessToken(token);
		    
			System.out.println(request.getServletPath());
			System.out.println( request.getContextPath());
			
			AccessToken token2 = AccessTokenUtil.readAccessToken();
			System.out.println("票据："+token2.getAccess_token());
			System.out.println("时间："+token2.getExpires_in());
			return "success";
	}
}
