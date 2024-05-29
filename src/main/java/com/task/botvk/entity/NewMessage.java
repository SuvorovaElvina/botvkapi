package com.task.botvk.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NewMessage {
    Long id;

    Long date;

    Long peerId;

    Long fromId;

    String text;

    Long groupId;
}
