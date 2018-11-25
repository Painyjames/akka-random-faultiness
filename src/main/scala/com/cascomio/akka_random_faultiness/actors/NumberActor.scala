package com.cascomio.akka_random_faultiness.actors

import akka.actor.{Actor, Props}

class NumberActor(number: Int) extends Actor {
  override def receive: Receive = {
    case n: Int if (n > number) => throw new IllegalArgumentException(s"$number not accepted")
    case n: Int => println(
      s"""
         |$n processed successfully
       """.stripMargin)
  }
}

object NumberActor {
  def apply() = Props(classOf[NumberActor], 90)
  def apply(n: Int) = Props(classOf[NumberActor], n)
}
