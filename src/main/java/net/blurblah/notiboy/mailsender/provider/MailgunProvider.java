package net.blurblah.notiboy.mailsender.provider;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import net.blurblah.notiboy.config.Config;
import net.blurblah.notiboy.mailsender.model.MailContent;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

@Service
public class MailgunProvider implements MailProvider {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	@Value("${mailgun.domain}")
	private String mailgunDomain;
	@Value("${mailgun.apikey}")
	private String mailgunKey;

	@Override
	public String request(ServletContext context, String jsonRequest){

		JSONObject result = new JSONObject();
		
		MailContent content = new MailContent(jsonRequest);
		HttpResponse<String> jsonResponse= null;
		
		try {
			if(!content.isParsed()){
				log.debug("json parse error", content.getErrorMessage());
				log.debug("jsonRequest", jsonRequest);
				return result.put("error", content.getErrorMessage()).toString();
			}

			String requestUrl = String.format(Config.mailgunUriFormat, mailgunDomain);
			log.debug("Request url(to mailgun)", requestUrl);
			
			if(mailgunKey == null)
				return result.put("error", "mailgun_api_key not exist context param.(web.xml)").toString();

			//String messageType = content.getMessage_type();
			String messageFormatFile = content.getMessage_format_file();
			String message = null;
			
			if(messageFormatFile != null) {
				
				//File file = new File(Config.formatPath + messageFormatFile); //ex : codealley_invite.html
				File file = new File(context.getRealPath("/" + Config.formatPath + "/" + messageFormatFile));
				log.debug("Message file path : " + file.getAbsolutePath());
				FileReader reader = new FileReader(file);
				char[] chars = new char[(int) file.length()];
				reader.read(chars);
				message = new String(chars);
				reader.close();

				if(content.getMessage_param() != null) {
					JSONObject params = content.getMessage_param();
					Iterator<?> iter = params.keys();
					String key, value;
					
					while(iter.hasNext()) {
						key = (String)iter.next();
						value = params.getString(key);
						
						// paramter format : ###[parameter]###  
						log.debug("parameter", key + " : " + value);
						message = message.replace(String.format(Config.paramFormat, key), value);
					}
				}

				log.debug(messageFormatFile, message);
			} else {
				message = content.getMessage_content();
			}

			jsonResponse = Unirest.post(requestUrl)
					.basicAuth("api", mailgunKey)
					.field("from", content.getFrom())
					.field("to", content.getTo())  
					.field("subject", content.getSubject())
					.field(content.getMessage_type().toLowerCase(), message)
					.asString();

			log.debug("Response(from mailgun)", jsonResponse.getBody().toString());

			/** Response Sample (from mailgun)
			 * {
				  "id": "<20150507095740.11928.11889@codealley.inslab.co.kr>",
				  "message": "Queued. Thank you."
				}
			 */
			JSONObject mailgun = new JSONObject(jsonResponse.getBody().toString());
			if(mailgun.has("id")) {
				result.put("result", true);
				result.put("id", mailgun.getString("id"));
			} else {
				result.put("result", false);
				result.put("error", jsonResponse.getBody().toString());
			}
				
		} catch (UnirestException | JSONException | IOException e) {
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
