package com.qianlong.libary.utils;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;

/***
 * Java基本类型与byte数组之间相互转换
 *
 */
public class ByteUtil {
	private static final String TAG = ByteUtil.class.getSimpleName();
	
	/**获取byte[] 指定位置offset开始len个byte的有效byte长度 */
	public static int getByteStringLen(byte[] str, int offset, int len) {
		for (int i = 0; i < len; i++) {
			if (str[offset + i] == 0) {
				len = i;
				break;
			}
		}
		return len;
	}
	
	/**获取byte的第几bit(位)*/
	public static byte getByteV(byte src, int i) {
		  switch(i){
		   case 0: return (byte)((src >> 0) & 0x1);
		   case 1: return (byte) ((src >> 1) & 0x1);
		   case 2: return (byte) ((src >> 2) & 0x1);
		   case 3: return (byte) ((src >> 3) & 0x1);
		   case 4: return (byte) ((src >> 4) & 0x1);
		   case 5: return (byte) ((src >> 5) & 0x1);
		   case 6: return (byte) ((src >> 6) & 0x1);
		   default: return (byte)((src >> 7) & 0x1);
		  }
	}

	/**获取byte(INT8)*/
	public static byte getByte(byte[] data, int offset) {
		try {
			return data[offset];
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public static int getByte2Int(byte[] data, int offset){
		return (int) getByte(data,offset) & 0xff;
	}

	/**从指定源数组中复制一个数组，复制从指定的位置开始,到目标数组的指定位置(len)结束*/
	public static void getBytes(byte[] data, int offset, byte[] out,
			int out_offset, int len) {
		try {
			System.arraycopy(data, offset, out, out_offset, len);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**获取INT16*/
	public static short getShort(byte[] data, int offset) {
		try {
			return (short) (((data[offset]) & 0xff) + ((data[offset + 1] << 8) & 0xff00));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public static int getShort2Int(byte[] data, int offset){
		return (int) getShort(data,offset) & 0xffff;
	}

	/**获取INT32*/
	public static int getInt(byte[] data, int offset) {
		try {
			return ((data[offset]) & 0xff) + ((data[offset + 1] << 8) & 0xff00)
					+ ((data[offset + 2] << 16) & 0xff0000)
					+ ((data[offset + 3] << 24) & 0xff000000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public static long getInt2Long(byte[] data, int offset){
			return getInt(data,offset) & 0xffffffffL;
	}

	/**获取INT64*/
	public static long getLong(byte[] data, int offset) {
		try {
			return ((data[offset]) & 0xff)
					+ (((long) data[offset + 1] << 8) & 0xff00)
					+ (((long) data[offset + 2] << 16) & 0xff0000)
					+ (((long) data[offset + 3] << 24) & 0xff000000L)
					+ (((long) data[offset + 4] << 32) & 0xff00000000L)
					+ (((long) data[offset + 5] << 40) & 0xff0000000000L)
					+ (((long) data[offset + 6] << 48) & 0xff000000000000L)
					+ (((long) data[offset + 7] << 56) & 0xff00000000000000L);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return 0;
	}

	public static float getFloat(byte[] data, int offset) {
		try {
			int f = getInt(data,offset);
			return Float.intBitsToFloat(f);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public static double getDouble(byte[] data, int offset) {
		try {
			long l = getLong(data,offset);
			return Double.longBitsToDouble(l);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public static String doubleToString(byte[] data, int offset) {
		double d = getDouble(data,offset);
		BigDecimal d1 = new BigDecimal(d);
		BigDecimal d2 = new BigDecimal(1);
		String result = d1.divide(d2,8, BigDecimal.ROUND_HALF_UP).toString();
		return subZeroAndDot(result);
	}

	public static String subZeroAndDot(String s){
		if(s.indexOf(".") > 0){
			//去掉多余的0
			s = s.replaceAll("0+?$", "");
			//如最后一位是.则去掉
			s = s.replaceAll("[.]$", "");
		}
		return s;
	}

	/**获取CHAR(8)*/
	public static char getChar(byte[] data, int offset) {
		if (offset >= data.length) {
			return 0;
		}
		return (char) (((data[offset]) & 0xff) + ((data[offset + 1] << 8) & 0xff00));
	}

	public static String getString(byte[] data, int offset, int len){
		int reaLen = ByteUtil.getByteStringLen(data, offset, len);
		return new String(data, offset, reaLen);
	}

	public static String getStringCharset(byte[] data, int offset, int len,String charsetName){
		int reaLen = ByteUtil.getByteStringLen(data, offset, len);
		String code = null;
		try {
			code = new String(data, offset, reaLen, charsetName);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return code;
	}

	public static String getStringGBK(byte[] data, int offset, int len){
		return getStringCharset(data,offset,len, "GBK");
	}

	/**INT8 转为byte[]*/
	public static void putByte(byte[] data, int offset, byte value) {
		data[offset] = value;
	}

	/**从指定源数组中复制一个数组，复制从指定的位置开始,到目标数组的指定位置(len)结束*/
	public static void putBytes(byte[] dst, int dst_offset, byte[] src,
			int src_offset, int len) {
		System.arraycopy(src, src_offset, dst, dst_offset, len);
	}

	/**INT16 转为 byte[]*/
	public static void putShort(byte[] data, int offset, short value) {
		data[offset] = (byte) (value & 0xff);
		data[offset + 1] = (byte) ((value >> 8) & 0xff);
	}

	/**CHAR(8) 转为 byte[]*/
	public static void putChar(byte[] data, int offset, char value) {
		data[offset] = (byte) (value & 0xff);
		data[offset + 1] = (byte) ((value >> 8) & 0xff);
	}

	/**INT32 转为 byte[]*/
	public static void putInt(byte[] data, int offset, int value) {
		data[offset] = (byte) (value & 0xff);
		data[offset + 1] = (byte) ((value >> 8) & 0xff);
		data[offset + 2] = (byte) ((value >> 16) & 0xff);
		data[offset + 3] = (byte) ((value >> 24) & 0xff);
	}

	/**INT64 转为 byte[]*/
	public static void putLong(byte[] data, int offset, long value) {
		data[offset] = (byte) (value & 0xff);
		data[offset + 1] = (byte) ((value >> 8) & 0xff);
		data[offset + 2] = (byte) ((value >> 16) & 0xff);
		data[offset + 3] = (byte) ((value >> 24) & 0xff);
		data[offset + 4] = (byte) ((value >> 32) & 0xff);
		data[offset + 5] = (byte) ((value >> 40) & 0xff);
		data[offset + 6] = (byte) ((value >> 48) & 0xff);
		data[offset + 7] = (byte) ((value >> 56) & 0xff);
	}

}