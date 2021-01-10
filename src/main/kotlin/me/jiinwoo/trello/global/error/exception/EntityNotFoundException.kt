package me.jiinwoo.trello.global.error.exception

open class EntityNotFoundException(message: String?) : BusinessException(message, ErrorCode.ENTITY_NOT_FOUND)