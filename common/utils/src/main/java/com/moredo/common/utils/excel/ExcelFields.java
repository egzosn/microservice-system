package com.moredo.common.utils.excel;

/**
 * Created by ZaoSheng on 2016/1/12.
 */
public class ExcelFields {
    private String fieldName;
    private String field;

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

	public ExcelFields() {
		super();
	}

	public ExcelFields(String fieldName, String field) {
		super();
		this.fieldName = fieldName;
		this.field = field;
	}
    
}