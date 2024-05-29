package com.task.botvk.service;

import com.task.botvk.dto.CallbackDto;

public interface CallbackService {
    String handleCallback(CallbackDto callbackDto);
}
