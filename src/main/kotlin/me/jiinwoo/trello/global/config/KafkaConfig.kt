package me.jiinwoo.trello.global.config

import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka

@Configuration
@EnableKafka
class KafkaConfig {


//    @Bean
//    fun kafkaTemplate(): KafkaTemplate<String, ChattingMessage> {
//        return KafkaTemplate(producerFactory())
//    }



//    @Bean
//    fun kafkaListenerContainerFactory(): ConcurrentKafkaListenerContainerFactory<String, ChattingMessage> {
//        val factory = ConcurrentKafkaListenerContainerFactory<String, ChattingMessage>()
//        factory.consumerFactory = consumerFactory()
//        return factory
//    }
//
//    @Bean
//    fun consumerFactory(): ConsumerFactory<String, ChattingMessage> {
//        return DefaultKafkaConsumerFactory(consumerConfigs(), null, JsonDeserializer(ChattingMessage::class.java))
//    }
//
//    @Bean
//    fun consumerConfigs(): Map<String, Any> {
//        return mapOf<String, Any>(
//            "bootstrap.servers" to "localhost:9092",
//            "key.deserializer" to IntegerSerializer::class.java,
//            "value.deserializer" to JsonDeserializer::class.java,
//            "group.id" to "spring-boot-test"
//        )
//    }

}