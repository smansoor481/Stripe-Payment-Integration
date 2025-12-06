package com.HulkHire_Tech.Payment.Config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    ChatClient chatClient(ChatClient.Builder ChatClientBuilder) {
        return ChatClientBuilder.build();
    }
}
