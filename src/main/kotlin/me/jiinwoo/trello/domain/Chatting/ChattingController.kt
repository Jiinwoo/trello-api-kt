package me.jiinwoo.trello.domain.Chatting


import me.jiinwoo.trello.global.config.Log
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.RestController

@RestController
@CrossOrigin
class ChattingController (
    private val sender: Sender,
    private val receiver: Receiver
        ){

    companion object: Log {
        const val BOOT_TOPIC = "kafka-chatting"
    }

    @MessageMapping("/message")
    fun sendMessage(message: ChattingMessage) {
        message.timestamp = System.currentTimeMillis()
        sender.send(BOOT_TOPIC, message)
    }


}