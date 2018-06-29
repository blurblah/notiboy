package net.blurblah.notiboy.mailsender.rest;


import net.blurblah.notiboy.mailsender.provider.MailProvider;
import net.blurblah.notiboy.util.JsonpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class MailController {

    @Autowired
    private MailProvider provider;

	@RequestMapping(value = "/test/{id}", method = RequestMethod.GET, produces = "application/json")     
    public String check(@PathVariable int id) {
        return "ok " + id;
    }
	
	@RequestMapping(value="/mail", method = RequestMethod.POST)
    public String send(@RequestParam(value = "callback", required = false) String callback, @RequestBody String payload) throws Exception {
		return JsonpUtil.checkJsonP(null, provider.request(null, payload));
    }
	
}
