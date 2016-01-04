package com.pedrorijo91.yo.model

import com.pedrorijo91.yo.YoGeneralSpec
import play.api.libs.json.Json

class AccountSpec extends YoGeneralSpec {

  "Account" should "be correctly parsed from JSON" in {

    val apiToken: String = "a2672dbc-d4ea-41d6-8c9e-6870d8474853"
    val displayName: String = "WTFPEDRO"
    val isApiUser = true
    val isSubscribable = false
    val tok = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VybmFtZSI6IldURlBFRFJPIiwiaWQiOiI1Njg5YzA3NjdjYWJhNzAwMjdiOTZhNzEiLCJ1c2VySUQiOm51bGwsImNyZWF0ZWQiOjE0NTE4NjgyNzg4NTAyNjh9.frEQlSM9UDNWGFubFPgmNuYPz4VVZ5c13pDk3flr3ao"
    val userType = "user"
    val userId = "5689c0767caba70027b96a71"
    val username = "WTFPEDRO"
    val yoCount = 0

    val jsonString =
      s"""
         |{
         | "api_token":"$apiToken",
         | "display_name":"$displayName",
         | "is_api_user":$isApiUser,
         | "is_subscribable":$isSubscribable,
         | "tok":"$tok",
         | "type":"$userType",
         | "user_id":"$userId",
         | "username":"$username",
         | "yo_count":$yoCount
         |}""".stripMargin

    val json = Json.parse(jsonString)

    val account = json.as[Account]

    account.apiToken should be(apiToken)
    account.displayName should be(displayName)
    account.isApiUser should be(isApiUser)
    account.isSubscribable should be(isSubscribable)
    account.tok should be(tok)
    account.userType should be(userType)
    account.userId should be(userId)
    account.username should be(username)
    account.yoCount should be(yoCount)
  }

}
