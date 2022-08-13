package com.example.demo.application.controller

import com.example.demo.application.resource.HelloRequest
import com.example.demo.application.resource.HelloResponse
import com.example.demo.domain.`object`.Greeter
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("greeter")
class GreeterController(
  @Qualifier("BadGreeterImpl")
  private val greeter: Greeter
) {
  @GetMapping("/hello")
  fun hello(@RequestParam("name") name: String): HelloResponse {
    return HelloResponse("Hello ${name}")
  }

  @GetMapping("/hello/{name}")
  fun helloPathValue(@PathVariable("name") name: String): HelloResponse {
    return HelloResponse("Hello $name")
  }

  @GetMapping("/hello/byservice/{name}")
  fun helloByService(@PathVariable("name") name: String): HelloResponse {
    val message = greeter.sayHello(name)
    return HelloResponse(message)
  }

  @PostMapping("hello")
  fun helloByPost(@RequestBody request: HelloRequest): HelloResponse {
    return HelloResponse("Hello ${request.name}")
  }
}

