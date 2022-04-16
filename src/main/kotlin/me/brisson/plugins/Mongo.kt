package me.brisson.plugins

import me.brisson.models.Customer
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo

const val DATABASE = "ktor-exp"

val client = KMongo.createClient().coroutine
val database = client.getDatabase(DATABASE)

// Collections
val customerCollection = database.getCollection<Customer>()