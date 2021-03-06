package com.hd.test.common;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.*;

/**
 * Base64 和 图片之间的转换
 */
public class Base64ToImagUtil {

    /**
     * base64字符串转化成图片
     *
     * @param imgStr base64字符
     * @param imgPath   新生成的图片的路径
     * @throws IOException
     */
    public static boolean generateImage(String imgStr, String imgPath)
    { //对字节数组字符串进行Base64解码并生成图片？
        if (imgStr == null) //图像数据为空
            return false;
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            //Base64解码
            byte[] b = decoder.decodeBuffer(imgStr);
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {//调整异常数据
                    b[i] += 256;
                }
            }
            //生成jpeg图片
            String imgFilePath = imgPath+"new.jpg";//新生成的图片
            OutputStream out = new FileOutputStream(imgFilePath);
            out.write(b);
            out.flush();
            out.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    /**
     * b图片转化成base64字符串
     *
     * @param imgFilePath 图片存储路径
     * @throws IOException
     */
    public static String getImageStr(String imgFilePath)

    {//将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        InputStream in = null;
        byte[] data = null;
        //读取图片字节数组
        try {
            in = new FileInputStream(imgFilePath);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);//返回Base64编码过的字节数组字符串
    }
}
