package com.pedrorijo91.yo

class YoAllSpec extends YoGeneralSpec {

  "send yoAll" should "return correct answer with existing user and no link" in {

    withValidClient {
      client =>

        val ans = client.yoAll()


        ans.isRight should be(true)

        val (success, yoId) = ans.right.get

        success should be(true)
        yoId.length should be(24)
    }
  }

  it should "return correct answer with existing user and link" in {

    withValidClient {
      client =>

        val ans = client.yoAll(Some(validLink))


        ans.isRight should be(true)

        val (success, yoId) = ans.right.get

        success should be(true)
        yoId.length should be(24)
    }
  }

  it should "report API error response with invalid link" in {

    withValidClient {
      client =>

        val ans = client.yoAll(Some(invalidLink))

        ans.isLeft should be(true)
        ans.left.get should be((400, "Invalid URL"))
    }
  }

  it should "signal invalid token for invalid client" in {
    withInvalidClient {
      client =>
        val ans = client.yoAll()

        ans.isLeft should be(true)
        ans.left.get should be((403, "Invalid API token."))
    }
  }

  it should "signal invalid token for empty client" in {
    withEmptyClient {
      client =>
        val ans = client.yoAll()

        ans.isLeft should be(true)
        ans.left.get should be((403, "Invalid API token."))
    }
  }

}
