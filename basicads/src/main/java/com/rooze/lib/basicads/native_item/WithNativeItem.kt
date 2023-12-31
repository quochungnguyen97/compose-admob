package com.rooze.lib.basicads.native_item

data class WithNativeItem<T>(
    val id: String = "",
    val nativeAbove: Boolean = false,
    val nativeBellow: Boolean = false,
    val data: T,
)
