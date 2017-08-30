/*
 * Copyright 2002-2017 the original moredo or egan.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package com.moredo.common.utils.sign;


import java.util.*;

/**
 * 签名
 *
 * @author: egan
 * @email egzosn@gmail.com
 * @date 2016/11/9 17:45
 */
public enum SignUtils {

    MD5 {
        /**
         *
         * @param content           需要签名的内容
         * @param key               密钥
         * @param characterEncoding 字符编码
         * @return
         */
        @Override
        public String createSign(String content, String key, String characterEncoding) {

            return com.moredo.common.utils.sign.encrypt.MD5.sign(content, key, characterEncoding);
        }

        /**
         * 签名字符串
         * @param text 需要签名的字符串
         * @param sign 签名结果
         * @param key 密钥
         * @param characterEncoding 编码格式
         * @return 签名结果
         */
        public boolean verify(String text, String sign, String key, String characterEncoding) {
            return com.moredo.common.utils.sign.encrypt.MD5.verify(text, sign, key, characterEncoding);
        }
    },

    RSA {
        @Override
        public String createSign(String content, String key, String characterEncoding) {
            return com.moredo.common.utils.sign.encrypt.RSA.sign(content, key, characterEncoding);
        }

        @Override
        public boolean verify(String text, String sign, String publicKey, String characterEncoding) {
            return com.moredo.common.utils.sign.encrypt.RSA.verify(text, sign, publicKey, characterEncoding);
        }
    };

    /**
     * 根据参数进行排序拼接为字符串
     *
     * @param parameters
     * @return
     */
    public String parameterText(Map parameters) {
        if(parameters == null){
            return "";
        }
        StringBuffer sb = new StringBuffer();

        // TODO 2016/11/11 10:14 author: egan 已经排序好处理
        if (parameters instanceof SortedMap) {
            for (String k : ((Set<String>) parameters.keySet())) {
                Object v = parameters.get(k);
                if (null != v && !"".equals(v) && !"sign".equals(k) && !"keyStr".equals(k) && !"appId".equals(k)) {
                    sb.append(k + "=" + v + "&");
                }
            }
            if (sb.length() > 0) {
                sb.deleteCharAt(sb.length() - 1);
            }
            return sb.toString();
        }


        // TODO 2016/11/11 10:14 author: egan 未排序须处理
        List<String> keys = new ArrayList<String>(parameters.keySet());
        //排序
        Collections.sort(keys);
        for (String k : keys) {
            String valueStr = "";
            Object o = parameters.get(k);
            if (o instanceof String[]) {
                String[] values = (String[]) parameters.get(k);
                for (int i = 0; i < values.length; i++) {
                    valueStr = (i == values.length - 1) ? valueStr + values[i]
                            : valueStr + values[i] + ",";
                }
            } else if (o != null) {
                valueStr = o.toString();
            }
            if (null != valueStr && !"".equals(valueStr) && !"sign".equals(k) && !"keyStr".equals(k) && !"appId".equals(k)) {
                sb.append(k + "=" + valueStr + "&");
            }
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

    /**
     * 签名
     *
     * @param parameters 需要进行排序签名的参数
     * @return
     */
    public String sign(Map parameters, String key, String characterEncoding) {

        return createSign(parameterText(parameters), key, characterEncoding);

    }

    /**
     * 签名
     *
     * @param content           需要签名的内容
     * @param key               密钥
     * @param characterEncoding 字符编码
     * @return
     */
    public abstract String createSign(String content, String key, String characterEncoding);

    /**
     * 签名字符串
     *
     * @param text              需要签名的字符串
     * @param sign              签名结果
     * @param key               密钥
     * @param characterEncoding 编码格式
     * @return 签名结果
     */
    public abstract boolean verify(String text, String sign, String key, String characterEncoding);

    //签名错误代码
    public static final int SIGN_ERROR = 91;
}
