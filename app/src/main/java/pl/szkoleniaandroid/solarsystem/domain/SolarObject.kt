package pl.szkoleniaandroid.solarsystem.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class SolarObject(
    val id: String,
    val name: String,
    val imageUrl: String,
    val video: String? = null
) : Parcelable