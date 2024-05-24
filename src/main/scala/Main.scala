import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors

object MessageSystem{
  def main(args: Array[String]): Unit = {
    // Create the root behavior for the actor system
    val mainBehavior = Behaviors.setup[String] { context =>
      // Define a simple actor that prints incoming messages
      val greeter = context.spawn(Behaviors.receiveMessage[String] { message =>
        println(s"Received message: $message")
        Behaviors.same
      }, "greeter")

      // Send a message to the greeter actor
      greeter ! "Hello, Akka!"

      Behaviors.same
    }

    // Create the actor system with the root behavior
    val system = ActorSystem(mainBehavior, "HelloSystem")

    // Properly terminate the actor system when done
    system ! "shutdown"
  }
}
