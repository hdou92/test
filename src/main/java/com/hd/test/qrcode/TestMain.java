package com.hd.test.qrcode;

import com.hd.test.common.ObjectUtils;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * 测试图片处理  生成二维码图片  和解析二维码图片
 */
public class TestMain {


    /**
     * 二维码中间的logo 图片地址
     */
    private static final String LOGO_PHOTO = "C:\\Users\\Administrator\\Pictures\\timg.jpg";

    /**
     * 二维码存放图片地址
     */
    private static final String QRCODE_PHOTO = "C:/Users/Administrator/Pictures/";

    public static void main(String[] args) throws Exception {
        System.out.println("start generate qrcode ... ");
        // 生成二维码到缓存区
        BufferedImage bufferedImage = QrCodeGenWrapper.of("我是侯渡abc").asBufferedImage();
        // 二维码存放位置
        String path = QRCODE_PHOTO + "我的二维码abc.png";
        File file = new File(path);
        FileImageOutputStream fileOutPut = null;
        try {
            fileOutPut = new FileImageOutputStream(file);
            // 从内存写入磁盘
            boolean isWrite = ImageIO.write(bufferedImage, "png", file);
            System.out.println("qrcode generate status : " + isWrite);
            String decode = QrCodeUtil.decode(file);
            System.out.println(decode);
        } catch (Exception e) {
            System.out.println("qrcode generate error ..." + e);
        } finally {
            try {
                if (ObjectUtils.isNotNull(fileOutPut)) {
                    fileOutPut.close();
                }
            } catch (IOException e) {
                System.out.println("qrcode close error ...");
            }
        }
    }
}
