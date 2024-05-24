import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.Behaviors

object SimplestAkka {
  // Message definition
  final case class SayHello(name: String)

  // Actor definition
  def helloActor: Behavior[SayHello] = Behaviors.receive { (context, message) =>
    println(s"Hello, ${message.name}!")
    Behaviors.same
  }
}

import akka.actor.typed.ActorSystem
import SimplestAkka._

object SimpleMain {
  def main(args: Array[String]): Unit = {
    // Create the actor system and the actor
    val helloActorSystem: ActorSystem[SayHello] = ActorSystem(helloActor, "HelloSystem")

    // Send a message to the actor
    // ! is the akka operator meant to send messages
    helloActorSystem ! SayHello("World")
  }
}
