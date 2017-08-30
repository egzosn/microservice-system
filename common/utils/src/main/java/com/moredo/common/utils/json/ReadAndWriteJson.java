/**
 * 
 */
package com.moredo.common.utils.json;

/**
 * @author ZaoSheng
 *
 */

import com.alibaba.fastjson.JSONObject;

import java.io.*;

public class ReadAndWriteJson {  
  
    /** 
     * @param args 
     * @throws IOException
     */  
    public static void main(String[] args) throws IOException  {  
        // TODO Auto-generated method stub  
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("money", 15079);  
        jsonObject.put("transaction", 528);  
        writeFile("orderNum.json", jsonObject.toString());  
    }  
  
    public static void writeFile(String filePath, String sets) throws IOException {  
        FileWriter fw = new FileWriter(filePath);  
        PrintWriter out = new PrintWriter(fw);  
        out.write(sets);  
        out.println();  
        fw.close();  
        out.close();  
    }  
  
    public static String ReadFile(String path) {
        try {
            return ReadFile(new FileInputStream(path));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String ReadFile(InputStream file) {

        BufferedReader reader = null;
        StringBuffer laststr = new StringBuffer();
        try {
            reader = new BufferedReader(new InputStreamReader(file, "UTF-8"));
            String tempString = null;
            while ((tempString = reader.readLine()) != null) {
                laststr.append(tempString);
            }
        } catch (IOException e) {
          //  e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        return laststr.toString();
    }
}  