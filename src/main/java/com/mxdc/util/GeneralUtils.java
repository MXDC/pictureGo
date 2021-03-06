package com.mxdc.util;

import com.mxdc.controller.UploadCenterPaneController;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author MXDC
 * @date 2019/8/26
 **/
public class GeneralUtils {

    private static final String KEY_AES = "AES";
    private static  StackPane root;
    private static UploadCenterPaneController upload = new UploadCenterPaneController();

    public static void setRoot(StackPane root) {
        GeneralUtils.root = root;
    }

    /**
     * 消息弹窗
     * @param title 标题
     * @param msg 信息
     * @param alertType 弹窗类型
     */
    public static void messageDialog(String title, String msg, Alert.AlertType alertType){
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    /**弹出信息提示动画的函数
     * @param stageStackPane 主舞台底下的Stack(堆)容器
     * @param fadingLabel 信息提示的label
     * */
    public static void toastInfo(StackPane stageStackPane, Label fadingLabel){
        fadingLabel.getStylesheets().add("css/FadingLabelStyle.css");
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(3),fadingLabel);
        fadeTransition.setFromValue(1);  //不透明度从1变到0,from 1 to 0.
        fadeTransition.setToValue(0);
        stageStackPane.getChildren().add(fadingLabel);
        //动画完成后移除label组件
        fadeTransition.setOnFinished(fade-> stageStackPane.getChildren().remove(fadingLabel));
        //开始播放渐变动画提示
        fadeTransition.play();
    }
    /**弹出信息提示动画的函数
     * @param fadingLabel 信息提示的label
     * */
    public static void toastInfo(Label fadingLabel){
        toastInfo(root,fadingLabel);
    }

    /**
     * 添加快捷键
     */
    public static void addShortcutKey(){
        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.OFF);
        new Thread(() -> GeneralUtils.addUploadShortcutKey()).start();
    }

    /**
     * 图片上传快捷键
     */
    public static void addUploadShortcutKey()  {
        try {
            GlobalScreen.registerNativeHook();
        }
        catch (NativeHookException ex) {
            System.err.println("There was a problem registering the native hook.");
            System.err.println(ex.getMessage());
            System.exit(1);
        }
        // 全局将键盘
        GlobalScreen.addNativeKeyListener(new NativeKeyListener() {
            @Override
            public void nativeKeyPressed(NativeKeyEvent e) {
                boolean isAltPressed = (e.getModifiers() & NativeKeyEvent.ALT_MASK) != 0;
                boolean isCtrlPressed = (e.getModifiers() & NativeKeyEvent.CTRL_MASK) != 0;
                boolean isUpressed = e.getKeyCode() == NativeKeyEvent.VC_U;
                if (isAltPressed & isCtrlPressed & isUpressed){
                    Platform.runLater(()-> upload.onClickedClipboard(null));
                }
            }
            @Override
            public void nativeKeyReleased(NativeKeyEvent e) {
            }
            @Override
            public void nativeKeyTyped(NativeKeyEvent e) {
            }
        });
    }
    /**
     * AES加密
     * @param src 明文
     * @param key 密钥
     * @return   密文
     * @throws Exception 加密异常
     */
    public static String encrypt(String src, String key) throws Exception {
        if (key == null || key.length() != 16) {
            throw new Exception("key不滿足條件");
        }
        byte[] raw = key.getBytes();
        SecretKeySpec skeySpec = new SecretKeySpec(raw, KEY_AES);
        Cipher cipher = Cipher.getInstance(KEY_AES);
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        byte[] encrypted = cipher.doFinal(src.getBytes());
        return byte2hex(encrypted);
    }

    /**
     * AES解密
     * @param src 要解密的文本
     * @param key 密钥
     * @return 明文
     * @throws Exception 解密异常
     */
    public static String decrypt(String src, String key) throws Exception {
        if (key == null || key.length() != 16) {
            throw new Exception("key不滿足條件");
        }
        byte[] raw = key.getBytes();
        SecretKeySpec skeySpec = new SecretKeySpec(raw, KEY_AES);
        Cipher cipher = Cipher.getInstance(KEY_AES);
        cipher.init(Cipher.DECRYPT_MODE, skeySpec);
        byte[] encrypted1 = hex2byte(src);
        byte[] original = cipher.doFinal(encrypted1);
        return new String(original);
    }

    public static byte[] hex2byte(String strhex) {
        if (strhex == null) {
            return null;
        }
        int l = strhex.length();
        if (l % 2 == 1) {
            return null;
        }
        byte[] b = new byte[l / 2];
        for (int i = 0; i != l / 2; i++) {
            b[i] = (byte) Integer.parseInt(strhex.substring(i * 2, i * 2 + 2),
                    16);
        }
        return b;
    }

    public static String byte2hex(byte[] b) {
        StringBuilder hs = new StringBuilder();
        String stmp;
        for (byte value : b) {
            stmp = (Integer.toHexString(value & 0XFF));
            if (stmp.length() == 1) {
                hs.append("0").append(stmp);
            } else {
                hs.append(stmp);
            }
        }
        return hs.toString().toUpperCase();
    }

    /**
     * 保存粘贴板的图片到本地
     * @param desFile 保存后的图片路径
     * @param image 粘贴板二进制图片
     */
    public static void saveClipboardImage(File desFile, Image image) throws IOException {
        //转成jpg
        //BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_RGB);
        //转成png
        BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_BGR);
        Graphics2D g = bufferedImage.createGraphics();
        g.drawImage(image, null, null);
        //ImageIO.write((RenderedImage)bufferedImage, "jpg", file);
        ImageIO.write(bufferedImage, "jpg", desFile);
    }
}
