package com.example.testingtimetutorial;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static org.mockito.BDDMockito.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc()
class ShouldIDeployIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  Clock clock;

  @Test
  void setTheClockTo1999() throws Exception {

    given(clock.instant()).willReturn(Instant.parse("1999-09-19T19:19:00Z"));
    given(clock.getZone()).willReturn(ZoneId.of("UCT"));

    System.out.println(LocalDateTime.now(clock));

    mockMvc.perform(MockMvcRequestBuilders.get("/now")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.content().string("\"1999-09-19T19:19:00\""));
  }
}