package com.pedrorijo91.yo.model

import play.api.libs.functional.syntax._
import play.api.libs.json._

case class Account(apiToken: String, displayName: String,
                   isApiUser: Boolean, isSubscribable: Boolean,
                   tok: String, userType: String,
                   userId: String, username: String,
                   yoCount: Long)

object Account {

  implicit val userReads: Reads[Account] = (
    (JsPath \ "api_token").read[String] and
      (JsPath \ "display_name").read[String] and
      (JsPath \ "is_api_user").read[Boolean] and
      (JsPath \ "is_subscribable").read[Boolean] and
      (JsPath \ "tok").read[String] and
      (JsPath \ "type").read[String] and
      (JsPath \ "user_id").read[String] and
      (JsPath \ "username").read[String] and
      (JsPath \ "yo_count").read[Long]
    ) (Account.apply _)
}