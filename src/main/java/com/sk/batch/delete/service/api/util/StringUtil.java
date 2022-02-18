package com.sk.batch.delete.service.api.util;

import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class provides convenience methods for
 * custom String operations.
 *
 * @author jayaraman
 *
 */
public class StringUtil {

	public static final String COLON = ":";
	public static final String SLASH = "/";
	public static final String EQUAL = "=";
	public static final String DOT = ".";

	/**
	 * Returns the name of the file with extension from a file path
	 *
	 * @param filePath
	 * @return
	 */
	public static String getFileNameFromFilePath(String filePath) {
		return (filePath == null) ? null : filePath.substring(filePath.lastIndexOf(SLASH) + 1);
	}

	/**
	 * Returns the name of the file with extension from a file path
	 *
	 * @param filePath
	 * @return
	 */
	public static String getFileExetnsionFromFileName(String fileName) {
		return (fileName == null) ? null : fileName.substring(fileName.lastIndexOf(DOT) + 1);
	}

	/**
	 * Returns last N characters of a string.
	 * Returns null if the value is null.
	 *
	 * @param value
	 * @param N
	 * @return
	 */
	public static String getLastNChars(String value, int N) {
		return (value == null) ? null : value.substring(value.length() - N);
	}

	/**
	 * Returns MD5 Digest String for the input String value.
	 *
	 * @param value
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String getMD5Hash(final String value)
			throws UnsupportedEncodingException {

		if(value == null)
			return value;

		StringBuffer sb = new StringBuffer();
		try {
			final java.security.MessageDigest md = java.security.MessageDigest
					.getInstance("MD5");
			final byte[] array = md.digest(value.getBytes("UTF-8"));
			sb = new StringBuffer();
			for (int i = 0; i < array.length; ++i) {
				sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100)
						.substring(1, 3));
			}
			return sb.toString();
		} catch (final java.security.NoSuchAlgorithmException e) {
		}
		return sb.toString();
	}

	/**
	 *
	 * @param data
	 * @return
	 */
	public static String convertMD5ToHex(String value) {
	    StringBuffer buf = new StringBuffer();
	    byte[] data = value.getBytes();
	    for (int i = 0; i < data.length; i++) {
	        int halfbyte = (data[i] >>> 4) & 0x0F;
	        int two_halfs = 0;
	        do {
	            if ((0 <= halfbyte) && (halfbyte <= 9))
	                buf.append((char) ('0' + halfbyte));
	            else
	                buf.append((char) ('a' + (halfbyte - 10)));
	            halfbyte = data[i] & 0x0F;
	        } while(two_halfs++ < 1);
	    }
	    return buf.toString();
	}



	/**
	 * Returns a 40 digit UUID for a 32 character MD5 String passed.
	 *
	 * @param MD5Digest
	 * @return
	 * @throws Exception
	 */
	public static String convertMD5ToUUID(String MD5Digest) throws Exception {
		String uuid = null;
		if (null != MD5Digest && MD5Digest.matches("[a-f0-9]{32}")) {
			Pattern md5Pattern = Pattern
					.compile("([a-f0-9]{8})([a-f0-9]{4})([a-f0-9]{4})([a-f0-9]{4})([a-f0-9]{12})");
			Matcher values = md5Pattern.matcher(MD5Digest);
			if (values.matches()) {
				uuid = values.group(1) + "-" +
						values.group(2) + "-" +
						values.group(3) + "-" +
						values.group(4) + "-" +
						values.group(5);
			}
		}

		return uuid;
	}

	/**
	 * Left pads a string with paddingValue based on the  paddingCount.
	 * Ex. lpad("text",5,'Z') returns "ZZZZZtext" as value.
	 *
	 * @param value
	 * @param paddingCount
	 * @param paddingValue
	 * @return String
	 */
	public static String lpad(String value, int paddingCount, char paddingValue){
		return value == null ? value : String.format("%"+ paddingCount +"s", value).replace(' ', paddingValue);

	}

	public static boolean isEmptyOrNull(String value){
		return (value == null || value.trim().length() == 0 ) ? true : false;
	}
}


