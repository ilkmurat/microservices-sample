package com.murat.user.service;

import com.murat.user.VO.Department;
import com.murat.user.VO.ResponseTemplateVO;
import com.murat.user.entity.User;
import com.murat.user.repository.UserRepository;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class UserService implements IUserService{

    Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestTemplate restTemplate;

    @CircuitBreaker(name = "service1", fallbackMethod = "fallbackForSaveUser")
    // @RateLimiter(name = "service1", fallbackMethod = "rateLimiterfallback")
    // @Retry(name = "retryService1", fallbackMethod = "retryfallback")
    // @Bulkhead(name = "bulkheadService1", fallbackMethod = "bulkHeadFallback")
    public User saveUser(User user) {
        log.info("saveUser -->UserService");
        return userRepository.save(user);
    }

    @CircuitBreaker(name = "service2", fallbackMethod = "fallbackForGetUserWithDepartment")
    @Bulkhead(name = "bulkheadService1", fallbackMethod = "bulkHeadFallback")
    @RateLimiter(name = "service1", fallbackMethod = "rateLimiterfallback")
    @Retry(name = "retryService1", fallbackMethod = "retryfallback")
    public ResponseTemplateVO getUserWithDepartment(Long userId) {
        log.info("getUserWithDepartment -->UserService");
        ResponseTemplateVO vo = new ResponseTemplateVO();
        User user = userRepository.findByUserId(userId);
    Department department =
            restTemplate.getForObject("http://department-service/departments/" + user.getDepartmentId(),
                    Department.class);


    vo.setUser(user);
    vo.setDepartment(department);


        return vo;
    }

    public ResponseTemplateVO bulkHeadFallback(Long userId, Throwable t) {
        ResponseTemplateVO responseTemplateVO = new ResponseTemplateVO();
        logger.error("Inside bulkHeadFallback, cause - {}", t.toString());
        return responseTemplateVO;
    }

    public ResponseTemplateVO retryfallback(Long userId, Throwable t) {
        ResponseTemplateVO responseTemplateVO = new ResponseTemplateVO();
      logger.error("Inside retryfallback, cause - {}", t.toString());
      return responseTemplateVO;
    }
    public User fallbackForSaveUser(User user, Throwable t ){
        User user1 = new User();

        logger.error("Inside circuit breaker fallbackForRegisterSeller, cause - {}", t.toString());
        return user1;
        //return "Inside circuit breaker fallback method. Some error occurred while calling service for User";
    }

    public ResponseTemplateVO fallbackForGetUserWithDepartment(Long userId ,Throwable t) {
        ResponseTemplateVO responseTemplateVO = new ResponseTemplateVO();
        logger.error("Inside fallbackForGetUserWithDepartment, cause - {}", t.toString());
        return responseTemplateVO;
    }

    public ResponseTemplateVO rateLimiterfallback(Long userId, Throwable t) {
        ResponseTemplateVO responseTemplateVO = new ResponseTemplateVO();
        logger.error("Inside rateLimiterfallback, cause - {}", t.toString());
        return responseTemplateVO;
    }
}
