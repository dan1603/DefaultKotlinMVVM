package com.kalashnyk.denys.moduleproject.data_source.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName="category")
data class CategoryEntity(

    /**
     *
     */
    @PrimaryKey(autoGenerate=true)
    @SerializedName("id")
    val id: Int,
    /**
     *
     */
    @SerializedName("name")
    val name: String
)