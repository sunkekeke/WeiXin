package sk.weichat.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.thoughtworks.xstream.XStream;

import sk.weichat.entity.Image;
import sk.weichat.entity.ImageMessage;
import sk.weichat.entity.News;
import sk.weichat.entity.NewsMessage;
import sk.weichat.entity.TextMessage;

public class MessageUtil {
	
	public static final String MESSAGE_TEXT="text";
	public static final String MESSAGE_NEWS="news";
	public static final String MESSAGE_IMAGE="image";
	public static final String MESSAGE_VOICE="voice";
	public static final String MESSAGE_VIDEO="video";
	public static final String MESSAGE_LINK="link";
	public static final String MESSAGE_LOCATION="location";
	public static final String MESSAGE_EVENT="event";
	public static final String MESSAGE_SUBSCRIBE="subscribe";
	public static final String MESSAGE_UNSUBSCRIBE="unsubscribe";
	public static final String MESSAGE_CLICK="click";
	public static final String MESSAGE_VIEW="view";
	
	
	
/**
 * xml转换为Map集合
 * @param req
 * @return
 * @throws IOException
 * @throws DocumentException
 */
	public static Map<String,String> xmlToMap(HttpServletRequest req) throws IOException, DocumentException{
		Map<String,String> map = new HashMap<String,String>();
		
		SAXReader reader = new SAXReader();
		
		InputStream ins = req.getInputStream();
		Document doc = reader.read(ins);
		
		Element root = doc.getRootElement();
		
		List<Element> list = root.elements();
		for(Element e:list){
			map.put(e.getName(), e.getText());
		}
		ins.close();
		return map;
	}
	
	/**
	 * 将文本对象转换为xml
	 */
	public static String MapToXml(TextMessage message){
		XStream xstream = new XStream();
		xstream.alias("xml", message.getClass());
		return xstream.toXML(message);
	}
	
	/**
	 *  格式化字符串
	 */
	public static String initText(String fromUserName,String toUserName,String content){
		TextMessage msg = new TextMessage();
		msg.setToUserName(fromUserName);
		msg.setFromUserName(toUserName);
		msg.setCreateTime(new Date().getTime()+"");
		msg.setMsgType(MessageUtil.MESSAGE_TEXT);
		msg.setContent(content);
		return MapToXml(msg);
	}
	
	
	/**
	 * 关注时返回消息
	 */
	public static String menuText(){
		StringBuffer sb = new StringBuffer();
		sb.append("谢谢关注，恭喜你来到这个神奇的地方\n");
		sb.append("你可以回复以下数字：\n");
		sb.append("1,自己玩\n");
		sb.append("2,跟机器人玩\n");
		sb.append("3,跟我玩 -_-\n");
		sb.append("4,百度-_-\n");
		sb.append("<====== 华丽的分隔符 ======>");
		return sb.toString();
	}
	
	/**
	 * 1消息
	 */
	public static String firstText(){
		StringBuffer sb = new StringBuffer();
		sb.append("好的，你可以离开了，或者\n");
		sb.append("你可以回复以下数字：\n");
		sb.append("11,贪吃蛇\n");
		sb.append("12,感叹号跳跃\n");
		sb.append("13,消消乐\n\n");
		sb.append("<====== 华丽的分隔符 ======>");
		return sb.toString();
	}
	
	/**
	 * 2消息
	 */
	public static String secondText(){
		StringBuffer sb = new StringBuffer();
		sb.append("好的，送你一个阿尔法狗\n\n");
		sb.append("<====== 华丽的分隔符 ======>");
		return sb.toString();
	}
	
	/**
	 * 3消息
	 */
	public static String thirdText(){
		StringBuffer sb = new StringBuffer();
		sb.append("聪明，这才是这个账号的精髓\n\n");
		sb.append("<====== 华丽的分隔符 ======>");
		return sb.toString();
	}
	
	/**
	 * 将图文对象转换为xml
	 */
	public static String newsMessageToXml(NewsMessage newsMessage){
		XStream xstream = new XStream();
		xstream.alias("xml", newsMessage.getClass());
		xstream.alias("item", new News().getClass());
		return xstream.toXML(newsMessage);
	}
	
	/**
	 *  格式化图文消息
	 */
	public static String initNewsMessage(String fromUserName,String toUserName){
		String message = "";
		List<News> list = new ArrayList<News>();
		NewsMessage newsMessage = new NewsMessage();
		
		News news = new News();
		news.setTitle("从这里开始");
		news.setPicUrl("http://superaera.bceapp.com/images/baidu.png");
		news.setDescription("百度");
		news.setUrl("https://www.baidu.com/");
		
		list.add(news);
		
		newsMessage.setToUserName(fromUserName);
		newsMessage.setFromUserName(toUserName);
		newsMessage.setCreateTime(new Date().getTime()+"");
		newsMessage.setMsgType(MESSAGE_NEWS);
		newsMessage.setArticleCount(list.size());
		newsMessage.setArticles(list);
		
		message = MessageUtil.newsMessageToXml(newsMessage);
		return message;
	}
	
	/**
	 * 格式化图片消息
	 */
	public static String initImageMessage(String fromUserName,String toUserName){
		String message = null;
		Image image = new Image();
		image.setMediaId("H7f_5I_dsqAIbU277TfGP_DY38xFE_roz4OcFIkdkS4HUJvCgvt0nePXt33lFIMw");
		ImageMessage imageMessage = new ImageMessage();
		imageMessage.setFromUserName(toUserName);
		imageMessage.setToUserName(fromUserName);
		imageMessage.setMsgType(MESSAGE_IMAGE);
		imageMessage.setCreateTime(new Date().getTime()+"");
		imageMessage.setImage(image);
		message = imageMessageToXml(imageMessage);
		return message;
	}
	
	/**
	 * 将图片消息转成xml
	 */
	public static String imageMessageToXml(ImageMessage imageMessage){
		XStream xstream = new XStream();
		xstream.alias("xml", imageMessage.getClass());
		return xstream.toXML(imageMessage);
	}
}
