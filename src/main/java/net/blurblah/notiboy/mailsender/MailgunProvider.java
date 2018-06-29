package net.blurblah.notiboy.mailsender;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import net.blurblah.notiboy.config.Config;
import org.json.JSONException;
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
	public String send(MailRequest request) {

		/** Response Sample (from mailgun)
		 * {
		 		"id": "<20150507095740.11928.11889@codealley.inslab.co.kr>",
				"message": "Queued. Thank you."
		   }
		 */
		JSONObject result = new JSONObject();
		HttpResponse<String> jsonResponse = null;
		
		try {
			String requestUrl = String.format(Config.mailgunUriFormat, mailgunDomain);
			log.debug("Request url : ", requestUrl);
			
			jsonResponse = Unirest.post(requestUrl)
					.basicAuth("api", mailgunKey)
					.field("from", request.getFrom())
					.field("to", request.getTo())
					.field("subject", request.getSubject())
					.field(request.getType().toLowerCase(), request.getContent())
					.asString();

			log.debug("Response : ", jsonResponse.getBody().toString());


			JSONObject mailgun = new JSONObject(jsonResponse.getBody().toString());
			if(mailgun.has("id")) {
				result.put("result", true);
				result.put("id", mailgun.getString("id"));
			} else {
				result.put("result", false);
				result.put("error", jsonResponse.getBody().toString());
			}
				
		} catch (UnirestException | JSONException e) {
			e.printStackTrace();
			try {
				result.put("result", false);
				if( jsonResponse != null )
					result.put("error", jsonResponse.getBody().toString());
				else
					result.put("error", e.getMessage());
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return result.toString();
	}
}
