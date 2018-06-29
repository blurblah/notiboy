package net.blurblah.notiboy.mailsender;

import net.blurblah.notiboy.mailsender.MailRequest;

public interface MailProvider {
	String send(MailRequest request);
}
