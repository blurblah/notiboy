package net.blurblah.notiboy.mailsender;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import net.blurblah.notiboy.common.Result;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MailgunProvider implements MailProvider {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	@Value("${mailgun.endpoint}")
	private String mailgunEndpoint;
	@Value("${mailgun.apikey}")
	private String mailgunKey;

	@Override
	public Result send(MailRequest request) {
		Result r = new Result("ok");

		try {
			StringBuilder recipientsBuilder = new StringBuilder();
			for(String recipient : request.getRecipients()) {
				recipientsBuilder.append(recipient).append(",");
			}
			recipientsBuilder.delete(recipientsBuilder.lastIndexOf(","),
					recipientsBuilder.lastIndexOf(",") + 1);

			HttpResponse<String> response = Unirest.post(mailgunEndpoint)
												.basicAuth("api", mailgunKey)
												.field("from", request.getFrom())
												.field("to", recipientsBuilder.toString())
												.field("subject", request.getSubject())
												.field(request.getType().toLowerCase(), request.getContent())
												.asString();
			log.info("Response : " + response.getBody().toString());

			JSONObject responseObject = new JSONObject(response.getBody().toString());
			if(response.getStatus() != 200) {
				r.setStatus("not_ok");
				r.setMessage((String)responseObject.get("message"));
			}
		} catch(Exception e) {
			log.error(e.getMessage());
			r.setStatus("not_ok");
			r.setMessage(e.getMessage());
		}

		return r;
	}
}
