package com.qianlong.libary.utils;

import android.text.format.Time;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;

/**
 * 字符串处理 帮助类
 */
public final class STD {
    /**
     * 从一个字符串中查找参数，格式：name1:1111|name2:2222|...
     */
    public final static String GetPara(String src, String para, char s, char e) {
        if (src == null) {
            return "";
        }
        int len = para.length();

        int start = src.indexOf(para);
        if (start >= 0) {
            if (start > 0) // 非第一个字符
            {
                if (src.charAt(start - 1) != e) // 假设没有空格
                {
                    return "";
                }
            }

            start += len;
            if (src.charAt(start) != s) // 不是属性名
            {
                return "";
            }

            start++;
            int end = start;
            int srclen = src.length();
            while (end < srclen) {
                if (src.charAt(end) == e) {
                    break;
                }
                end++;
            }
            if (end > start) {
                return src.substring(start, end);
            }
        }

        return "";
    }

    public final static int GetParaInt(String src, String para, char s, char e) {
        int value = 0;
        try {
            value = Integer.parseInt(GetPara(src, para, s, e));
        } catch (NumberFormatException err) {
        }
        return value;
    }

    // 从一个字符串中查找子字串，以ch为分隔符，查找第num个子字串，从1开始
    // 如：||股票买卖|5800|200|55555555|000001|深发展A|29.00|200|
    // 是以'|'分隔符
    public final static String GetValue(String src, int num, char ch) {
        return GetValue(src, num, ch, '\0');
    }

    public final static String GetValue(String src, int num, char ch,
                                        char EndChar) {
        // int tt = num;
        int srclen = src.length();
        int start = 0;

        // 查找起始位置
        if (num > 1) {
            while (start < srclen) {
                char c = src.charAt(start);
                start++;
                if (c == '\n' || c == EndChar) {
                    break;
                }
                if (c == ch) {
                    num--;
                    if (num == 1) {
                        break;
                    }
                }
            }
        }

        // 查找结束位置
        int end = start;
        while (end < srclen)
        // while (*src!=0 && *src!='\n' && *src!=ch && size > 1)
        {
            char c = src.charAt(end);
            if (c == '\n' || c == EndChar || c == ch) {
                break;
            }
            end++;
        }

        while (start < end) {
            if (src.charAt(start) != ' ') {
                break;
            }
            start++;
        }

        end--;
        while (end > start) {
            if (src.charAt(end) != ' ') {
                break;
            }
            end--;
        }

        end++;
        if (start < end) {
            return src.substring(start, end);
        } else {
            return "";
        }
    }

    public final static int GetValueInt(String src, int num, char ch) {
        return GetValueInt(src, num, ch, '\0');
    }

    public final static int GetValueInt(String src, int num, char ch,
                                        char EndChar) {
        int value = 0;
        try {
            value = Integer.parseInt(GetValue(src, num, ch, EndChar));
        } catch (NumberFormatException e) {
            int i = 0;
        }
        return value;
    }

    public final static int GetValueInt(String src, int num, char ch, int def) {
        return GetValueInt(src, num, ch, '\0', def);
    }

    public final static int GetValueInt(String src, int num, char ch,
                                        char EndChar, int def) {
        int value = def;
        try {
            value = Integer.parseInt(GetValue(src, num, ch, EndChar));
        } catch (NumberFormatException e) {
        }
        return value;
    }

    public static final String getTimeSringhhmmss(StringBuffer str, int time) // 格式%02d:%02d:%02d
    {
        return getTimeSringhhmmss(str, time / 10000, time / 100 % 100,
                time % 100);
    }

    public static final String getTimeSringhhmmss(StringBuffer str, int h,
                                                  int m, int s) // 格式%02d:%02d:%02d
    {
        str.delete(0, str.length());
        str.append(h + 100);
        str.deleteCharAt(0);
        str.append(m + 100);
        str.setCharAt(2, ':');
        str.append(s + 100);
        str.setCharAt(5, ':');
        return str.toString();
    }

    public static final String getTimeSringddhhmm(int date, int time) // 格式%02d%02d%02d
    {
        StringBuffer str = new StringBuffer();
        str.append(time / 60 + 100);
        str.deleteCharAt(0);
        str.append(time % 60 + 100);
        str.deleteCharAt(2);
        str.insert(0, date % 100 + 100);
        str.deleteCharAt(0);
        return str.toString();
    }

    public static final String getTimeSringhhmm(int time) // 格式%02d:%02d
    {
        StringBuffer str = new StringBuffer();
        str.append(time / 60 + 100);
        str.deleteCharAt(0);
        str.append(time % 60 + 100);
        str.setCharAt(2, ':');
        return str.toString();
    }

    public static final String getTimeSringyyyymmdd(int date) // 格式%04d/%02d/%02d
    {
        StringBuffer str = new StringBuffer();
        str.append(date / 10000);
        getTimeSringyyyymmdd(str, date);
        return str.toString();
    }

    public static final void getTimeSringyyyymmdd(StringBuffer str, int date) // 格式%04d/%02d/%02d
    {
        str.append(date / 10000);
        int len = str.length();
        str.append(date / 100 % 100 + 100);
        str.setCharAt(len, '/');
        len = str.length();
        str.append(date % 100 + 100);
        str.setCharAt(len, '/');
    }

    public static final String getDateSringmmdd(int date) // 格式%02d%02d
    {
        StringBuffer str = new StringBuffer();
        str.append(date % 10000 + 10000);
        str.deleteCharAt(0);
        return str.toString();
    }

    public static long my_int_times(long value, int times) {
        if (times <= 1) {
            return value;
        }

        if (value < 0) {
            return (value - times / 2) / times;
        } else {
            return (value + times / 2) / times;
        }
    }

    /**
     * String to Unicode Bytes
     */
    public static byte[] str2unicode(String src) {
        int len = src.length();
        byte[] unicode = new byte[len << 1];
        int offset = 0;
        char[] ss = src.toCharArray();
        for (int i = 0; i < ss.length; i++) {
            ByteUtil.putChar(unicode, offset, ss[i]);
            offset += 2;
        }
        return unicode;
    }

    /**
     * 拷贝byte数据到char
     *
     * @param start dst的起始位置
     * @param len   src的长度
     */
    public static int strcpy(char[] dst, int start, byte[] src, int len) {
        len = Math.min(dst.length - start, len);
        for (int i = 0; i < len; i++) {
            char c = (char) src[i];
            dst[start + i] = c;
            if (c == 0) {
                return i;
            }
        }
        return len;
    }

    /**
     * 拷贝byte数据到char,byte为unicode格式
     */
    public static int strcpy(char[] dst, int start, byte[] src, int offset,
                             int len) {
        len = Math.min(dst.length - start, len);
        for (int i = 0; i < len; i++) {
            char c = ByteUtil.getChar(src, offset + (i << 1));
            dst[start + i] = c;
            if (c == 0) {
                return i;
            }
        }
        return len;
    }

    public static int dicstrcpy(char[] dst, char[] src) {
        int num = Math.min(dst.length - 1, src.length);
        for (int i = 0; i < num; i++) {
            dst[i] = src[i];
            if (src[i] == 0) {
                return i;
            }
        }
        dst[num] = 0;
        return num;
    }

    public static int wstrlen(char[] str, int maxlen) {
        int num = str.length;
        for (int i = 0; i < num && i + 1 < num; i++) {
            if (str[i] == 0) {
                return i;
            }
            if (str[i] == 32) {
                return i;
            }
        }
        return num;
    }

    public static String byte2HexStr(byte[] b) {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1)
                hs = hs + "0" + stmp;
            else
                hs = hs + stmp;
            // if (n<b.length-1) hs=hs+":";
        }
        return hs.toUpperCase();
    }

    public static void copyB2B(byte[] dic, byte[] src, int offset, int len) {
        byte strtttt[] = new byte[len];
        for (int x = 0; x < len; x++) {
            strtttt[x] = src[offset + x];
        }

        int z = 0;
        for (int i = 0; i < len; i += 2) {
            if (strtttt[i] == 32 && strtttt[i + 1] == 0)
                continue;
            if (strtttt[i] == 0 && strtttt[i + 1] == 0)
                continue;
            dic[z + 1] = strtttt[i];
            dic[z] = strtttt[i + 1];
            z += 2;
        }
    }

    public static String dicstrcpy(byte[] src, int offset, int len) {
        byte str[] = new byte[len];
        copyB2B(str, src, offset, len);
        try {
            //String ss = new String(str);
            String sss = new String(str, Charset.forName("Unicode"));
            sss = sss.trim();
            return sss;
        } catch (UnsupportedOperationException e) {

        }
        return "";
    }

    public static String strcpy(byte[] src, int offset, int len) {
        char str[] = new char[len];
        memset(str);
        len = strcpy(str, 0, src, offset, len);
        return new String(str, 0, len);
    }

    public static int strcpy(char[] dst, char[] src) {
        int num = Math.min(dst.length - 1, src.length);
        for (int i = 0; i < num; i++) {
            dst[i] = src[i];
            if (src[i] == 0) {
                return i;
            }
        }
        dst[num] = 0;
        return num;
    }

    public static int strcpy(char[] dst, int start, char[] src, int len) {
        len = Math.min(dst.length - start, len);
        for (int i = 0; i < len; i++) {
            dst[start + i] = src[i];
            if (src[i] == 0) {
                return i;
            }
        }
        return len;
    }

    public static int strcpy(char[] dst, String src) {
        char[] str = src.toCharArray();
        return strcpy(dst, str);
    }

    public static int bytecpy(byte[] dst, int offset, char[] src) {
        int num = Math.min(dst.length - offset - 1, src.length);
        for (int i = 0; i < num; i++) {
            dst[offset + i] = (byte) src[i];
            if (src[i] == 0) {
                return i;
            }
        }
        dst[offset + num] = 0;
        return num;
    }

    /**
     * 字节拷贝
     *
     * @param dst    目标数据
     * @param str    源数据
     * @param offset 原数据偏移
     * @param len    长度
     * @return
     */
    public static int bytecpy(byte[] dst, int start, byte[] str, int offset,
                              int len) {
        len = Math.min(str.length - offset, dst.length - start);
        for (int i = 0; i < len; i++) {
            dst[start + i] = str[offset + i];
            if (str[offset + i] == 0) {
                len = i;
                break;
            }
        }
        return len;
    }

    // 比较字符串
    // 分别返回-1,0,1
    public static int strcmp(char[] str1, char[] str2) {
        int num = Math.min(str1.length, str2.length);
        for (int i = 0; i < num; i++) {
            int s = str1[i] - str2[i];
            if (s < 0) {
                return -1;
            } else if (s > 0) {
                return 1;
            } else {
                if (str1[i] == 0) {
                    return 0;
                }
            }
        }
        if (num < str1.length) {
            if (str1[num] != 0) {
                return 1;
            }
        }
        if (num < str2.length) {
            if (str2[num] != 0) {
                return -1;
            }
        }
        return 0;
    }

    public static int strncmp(byte[] str1, byte[] str2, int len) {
        int num = len;// Math.min(str1.length, str2.length);
        for (int i = 0; i < num; i++) {
            int s = str1[i] - str2[i];
            if (s < 0) {
                return -1;
            } else if (s > 0) {
                return 1;
            } else {
                if (str1[i] == 0) {
                    return 0;
                }
            }
        }
        // if (num < str1.length)
        // {
        // if (str1[num] != 0)
        // {
        // return 1;
        // }
        // }
        // if (num < str2.length)
        // {
        // if (str2[num] != 0)
        // {
        // return -1;
        // }
        // }
        return 0;
    }

    public static int strcmp(String str1, String str2, int start, int len) {
        int num = Math.min(str1.length(), len);
        for (int i = 0; i < num; i++) {
            int s = str1.charAt(i) - str2.charAt(start + i);
            if (s < 0) {
                return -1;
            } else if (s > 0) {
                return 1;
            }
        }
        if (num < str1.length()) {
            return 1;
        }
        if (num < len) {
            return -1;
        }
        return 0;
    }

    // 比较字符串
    // 分别返回-1,0,1
    public static int strcmp(char[] str1, int start1, byte[] str2, int start2,
                             int num) {
        // int num = Math.min(str1.length, str2.length);
        int len1 = str1.length;
        int len2 = str2.length;
        for (int i = 0; i < num && start1 < len1 && start2 < len2; i++, start1++, start2++) {
            // byte t = (byte)str1[start1];
            int s = str1[start1] - str2[start2];
            // mobilestock.writelog(t + "," + str2[start2] + "," + s);
            if (s < 0) {
                return -1;
            } else if (s > 0) {
                return 1;
            } else {
                if (str1[start1] == 0) {
                    return 0;
                }
            }
        }
        if (start1 < len1) {
            if (str1[start1] != 0) {
                return 1;
            }
        }
        if (start2 < len1) {
            if (str2[start2] != 0) {
                return -1;
            }
        }
        return 0;
    }

    public static int strlen(char[] str) {
        int num = str.length;
        for (int i = 0; i < num; i++) {
            if (str[i] == 0) {
                return i;
            }
        }
        return num;
    }

    public static int strlen(byte[] str) {
        return strlen(str, 0, str.length);
    }

    public static int strlen(byte[] str, int offset, int len) {
        int num = Math.min(str.length - offset, len);

        for (int i = 0; i < num; i++) {
            // str[i+offset]);
            if (str[i + offset] == 0) {
                return i;
            }
        }
        return num;
    }

    public static void memcpy(int[] data, int[] src, int len) {
        memset(data);
        for (int i = 0; i < len; i++) {
            data[i] = src[i];
        }
    }

    public static void memcpy(byte[] data, byte[] src, int offset, int len) {
        memset(data, offset, data.length);
        for (int i = 0; i < len; i++) {
            data[i + offset] = src[i];
        }
    }

    public static void memcpy(byte[] data, byte[] src, int len) {
        memset(data);
        for (int i = 0; i < len; i++) {
            data[i] = src[i];
        }
    }

    public static void memset(char[] str) {
        int num = str.length;
        for (int i = 0; i < num; i++) {
            str[i] = 0;
        }
    }

    public static void memset(ArrayList<Integer> str) {
        str.clear();
    }

    public static void memset(byte[] str) {
        memset(str, 0, str.length);
    }

    public static void memset(byte[] str, int start, int len) {
        int num = Math.min(str.length - start, len);
        for (int i = 0; i < num; i++) {
            str[start + i] = 0;
        }
    }

    public static void memsetNew(int[] str, int start, int len) {
        int num = Math.min(str.length - start, len);
        for (int i = 0; i < num; i++) {
            str[start + i] = 0;
        }
    }

    public static void memset(int[] data) {
        int num = data.length;
        for (int i = 0; i < num; i++) {
            data[i] = 0;
        }
    }

    public static void memset(int[][] data) {
        int num = data.length;
        for (int i = 0; i < num; i++) {
            int num2 = data[i].length;
            for (int j = 0; j < num2; j++) {
                data[i][j] = 0;
            }
        }
    }

    public static int strchr(StringBuffer str, char c) {
        int len = str.length();
        for (int i = 0; i < len; i++) {
            if (str.charAt(i) == c) {
                return i;
            }
        }
        return -1;
    }

    public static int strchr(String str, int start, int len, char c) {
        for (int i = 0; i < len; i++) {
            if (str.charAt(start + i) == c) {
                return i + start;
            }
        }
        return -1;
    }

    //
    public static int getByteStringLen(byte[] str, int offset, int len) {
        for (int i = 0; i < len; i++) {
            if (str[offset + i] == 0) {
                len = i;
                break;
            }
        }
        return len;
    }

    /**
     * 字节匹配查找
     */
    public static int bytechr(byte[] str, int start, byte[] dst, int len) {
        int d_len = Math.min(STD.strlen(dst), len);
        int i = start, s_Index = 0, d_Index = 0, ll = len;
        while (str[i] != 0 && ll > 0) {
            if (len + start - i < d_len)
                break;
            if (str[i] == dst[0]) {
                s_Index = i + 1;
                d_Index = 1;
                while (d_Index < d_len) {
                    if (str[s_Index] != dst[d_Index]) {
                        break;
                    }
                    s_Index++;
                    d_Index++;
                }
                if (d_Index == d_len) {
                    return i;
                }
            }
            i++;
            ll--;
            if (i >= str.length) {
                break;
            }
        }
        return -1;
    }

    // public static final int NUMBER_POUND[] = {10, 100, 1000, 10000, 100000,
    // 1000000, 10000000, 100000000, 1000000000};
    public static final long NUMBER_POUND_long[] = {10, 100, 1000, 10000,
            100000, 1000000, 10000000, 100000000, 1000000000L, 10000000000L,
            100000000000L, 1000000000000L, 10000000000000L, 100000000000000L,
            1000000000000000L, 10000000000000000L, 100000000000000000L,
            1000000000000000000L};

    public static final long getNumberPound(int num) {
        if (num <= 0) {
            return 1;
        } else if (num <= NUMBER_POUND_long.length) {
            return NUMBER_POUND_long[num - 1];
        } else {
            return NUMBER_POUND_long[NUMBER_POUND_long.length - 1];
        }
    }

    public static final int getDataLength(long data) {
        if (data < 0) {
            data = -data;
        }
        for (int i = 0; i < NUMBER_POUND_long.length; i++) {
            if (data < NUMBER_POUND_long[i]) {
                return i + 1;
            }
        }
        return NUMBER_POUND_long.length;
    }

    public static final String getDateSringyyyymmdd(int date) // 格式yyyymmdd
    {
        StringBuffer str = new StringBuffer();
        str.append(date);
        return str.toString();
    }

    //
    public static final String getStringDateMinmmddhhmm(int date, int min) // 格式mmdd-hhmm
    {
        StringBuffer str = new StringBuffer();
        if (date < 1000)
            str.append('0');
        str.append(date);
        str.append('-');
        if (min < 1000)
            str.append('0');
        str.append(min);
        return str.toString();
    }

    public static final String getStringDateyymmddhhmmss(long date) // 格式yyyy-mm-dd
    // hh:mm:ss
    {
        int d = STD.getCurDate(date);
        int t = STD.getCurTime(date);
        return String.format("%04d-%02d-%02d %02d:%02d:%02d", d / 10000,
                d / 100 % 100, d % 100, t / 10000, t / 100 % 100, t % 100);
    }

    //
    public static final String getDateSring(int date) // 格式yymmdd
    {
        StringBuffer str = new StringBuffer();
        str.append(date % 1000000);
        return str.toString();
    }

    //
    public static final String getStringDateMin(int date, int min) // 格式ddhhmm
    {
        StringBuffer str = new StringBuffer();
        // if(date < 1000)
        // str.append('0');
        str.append(date % 100);
        // str.append('-');
        if (min < 1000)
            str.append('0');
        str.append(min);
        return str.toString();
    }

    public static final String getNumString(int num) {
        StringBuffer str = new StringBuffer();

        if (num < 10)
            str.append("0").append(num);
        else
            str.append(num);

        return str.toString();
    }

    // 空字符串数值校正, dotlen：小数点位数
    public static final String ValueString(String text, int dotlen) {
        StringBuffer str = new StringBuffer();
        if (text.length() <= 0) {
            if (dotlen == 0) {
                str.append("0");
                return str.toString();
            }
            str.append("0.");
            for (int i = 0; i < dotlen; i++) {
                str.append("0");
            }
            return str.toString();
        } else {
            return str.append(text).toString();
        }
    }

    public static final String LongtoString(long text) {
        StringBuffer str = new StringBuffer();
        return str.append(text).toString();
    }

    //
    public static final Float StringToValue(String text) {
        if (text.length() <= 0)
            text = "0.00";

        try {
            return Float.parseFloat(text);
        } catch (Exception e) {
            return 0.00f;
        }
    }

    //
    public static final double StringToDouble(String text) {
        if (text.length() <= 0)
            text = "0.00";
        return Double.parseDouble(text);
    }

    // 通过ch字符，把str解析成string的数组
    public static final String[] getStringBufferArray(String str, String ch) {
        String temp = str.replaceAll(ch, "");
        int l = str.length();
        int start = 0;
        int param = 0;
        int length = l - temp.length() + 1;

        if (0 >= length) {
            return new String[0];
        }

        String[] sbf = new String[length];

        for (int i = 0; i < l - 1; i++) {
            if (ch.charAt(0) == str.charAt(i)) {
                sbf[param++] = str.substring(start, i);
                start = i + 1;
            }
        }
        sbf[param] = str.substring(start);
        return sbf;
    }

    public static final String[] getStringBufferArray(String str) {
        return getStringBufferArray(str, " ");
    }

    public static final String[] getStringBufferArray(String str, String ch,
                                                      int length) {
        int l = str.length();
        int start = 0;
        int param = 0;
        String[] sbf = new String[length];
        for (int i = 0; i < l - 1; i++) {
            if (ch.charAt(0) == str.charAt(i)) {
                sbf[param++] = str.substring(start, i);
                start = i + 1;
                if (param == length)
                    return sbf;
            }
        }
        sbf[param] = str.substring(start);
        return sbf;
    }

    public static final String[] getStringBufferArray(String str, int length) {
        return getStringBufferArray(str, " ", length);
    }

    public static final long getCurDataTime(Date d) {

        int date = d.getYear() * 10000 + d.getMonth() * 100 + d.getDate();
        int time = d.getHours() * 10000 + d.getMinutes() * 100 + d.getSeconds();

        return (((long) date << 32) & 0xffffffff00000000L) + time;
    }

    // 获取当前数据时间， 格式：低四字节为时间(hhmmss)，高四字节为日期(yyyymmdd)
    public static final long getCurDataTime() {

        Time t = new Time("GMT+8");
        t.setToNow(); // 取得系统时间

        int date = t.year * 10000 + t.month * 100 + t.monthDay;
        int time = t.hour * 10000 + t.minute * 100 + t.second;

        return (((long) date << 32) & 0xffffffff00000000L) + time;
    }

    public static final long getDataTime(int date, int time) {

        // Time t = new Time("GMT+8");
        // t.setToNow(); // 取得系统时间
        //
        // int date = t.year * 10000 + t.month * 100 + t.monthDay;
        // int time = t.hour * 10000 + t.minute * 100 + t.second;

        return (((long) date << 32) & 0xffffffff00000000L) + time;
    }

    //
    public static final int getCurDate(long time) {
        int date = (int) (time >> 32);
        return date;
    }

    //
    public static final int getCurTime(long time) {
        int ret = (int) (time & 0xffffffff);
        return ret;
    }

}

