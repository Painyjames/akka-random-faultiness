package com.cascomio.akka_random_faultiness.actors

import akka.actor.{Actor, OneForOneStrategy, Props, SupervisorStrategy}
import akka.pattern.{Backoff, BackoffSupervisor}
import com.cascomio.akka_random_faultiness._
import scala.concurrent.duration._

class RootActor(
               numberActor: Props,
               sentenceActor: Props
               ) extends Actor {

  val supervisor = BackoffSupervisor.props(
    Backoff.onFailure(
      numberActor,
      childName = "numberActor",
      minBackoff = 3.seconds,
      maxBackoff = 30.seconds,
      randomFactor = 0.2,
      maxNrOfRetries = 3
    ).withAutoReset(10 seconds)
      .withSupervisorStrategy(
        OneForOneStrategy() {
          case _: IllegalArgumentException ⇒ SupervisorStrategy.Restart
          case _              ⇒ SupervisorStrategy.Escalate
        }))
  private val numberActorRef = context.system.actorOf(supervisor)
  private val sentenceActorRef = context.system.actorOf(sentenceActor)

  override def receive: Receive = {
    case n: Int => numberActorRef ! n
    case w: String => sentenceActorRef ! w
    case () => self ! sendMessage()
  }
}

object RootActor {
  def apply() = Props(classOf[RootActor], NumberActor(), SentenceActor())
}