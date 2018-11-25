package com.cascomio.akka_random_faultiness.actors

import akka.actor.{Actor, Props}

class SentenceActor(letter: String) extends Actor {
  override def receive: Receive = {
    case w: String if (w.endsWith(letter)) =>
      throw new IllegalArgumentException(s"$w not accepted because it ended in $letter")
    case word: String => println(
      s"""
         |$word processed successfully
       """.stripMargin)
  }
}

object SentenceActor {
  def apply() = Props(classOf[SentenceActor], "o")
  def apply(c: String) = Props(classOf[SentenceActor], c)
}