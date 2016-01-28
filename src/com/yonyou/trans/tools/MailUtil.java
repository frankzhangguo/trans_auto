package com.yonyou.trans.tools;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

import com.yonyou.trans.auto.model.Mail;
import com.yonyou.trans.auto.model.Project;
import com.yonyou.trans.auto.model.Trans_simpchnVO;

/**
 * �ʼ����͹���ʵ����
 * 
 * @author shadow
 * @create 2013/07/12
 */
public class MailUtil {

	public SimpleEmail getSimpleEmail() {
		// ����email
		SimpleEmail email = new SimpleEmail();

		try {
			// ������SMTP���ͷ�����������
			email.setHostName("mail.yonyou.com");
			// �ַ����뼯������
			email.setCharset("UTF-8");
			// �ռ��˵�����
			String receivers = Project.getProjectConfig().getMailReceivers();
			String[] tos = receivers.split(";");
			for (String toEmailUser : tos) {
				email.addTo(toEmailUser);
			}

			// �����˵�����
			email.setFrom("nc-sdp@yonyou.com");
			// �����Ҫ��֤��Ϣ�Ļ���������֤���û���-���롣�ֱ�Ϊ���������ʼ��������ϵ�ע�����ƺ�����
			email.setAuthentication("nc-sdp", "#sdp08#");
			// Ҫ���͵��ʼ�����
			email.setSubject(Mail.getSubject());
		} catch (EmailException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return email;
	}

	public void send(String error) {
		// ����email
		SimpleEmail email = getSimpleEmail();

		try {
			email.setMsg(error);
			// ����
			email.send();
		} catch (EmailException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void send(Trans_simpchnVO[] sheetVOs) {
		// ����email
		SimpleEmail email = getSimpleEmail();

		try {
			email.setMsg(Mail.getMessage(sheetVOs));
			// ����
			email.send();
		} catch (EmailException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}