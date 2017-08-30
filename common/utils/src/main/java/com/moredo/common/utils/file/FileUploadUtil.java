package com.moredo.common.utils.file;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Created by LinJie on 2014/10/20.
 *
 * @author Linjie
 */
public class FileUploadUtil {

    private static String rename() {
        Long now = Long.parseLong(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
        String filename = now + UUID.randomUUID().toString();
        return filename;
    }

    private static void _uploadFileOfStream(InputStream is, String dir, String filename) {
        try {
            FileUtils.copyInputStreamToFile(is, new File(dir + "/" + filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String saveStreamToFileAndRename(InputStream is, String dir, String filename) {
        String _filename = rename() + "."  + FilenameUtils.getExtension(filename);
        _uploadFileOfStream(is, dir, _filename);
        return _filename;
    }

    public static String saveStreamToFile(InputStream is, String dir, String filename, String exname) {
        filename = filename + "." + exname;
        _uploadFileOfStream(is, dir, filename);
        return filename;
    }

    /**
     * 删除文件
     * @param saveFolder
     * @param fileName
     */
    public static void deleteFiles(String saveFolder, String fileName) {
        try {
            File file = new File(saveFolder, fileName);
            if (file.exists()) {
                file.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
       /**
     * @param saveFolder
     * @param file
     * @param fileName
     * @return
     */
    public static String uploadFile(String saveFolder, InputStream file, String fileName) {

        if (file == null) {
            return "";
        }

        String ext = "." + FilenameUtils.getExtension(fileName);
        String newFileName = System.currentTimeMillis() + ext;

        File iconUploadFolder = new File(saveFolder);

        File iconFile = new File(iconUploadFolder, newFileName);
        try {
            FileUtils.copyInputStreamToFile(file, iconFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return newFileName;

    }

    public static String getFilePathUri(String folder, String fileName) {
        return folder + "/" + fileName;
    }

}
