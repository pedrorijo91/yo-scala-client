package com.pedrorijo91.yo.model

import play.api.libs.functional.syntax._
import play.api.libs.json._

case class User(displayName: String, firstName: Option[String],
                isApiUser: Boolean, isSubscribable: Boolean,
                lastName: Option[String], name: String,
                photo: Option[String], userType: String,
                userId: String, username: String,
                yoCount: Long)

object User {

  implicit val userReads: Reads[User] = (
    (JsPath \ "display_name").read[String] and
      (JsPath \ "first_name").readNullable[String] and
      (JsPath \ "is_api_user").read[Boolean] and
      (JsPath \ "is_subscribable").read[Boolean] and
      (JsPath \ "last_name").readNullable[String] and
      (JsPath \ "name").read[String] and
      (JsPath \ "photo").readNullable[String] and
      (JsPath \ "type").read[String] and
      (JsPath \ "user_id").read[String] and
      (JsPath \ "username").read[String] and
      (JsPath \ "yo_count").read[Long]
    ) (User.apply _)
}
