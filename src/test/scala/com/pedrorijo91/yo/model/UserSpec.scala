package com.pedrorijo91.yo.model

import com.pedrorijo91.yo.YoGeneralSpec
import play.api.libs.json.Json

class UserSpec extends YoGeneralSpec {

  "User" should "be correctly parsed from full JSON" in {

    val displayName: String = "Pedro R."
    val firstName: String = "Pedro"
    val isApiUser = true
    val isSubscribable = false
    val lastName = "Rijo"
    val name = "Pedro Rijo"
    val photo = "https://s3.amazonaws.com/yoapp-images/553265097643480027317b68036d5393.jpg"
    val userType = "user"
    val userId = "553265097643480027317b68"
    val username = "PEDRORIJO91"
    val yoCount = 140

    val jsonString =
      s"""
         |{
         | "recipient":{
         |   "display_name":"$displayName",
         |   "first_name":"$firstName",
         |   "is_api_user":$isApiUser,
         |   "is_subscribable":$isSubscribable,
         |   "last_name":"$lastName",
         |   "name":"$name",
         |   "photo":"$photo",
         |   "type":"$userType",
         |   "user_id":"$userId",
         |   "username":"$username",
         |   "yo_count":$yoCount
         | },
         | "success":true,
         | "yo_id":"5689682c5f222d002972faa1"
         |}
         |""".stripMargin

    val json = Json.parse(jsonString) \ "recipient"

    val account = json.as[User]

    account.displayName should be(displayName)
    account.firstName should be(Some(firstName))
    account.isApiUser should be(isApiUser)
    account.isSubscribable should be(isSubscribable)
    account.lastName should be(Some(lastName))
    account.name should be(name)
    account.photo should be(Some(photo))
    account.userType should be(userType)
    account.userId should be(userId)
    account.username should be(username)
    account.yoCount should be(yoCount)
  }

  it should "be correctly parsed from minimal JSON" in {

    val displayName: String = "Pedro R."
    val isApiUser = true
    val isSubscribable = false
    val name = "Pedro Rijo"
    val userType = "user"
    val userId = "553265097643480027317b68"
    val username = "PEDRORIJO91"
    val yoCount = 140

    val jsonString =
      s"""
         |{
         | "recipient":{
         |   "display_name":"$displayName",
         |   "is_api_user":$isApiUser,
         |   "is_subscribable":$isSubscribable,
         |   "name":"$name",
         |   "type":"$userType",
         |   "user_id":"$userId",
         |   "username":"$username",
         |   "yo_count":$yoCount
         | },
         | "success":true,
         | "yo_id":"5689682c5f222d002972faa1"
         |}
         |""".stripMargin

    val json = Json.parse(jsonString) \ "recipient"

    val account = json.as[User]

    account.displayName should be(displayName)
    account.firstName should be(None)
    account.isApiUser should be(isApiUser)
    account.isSubscribable should be(isSubscribable)
    account.lastName should be(None)
    account.name should be(name)
    account.photo should be(None)
    account.userType should be(userType)
    account.userId should be(userId)
    account.username should be(username)
    account.yoCount should be(yoCount)
  }

}
