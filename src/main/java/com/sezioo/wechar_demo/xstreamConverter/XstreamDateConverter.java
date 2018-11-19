package com.sezioo.wechar_demo.xstreamConverter;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.SingleValueConverter;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class XstreamDateConverter implements SingleValueConverter {

	@Override
	public boolean canConvert(Class type) {
		return Date.class == type;
	}

	@Override
	public String toString(Object obj) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format((Date)obj);
	}

	@Override
	public Object fromString(String str) {
		Long timeMillis = Long.parseLong(str);
		Date date = new Date(timeMillis);
		return date;
	}


}
