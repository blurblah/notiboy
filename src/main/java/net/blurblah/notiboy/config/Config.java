package net.blurblah.notiboy.config;

public class Config {
	public static boolean isDebug = true;
	public static final String mailgunUriFormat	= "https://api.mailgun.net/v3/%s/messages";
	public final static String formatPath = "mailformat";
	public final static String paramFormat = "###[%s]###";
}
