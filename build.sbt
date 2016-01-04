import Dependencies._

name := """yo-scala-client"""

version := "1.0.0"

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  playWS,
  scalatest
)

parallelExecution in Test := false

// coverage settings

coverageExcludedPackages := "controllers.javascript;router;views.html;views.html.feature;views.html.help;controllers.ReverseHelpController;controllers.ReverseAssets;controllers.ReverseFeatureController"

coverageMinimum := 70

coverageFailOnMinimum := true // should be true
