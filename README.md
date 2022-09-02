# Ktor Swagger-UI

[![](https://jitpack.io/v/SMILEY4/ktor-swagger-ui.svg)](https://jitpack.io/#SMILEY4/ktor-swagger-ui)


This library provides a Ktor plugin to document routes, generate an OpenApi Specification and serve a Swagger UI. It is meant to be  minimally invasive, meaning it can be plugged into existing application without requiring immediate changes to the code. Routes can then be gradually enhanced with documentation.


## Features

- minimally invasive (no immediate change to existing code required)
- provides swagger-ui with no initial configuration required
- supports most of the [OpenAPI 3.0.3 Specification](https://swagger.io/specification/)
- Authentication (Basic, JWT, ...)
- automatic json-schema generation from arbitrary types/classes for bodies and parameters
- no annotations


## Documentation

A wiki with a short documentation is available [here](https://github.com/SMILEY4/ktor-swagger-ui/wiki)


## Installation

Step 1. Add the JitPack repository

```kotlin
repositories {
    maven { url "https://jitpack.io" }
}
```

Step 2. Add the dependency

```kotlin
dependencies {
    implementation 'io.github.smiley4:ktor-swagger-ui:<VERSION>'
}
```


## Example

Full examples can be found in `src/test/kotlin/io/github/smiley4/swaggerui/examples`.

```kotlin
install(SwaggerUI) {
    swagger {
        swaggerUrl = "swagger-ui"
        forwardRoot = true
    }
    info {
        title = "Example API"
        version = "latest"
        description = "Example API for testing and demonstration purposes."
    }
    server {
        url = "http://localhost:8080"
        description = "Development Server"
    }
}
```

```kotlin
get("hello", {
    description = "Hello World Endpoint."
    response {
        HttpStatusCode.OK to {
            description = "Successful Request"
            body(String::class.java) { description = "the response" }
        }
        HttpStatusCode.InternalServerError to {
            description = "Something unexpected happened"
        }
    }
}) {
    call.respondText("Hello World!")
}
```

```kotlin
post("math/{operation}", {
    tags = listOf("test")
    description = "Performs the given operation on the given values and returns the result"
    request {
        pathParameter("operation", String::class.java) {
            description = "the math operation to perform. Either 'add' or 'sub'"
        }
        body(MathRequest::class.java)
    }
    response {
        HttpStatusCode.OK to {
            description = "The operation was successful"
            body(MathResult::class.java) {
                description = "The result of the operation"
            }
        }
        HttpStatusCode.BadRequest to {
            description = "An invalid operation was provided"
        }
    }
}) {
    val operation = call.parameters["operation"]!!
    call.receive<MathRequest>().let { request ->
        when (operation) {
            "add" -> call.respond(HttpStatusCode.OK, MathResult(request.a + request.b))
            "sub" -> call.respond(HttpStatusCode.OK, MathResult(request.a - request.b))
            else -> call.respond(HttpStatusCode.BadRequest, Unit)
        }
    }
}

data class MathRequest(
    val a: Int,
    val b: Int
)

data class MathResult(
    val value: Int
)
```

