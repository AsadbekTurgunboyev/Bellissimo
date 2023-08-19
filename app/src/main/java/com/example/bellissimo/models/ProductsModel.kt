package com.example.bellissimo.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductItemModel(
    val productName: String? = "",
    val productImage: Int? = null ,
    val productPrice: Int? = null,
    val categoryName: String? = "",
    val productDescription: String? = "",
    val smallSize: ProductSize? = null,
    val mediumSize: ProductSize? = null,
    val largeSize: ProductSize? = null,

): Parcelable

@Parcelize
data class ProductSize(
    val isActive: Boolean? = false,
    val price: Int? = null,
    val text: String? = ""
): Parcelable


