package com.example.testingtimetutorial;

import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDateTime;

@Service
public record MyDateTimeService(Clock clock) {

  public LocalDateTime currentTime() {
    return LocalDateTime.now(clock);
  }
}
