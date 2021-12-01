package com.blink.blinkshopping.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BaseSubCategory(
        var data: subData?
) : Parcelable {}

@Parcelize
data class subData(
        val categoryList: List<Category>
) : Parcelable {}

@Parcelize
data class Category(
        val children: List<Children>,
        val children_count: String,
        val id: Int,
        val image: String,
        val include_in_menu: Int,
        val level: Int,
        val name: String,
        val path: String,
        val position: Int,
        val url_path: String
) : Parcelable {}

@Parcelize
data class Children(
        val children: List<ChildrenX>,
        val children_count: String,
        val id: Int,
        val image: String,
        val include_in_menu: Int,
        val level: Int,
        val name: String,
        val path: String,
        val position: Int,
        val url_path: String
) : Parcelable {}

@Parcelize
data class ChildrenX(
        val children: List<ChildrenXX>,
        val children_count: String,
        val id: Int,
        val image: String,
        val include_in_menu: Int,
        val level: Int,
        val name: String,
        val path: String,
        val position: Int,
        val url_path: String
) : Parcelable {}


@Parcelize
data class ChildrenXX(
        val children_count: String,
        val id: Int,
        val image: String,
        val include_in_menu: Int,
        val level: Int,
        val name: String,
        val path: String,
        val position: Int,
        val url_path: String
) : Parcelable {}

