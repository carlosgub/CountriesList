package com.carlosgub.countries.core.sealed

sealed class GenericState<out T> {
    data class Error(val message: String) : GenericState<Nothing>()
    data class Success<T>(val item: T) : GenericState<T>()
}