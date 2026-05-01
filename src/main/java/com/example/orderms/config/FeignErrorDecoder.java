package com.example.orderms.config;

import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.stereotype.Component;

@Component
public class FeignErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {

        int status = response.status();

        if (status == 400) {
            return new RuntimeException("Bad request from external service: " + methodKey);
        }

        if (status == 401) {
            return new RuntimeException("Unauthorized call to external service: " + methodKey);
        }

        if (status == 404) {
            return new RuntimeException("Resource not found in external service: " + methodKey);
        }

        if (status == 409) {
            return new RuntimeException("Conflict in external service: " + methodKey);
        }

        if (status >= 500) {
            return new RuntimeException("External service internal error: " + methodKey);
        }

        return new RuntimeException("Unknown Feign error: " + status);
    }
}
