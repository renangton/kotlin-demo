package com.example.demo.domain.`object`

import org.springframework.stereotype.Component

@Component("BadGreeterImpl")
class BadGreeterImpl : Greeter {
  override fun sayHello(name: String) = "Booo! $name"
}
