package com.hd.test.file;

import com.alibaba.fastjson.JSON;
import com.hd.test.common.SignUtil;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.ByteArrayRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.*;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.http.entity.ContentType;
import org.junit.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class upload {

    private static final String BASE_URL = "http://127.0.0.1:9001/";

    private static final String API_URL = "robot/robotUploadLogFile";

    private String token = "bearer;eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoicm9ib3QiLCJ1bmlxdWVfbmFtZSI6InJvYm90MSIsInVzZXJpZCI6IjNhMDQ1NTFjNTA3YzRhYTNiZTZjMDg2ZDUyMjk3ZWYwIiwiaXNzIjoiZXBzaGVhbHRoIiwiYXVkIjoiMTkyLjE2OC4yLjkiLCJleHAiOjE1ODM0MDUwNTYsIm5iZiI6MTU4MjgwMDI1Nn0.nC47WX2kpm_0FmpMJSwsnBtpgb3aJTbUgo3L7xPuBg4";

    private String securityKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCd1pojfE/E6cIpioxewqZ6rR0oYJP/1aUD9boXJ1r7DdN07bP2h2LnDWRZLVuf1lC9LHOM1n3O8O9oA9qyqxOGwtaN6trZZ1S1cEtU0wr/SGBhT63HGf8npZoKFKMOjM5kkKHXz2mKG1Nbt3T0MEePlWQmyf1+xe5BUQV+PyKbsQIDAQAB";

    private String timestamp = "1582800348138";

    private String sign = "62b80a37003f8c2e977f5799c953fd03";

    /**
     * 日志上传
     *
     * @return 200 成功  其他都是失败
     */
    @Test
    public void logUpload() throws Exception {

        String fileName = "7.rar";
        File file = new File("/var/test/file/client/" + fileName);
        //将文件传给服务器
//        FilePart filePart = new FilePart("logFile", file);// 请求接口所需参数 logFile

        System.out.println(file.length());
        FileInputStream fis = new FileInputStream(file);
        byte[] bytes = new byte[10000];
        fis.read(bytes, 0, 10000);

        // 发送 HTTP 请求 ，文件上传
        int code = httpSendFile(BASE_URL + API_URL, bytes, new LogFileVo(fileName, "0"), getContentRange(0, 10000, file.length()),new ByteArrayRequestEntity(bytes));
        bytes = new byte[(int)file.length() - bytes.length];
        System.out.println(file.length());
        fis.read(bytes, ((int)(file.length() -1) - bytes.length), (int)file.length() - bytes.length);
        // 发送 HTTP 请求 ，文件上传
        int code1 = httpSendFile(BASE_URL + API_URL, bytes, new LogFileVo(fileName, "0"), getContentRange(10000, file.length() - 1, file.length()),new ByteArrayRequestEntity(bytes));
        System.out.println(code);
        System.out.println(code1);
        fis.close();
    }


    /**
     * http 发送文件 工具方法
     *
     * @param url       访问路径
     * @param file      发送文件
     * @param logFileVo 其他文本参数
     * @return 响应请求状态
     * @throws Exception             请求错误
     * @throws FileNotFoundException 找不到文件
     */
    public int httpSendFile(String url, byte[] file, LogFileVo logFileVo, String range,ByteArrayRequestEntity bare) throws IOException {

        RequestBody body = RequestBody.create(MediaType.parse("application/octet-stream"), file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("logFile", logFileVo.getLogFileName(), body);

//        FilePart filePart = new FilePart("logFile",  part);
        //PostMethod filePost = new PostMethod(nettyConfig.getFrontServerLoginURL() + url);
        PostMethod filePost = new PostMethod(url);
        filePost.setRequestHeader("token", token);
        filePost.setRequestHeader("Content-Range", range);
        filePost.setRequestEntity(bare);
        // Part[] parts = new Part[params.length + 1];
//        Part[] parts = new Part[4];
        Part[] parts = new Part[3];
//        String timestamp = System.currentTimeMillis() + "";
        StringPart timep = new StringPart("timestamp", timestamp);
        parts[0] = timep;
        Map<String, String> map = new HashMap<>();
        map.put("timestamp", timestamp);
        map.put("bizContent", JSON.toJSONString(logFileVo));
        // StringPart:普通的文本参数
        StringPart stringPart = new StringPart("bizContent", JSON.toJSONString(logFileVo));
        parts[1] = stringPart;
//        String sign = SignUtil.signParam(map, securityKey);
        StringPart signp = new StringPart("sign", sign);
        parts[2] = signp;
        // 添加 FilePart 文件参数
//        parts[parts.length - 1] = part;

        MultipartRequestEntity mre = new MultipartRequestEntity(parts, filePost.getParams());
        filePost.setRequestEntity(mre);
        HttpClient client = new HttpClient();
        try {
            return client.executeMethod(filePost);
        } catch (IOException e) {
            throw new IOException("文件上传错误：" + e.getMessage());
        }

    }

    private String getContentRange(File file) {
        return "bytes 0-" + (file.length() - 1) + "/" + file.length();
    }

    private String getContentRange(long startIndex, long endIndex, long length) {
        return "bytes " + startIndex + "-" + endIndex + "/" + length;
    }


    class LogFileVo {

        private String logFileName;//日志文件名
        private String logType;//0-APP;1-工控机；2-底盘

        public LogFileVo() {
            super();
        }

        public LogFileVo(String logFileName, String logType) {
            this.logFileName = logFileName;
            this.logType = logType;
        }

        public String getLogFileName() {
            return logFileName;
        }

        public void setLogFileName(String logFileName) {
            this.logFileName = logFileName;
        }

        public String getLogType() {
            return logType;
        }

        public void setLogType(String logType) {
            this.logType = logType;
        }
    }
}
