package com.pedrorijo91.yo

import com.ning.http.client.AsyncHttpClientConfig
import com.pedrorijo91.yo.model.{Account, Location, User}
import play.api.Logger
import play.api.http.Status
import play.api.libs.json.{JsArray, JsValue, Json}
import play.api.libs.ws.WSResponse
import play.api.libs.ws.ning.{NingAsyncHttpClientConfigBuilder, NingWSClient}
import play.utils.UriEncoding
import scala.concurrent.Await
import scala.concurrent.duration._
import scala.util.{Failure, Success, Try}

class YoClient(private val token: String) {

  private[this] val requestTimeout = Duration(10, SECONDS)
  private[this] val errorCode = -1

  def yoAll(link: Option[String] = None): Either[(Int, String), (Boolean, String)] = {

    val result = withClient {
      client =>

        val url =
          s"https://api.justyo.co/yoall/?api_token=$token" +
            link.map(l => s"&link=$l").getOrElse("")

        val promise = client.url(url).post(Json.obj())
        Await.result(promise, requestTimeout)
    }

    val newOnSuccess: JsValue => (Boolean, String) = {
      json =>
        val success = (json \ "success").as[Boolean]
        val yoId = (json \ "yo_id").as[String]

        (success, yoId)
    }

    dealResponse(result, newOnSuccess)
  }

  def yo(username: String, link: Option[String] = None, location: Option[Location] = None,
         text: Option[String] = None): Either[(Int, String), (User, Boolean, String)] = {

    val result = withClient {
      client =>

        val url =
          s"https://api.justyo.co/yo/?api_token=$token&username=${username.toUpperCase}" +
            text.map(t => s"&text=${UriEncoding.encodePathSegment(t, "utf-8")}").getOrElse("") +
            link.map(l => s"&link=$l").getOrElse("") +
            location.map(loc => s"&location=${loc.value}").getOrElse("")

        val promise = client.url(url).post(Json.obj())
        Await.result(promise, requestTimeout)
    }

    val newOnSuccess: JsValue => (User, Boolean, String) = {
      json =>
        val user = (json \ "recipient").as[User]
        val success = (json \ "success").as[Boolean]
        val yoId = (json \ "yo_id").as[String]

        (user, success, yoId)
    }

    dealResponse(result, newOnSuccess)
  }

  def numberSubscribers: Either[(Int, String), Int] = {

    val result = withClient {
      client =>
        val url = s"http://api.justyo.co/subscribers_count/?api_token=$token"

        val promise = client.url(url).get
        Await.result(promise, requestTimeout)
    }

    val newOnSuccess: JsValue => Int = {
      json =>
        (json \ "count").as[Int]
    }

    dealResponse(result, newOnSuccess)
  }

  def checkUsername(username: String): Either[(Int, String), Boolean] = {

    val result = withClient {
      client =>
        val url = s"http://api.justyo.co/check_username/?api_token=$token&username=${username.toUpperCase}"

        val promise = client.url(url).get
        Await.result(promise, requestTimeout)
    }

    val newOnSuccess: JsValue => Boolean = {
      json =>
        (json \ "exists").as[Boolean]
    }

    dealResponse(result, newOnSuccess)
  }


  def accounts(username: String, password: Option[String] = None, callbackUrl: Option[String] = None,
               email: Option[String] = None, description: Option[String] = None,
               needsLocation: Option[Boolean] = None, welcomeLink: Option[String] = None): Either[(Int, String), Account] = {

    val result = withClient {
      client =>
        val url = s"https://api.justyo.co/accounts/?api_token=$token&username=${username.toUpperCase}" +
          password.map(p => s"&password=$p").getOrElse("") +
          callbackUrl.map(c => s"&callback_url=$c").getOrElse("") +
          email.map(e => s"&email=$e").getOrElse("") +
          description.map(d => s"&description=$d").getOrElse("") +
          needsLocation.map(loc => s"&needs_location=$loc").getOrElse("") +
          welcomeLink.map(l => s"&welcome_link=$l").getOrElse("")

        val promise = client.url(url).post(Json.obj())
        Await.result(promise, requestTimeout)
    }

    val newOnSuccess: JsValue => Account = {
      json =>
        json.as[Account]
    }

    val onError: JsValue => String = {
      json =>
        val error = (json \ "error").as[String]

        val welcomeLinkErrors = (json \ "payload")
          .asOpt[JsValue]
          .map(_ \ "welcome_link")
          .flatMap(_.toOption.map(_.as[JsArray].value.map(_.as[String])))
          .getOrElse(Seq.empty)
          .map("Welcome link error: " + _)

        val userNameErrors = (json \ "payload")
          .asOpt[JsValue]
          .map(_ \ "username")
          .flatMap(_.toOption.map(_.as[JsArray].value.map(_.as[String])))
          .getOrElse(Seq.empty)
          .map("Username error: " + _)

        val otherErrors = welcomeLinkErrors ++ userNameErrors

        error + (if (otherErrors.nonEmpty) s" : ${otherErrors.mkString(", ")}" else "")
    }

    dealResponse(result, newOnSuccess, onError = onError, expectedStatus = Status.CREATED)
  }

  private[this] def withClient(block: NingWSClient => WSResponse): Try[WSResponse] = {
    val config = new NingAsyncHttpClientConfigBuilder().build()
    val clientConfig = new AsyncHttpClientConfig.Builder(config)
      // .setExecutorService(new ThreadPoolExecutor(0, 10, 30L, TimeUnit.SECONDS, new LinkedBlockingQueue[Runnable]))
      .build()
    val client = new NingWSClient(clientConfig)
    val result = Try(block(client))
    client.close()
    result
  }

  private[this] val defaultParseError: (JsValue) => String = (json: JsValue) => (json \ "error").as[String]

  private[this] def dealResponse[A](wSResponse: Try[WSResponse],
                                    onSuccess: JsValue => A, expectedStatus: Int = Status.OK,
                                    onError: JsValue => String = defaultParseError): Either[(Int, String), A] = {

    wSResponse match {
      case Failure(e) =>
        Logger.error(s"Failure executing request: $e")
        Left((errorCode, s"Failure executing request: $e"))

      case Success(response) =>
        Logger.debug(s"Success response: (${response.status}) ${response.json}")

        if (response.status == expectedStatus) {
          Right(onSuccess(response.json))
        } else {
          Logger.warn(s"Request returned with not Ok (${Status.OK}) code: (${response.status}) ${response.json}")

          val errorMessage: String = onError(response.json)

          Left((response.status, errorMessage))
        }
    }
  }

}
