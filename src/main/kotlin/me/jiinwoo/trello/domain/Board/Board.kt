package me.jiinwoo.trello.domain.Board

import me.jiinwoo.trello.domain.model.AuditingEntity
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table


@Table(name = "board")
@Entity
class Board(
    @Column(name = "title")
    var title: String,
): AuditingEntity()