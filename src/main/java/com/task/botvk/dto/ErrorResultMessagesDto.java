package com.task.botvk.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;


@Builder
@Getter
public class ErrorResultMessagesDto {
    @JsonProperty(value = "error_code")
    Long errorCode;

    @JsonProperty(value = "error_msg")
    String errorMsg;
}
