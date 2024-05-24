import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.kafka.{ConsumerSettings, Subscriptions}
import akka.kafka.scaladsl.Consumer
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.Sink
import org.apache.kafka.common.serialization.StringDeserializer


object KafkaConsumerApp {

  def main(args: Array[String]): Unit = {
    implicit val system: ActorSystem[Nothing] = ActorSystem(Behaviors.empty, "KafkaConsumerSystem")

    val consumerSettings = ConsumerSettings(system, new StringDeserializer, new StringDeserializer)
      .withBootstrapServers("localhost:9092")
      .withGroupId("group1")
      .withProperty("auto.offset.reset", "earliest")

    Consumer
      .plainSource(consumerSettings, Subscriptions.topics("message-demo2"))
      .runWith(Sink.foreach(msg => println(s"Received: ${msg.value()} Key: ${msg.key()} offset: ${msg.offset()}")))
  }
}
