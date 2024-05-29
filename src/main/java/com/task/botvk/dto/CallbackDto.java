package com.task.botvk.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.task.botvk.dto.enums.CallbackType;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Map;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CallbackDto {
    CallbackType type;

    Map<String, Object> object;

    @JsonProperty(value = "group_id")
    Long groupId;

    String secret;

    @JsonProperty(value = "event_id")
    String eventId;
}
