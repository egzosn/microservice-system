package com.moredo.common.utils.file;

import java.io.*;
import java.util.List;
import java.util.zip.CRC32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * zip文件打包与解压
 *
 *  2016/8/26 15:08 author: egan
 */
public class ZipUtil {
    
    /**
     * 
     * 功能:对给定的文件进行压缩
     * @param filePathList 文件路径集合
     * @param fileNameList 文件名集合（该参数可以为空）
     * @param zipPath
     *            输出的压缩文件路径
     * @throws Exception
     */
    public void Zip(String zipPath,List filePathList,List fileNameList) throws  Exception {
        // 如果传入的路径为空不能执行压缩操作
        if (zipPath == null || "".equals(zipPath)){
            throw new Exception("The ZipFile path cant not null ");
        }
        // 如果没有指定要压缩的文件，不能执行压缩操作
        if (filePathList == null || filePathList.size() == 0){
            throw new Exception("Have no file to Zip");
        }
        if(fileNameList!=null && filePathList.size()!=fileNameList.size()){
            throw new Exception("fileNum is not equares PathNum");
        }
        // 根据指定路径获得压缩文件输出流
        FileOutputStream fos = new FileOutputStream(zipPath);
        CheckedOutputStream ch = new CheckedOutputStream(fos, new CRC32());
        ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(ch)); 
        for(int i=0;i<filePathList.size();i++){
            String path=filePathList.get(i).toString();   
            String fileName = path;
            if(fileNameList!=null && fileNameList.size()>0){
                fileName=fileNameList.get(i).toString();
            }
            // 根据路径获得缓冲流
            BufferedReader in = new BufferedReader(new InputStreamReader( new FileInputStream(path), "ISO8859_1"));                
            // 对输出流添加文件
            out.putNextEntry(new ZipEntry(fileName));          
            // 写入压缩文件
            int c;
            while ((c = in.read()) != -1){
                out.write(c);
            }
            in.close();
        }      
        out.close();
    }
}

