package com.yonyou.trans.db;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Utl {
	private static final char HEX_DIGITS[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

	public static String Bit16(String SourceString) throws Exception {

		return Bit32(removeSpace(SourceString)).substring(8, 24);
	}

	private static String Bit32(String SourceString) throws Exception {
		MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
		digest.update(SourceString.getBytes());
		byte messageDigest[] = digest.digest();
		return toHexString(messageDigest);
	}

	public static String fileMD5(String inputFile) throws IOException {
		// 缓冲区大小（这个可以抽出一个参数）

		int bufferSize = 256 * 1024;
		FileInputStream fileInputStream = null;
		DigestInputStream digestInputStream = null;

		try {

			// 拿到一个MD5转换器（同样，这里可以换成SHA1）

			MessageDigest messageDigest = MessageDigest.getInstance("MD5");

			// 使用DigestInputStream

			fileInputStream = new FileInputStream(inputFile);

			digestInputStream = new DigestInputStream(fileInputStream, messageDigest);

			// read的过程中进行MD5处理，直到读完文件

			byte[] buffer = new byte[bufferSize];

			while (digestInputStream.read(buffer) > 0) {
				;
			}

			// 获取最终的MessageDigest

			messageDigest = digestInputStream.getMessageDigest();

			// 拿到结果，也是字节数组，包含16个元素

			byte[] resultByteArray = messageDigest.digest();

			// 同样，把字节数组转换成字符串

			return toHexString(resultByteArray);

		} catch (NoSuchAlgorithmException e) {

			return null;

		} finally {

			try {

				digestInputStream.close();

			} catch (Exception e) {

			}

			try {

				fileInputStream.close();

			} catch (Exception e) {

			}

		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			System.out.println(MD5Utl.fileMD5("C:/uaptrans/1/aeam.xls"));
			System.out.println(MD5Utl.fileMD5("C:/uaptrans/aeam.xls"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static final String removeSpace(String ss) {

		ss = ss.replaceAll("　", " ");

		ss = ss.trim();
		return ss;
	}

	public static String string2Unicode(String string) {

		StringBuffer unicode = new StringBuffer();

		for (int i = 0; i < string.length(); i++) {

			// 取出每一个字符
			char c = string.charAt(i);

			// 转换为unicode
			unicode.append("\\u" + Integer.toHexString(c));
		}

		return unicode.toString();
	}

	private static String toHexString(byte[] b) {
		StringBuilder sb = new StringBuilder(b.length * 2);
		for (int i = 0; i < b.length; i++) {
			sb.append(HEX_DIGITS[(b[i] & 0xf0) >>> 4]);
			sb.append(HEX_DIGITS[b[i] & 0x0f]);
		}
		return sb.toString();
	}
}
