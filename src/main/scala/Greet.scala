import akka.actor.typed.{ActorSystem, Behavior}
import akka.actor.typed.scaladsl.Behaviors

// Sealed trait for our messages
sealed trait CommonWish
case class Wish(name: String) extends CommonWish
case object GetWishingCount extends CommonWish
case class WishingCount(count: Int)

// Greeting actor with state
object Wisher{
  def apply(): Behavior[CommonWish] = wish(0)

  private def wish(count: Int): Behavior[CommonWish] = Behaviors.receive { (context, message) =>
    message match {
      case Wish(name) =>
        println(s"Hello, $name!")
        wish(count + 1) // Increment count and continue
      case GetWishingCount =>
        println(s"Total greetings: $count")
        Behaviors.same // Continue with the same behavior
    }
  }
}


object Mainer {
  def main(args: Array[String]): Unit = {
    // Create the actor system
    val greeterSystem: ActorSystem[CommonWish] = ActorSystem(Wisher(), "GreeterSystem")

    // Send a few greeting messages
    greeterSystem ! Wish("Vinodh")
    greeterSystem ! Wish("Alice")
    greeterSystem ! Wish("Bob")

    // Request the count of greetings
    greeterSystem ! GetWishingCount

    // Stop the actor system
    greeterSystem.terminate()
  }
}
