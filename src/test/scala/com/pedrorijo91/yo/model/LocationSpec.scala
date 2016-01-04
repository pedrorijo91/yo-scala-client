package com.pedrorijo91.yo.model

import com.pedrorijo91.yo.YoGeneralSpec

class LocationSpec extends YoGeneralSpec {

  "Location" should "be outputed in correct format" in {
    val latitude = "38.7166700"
    val longitude = "-9.1333300"

    val location = Location(latitude, longitude)

    location.value should be(s"$latitude,$longitude")
  }

}
