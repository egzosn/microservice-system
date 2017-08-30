package com.moredo.common.utils.file;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

public class FileUtil {

    //日志
    private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);

    /**
     * 将文件转成base64 字符串
     *
     * @param path 文件路径
     * @return *
     * @throws Exception
     */
    public static String fileToBase64(String path) throws Exception {
        FileInputStream inputFile = null;
        try {
            File file = new File(path);
            inputFile = new FileInputStream(file);
            byte[] buffer = new byte[(int) file.length()];
            inputFile.read(buffer);
            return new BASE64Encoder().encode(buffer);
        } catch (Exception e) {
            logger.error("文件转base64异常：", e);
        } finally {
            if (inputFile != null) {
                inputFile.close();
            }
        }
        return null;
    }

    /**
     * 将base64字符解码保存文件
     *
     * @param base64Code
     * @param targetPath
     * @throws Exception
     */

    public static void base64ToFile(String base64Code, String targetPath)
            throws Exception {
        FileOutputStream out = null;
        try {
            byte[] buffer = new BASE64Decoder().decodeBuffer(base64Code);
            out = new FileOutputStream(targetPath);
            out.write(buffer);
            out.close();
        } catch (Exception e) {
            logger.error("base64转文件异常：", e);
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    /**
     * 得到上传文件的文件头
     *
     * @param src
     * @return
     */
    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    /**
     * 根据制定文件的文件头判断其文件类型
     *
     * @param filePath
     * @return
     */
    public static String getFileType(String filePath) {
        FileInputStream is = null;
        try {
            //校验文件是否存在
            File file = new File(filePath);
            if (!file.exists()) {
                return null;
            }
            //读取文件内容
            is = new FileInputStream(filePath);
            byte[] b = new byte[10];
            is.read(b, 0, b.length);
            //获取文件头
            return bytesToHexString(b);
        } catch (Exception e) {
            logger.error("读取文件头异常：", e);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    logger.error("关闭文件IO流异常：", e);
                }
            }
        }
        return null;
    }

    /**
     * 下载网络文件
     *
     * @param uri 文件链接地址
     * @return
     */
    public static byte[] download(String uri) {
        int byteRead = 0;
        InputStream inputStream = null;
        ByteArrayOutputStream output = null;
        try {
            URL url = new URL(uri);
            URLConnection conn = url.openConnection();
            inputStream = conn.getInputStream();
            // 把输入流转换成字节数组
            output = new ByteArrayOutputStream();
            byte[] buffer = new byte[1204];
            while ((byteRead = inputStream.read(buffer)) != -1) {
                output.write(buffer, 0, byteRead);
            }
            return output.toByteArray();
        } catch (Exception e) {
            logger.error("网络文件下载异常：", e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * 创建文件
     * @param path  文件路径
     * @param content   文件内容
     * @return  是否创建成功，成功则返回true
     */
    public static boolean createFile(String path, String content){
        boolean bool = false;
        File file = new File(path);
        try {
            //如果文件不存在，则创建新的文件
            if(!file.exists()){
                file.createNewFile();
                bool = true;
                //创建文件成功后，写入内容到文件里
                writeFileContent(path, content);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bool;
    }

    /**
     * 向文件中写入内容
     * @param filepath 文件路径与名称
     * @param content  写入的内容
     * @return
     * @throws IOException
     */
    public static boolean writeFileContent(String filepath, String content) throws IOException{
        Boolean bool = false;
        String temp  = "";
        FileInputStream fis = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        FileOutputStream fos  = null;
        PrintWriter pw = null;
        try {
            File file = new File(filepath);//文件路径(包括文件名称)
            //将文件读入输入流
            fis = new FileInputStream(file);
            isr = new InputStreamReader(fis);
            br = new BufferedReader(isr);
            StringBuffer buffer = new StringBuffer();
            //文件原有内容
            for(int i=0;(temp =br.readLine())!=null;i++){
                buffer.append(temp);
                // 行与行之间的分隔符 相当于“\n”
                buffer = buffer.append(System.getProperty("line.separator"));
            }
            buffer.append(content);
            fos = new FileOutputStream(file);
            pw = new PrintWriter(fos);
            pw.write(buffer.toString().toCharArray());
            pw.flush();
            bool = true;
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            //不要忘记关闭
            if (pw != null) {
                pw.close();
            }
            if (fos != null) {
                fos.close();
            }
            if (br != null) {
                br.close();
            }
            if (isr != null) {
                isr.close();
            }
            if (fis != null) {
                fis.close();
            }
        }
        return bool;
    }

}