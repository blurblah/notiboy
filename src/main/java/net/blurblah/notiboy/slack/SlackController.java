package net.blurblah.notiboy.slack;

import com.github.seratch.jslack.Slack;
import com.github.seratch.jslack.api.methods.SlackApiException;
import com.github.seratch.jslack.api.methods.request.channels.ChannelsListRequest;
import com.github.seratch.jslack.api.methods.request.chat.ChatPostMessageRequest;
import com.github.seratch.jslack.api.methods.request.groups.GroupsListRequest;
import com.github.seratch.jslack.api.methods.response.channels.ChannelsListResponse;
import com.github.seratch.jslack.api.methods.response.chat.ChatPostMessageResponse;
import com.github.seratch.jslack.api.methods.response.groups.GroupsListResponse;
import com.github.seratch.jslack.api.model.Channel;
import com.github.seratch.jslack.api.model.Group;
import com.google.gson.Gson;
import net.blurblah.notiboy.common.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.io.IOException;
import java.util.NoSuchElementException;

@RestController
public class SlackController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    @Value("${slack.name}")
    private String slackName;
    @Value("${slack.emoji}")
    private String slackEmoji;
    @Value("${slack.oauth.token}")
    private String token;

    @RequestMapping(value="/slack", method=RequestMethod.POST, consumes="application/json")
    public Result postMessage(@Valid @RequestBody SlackRequest request) {
        Gson gson = new Gson();
        log.info("Requested body:" + gson.toJson(request));
        Result result = new Result("ok");

        try {
            String channelId = findChannel(token, request.getChannel());

            Slack slack = Slack.getInstance();
            ChatPostMessageResponse postResponse = slack.methods().chatPostMessage(
                    ChatPostMessageRequest.builder().token(token).channel(channelId)
                            .username(slackName).iconEmoji(slackEmoji).text(request.getMessage()).build()
            );
            log.info("response => " + gson.toJson(postResponse));

            if(!postResponse.isOk()) {
                result.setStatus("not_ok");
                result.setMessage(postResponse.getError());
            }
        } catch (IOException e) {
            log.error(e.getMessage());
            result.setStatus("not_ok");
            result.setMessage(e.getMessage());
        } catch (SlackApiException e) {
            log.error(e.getMessage());
            result.setStatus("not_ok");
            result.setMessage(e.getMessage());
        } catch (NoSuchElementException e) {
            log.error(e.getMessage() + " : " + request.getChannel());
            result.setStatus("not_ok");
            result.setMessage(e.getMessage() + " : " + request.getChannel());
        }

        return result;
    }

    private String findChannel(String token, String channelName)
            throws IOException, SlackApiException, NoSuchElementException {
        Gson gson = new Gson();
        try {
            Channel pub = findPublicChannel(token, channelName);
            log.info("Public channel found => " + gson.toJson(pub));
            return pub.getId();
        } catch (NoSuchElementException e) {
            log.warn(channelName + " is not in public channels.");
        }

        Group priv = findPrivateChannel(token, channelName);
        log.info("Private channel found => " + gson.toJson(priv));
        return priv.getId();
    }

    private Channel findPublicChannel(String token, String channelName)
            throws IOException, SlackApiException, NoSuchElementException {
        Slack slack = Slack.getInstance();
        ChannelsListResponse channels = slack.methods().channelsList(
                ChannelsListRequest.builder().token(token).build());
        return channels.getChannels().stream()
                .filter(c -> c.getName().equals(channelName)).findFirst().get();

    }

    private Group findPrivateChannel(String token, String channelName)
            throws IOException, SlackApiException, NoSuchElementException {
        Slack slack = Slack.getInstance();
        GroupsListResponse groups = slack.methods().groupsList(
                GroupsListRequest.builder().token(token).build());
        return groups.getGroups().stream()
                .filter(g -> g.getName().equals(channelName)).findFirst().get();
    }
}
