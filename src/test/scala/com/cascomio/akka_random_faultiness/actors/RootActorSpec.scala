package com.cascomio.akka_random_faultiness.actors

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.testkit.{DefaultTimeout, ImplicitSender, TestKit, TestProbe}
import org.scalamock.scalatest.MockFactory
import org.scalatest.{BeforeAndAfterAll, Matchers, WordSpecLike}

import scala.concurrent.duration._

class RootActorSpec extends TestKit(ActorSystem("rootActor"))
  with WordSpecLike
  with BeforeAndAfterAll
  with Matchers
  with DefaultTimeout
  with MockFactory
  with ImplicitSender {
  implicit val asystem = ActorSystem()
  implicit val materializer = ActorMaterializer()
  implicit val executionContext = system.dispatcher

  "RootActor" should {
    "accept empty message" in {
      val rootActor = RootActor()
      val actorRef = system.actorOf(rootActor)
      val probe = TestProbe()
      probe watch actorRef
      within(3000 millis) {
        val msg = ()

        probe.ref ! msg

        probe.expectMsg(msg)
      }
    }
  }
}
