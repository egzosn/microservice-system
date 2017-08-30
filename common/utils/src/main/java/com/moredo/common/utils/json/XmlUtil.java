package com.moredo.common.utils.json;



import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

import java.util.Map;

/**
 * Created by ZaoSheng on 2015/6/15.
 */
public class XmlUtil {

    public static Map xml2Map(String xml) {
        return xml2Entity(xml, Map.class);
    }

    public static <T> T xml2Entity(String xml, Class<T> clazz) {
        try {

            return JsonMapper.jsonToEntity(xml2Json(xml).toString(), clazz);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static JSONObject xml2Json(String xml) {

        try {
            return XML.toJSONObject(xml);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    /*public static String xml2String(Document document)
    {
        OutputFormat format = OutputFormat.createCompactFormat(); //createPrettyPrint() 层次格式化
        StringWriter writer = new StringWriter();
        XMLWriter output = new XMLWriter(writer, format);
        try {
            output.write(document);
            writer.close();
            output.close();
           return  writer.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }*/
}
