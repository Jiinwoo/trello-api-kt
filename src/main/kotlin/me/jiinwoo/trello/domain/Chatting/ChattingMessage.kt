package me.jiinwoo.trello.domain.Chatting

class ChattingMessage(
    var message: String,
    var user: String,
    var timestamp: Long,
    var fileName: String,
    var rawData: String,

    )