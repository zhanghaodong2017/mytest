package com.test.QRCode;

/**
 * 二维码生成测试类
 * @author Cloud
 * @data   2016-11-21
 * QRCodeTest
 */
public class QRCodeTest {

    public static void main(String[] args) throws Exception {
        /**
         *    QRcode 二维码生成测试
         *    QRCodeUtil.QRCodeCreate("http://blog.csdn.net/u014266877", "E://qrcode.jpg", 15, "E://icon.png");
         */
        /**
         *     QRcode 二维码解析测试
         *    String qrcodeAnalyze = QRCodeUtil.QRCodeAnalyze("E://qrcode.jpg");
         */
        /**
         * ZXingCode 二维码生成测试
         * QRCodeUtil.zxingCodeCreate("http://blog.csdn.net/u014266877", 300, 300, "E://zxingcode.jpg", "jpg");
         */
        /**
         * ZxingCode 二维码解析
         *    String zxingAnalyze =  QRCodeUtil.zxingCodeAnalyze("E://zxingcode.jpg").toString();
         */
        System.out.println("success");
    }
}
