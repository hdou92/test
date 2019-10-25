package com.hd.test.qrcode;

import com.hd.test.common.ObjectUtils;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;

/**
 * 测试图片处理  生成二维码图片  和解析二维码图片
 */
public class TestMain {

    /**
     * 二维码存放图片地址
     */
    private static final String QRCODE_PHOTO = "C:/Users/Administrator/Pictures/";

    public static void main(String[] args) throws Exception {
        encode(QRCODE_PHOTO + "我的二维码abc.png", "我是侯渡abc");
        decode(QRCODE_PHOTO + "我的二维码abc.png");
    }

    /**
     * 解析二维码图片
     *
     * @param path 解析二维码的图片路径
     * @throws Exception
     */
    private static void decode(String path) throws Exception {
        String decode = QrCodeUtil.decode(new File(path));
        System.out.println(URLDecoder.decode(decode, "utf-8"));
    }

    /**
     * 生成二维码 并存放到目录
     *
     * @param path 二维码存储目录
     * @param body 二维码内容
     * @throws Exception
     */
    private static void encode(String path, String body) throws Exception {
        System.out.println("start generate qrcode ... ");
        // 生成二维码到缓存区
        BufferedImage bufferedImage = QrCodeGenWrapper.of(body).asBufferedImage();
        // 二维码存放位置
        File file = new File(path);
        FileImageOutputStream fileOutPut = null;
        try {
            fileOutPut = new FileImageOutputStream(file);
            // 从内存写入磁盘
            boolean isWrite = ImageIO.write(bufferedImage, "png", file);
            System.out.println("qrcode generate status : " + isWrite);
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
