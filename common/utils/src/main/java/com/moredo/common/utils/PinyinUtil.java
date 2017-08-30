package com.moredo.common.utils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * 
 * 获取汉语文字的拼音
 * 
 * @author Everest
 * @version 1.0 23/07/2010
 * 
 */
public class PinyinUtil
{

    /**
     * 获取字符串内的所有汉字的汉语拼音
     * 
     * @param src
     * @return		所有汉字的汉语拼音
     */
    public static String spell(String src)
    {
    	HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE); // 小写拼音字母
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE); // 不加语调标识
        format.setVCharType(HanyuPinyinVCharType.WITH_V); // u:的声母替换为v

        StringBuffer sb = new StringBuffer();
        int strLength = src.length();
        try
        {
            for(int i = 0; i<strLength; i++)
            {
                // 对英文字母的处理：小写字母转换为大写，大写的直接返回
                char ch = src.charAt(i);
                if(ch>='a'&&ch<='z')
                    sb.append((char)(ch-'a'+'A'));
                if(ch>='A'&&ch<='Z')
                    sb.append(ch);
                // 对汉语的处理
                String[] arr = PinyinHelper.toHanyuPinyinStringArray(ch, format);
                if(arr!=null&&arr.length>0)
                    sb.append(arr[0]).append(" ");
            }
        }
        catch(BadHanyuPinyinOutputFormatCombination e)
        {
            e.printStackTrace();
        }
        return sb.toString();
    }

    /**
     * 获取字符串内的所有汉字的汉语拼音并大写每个字的首字母
     * 
     * @param src
     * @return		所有汉字的汉语拼音并大写每个字的首字母
     */
    public static String spellWithTone(String src)
    {
    	HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);// 小写
        format.setToneType(HanyuPinyinToneType.WITH_TONE_MARK);// 标声调
        format.setVCharType(HanyuPinyinVCharType.WITH_U_UNICODE);// u:的声母

        if(src==null)
        {
            return null;
        }
        try
        {
            StringBuilder sb = new StringBuilder();
            for(int i = 0; i<src.length(); i++)
            {
                // 对英文字母的处理：小写字母转换为大写，大写的直接返回
                char ch = src.charAt(i);
                if(ch>='a'&&ch<='z')
                    sb.append((char)(ch-'a'+'A'));
                if(ch>='A'&&ch<='Z')
                    sb.append(ch);
                // 对汉语的处理
                String[] arr = PinyinHelper.toHanyuPinyinStringArray(ch, format);
                if(arr==null||arr.length==0)
                {
                    continue;
                }
                String s = arr[0];// 不管多音字,只取第一个
                char c = s.charAt(0);// 大写第一个字母
                String pinyin = String.valueOf(c).toUpperCase().concat(s.substring(1));
                sb.append(pinyin).append(" ");
            }
            return sb.toString();
        }
        catch(BadHanyuPinyinOutputFormatCombination e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取字符串内的所有汉字的汉语拼音并大写每个字的首字母
     * 
     * @param src
     * @return		所有汉字的汉语拼音并大写每个字的首字母
     */
    public static String spellNoneTone(String src)
    {
    	HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);// 小写
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);// 标声调
        format.setVCharType(HanyuPinyinVCharType.WITH_U_UNICODE);// u:的声母

        if(src==null)
        {
            return null;
        }
        try
        {
            StringBuilder sb = new StringBuilder();
            for(int i = 0; i<src.length(); i++)
            {
                // 对英文字母的处理：小写字母转换为大写，大写的直接返回
                char ch = src.charAt(i);
                if(ch>='a'&&ch<='z')
                    sb.append((char)(ch-'a'+'A'));
                if(ch>='A'&&ch<='Z')
                    sb.append(ch);
                // 对汉语的处理
                String[] arr = PinyinHelper.toHanyuPinyinStringArray(ch, format);
                if(arr==null||arr.length==0)
                {
                    continue;
                }
                String s = arr[0];// 不管多音字,只取第一个
                char c = s.charAt(0);// 大写第一个字母
                String pinyin = String.valueOf(c).toUpperCase().concat(s.substring(1));
                sb.append(pinyin).append("");
            }
            return sb.toString();
        }
        catch(BadHanyuPinyinOutputFormatCombination e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取汉语第一个字的首英文字母
     * 
     * @param src
     * @return		汉语第一个字的首英文字母
     */
    public static String getTerm(String src)
    {
        String res = spell(src);
        if(res!=null&&res.length()>0)
        {
            return res.toUpperCase().charAt(0)+"";
        }
        else
        {
            return "OT";
        }
    }

    /**
     * 汉字转换位汉语拼音首字母，英文字符不变
     * 
     * @param chines
     *            汉字
     * @return 拼音
     */
    public static String converterToFirstSpell(String chines)
    {
    	if(chines==null||chines.length()==0)return "";
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        defaultFormat.setVCharType(HanyuPinyinVCharType.WITH_U_AND_COLON);
        StringBuffer pinyinName = new StringBuffer();
        int len = chines.length();
        for(int i = 0; i<len; i++)
        {
        	char c = chines.charAt(i);
        	if(c>='0' && c<='9'){
        		pinyinName.append(c);
        	}else	if(c >= 'a' && c <= 'z') {
        		pinyinName.append(c);
        	}else if(c >= 'A' && c <= 'Z') {
        		pinyinName.append(c);
        	}else if(c > 128) {
        		Character cTemp = null;
                try
                {
                    String[] temps = PinyinHelper.toHanyuPinyinStringArray(c, defaultFormat);
                    if(temps == null || temps.length <= 0 || temps[0].length() <= 0) {
                    	//没有对应的拼音
                    }else {
                    	cTemp = temps[0].charAt(0);
                    }
                }
                catch(BadHanyuPinyinOutputFormatCombination e)
                {
                	cTemp = null;
                }
                if(cTemp == null){
                	pinyinName.append(c);
                }else{
                	pinyinName.append(cTemp);
                }
            }else{
            	pinyinName.append(c);
            }
        }
        return pinyinName.toString();
    }

    /**
     * 获取字符串内的所有汉字的汉语拼音并大写每个字的首字母(原本有方法，但是没返回不能转成拼音的字符，暂时这里使用，所以重写在这里)
     *
     * @param src
     * @return		所有汉字的汉语拼音并大写每个字的首字母
     */
    public static String spellNoneTone2(String src)
    {
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);// 小写
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);// 标声调
        format.setVCharType(HanyuPinyinVCharType.WITH_U_UNICODE);// u:的声母

        if(src==null)
        {
            return null;
        }
        try
        {
            StringBuilder sb = new StringBuilder();
            for(int i = 0; i<src.length(); i++)
            {
                // 对英文字母的处理：小写字母转换为大写，大写的直接返回
                char ch = src.charAt(i);
                if(ch>='a'&&ch<='z'){
                    sb.append((char)(ch-'a'+'A'));
                    continue;
                }
                if(ch>='A'&&ch<='Z'){
                    sb.append(ch);
                    continue;
                }
                // 对汉语的处理
                String[] arr = PinyinHelper.toHanyuPinyinStringArray(ch, format);
                if(arr==null||arr.length==0)
                {
                    sb.append(ch);
                    continue;
                }
                String s = arr[0];// 不管多音字,只取第一个
                char c = s.charAt(0);// 大写第一个字母
                String pinyin = String.valueOf(c).toUpperCase().concat(s.substring(1));
                sb.append(pinyin).append("");
            }
            return sb.toString();
        }
        catch(BadHanyuPinyinOutputFormatCombination e)
        {
            e.printStackTrace();
        }
        return null;
    }
    
}
