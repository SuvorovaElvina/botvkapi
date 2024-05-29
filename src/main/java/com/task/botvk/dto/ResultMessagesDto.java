package com.task.botvk.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResultMessagesDto {
    @JsonProperty(value = "peer_id")
    Long peerId;

    @JsonProperty(value = "message_id")
    Long messageId;

    ErrorResultMessagesDto error;
}
