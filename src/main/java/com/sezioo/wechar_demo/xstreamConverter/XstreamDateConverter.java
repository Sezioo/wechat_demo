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
		Date date = (Date) obj;
		if(obj != null) {
			long time = date.getTime();
			return String.valueOf(time);
		}
		return null;
	}

	@Override
	public Object fromString(String str) {
		Long timeMillis = Long.parseLong(str);
		Date date = new Date(timeMillis);
		return date;
	}


}
