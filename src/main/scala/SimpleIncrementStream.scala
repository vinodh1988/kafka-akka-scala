import akka.actor.ActorSystem
import akka.stream.scaladsl._
import akka.stream.ActorMaterializer

object SimpleIncrementStream extends App {
  implicit val system = ActorSystem("SimpleIncrementStream")
  implicit val materializer = ActorMaterializer()

  // Create a source of integers
  val source = Source(1 to 5)

  // Define a flow that increments each integer by 1
  val incrementFlow = Flow[Int].map(_ + 1)

  // Create a sink that prints each number
  val printSink = Sink.foreach[Int](println)

  // Construct the stream by connecting source, flow, and sink
  val runnableGraph = source.via(incrementFlow).to(printSink)

  // Run the graph
  runnableGraph.run()
}
