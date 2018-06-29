package net.blurblah.notiboy.mailsender;


import net.blurblah.notiboy.mailsender.MailRequest;
import net.blurblah.notiboy.mailsender.MailProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
public class MailController {

    @Autowired
    private MailProvider provider;

	@RequestMapping(value="/mail", method=RequestMethod.POST, consumes="application/json")
    public String send(@Valid @RequestBody MailRequest payload) {
        // payload fields already checked
		return provider.send(payload);
    }
}
