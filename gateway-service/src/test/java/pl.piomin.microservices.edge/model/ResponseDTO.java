package pl.piomin.microservices.edge.model;

import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseDTO {

    private String status;
    private String message;
}
