package com.hd.test.qrcode;

import com.sylan.biss.core.common.rest.RestResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/*
*
* 日志上传工具类
*
* */
public class UploadFileUtil {
    private static Logger logger = LoggerFactory.getLogger(UploadFileUtil.class);

    public static RestResult uploadFileLogo(MultipartFile logFile, String newFilename, String dir) {
        logger.info("logo存储于：" + dir);

        InputStream fis = null;
        FileOutputStream fos = null;
        try {
            File targetDir = new File(dir);
            targetDir.mkdirs();
            logger.debug("targetDir:" + dir);
            File target = new File(dir + newFilename);
            fis = logFile.getInputStream();

            if (target.exists()) {
                target.delete();
            }
            target.createNewFile();
            fos = new FileOutputStream(target);
            byte[] temp = new byte[1024];
            int i = fis.read(temp);
            while (i != -1) {
                fos.write(temp, 0, temp.length);
                i = fis.read(temp);
            }
            fos.flush();
            RestResult ret = new RestResult();
            return ret;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return new RestResult("201", "上传文件失败");
    }

}
