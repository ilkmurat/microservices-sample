package com.murat.cloud.gateway;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class FallBackMethodController {

    private RestTemplate restTemplate;

    private final String BORED_API = "http://localhost:9191/";

    public FallBackMethodController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
/**
    @GetMapping
    @CircuitBreaker(name = "randomActivity", fallbackMethod = "userServiceFallBack")
    public String userService() {
        ResponseEntity<Activity> responseEntity = restTemplate.getForEntity(BORED_API, Activity.class);
        Activity activity = responseEntity.getBody();
        log.info("Activity received: " + activity.getActivity());
        return activity.getActivity();
    }
*/
    @GetMapping("/userServiceFallBack")
    public String userServiceFallBackMethod() {
        return "User Service is taking longer than Expected." +
                " Please try again later";
    }

    @GetMapping("/departmentServiceFallBack")
    public String departmentServiceFallBackMethod() {
        return "Department Service is taking longer than Expected." +
                " Please try again later";
    }
}
