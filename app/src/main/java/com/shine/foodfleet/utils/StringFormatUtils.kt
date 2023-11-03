package com.shine.foodfleet.utils

import android.icu.text.NumberFormat
import android.icu.util.Currency
import android.os.Build
import androidx.annotation.RequiresApi
import kotlin.math.roundToInt

@RequiresApi(Build.VERSION_CODES.N)
fun Double.toCurrencyFormat(): String {
    val format: NumberFormat = NumberFormat.getCurrencyInstance()
    format.maximumFractionDigits = 0
    format.currency = Currency.getInstance("IDR")
    return format.format(this.roundToInt())
}
