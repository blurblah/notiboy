package net.blurblah.notiboy.mailsender.model;


import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MailContent {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	private boolean isParsed = false;
	private String errorMessage;
	
	private String project;
	private String from;
	private String to;
	private String subject;
	
	private String message_type;		// message type : text, html
	private String message_format_file;	// message format file name
	private String message_content;		// if only text, must use...
	private JSONObject message_param;	// message format params : replace keyword
	
	
	public MailContent(String jsonContent){

		try{
			JSONObject obj = new JSONObject(jsonContent);
			
			project 			= obj.has("project") 			? obj.getString("project") 				: null;
			from 				= obj.has("from") 				? obj.getString("from") 				: null;
			to 					= obj.has("to") 				? obj.getString("to") 					: null;
			subject 			= obj.has("subject") 			? obj.getString("subject") 				: null;
			message_type 		= obj.has("message_type") 		? obj.getString("message_type") 		: null;
			message_format_file = obj.has("message_format_file")? obj.getString("message_format_file") 	: null;
			message_content 	= obj.has("message_content") 	? obj.getString("message_content") 		: null;
			message_param 		= obj.has("message_param") 		? obj.getJSONObject("message_param") 	: null;
			
			isParsed = true;
			
			log.debug("project", 				project);
			log.debug("from", 					from);
			log.debug("to", 					to);
			log.debug("subject",				subject);
			log.debug("message_type",			message_type);
			log.debug("message_format_file",	message_format_file);
			log.debug("message_content",		message_content);
			log.debug("message_param", 		message_param != null ? message_param.toString() : null );
			
			
		}catch(JSONException e){
			e.printStackTrace();
			errorMessage = e.getMessage();
		}
	}


	public boolean isParsed() {
		return isParsed;
	}


	public String getFrom() {
		return from;
	}


	public void setFrom(String from) {
		this.from = from;
	}


	public String getTo() {
		return to;
	}


	public void setTo(String to) {
		this.to = to;
	}


	public String getSubject() {
		return subject;
	}


	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getErrorMessage() {
		return this.errorMessage;
	}


	public String getProject() {
		return project;
	}


	public void setProject(String project) {
		this.project = project;
	}


	public String getMessage_type() {
		return message_type;
	}


	public void setMessage_type(String message_type) {
		this.message_type = message_type;
	}


	public String getMessage_format_file() {
		return message_format_file;
	}


	public void setMessage_format_file(String message_format_file) {
		this.message_format_file = message_format_file;
	}


	public String getMessage_content() {
		return message_content;
	}


	public void setMessage_content(String message_content) {
		this.message_content = message_content;
	}


	public JSONObject getMessage_param() {
		return message_param;
	}


	public void setMessage_param(JSONObject message_param) {
		this.message_param = message_param;
	}


	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	
	
	
}
