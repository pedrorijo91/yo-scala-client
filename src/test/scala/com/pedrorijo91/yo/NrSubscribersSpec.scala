package com.pedrorijo91.yo

class NrSubscribersSpec extends YoGeneralSpec {

  "number subscribers" should "be correctly returned" in {

    withValidClient {
      client =>
        val ans = client.numberSubscribers

        ans.isRight should be(true)
        ans.right.get should be(0)
    }
  }

  it should "signal invalid token for invalid client" in {
    withInvalidClient {
      client =>
        val ans = client.numberSubscribers

        ans.isLeft should be(true)
        ans.left.get should be((403, "Invalid API token."))
    }
  }

  it should "signal invalid token for empty client" in {
    withEmptyClient {
      client =>
        val ans = client.numberSubscribers

        ans.isLeft should be(true)
        ans.left.get should be((403, "Invalid API token."))
    }
  }
}
