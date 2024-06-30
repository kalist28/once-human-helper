package ru.kalistratov.duckside.raidhelper.core

interface BaseIntent
interface BaseAction
interface SideEffect

interface BaseViewState {
    companion object {
        val empty get() = object : BaseViewState {}
    }
}