package me.jiinwoo.trello

import me.jiinwoo.trello.global.config.AppProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@EnableConfigurationProperties(AppProperties::class)
@SpringBootApplication
class TrelloApplication

fun main(args: Array<String>) {
    runApplication<TrelloApplication>(*args)
}
