package ir.surena.sample.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.houbb.id.util.IdHelper;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.math.BigInteger;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.TimeZone;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;
import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class GeneralBase {
    private static final Logger log = LoggerFactory.getLogger(GeneralBase.class);
    private static final String AB = "0123456789ABCDEGHIJKLMONPQRSTUVWXYZabcdefghijklomnpqrstuvwsyz";
    private static SecureRandom rnd = new SecureRandom();

    public GeneralBase() {
    }

    public static LocalDateTime getFromString(String str) throws Exception {
        if (StringUtils.isEmpty(str)) {
            return null;
        } else {
            String[] c = str.split("\\s");
            String[] dateStr = c[0].split("-");
            String[] timeStr = c[1].split(":");
            Integer year = Integer.valueOf(dateStr[0]);
            Integer month = Integer.valueOf(dateStr[1]);
            if (month >= 1 && month <= 12) {
                Integer day = Integer.valueOf(dateStr[2]);
                if (day >= 0 && day <= 31) {
                    Integer hour = Integer.valueOf(timeStr[0]);
                    if (hour >= 0 && hour <= 23) {
                        Integer minute = Integer.valueOf(timeStr[1]);
                        if (minute >= 0 && minute <= 59) {
                            Integer second = 0;

                            try {
                                second = Integer.valueOf(timeStr[2]);
                                if (second < 0 || second > 59) {
                                    throw new Exception("Second not valid!");
                                }
                            } catch (Exception var12) {
                            }

                            CalendarTool calender = new CalendarTool();
                            calender.setIranianDate(year, month, day, hour, minute, second);
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                            return LocalDateTime.parse(calender.getGregorianDate2WithTime(), formatter);
                        } else {
                            throw new Exception("Minute not valid!");
                        }
                    } else {
                        throw new Exception("Hour not valid!");
                    }
                } else {
                    throw new Exception("Day not valid!");
                }
            } else {
                throw new Exception("Month not valid!");
            }
        }
    }

    public static String convertLocalDateToShamsi(LocalDateTime localDateTime) {
        CalendarTool calendarTool = new CalendarTool(localDateTime.getYear(), localDateTime.getMonthValue(), localDateTime.getDayOfMonth());
        int irYear = calendarTool.getIranianYear();
        int irMonth = calendarTool.getIranianMonth();
        int irDay = calendarTool.getIranianDay();
        calendarTool.setIranianDate(irYear, irMonth, irDay, localDateTime.getHour(), localDateTime.getMinute(), localDateTime.getSecond());
        calendarTool.getGregorianDate2();
        return calendarTool.getIranianDateTime2();
    }

    public static LocalDateTime convertToLocalDateTimeViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public static LocalDateTime convertToLocalDateTimeViaMilisecond(Date dateToConvert) {
        return Instant.ofEpochMilli(dateToConvert.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public static boolean isNumber(String str) {
        String regex = "[0-9]+";
        return str.matches(regex);
    }

    public static boolean isNumberWithSignSupported(String str) {
        String regex = "^[-+]?[0-9]+";
        return str.matches(regex);
    }

    public static boolean isValidUUID(String str) {
        String regex = "^[0-9a-fA-F]{32}$";
        return str.matches(regex);
    }

    public static long parseLong(String s) {
        try {
            return Long.parseLong(s);
        } catch (Exception var2) {
            return 0L;
        }
    }

    public static Long parseLongNullable(String s) {
        return s == null ? null : parseLong(s);
    }

    public static long parseLong(String s, long defaultvalue) {
        try {
            return Long.parseLong(s);
        } catch (Exception var4) {
            return defaultvalue;
        }
    }

    public static double parseDouble(Object s) {
        try {
            return Double.parseDouble(parseString(s));
        } catch (Exception var2) {
            return 0.0D;
        }
    }

    public static Double parseDoubleNullable(Object s) {
        return s == null ? null : parseDouble(s);
    }

    public static String parseString(Object s) {
        try {
            return TextHelper.toStandardPersian(s.toString());
        } catch (Exception var2) {
            return "";
        }
    }

    public static float parseFloat(Object s) {
        try {
            return Float.parseFloat(parseString(s));
        } catch (Exception var2) {
            return 0.0F;
        }
    }

    public static int parseInt(String s, int defaultvalue) {
        try {
            return Integer.parseInt(s);
        } catch (Exception var3) {
            return defaultvalue;
        }
    }

    public static short parseShort(Object s) {
        try {
            return Short.parseShort(parseString(s));
        } catch (Exception var2) {
            return 0;
        }
    }

    public static Date parseDate(Object str) {
        SimpleDateFormat pd = new SimpleDateFormat("yyyy-MM-dd");

        try {
            return str instanceof Date ? (Date)str : pd.parse(parseString(str));
        } catch (Exception var3) {
            return new Date();
        }
    }

    public static Date parseAnyDateToMiladi(Object str) {
        return parseAnyDateToMiladi(parseString(str));
    }

    public static Date parseAnyDateToMiladi(String datestr) {
        if (isEmpty(datestr)) {
            return null;
        } else {
            datestr = datestr.replace("/", "-");
            String[] datArr = datestr.split("-");
            int year = 0;
            if (datArr.length > 0) {
                year = parseInt(datArr[0]);
            }

            if (year == 0) {
                return null;
            } else {
                return year < 1500 ? convertPersianDateString2MiladiDate(datestr) : parseDate(datestr);
            }
        }
    }

    public static Date parseAnyValidDateToMiladi(String datestr) {
        if (isEmpty(datestr)) {
            return null;
        } else {
            datestr = datestr.replace("/", "-");
            String[] datArr = datestr.split("-");
            int year = 0;
            if (datArr.length > 0) {
                year = parseInt(datArr[0]);
            }

            if (year == 0) {
                return null;
            } else {
                return year < 1500 ? convertPersianDateString2MiladiDate(datestr) : parseDate(datestr);
            }
        }
    }

    public static Date parseAnyDateToMiladiAsUTC(String datestr) {
        if (isEmpty(datestr)) {
            return new Date();
        } else {
            datestr = datestr.replace("/", "-");
            String[] datArr = datestr.split("-");
            int year = 0;
            if (datArr.length > 0) {
                year = parseInt(datArr[0]);
            }

            if (year == 0) {
                return new Date();
            } else {
                return year < 1500 ? convertPersianDateString2MiladiDateasUTC(datestr) : parseDateAsUTC(datestr);
            }
        }
    }

    public static boolean isValidDate(String datestr) {
        if (isEmpty(datestr)) {
            return false;
        } else {
            datestr = datestr.replace("/", "-");
            if (!datestr.matches("[0-9-]+")) {
                return false;
            } else {
                String[] datArr = datestr.trim().split("-");
                if (datArr.length != 3) {
                    return false;
                } else {
                    int year = parseInt(datArr[0]);
                    if (year >= 1000 && year < 10000) {
                        int month = parseInt(datArr[1]);
                        if (month >= 1 && month <= 12) {
                            int day = parseInt(datArr[2]);
                            return day >= 1 && day <= 31;
                        } else {
                            return false;
                        }
                    } else {
                        return false;
                    }
                }
            }
        }
    }

    public static boolean isValidTime(String timestr) {
        if (isEmpty(timestr)) {
            return false;
        } else {
            String TIME24HOURS_PATTERN = "([01]?[0-9]|2[0-3]):[0-5][0-9](:[0-5][0-9])?";
            Pattern pattern = Pattern.compile(TIME24HOURS_PATTERN);
            Matcher matcher = pattern.matcher(timestr.trim());
            return matcher.matches();
        }
    }

    public static boolean isValidDateTime(String str) {
        if (isEmpty(str)) {
            return false;
        } else {
            String[] datArr = str.trim().split(" ");
            if (datArr.length >= 1 && datArr.length <= 2) {
                String dateStr = datArr[0];
                boolean validDate = isValidDate(dateStr);
                if (!validDate) {
                    return false;
                } else {
                    if (datArr.length == 2) {
                        String timeStr = datArr[1];
                        boolean validTime = isValidTime(timeStr);
                        if (!validTime) {
                            return false;
                        }
                    }

                    return true;
                }
            } else {
                return false;
            }
        }
    }

    public static boolean isEmpty(String s) {
        return s == null || s.trim().equals("");
    }

    public static int parseInt(String s) {
        try {
            return Integer.parseInt(s);
        } catch (Exception var2) {
            return 0;
        }
    }

    public static Integer parseIntNullable(String s) {
        return s == null ? null : parseInt(s);
    }

    public static Date convertPersianDateString2MiladiDate(String pdate) {
        CalendarTool ca = new CalendarTool();
        int year = parseInt(pdate.split("-")[0]);
        int mont = parseInt(pdate.split("-")[1]);
        int day = parseInt(pdate.split("-")[2]);
        ca.setIranianDate(year, mont, day);
        return parseDate(ca.getGregorianDate2());
    }

    public static Date convertPersianDateString2MiladiDateasUTC(String pdate) {
        CalendarTool ca = new CalendarTool();
        int year = parseInt(pdate.split("-")[0]);
        int mont = parseInt(pdate.split("-")[1]);
        int day = parseInt(pdate.split("-")[2]);
        ca.setIranianDate(year, mont, day);
        return parseDateAsUTC(ca.getGregorianDate2());
    }

    public static Date parseDate(String str) {
        SimpleDateFormat pd = new SimpleDateFormat("yyyy-MM-dd");

        try {
            return pd.parse(str);
        } catch (Exception var3) {
            return new Date();
        }
    }

    public static Date parseDateAsUTC(String str) {
        SimpleDateFormat pd = new SimpleDateFormat("yyyy-MM-dd");
        pd.setTimeZone(TimeZone.getTimeZone("UTC"));

        try {
            return pd.parse(str);
        } catch (Exception var3) {
            return new Date();
        }
    }

    public static Date parseTime(String str) {
        SimpleDateFormat pd = new SimpleDateFormat("HH:mm");

        try {
            return pd.parse(str);
        } catch (Exception var3) {
            return new Date();
        }
    }

    public static Date parseAnyDateToMiladiWithTime(Object str) {
        return parseAnyDateToMiladiWithTime(parseString(str));
    }

    public static Date parseAnyDateToMiladiWithTime(String datestr) {
        if (isEmpty(datestr)) {
            return new Date();
        } else {
            String[] datArr = datestr.split("-");
            int year = 0;
            if (datArr.length > 0) {
                year = parseInt(datArr[0]);
            }

            if (year == 0) {
                return new Date();
            } else {
                return year < 1500 ? convertPersianDateString2MiladiDateTime(datestr) : parseDateWithTime(datestr);
            }
        }
    }

    public static Date convertPersianDateString2MiladiDateTime(String pdate) {
        CalendarTool ca = new CalendarTool();
        String[] pdateArr = pdate.split(" ");
        String date = pdateArr.length > 0 ? pdateArr[0] : "";
        String time = pdateArr.length > 1 ? pdateArr[1] : "";
        String[] dateArr = date.split("-");
        int year = dateArr.length > 0 ? parseInt(dateArr[0]) : 0;
        int mont = dateArr.length > 1 ? parseInt(dateArr[1]) : 0;
        int day = dateArr.length > 2 ? parseInt(dateArr[2]) : 0;
        String[] timeArr = time.split(":");
        int hour = timeArr.length > 0 ? parseInt(timeArr[0]) : 0;
        int min = timeArr.length > 1 ? parseInt(timeArr[1]) : 0;
        int sec = timeArr.length > 2 ? parseInt(timeArr[2]) : 0;
        ca.setIranianDate(year, mont, day, hour, min, sec);
        return parseDateWithTime(ca.getGregorianDate2WithTime());
    }

    public static Date parseDateWithTime(String str) {
        SimpleDateFormat pd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            return pd.parse(str);
        } catch (Exception var3) {
            return new Date();
        }
    }

    public static Date parseDateWithTimeFromTS(Object obj) {
        return parseDateWithTimeFromTS(parseString(obj));
    }

    public static Date parseDateWithTimeFromTS(String str) {
        SimpleDateFormat pd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            Date date = new Date(parseLong(str));
            return parseDateWithTime(pd.format(date));
        } catch (Exception var3) {
            return new Date();
        }
    }

    public static Double getJavaVersion() {
        String version = parseString(System.getProperty("java.version"));
        int pos = version.indexOf(46);
        pos = version.indexOf(46, pos + 1);
        return Double.parseDouble(version.substring(0, pos));
    }

    static String encodeUTF8(String text, String source_encoding) {
        try {
            Charset UTF8_CHARSET = Charset.forName("UTF-8");
            Charset src_CHARSET = Charset.forName(source_encoding);
            return new String(text.getBytes(src_CHARSET), UTF8_CHARSET);
        } catch (Exception var4) {
            log.error("encodeUTF8", var4);
            return text;
        }
    }

    static String convertEncodingFromTo(String text, String from_encoding, String to_encoding) {
        try {
            return new String(text.getBytes(Charset.forName(from_encoding)), Charset.forName(to_encoding));
        } catch (Exception var4) {
            log.error("convertEncodingFromTo", var4);
            return text;
        }
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

    public static Date addValuetoDate(Date myDate, int calendar_field, int amount) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(myDate);
        cal.add(calendar_field, amount);
        return cal.getTime();
    }

    public static int getDayOfWeek(Date myDate) {
        Calendar cal = Calendar.getInstance();
        if (myDate != null) {
            cal.setTime(myDate);
        }

        int dayOfWeek = cal.get(7);
        if (dayOfWeek == 7) {
            dayOfWeek = 0;
        }

        return dayOfWeek;
    }

    public static String removeBadCharacterFromMobielNumber(String str) {
        str = str.replaceAll("-", "");
        str = str.replaceAll(" ", "");
        str = str.replaceAll("\\(", "").replaceAll("\\)", "");
        return str;
    }

    public static String getDateTimeString(String format, Date date) {
        return (new SimpleDateFormat(format)).format(date);
    }

    public static boolean isValidIMEI(String imei) {
        int[] ints = new int[imei.length()];

        int sum;
        for(sum = 0; sum < imei.length(); ++sum) {
            ints[sum] = parseInt(imei.substring(sum, sum + 1));
        }

        for(sum = ints.length - 2; sum >= 0; sum -= 2) {
            int j = ints[sum];
            j *= 2;
            if (j > 9) {
                j = j % 10 + 1;
            }

            ints[sum] = j;
        }

        sum = 0;
        int[] var7 = ints;
        int var4 = ints.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            int anInt = var7[var5];
            sum += anInt;
        }

        return sum % 10 == 0;
    }

    public static int parseInt(Object s) {
        try {
            return Integer.parseInt(parseString(s));
        } catch (Exception var2) {
            return 0;
        }
    }

    public static boolean saveMultipartFileToDisk(MultipartFile multipartFile, String savetofilepath) {
        try {
            InputStream inputStream = multipartFile.getInputStream();
            FileOutputStream outputStream = new FileOutputStream(savetofilepath);
            return saveFileToDisk(inputStream, outputStream);
        } catch (Exception var4) {
            log.error("saveMultipartFileToDisk", var4);
            return false;
        }
    }

    public static boolean saveFileToDisk(InputStream inputStream, OutputStream outputStream) {
        try {
            int readBytes = 0;
            byte[] buffer = new byte[10000];

//            int readBytes;
            while((readBytes = inputStream.read(buffer, 0, 10000)) != -1) {
                outputStream.write(buffer, 0, readBytes);
            }

            outputStream.close();
            inputStream.close();
            return true;
        } catch (Exception var4) {
            log.error("saveFileToDisk", var4);
            return false;
        }
    }

    public static String printStackTraceToString(Exception ex) {
        if (ex == null) {
            return "";
        } else {
            StringWriter errors = new StringWriter();
            ex.printStackTrace(new PrintWriter(errors));
            return errors.toString();
        }
    }

    public static long parseLong(Object s) {
        try {
            return Long.parseLong(parseString(s));
        } catch (Exception var2) {
            return 0L;
        }
    }

    public static String urldecodeNullable(String url) {
        try {
            return url == null ? null : URLDecoder.decode(parseString(url), "UTF-8");
        } catch (Exception var2) {
            return url;
        }
    }

    public static String urldecode(String url) {
        try {
            return URLDecoder.decode(parseString(url), "UTF-8");
        } catch (Exception var2) {
            return url;
        }
    }

    public static String urlencode(String url) {
        try {
            return URLEncoder.encode(parseString(url), "UTF-8");
        } catch (Exception var2) {
            return parseString(url);
        }
    }

    public static Document parseXmlFile(String in) {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            InputSource is = new InputSource(new StringReader(in));
            return db.parse(is);
        } catch (Exception var4) {
            log.error("parseXmlFile", var4);
            return null;
        }
    }

    public static Map<String, String> createMapFromNode(Node node) {
        Map<String, String> map = new HashMap();
        NodeList nodeList = node.getChildNodes();

        for(int i = 0; i < nodeList.getLength(); ++i) {
            Node currentNode = nodeList.item(i);
            if (currentNode.hasAttributes()) {
                for(int j = 0; j < currentNode.getAttributes().getLength(); ++j) {
                    Node item = currentNode.getAttributes().item(i);
                    map.put(item.getNodeName(), item.getTextContent());
                }
            }

            if (node.getFirstChild() != null && node.getFirstChild().getNodeType() == 1) {
                map.putAll(createMapFromNode(currentNode));
            } else if (node.getFirstChild().getNodeType() == 3) {
                map.put(node.getNodeName(), node.getTextContent());
            }
        }

        return map;
    }

    public static boolean parseBoolean(String s) {
        boolean result = false;

        try {
            result = Boolean.parseBoolean(s);
            if (s != null && !result) {
                double aDouble = parseDouble(s);
                if (aDouble >= 1.0D) {
                    result = true;
                }
            }
        } catch (Exception var4) {
        }

        return result;
    }

    public static boolean parseBoolean(Integer i) {
        try {
            return i == 1;
        } catch (Exception var2) {
            return false;
        }
    }

    public static boolean parseBoolean(Object o) {
        boolean result = false;

        try {
            if (o == null) {
                result = false;
            } else {
                String s = null;
                if (o instanceof String) {
                    s = (String)o;
                } else {
                    s = parseString(o);
                }

                result = parseBoolean(s);
            }
        } catch (Exception var3) {
        }

        return result;
    }

    public static double angleFromCoordinate(double lat1, double long1, double lat2, double long2) {
        double dLon = long2 - long1;
        double y = Math.sin(dLon) * Math.cos(lat2);
        double x = Math.cos(lat1) * Math.sin(lat2) - Math.sin(lat1) * Math.cos(lat2) * Math.cos(dLon);
        double brng = Math.atan2(y, x);
        brng = Math.toDegrees(brng);
        brng = (brng + 360.0D) % 360.0D;
        brng = 360.0D - brng;
        return brng;
    }

    public static int randInt(int min, int max) {
        Random rand = new Random();
        return rand.nextInt(max - min + 1) + min;
    }

    public static double parseDouble(String s) {
        try {
            return Double.parseDouble(s);
        } catch (Exception var2) {
            return 0.0D;
        }
    }

    public static double round(double number, int multiple) {
        double result = 0.0D;
        double remain = number % (double)multiple;
        if (remain != 0.0D) {
            int extraOne = 0;
            if (remain * 2.0D >= (double)multiple) {
                extraOne = 1;
            }

            long division = (long)(number / (double)multiple + (double)extraOne);
            result = (double)(division * (long)multiple);
        } else {
            result = Math.max((double)multiple, number);
        }

        return result;
    }

    public static String getDateByLanguageWithTime(long date, String lang) {
        return getDateByLanguageWithTime(new Date(date), lang);
    }

    public static String getDateByLanguageWithTime(Date date, String lang) {
        if (date == null) {
            return "";
        } else {
            lang = parseString(lang);
            if (lang.contains("fa")) {
                return convertDate2PersianDateWithTime(date);
            } else if (lang.contains("en")) {
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                return dateFormat.format(date);
            } else {
                return "";
            }
        }
    }

    public static String convertDate2PersianDateWithTime(Date date) {
        CalendarTool ca = new CalendarTool(date);
        return ca.getIranianDateTime2();
    }

    public static String getDateByLanguageFromPersian(Date date, String lang) {
        if (date == null) {
            return "";
        } else {
            lang = parseString(lang);
            new CalendarTool(date);
            if (lang.contains("fa")) {
                return convertDate2PersianDateWithTime(date);
            } else if (lang.contains("en")) {
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                return dateFormat.format(date);
            } else {
                return "";
            }
        }
    }

    public static String getDateByLanguage(long date, String lang) {
        return getDateByLanguage(new Date(date), lang);
    }

    public static String getDateByLanguage(String date, String lang) {
        Date dateBirthDate = parseAnyValidDateToMiladi(date);
        if (dateBirthDate != null) {
            String dateByLanguage = getDateByLanguage(dateBirthDate, lang);
            if (!isEmpty(dateByLanguage)) {
                date = dateByLanguage;
            }
        }

        return date;
    }

    public static String getDateByLanguage(Date date, String lang) {
        if (date == null) {
            return "";
        } else {
            lang = parseString(lang);
            if (lang.contains("fa")) {
                return convertDate2PersianDate(date);
            } else if (lang.contains("en")) {
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                return dateFormat.format(date);
            } else {
                return "";
            }
        }
    }

    public static String convertDate2PersianDate(Date date) {
        CalendarTool ca = new CalendarTool(date);
        return ca.getIranianDate2();
    }

    public static String convertDate2PersianDateOnly(Date date) {
        CalendarTool ca = new CalendarTool(date);
        return ca.getIranianDate();
    }

    public static String getExtension(String fileName) {
        fileName = parseString(fileName);
        String format = "";
        int index = fileName.lastIndexOf(".");
        if (index > 0) {
            format = fileName.substring(index + 1);
        }

        return format;
    }

    public static String replaceLast(String string, String substring, String replacement) {
        int index = string.lastIndexOf(substring);
        if (index == -1) {
            return string;
        } else {
            String var10000 = string.substring(0, index);
            return var10000 + replacement + string.substring(index + substring.length());
        }
    }

    public static boolean convertJpg2Png(String from, String to) {
        log.debug("convertJpg2Png from: " + from + " output: " + to);
        if (!getExtension(from).equalsIgnoreCase("jpg")) {
            log.debug("input must be a jpg file");
            return false;
        } else if (!getExtension(to).equalsIgnoreCase("png")) {
            log.debug("output must be a png file");
            return false;
        } else {
            File inputFile = new File(from);
            File outputFile = new File(to);

            try {
                InputStream is = new FileInputStream(inputFile);
                BufferedImage image = ImageIO.read(is);
                OutputStream os = new FileOutputStream(outputFile);
                ImageIO.write(image, "png", os);
                File newto = new File(to);
                return newto.exists();
            } catch (Exception var8) {
                log.error("convertJpg2Png", var8);
                return false;
            }
        }
    }

    public static String changeExtension(String path, String newExt) {
        path = parseString(path);
        int index = path.lastIndexOf(".");
        if (index > 0) {
            String var10000 = path.substring(0, index + 1);
            path = var10000 + newExt;
        }

        return path;
    }

    public static String removeExtension(String path) {
        path = parseString(path);
        int index = path.lastIndexOf(".");
        if (index > 0) {
            path = path.substring(0, index);
        }

        return path;
    }

    public static String addCommas(long maindigits) {
        String digits = parseString(maindigits);
        String result = digits;
        if (digits.length() <= 3) {
            return digits;
        } else {
            for(int i = 0; i < (digits.length() - 1) / 3; ++i) {
                int commaPos = digits.length() - 3 - 3 * i;
                String var10000 = result.substring(0, commaPos);
                result = var10000 + "," + result.substring(commaPos);
            }

            return result;
        }
    }

    public static int countLines(String str) {
        String[] lines = str.split("\r\n|\r|\n");
        return lines.length;
    }

    public static String randomString(int len) {
        StringBuilder sb = new StringBuilder(len);

        for(int i = 0; i < len; ++i) {
            sb.append("0123456789ABCDEGHIJKLMONPQRSTUVWXYZabcdefghijklomnpqrstuvwsyz".charAt(rnd.nextInt("0123456789ABCDEGHIJKLMONPQRSTUVWXYZabcdefghijklomnpqrstuvwsyz".length())));
        }

        return sb.toString();
    }

    public static String randomStringFrom(String fromAB, int len) {
        StringBuilder sb = new StringBuilder(len);

        for(int i = 0; i < len; ++i) {
            sb.append(fromAB.charAt(rnd.nextInt(fromAB.length())));
        }

        return sb.toString();
    }

    public static long randomLong(long min, long max) {
        return (long)((double)min + rnd.nextDouble() * (double)(max - min));
    }

    public static long randomLong(int length) {
        long low = (long)Math.pow(10.0D, (double)(length - 1));
        long high = (long)Math.pow(10.0D, (double)length);
        return randomLong(low, high);
    }

    public static String removeQueryStringFromPath(String path) {
        if (path != null && !path.equals("")) {
            return path.contains("?") ? path.substring(0, path.lastIndexOf(63)) : path;
        } else {
            return "";
        }
    }

    public static String getPathFromFileName(String path) {
        if (path != null && !path.equals("")) {
            return !path.contains("/") ? "" : path.substring(0, path.lastIndexOf(47));
        } else {
            return "";
        }
    }

    public static String getFileNameFromPath(String path) {
        int lastIndexOfSlash = path.lastIndexOf(47);
        int lastIndexOfQmark = path.lastIndexOf(63);
        String filename = path;
        if (lastIndexOfSlash > -1) {
            filename = path.substring(lastIndexOfSlash + 1);
        }

        if (lastIndexOfQmark > -1 && lastIndexOfQmark > lastIndexOfSlash) {
            filename = filename.substring(0, lastIndexOfQmark);
        }

        return filename;
    }

    public static String getFilePathWithoutExtension(String path) {
        return path.substring(0, path.lastIndexOf(46));
    }

    private static boolean deleteFolder(File file) throws IOException {
        if (!file.isDirectory()) {
            return file.delete();
        } else if (file.list().length == 0) {
            return file.delete();
        } else {
            String[] files = file.list();
            String[] var2 = files;
            int var3 = files.length;

            for(int var4 = 0; var4 < var3; ++var4) {
                String temp = var2[var4];
                File fileDelete = new File(file, temp);
                deleteFolder(fileDelete);
            }

            return file.list().length == 0 ? file.delete() : false;
        }
    }

    public static String convertMapString2QueryString(Map<String, String> parameters) {
        StringBuilder ret = new StringBuilder();
        Iterator var2 = parameters.keySet().iterator();

        while(var2.hasNext()) {
            String key = (String)var2.next();
            if (parseString(key).trim().equals("")) {
                log.trace("empty key detected, skipping");
            } else {
                String val = (String)parameters.get(key);
                ret.append(key).append("=").append(val).append("&");
            }
        }

        if (ret.toString().endsWith("&")) {
            ret = new StringBuilder(ret.substring(0, ret.length() - 1));
        }

        return ret.toString();
    }

    public static boolean isHexNumber(String cadena) {
        try {
            long hexnumber = Long.parseLong(cadena, 16);
            return true;
        } catch (NumberFormatException var3) {
            return false;
        }
    }

    public static String normalizeTag(String tag) {
        String ret = parseString(tag).replaceAll("[^A-Za-z0-9ا-ی۰-۹\\s\\_]+", "").toLowerCase().trim();
        ret = ret.replaceAll("\\s+", "_");
        return ret;
    }

    public static String normalizeString(String str) {
        return parseString(str).replaceAll("[^\\p{L}\\p{Z}\\s\\d_]", "");
    }

    public static String normalizeStringFilename(String str) {
        String extension = getExtension(str);
        str = removeExtension(str);
        String ret = parseString(str).replaceAll("[^\\p{L}\\p{Z}\\s\\d_]", "");
        if (!isEmpty(extension)) {
            ret = ret + "." + extension;
        }

        return ret;
    }

    private static void sleep(int i) {
        try {
            Thread.sleep((long)i);
        } catch (InterruptedException var2) {
            log.error("sleep", var2);
        }

    }

    public static void writeFile(String path, String content) {
        File file = new File(path);

        try {
            if (!file.exists()) {
                boolean var3 = file.createNewFile();
            }

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(content);
            bw.close();
        } catch (Exception var5) {
            log.error("writeFile", var5);
        }

    }

    public static String detectClientServiceName(String uri) {
        String f = "/services/client/";
        int fidx = uri.indexOf(f);
        if (fidx == -1) {
            return "";
        } else {
            fidx += f.length();
            String tmpuri = uri.substring(fidx);
            fidx = tmpuri.indexOf("/");
            return fidx == -1 ? tmpuri : tmpuri.substring(0, fidx);
        }
    }

    public static String detectServiceName(String uri) {
        String f = "/services/";
        int fidx = uri.indexOf(f);
        if (fidx == -1) {
            return "";
        } else {
            fidx += f.length();
            String tmpuri = uri.substring(fidx);
            return f + tmpuri;
        }
    }

    public static String detectServiceNameAfterTextBeforeSlash(String uri, String findAfterstring) {
        int fidx = uri.indexOf(findAfterstring);
        if (fidx == -1) {
            return "";
        } else {
            fidx += findAfterstring.length();
            String tmpuri = uri.substring(fidx);
            fidx = tmpuri.indexOf("/");
            return fidx == -1 ? tmpuri : tmpuri.substring(0, fidx);
        }
    }

    public static boolean isVersioningString(String value) {
        value = parseString(value);

        for(int i = 0; i < value.length(); ++i) {
            char c = value.charAt(i);
            if (i == 0) {
                if (c != 'v') {
                    return false;
                }
            } else if (c < '0' || c > '9') {
                return false;
            }
        }

        return true;
    }

    public static String detectB2bMbassGwServiceName(String uri, String apiSection) {
        uri = uri.replaceAll("/v./", "/");
        String f = "/services/" + apiSection + "/mbaas/gateway/";
        int fidx = uri.indexOf(f);
        if (fidx == -1) {
            return "";
        } else {
            fidx += f.length();
            String tmpuri = uri.substring(fidx);
            fidx = tmpuri.indexOf("/");
            if (fidx == -1) {
                return tmpuri;
            } else {
                int fidx2 = tmpuri.indexOf("/", fidx + 1);
                if (fidx2 != -1) {
                    fidx = fidx2;
                } else if (tmpuri.length() > fidx) {
                    fidx = tmpuri.length();
                }

                return tmpuri.substring(0, fidx);
            }
        }
    }

    public static long findmidnightMilisec() {
        Calendar date = new GregorianCalendar();
        date.set(11, 0);
        date.set(12, 0);
        date.set(13, 1);
        date.set(14, 0);
        return date.getTimeInMillis();
    }

    public static String getRandomUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static String getRandomId() {
        try {
            return IdHelper.snowflake();
        } catch (Exception var1) {
            log.error("getRandomSnowFlake", var1);
            return IdHelper.uuidNum();
        }
    }

    public static long getRandomLongSnowFlake() {
        try {
            return Long.parseLong(IdHelper.snowflake());
        } catch (Exception var3) {
            log.error("getRandomSnowFlake", var3);
            String uuid = IdHelper.uuid32();
            long longValue = (new BigInteger(uuid, 16)).longValue();
            return Math.abs(longValue);
        }
    }

    public static String getRandomStringSnowFlake() {
        try {
            return IdHelper.snowflake();
        } catch (Exception var3) {
            log.error("getRandomSnowFlake", var3);
            String uuid = IdHelper.uuid32();
            long longValue = (new BigInteger(uuid, 16)).longValue();
            return String.valueOf(Math.abs(longValue));
        }
    }

    public static String convertDate2PersianDateWithTime1(Date date) {
        CalendarTool ca = new CalendarTool(date);
        return ca.getIranianDateTime();
    }

    public static Date removeTime(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(11, 0);
        cal.set(12, 0);
        cal.set(13, 0);
        cal.set(14, 0);
        return cal.getTime();
    }

    public static Date removeTimeGMT(Date date) {
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        cal.setTime(date);
        cal.set(11, 0);
        cal.set(12, 0);
        cal.set(13, 0);
        cal.set(14, 0);
        return cal.getTime();
    }

    public static String getBundleNumber(long number, String lang) {
        return getBundleNumber(parseString(number), lang);
    }

    public static String getBundleNumber(String number, String lang) {
        char[] arabicChars = new char[]{'۰', '۱', '۲', '۳', '۴', '۵', '۶', '۷', '۸', '۹'};
        char[] englishChars = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        StringBuilder builder = new StringBuilder();

        for(int i = 0; i < number.length(); ++i) {
            if (lang.equalsIgnoreCase("en")) {
                if (Character.isDigit(number.charAt(i))) {
                    builder.append(englishChars[number.charAt(i) - 48]);
                } else {
                    builder.append(number.charAt(i));
                }
            } else if (Character.isDigit(number.charAt(i))) {
                builder.append(arabicChars[number.charAt(i) - 48]);
            } else {
                builder.append(number.charAt(i));
            }
        }

        return builder.toString();
    }

    public static String parseException(Exception ex) {
        String result = ex.getMessage();

        try {
            StackTraceElement[] stackTrace = ex.getStackTrace();
            String strlog = ex.toString() + "_";
            StackTraceElement[] var4 = stackTrace;
            int var5 = stackTrace.length;

            for(int var6 = 0; var6 < var5; ++var6) {
                StackTraceElement ste = var4[var6];
                strlog = strlog + ste.toString() + "_";
            }

            result = strlog;
        } catch (Exception var8) {
        }

        return result;
    }

    public static String addSlash(String path) {
        path = parseString(path);
        if (!path.endsWith("/")) {
            path = path + "/";
        }

        return path;
    }

    public static String addPathSeparator(String path) {
        path = parseString(path);
        if (!path.endsWith(File.separator)) {
            path = path + File.separator;
        }

        return path;
    }

    public static String removeTrailingSlash(String path) {
        path = parseString(path);
        if (isEmpty(path)) {
            return "";
        } else {
            StringBuilder str = new StringBuilder(path);
            int index = str.length() - 1;
            if (str.charAt(index) == '/') {
                str.deleteCharAt(index);
            }

            return str.toString();
        }
    }

    public static boolean tryToDeleteFile(File source) {
        try {
            return source.exists() && source.delete();
        } catch (Exception var2) {
            log.error("tryToDeleteFile", var2);
            return false;
        }
    }

    public static String makeDir(String path) {
        path = addSlash(path);

        try {
            File file = new File(path);
            if (!file.exists()) {
                boolean var2 = file.mkdirs();
            }
        } catch (Exception var3) {
            log.error("makeDir", var3);
        }

        return path;
    }

    public static String makeDirFromFilePath(String path) {
        try {
            if (Paths.get(path).getParent() != null) {
                Files.createDirectories(Paths.get(path).getParent());
            }
        } catch (Exception var2) {
            log.error("makeDirFromFilePath", var2);
        }

        return path;
    }

    public static String makeDirFromDirPath(String path) {
        try {
            Files.createDirectories(Paths.get(path));
        } catch (Exception var2) {
            log.error("makeDirFromDirPath", var2);
        }

        return path;
    }

    public static String parseStringWithValidateDB(String str, int len) {
        return parseStringWithValidateDB(str, len, false);
    }

    public static String parseStringWithValidateDB(String str, int len, boolean nullable) {
        if (str == null) {
            if (nullable) {
                return "NULL";
            }

            str = "";
        }

        str = sqlQuote(str);
        return str.length() > len ? str.substring(0, len) : str;
    }

    public static String sqlQuote(String s) {
        return s == null ? null : s.replaceAll("'", "''").replace("\\", "\\\\");
    }

    public static String parseStringWithValidateDB(Object str) {
        return sqlQuote(parseString(str));
    }

    public static String parseStringWithValidateDB(String str) {
        return sqlQuote(str);
    }

    public static boolean isValidIranianNationalCode(String input) {
        if (!input.matches("^\\d{10}$")) {
            return false;
        } else {
            int check = Integer.parseInt(input.substring(9, 10));
            int sum = IntStream.range(0, 9).map((x) -> {
                return Integer.parseInt(input.substring(x, x + 1)) * (10 - x);
            }).sum() % 11;
            return sum < 2 && check == sum || sum >= 2 && check + sum == 11;
        }
    }

    public static void sleep(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException var3) {
        }

    }

    public static File convertMultipartFile2File(MultipartFile file) throws IOException {
        String prefix = file.getName();
        String suffix = getExtension(prefix);
        if (!isEmpty(suffix)) {
            suffix = "." + suffix;
        }

        File convFile = File.createTempFile(prefix, suffix);
        convFile.deleteOnExit();
        convFile.createNewFile();
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }

    public static Date getPreviousDay(Date date) {
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        cal.setTime(date);
        cal.set(11, 0);
        cal.set(12, 0);
        cal.set(13, 0);
        cal.set(14, 0);
        cal.add(6, -1);
        return cal.getTime();
    }

    public static String parseStringPersian(Object s) {
        return parseString(s).replace("الله", "اله").replace("ا...", "اله");
    }

    public static boolean equalNames(String name1, String name2) {
        name1 = parseStringPersian(name1).replace("آ", "ا").replace(" ", "");
        name2 = parseStringPersian(name2).replace("آ", "ا").replace(" ", "");
        return name1.equalsIgnoreCase(name2);
    }

    public static boolean jsonValidation(String s) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.readTree(s);
            return true;
        } catch (IOException var2) {
            return false;
        }
    }

    public static void leftShift(Object[] array, int n) {
        for(int shift = 0; shift < n; ++shift) {
            Object first = array[0];
            System.arraycopy(array, 1, array, 0, array.length - 1);
            array[array.length - 1] = first;
        }

    }

    public static void rightShift(Object[] array, int n) {
        for(int shift = 0; shift < n; ++shift) {
            Object last = array[array.length - 1];
            System.arraycopy(array, 0, array, 1, array.length - 1);
            array[0] = last;
        }

    }

    public static String toLowerCamelCase(String string) {
        if (string == null) {
            return null;
        } else if (string.isEmpty()) {
            return string;
        } else if (string.length() > 1 && Character.isUpperCase(string.charAt(1)) && Character.isUpperCase(string.charAt(0))) {
            return string;
        } else {
            char[] chars = string.toCharArray();
            chars[0] = Character.toLowerCase(chars[0]);
            return new String(chars);
        }
    }

}
