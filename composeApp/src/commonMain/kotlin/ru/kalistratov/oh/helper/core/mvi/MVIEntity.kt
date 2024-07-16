package ru.kalistratov.oh.helper.core.mvi

interface BaseIntent
interface BaseAction
interface SideEffect

interface BaseViewState {
    companion object {
        val empty get() = object : BaseViewState {}
    }
}