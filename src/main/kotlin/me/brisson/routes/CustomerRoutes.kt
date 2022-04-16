package me.brisson.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import me.brisson.plugins.customerCollection
import me.brisson.models.Customer
import org.litote.kmongo.eq

fun Route.customerRouting() {
    route("/customer") {

        // Getting all customers
        get {
            val customers = customerCollection.find().toList()
            call.respond(customers)
        }

        // Getting one customer by id
        get("{id?}") {
            val id = call.parameters["id"] ?: return@get call.respondText(
                text = "Missing id",
                status = HttpStatusCode.BadRequest
            )

            val customer =
                customerCollection.findOne(Customer::id eq id) ?: return@get call.respondText(
                    text = "No customer with id $id",
                    status = HttpStatusCode.NotFound
                )

            call.respond(customer)
        }

        // Creating a customer
        post {
            val customerBody = call.receive<Customer>()

            if (customerCollection.findOne(Customer::email eq customerBody.email) == null){
                customerCollection.insertOne(customerBody)
                val customerResponse = customerCollection.findOne(Customer::email eq customerBody.email)
                call.respond(customerResponse!!)
            } else {
                call.respondText(text = "This email is already registered", status = HttpStatusCode.MethodNotAllowed)
            }

        }

        // Deleting a customer by id
        delete("{id?}") {
            val id = call.parameters["id"] ?: return@delete call.respondText(
                text = "Missing id",
                status = HttpStatusCode.BadRequest
            )

            if (customerCollection.deleteOne(Customer::id eq id).wasAcknowledged()) {
                call.respondText(
                    text = "Customer removed correctly",
                    status = HttpStatusCode.Accepted
                )
            } else {
                call.respondText(
                    text = "Not found",
                    status = HttpStatusCode.NotFound
                )
            }

        }
    }
}