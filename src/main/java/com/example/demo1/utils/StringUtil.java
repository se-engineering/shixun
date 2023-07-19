package com.example.demo1.utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Random;
import java.util.UUID;

public class StringUtil {
    public static String randomNumber(int count){
        // 一个容器来装字符串
        StringBuilder stringBuilder = new StringBuilder();
        //java产生随机数
        Random random = new Random();
        for (int i=0;i<count;i++){
            int num = random.nextInt(10);//产生一个10以内的随机数
            stringBuilder.append(num);
        }
        return stringBuilder.toString();
    }
    public static String uuid (){
        return UUID.randomUUID().toString().replace("-","");
    }
    public static String changeImage2String(BufferedImage bufferedImage){
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ImageIO.write(bufferedImage,"jpg",bos);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(bos.toByteArray());
    }
}
