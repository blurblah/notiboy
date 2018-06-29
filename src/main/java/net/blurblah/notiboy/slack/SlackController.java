package net.blurblah.notiboy.slack;

import com.github.seratch.jslack.Slack;
import com.github.seratch.jslack.api.webhook.Payload;
import com.github.seratch.jslack.api.webhook.WebhookResponse;
import com.google.gson.Gson;
import net.blurblah.notiboy.log.SLog;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.io.IOException;

@RestController
public class SlackController {

    @Value("${slack.token}")
    private String token;

    @RequestMapping(value="/slack", method=RequestMethod.POST, consumes="application/json")
    public Result postMessage(@Valid @RequestBody SlackRequest request) {
        Gson gson = new Gson();
        SLog.i("Requested body:" + gson.toJson(request));

        String url = String.format("https://hooks.slack.com/services/%s", token);
        Slack slack = Slack.getInstance();

        Result result = new Result("ok");
        try {
            WebhookResponse response = slack.send(url, buildPayload(request.getChannel(),
                    request.getMessage()));
            SLog.i(String.format("Slack response ==> Code:%d, Message:%s, Body:%s", response.getCode(),
                    response.getMessage(), response.getBody()));
            if(response.getCode() != 200) {
                result.setStatus("not_ok");
                result.setMessage(response.getMessage());
            }
        } catch (IOException e) {
            SLog.e(e.getMessage());
            result.setStatus("not_ok");
            result.setMessage(e.getMessage());
        }

        return result;
    }

    private Payload buildPayload(String channel, String message) {
        if(!channel.startsWith("#") && !channel.startsWith("@")) channel = "#" + channel;
        return Payload.builder().channel(channel)
                .username("Notiboy").iconEmoji(":male-technologist:")
                .text(message).build();
    }
}
