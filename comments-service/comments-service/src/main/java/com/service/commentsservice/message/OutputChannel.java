package com.service.commentsservice.message;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface OutputChannel {

    @Output
    MessageChannel output();
}
