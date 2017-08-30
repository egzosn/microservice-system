/**
 *
 */
package com.moredo.common.utils.beans;

import com.moredo.common.utils.log.Log4jUtil;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

/**
 * 根据属性名调用setAttr与getAttr
 *
 * @author ZaoSheng
 */
public class PropertyDescriptorUtil {

    /**
     * 设置属性
     * @param obj 对象
     * @param propertyName 属性名
     * @param value 值
     * @return value 值
     */
    public static Object setProperty(Object obj, String propertyName, Object value) {
        try {
            PropertyDescriptor pd = new PropertyDescriptor(propertyName, obj.getClass());//获取 clazz 类型中的 propertyName 的属性描述器
            Method setMethod = pd.getWriteMethod();//从属性描述器中获取 set 方法
            setMethod.invoke(obj, new Object[]{value});//调用 set 方法将传入的value值保存属性中去
        } catch (Exception e) {
            Log4jUtil.error("转化异常:", e);

        }
        return value;
    }

    /**
     *  获取属性值
     * @param obj 对象
     * @param propertyName 属性名
     * @return 属性值
     */
    public static Object getProperty(Object obj, String propertyName) {
        try {
            PropertyDescriptor pd = new PropertyDescriptor(propertyName, obj.getClass());//获取 clazz 类型中的 propertyName 的属性描述器
            Method getMethod = pd.getReadMethod();
            return getMethod.invoke(obj);//调用 set 方法将传入的value值保存属性中去
        } catch (Exception e) {
            Log4jUtil.error("转化异常:", e);
        }
        return null;//返回值
    }

}
