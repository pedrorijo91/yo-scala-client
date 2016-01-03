package com.pedrorijo91.yo

import java.util.concurrent.{SynchronousQueue, ThreadPoolExecutor, TimeUnit}

import com.ning.http.client.AsyncHttpClientConfig
import play.api.libs.json.Json
import play.api.libs.ws.DefaultWSClientConfig
import play.api.libs.ws.ning.{NingAsyncHttpClientConfigBuilder, NingWSClient}
import play.utils.UriEncoding

import scala.concurrent.Await
import scala.concurrent.duration._
import scala.util.Try

/*
 TODO
 - v2 OAuth http://docs.justyo.co/v2.0/docs/oauth
 - docs: README, CONTRIBUTING, LICENSE
 - Try/Either
 - refactor
 - codacy/CI
 - tests
 - answers
 - user
 - location
 - accounts http://docs.justyo.co/v2.0/docs/accounts
 */

class YoClient(apiToken: String) {

  private val requestTimeout = Duration(10, SECONDS)

  private val token = apiToken

  def numberSubscribers: Try[Int] = withClient {
    client =>
      val url = s"http://api.justyo.co/subscribers_count/?api_token=$token"

      val promise = client.url(url).get()
      val result = Await.result(promise, requestTimeout)

      (result.json \ "count").toString().toInt
  }

  def checkUsername(username: String): Try[Boolean] = withClient {
    client =>
      val url = s"http://api.justyo.co/check_username/?api_token=$token&username=${username.toUpperCase}"

      val promise = client.url(url).get
      val result = Await.result(promise, requestTimeout)

      (result.json \ "exists").toString().toBoolean
  }

  def accounts() = {

  }

  def yoAll(link: Option[String] = None) = withClient {
    client =>

      val url =
        s"https://api.justyo.co/yoall/?api_token=$token" +
          link.map(l => s"&link=$l").getOrElse("")

      val promise = client.url(url).post(Json.obj())
      val result = Await.result(promise, requestTimeout)

      // response example
      """
        |{
        | "success":true,
        | "yo_id":"568969297caba7002cabe14a"
        |}
      """.stripMargin

      result.json
  }

  def yo(username: String, link: Option[String] = None, location: Option[String] = None, text: Option[String] = None) = withClient {
    client =>

      assert(link.isEmpty || location.isEmpty, "can only send link OR location but not both. more info at http://docs.justyo.co/v2.0/docs/yo")

      val url =
        s"https://api.justyo.co/yo/?api_token=$token&username=${username.toUpperCase}" +
          text.map(t => s"&text=${UriEncoding.encodePathSegment(t, "utf-8")}").getOrElse("") +
          link.map(l => s"&link=$l").getOrElse("") +
          location.map(loc => s"&location=$loc").getOrElse("") // http://dateandtime.info/pt/citycoordinates.php?id=2267057

      val promise = client.url(url).post(Json.obj())
      val result = Await.result(promise, requestTimeout)

      // response example
      """
        |{
        | "recipient":
        | {
        |   "display_name":"Pedro R.",
        |   "first_name":"Pedro",
        |   "is_api_user":false,
        |   "is_subscribable":false,
        |   "last_name":"Rijo",
        |   "name":"Pedro Rijo",
        |   "photo":"https://s3.amazonaws.com/yoapp-images/553265097643480027317b68036d5393.jpg",
        |   "type":"user",
        |   "user_id":"553265097643480027317b68",
        |   "username":"PEDRORIJO91",
        |   "yo_count":140
        | },
        | "success":true,
        | "yo_id":"5689682c5f222d002972faa1"
        |}
      """.stripMargin

      result.json
  }

  private def withClient[T](block: NingWSClient => T): Try[T] = {
    val config = new NingAsyncHttpClientConfigBuilder(DefaultWSClientConfig()).build()
    val clientConfig = new AsyncHttpClientConfig.Builder(config)
      .setExecutorService(new ThreadPoolExecutor(5, 15, 30L, TimeUnit.SECONDS, new SynchronousQueue[Runnable]))
      .build()
    val client = new NingWSClient(clientConfig)
    val result = Try(block(client))
    client.close()
    result
  }

}