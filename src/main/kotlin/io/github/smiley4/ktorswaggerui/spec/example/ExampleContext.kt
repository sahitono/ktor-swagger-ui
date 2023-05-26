package io.github.smiley4.ktorswaggerui.spec.example

import io.github.smiley4.ktorswaggerui.dsl.OpenApiRequestParameter
import io.github.smiley4.ktorswaggerui.dsl.OpenApiSimpleBody
import io.swagger.v3.oas.models.examples.Example

class ExampleContext {

    private val examplesParameters = mutableMapOf<OpenApiRequestParameter, String>()
    private val examplesBody = mutableMapOf<Pair<OpenApiSimpleBody, String>, Example>()


    fun addExample(parameter: OpenApiRequestParameter, value: String) {
        examplesParameters[parameter] = value
    }

    fun addExample(body: OpenApiSimpleBody, name: String, value: Example) {
        examplesBody[body to name] = value
    }

    fun finalize() {
        // TODO
    }

    fun getExample(parameter: OpenApiRequestParameter): String? {
        return examplesParameters[parameter]
    }

    fun getExample(body: OpenApiSimpleBody, name: String): Example? {
        return examplesBody[body to name]
    }

}