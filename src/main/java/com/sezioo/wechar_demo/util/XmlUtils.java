package com.sezioo.wechar_demo.util;

import java.io.Writer;

import com.sezioo.wechar_demo.dto.TextMessage;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.basic.DateConverter;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.naming.NoNameCoder;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class XmlUtils {
	public static <T> T xmlToBean(Class<T> clazz,String xml) {
		try {
            XStream xStream = new XStream();
            xStream .ignoreUnknownElements();
            xStream.processAnnotations(clazz);
            xStream.autodetectAnnotations(true);
            xStream.setClassLoader(clazz.getClassLoader());
            return (T) xStream.fromXML(xml);
        } catch (Exception e) {
            log.error("[XStream]XML转对象出错", e);
            throw new RuntimeException("[XStream]XML转对象出错");
        }
	}
	
	 public static String beanToXml(Object bean) {  
//        XStream xStream = new XStream();
        XStream xStream = new XStream(new XppDriver(new NoNameCoder()) {
        	 
            @Override
            public HierarchicalStreamWriter createWriter(Writer out) {
                return new PrettyPrintWriter(out) {
                    boolean cdata = true;
 
                    @Override
                    @SuppressWarnings("rawtypes")
                    public void startNode(String name, Class clazz) {
                        super.startNode(name, clazz);
                    }
 
                    @Override
                    public String encodeNode(String name) {
                        return name;
                    }
 
 
                    @Override		
                    protected void writeText(QuickWriter writer, String text) {
                        if (cdata) {
                            writer.write("<![CDATA[");
                            writer.write(text);
                            writer.write("]]>");
                        } else {
                            writer.write(text);
                        }
                    }
                };
            }
        });
        
        xStream.registerConverter(new DateConverter());  
        xStream.autodetectAnnotations(true);  
        return xStream.toXML(bean);  
     }
	 
	 public static void main(String[] args) {
		String xml = "<xml><ToUserName><![CDATA[gh_7b4db4e9dd5f]]></ToUserName><FromUserName><![CDATA[od3N-5m7P7zmTwdrNfuKkOPfEs_Q]]></FromUserName><CreateTime>1542617291</CreateTime><MsgType><![CDATA[text]]></MsgType><Content><![CDATA[111]]></Content><MsgId>6625490815517365779</MsgId></xml>";
		TextMessage textMessage = XmlUtils.xmlToBean(TextMessage.class, xml);
		System.out.println(textMessage.getFromUserName());
	 }
	 
	 
}
