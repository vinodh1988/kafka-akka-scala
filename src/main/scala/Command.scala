import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.Behaviors

// Sealed trait for our messages
sealed trait Command
case class Greet(name: String) extends Command
case object GetGreetingCount extends Command
case class GreetingCount(count: Int)

// Greeting actor with state
object Greeter {
  def apply(): Behavior[Command] = greet(0)

  private def greet(count: Int): Behavior[Command] = Behaviors.receive { (context, message) =>
    message match {
      case Greet(name) =>
        println(s"Hello, $name!")
        greet(count + 1) // Increment count and continue
      case GetGreetingCount =>
        println(s"Total greetings: $count")
        Behaviors.same // Continue with the same behavior
    }
  }
}

import akka.actor.typed.ActorSystem

object Main {
  def main(args: Array[String]): Unit = {
    // Create the actor system
    val greeterSystem: ActorSystem[Command] = ActorSystem(Greeter(), "GreeterSystem")

    // Send a few greeting messages
    greeterSystem ! Greet("Vinodh")
    greeterSystem ! Greet("Alice")
    greeterSystem ! Greet("Bob")

    // Request the count of greetings
    greeterSystem ! GetGreetingCount

    // Stop the actor system
    greeterSystem.terminate()
  }
}
