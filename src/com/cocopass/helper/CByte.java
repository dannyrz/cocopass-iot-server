package com.cocopass.helper;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public class CByte {

	/** */
	/**
	 * 把字节数组转换成16进制字符串
	 * 
	 * @param bArray
	 * @return
	 */
	public static final String BytesToHexString(byte[] bArray) {
		StringBuffer sb = new StringBuffer(bArray.length);
		for (int i = 0; i < bArray.length; i++) {
			sb.append(ByteToHexString(bArray[i]));
		}
		return sb.toString();
	}

	public static final String ByteToHexString(byte bte) {
		String sTemp = Integer.toHexString(0xFF & bte);
		if (sTemp.length() < 2) {
			sTemp = "0" + sTemp.toUpperCase();
		}
		return sTemp;
	}

	public static final String IntToHexString(int value) {
		String sTemp = Integer.toHexString(value & 0xff);
		if (sTemp.length() < 2) {
			sTemp = "0" + sTemp.toUpperCase();
		}
		return sTemp;
	}

	/**
	 * byte转无符号int
	 * 
	 * @param bytes
	 * @return
	 */
	public static int byteToUnit(byte b) {
		int i = b;
		i = b & 0xff;
		return i;
	}

	/**
	 * @parmam int
	 * @return i=1 {0,0,0,1}
	 */
	public static byte[] IntToByteArray(int i) {
		byte[] result = new byte[4];
		// 由高位到低位
		result[0] = (byte) ((i >> 24) & 0xFF);
		result[1] = (byte) ((i >> 16) & 0xFF);
		result[2] = (byte) ((i >> 8) & 0xFF);
		result[3] = (byte) (i & 0xFF);
		return result;
	}

	// public static byte[] int2byte(int res) {
	// byte[] targets = new byte[4];
	//
	// targets[0] = (byte) (res & 0xff);// 最低位
	// targets[1] = (byte) ((res >> 8) & 0xff);// 次低位
	// targets[2] = (byte) ((res >> 16) & 0xff);// 次高位
	// targets[3] = (byte) (res >>> 24);// 最高位,无符号右移。
	// return targets;
	// }
	/**
	 * int到byte[]
	 * 
	 * @param i
	 * @return
	 */
	// public static byte[] intToByteArray(int i) {
	// byte[] result = new byte[4];
	// //由高位到低位
	// result[0] = (byte)((i >> 24) & 0xFF);
	// result[1] = (byte)((i >> 16) & 0xFF);
	// result[2] = (byte)((i >> 8) & 0xFF);
	// result[3] = (byte)(i & 0xFF);
	// return result;
	// }
	//
	/**
	 * byte[]转int
	 * 
	 * @param bytes
	 * @return
	 */
	/*
	 * public static int byteArrayToInt(byte[] bytes) { int value= 0; //由高位到低位
	 * for (int i = 0; i < 4; i++) { int shift= (4 - 1 - i) * 8; value
	 * +=(bytes[i] & 0x000000FF) << shift;//往高位游 } return value; }
	 * 
	 */

	/**
	 * byte[]转int 
	 * 如果10进制30，则传入的byte数组为 {0x1e,0,0,0} 要注意顺序
	 * @param bytes
	 *            会出现负数情况，跟JAVA没有无符号整型相关
	 * @return
	 */
	public static int ByteArrayToInt(byte[] res) {
		// 一个byte数据左移24位变成0x??000000，再右移8位变成0x00??0000

		int targets = (res[0] & 0xff) | ((res[1] << 8) & 0xff00) // | 表示安位或
				| ((res[2] << 24) >>> 8) | (res[3] << 24);
		return targets;
	}

	/**
	 * short整数转换为2字节的byte数组
	 * 
	 * @param s
	 *            short整数
	 * @return byte数组
	 */
	public static byte[] unsignedShortToByte2(int s) {
		byte[] targets = new byte[2];
		targets[0] = (byte) (s >> 8 & 0xFF);
		targets[1] = (byte) (s & 0xFF);
		return targets;
	}

	/**
	 * byte数组转换为无符号short整数
	 * 
	 * @param bytes
	 *            byte数组
	 * @return short整数
	 */
	public static int byte2ToUnsignedShort(byte[] bytes) {
		return byte2ToUnsignedShort(bytes, 0);
	}

	/**
	 * byte数组转换为无符号short整数
	 * 
	 * @param bytes
	 *            byte数组
	 * @param off
	 *            开始位置
	 * @return short整数
	 */
	public static int byte2ToUnsignedShort(byte[] bytes, int off) {
		int high = bytes[off];
		int low = bytes[off + 1];
		return (high << 8 & 0xFF00) | (low & 0xFF);
	}

	/*
	 * public static int toInt(byte[] bRefArr) { int iOutcome = 0; byte bLoop;
	 * 
	 * for (int i = 0; i < bRefArr.length; i++) { bLoop = bRefArr[i]; iOutcome
	 * += (bLoop & 0xFF) << (8 * i); } return iOutcome; }
	 */
	/**
	 * byte数组转无符号int 可能存在问题，未验证！！
	 * 
	 * @param res
	 * @return
	 */
	// public static int ByteArrayToUInt(byte[] res) {
	// // 一个byte数据左移24位变成0x??000000，再右移8位变成0x00??0000
	//
	// int targets =res[0]*256*256*256+res[1]*256*256+res[2]*256+res[3];
	// return targets;
	// }

	/**
	 * 将byte转换为一个长度为8的boolean数组（每bit代表一个boolean值）
	 * 
	 * @param b
	 *            byte
	 * @return boolean数组
	 */
	public static boolean[] ToBooleanArray(byte b) {
		boolean[] array = new boolean[8];
		for (int i = 7; i >= 0; i--) { // 对于byte的每bit进行判定
			array[i] = (b & 1) == 1; // 判定byte的最后一位是否为1，若为1，则是true；否则是false
			b = (byte) (b >> 1); // 将byte右移一位
		}
		return array;
	}

	/**
	 * 将一个长度为8的boolean数组（每bit代表一个boolean值）转换为byte 2014-7-4 下午5:28:28
	 * 
	 * @param array
	 * @return
	 * 
	 */
	public static byte BooleanArrayToByte(boolean[] array) {
		if (array != null && array.length > 0) {
			byte b = 0;
			for (int i = 0; i <= 7; i++) {
				if (array[i]) {
					int nn = (1 << (7 - i));
					b += nn;
				}
			}
			return b;
		}
		return 0;
	}

	public static String GetRemoveFirstZeroString(byte[] bytes) {
		String result = BytesToHexString(bytes);
		result = result.replaceFirst("^0*", "");
		if (result.equals(""))
			result = "0";
		return result;

	}

	/**
	 * @parmam long
	 * @return i=1 {0,0,0,0,0,0,0,1}
	 */
	public static byte[] longToBytes(long x) {

		ByteBuffer buffer = ByteBuffer.allocate(8);
		buffer.putLong(0, x);
		return buffer.array();
	}

	public static long bytesToLong(byte[] bytes) {

		ByteBuffer buffer = ByteBuffer.allocate(8);
		buffer.put(bytes, 0, bytes.length);
		buffer.flip();// need flip
		return buffer.getLong();
	}

	// public static byte[] long2Bytes(long num) {
	// byte[] byteNum = new byte[8];
	// for (int ix = 0; ix < 8; ++ix) {
	// int offset = 64 - (ix + 1) * 8;
	// byteNum[ix] = (byte) ((num >> offset) & 0xff);
	// }
	// return byteNum;
	// }

	// public static long bytes2Long(byte[] byteNum) {
	//
	// long num = 0;
	// try{
	// for (int ix = 0; ix < 8; ++ix) {
	// num <<= 8;
	// num |= (byteNum[ix] & 0xff);
	// }
	// }
	// catch(Exception er)
	// {
	// er.printStackTrace();
	// }
	// return num;
	// }

	/*
	 * Convert byte[] to hex
	 * string.这里我们可以将byte转换成int，然后利用Integer.toHexString(int)来转换成16进制字符串。
	 * 
	 * @param src byte[] data
	 * 
	 * @return hex string
	 */
	public static String bytesToHexString(byte[] src) {
		StringBuilder stringBuilder = new StringBuilder("");
		if (src == null || src.length <= 0) {
			return null;
		}
		for (int i = 0; i < src.length; i++) {
			int v = src[i] & 0xFF;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				stringBuilder.append(0);
			}
			stringBuilder.append(hv);
		}
		return stringBuilder.toString();
	}

	/**
	 * Convert hex string to byte[]
	 * 
	 * @param hexString
	 *            the hex string
	 * @return byte[]
	 */
	public static byte[] hexStringToBytes(String hexString) {
		if (hexString == null || hexString.equals("")) {
			return null;
		}
		hexString = hexString.toUpperCase();
		int length = hexString.length() / 2;
		char[] hexChars = hexString.toCharArray();
		byte[] d = new byte[length];
		for (int i = 0; i < length; i++) {
			int pos = i * 2;
			d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
		}
		return d;
	}

	/**
	 * Convert char to byte
	 * 
	 * @param c
	 *            char
	 * @return byte
	 */
	private static byte charToByte(char c) {
		return (byte) "0123456789ABCDEF".indexOf(c);
	}

	public static byte[] ReadInputStreamBytes(InputStream in) throws IOException {
		BufferedInputStream bufin = new BufferedInputStream(in);
		int buffSize = 1024;
		ByteArrayOutputStream out = new ByteArrayOutputStream(buffSize);

		// System.out.println("Available bytes:" + in.available());

		byte[] temp = new byte[buffSize];
		int size = 0;
		while ((size = bufin.read(temp)) != -1) {
			out.write(temp, 0, size);
		}
		bufin.close();

		byte[] content = out.toByteArray();
		return content;
	}

}
