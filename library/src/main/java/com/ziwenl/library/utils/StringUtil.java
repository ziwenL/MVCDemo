package com.ziwenl.library.utils;

import android.content.ClipboardManager;
import android.content.Context;

import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description: 字符类型转换工具
 */
public class StringUtil {

    /**
     * 字符串是否为空
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        return (str == null || str.length() == 0);
    }

    public static String toString(Object obj) {
        if (obj == null)
            return "";
        else
            return obj.toString();
    }

    public static int toInt(Object obj) {
        if (obj == null || "".equals(obj) || "null".equals(obj)) {
            return -1;
        } else {
            return Integer.parseInt(obj.toString());
        }
    }

    public static short toShort(Object obj) {
        if (obj == null || "".equals(obj) || "null".equals(obj)) {
            return -1;
        } else {
            return Short.parseShort(obj.toString());
        }
    }

    public static int toCount(Object obj) {
        if (obj == null || "".equals(obj) || "null".equals(obj)) {
            return 0;
        } else {
            return Integer.parseInt(obj.toString());
        }
    }

    public static float toFloat(Object obj) {
        if (obj == null || "".equals(obj)) {
            return 0;
        } else {
            return Float.parseFloat(obj.toString());
        }
    }

    public static double toDouble(Object obj) {
        if (obj == null || "".equals(obj)) {
            return 0;
        } else {
            return Double.parseDouble(obj.toString());
        }
    }

    /**
     * 将对象转换成Long型空对象默认装换成0
     *
     * @param obj
     * @return
     */
    public static Long toLong(Object obj) {
        if (obj == null || "".equals(obj)) {
            return 0L;
        } else {
            return Long.parseLong(obj.toString());
        }
    }

    /**
     * 将对象转换成boolean类型,默认为false
     *
     * @param obj
     * @return
     */
    public static Boolean toBoolean(Object obj) {
        if (obj == null || "".equals(obj)) {
            return false;
        }
        return Boolean.valueOf(obj.toString());
    }

    public static boolean checkStr(String str) {
        boolean bool = true;
        if (str == null || "".equals(str.trim()))
            bool = false;
        return bool;
    }

    public static String buildFirstChar(String str) {
        if (str == null)
            return null;
        return str.substring(0, 1).toLowerCase() + str.substring(1);
    }

    public static double double2point(double ff) {
        int j = (int) Math.round(ff * 10000);
        double k = (double) j / 100.00;

        return k;
    }

    public String snumberFormat(double unm) {
        DecimalFormat df = new DecimalFormat("#.00");
        return df.format(unm);
    }

    public static String delSpaceString(String d) {
        String ret = "";
        if (d != null) {
            ret = d.trim();
        }
        return ret;
    }

    /**
     * 数据定长输出
     *
     * @param pattern 长度及其格式（例如：定长�?0位，不足则前面补零，那么pattern�?0000000000"�?
     * @param number
     * @return
     */
    public static String getDecimalFormat(String pattern, String number) {
        DecimalFormat myformat = new DecimalFormat();
        myformat.applyPattern(pattern);
        int num = Integer.parseInt(toInt(number) + "");
        if ((num + "").length() > pattern.length()) {
            String newNumber = (num + "").substring(0, pattern.length() - 1);
            num = Integer.parseInt(newNumber);
        }
        return myformat.format(num);
    }

    public static String formatDecimal(String pattern, int number) {
        DecimalFormat myformat = new DecimalFormat();
        myformat.applyPattern(pattern);
        if ((number + "").length() > pattern.length()) {
            String newNumber = (number + "").substring(0, pattern.length() - 1);
            number = Integer.parseInt(newNumber);
        }
        return myformat.format(number);
    }

    public static String bytesToHexString(byte[] src, int length) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || length <= 0) {
            return null;
        }
        for (int i = 0; i < length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    public static float max(float... values) {
        float max = 0;
        for (float item : values) {
            if (max == 0) {
                max = item;
            } else {
                max = Math.max(max, item);
            }
        }
        return max;
    }

    public static float min(float... values) {
        float min = 0;
        for (float item : values) {
            if (min == 0) {
                min = item;
            } else {
                if (item == 0) {
                    continue;
                }
                min = Math.min(min, item);
            }
        }
        return min;
    }

    public static int level(float value, float level[]) {
        for (int i = 0; i < level.length; i++) {
            if (value < level[i]) {
                return i;
            }
        }
        return level.length;
    }


    /**
     * @param @param  number
     * @param @return 设定文件
     * @return String 返回类型
     * @throws
     * @Title: topercent
     * @Description: 将double类型的数据改为百分数，默认显示两位小数
     */
    public static String toPercent(double number) {
        NumberFormat percentFormat = NumberFormat.getPercentInstance();
        percentFormat.setMinimumFractionDigits(2);
        return percentFormat.format(number);

    }

    public static String toNumberFormat(double number) {
        NumberFormat numberFormat = NumberFormat.getNumberInstance();
        return numberFormat.format(number);
    }

    public static boolean isMobile(String phoneNumber) {
        String regex = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";
        if (phoneNumber.length() != 11) {
            return false;
        } else {
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(phoneNumber);
            return m.matches();
        }
    }

    /**
     * 判断是否是字母+数字
     *
     * @param number
     * @return
     */
    public static boolean isDigitalAndAlphabet(String number) {
        Pattern p1 = Pattern.compile("[0-9]*$");
        Pattern p2 = Pattern.compile("^[A-Za-z]+$");
        if (p1.matcher(number).matches() || p2.matcher(number).matches()) {
            return false;
        }
        return true;
    }

    /**
     * 判断是否为数字
     *
     * @param number
     * @return
     */
    public static boolean isNumber(String number) {
        Pattern p1 = Pattern.compile("[0-9]*$");
        if (p1.matcher(number).matches()) {
            return true;
        }
        return false;
    }

    /**
     * 判断是否为字母
     *
     * @param number
     * @return
     */
    public static boolean isLetter(String number) {
        Pattern p2 = Pattern.compile("^[A-Za-z]+$");
        if (p2.matcher(number).matches()) {
            return true;
        }
        return false;
    }

    /**
     * @param @param  phone
     * @param @return 设定文件
     * @return String 返回类型
     * @throws
     * @Title: phone4Unknown
     * @Description: 将手机号中间4位变为****
     */
    public static String phone4Unknown(String phone) {
        try {
            return phone.substring(0, 3) + "****" + phone.substring(phone.length() - 4, phone.length());
        } catch (Exception e) {
            return phone;
        }
    }

    /**
     * 将数据的后len位置為****
     *
     * @param data
     * @param len
     * @return
     */
    public static String parseToLastUnknown(String data, int len) {
        int count = data.length();
        StringBuffer sb = new StringBuffer();
        if (count > len) {
            for (int i = 0; i < len; i++) {
                sb.append("*");
            }
            return data.substring(0, count - len) + sb.toString();
        } else {
            return data;
        }
    }

    /**
     * 将数据的startIndex - endIndex位置设为*****
     *
     * @param data
     * @param startIndex 起始位置(包含)
     * @param endIndex   结束位置(包含)
     *                   eg: String data=abcdefg;  startIndex = 1 endIndex = 3  parseToCenterUnknown(data,startIndex,endIndex)="a***efg"
     * @return
     */
    public static String parseToCenterUnknown(String data, int startIndex, int endIndex) {
        StringBuffer sb = new StringBuffer();
        int count = data.length();
        if (startIndex > count || endIndex > count || startIndex > endIndex) {
            return data;
        } else {
            for (int i = startIndex; i <= endIndex; i++) {
                sb.append("*");
            }
            return data.substring(0, startIndex) + sb.toString() + data.substring(endIndex + 1, count);
        }
    }

    /**
     * 保留前len位
     *
     * @param data
     * @param len
     * @return
     */
    public static String remainFirstWords(String data, int len) {
        int count = data.length();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < len; i++) {
            sb.append("*");
        }
        if (count < len) {
            return data;
        } else {
            return data.substring(0, count - len) + sb.toString();
        }
    }


    /**
     * @param @param  data
     * @param @return 设定文件
     * @return String 返回类型
     * @throws
     * @Title: to2Decimal
     * @Description: 保留两位小数
     */
    public static String to2Decimal(double data) {
        DecimalFormat df = new DecimalFormat();
        String style = "#,##0.00";// 定义要显示的数字的格式
        df.applyPattern(style);
        return df.format(data);
    }

    /**
     * 保留一位小数
     */
    public static String to1Decimal(double data) {
        DecimalFormat df = new DecimalFormat();
        String style = "#,###.#";// 定义要显示的数字的格式
        df.applyPattern(style);
        return df.format(data);
    }

    /**
     * @param @param  data
     * @param @return 设定文件
     * @return double 返回类型
     * @throws
     * @Title: to2Double
     * @Description: 四舍五入，保留两位小数，返回double
     */
    public static double to2Double(double data) {
        DecimalFormat df = new DecimalFormat();
        String style = "#.00";// 定义要显示的数字的格式
        df.applyPattern(style);
        return StringUtil.toDouble(df.format(data));

    }

    public static String toFormatLong(long data) {
        DecimalFormat df = new DecimalFormat();
        String style = "#,###";// 定义要显示的数字的格式
        df.applyPattern(style);
        return df.format(data);
    }

    public static String toHundred(long data) {
        int remainder = (int) (data % 100 / 10);
        if (remainder >= 5) {
            return String.valueOf(((data / 100) + 1) * 100);
        } else {
            return String.valueOf(data / 100 * 100);
        }

    }

    /**
     * @param @param  value
     * @param @return 设定文件
     * @return int 返回类型
     * @throws
     * @Title: getStringLength
     * @Description: 获取字符的长度（中文算2，英文算1）
     */
    public static int getStringLength(String value) {
        int valueLength = 0;
        String chinese = "[\u4e00-\u9fa5]";
        // 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1
        for (int i = 0; i < value.length(); i++) {
            // 获取一个字符
            String temp = value.substring(i, i + 1);
            // 判断是否为中文字符
            if (temp.matches(chinese)) {
                // 中文字符长度为1
                valueLength += 2;
            } else {
                // 其他字符长度为0.5
                valueLength += 1;
            }
        }
        return valueLength;
    }

    /**
     * MD5加密
     *
     * @param s
     * @return
     */
    public final static String MD5(String s) {
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        try {
            byte[] btInput = s.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 转换数字为中文
     *
     * @param s
     * @return
     */
    public static String convertDecimal2Text(String s) {
        int i = toInt(s);
        String[] strs = {"", "一", "二", "三", "四", "五", "六", "七", "八", "九", "十", "十一", "十二"};
        if (i > 0 && i < strs.length) {
            return strs[i];
        }
        return strs[0];
    }

    /**
     * 转换数字为星期
     *
     * @param s
     * @return
     */
    public static String decimalStr2WeekdayStr(String s) {
        s = s.replace("1", "周一")
                .replace("2", "周二")
                .replace("3", "周三")
                .replace("4", "周四")
                .replace("5", "周五")
                .replace("6", "周六")
                .replace("7", "周日")
                .replace(",", "，");
        return s;
    }


    /**
     * 将50%转为0.5之类的
     *
     * @param s
     * @return
     */
    public static float percentToFloat(String s) {
        float result = 0;
        if (s != null && !"".equals(s) && s.endsWith("%") && s.length() > 1) {
            result = toFloat(s.substring(0, s.length() - 1)) / 100.0f;
        }
        return result;
    }

    /**
     * <pre>
     * 将字符串从右至左每三位加一逗号
     * </pre>
     *
     * @param str 需要加逗号的字符串
     * @return 以从右至左每隔3位加一逗号显示
     */
    public static String displayWithComma(String str) {
        str = new StringBuffer(str).reverse().toString(); // 先将字符串颠倒顺序
        String str2 = "";

        int size = (str.length() % 3 == 0) ? (str.length() / 3) : (str.length() / 3 + 1); // 每三位取一长度

        /*
         * 比如把一段字符串分成n段,第n段可能不是三个数,有可能是一个或者两个,
         * 现将字符串分成两部分.一部分为前n-1段,第二部分为第n段.前n-1段，每一段加一",".而第n段直接取出即可
         */
        for (int i = 0; i < size - 1; i++) { // 前n-1段
            str2 += str.substring(i * 3, i * 3 + 3) + ",";
        }

        for (int i = size - 1; i < size; i++) { // 第n段
            str2 += str.substring(i * 3, str.length());
        }

        str2 = new StringBuffer(str2).reverse().toString();

        return str2;
    }


    /**
     * 超过千除以千加k返回
     *
     * @param i
     * @return
     */
    public static String formatConversion(int i) {
        String s;
        if (i >= 1000) {
            s = to2Decimal((double) ((float) i / (float) 1000)) + "k";
        } else {
            s = String.valueOf(i);
        }
        return s;
    }

    /**
     * 当数据的整数部分大于0时，保留一位小数点，当数据的整数为0时，只显示小数的前3位，并转换为整数
     * 用于距离显示
     *
     * @param value 距离
     */
    public static String converDistance(String value) {
        int intOfDistance = BigDecimalUtil.convertsToInt(StringUtil.toDouble(value));
        if (intOfDistance > 0) {
            // 如果整数部分不为零
            return StringUtil.to1Decimal(StringUtil.toDouble(value)) + "km";
        } else {
            // 如果整数部分为零
            int n = value.indexOf(".");
            String subString = value.substring(n + 1, value.length() < 4 ? value.length() : (n + 4));
            int intValue = isEmpty(subString) ? 0 : Integer.valueOf(subString);
            return StringUtil.toString(intValue) + "m";
        }
    }


    private static final double EARTH_RADIUS = 6378137.0;

    /**
     * 計算兩個經緯度間的距離
     *
     * @param longitude1
     * @param latitude1
     * @param longitude2
     * @param latitude2
     * @return 米
     */
    public static double getDistance(double longitude1, double latitude1,
                                     double longitude2, double latitude2) {
        double Lat1 = rad(latitude1);
        double Lat2 = rad(latitude2);
        double a = Lat1 - Lat2;
        double b = rad(longitude1) - rad(longitude2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
                + Math.cos(Lat1) * Math.cos(Lat2)
                * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 10000) / 10000;
        return s;
    }

    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }

    /**
     * 检测邮箱
     *
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        String regex = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
        return email.matches(regex);
    }

    /**
     * 复制文本内容到系统剪贴板
     */
    public static boolean copyString(Context context, String content) {
        // 从API11开始android推荐使用android.content.ClipboardManager
        // 为了兼容低版本我们这里使用旧版的android.text.ClipboardManager，虽然提示deprecated，但不影响使用。
        ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        // 将文本内容放到系统剪贴板里。
        if (cm != null) {
            cm.setText(content);
            return true;
        } else {
            return false;
        }
    }


    /**
     * 比较两个版本号的大小
     *
     * @param appVersion 当前版本号 1.0.1
     * @param newVersion 最新版本号 2.1.1
     * @return
     */
    public static boolean compareVersionNumber(String appVersion, String newVersion) {
        boolean isNeedUpdate = true;
        String[] appVersionArray = appVersion.split("\\.");
        String[] newVersionArray = newVersion.split("\\.");
        StringBuilder appVersionSb = new StringBuilder();
        StringBuilder newVersionSb = new StringBuilder();
        for (int i = 0; i < appVersionArray.length; i++) {
            String a, n;
            try {
                a = appVersionArray[i];
            } catch (Exception e) {
                a = "";
            }
            try {
                n = newVersionArray[i];
            } catch (Exception e) {
                n = "";
            }
            if (a.length() != n.length()) {
                if (a.length() > n.length()) {
                    StringBuilder needAdd = new StringBuilder();
                    for (int j = 1; j <= a.length(); j++) {
                        if (n.length() < j) {
                            needAdd.append("0");
                        }
                    }
                    n = n + needAdd.toString();
                } else {
                    StringBuilder needAdd = new StringBuilder();
                    for (int j = 1; j <= n.length(); j++) {
                        if (a.length() < j) {
                            needAdd.append("0");
                        }
                    }
                    a = a + needAdd.toString();
                }
            }
            appVersionSb.append(a);
            newVersionSb.append(n);
        }
        isNeedUpdate = Integer.valueOf(newVersionSb.toString()) > Integer.valueOf(appVersionSb.toString());
        return isNeedUpdate;
    }

    /**
     * 剔除html内容
     *
     * @param strHtml
     * @return
     */
    public static String StripHT(String strHtml) {
        String txtcontent = strHtml.replaceAll("</?[^>]+>", ""); //剔出<html>的标签
        txtcontent = txtcontent.replaceAll("<a>\\s*|\t|\r|\n</a>", "");//去除字符串中的空格,回车,换行符,制表符
        return txtcontent;
    }

    public static String timeConversion(long time) {
        String s;
        long nowTime = System.currentTimeMillis();
        long day = 86400000L;
        long twoDay = 172800000L;
        long threeDay = 259200000L;
        long year = 31536000000L;
        if (nowTime - time > year) {
            //其余：xxxx年xx月xx日 xx时xx分
            s = DateUtil.parseToString(time, "yyyy-MM-dd");
        } else if (nowTime - time > threeDay) {
            //1年内：xx月xx日 xx时xx分
            s = DateUtil.parseToString(time, "MM-dd");
        } else if (nowTime - time > twoDay) {
            //前天
            s = "前天";
        } else if (nowTime - time > day) {
            //昨天
            s = "昨天";
        } else {
            //今天
            s = "今天";
        }
        return s;
    }

    public static String timeConversion2(long time) {
        String s;
        long nowTime = System.currentTimeMillis();
        long day = 86400000L;
        long twoDay = 172800000L;
        long threeDay = 259200000L;
        long year = 31536000000L;
        if (nowTime - time > year) {
            //其余：xxxx年xx月xx日 xx时xx分
            s = DateUtil.parseToString(time, "yyyy-MM-dd");
        } else if (nowTime - time > threeDay) {
            //1年内：xx月xx日 xx时xx分
            s = DateUtil.parseToString(time, "MM-dd");
        } else if (nowTime - time > twoDay) {
            //前天
            s = "前天";
        } else if (nowTime - time > day) {
            //昨天
            s = "昨天";
        } else {
            //今天
            s = DateUtil.parseToString(time, "HH:mm");
        }
        return s;
    }

    /**
     * 数值转换
     * - 小于1000直接显示
     * - 小于10000显示 x k+
     * - 小于等于9999999显示 x w+
     * - 大于9999999显示 999w+
     *
     * @return
     */
    public static String numericalConversion(long number) {
        String s;
        if (number > 9999999) {
            s = "999w+";
        } else if (number > 10000) {
            s = number / 10000 + "w+";
        } else if (number > 1000) {
            s = number / 1000 + "k+";
        } else {
            s = String.valueOf(number);
        }
        return s;
    }

    /**
     * 超过最大长度,截取内容后面显示省略号
     *
     * @param content   内容
     * @param maxLength 最大长度
     * @return
     */
    public static String omitString(String content, int maxLength) {
        String outContent;
        if (content.length() > maxLength) {
            outContent = content.substring(0, maxLength) + "…";
        } else {
            outContent = content;
        }
        return outContent;
    }
}
