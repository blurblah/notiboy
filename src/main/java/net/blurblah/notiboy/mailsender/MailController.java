package net.blurblah.notiboy.mailsender;


import com.google.gson.Gson;
import net.blurblah.notiboy.common.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
public class MailController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private MailProvider provider;

	@RequestMapping(value="/mail", method=RequestMethod.POST, consumes="application/json")
    public Result send(@Valid @RequestBody MailRequest payload) {
        // payload fields already checked
        Gson gson = new Gson();
        log.info("Send email request payload => " + gson.toJson(payload));
		return provider.send(payload);
    }
}
