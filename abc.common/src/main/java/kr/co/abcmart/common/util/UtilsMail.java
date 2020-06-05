package kr.co.abcmart.common.util;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import freemarker.template.Configuration;
import kr.co.abcmart.common.constant.BaseConst;
import kr.co.abcmart.common.exception.EmailException;
import lombok.extern.slf4j.Slf4j;

/**
 * 메일을 전송할 때 사용한다.
 * 
 */
@Slf4j
public class UtilsMail {

	private static JavaMailSender javaMailSender;

	private static Configuration freemarkerConfig;

	public static final void init(JavaMailSender javaMailSender, Configuration freeMarkerConfig) {
		UtilsMail.javaMailSender = javaMailSender;
		UtilsMail.freemarkerConfig = freeMarkerConfig;
	}

	public static void send(String senderEmail, String senderName, String receiverEmail, String receiverName,
			String subject, String content) throws EmailException {
		send(senderEmail, senderName, receiverEmail, receiverName, subject, content, true);
	}

	/**
	 * @Desc : 메일발송
	 * @Method Name : send
	 * @Date : 2019. 4. 8.
	 * @Author : Kimyounghyun
	 * @param senderEmail
	 * @param receiverEmail
	 * @param subject
	 * @param content
	 * @param isHtml
	 * @throws EmailException
	 */
	private static void send(String senderEmail, String senderName, String receiverEmail, String receiverName,
			String subject, String content, boolean isHtml) throws EmailException {
		try {

			MimeMessage message = javaMailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_NO,
					BaseConst.DEFAULT_CHARSET_UTF_8);

//			helper.setFrom(senderEmail);
			helper.setFrom(new InternetAddress(senderEmail, senderName));
//			helper.setTo(receiverEmail);
			helper.setTo(new InternetAddress(receiverEmail, receiverName));
			helper.setSubject(subject);
			helper.setText(content, isHtml);

			javaMailSender.send(message);

		} catch (Exception e) {
			throw new EmailException(e);
		}
	}
}
