package com.moredo.common.validator.constraintvalidators;

import com.moredo.common.validator.constraints.SingleFileUploadValidate;
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
 *         文件合法验证
 */
public class SingleFileUploadValidateImpl implements ConstraintValidator<SingleFileUploadValidate, MultipartFile> {

    private static final Logger LOG = LoggerFactory.getLogger(SingleFileUploadValidateImpl.class);

    private final static Map<String, Object> map = new HashMap<String, Object>();
    private final static String configPath = "upload-config.properties";
    private static String _contentTypeKey = "_allow_content_type";
    private static String _sizeKey = "_allow_size_kb";

    private long size;
    private String[] contentTypes;
    private String useConfig;
    private boolean allowEmpty;

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
                }
            }
        } catch (ConfigurationException e) {
            LOG.debug(e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SingleFileUploadValidateImpl p = new SingleFileUploadValidateImpl();

    }

    @Override
    public void initialize(SingleFileUploadValidate uploadFileValidate) {
        this.allowEmpty = uploadFileValidate.allowEmpty();
        this.size = uploadFileValidate.size();
        this.contentTypes = uploadFileValidate.contentTypes();
        this.useConfig = uploadFileValidate.useConfig();
        if (!(null == contentTypes || "".equals(contentTypes)) && useConfig != null && !"".equals(useConfig.trim())) {
            String[] _contentTypes = (String[]) map.get(useConfig + _contentTypeKey);
            String sizeStr = (String) map.get(useConfig + _sizeKey);
            if (_contentTypes != null) {
                contentTypes = _contentTypes;
            }
            if (sizeStr != null) {
                size = Long.valueOf(sizeStr);
            }
        }
        this.size = size * 1024;
    }

    @Override
    public boolean isValid(MultipartFile multipartFile, ConstraintValidatorContext constraintValidatorContext) {
        if (multipartFile == null || multipartFile.getSize() == 0) {
            return allowEmpty;
        }
        return (allowSize(multipartFile) && allowContentType(multipartFile));
       /* if (size == 0 || multipartFile.getSize() <= size) {
            if (contentTypes.length > 0) {
                for (int i = 0; i < contentTypes.length; i++) {
                    if (contentTypes[i].equals(multipartFile.getContentType())) {
                        return true;
                    }
                }
                return false;
            }
            return true;
        }*/
    }

    private boolean allowSize(MultipartFile multipartFile) {

        return (size == 0 || multipartFile.getSize() <= size);
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
