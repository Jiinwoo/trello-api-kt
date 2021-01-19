package me.jiinwoo.trello.domain.Chatting

import me.jiinwoo.trello.global.config.Log
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class Sender(
    private val kafkaTemplate: KafkaTemplate<String, ChattingMessage>
) {

    companion object : Log

    fun send(topic: String, data: ChattingMessage) {
        logger.info("sending data='{}' to topic='{}'", data, topic)
        kafkaTemplate.send(topic, data)
    }

}