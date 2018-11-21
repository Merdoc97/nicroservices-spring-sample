package pl.piomin.microservices.edge.model;

import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class RequestDTO {

    private String message;
}
