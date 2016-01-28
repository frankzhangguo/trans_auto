package com.yonyou.trans.tools;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

import com.yonyou.trans.auto.model.Mail;
import com.yonyou.trans.auto.model.Project;
import com.yonyou.trans.auto.model.Trans_simpchnVO;

/**
 * 邮件发送工具实现类
 * 
 * @author shadow
 * @create 2013/07/12
 */
public class MailUtil {

	public SimpleEmail getSimpleEmail() {
		// 发送email
		SimpleEmail email = new SimpleEmail();

		try {
			// 这里是SMTP发送服务器的名字
			email.setHostName("mail.yonyou.com");
			// 字符编码集的设置
			email.setCharset("UTF-8");
			// 收件人的邮箱
			String receivers = Project.getProjectConfig().getMailReceivers();
			String[] tos = receivers.split(";");
			for (String toEmailUser : tos) {
				email.addTo(toEmailUser);
			}

			// 发送人的邮箱
			email.setFrom("nc-sdp@yonyou.com");
			// 如果需要认证信息的话，设置认证：用户名-密码。分别为发件人在邮件服务器上的注册名称和密码
			email.setAuthentication("nc-sdp", "#sdp08#");
			// 要发送的邮件主题
			email.setSubject(Mail.getSubject());
		} catch (EmailException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return email;
	}

	public void send(String error) {
		// 发送email
		SimpleEmail email = getSimpleEmail();

		try {
			email.setMsg(error);
			// 发送
			email.send();
		} catch (EmailException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void send(Trans_simpchnVO[] sheetVOs) {
		// 发送email
		SimpleEmail email = getSimpleEmail();

		try {
			email.setMsg(Mail.getMessage(sheetVOs));
			// 发送
			email.send();
		} catch (EmailException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}