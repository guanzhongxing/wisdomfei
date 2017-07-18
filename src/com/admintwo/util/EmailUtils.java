package com.admintwo.util;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailUtils {
	/**
	 * 该方法用于发送激活邮件
	 * 
	 * @param name
	 * @param email
	 * @throws MessagingException
	 */
	public static void sendEmail(String name, String email) throws MessagingException {
		if (name == null) {
			name = email + "用户";
		}
		Properties prop = new Properties();
		/*prop.put("mail.host", "smtp.admintwo.com");
		prop.put("mail.transport.protocol", "smtp");
		prop.put("mail.smtp.auth", "true");*/
		prop.setProperty("mail.smtp.host", "smtp.admintwo.com");
        prop.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        prop.setProperty("mail.smtp.socketFactory.fallback", "false");
        prop.setProperty("mail.smtp.port", "465");
        prop.setProperty("mail.smtp.socketFactory.port", "465");
        prop.put("mail.smtp.auth", "true");
		// 1.创建sesssion
		Session session = Session.getInstance(prop);
		// 开启session的调试模式，可以查看当前邮件发送状态
		// session.setDebug(true);
		// 2.通过session获取Transport对象（发送邮件的核心API）
		Transport ts = session.getTransport();
		// 3.通过邮件用户名密码链接，阿里云默认是开启个人邮箱pop3、smtp协议的，所以无需在阿里云邮箱里设置
		ts.connect("test@admintwo.com", "Daimaku1234");
		// 4.创建邮件
		Message msg = createSimpleMail(session, name, email);
		// 5.发送电子邮件
		ts.sendMessage(msg, msg.getAllRecipients());
	}

	public static MimeMessage createSimpleMail(Session session, String name, String email)
			throws AddressException, MessagingException {
		// 创建邮件对象
		MimeMessage mm = new MimeMessage(session);
		// 设置发件人
		mm.setFrom(new InternetAddress("代码库测试邮箱"));
		// 设置收件人
		mm.setRecipient(Message.RecipientType.TO, new InternetAddress(email));
		mm.setSubject("AdminTwo代码库");
		// 邮件的内容
		StringBuffer sb = activate(name, email);
		mm.setContent(sb.toString(), "text/html;charset=gbk");
		return mm;
	}

	public static StringBuffer activate(String name, String email) {
		StringBuffer sb = new StringBuffer();
		sb.append(
				"<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"width: 600px; border: 1px solid #ddd; border-radius: 3px; color: #555; font-family: 'Helvetica Neue Regular',Helvetica,Arial,Tahoma,'Microsoft YaHei','San Francisco','微软雅黑','Hiragino Sans GB',STHeitiSC-Light; font-size: 12px; height: auto; margin: auto; overflow: hidden; text-align: left; word-break: break-all; word-wrap: break-word;\">");
		sb.append("<tbody style=\"margin: 0; padding: 0;\">");
		sb.append("<tr style=\"background-color: #393D49; height: 60px; margin: 0; padding: 0;\">");
		sb.append("<td style=\"margin: 0; padding: 0;\">");
		sb.append("<div style=\"color: #5EB576; margin: 0; margin-left: 30px; padding: 0;\">");
		sb.append(
				"<a style=\"font-size: 14px; margin: 0; padding: 0; color: #5EB576; text-decoration: none;\" href=\"http://www.admintwo.com/\" target=\"_blank\">AdminTwo代码库 - 致力于为程序员搜集最有效的代码</a>");
		sb.append("</div>");
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("<tr style=\"margin: 0; padding: 0;\">");
		sb.append("<td style=\"margin: 0; padding: 30px;\">");
		sb.append(
				"<p style=\"line-height: 20px; margin: 0; margin-bottom: 10px; padding: 0;\"> 伟大的程序员，<em style=\"font-weight: 700;\">"
						+ name
						+ "</em>： </p> <p style=\"line-height: 2; margin: 0; margin-bottom: 10px; padding: 0;\"> 欢迎你加入<em>admintwo代码库</em>，希望你能在代码库找到自己所需的代码，同时也希望你能贡献一点自己的珍藏代码，帮助大家学习和交流。<br/>请点击下面的按钮激活邮箱，邮件有效时间24小时。 </p>");
		sb.append("<div style=\"\"> <a href=\"http://www.admintwo.com/user/activate?email=" + email
				+ "\" style=\"background-color: #009E94; color: #fff; display: inline-block; height: 32px; line-height: 32px; margin: 0 15px 0 0; padding: 0 15px; text-decoration: none;\" target=\"_blank\">立即激活邮箱</a> </div>");
		sb.append(
				"<p style=\"line-height: 20px; margin-top: 20px; padding: 10px; background-color: #f2f2f2; font-size: 12px;\"> 如果该邮件不是由你本人操作，请勿进行激活！否则你的邮箱将会被他人绑定。 </p>");
		sb.append("</td>");
		sb.append("</tr>");
		sb.append(
				"<tr style=\"background-color: #fafafa; color: #999; height: 35px; margin: 0; padding: 0; text-align: center;\">");
		sb.append("<td style=\"margin: 0; padding: 0;\">系统邮件，请勿直接回复。</td>");
		sb.append("</tr>");
		sb.append("</tbody>");
		sb.append("</table>");
		return sb;
	}

	/**
	 * 该方法用于发送修改密码邮件
	 * 
	 * @param name
	 * @param email
	 * @throws MessagingException
	 */
	public static void sendEmailForpassword(String name, String email) throws MessagingException {
		if (name == null) {
			name = email + "用户";
		}
		Properties prop = new Properties();
		prop.setProperty("mail.smtp.host", "smtp.admintwo.com");
        prop.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        prop.setProperty("mail.smtp.socketFactory.fallback", "false");
        prop.setProperty("mail.smtp.port", "465");
        prop.setProperty("mail.smtp.socketFactory.port", "465");
        prop.put("mail.smtp.auth", "true");

		// 1.创建sesssion
		Session session = Session.getInstance(prop);
		// 开启session的调试模式，可以查看当前邮件发送状态
		// session.setDebug(true);
		// 2.通过session获取Transport对象（发送邮件的核心API）
		Transport ts = session.getTransport();
		// 3.通过邮件用户名密码链接，阿里云默认是开启个人邮箱pop3、smtp协议的，所以无需在阿里云邮箱里设置
		ts.connect("test@admintwo.com", "Daimaku1234");
		// 4.创建邮件
		Message msg = createSimpleMailForpassword(session, name, email);
		// 5.发送电子邮件
		ts.sendMessage(msg, msg.getAllRecipients());
	}

	public static MimeMessage createSimpleMailForpassword(Session session, String name, String email)
			throws AddressException, MessagingException {
		// 创建邮件对象
		MimeMessage mm = new MimeMessage(session);
		// 设置发件人
		mm.setFrom(new InternetAddress("代码库测试邮箱"));
		// 设置收件人
		mm.setRecipient(Message.RecipientType.TO, new InternetAddress(email));
		mm.setSubject("AdminTwo代码库");
		// 邮件的内容
		StringBuffer sb = activateForpassword(name, email);
		mm.setContent(sb.toString(), "text/html;charset=gbk");
		return mm;
	}

	public static StringBuffer activateForpassword(String name, String email) {
		StringBuffer sb = new StringBuffer();
		sb.append(
				"<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"width: 600px; border: 1px solid #ddd; border-radius: 3px; color: #555; font-family: 'Helvetica Neue Regular',Helvetica,Arial,Tahoma,'Microsoft YaHei','San Francisco','微软雅黑','Hiragino Sans GB',STHeitiSC-Light; font-size: 12px; height: auto; margin: auto; overflow: hidden; text-align: left; word-break: break-all; word-wrap: break-word;\">");
		sb.append("<tbody style=\"margin: 0; padding: 0;\">");
		sb.append("<tr style=\"background-color: #393D49; height: 60px; margin: 0; padding: 0;\">");
		sb.append("<td style=\"margin: 0; padding: 0;\">");
		sb.append("<div style=\"color: #5EB576; margin: 0; margin-left: 30px; padding: 0;\">");
		sb.append(
				"<a style=\"font-size: 14px; margin: 0; padding: 0; color: #5EB576; text-decoration: none;\" href=\"http://www.admintwo.com/\" target=\"_blank\">AdminTwo代码库 - 致力于为程序员搜集最有效的代码</a>");
		sb.append("</div>");
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("<tr style=\"margin: 0; padding: 0;\">");
		sb.append("<td style=\"margin: 0; padding: 30px;\">");
		sb.append(
				"<p style=\"line-height: 20px; margin: 0; margin-bottom: 10px; padding: 0;\"> 伟大的程序员，<em style=\"font-weight: 700;\">"
						+ name
						+ "</em>： </p> <p style=\"line-height: 2; margin: 0; margin-bottom: 10px; padding: 0;\"> 欢迎你加入<em>admintwo代码库</em>，希望你能在代码库找到自己所需的代码，同时也希望你能贡献一点自己的珍藏代码，帮助大家学习和交流。<br/>请点击下面的按钮重新设置密码，邮件有效时间24小时。 </p>");
		sb.append("<div style=\"\"> <a href=\"http://www.admintwo.com/user_repassword?email=" + email
				+ "\" style=\"background-color: #009E94; color: #fff; display: inline-block; height: 32px; line-height: 32px; margin: 0 15px 0 0; padding: 0 15px; text-decoration: none;\" target=\"_blank\">立即重设密码</a> </div>");
		sb.append(
				"<p style=\"line-height: 20px; margin-top: 20px; padding: 10px; background-color: #f2f2f2; font-size: 12px;\"> 如果该邮件不是由你本人操作，请勿进行操作！ </p>");
		sb.append("</td>");
		sb.append("</tr>");
		sb.append(
				"<tr style=\"background-color: #fafafa; color: #999; height: 35px; margin: 0; padding: 0; text-align: center;\">");
		sb.append("<td style=\"margin: 0; padding: 0;\">系统邮件，请勿直接回复。</td>");
		sb.append("</tr>");
		sb.append("</tbody>");
		sb.append("</table>");
		return sb;
	}
}
