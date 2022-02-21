package ru.kirea.filmswithpagination.states

/**
 * Базовое состояние.
 * Если в sealed class наследоваться от данного sealed interface, то во ViewModel<BaseState>
 * можно передавать состояния как из sealed class, так и из данного sealed interface.
 */
sealed interface BaseState {
    object Loading: BaseState
    data class Error(val text: String?): BaseState
    object Null: BaseState
}