package com.moredo.common.utils.beans;

import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.util.Assert;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;

/**
 * Created by zy on 2016/11/15.
 * 实体工具类
 */
public class BeanUtils extends org.springframework.beans.BeanUtils  {

    public static void copyNotNulProperties(Object source, Object target, String[] ignoreProperties) throws BeansException {
        copyNNulProperties(source, target, null, ignoreProperties);
    }

    public static void copyNotNulProperties(Object source, Object target) throws BeansException {
        copyNNulProperties(source, target, null, null);
    }

    /**
     * object 2  MultiValueMap
     * @param obj
     * @return
     * @throws Exception
     */
    public static MultiValueMap<String, String> objectToMultiValueMap(Object obj) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        if(obj == null){
            return null;
        }
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>(1);

        Field[] declaredFields = obj.getClass().getDeclaredFields();
        Method[] methods = obj.getClass().getDeclaredMethods();
        for (Field field : declaredFields) {
            field.setAccessible(true);
            String fieldGetName = parGetName(field.getName());
            if (!checkGetMet(methods, fieldGetName)) {
                map.add(field.getName(), String.valueOf(field.get(obj)));
            }else {
                Method fieldGetMet = obj.getClass().getMethod(fieldGetName, new Class[]{});
                Object fieldVal = fieldGetMet.invoke(obj, new Object[]{});
                map.add(field.getName(),String.valueOf(fieldVal));
            }
        }

        return map;
    }

    /**
     * 校验实体全部属性是否有为空
     * @param bean              校验实体
     * @return
     */
    public static boolean checkFieldValueNull(Object bean) {
        try {
            if (bean == null) {
                return true;
            }
            Class<?> cls = bean.getClass();
            Method[] methods = cls.getDeclaredMethods();
            Field[] fields = cls.getDeclaredFields();
            for (Field field : fields) {
                String fieldGetName = parGetName(field.getName());
                if (!checkGetMet(methods, fieldGetName)) {
                    return true;
                }
                Method fieldGetMet = cls.getMethod(fieldGetName, new Class[]{});
                Object fieldVal = fieldGetMet.invoke(bean, new Object[]{});
                if (fieldVal != null) {
                    if ("".equals(fieldVal)) {
                        return true;
                    }
                }else {
                    return true;
                }
            }
            return false;
        }catch (Exception e) {
            e.printStackTrace();
            return true;
        }
    }


    /**
     * 拼接某属性的 get方法
     *
     * @param fieldName
     * @return String
     */
    private static String parGetName(String fieldName) {
        if (null == fieldName || "".equals(fieldName)) {
            return null;
        }
        int startIndex = 0;
        if (fieldName.charAt(0) == '_')
            startIndex = 1;
        return "get"
                + fieldName.substring(startIndex, startIndex + 1).toUpperCase()
                + fieldName.substring(startIndex + 1);
    }

    /**
     * 判断是否存在某属性的 get方法
     *
     * @param methods
     * @param fieldGetMet
     * @return boolean
     */
    private static boolean checkGetMet(Method[] methods, String fieldGetMet) {
        for (Method met : methods) {
            if (fieldGetMet.equals(met.getName())) {
                return true;
            }
        }
        return false;
    }

    private static void copyNNulProperties(Object source, Object target, Class<?> editable, String[] ignoreProperties) throws BeansException {
        Assert.notNull(source, "Source must not be null");
        Assert.notNull(target, "Target must not be null");
        Class actualEditable = target.getClass();
        if(editable != null) {
            if(!editable.isInstance(target)) {
                throw new IllegalArgumentException("Target class [" + target.getClass().getName() + "] not assignable to Editable class [" + editable.getName() + "]");
            }

            actualEditable = editable;
        }

        PropertyDescriptor[] targetPds = getPropertyDescriptors(actualEditable);
        List ignoreList = ignoreProperties != null? Arrays.asList(ignoreProperties):null;
        PropertyDescriptor[] arr$ = targetPds;
        int len$ = targetPds.length;

        for(int i$ = 0; i$ < len$; ++i$) {
            PropertyDescriptor targetPd = arr$[i$];
            if(targetPd.getWriteMethod() != null && (ignoreProperties == null || !ignoreList.contains(targetPd.getName()))) {
                PropertyDescriptor sourcePd = getPropertyDescriptor(source.getClass(), targetPd.getName());
                if(sourcePd != null && sourcePd.getReadMethod() != null) {
                    try {
                        Method ex = sourcePd.getReadMethod();
                        if(!Modifier.isPublic(ex.getDeclaringClass().getModifiers())) {
                            ex.setAccessible(true);
                        }

                        Object value = ex.invoke(source, new Object[0]);
                        if (null == value) {continue;}
                        Method writeMethod = targetPd.getWriteMethod();
                        if(!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
                            writeMethod.setAccessible(true);
                        }

                        writeMethod.invoke(target, new Object[]{value});
                    } catch (Throwable var15) {
                        throw new FatalBeanException("Could not copy properties from source to target", var15);
                    }
                }
            }
        }

    }
}
