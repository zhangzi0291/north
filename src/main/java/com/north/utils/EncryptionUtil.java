package com.north.utils;

import javax.sound.midi.Soundbank;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * 类的描述
 *
 * @Author zhengxiangnan
 * @Date 2018/6/21 14:53
 */
public class EncryptionUtil {

    public static void main(String[] args) {
        System.out.println(EncryptionUtil.getPasswordEncoder("admin","admin"));
    }

    public static String getPasswordEncoder(String username, String password){
        String encoderPassword = getSHA1Str(password);
        String encoderStr = getSHA1Str(username+encoderPassword);
        return encoderStr;
    }

    public static String getMD5Str(String value){
        try {
            MessageDigest md5=MessageDigest.getInstance("MD5");
            String md5str= Base64.getEncoder().encodeToString(md5.digest(value.getBytes("utf-8")));
            return md5str;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getSHA1Str(String value){
        try {
            //指定sha1算法
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            digest.update(value.getBytes());
            //获取字节数组
            byte messageDigest[] = digest.digest();
            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            // 字节数组转换为 十六进制 数
            for (int i = 0; i < messageDigest.length; i++) {
                String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexString.append(0);
                }
                hexString.append(shaHex);
            }
            return hexString.toString().toUpperCase();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}
