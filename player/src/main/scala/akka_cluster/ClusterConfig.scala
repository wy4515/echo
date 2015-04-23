import java.net.InetAddress

import akka.actor.{Props, ActorSystem}
import com.typesafe.config.ConfigFactory
import module.SongModel

/**
 * Created by yangwu on 4/9/15.
 */

class ClusterConfig(ip:String, port:String) {

//  val config = ConfigFactory.parseString("akka.remote.netty.tcp.port=" + "33333").
//    withFallback(ConfigFactory.load())


  val _config = ConfigFactory.parseString("akka.remote.netty.tcp.hostname=\""+ InetAddress.getLocalHost.getHostAddress +"\"")
    .withFallback(ConfigFactory.parseString("akka.remote.netty.tcp.port="+"33333"))
    .withFallback(ConfigFactory.parseString("akka.cluster.seed-nodes=[\"akka.tcp://ClusterSystem@"+ip+":"+port+"\"]"))
    .withFallback(ConfigFactory.load())

  println("- CONFIG: IP:PORT "+ip+":"+port)

  val system = ActorSystem("ClusterSystem", _config)

  val listener = system.actorOf(Props(classOf[SimpleClusterListener]), name = "SimpleClusterListener")

  def setUp(): Unit = {
    /** empty */
  }


  def broadcast(s:String): Unit = {
    println("- CHECK BROADCAST -" + s)
    listener ! requestPlay(s)
  }

  def config = _config
}
