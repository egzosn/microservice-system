package com.moredo.common.validator.constraintvalidators;

import com.moredo.common.validator.constraints.MultipartFilesUploadValidate;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by LinJie on 2014/10/22.
 *
 * @author Linjie
 *         多文件法验证
 */
public class MultipartFilesUploadValidateImpl implements ConstraintValidator<MultipartFilesUploadValidate, MultipartFile[]> {

    private static final Logger LOG = LoggerFactory.getLogger(MultipartFilesUploadValidateImpl.class);

    private final static Map<String, Object> map = new HashMap<String, Object>();
    private final static String configPath = "upload-config.properties";
    private static String _contentTypeKey = "_allow_content_type";
    private static String _sizeKey = "_allow_single_size_kb";
    private static String _totalSizeKey = "_allow_total_size_kb";
    private boolean allowSingleEmpty;
    private long singleSize;
    private String[] contentTypes;
    private String useConfig;
    private boolean allowEmpty;
    private long totalSize;

    static {
        try {
            Configuration config = new PropertiesConfiguration(configPath);
            Iterator<String> keys = config.getKeys();
            String key = null;
            while (keys.hasNext()) {
                key = keys.next();
                if (key.endsWith(_contentTypeKey)) {
                    map.put(key, config.getStringArray(key));
                } else if (key.endsWith(_sizeKey)) {
                    map.put(key, config.getString(key));
                } else if (key.endsWith(_totalSizeKey)) {
                    map.put(key, config.getString(key));
                }
            }
        } catch (ConfigurationException e) {
            LOG.debug(e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        MultipartFilesUploadValidateImpl p = new MultipartFilesUploadValidateImpl();
    }

    @Override
    public void initialize(MultipartFilesUploadValidate uploadFileValidate) {
        this.allowEmpty = uploadFileValidate.allowEmpty();
        this.allowSingleEmpty = uploadFileValidate.allowSingleEmpty();
        this.singleSize = uploadFileValidate.singleSize();
        this.totalSize = uploadFileValidate.totalSize();
        this.contentTypes = uploadFileValidate.contentTypes();
        this.useConfig = uploadFileValidate.useConfig();
        if (useConfig != null && !"".equals(useConfig.trim())) {
            String[] _contentTypes = (String[]) map.get(useConfig + _contentTypeKey);
            String sizeStr = (String) map.get(useConfig + _sizeKey);
            String totalSizeStr = (String) map.get(useConfig + _totalSizeKey);
            if (_contentTypes != null) {
                contentTypes = _contentTypes;
            }
            if (sizeStr != null) {
                singleSize = Long.valueOf(sizeStr);
            }
            if (totalSizeStr != null) {
                this.totalSize = Long.valueOf(totalSizeStr);
            }
        }
        this.singleSize = singleSize * 1024;
        this.totalSize = totalSize * 1024;
    }

    @Override
    public boolean isValid(MultipartFile[] multipartFiles, ConstraintValidatorContext constraintValidatorContext) {
        long _totalSize = 0l;
        if (multipartFiles == null || multipartFiles.length <= 0) {
            return allowEmpty;
        }
        boolean result = false;
        for (MultipartFile multipartFile : multipartFiles) {
            result = (allowSize(multipartFile) && allowContentType(multipartFile));
            if (!result) {
                return false;
            }
            _totalSize += multipartFile.getSize();
        }
        /*
          totalSize = 0 ,则不限制
          如果超出大小，则限制
         */
        if (totalSize == 0 || _totalSize < totalSize) {
            return true;
        }
        return false;
    }

    private boolean allowSize(MultipartFile multipartFile) {
        if (multipartFile.getSize() <= 0) {
            if (allowSingleEmpty)
                return true;
            return false;
        }
        return (singleSize == 0 || multipartFile.getSize() <= singleSize);
    }

    private boolean allowContentType(MultipartFile multipartFile) {
        // 不限制
        if (contentTypes.length == 0)
            return true;
        for (int i = 0; i < contentTypes.length; i++) {
            if (contentTypes[i].equals(multipartFile.getContentType())) {
                return true;
            }
        }
        return false;
    }


}
