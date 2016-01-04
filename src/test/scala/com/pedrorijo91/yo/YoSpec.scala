package com.pedrorijo91.yo

class YoSpec extends YoGeneralSpec {

  "send yo" should "return correct answer with existing user" in {
    
    withValidClient {
      client =>

        val receiver = yoReceiver
        val ans = client.yo(receiver.username)

        ans.isRight should be(true)

        val (user, success, yoId) = ans.right.get

        success should be(true)
        yoId.length should be(24)

        user.displayName should be(receiver.displayName)
        user.firstName should be(receiver.firstName)
        user.isApiUser should be(receiver.isApiUser)
        user.isSubscribable should be(receiver.isSubscribable)
        user.lastName should be(receiver.lastName)
        user.name should be(receiver.name)
        user.photo should be(receiver.photo)
        user.userType should be(receiver.userType)
        user.userId should be(receiver.userId)
        user.username should be(receiver.username)
        user.yoCount > 0 should be(true)
    }
  }

  it should "be prepared for lowercase usernames" in {

    withValidClient {
      client =>

        val receiver = yoReceiver
        val ans = client.yo(receiver.username.toLowerCase)

        ans.isRight should be(true)

        val (user, success, yoId) = ans.right.get

        success should be(true)
        yoId.length should be(24)

        user.displayName should be(receiver.displayName)
        user.firstName should be(receiver.firstName)
        user.isApiUser should be(receiver.isApiUser)
        user.isSubscribable should be(receiver.isSubscribable)
        user.lastName should be(receiver.lastName)
        user.name should be(receiver.name)
        user.photo should be(receiver.photo)
        user.userType should be(receiver.userType)
        user.userId should be(receiver.userId)
        user.username should be(receiver.username)
        user.yoCount > 0 should be(true)
    }
  }

  it should "parse receiver with full data" in {

    withValidClient {
      client =>

        val receiver = yoFullReceiver
        val ans = client.yo(receiver.username.toLowerCase)

        ans.isRight should be(true)

        val (user, success, yoId) = ans.right.get

        success should be(true)
        yoId.length should be(24)

        user.displayName should be(receiver.displayName)
        user.firstName should be(receiver.firstName)
        user.isApiUser should be(receiver.isApiUser)
        user.isSubscribable should be(receiver.isSubscribable)
        user.lastName should be(receiver.lastName)
        user.name should be(receiver.name)
        user.photo should be(receiver.photo)
        user.userType should be(receiver.userType)
        user.userId should be(receiver.userId)
        user.username should be(receiver.username)
        user.yoCount > 0 should be(true)
    }
  }

  it should "return correct answer with text" in {

    withValidClient {
      client =>

        val receiver = yoReceiver
        val ans = client.yo(receiver.username, None, None, Some("some random text"))

        ans.isRight should be(true)

        val (user, success, yoId) = ans.right.get

        success should be(true)
        yoId.length should be(24)

        user.displayName should be(receiver.displayName)
        user.firstName should be(receiver.firstName)
        user.isApiUser should be(receiver.isApiUser)
        user.isSubscribable should be(receiver.isSubscribable)
        user.lastName should be(receiver.lastName)
        user.name should be(receiver.name)
        user.photo should be(receiver.photo)
        user.userType should be(receiver.userType)
        user.userId should be(receiver.userId)
        user.username should be(receiver.username)
        user.yoCount > 0 should be(true)
    }
  }

  it should "return correct answer with location but not link" in {

    withValidClient {
      client =>

        val receiver = yoReceiver
        val ans = client.yo(receiver.username, None, Some(validLocation), Some("some random text"))

        ans.isRight should be(true)

        val (user, success, yoId) = ans.right.get

        success should be(true)
        yoId.length should be(24)

        user.displayName should be(receiver.displayName)
        user.firstName should be(receiver.firstName)
        user.isApiUser should be(receiver.isApiUser)
        user.isSubscribable should be(receiver.isSubscribable)
        user.lastName should be(receiver.lastName)
        user.name should be(receiver.name)
        user.photo should be(receiver.photo)
        user.userType should be(receiver.userType)
        user.userId should be(receiver.userId)
        user.username should be(receiver.username)
        user.yoCount > 0 should be(true)
    }
  }

  it should "return correct answer with link but not location" in {

    withValidClient {
      client =>

        val receiver = yoReceiver
        val ans = client.yo(receiver.username, Some(validLink), None, Some("some random text"))

        ans.isRight should be(true)

        val (user, success, yoId) = ans.right.get

        success should be(true)
        yoId.length should be(24)

        user.displayName should be(receiver.displayName)
        user.firstName should be(receiver.firstName)
        user.isApiUser should be(receiver.isApiUser)
        user.isSubscribable should be(receiver.isSubscribable)
        user.lastName should be(receiver.lastName)
        user.name should be(receiver.name)
        user.photo should be(receiver.photo)
        user.userType should be(receiver.userType)
        user.userId should be(receiver.userId)
        user.username should be(receiver.username)
        user.yoCount > 0 should be(true)
    }
  }

  it should "report API error when sending both location and link" in {

    withValidClient {
      client =>

        val ans = client.yo(yoReceiver.username, Some(validLink), Some(validLocation))

        ans.isLeft should be(true)
        ans.left.get should be((400, "Can't send Yo with location and link."))
    }
  }

  it should "report API error with invalid location" in {

    withValidClient {
      client =>

        val ans = client.yo(yoReceiver.username, None, Some(invalidLocation))

        ans.isLeft should be(true)
        ans.left.get should be((400, "Received invalid data"))
    }
  }

  it should "report API error with empty location" in {

    withValidClient {
      client =>

        val ans = client.yo(yoReceiver.username, None, Some(emptyLocation))

        ans.isLeft should be(true)
        ans.left.get should be((400, "Received invalid data"))
    }
  }

  it should "report API error with invalid link" in {

    withValidClient {
      client =>

        val receiver = yoReceiver
        val ans = client.yo(receiver.username, Some(invalidLink), None)

        ans.isRight should be(true)

        val (user, success, yoId) = ans.right.get

        success should be(true)
        yoId.length should be(24)

        user.displayName should be(receiver.displayName)
        user.firstName should be(receiver.firstName)
        user.isApiUser should be(receiver.isApiUser)
        user.isSubscribable should be(receiver.isSubscribable)
        user.lastName should be(receiver.lastName)
        user.name should be(receiver.name)
        user.photo should be(receiver.photo)
        user.userType should be(receiver.userType)
        user.userId should be(receiver.userId)
        user.username should be(receiver.username)
        user.yoCount > 0 should be(true)
    }
  }

  it should "report API error response with empty username" in {

    withValidClient {
      client =>
        val ans = client.yo("")

        ans.isLeft should be(true)
        ans.left.get should be((400, "Can't send Yo without a recipient."))
    }
  }

  it should "report API error response with unexisting username" in {

    withValidClient {
      client =>
        val ans = client.yo(randomUsername())

        ans.isLeft should be(true)
        ans.left.get should be((404, "No user found"))
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