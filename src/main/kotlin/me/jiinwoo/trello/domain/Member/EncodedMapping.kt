package me.jiinwoo.trello.domain.Member

import org.mapstruct.Qualifier

@Qualifier
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.TYPE)
@Retention(AnnotationRetention.SOURCE)
annotation class EncodedMapping()
