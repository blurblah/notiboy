package net.blurblah.notiboy.mailsender;

import net.blurblah.notiboy.common.Result;

public interface MailProvider {
	Result send(MailRequest request);
}
