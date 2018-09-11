package com.okawa.voip.utils.extensions

import android.widget.EditText

fun EditText.clear() {
    setText("")
}

fun EditText.adjustCursor() {
    setSelection(text.length)
}