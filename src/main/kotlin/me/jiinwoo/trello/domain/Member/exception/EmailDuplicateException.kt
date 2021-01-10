package me.jiinwoo.trello.domain.Member.exception

import me.jiinwoo.trello.global.error.exception.ErrorCode
import me.jiinwoo.trello.global.error.exception.InvalidValueException

class EmailDuplicateException(email: String): InvalidValueException(email, ErrorCode.EMAIL_DUPLICATION) {
}