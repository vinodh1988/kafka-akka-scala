import akka.actor.Actor
import akka.actor.Props
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord, ProducerConfig}
import java.util.Properties

class KafkaProducerActor(brokerList: String, topic: String) extends Actor {
  // Configuration properties for the producer
  val props = new Properties()
  props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, brokerList)
  props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer")
  props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer")
  props.put(ProducerConfig.ACKS_CONFIG,"0")
  // Create Kafka producer
  val producer = new KafkaProducer[String, String](props)

  def receive: Receive = {
    case msg: String =>
      // Send message to Kafka topic
      val record = new ProducerRecord[String, String](topic, "Hard Coded Key",msg)
      producer.send(record)
      println(s"Sent message: $msg")

    case _ =>
      println("Received unknown message")
  }

  override def postStop(): Unit = {
    producer.close() // Close producer on actor shutdown
  }
}

object KafkaProducerApp extends App {
  val system = akka.actor.ActorSystem("KafkaProducerSystem")
  val producerActor = system.actorOf(Props(new KafkaProducerActor("localhost:9092", "message-demo2")), name = "producerActor")

  // Send messages
  producerActor ! "Let us check whether consumer consumes it"
  producerActor ! "Lets Check it now."
}
