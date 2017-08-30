package com.moredo.common.utils.common;

import org.apache.commons.beanutils.BeanComparator;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

/**
 * 常用工具类
 *
 * @author 肖红星
 * @create 2016/11/10
 */
public class CommonUtil {

    public static String getUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * List 元素的多个属性进行排序。例如 ListSorter.sort(list, "name", "age")，则先按
     * name 属性排序，name 相同的元素按 age 属性排序。
     *
     * @param list       包含要排序元素的 List
     * @param properties 要排序的属性。前面的值优先级高。
     */
    public static <V> void sort(List<V> list, final String... properties) {
        Collections.sort(list, new Comparator<V>() {
            public int compare(V o1, V o2) {
                if (o1 == null && o2 == null) return 0;
                if (o1 == null) return -1;
                if (o2 == null) return 1;

                for (String property : properties) {
                    Comparator c = new BeanComparator(property);
                    int result = c.compare(o1, o2);
                    if (result != 0) {
                        return result;
                    }
                }
                return 0;
            }
        });
    }

}
