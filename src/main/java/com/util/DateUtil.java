package com.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.alibaba.fastjson.JSONObject;

public class DateUtil {
	private DateUtil() {

	}

	private static DateUtil instance = new DateUtil();

	public static DateUtil getInstance() {
		return instance;
	}

	public String transformDateToFormatString(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String formatDate = formatter.format(date);
		return formatDate;
	}

	public static void main(String[] args) {
		Date date = new Date(System.currentTimeMillis());
		String result = DateUtil.getInstance().transformDateToFormatString(date);
		System.out.println(result);
		JSONObject json = new JSONObject();
		json.put("1", "1");
		System.out.println(json);
		json.put("1", "2");
		System.out.println(json);
	}
}
