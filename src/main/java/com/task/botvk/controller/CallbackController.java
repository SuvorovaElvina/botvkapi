package com.task.botvk.controller;

import com.task.botvk.dto.CallbackDto;
import com.task.botvk.service.CallbackService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/callback")
@RequiredArgsConstructor
public class CallbackController {
    private final CallbackService callbackService;

    @PostMapping
    @ResponseBody
    public ResponseEntity<String> handleCallback(@RequestBody CallbackDto callbackDto) {
        log.info("Контроллер получил данные: {}", callbackDto);
        return new ResponseEntity<>(callbackService.handleCallback(callbackDto), HttpStatus.OK);
    }
}
