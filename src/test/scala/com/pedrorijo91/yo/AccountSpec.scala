package com.pedrorijo91.yo

import com.pedrorijo91.yo.model.Account
import scala.util.Random

class AccountSpec extends YoGeneralSpec {

  private val expectedTokSize = 195

  "create account" should "return correct answer with valid params" in {

    withValidClient {
      client =>

        val username = randomUsername()
        val description = Random.nextString(20)

        val ans = client.accounts(username, Some("password"), Some(validLink), Some(validEmail), Some(description), Some(false), Some(validLink))

        ans.isRight should be(true)

        val account: Account = ans.right.get

        account.apiToken.matches(s"$alphaCharLowerRegex{8}-$alphaCharLowerRegex{4}-$alphaCharLowerRegex{4}-$alphaCharLowerRegex{4}-$alphaCharLowerRegex{12}") should be(true)
        account.displayName should be(description)
        account.isApiUser should be(true)
        account.isSubscribable should be(false)
        account.userType should be("user")
        account.userId.length should be(24)
        account.username should be(username)
        account.yoCount should be(0)
        account.tok.length should be(195)
    }
  }


  it should "create expected account with Optional parameters missing" in {
    withValidClient {
      client =>

        val username = randomUsername()

        val ans = client.accounts(username)

        ans.isRight should be(true)

        val account: Account = ans.right.get

        account.apiToken.matches(s"$alphaCharLowerRegex{8}-$alphaCharLowerRegex{4}-$alphaCharLowerRegex{4}-$alphaCharLowerRegex{4}-$alphaCharLowerRegex{12}") should be(true)
        account.displayName should be(username)
        account.isApiUser should be(true)
        account.isSubscribable should be(false)
        account.userType should be("user")
        account.userId.length should be(24)
        account.username should be(username)
        account.yoCount should be(0)
        account.tok.length should be(expectedTokSize)
    }
  }

  it should "report API error response with already existing username" in {

    withValidClient {
      client =>
        val ans = client.accounts(testUsername)

        ans.isLeft should be(true)
        ans.left.get should be((422, "User already exists."))
    }
  }

  it should "be prepared for lowercase usernames" in {

    withValidClient {
      client =>
        val username = randomUsername()

        val ans = client.accounts(username.toLowerCase)

        ans.isRight should be(true)

        val account: Account = ans.right.get

        account.apiToken.matches(s"$alphaCharLowerRegex{8}-$alphaCharLowerRegex{4}-$alphaCharLowerRegex{4}-$alphaCharLowerRegex{4}-$alphaCharLowerRegex{12}") should be(true)
        account.displayName should be(username)
        account.isApiUser should be(true)
        account.isSubscribable should be(false)
        account.userType should be("user")
        account.userId.length should be(24)
        account.username should be(username)
        account.yoCount should be(0)
        account.tok.length should be(expectedTokSize)
    }
  }

  it should "report API error response with empty username" in {

    withValidClient {
      client =>
        val username = ""

        val ans = client.accounts(username)

        ans.isLeft should be(true)
        ans.left.get should be((400, "Received invalid data : Username error: This field is required."))
    }
  }

  it should "report API error response with invalid username" in {

    withValidClient {
      client =>

        val ans = client.accounts(invalidUsername)

        ans.isLeft should be(true)
        ans.left.get should be((400, "Received invalid data : Username error: username must start with a letter and contain [A-Z0-9], Username error: Invalid input."))
    }
  }

  it should "report API error response with empty strings" in {
    withValidClient {
      client =>

        val username = randomUsername()

        val ans = client.accounts(username, Some(""), Some(""), Some(""), Some(""), Some(false), Some(""))

        ans.isRight should be(true)

        val account: Account = ans.right.get

        account.apiToken.matches(s"$alphaCharLowerRegex{8}-$alphaCharLowerRegex{4}-$alphaCharLowerRegex{4}-$alphaCharLowerRegex{4}-$alphaCharLowerRegex{12}") should be(true)
        account.displayName should be(username)
        account.isApiUser should be(true)
        account.isSubscribable should be(false)
        account.userType should be("user")
        account.userId.length should be(24)
        account.username should be(username)
        account.yoCount should be(0)
        account.tok.length should be(195)
    }
  }

  it should "signal invalid token for invalid client" in {
    withInvalidClient {
      client =>
        val username = randomUsername()

        val ans = client.accounts(username)

        ans.isLeft should be(true)
        ans.left.get should be((403, "Invalid API token."))
    }
  }

  it should "signal invalid token for empty client" in {
    withEmptyClient {
      client =>
        val username = randomUsername()

        val ans = client.accounts(username)

        ans.isLeft should be(true)
        ans.left.get should be((403, "Invalid API token."))
    }
  }
}
