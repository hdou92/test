package com.hd.test.qrcode;

import com.google.zxing.WriterException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;

/**
 * 测试二维码获取接口
 */
@RestController
public class TestQrCodeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestQrCodeController.class);

    private static final String FILE_NAME_SUFFIX = ".png";

    /**
     * @param body 构建二维码内容
     * @param res  响应 response
     * @throws WriterException 写数据错误
     * @throws IOException     流错误
     */
    @RequestMapping(value = "testQrcode/{body}/{fileName}", method = RequestMethod.GET)
    public void downloadQr(@PathVariable("body") String body,
                           @PathVariable("fileName") String fileName, HttpServletResponse res) throws WriterException, IOException {
        LOGGER.debug(" generate qrcode api start .... body : " + body + " fileName : " + fileName);
        res.setContentType("application/octet-stream;charset=utf-8");
        res.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(getFileName(fileName), "UTF-8"));
        //生成二维码图片
        BufferedImage image = QrCodeGenWrapper.of(URLEncoder.encode(body, "utf-8")).asBufferedImage();
        //图片写入response
        OutputStream os = null;
        try {
            os = res.getOutputStream();
            ImageIO.write(image, "png", os);
        } catch (Exception e) {
            LOGGER.error("系统出现了错误 error : " + e);
        } finally {
            if (os != null) {
                os.close();
            }
        }
    }

    /**
     * 加入后缀
     *
     * @param fileName 文件名
     * @return 文件名 + 后缀（.png）
     */
    private String getFileName(String fileName) {
        return fileName + FILE_NAME_SUFFIX;
    }
}
