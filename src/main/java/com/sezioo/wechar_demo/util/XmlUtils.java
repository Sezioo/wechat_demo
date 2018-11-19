package com.sezioo.wechar_demo.util;

import java.util.Date;

import com.sezioo.wechar_demo.dto.TextMessage;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.basic.DateConverter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class XmlUtils {
	public static <T> T xmlToBean(Class<T> clazz,String xml) {
		try {
            XStream xstream = new XStream();
            xstream.processAnnotations(clazz);
            xstream.autodetectAnnotations(true);
            xstream.setClassLoader(clazz.getClassLoader());
            return (T) xstream.fromXML(xml);
        } catch (Exception e) {
            log.error("[XStream]XML转对象出错", e);
            throw new RuntimeException("[XStream]XML转对象出错");
        }
	}
	
	 public static String beanToXml(Object bean) {  
        XStream xstream = new XStream();  
        xstream.registerConverter(new DateConverter());  
        xstream.autodetectAnnotations(true);  
        return xstream.toXML(bean);  
     }
	 
	 public static void main(String[] args) {
		String xml = "<xml><ToUserName><![CDATA[gh_7b4db4e9dd5f]]></ToUserName><FromUserName><![CDATA[od3N-5m7P7zmTwdrNfuKkOPfEs_Q]]></FromUserName><CreateTime>1542617291</CreateTime><MsgType><![CDATA[text]]></MsgType><Content><![CDATA[111]]></Content><MsgId>6625490815517365779</MsgId></xml>";
		TextMessage textMessage = XmlUtils.xmlToBean(TextMessage.class, xml);
		System.out.println(textMessage.getFromUserName());
	 }
	 
	 
}
