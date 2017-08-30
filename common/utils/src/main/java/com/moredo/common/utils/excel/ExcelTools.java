package com.moredo.common.utils.excel;


import com.moredo.common.utils.log.Log4jUtil;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import java.beans.Statement;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * excel解析工具
 * Created by ZaoSheng on 2016/3/15.
 */
public class ExcelTools<T> {

    //缓存   针对excle行转具体实体，用于存储字段名与对应的类型
    public static final Map<Class, Map<String, Field>> clazzs = new HashMap<>();
    //当前类的
    protected Class<T> clazz = null;
    //excel的列对应
    protected Map<Integer, String> propertys = null;
    //针对excle行转具体实体，用于存储字段名与对应的类型
    protected Map<String, Field> fields = null;
    //针对excle行转具体实体，用于存储字段名与对应的类型
    protected Map<String, Class<?>> fieldsClass = null;

    //需要解析的文档
    protected Workbook workbook = null;
    //失败的文档
    private Workbook failureWork = null;
    //是否失败的文档
    private boolean isSaveFailure = false;
    //失败文档对应的页
    private  Sheet failureSheet = null;
    //是否导入失败的文档模版
    protected boolean isFailureTemplate = false;
    //失败文件
    private String failedFileName = null;
    //从第几行开始读取
    private int readRow = 1;
    //写入行
    protected int writeRow = 0;
    //参数行
    private int propertysRow = 0;
    //存储exce版本类型
    private ExcelVersion excelVersion;


//    private Map<Integer, >

    /**
     * 构造初始化
     */
    public ExcelTools() {
        java.lang.reflect.Type genType = this.getClass().getGenericSuperclass();
        if (!(genType instanceof ParameterizedType)) {
            clazz = (Class<T>) Object.class;
        } else {
            java.lang.reflect.Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
            try {
                clazz = (Class<T>) params[0];
            } catch (Exception e) {
                try {
                    clazz = (Class<T>) ((ParameterizedType) params[0]).getRawType();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        }

        fields = clazzs.get(clazz);
        if (null == fields && !Map.class.isAssignableFrom(clazz)) {
            fields = new HashMap<String, Field>();
            clazzs.put(clazz, fields);
        }
        fieldsClass = new HashMap<>();
    }

    /**
     *  设置失败文档的模版
     * @param failureWork 失败文档
     */
    public void setFailureWorkByTemplate(Workbook failureWork) {
        isFailureTemplate = true;
        this.failureWork = failureWork;
    }

    /**
     *  设置失败文档的模版
     * @param path 失败文件路径
     */
    public void setFailureWorkByTemplate(String path) {
        setFailureWorkByTemplate(new File(path));
    }

    /**
     *  设置失败文档的模版
     * @param failureWork 失败文件
     */
    public void setFailureWorkByTemplate(File failureWork) {
        String extension = FilenameUtils.getExtension(failureWork.getName());
        ExcelVersion excelVersion = ExcelVersion.valueOf(extension);
        try {
            setFailureWorkByTemplate(excelVersion.create(new FileInputStream(failureWork)));
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    /**
     * 读取文件
     * @param workbook 文档对应的文件流
     * @param suffix 后缀名
     */
    public void read(InputStream workbook, String suffix) {
        excelVersion = ExcelVersion.valueOf(suffix);
        try {
            if (null ==getFailureWork()){
                 createFailureWork(excelVersion.create());
            }

            readExcel(excelVersion.create(workbook));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
        }finally {
            if (null != workbook){
                try {
                    workbook.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    protected void parseFailureException() throws IllegalAccessException {
        throw  new IllegalAccessException("解析失败，你是否用的最新模版？");
    }

    /**
     * 读取文档
     * @param workbook 文档
     */
    public void readExcel(Workbook workbook) throws ReflectiveOperationException {
        this.workbook = workbook;
        int count = workbook.getNumberOfSheets();
        if (count > 0) {
            // Read the Sheet
            for (int numSheet = 0; numSheet < count; numSheet++) {
                Sheet sheet = workbook.getSheetAt(numSheet);
                if (sheet == null) {
                    continue;
                }

                int lastRowNum = sheet.getLastRowNum();
                //read first line
                if (lastRowNum < 0) {
                   throw new IllegalAccessException("空文档");
                }

                if (null == propertys || propertys.isEmpty()){
                    setPproperty(sheet);
                }

                if (propertys.isEmpty()) {
                    parseFailureException();
                }

                befor(sheet);
                //开始解析
                parsing(sheet, lastRowNum);
                after(sheet);
                //暂时只读取非空表
                break;
            }
        }

    }

    /**
     * 文档
     * @param sheet
     * @param rowCount
     * @throws ReflectiveOperationException
     */
    private void parsing(Sheet sheet, int rowCount) throws ReflectiveOperationException {
        //是否发生异常 false无异常
        boolean isException = false;
        T before = null;
        for (int rowNum = readRow; rowNum <= rowCount; rowNum++) {
            //用于存放异常列
            List<String> exceptionField = new ArrayList<String>();
            Row row = sheet.getRow(rowNum);
            if (row != null) {
                T bean = clazz.newInstance();
                try {
                    rowBefor(sheet, rowNum);
                    short lastNum = row.getLastCellNum();
                    if (lastNum < 0) continue;

                    if (Map.class.isAssignableFrom(clazz)) {
                        put(bean, row, exceptionField);
                    } else {
                        setAttr(bean, row, exceptionField);
                    }
                    rowAfter(sheet, rowNum);
                    toBean(bean);
                    toBean(bean, before);
                    toBean(bean, exceptionField);
                    rowAfter();
                    before = bean;
                } catch (Exception e) {
                    Log4jUtil.error(String.format("读取%s行失败", rowNum + 1), e);
                    //这里回调
                    rowException(sheet, row);
                    if (isSaveFailure) {
                        isException = true;
                        if (e instanceof ExcelException) {
                            ExcelException excelException = (ExcelException) e;
                            writeRow(writeRow++, row, excelException.getCellComm());
                        } else {
                            writeRow(writeRow++, row);
                        }
                    }

                }
            }

        }
        if (isException) {
            writeRow = 0;
            throwException(failureWork);
        }
    }



    /**
     * 设置参数
     * @param propertys 参数
     */
    public void setPproperty(Map<Integer, String> propertys) {
        this.propertys = propertys;
    }

    /**
     * 根据表获取对应参数行设置
     * @param sheet 表
     * @throws ReflectiveOperationException
     */
    private void setPproperty(Sheet sheet) throws ReflectiveOperationException {
        Row row = null;
        if (isSaveFailure) {
            writeRow = getReadRow();
            //是否传入模版， true 自定义模版
            if (!isFailureTemplate) {
                //创建初始化失败的行
                for (int i = 0; i < getReadRow(); i++) {
                    Row failureRow = getFailureSheet().createRow(i);
                    row = sheet.getRow(i);
                    failureRow.setZeroHeight(row.getZeroHeight());
                    for (short start = 0; start <= row.getLastCellNum(); start++) {
                        Cell cell = row.getCell(start);
                        if (null != cell) {
                            Object value = getValue(cell);
                            if (null == value) {
                                continue;
                            }
                            failureRow.createCell(start).setCellValue(getValue(cell).toString());
                        }
                    }
                }
            }
        }
        if (null == propertys) {
            propertys = new HashMap<>();
        } else {
            propertys.clear();
        }
        row = sheet.getRow(getPropertysRow());
        for (short start = 0; start <= row.getLastCellNum(); start++) {
            Cell cell = row.getCell(start);
            if (null == cell) {
                continue;
            }
            Object value = getValue(cell);
            String p = conversionAttr(value);
            if (null != p) {
                propertys.put((int) start, p);
                if (Map.class.isAssignableFrom(clazz) || Object.class == clazz) {
                    fieldsClass.put(p, Object.class);
                } else {
                    fieldsClass.put(p, getFieldType(p));
                }
            }
        }
    }

    public Map<Integer, String> getPropertys() {
        return propertys;
    }

    protected void setAttr(T bean, Row row, List<String> exceptionField) throws Exception {
        for (Integer index : propertys.keySet()) {
            try {
                Cell cell = row.getCell(index);
                if (null == cell) continue;
                String p = propertys.get(index);
                Field field = fields.get(p);
                field.setAccessible(true);
                field.set(bean, conversionVal(getValue(cell), fieldsClass.get(p)));
                field.setAccessible(false);

                //这里暂时抛弃
//            PropertyDescriptorUtil.setProperty(bean, p, conversionVal(getValue(cell), fieldsClass.get(p)));
            } catch (Exception e) {
                if (null != exceptionField) {
                    exceptionField.add(propertys.get(index));
                    throw new ExcelException(1001011, exceptionField);
                }
            }
        }
    }

    /**
     *  Map子类 进行put
     * @param map Map具体的实现类，可实例化
     * @param row 行
     * @throws Exception
     */
    private void put(T map, Row row, List<String> exceptionField) throws Exception {
        for (Integer index : propertys.keySet()) {
            try {
                Cell cell = row.getCell(index);
                if (null == cell) continue;
                String p = propertys.get(index);
                Object value = conversionVal(getValue(cell), fieldsClass.get(p));
                if (null == value || "".equals(value)) {
                } else {
                    new Statement(map, "put", new Object[]{p, value}).execute();
                }
            }catch (Exception e){
                if (null != exceptionField){
                    exceptionField.add(propertys.get(index));
                     throw new ExcelException(1001011, exceptionField);
                }
            }

        }
    }

    /**
     *  行异常的时候执行
     * @param sheet 表
     * @param row 行
     */
    protected void rowException(Sheet sheet, Row row) {
    }

    /**
     * 对象属性值类型转化转化
     * @param value 对应列的值
     * @param type 列对应的类型
     * @return
     */
    protected Object conversionVal(Object value, Class<?> type) {
        return value;
    }


    /**
     * 获取字段的类型,这里主要针对于简单类型对象（对象里面都是简单类型，而非复杂对象）
     * @param fieldName 对象字段名字
     * @return
     * @throws NoSuchFieldException
     */
    protected Class getFieldType(String fieldName) throws NoSuchFieldException {
        Field field = fields.get(fieldName);
        if (null == field){
            field= clazz.getDeclaredField(fieldName);
            fields.put(fieldName, field);
        }
        return field.getType();
    }


    /**
     * 创建失败的文档
     */
    private void createFailureWork(Workbook failureWork) {
        if (isSaveFailure) {
            //这里创建初始化失败WorkBook
            this.failureWork = failureWork;
            failureSheet = this.failureWork.createSheet();
        }
    }

    /**
     * 提取参数行的值进行转化对象属性
     * @param value 值
     * @return
     */
    protected String conversionAttr(Object value){
        return null == value ? null : value.toString();
    }


    /**
     *   根据对应列获得对应类型的值
     */
    protected Object getValue(Cell hssfCell) {
        switch (hssfCell.getCellType()) {
            case Cell.CELL_TYPE_BOOLEAN:
                return hssfCell.getBooleanCellValue();
            case Cell.CELL_TYPE_NUMERIC:
                return hssfCell.getNumericCellValue();
            case Cell.CELL_TYPE_BLANK:
                return hssfCell.getDateCellValue();
            default:
                return hssfCell.getStringCellValue();
        }
    }

    protected Sheet getFailureSheet() throws IllegalAccessException {
        if (null == failureSheet) {
            if (null == failureWork) {
                throw new IllegalAccessException("未创建失败文档");
            }
            failureSheet = failureWork.getSheetAt(0);
        }

        return failureSheet;
    }


    /**
     * 读取文档表前执行
     * @param sheet 文档表
     */
    protected void befor(Sheet sheet) {
        //这里回调
    }

    /**
     * 读取文档表后执行
     * @param sheet 文档表
     */
    protected void after(Sheet sheet) {
        //这里回调
    }

    /**
     * 读取到的每行记录之前
     * @param sheet  表
     * @param rowNum 第几行
     * @throws Exception
     */
    protected void rowBefor(Sheet sheet, int rowNum) {
        //这里回调
    }

    /**
     * 读取到的每行记录之后
     * @param sheet  表
     * @param rowNum 第几行
     * @throws Exception
     */
    protected void rowAfter(Sheet sheet, int rowNum) throws Exception {
        //这里回调
    }

    /**
     * 将对应的行记录注入到对应的实体内，简单类型对象。
     * @param  bean 对应的实体对象，可以是自定义对象，Map具体的实现类，可实例化
     * @throws Exception
     */
    protected  void toBean(final T bean) throws Exception{  }
    /**
     * 将对应的行记录注入到对应的实体内，简单类型对象。
     * @param  bean 对应的实体对象，可以是自定义对象，Map具体的实现类，可实例化
     * @throws Exception
     */
    public  void toBean(final T bean, T before) throws Exception{  }
    /**
     * 将对应的行记录注入到对应的实体内，简单类型对象。
     * @param  bean 对应的实体对象，可以是自定义对象，Map具体的实现类，可实例化
     * @throws Exception
     */
    protected  void toBean(final T bean, List<String> exceptionField) throws Exception{  }
    /**
     * 读取到的每行记录之后
     */
    protected void rowAfter() {
    }

    /**
     * generate the Excel file
     *
     * @param fieldses the is of the Excel Template Object
     * @return
     * @throws java.io.IOException
     * @throws ReflectiveOperationException
     */
    public void genExcel(List<ExcelFields> fieldses, ExcelVersion version, String sheetName) {

        if (null != version) {
            propertys = new HashMap<>();
            try {
                this.excelVersion = version;
                generate(version.create(), fieldses, sheetName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


    protected void generate(Workbook workbook, List<ExcelFields> fields, String sheetName) {
        this.workbook = workbook;
        this.failureSheet = workbook.createSheet("".equals(sheetName) ? "sheet1" : sheetName);
        Row row = this.failureSheet.createRow(0);
        for (int i = 0; i < fields.size(); i++) {
            ExcelFields field = fields.get(i);
            propertys.put(i, field.getField());
            String value = field.getFieldName();
            if (null == value) {
                value = "";
            }
            row.createCell(i).setCellValue(value.toString());
        }
    }


    /**
     *  设置导出文档的模版
     * @param workbook 导出文档
     */
    public void genExcel(Workbook workbook) {
        this.workbook = workbook;
        this.failureSheet = workbook.getSheetAt(0);
        if (null == propertys || propertys.isEmpty()) {
            try {
                isFailureTemplate =  true;
                isSaveFailure = true;
                setPproperty(failureSheet);
            } catch (ReflectiveOperationException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     *  设置导出文档的模版
     * @param path 导出文档
     */
    public void genExcel(String path) {
        genExcel(new File(path));
    }
    /**
     *  设置导出文档的模版
     * @param work 导出文档
     */
    public void genExcel(File work) {
        String extension = FilenameUtils.getExtension(work.getName());
        ExcelVersion excelVersion = ExcelVersion.valueOf(extension);
        try {
            genExcel(excelVersion.create(new FileInputStream(work)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 自定义写入文档
     * @param attr 文档对应的属性
     */
    public void writeRow(Map<String, Object> attr) throws IllegalAccessException {
        Row row = getFailureSheet().createRow(getWriteRow());
        for (Integer p : propertys.keySet()) {
            Object value = attr.get(propertys.get(p));
            StringBuffer text =  new StringBuffer();
            if (null == value) {
                text.append("");
            }else if(value.getClass().isArray()){
                Object[] vs = (Object[]) value;
                for (Object v :vs){
                    if (null == v){
                        v = "";
                    }
                    text.append(v.toString()).append(";\n");
                }
            }else{
                text.append(value.toString());
            }
            row.createCell(p).setCellValue(text.toString());
        }
    }

    protected void writeRow(int rowNum, Row row, List<String> exceptionComm) throws IllegalAccessException {
        Row failureRow = getFailureSheet().createRow(rowNum);
        for (Integer p : propertys.keySet())
            if (null != row.getCell(p)) {
                Object value = getValue(row.getCell(p));
                if (null == value) {
                    continue;
                }
    /*            Cell cell = failureRow.createCell(p);
                Comment comment = cell.getCellComment();
                comment.setAuthor("异常");
                cell.setCellComment(comment);
                cell.setCellValue(value.toString());*/
                failureRow.createCell(p).setCellValue(value.toString());
            }
    }
    protected void writeRow(int rowNum, Row row) throws IllegalAccessException {
        Row failureRow = getFailureSheet().createRow(rowNum);
        for (Integer p : propertys.keySet()) {
            if (null != row.getCell(p)) {
                Object value = getValue(row.getCell(p));
                if (null == value) {
                    continue;
                }
                failureRow.createCell(p).setCellValue(value.toString());
            }

        }
    }

    /**
     * 对失败的文档进行处理
     * @param failureWork 失败的文档
     */
    protected void throwException(Workbook failureWork) {
        //这里回调
    }

    /**
     * 删除对应的行，这里不建议使用
     * @param sheet 表
     * @param rowNum 行号
     */
    public void removeRow(Sheet sheet, int rowNum) {
        /*try{

			sheet.shiftRows(rowNum+1, sheet.getLastRowNum(), -1);
		}catch(RuntimeException e){
			sheet.removeRow(sheet.getRow(rowNum));
		}*/
        sheet.removeRow(sheet.getRow(rowNum));

    }




    public Workbook getFailureWork() {
        return failureWork;
    }

    public void setFailureWork(Workbook failureWork) {
        this.failureWork = failureWork;
    }

    public boolean isSaveFailure() {
        return isSaveFailure;
    }

    public void setSaveFailure(boolean isSaveFailure) {
        this.isSaveFailure = isSaveFailure;
    }

    public int getReadRow() {
        return readRow;
    }

    public void setReadRow(int readRow) {
        this.readRow = readRow;
    }

    public int getWriteRow() {
        return writeRow;
    }

    public void setWriteRow(int writeRow) {
        this.writeRow = writeRow;
    }

    public int getPropertysRow() {
        return propertysRow;
    }

    public void setPropertysRow(int propertysRow) {
        this.propertysRow = propertysRow;
    }

    public String getFailedFileName() {
        return failedFileName;
    }

    public void setFailedFileName(String failedFileName) {
        this.failedFileName = failedFileName;
    }

    public ExcelVersion getExcelVersion() {
        return excelVersion;
    }

    public Workbook getWorkbook() {
        return workbook;
    }

    public void setWorkbook(Workbook workbook) {
        this.workbook = workbook;
    }
}