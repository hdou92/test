package com.hd.test.qrcode;

import com.google.zxing.WriterException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;

@RestController
public class TestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestController.class);

    private static final String URL = "http://localhost:8888";

    @RequestMapping("testQrcode")
    public void downloadQr(HttpServletResponse res) throws WriterException, IOException {
        String filename = "我的二维码abc.png";
        res.setContentType("application/octet-stream;charset=utf-8");
        res.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, "UTF-8"));
        //构建二维码内容
        StringBuffer sb = new StringBuffer();
        // 把/api这段去掉
        sb.append(URL);
        sb.append("#/rl?g=");
        String name = "Im 侯渡";
        name = URLEncoder.encode(name);// encode,更好的支持中文
        sb.append(name);

        //生成二维码图片
        BufferedImage image = QrCodeGenWrapper.of(sb.toString()).asBufferedImage();
        //图片写入response
        OutputStream os = null;
        try {
            os = res.getOutputStream();
            ImageIO.write(image, "png", os);
        } catch (IOException e) {
            LOGGER.error("系统出现了错误！");
        } finally {
            if(os != null) {
                os.close();
            }
        }
    }
}
