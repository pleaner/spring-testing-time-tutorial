package com.example.testingtimetutorial;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;

@SpringBootTest
class ShouldIDeployServiceUnitTest {

  @Autowired MyDateTimeService serviceUnderTest;

  @MockBean Clock clock;

  @Test
  void shouldIDeployOnTuesday() {

    // Set the Mocked Time
    given(clock.instant()).willReturn(Instant.parse("2001-01-01T12:15:00Z"));
    given(clock.getZone()).willReturn(ZoneId.of("UCT"));

    String actual = serviceUnderTest.currentTime().toString();
    assertThat(actual).isEqualTo("2001-01-01T12:15");
  }

  @Test
  void shouldIDeployOnFriday() {

    // Set the Mocked Time
    given(clock.instant()).willReturn(Instant.parse("2023-03-03T03:03:00Z"));
    given(clock.getZone()).willReturn(ZoneId.of("UCT"));

    // Inject the Fixed Clock
    String actual = serviceUnderTest.currentTime().toString();
    assertThat(actual).isEqualTo("2023-03-03T03:03");
  }
}