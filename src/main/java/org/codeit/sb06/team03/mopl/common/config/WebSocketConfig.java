package org.codeit.sb06.team03.mopl.common.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.codeit.sb06.team03.mopl.common.StompAuthInboundInterceptor;
import org.codeit.sb06.team03.mopl.liveChat.infra.in.web.StompContentInboundInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Slf4j
@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private final StompAuthInboundInterceptor stompAuthInboundInterceptor;
    private final StompContentInboundInterceptor stompContentInboundInterceptor;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/sub") // 클라이언트 + 서버가 broker에 요청하는 destination에 대한 prefix
                .setHeartbeatValue(new long[]{4000, 4000})
                .setTaskScheduler(websocketTaskScheduler());
        registry.setApplicationDestinationPrefixes("/pub"); // 서버의 controller mapping에 대한 prefix
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")
                .setAllowedOriginPatterns("*")
                .withSockJS();
        registry.addEndpoint("/ws")
                .setAllowedOriginPatterns("*");
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.executor(websocketInboundExecutor())
                .interceptors(stompAuthInboundInterceptor, stompContentInboundInterceptor);
    }

    @Override
    public void configureClientOutboundChannel(ChannelRegistration registration) {
        registration.executor(websocketOutboundExecutor());
    }

    @Bean("websocketTaskScheduler")
    public TaskScheduler websocketTaskScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(Runtime.getRuntime().availableProcessors());
        scheduler.setThreadNamePrefix("web-socket-");
        scheduler.setWaitForTasksToCompleteOnShutdown(true);
        scheduler.setAwaitTerminationSeconds(10);
        scheduler.setRemoveOnCancelPolicy(true);
        scheduler.setErrorHandler(t -> log.error("websocketTaskScheduler error: {}", t.getMessage()));
        scheduler.initialize();
        return scheduler;
    }

    @Bean("websocketInboundExecutor")
    public TaskExecutor websocketInboundExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(16);
        executor.setMaxPoolSize(32);
        executor.setQueueCapacity(1000);
        executor.setThreadNamePrefix("inbound-");
        executor.setWaitForTasksToCompleteOnShutdown(false);
        executor.setKeepAliveSeconds(60);
        executor.initialize();
        return executor;
    }

    @Bean("websocketOutboundExecutor")
    public TaskExecutor websocketOutboundExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(16);
        executor.setMaxPoolSize(32);
        executor.setQueueCapacity(1000);
        executor.setThreadNamePrefix("outbound-");
        executor.setWaitForTasksToCompleteOnShutdown(false);
        executor.setKeepAliveSeconds(60);
        executor.initialize();
        return executor;
    }
}
