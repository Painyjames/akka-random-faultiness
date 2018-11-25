package com.cascomio.akka_random_faultiness

package object actors {
  val r = scala.util.Random
  def randomNumber(max: Int = 100): Int = r.nextInt(max)
  def randomWord(): String = r.alphanumeric.take(randomNumber(10)).mkString("")

  def sendMessage(): Any = {
    val getNumber = r.nextBoolean
    if (getNumber)
      randomNumber()
    else
      randomWord()
  }
}
