package me.brisson.plugins

import io.ktor.server.application.*
import io.ktor.server.routing.*
import me.brisson.routes.customerRouting

fun Application.configureRouting() {
    routing {
        customerRouting()
    }
}
