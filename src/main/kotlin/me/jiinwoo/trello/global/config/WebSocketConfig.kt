package me.jiinwoo.trello.global.config

import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer

@EnableWebSocketMessageBroker
class WebSocketConfig : WebSocketMessageBrokerConfigurer{
}