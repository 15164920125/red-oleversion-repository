package cn.ms.util;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

@SuppressWarnings("restriction")
public class Base64Util {
	public static String getStrFromBASE64(String s) {
		byte[] b = getByteFromBASE64(s);
		return new String(b);
	}

	public static byte[] getByteFromBASE64(String s) {
		if (s == null) {
			return null;
		}
		BASE64Decoder decoder = new BASE64Decoder();
		try {
			return decoder.decodeBuffer(s);
		} catch (Exception e) {
		}
		return null;
	}

	public static String getBASE64FromByte(byte[] s) {
		if (s == null) {
			return null;
		}
		
		BASE64Encoder encoder = new BASE64Encoder();
		try {
			return encoder.encodeBuffer(s);
		} catch (Exception e) {
		}
		return null;
	}

	public static String getBASE64FromStr(String s) {
		return getBASE64FromByte(s.getBytes()).trim();
	}
}
