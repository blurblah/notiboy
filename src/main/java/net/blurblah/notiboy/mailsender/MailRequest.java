package net.blurblah.notiboy.mailsender;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class MailRequest {

    // TODO: Support html type
    private final String type = "text";
    @NotNull
    @NotBlank
    private String from;
    @NotNull
    @NotEmpty
    private String[] recipients;
    @NotNull
    private String subject;
    @NotNull
    private String content;

    public String getFrom() {
        return from;
    }

    public String[] getRecipients() {
        return recipients;
    }

    public String getSubject() {
        return subject;
    }

    public String getType() {
        return type;
    }

    public String getContent() {
        return content;
    }
}
