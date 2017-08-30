package com.moredo.common.utils.encrypt;

import java.io.IOException;

public class Base64Util {

	/**  
	    * 编码  
	    * @param bstr  
	    * @return String  
	    */    
	   public static String encode(byte[] bstr){    
	   return new sun.misc.BASE64Encoder().encode(bstr);    
	   }    
	   
	   /**  
	    * 解码  
	    * @param str  
	    * @return string  
	    */    
	   public static byte[] decode(String str){    
	   byte[] bt = null;    
	   try {    
	       sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();    
	       bt = decoder.decodeBuffer( str );    
	   } catch (IOException e) {    
	       e.printStackTrace();    
	   }    
	   
	       return bt;    
	   }    
	   
	   
	 /*  public static void main(String[] args) {
		  String s1="eyJvdXRmcm9tQXJlYV9wcm92aW5jZU5hbWUiOiIiLCJvdXRmcm9tQXJlYV9wcm92aW5jZUNvZGUiOiIiLCJvdXRmcm9tQXJlYV9jaXR5TmFtZSI6IuWOpumXqOW4giIsIm91dGZyb21BcmVhX2NpdHlDb2RlIjoiIiwib3V0ZnJvbUFyZWFfZGlzTmFtZSI6Iua1t+ayp+WMuiIsIm91dGZyb21BcmVhX2Rpc0NvZGUiOiIiLCJkZXRhaWxzWzBdIjp7IndlaWdodCI6IjAuNTAiLCJsZW5ndGgiOiIxIiwid2lkdGgiOiIxIiwiaGlnaCI6IjEiLCJxdHkiOiIxIn0sIm91dFRvQXJlYV9wcm92aW5jZU5hbWUiOiIiLCJvdXRUb0FyZWFfcHJvdmluY2VDb2RlIjoiIiwib3V0VG9BcmVhX2NpdHlOYW1lIjoi6Zi/5ouJ5Lyv6IGU5ZCI6YWL6ZW/5Zu9Iiwib3V0VG9BcmVhX2NpdHlDb2RlIjoiVW5pdGVkIEFyYWIgRW1pcmF0ZXMiLCJvdXRUb0FyZWFfZGlzTmFtZSI6IiIsIm91dFRvQXJlYV9kaXNDb2RlIjoiIn0=";
		  String s2="eyJvdXRmcm9tQXJlYV9wcm92aW5jZU5hbWUiOiIiLCJvdXRmcm9tQXJlYV9wcm92aW5jZUNvZGUiOiIiLCJvdXRmcm9tQXJlYV9jaXR5TmFtZSI6IuWOpumXqOW4giIsIm91dGZyb21BcmVhX2NpdHlDb2RlIjoiIiwib3V0ZnJvbUFyZWFfZGlzTmFtZSI6Iua1t+ayp+WMuiIsIm91dGZyb21BcmVhX2Rpc0NvZGUiOiIiLCJkZXRhaWxzWzBdIjp7IndlaWdodCI6IjAuNTAiLCJsZW5ndGgiOiIxIiwid2lkdGgiOiIxIiwiaGlnaCI6IjEiLCJxdHkiOiIxIn0sIm91dFRvQXJlYV9wcm92aW5jZU5hbWUiOiIiLCJvdXRUb0FyZWFfcHJvdmluY2VDb2RlIjoiIiwib3V0VG9BcmVhX2NpdHlOYW1lIjoi6Zi/5ouJ5Lyv6IGU5ZCI6YWL6ZW/5Zu9Iiwib3V0VG9BcmVhX2NpdHlDb2RlIjoiVW5pdGVkIEFyYWIgRW1pcmF0ZXMiLCJvdXRUb0FyZWFfZGlzTmFtZSI6IiIsIm91dFRvQXJlYV9kaXNDb2RlIjoiIn0=";
		  if(s1.equals(s2)){
			  System.out.println("==");
		  }
	   }*/
	   
}
