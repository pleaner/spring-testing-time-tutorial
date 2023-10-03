package com.example.testingtimetutorial;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
public record MyDateTimeController(
    MyDateTimeService service
) {

  @GetMapping("now")
  LocalDateTime shouldI(){
    return service.currentTime();
  }
}
