package com.pedrorijo91.yo

import com.pedrorijo91.yo.model.{Location, User}
import org.scalatest._
import scala.util.Random

class YoGeneralSpec extends FlatSpec with Matchers {

  private val testApiToken = "dbc59db4-94c1-4e25-9985-ee4d35544bba"
  // https://dev.justyo.co/u/pedrorijo91testscala/send
  protected val testUsername = "PEDRORIJO91TESTSCALA"

  private val invalidApiToken = "1e20ec80-d572-44ac-a459-6870d8474853"

  protected val yoReceiver: User =
    User("PEDRORIJO91TESTSCALARECEIVER", None, isApiUser = true, isSubscribable = false,
      None, "", None, "user", "56900b35b338700028473ab2", "PEDRORIJO91TESTSCALARECEIVER", -1)

  protected val yoFullReceiver: User =
    User("randomDescription", None, isApiUser = true, isSubscribable = false, None, "randomDescription",
      Some("https://s3.amazonaws.com/yoapp-images/5690137d08538500272a20f6edac0066.jpg"), "user",
      "5690137d08538500272a20f6", "PEDRORIJO91TESTSCALAFULLRECEIVER", -1)

  protected val validLocation = Location("38.7166700", "-9.1333300")
  protected val invalidLocation = Location("pedrorijo91", "pedrorijo91")
  protected val emptyLocation = Location("", "")

  protected val validLink = "http://pedrorijo.com/"
  protected val invalidLink = "pedrorijo91"

  protected val invalidUsername = "1pedrorijo91"

  protected val validEmail = "pedrorijo91@pedrorijo.com"

  protected val alphaCharLowerRegex = "[a-z0-9]"

  protected def withValidClient[T](block: YoClient => T): T = {
    val client = new YoClient(testApiToken)
    block(client)
  }

  protected def withInvalidClient[T](block: YoClient => T): T = {
    val client = new YoClient(invalidApiToken)
    block(client)
  }

  protected def withEmptyClient[T](block: YoClient => T): T = {
    val client = new YoClient("")
    block(client)
  }

  protected def randomUsername(size: Int = 10): String = Random.alphanumeric.dropWhile(_.isDigit).take(size - 1).mkString.toUpperCase

}
