package pl.piomin.microservices.edge.test_controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.piomin.microservices.edge.model.RequestDTO;
import pl.piomin.microservices.edge.model.ResponseDTO;

@RestController
public class TestControllers {

    @GetMapping("/post-filter")
    public ResponseEntity<HttpStatus> testPostFilter() {
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/pre-filter")
    public ResponseEntity<HttpStatus> testPreFilter(@RequestHeader("TEST_PRE_FILTER") String header) {
        if (header.equalsIgnoreCase("test"))
            return ResponseEntity.status(HttpStatus.OK).build();
        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }


    @PostMapping(value = "/route-filter", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDTO testRouteFilter(@RequestBody RequestDTO requestDTO) {
        if (requestDTO.getMessage().equalsIgnoreCase("converted"))
            return ResponseDTO.builder()
                    .message("passed")
                    .status("200")
                    .build();
        else return ResponseDTO.builder()
                .status("400")
                .build();
    }

}
