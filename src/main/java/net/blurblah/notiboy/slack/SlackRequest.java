package net.blurblah.notiboy.slack;

import com.github.seratch.jslack.api.model.Attachment;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

public class SlackRequest {
    @NotNull
    @NotBlank
    private String channel;
    @NotNull
    private String message;
    private List<Attachment> attachments;

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }
}
