package com.hd.test.qrcode;

import com.google.zxing.WriterException;

import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * 测试图片处理  生成二维码
 */
public class TestMain {

    /**
     * 二维码中间的logo 图片地址
     */
    private static final String LOGO_PHOTO = "C:\\Users\\Administrator\\Pictures\\timg.jpg";

    /**
     * 二维码存放图片地址
     */
    private static final String QRCODE_PHOTO = "C:/Users/Administrator/Pictures/1.jpg";

    public static void main(String[] args) throws Exception {
//        BufferedImage bufferedImage = QrCodeGenWrapper.of("").asBufferedImage();
//        System.out.println(bufferedImage);

        //TODO ImageUtil 中需要加如  讲图片写入磁盘的方法
    }
}
