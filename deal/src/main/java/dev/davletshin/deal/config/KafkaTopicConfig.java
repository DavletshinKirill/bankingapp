package dev.davletshin.deal.config;

import dev.davletshin.deal.domain.Topic;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.config.TopicConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

import java.time.Duration;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic finishRegistrationTopic() {
        return TopicBuilder.name(Topic.FINISH_REGISTRATION.getTopicTitle())
                .partitions(5)
                .replicas(1)
                .config(
                        TopicConfig.RETENTION_MS_CONFIG,
                        String.valueOf(Duration.ofDays(7).toMillis())
                )
                .build();
    }

    @Bean
    public NewTopic createDocumentsTopic() {
        return TopicBuilder.name(Topic.CREATE_DOCUMENTS.getTopicTitle())
                .partitions(5)
                .replicas(1)
                .config(
                        TopicConfig.RETENTION_MS_CONFIG,
                        String.valueOf(Duration.ofDays(7).toMillis())
                )
                .build();
    }

    @Bean
    public NewTopic sendDocumentsTopic() {
        return TopicBuilder.name(Topic.SEND_DOCUMENTS.getTopicTitle())
                .partitions(5)
                .replicas(1)
                .config(
                        TopicConfig.RETENTION_MS_CONFIG,
                        String.valueOf(Duration.ofDays(7).toMillis())
                )
                .build();
    }

    @Bean
    public NewTopic sendSesTopic() {
        return TopicBuilder.name(Topic.SEND_SES.getTopicTitle())
                .partitions(5)
                .replicas(1)
                .config(
                        TopicConfig.RETENTION_MS_CONFIG,
                        String.valueOf(Duration.ofDays(7).toMillis())
                )
                .build();
    }

    @Bean
    public NewTopic creditIssuedTopic() {
        return TopicBuilder.name(Topic.CREDIT_ISSUED.getTopicTitle())
                .partitions(5)
                .replicas(1)
                .config(
                        TopicConfig.RETENTION_MS_CONFIG,
                        String.valueOf(Duration.ofDays(7).toMillis())
                )
                .build();
    }

    @Bean
    public NewTopic statementDeniedTopic() {
        return TopicBuilder.name(Topic.STATEMENT_DENIED.getTopicTitle())
                .partitions(5)
                .replicas(1)
                .config(
                        TopicConfig.RETENTION_MS_CONFIG,
                        String.valueOf(Duration.ofDays(7).toMillis())
                )
                .build();
    }
}
