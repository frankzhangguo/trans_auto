package com.yonyou.trans.auto.model;

public class MailContents {
	static StringBuffer buffer = null;

	public static void addComments(String comments) {
		getContents().append("\r\n");
		getContents().append(comments);
	}

	public static void clearContents() {
		buffer = null;
	}

	public static StringBuffer getContents() {
		if (buffer == null) {
			buffer = new StringBuffer("");
		}
		return buffer;
	}
}
