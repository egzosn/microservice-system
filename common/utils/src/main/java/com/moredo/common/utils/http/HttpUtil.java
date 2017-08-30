package com.moredo.common.utils.http;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.Map;

public class HttpUtil {

	/**
	 * 获取请求的ip地址
	 */
	public static String getIpAddr(HttpServletRequest request) {
	      String ip = request.getHeader("x-forwarded-for");  
	      if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	           ip = request.getHeader("Proxy-Client-IP");  
	      }  
	            if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	                ip = request.getHeader("WL-Proxy-Client-IP");  
	            }  
	            if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	                ip = request.getRemoteAddr();  
	            }  
	               return ip;  
	       }  

	/**
	 * 获取真实ip地址
	 */
	 public static String getRealIp() throws SocketException { 
		 
		 String localip = null;// 本地IP，如果没有配置外网IP则返回它 
		 String netip = null;// 外网IP 
		 Enumeration<NetworkInterface> netInterfaces = 
			  NetworkInterface.getNetworkInterfaces(); 
		 InetAddress ip = null; 
		 boolean finded = false;// 是否找到外网IP 
		 while (netInterfaces.hasMoreElements() && !finded) { 
			 NetworkInterface ni = netInterfaces.nextElement(); 
			 Enumeration<InetAddress> address = ni.getInetAddresses(); 
			 while (address.hasMoreElements()) { 
				 ip = address.nextElement(); 
				 if (!ip.isSiteLocalAddress()  
						 && !ip.isLoopbackAddress()  
						  && ip.getHostAddress().indexOf(":") == -1) {// 外网IP 
					  netip = ip.getHostAddress(); 
					  finded = true; 
					  break; 
				 } else if (ip.isSiteLocalAddress()  
						  && !ip.isLoopbackAddress()  
						  && ip.getHostAddress().indexOf(":") == -1) {// 内网IP 
					 localip = ip.getHostAddress(); 
				 }
			 }
		 }
		 if (netip != null && !"".equals(netip)) { 
			 return netip; 
		 } else { 
			 return localip; 
		 }
		 
		 
	 }

	/**
	 * 生成请求头信息
	 *
	 * @param mediaType 请求内容类型
	 * @param headerMap 请求头参数
	 * @param params    请求参数
	 * @return
	 */
	public static HttpEntity<Object> buildHeader(MediaType mediaType, Map<String, String> headerMap, Object params) {
		if(mediaType == null){
			mediaType = MediaType.parseMediaType(MediaType.APPLICATION_JSON + ";charset=UTF-8");
		}
		HttpHeaders headers = null;
		if (mediaType != null || headerMap != null) {
			headers = new HttpHeaders();
			//设置请求类型类型
			if (mediaType != null) {
				headers.setContentType(mediaType);
			}
			if (headerMap != null && !headerMap.isEmpty()) {
				headers.setAll(headerMap);
			}
		}
		return new HttpEntity<Object>(params, headers);
	}
}
