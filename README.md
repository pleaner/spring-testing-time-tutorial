# Mocking Time in Spring Boot Tests
Often we need to mock time in when testing logic that is dependent on it.
LocalDate, LocalTime, LocalDateTime and ZonedDateTime form the java.time.* package have a method now that accepts an optional clock. 
By creating a bean that returns a Clock and inject it into our service using dependency injection we can mock that bean in the tests.

## Step 1: Create a ClockBean
Create a configuration class and with a @Bean returning a Clock with the systems current time and zone.

```java
@Configuration
public class ClockConfig {
    @Bean
    Clock clock() {
      return Clock.systemDefaultZone();
    }
}
```

## Step 2: Inject the Clock Bean into your class
Inject the Clock into your logic class using dependency injection. Spring will find the bean you created above.
In any calls to `LocalDate.now(clock)`, `LocalTime.now(clock)`, `LocalDateTime.now(clock)` or `ZonedDateTime.now(clock)` pass in the injected clock.
@Service annotation tells spring to create a singleton bean of this class on the class path.


```java
// Annotate your @Service, @RestController or @Component
@Service
public record MyDateTimeService(Clock clock) { // inject the Clock

    public LocalDateTime currentTime() {
    // Pass the injected clock into the now call.
    return LocalDateTime.now(clock);
    }
}
```
## Step 3: Mock the Clock Bean with any time of your choosing
In the SpringBootTest create a @MockBean Clock and set its time and zone with Mockito. Then call the service you wish to test.
```java
@SpringBootTest
class ShouldIDeployServiceUnitTest {

// Inject the service to test
@Autowired MyDateTimeService serviceUnderTest;

// Mock the Clock Bean
@MockBean Clock clock;

@Test
void setTheClockTo2042() {

    // Set the Mocked Time
    given(clock.instant()).willReturn(Instant.parse("2042-01-01T12:15:00Z"));
    given(clock.getZone()).willReturn(ZoneId.of("UCT"));

    // Call the service
    String actual = serviceUnderTest.currentTime().toString();
    assertThat(actual).isEqualTo("2042-01-01T12:15");
}
}
```
The same strategy also works for MockMvc integration tests with a standard rest controller.

```java
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc()
class ShouldIDeployIntegrationTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean Clock clock;
    
    @Test
    void setTheClockTo1999() {
    
        given(clock.instant()).willReturn(Instant.parse("1999-09-19T19:19:00Z"));
        given(clock.getZone()).willReturn(ZoneId.of("UCT"));
    
        System.out.println(LocalDateTime.now(clock));
    
        mockMvc.perform(MockMvcRequestBuilders.get("/now"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().string("\"1999-09-19T19:19:00\""));
    }
}

```
Good Luck out there.

