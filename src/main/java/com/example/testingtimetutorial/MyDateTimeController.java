package com.example.testingtimetutorial;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("now")
public record MyDateTimeController(
    MyDateTimeService service
) {

  @GetMapping
  LocalDateTime shouldI(){
    return service.currentTime();
  }
}
