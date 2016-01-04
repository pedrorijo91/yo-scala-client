package com.pedrorijo91.yo.model

case class Location(private val latitude: String, private val longitude: String) {
  def value: String = s"$latitude,$longitude"
}
