package ir.surena.sample.util;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Locale.LanguageRange;
import java.util.concurrent.ThreadLocalRandom;
import javax.servlet.http.HttpServletRequest;

import ir.surena.sample.dto.ResourceFileControl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class General {
    private static final Logger log = LoggerFactory.getLogger(General.class);
    private static final List<Locale> LOCALES = Arrays.asList(new Locale("fa"), new Locale("en"));

    public General() {
    }

    public static int randInt(int min, int max) {
        Random rand = new Random();
        return rand.nextInt(max - min + 1) + min;
    }

    public static int randIntThread(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    public static void sleep(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException var3) {
        }

    }

    public static String removeBadCharacterFromMobileNumber(String str) {
        str = str.replaceAll("-", "");
        str = str.replaceAll(" ", "");
        str = str.replaceAll("\\(", "").replaceAll("\\)", "");
        return str;
    }

    public static boolean isNumber(String str) {
        String regex = "[0-9]+";
        return str.matches(regex);
    }

    public static String md5(String original) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(original.getBytes());
            byte[] digest = md.digest();
            StringBuilder sb = new StringBuilder();
            byte[] var4 = digest;
            int var5 = digest.length;

            for(int var6 = 0; var6 < var5; ++var6) {
                byte b = var4[var6];
                sb.append(String.format("%02x", b & 255));
            }

            return sb.toString();
        } catch (Exception var8) {
            log.error("md5", var8);
            return "";
        }
    }

    public static String toCamelCase(String s) {
        String[] parts = s.split("_");
        String camelCaseString = "";
        String[] var3 = parts;
        int var4 = parts.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            String part = var3[var5];
            camelCaseString = camelCaseString + toProperCase(part);
        }

        String var10000 = camelCaseString.substring(0, 1).toLowerCase();
        return var10000 + camelCaseString.substring(1);
    }

    static String toProperCase(String s) {
        String var10000 = s.substring(0, 1).toUpperCase();
        return var10000 + s.substring(1).toLowerCase();
    }

    public static String getBundleMessage(String key, String lang) {
        Locale locale;
        if (lang.equalsIgnoreCase("fa")) {
            locale = new Locale("fa", "IR");
        } else {
            locale = new Locale("en", "US");
        }

        String result = getBundleMessage(key, locale);
        return result;
    }

    public static String getBundleMessage(String key, Locale locale) {
        String result = getBundleMessage(key, locale, false);
        return result;
    }

    public static String getBundleMessage(String key, Locale locale, boolean force_change_encoding) {
        if (key == null) {
            return null;
        } else if (key.startsWith("{")) {
            key = key.substring(1, key.length() - 1);
            ResourceFileControl resourceFileControl = ResourceFileControl.getInstance();
            ResourceBundle bundle = ResourceBundle.getBundle("messages", locale, resourceFileControl);
            String message = getBundleMessage(key, locale, force_change_encoding, bundle);
            if (key.equals(message)) {
                bundle = ResourceBundle.getBundle("messagesDefault", locale, resourceFileControl);
                message = getBundleMessage(key, locale, force_change_encoding, bundle);
            }

            return message;
        } else {
            return key;
        }
    }

    public static String getBundleMessageDefault(String key, Locale locale, boolean force_change_encoding) {
        ResourceFileControl resourceFileControl = ResourceFileControl.getInstance();
        ResourceBundle bundle = ResourceBundle.getBundle("messagesDefault", locale, resourceFileControl);
        return getBundleMessage(key, locale, force_change_encoding, bundle);
    }

    private static String getBundleMessage(String key, Locale locale, boolean force_change_encoding, ResourceBundle bundle1) {
        String k = key;
        String args = "";
        if (key.contains("|")) {
            k = key.split("\\|")[0];
            args = key.split("\\|")[1];
        }

        Object[] varargs = null;
        if (!args.equals("")) {
            varargs = args.split(",");

            for(int i = 0; i < varargs.length; ++i) {
                varargs[i] = getBundleMessage(GeneralBase.parseString(varargs[i]), locale, false);
            }
        }

        String textbundle;
        try {
            textbundle = bundle1.getString(k);
        } catch (Exception var9) {
            textbundle = convertEncodingFromTo(k, "UTF-8", "ISO-8859-1");
            return textbundle;
        }

        if (varargs != null) {
            textbundle = String.format(textbundle, varargs);
        }

        return force_change_encoding ? encodeUTF8(textbundle, "ISO-8859-1") : textbundle;
    }

    private static String convertEncodingFromTo(String text, String from_encoding, String to_encoding) {
        try {
            return new String(text.getBytes(Charset.forName(from_encoding)), Charset.forName(to_encoding));
        } catch (Exception var4) {
            log.error("convertEncodingFromTo", var4);
            return text;
        }
    }

    private static String encodeUTF8(String text, String source_encoding) {
        try {
            Charset UTF8_CHARSET = Charset.forName("UTF-8");
            Charset src_CHARSET = Charset.forName(source_encoding);
            return new String(text.getBytes(src_CHARSET), UTF8_CHARSET);
        } catch (Exception var4) {
            log.error("encodeUTF8", var4);
            return text;
        }
    }

    public static Locale resolveLocale() {
        return resolveLocale(getHttpRequest());
    }

    public static Locale resolveLocale(ServerHttpRequest request) {
        if (request == null) {
            return Locale.ENGLISH;
        } else {
            String headerLang = request.getHeaders().getFirst("Accept-Language");
            return headerLang != null && !headerLang.isEmpty() ? Locale.lookup(LanguageRange.parse(headerLang), LOCALES) : Locale.getDefault();
        }
    }

    public static Locale resolveLocale(HttpServletRequest request) {
        if (request == null) {
            return Locale.ENGLISH;
        } else {
            String headerLang = request.getHeader("Accept-Language");
            return headerLang != null && !headerLang.isEmpty() ? Locale.lookup(LanguageRange.parse(headerLang), LOCALES) : Locale.getDefault();
        }
    }

    public static HttpServletRequest getHttpRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            return null;
        } else {
            HttpServletRequest request = ((ServletRequestAttributes)requestAttributes).getRequest();
            return request;
        }
    }
}
