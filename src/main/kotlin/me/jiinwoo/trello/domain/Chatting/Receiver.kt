package me.jiinwoo.trello.domain.Chatting

import com.fasterxml.jackson.databind.ObjectMapper
import me.jiinwoo.trello.global.config.Log
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Service

@Service
class Receiver(
    private val template: SimpMessagingTemplate,
    private val objectMapper: ObjectMapper
) {

    companion object : Log

    @KafkaListener(id = "main-listener", topics = ["kafka-chatting"])
    fun receive(message: ChattingMessage) {
        logger.info("message='{}'", message)
        val msg = mapOf<String, Any>(
            "timestamp" to message.timestamp.toString(),
            "message" to message.message,
            "author" to message.user
        )
        template.convertAndSend("/topic/public",objectMapper.writeValueAsString(msg))
    }
}