package taa.poc.springbootsoapclient;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class MessageEntity {

    private String url;

    private String contentType;

    private String payload;

    private String originalHeaders;

    private String forwardedHeaders;

    private Integer statusCode;
}
