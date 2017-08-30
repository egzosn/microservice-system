package com.moredo.common.utils.encrypt;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class CommonUtil {

	public static String getGMTString(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.US);
		format.setTimeZone(TimeZone.getTimeZone("GMT"));
		return format.format(date);
	}

	public static final String HMAC_SHA1_ALGORITHM = "HmacSHA1";

	public static String calcHash(String secretAccessKey, String data) {
		try {
			Charset utf8 = Charset.forName("UTF-8");
			SecretKeySpec signingKey = new SecretKeySpec(secretAccessKey.getBytes(utf8), HMAC_SHA1_ALGORITHM);

			Mac mac = Mac.getInstance(HMAC_SHA1_ALGORITHM);
			mac.init(signingKey);

			byte[] rawHmac = mac.doFinal(data.getBytes(utf8));
			return new String(base64Encode(rawHmac), utf8);
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}

	public static final char[] digits = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-_".toCharArray();

	public static byte[] base64Encode(byte[] data) {
		int sourceChunks = data.length / 3;
		int len = ((data.length + 2) / 3) * 4;
		byte[] result = new byte[len];
		int extraBytes = data.length - (sourceChunks * 3);

		int dataIndex = 0;
		int resultIndex = 0;
		int allBits = 0;
		for (int i = 0; i < sourceChunks; i++) {
			allBits = 0;
			for (int j = 0; j < 3; j++)
				allBits = (allBits << 8) | (data[dataIndex++] & 0xff);

			for (int j = resultIndex + 3; j >= resultIndex; j--) {
				result[j] = (byte) digits[(allBits & 0x3f)]; // Bottom

				allBits = allBits >>> 6;
			}
			resultIndex += 4;
		}

		switch (extraBytes) {
		case 1:
			allBits = data[dataIndex++];
			allBits = allBits << 8;
			allBits = allBits << 8;
			for (int j = resultIndex + 3; j >= resultIndex; j--) {
				result[j] = (byte) digits[(allBits & 0x3f)];

				allBits = allBits >>> 6;
			}
			result[result.length - 1] = (byte) '=';
			result[result.length - 2] = (byte) '=';
			break;
		case 2:
			allBits = data[dataIndex++];
			allBits = (allBits << 8) | (data[dataIndex++] & 0xff);
			allBits = allBits << 8;
			for (int j = resultIndex + 3; j >= resultIndex; j--) {
				result[j] = (byte) digits[(allBits & 0x3f)];

				allBits = allBits >>> 6;
			}
			result[result.length - 1] = (byte) '=';
			break;
		}
		return result;
	}
	
}
