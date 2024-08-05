package org.teamtuna.yaguroute.config;

import org.apache.kafka.common.TopicPartition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.ExponentialBackOffWithMaxRetries;
import org.teamtuna.yaguroute.dto.MailDTO;

@EnableKafka
@Configuration
public class KafkaConfig {

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, MailDTO> kafkaListenerContainerFactory(ConsumerFactory<String, MailDTO> consumerFactory, KafkaTemplate<String, MailDTO> kafkaTemplate) {
        ConcurrentKafkaListenerContainerFactory<String, MailDTO> factory = new ConcurrentKafkaListenerContainerFactory<>();

        DeadLetterPublishingRecoverer recoverer = new DeadLetterPublishingRecoverer(kafkaTemplate,
                (record, exception) -> {
                    if (record != null) {
                        return new TopicPartition("email-dlq", record.partition());
                    } else {
                        return new TopicPartition("unknown.DLT", -1);
                    }
                });

        ExponentialBackOffWithMaxRetries backOff = new ExponentialBackOffWithMaxRetries(3);
        backOff.setInitialInterval(1000L);
        backOff.setMultiplier(2.0);
        backOff.setMaxInterval(10000L);

        factory.setConsumerFactory(consumerFactory);
        factory.setCommonErrorHandler(new DefaultErrorHandler(recoverer, backOff));

        return factory;
    }
}
