package pl.piomin.microservices.account.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/retry")
@Slf4j
public class RetryTestController {

    private int counter=0;

    @GetMapping
    ResponseEntity<String>tryRetry(){
        if (counter<3){
            counter++;
            log.info("bad retry");
            return ResponseEntity.status(502).body("bad response");
        }
        else {
            counter=0;
            log.info("ok retry");
            return ResponseEntity.ok("ok");
        }
    }
}
