package net.blurblah.notiboy.mailsender;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import net.blurblah.notiboy.common.Result;
import net.blurblah.notiboy.config.Config;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MailgunProvider implements MailProvider {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	@Value("${mailgun.domain}")
	private String mailgunDomain;
	@Value("${mailgun.apikey}")
	private String mailgunKey;

	@Override
	public Result send(MailRequest request) {

		String requestUrl = String.format(Config.mailgunUriFormat, mailgunDomain);
		log.debug("Request url : ", requestUrl);
		Result r = new Result("ok");

		try {
			HttpResponse<String> response = Unirest.post(requestUrl)
												.basicAuth("api", mailgunKey)
												.field("from", request.getFrom())
												.field("to", request.getTo())
												.field("subject", request.getSubject())
												.field(request.getType().toLowerCase(), request.getContent())
												.asString();
			/**
			 * Response sample from mailgun
			 * {
			 *     "id": "<20150507095740.11928.11889@notiboy.blurblah.net>",
			 *     "message": "Queued. Thank you."
			 * }
			 */
			log.info("Response : ", response.getBody().toString());

			JSONObject responseObject = new JSONObject(response.getBody().toString());
			if(response.getStatus() == 200) {
				r.setMessage(responseObject.getString("id"));
			} else {
				r.setStatus("not_ok");
				r.setMessage(responseObject.toString());
			}
			return r;
		} catch(Exception e) {
			log.error(e.getMessage());
			return new Result("not_ok", e.getMessage());
		}
	}
}
