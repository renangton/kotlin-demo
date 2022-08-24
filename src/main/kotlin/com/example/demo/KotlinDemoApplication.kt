package com.example.demo

import database.UserDynamicSqlSupport.User.age
import database.UserDynamicSqlSupport.User.name
import database.UserDynamicSqlSupport.User.profile
import database.UserMapper
import database.UserRecord
import database.count
import database.deleteByPrimaryKey
import database.insert
import database.insertMultiple
import database.select
import database.selectByPrimaryKey
import database.update
import database.updateByPrimaryKeySelective
import database.updateSelectiveColumns
import org.apache.ibatis.io.Resources
import org.apache.ibatis.session.SqlSessionFactory
import org.apache.ibatis.session.SqlSessionFactoryBuilder
import org.mybatis.dynamic.sql.util.kotlin.elements.isEqualTo
import org.mybatis.dynamic.sql.util.kotlin.elements.isGreaterThanOrEqualTo
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class KotlinDemoApplication


fun main(args: Array<String>) {
  runApplication<KotlinDemoApplication>(*args)
  chapter5_4_3()
  chapter5_4_4()
  chapter5_4_6()
  chapter5_4_8()
  chapter5_4_9()
//  chapter5_4_10()
//  chapter5_4_12()
  chapter5_4_14()
  chapter5_4_16()
  chapter5_4_18()
  chapter5_4_20()
}

fun createSessionFactory(): SqlSessionFactory {
  val resource = "mybatis-config.xml"
  val inputStream = Resources.getResourceAsStream(resource)
  return SqlSessionFactoryBuilder().build(inputStream)
}

fun chapter5_4_3() {
  createSessionFactory().openSession().use { session ->
    val mapper = session.getMapper(UserMapper::class.java)
    val user = mapper.selectByPrimaryKey(100)
    println(user)
  }
}

fun chapter5_4_4() {
  createSessionFactory().openSession().use { session ->
    val mapper = session.getMapper(UserMapper::class.java)
    val user = mapper.select {
      where(column = name, condition = isEqualTo("Jiro"))
    }
    println(user)
  }
}

fun chapter5_4_6() {
  createSessionFactory().openSession().use { session ->
    val mapper = session.getMapper(UserMapper::class.java)
    val userList = mapper.select {
      where(age, isGreaterThanOrEqualTo(25))
    }
    println(userList)
  }
}

fun chapter5_4_8() {
  createSessionFactory().openSession().use { session ->
    val mapper = session.getMapper(UserMapper::class.java)
    val count = mapper.count {
      where(age, isGreaterThanOrEqualTo(25))
    }
    println(count)
  }
}

fun chapter5_4_9() {
  createSessionFactory().openSession().use { session ->
    val mapper = session.getMapper(UserMapper::class.java)
    val count = mapper.count { allRows() }
    println(count)
  }
}

fun chapter5_4_10() {
  val user = UserRecord(103, "Shiro", 18, "Hello")
  createSessionFactory().openSession().use { session ->
    val mapper = session.getMapper(UserMapper::class.java)
    val count = mapper.insert(user)
    session.commit()
    println("${count}行のレコードを挿入しました")
  }
}

fun chapter5_4_12() {
  val userList = listOf(UserRecord(104, "Goro", 15, "Hello"), UserRecord(105, "Rokuro", 13, "Hello"))
  createSessionFactory().openSession().use { session ->
    val mapper = session.getMapper(UserMapper::class.java)
    val count = mapper.insertMultiple(userList)
    session.commit()
    println("${count}行のレコードを挿入しました")
  }
}

fun chapter5_4_14() {
  val user = UserRecord(id = 105, profile = "Bye")
  createSessionFactory().openSession().use { session ->
    val mapper = session.getMapper(UserMapper::class.java)
    val count = mapper.updateByPrimaryKeySelective(user)
    session.commit()
    println("${count}行のレコードを更新しました")
  }
}

fun chapter5_4_16() {
  createSessionFactory().openSession().use { session ->
    val mapper = session.getMapper(UserMapper::class.java)
    val count = mapper.update {
      set(profile).equalTo("Hey")
      where(name, isEqualTo("Shiro"))
    }
    session.commit()
    println("${count}行のレコードを更新しました")
  }
}

fun chapter5_4_18() {
  val user = UserRecord(profile = "Good Morning")
  createSessionFactory().openSession().use { session ->
    val mapper = session.getMapper(UserMapper::class.java)
    val count = mapper.update {
      updateSelectiveColumns(user)
      where(name, isEqualTo("Shiro"))
    }
    session.commit()
    println("${count}行のレコードを更新しました")
  }
}

fun chapter5_4_20() {
  createSessionFactory().openSession().use { session ->
    val mapper = session.getMapper(UserMapper::class.java)
    val count = mapper.deleteByPrimaryKey(103)
    session.commit()
    println("${count}行のレコードを削除しました")
  }
}

// docker container run --rm -d -e MYSQL_ROOT_PASSWORD=mysql -p 3306:3306 --name mysql mysql
// winpty docker exec -it mysql mysql -uroot -pmysql
// create database example;
// use example;
// CREATE TABLE user (id int(10) NOT NULL, name varchar(16) NOT NULL, age int(10) NOT NULL,profile varchar(64) NOT NULL,PRIMARY KEY(id))ENGINE=InnoDB DEFAULT CHARSET=utf8;
