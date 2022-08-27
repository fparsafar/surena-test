package ir.surena.sample.util;

import java.nio.charset.Charset;
import java.util.HashSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TextHelper {
    private static final Logger log = LoggerFactory.getLogger(TextHelper.class);
    private static HashSet<Character> kafSet = new HashSet();
    private static HashSet<Character> wawSet = new HashSet();
    private static HashSet<Character> hehSet = new HashSet();
    private static HashSet<Character> yehSet = new HashSet();

    public TextHelper() {
    }

    public static String toStandardPersian(String s) {
        if (s == null) {
            return null;
        } else {
            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < s.length(); ++i) {
                char c = s.charAt(i);
                if (Character.isDigit(c)) {
                    sb.append(Character.getNumericValue(c));
                } else if (kafSet.contains(c)) {
                    sb.append('ک');
                } else if (wawSet.contains(c)) {
                    sb.append('و');
                } else if (hehSet.contains(c)) {
                    sb.append('ه');
                } else if (yehSet.contains(c)) {
                    sb.append('ی');
                } else {
                    sb.append(c);
                }
            }

            return sb.toString();
        }
    }

    public static String changeDigitsToEnglish(String str) {
        StringBuilder builder = new StringBuilder();
        if (str != null && str.trim().length() != 0) {
            for (int i = 0; i < str.length(); ++i) {
                char chr = str.charAt(i);
                if (Character.isDigit(chr)) {
                    builder.append(Character.getNumericValue(chr));
                } else {
                    builder.append(chr);
                }
            }

            return builder.toString();
        } else {
            return str;
        }
    }

    public static String encodeUTF8(String text, String source_encoding) {
        if (text == null) {
            return null;
        } else {
            String ret = text;

            try {
                if (source_encoding == null) {
                    source_encoding = "ISO-8859-5";
                }

                Charset UTF8_CHARSET = Charset.forName("UTF-8");
                Charset src_CHARSET = Charset.forName(source_encoding);
                byte[] s1 = text.getBytes(src_CHARSET);
                ret = new String(s1, UTF8_CHARSET);
            } catch (Exception var6) {
                log.error("encodeUTF8", var6);
            }

            return ret;
        }
    }

    public static boolean isPersian(String msg) {
        boolean isFarsi = false;
        if (msg != null && msg.length() > 0) {
            for (int i = 0; i < msg.length(); ++i) {
                if (msg.charAt(i) > 1000) {
                    isFarsi = true;
                    break;
                }
            }
        }

        return isFarsi;
    }

    static {
        char[] kaf = new char[]{'ک', 'ݢ', 'ݣ', 'ݤ', 'ﮎ', 'ﮏ', 'ﮐ', 'ﮑ', 'ك', 'ګ', 'ڬ', 'ڮ', 'ﻙ', 'ﻚ', 'ﻛ', 'ﻜ'};
        char[] waw = new char[]{'ؤ', 'و', 'ٶ', 'ۄ', 'ۊ', 'ۏ', 'ﺅ', 'ﺆ', 'ﻭ', 'ﻮ'};
        char[] heh = new char[]{'ه', 'ھ', 'ۀ', 'ہ', 'ۂ', 'ﮤ', 'ﮥ', 'ﮦ', 'ﮪ', 'ﮫ', 'ﮬ', 'ﮭ', 'ﻩ', 'ﻪ', 'ﻫ', 'ﻬ', 'ة', 'ۃ', 'ﺓ', 'ﺔ'};
        char[] yeh = new char[]{'ئ', 'ي', 'ۍ', 'ێ', 'ۑ', 'ے', 'ۓ', 'ﮮ', 'ﮯ', 'ﮰ', 'ﮱ', 'ﺉ', 'ﺊ', 'ﺋ', 'ﺌ', 'ﻱ', 'ﻲ', 'ﻳ', 'ﻴ', 'ی', 'ﯼ', 'ﯽ', 'ﯾ', 'ﯿ', 'ى', 'ﻯ', 'ﻰ'};
        char[] var4 = kaf;
        int var5 = kaf.length;

        int var6;
        char aYeh;
        for (var6 = 0; var6 < var5; ++var6) {
            aYeh = var4[var6];
            kafSet.add(aYeh);
        }

        var4 = waw;
        var5 = waw.length;

        for (var6 = 0; var6 < var5; ++var6) {
            aYeh = var4[var6];
            wawSet.add(aYeh);
        }

        var4 = heh;
        var5 = heh.length;

        for (var6 = 0; var6 < var5; ++var6) {
            aYeh = var4[var6];
            hehSet.add(aYeh);
        }

        var4 = yeh;
        var5 = yeh.length;

        for (var6 = 0; var6 < var5; ++var6) {
            aYeh = var4[var6];
            yehSet.add(aYeh);
        }

    }
}