package org.example.expert.aop;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.example.expert.domain.user.entity.User;
import org.example.expert.domain.user.repository.UserRepository;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.nio.charset.StandardCharsets;
import java.util.Map;

@Slf4j(topic = "UseTimeAop")
@Aspect
@Component
@RequiredArgsConstructor
public class UseTimeAop {

    private final ApiUseTimeRepository apiUseTimeRepository;
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;

    @Pointcut("execution(* org.example.expert.domain.comment.controller.CommentAdminController.*(..))")
    private void deleteComment() {}
    @Pointcut("execution(* org.example.expert.domain.user.controller.UserAdminController.*(..))")
    private void changeUserRole() {}

    @Around("deleteComment() || changeUserRole()")
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
        // 측정 시작 시간
        long time = System.currentTimeMillis();
        Object output = null;

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String url = request.getRequestURI();
        String header = request.getHeader("Authorization");
        Long userId = (Long)request.getAttribute("userId");

        try {
            // 핵심기능 수행
            output = joinPoint.proceed();
            return output;

        } finally {

                    User user = userRepository.findById(userId).orElseThrow(()->
                            new NullPointerException("User not found")
                    );

                if(header != null) {
                    String response = (output != null) ? output.toString() : null;

                    ServletInputStream inputStream = request.getInputStream();
                    String json = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

                    //가독성을 위해 json 형식을 한 줄로 format
                    String requestJson = json;
                    try {
                        Map<String, Object> jsonMap = objectMapper.readValue(requestJson, Map.class);
                        requestJson = objectMapper.writeValueAsString(jsonMap);
                    } catch (JsonProcessingException e) {
                        log.error("Error processing request JSON", e);
                    }

                    ApiUseTime apiUseTime = new ApiUseTime(user, time, url, requestJson, response);

                    log.info("[API Use Time] UserId: {}, Time: {} ms, URL: {}, Request: {}, Response: {}",
                            user.getId(), time, url, requestJson, response);

                    apiUseTimeRepository.save(apiUseTime);
                }
        }
    }
}
