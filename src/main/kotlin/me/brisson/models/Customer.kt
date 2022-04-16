package me.brisson.models

import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import java.util.UUID

@Serializable
data class Customer(
    @BsonId
    val id: String = UUID.randomUUID().toString().replace("-", ""),
    val firstName: String,
    val lastName: String,
    val email: String,
    val createdAt : Long = System.currentTimeMillis()
)