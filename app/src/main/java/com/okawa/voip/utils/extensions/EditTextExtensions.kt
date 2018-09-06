package com.okawa.voip.utils.extensions

import android.widget.EditText

fun EditText.isEmpty() = text.isEmpty()

fun EditText.clear() {
    setText("")
}

fun EditText.adjustCursor() {
    setSelection(text.length)
}

fun EditText.getTextString() = text.toString()