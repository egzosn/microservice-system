package com.moredo.common.utils.excel;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by ZaoSheng on 2016/3/15.
 */
public enum ExcelVersion {
    xls("EXCEL_2003") {
        @Override
        public Workbook create(InputStream workbook) throws IOException {
            if (null == workbook){
                return new HSSFWorkbook();
            }
            return new HSSFWorkbook(workbook);
        }

    },xlsx("EXCEL_2007") {
        @Override
        public Workbook create(InputStream workbook) throws IOException {
            if (null == workbook){
                return new XSSFWorkbook();
            }
            return new XSSFWorkbook(workbook);
        }
    }, ;
    public String version;

    ExcelVersion(String version) {
        this.version = version;
    }
    public String getVersion() {
        return version;
    }

    /**
     * 根据文件流创建对应的文档
     * @param stream
     * @return
     * @throws java.io.IOException
     */
    public abstract Workbook create(InputStream stream) throws IOException;

    /**
     * 创建一个空文档
     * @return
     * @throws java.io.IOException
     */
    public Workbook create() throws IOException{
        return create(null);
    }


}
