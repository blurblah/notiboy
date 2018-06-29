package net.blurblah.notiboy.mailsender.provider;

import net.blurblah.notiboy.mailsender.MailRequest;

public interface MailProvider {
	String send(MailRequest request);
}
