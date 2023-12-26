package com.rooze.lib.basicads.data

enum class AdState {
    INIT, LOADING, FAILED, SUCCESS;

    val isLoading: Boolean get() = this == INIT || this == LOADING
    val shouldLoad: Boolean get() = this == FAILED || this == INIT
}