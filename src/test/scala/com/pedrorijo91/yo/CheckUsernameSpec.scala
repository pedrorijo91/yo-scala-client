package com.pedrorijo91.yo

class CheckUsernameSpec extends YoGeneralSpec {

  "check username" should "return correct answer with existing user" in {

    withValidClient {
      client =>
        val ans = client.checkUsername(testUsername)

        ans.isRight should be(true)
        ans.right.get should be(true)
    }
  }

  it should "be prepared for lowercase usernames" in {

    withValidClient {
      client =>
        val ans = client.checkUsername(testUsername.toLowerCase)

        ans.isRight should be(true)
        ans.right.get should be(true)
    }
  }

  it should "report API error response with empty username" in {

    withValidClient {
      client =>
        val ans = client.checkUsername("")

        ans.isLeft should be(true)
        ans.left.get should be((400, "Must supply username"))
    }
  }

  it should "return false for inexistent user" in {

    withValidClient {
      client =>
        val ans = client.checkUsername(randomUsername())

        ans.isRight should be(true)
        ans.right.get should be(false)
    }
  }

  it should "return false for huge username" in {

    withValidClient {
      client =>
        val ans = client.checkUsername(randomUsername() * 50)

        ans.isRight should be(true)
        ans.right.get should be(false)
    }
  }

  it should "signal invalid token for invalid client" in {
    withInvalidClient {
      client =>
        val ans = client.checkUsername(testUsername)

        ans.isLeft should be(true)
        ans.left.get should be((403, "Invalid API token."))
    }
  }

  it should "signal invalid token for empty client" in {
    withEmptyClient {
      client =>
        val ans = client.checkUsername(testUsername)

        ans.isLeft should be(true)
        ans.left.get should be((403, "Invalid API token."))
    }
  }

}
