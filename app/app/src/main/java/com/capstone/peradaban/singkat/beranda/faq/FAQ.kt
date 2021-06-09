package com.capstone.peradaban.singkat.beranda.faq

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FAQ(
    var pertanyaan: String,
    var jawaban: String
    ) : Parcelable