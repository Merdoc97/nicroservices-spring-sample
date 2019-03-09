package hello.advice;

import hello.dto.ErrorDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;


@Controller
@ControllerAdvice
public class WebSocketGeneralController {

    @Autowired
    private SimpMessageSendingOperations simpMessageSendingOperations;

    /**
     * current exception handler sends error only to client who sends not correct request, not for all subscribers
     * @param e
     * @param accessor
     */
    @MessageExceptionHandler(value = {Exception.class})
    public void handleException(Exception e, SimpMessageHeaderAccessor accessor) {


        simpMessageSendingOperations.convertAndSend("/topic/errors/" + accessor.getSessionId(), new ErrorDTO(HttpStatus.BAD_REQUEST.getReasonPhrase(), accessor.getDestination(), e.getMessage()));

    }
}
