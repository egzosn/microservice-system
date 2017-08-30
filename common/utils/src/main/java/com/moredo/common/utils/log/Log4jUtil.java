package com.moredo.common.utils.log;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 日志处理类
 *  @author: egan
 *  2016/8/26 14:33
 *
 */
public class Log4jUtil {

    private static final Logger logLoc = LoggerFactory.getLogger(Log4jUtil.class);


	public static void error(String msg, Throwable e){
		if(logLoc.isErrorEnabled()){
			String eMsg="";
			if(e!=null) eMsg=":"+e.getMessage();
			logLoc.error(msg+"\n"+ eMsg+getCaller());
		}
	}

	public static void warn(String msg){
		if(logLoc.isWarnEnabled()){
			logLoc.warn(msg+"\n"+getCaller());
		}
	}

	public static void info(String msg){
		if(logLoc.isInfoEnabled()){
			logLoc.info(msg+"\n"+getCaller());
		}
	}

	public static void debug(String msg){
		if(logLoc.isDebugEnabled()){
			logLoc.debug(msg+"\n"+getCaller());
		}
	}


	/**
	 * 获得代码调用的轨迹
	 */
	public static String getCaller(){
		StackTraceElement stack[] = (new Throwable()).getStackTrace();
		StringBuffer msg=new StringBuffer();
		for (int i = 2; i < stack.length&&i<3; i++)
		{
			StackTraceElement s = stack[i];
			msg.append("ClassName"+i+":");
			msg.append(s.getClassName());
			msg.append(";MethodName"+i+":");
			msg.append(s.getClassName());
			msg.append(";LineNumber"+i+":");
			msg.append(s.getLineNumber()+"\n");
		}
        return msg.toString();
	}


}
