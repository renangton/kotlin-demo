package com.example.demo.domain.`object`

import org.springframework.stereotype.Component

@Component("GreeterImple")
class GreeterImpl : Greeter {
  override fun sayHello(name: String) = "Hello $name"
}
