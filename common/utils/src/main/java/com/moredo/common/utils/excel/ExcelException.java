package com.moredo.common.utils.excel;

import java.util.List;

/**
 * Created by ZaoSheng on 2016/3/18.
 */
public class ExcelException extends RuntimeException {
    private Integer code;
    private List<String> cellComm;
    private Short[] cellNum;

    public ExcelException(Integer code, List<String> cellComm) {
        this.code = code;
        this.cellComm = cellComm;
    }

    public ExcelException(Integer code, Short[] cellNum) {
        this.code = code;
        this.cellNum = cellNum;
    }

    public ExcelException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public List<String> getCellComm() {
        return cellComm;
    }

    public Short[] getCellNum() {
        return cellNum;
    }
}
