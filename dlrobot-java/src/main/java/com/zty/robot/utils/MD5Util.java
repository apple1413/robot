//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.zty.robot.utils;

import java.security.MessageDigest;

public class MD5Util {
    public MD5Util() {
    }

    public static final String signature(String s) {
        char[] hexDigits = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

        try {
            byte[] strTemp = s.getBytes("utf-8");
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(strTemp);
            byte[] md = mdTemp.digest();
            int j = md.length;
            char[] str = new char[j * 2];
            int k = 0;

            for(int i = 0; i < j; ++i) {
                byte b = md[i];
                str[k++] = hexDigits[b >> 4 & 15];
                str[k++] = hexDigits[b & 15];
            }

            return new String(str);
        } catch (Exception var10) {
            return null;
        }
    }
}
