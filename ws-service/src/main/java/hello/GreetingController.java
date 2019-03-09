package hello;

import hello.dto.RequestDTO;
import hello.dto.ResponseDTO;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.HtmlUtils;

import javax.validation.Valid;

@RestController
public class GreetingController {


    @MessageMapping({"/hello"})
    @SendTo("/topic/greetings")
    public Greeting greeting(@Valid @RequestBody HelloMessage message) throws Exception {
        System.out.println("get message "+message.getName());
        return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
    }

    @MessageMapping("/request")
    @SendTo("/topic/response")
    public ResponseDTO request(RequestDTO message) throws Exception {
        System.out.println("get message "+message.getValue());
        return new ResponseDTO(message.getValue());
    }

}
