package com.moredo.common.utils.encode;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

public class UrlUtils {
	@SuppressWarnings("unchecked")
	public static String getSortedContent(Map params) {
		List keys = new ArrayList(params.keySet());
		Collections.sort(keys);
		String prestr = "";
		for (int i = 0; i < keys.size(); i++) {
			String key = (String) keys.get(i);
			String value = (String) params.get(key);
			if (key != null && value != null) {
				if (i == keys.size() - 1) {
					prestr = prestr + key + "=" + value;
				} else {
					prestr = prestr + key + "=" + value + "&";
				}
			}
		}
		return prestr;
	}

	public static Map encodeParameters(Map<String, String> original, String charset) {
		Map<String, String> tempMap = new HashMap<String, String>();
		for (Map.Entry<String, String> entry : original.entrySet()) {
			try {
				if (entry.getKey() != null && entry.getValue() != null)
					tempMap.put(entry.getKey(), URLEncoder.encode(entry.getValue(), charset));
			} catch (UnsupportedEncodingException e) {
				throw new RuntimeException(e);
			}
		}
		return tempMap;
	}
}
