package com.sametb.cinequiltapp._custom

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

fun decodeString(s: String?): String = URLDecoder.decode(s, StandardCharsets.UTF_8)

@Throws(JsonProcessingException::class)
fun prettyJsonMaker(o: Any): String {
    val objectMapper = ObjectMapper()
    return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(o)
}
