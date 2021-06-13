package com.deltaqin.seckill.common.utils;

import com.deltaqin.seckill.common.springcustom.CodePropertiesBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @author deltaqin
 * @date 2021/6/13 上午9:29
 */

@Slf4j
public class CodeUtil {
    private static int WIDTH = 90;
    private static int HEIGHT = 20;
    private static int CODE_COUNT = 4;
    private static int XX = 15;
    private static int FONT_HEIGHT = 18;
    private static int CODE_Y = 16;
    private static char[] CODE_SEQUENCE = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R',
            'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };

    //private static String LOCATION = "./";
    //private static String LOCATION = CodePropertiesBean.getInstance().getProperty("deltaqin.code.location");

    public static Map<String , Object> generateCodeAndPic() {
        BufferedImage bufferedImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);

        Graphics gd = bufferedImage.getGraphics();
        // 填充白色背景
        gd.setColor(Color.WHITE);
        gd.fillRect(0,0, WIDTH, HEIGHT);

        // 边框
        gd.setColor(Color.BLACK);
        gd.drawRect(0, 0, WIDTH - 1, HEIGHT - 1);

        // 大小依据图片高度
        Font font = new Font("Fixedsys", Font.BOLD, FONT_HEIGHT);
        gd.setFont(font);

        Random random = new Random();

        // 干扰线
        gd.setColor(Color.BLACK);
        for (int i = 0; i < 30; i++) {
            int x = random.nextInt(WIDTH);
            int y = random.nextInt(HEIGHT);
            int xl = random.nextInt(12);
            int yl = random.nextInt(12);
            // 线的两个端点
            gd.drawLine(x, y, x + xl, y + yl);
        }

        int red = 0, green = 0, blue = 0;
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < CODE_COUNT; i++) {
            String s = String.valueOf(CODE_SEQUENCE[random.nextInt(36)]);
            red = random.nextInt(255);
            green = random.nextInt(255);
            blue = random.nextInt(255);
            gd.setColor(new Color(red, green, blue));
            gd.drawString(s, (i + 1) * XX, CODE_Y);
            stringBuilder.append(s);
        }

        Map<String, Object> stringObjectMap = new HashMap<>();
        stringObjectMap.put("code", stringBuilder.toString());
        stringObjectMap.put("pic", bufferedImage);

        return stringObjectMap;

    }

    //public static void main(String[] args) throws IOException {
    //    FileOutputStream fileOutputStream = new FileOutputStream(LOCATION +System.currentTimeMillis()+".jpg");
    //    Map<String, Object> stringObjectMap = CodeUtil.generateCodeAndPic();
    //    ImageIO.write((RenderedImage) stringObjectMap.get("pic"), "jpeg", fileOutputStream);
    //    log.info("验证码是：" + stringObjectMap.get("code"));
    //}
}
