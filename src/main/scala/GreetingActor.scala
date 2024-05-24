import akka.actor.{Actor, ActorSystem, Props}

// Define the greeting actor
class GreetingActor extends Actor {
  def receive = {
    case "hello" => println("Hello back at you!")
    case _       => println("Sorry, I didn't understand the message.")
  }
}

// Define the counting actor
class CountingActor extends Actor {
  var count = 0

  def receive = {
    case "increment" =>
      count += 1
      println(s"Current count: $count")
    case "get" =>
      sender() ! count
  }
}

object MainMany extends App {
  // Create the actor system
  val system = ActorSystem()

  // Create the greeting actor
  val greeter = system.actorOf(Props[GreetingActor], name = "greeter")

  // Create the counting actor
  val counter = system.actorOf(Props[CountingActor], name = "counter")

  // Send messages to the greeting actor
  greeter ! "hello"
  greeter ! "hi"

  // Send messages to the counting actor
  counter ! "increment"
  counter ! "increment"
  counter ! "get"
}
