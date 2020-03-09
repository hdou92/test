package com.hd.test.common;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;


public class SignUtil {
    private static Log logger = LogFactory.getLog(SignUtil.class);
    private static Comparator comparator = new Comparator<String>() {

        @Override
        public int compare(String o1, String o2) {
            return o1.compareTo(o2);
        }
    };
    private static String sortParam(Map<String, String> paramsMap,String securityKey) {
        StringBuilder sign = new StringBuilder();
        String[] params = (String[])paramsMap.keySet().toArray(new String[0]);
        Arrays.sort(params, comparator);
        int number = 0;

        for(int i = 0; i < params.length; ++i) {
            String str = params[i];
            if (StringUtils.isNotEmpty(paramsMap.get(str))) {
                if (number > 0) {
                    sign.append("&");
                }

                sign.append(str + "=" + (String)paramsMap.get(str));
                ++number;
            }
        }

        logger.debug("Sign data : map = " + paramsMap.toString());
        sign.append("&securityKey=" + securityKey);
        logger.debug("Sign data :String for sign = " + sign.toString());
        return sign.toString();
    }

    private static String signStr(String string) {
        byte[] hash;
        try {
            hash = MessageDigest.getInstance("MD5").digest(string.getBytes("utf-8"));
        } catch (NoSuchAlgorithmException var7) {
            var7.printStackTrace();
            return null;
        } catch (UnsupportedEncodingException var8) {
            var8.printStackTrace();
            return null;
        }

        StringBuffer buffer = new StringBuffer();


        for(int i = 0; i < hash.length; ++i) {

            buffer.append(String.format("%02x", hash[i]));

        }

        return buffer.toString();
    }
    /**
     * 对参数排序并签名
     * @param params
     * @return
     */
    public static String signParam(Map<String,String> params,String securityKey){
        String str=sortParam(params,securityKey);
        return signStr(str);
    }


}

