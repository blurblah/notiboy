package net.blurblah.notiboy.slack;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

public class SlackRequest {
    @NotNull
    @NotBlank
    private String channel;
    @NotNull
    @NotBlank
    private String message;

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
}
