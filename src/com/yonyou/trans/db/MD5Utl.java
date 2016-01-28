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
		// ��������С��������Գ��һ��������

		int bufferSize = 256 * 1024;
		FileInputStream fileInputStream = null;
		DigestInputStream digestInputStream = null;

		try {

			// �õ�һ��MD5ת������ͬ����������Ի���SHA1��

			MessageDigest messageDigest = MessageDigest.getInstance("MD5");

			// ʹ��DigestInputStream

			fileInputStream = new FileInputStream(inputFile);

			digestInputStream = new DigestInputStream(fileInputStream, messageDigest);

			// read�Ĺ����н���MD5����ֱ�������ļ�

			byte[] buffer = new byte[bufferSize];

			while (digestInputStream.read(buffer) > 0) {
				;
			}

			// ��ȡ���յ�MessageDigest

			messageDigest = digestInputStream.getMessageDigest();

			// �õ������Ҳ���ֽ����飬����16��Ԫ��

			byte[] resultByteArray = messageDigest.digest();

			// ͬ�������ֽ�����ת�����ַ���

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

		ss = ss.replaceAll("��", " ");

		ss = ss.trim();
		return ss;
	}

	public static String string2Unicode(String string) {

		StringBuffer unicode = new StringBuffer();

		for (int i = 0; i < string.length(); i++) {

			// ȡ��ÿһ���ַ�
			char c = string.charAt(i);

			// ת��Ϊunicode
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
