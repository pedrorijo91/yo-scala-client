import Dependencies._

name := """yo-scala-client"""

version := "1.0.0"

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  playWS,
  scalatest
)

// coverage settings

coverageExcludedPackages := "controllers.javascript;router;views.html;views.html.feature;views.html.help;controllers.ReverseHelpController;controllers.ReverseAssets;controllers.ReverseFeatureController"

coverageMinimum := 70

coverageFailOnMinimum := true

// publish settings


organization := "com.pedrorijo91"

organizationName := "pedrorijo91"

organizationHomepage := Some(new URL("https://www.pedrorijo.com"))

publishMavenStyle := true

resolvers += Resolver.jcenterRepo

publishTo := Some("Bintray API Realm" at "https://api.bintray.com/content/pedrorijo91/maven/yo-scala-client/1.0.0")

credentials += Credentials(new File("credentials.properties"))

startYear := Some(2016)

description := "Yo Scala Client"

licenses := Seq("The MIT License (MIT)" -> url("https://opensource.org/licenses/MIT"))

pomExtra :=
  <scm>
    <url>https://github.com/pedrorijo91/yo-scala-client.git</url>
    <connection>scm:git:git@github.com:pedrorijo91/yo-scala-client.git</connection>
    <developerConnection>scm:git:https://github.com/pedrorijo91/yo-scala-client.git</developerConnection>
  </scm>
    <developers>
      <developer>
        <id>pedrorijo91</id>
        <name>Pedro Rijo</name>
        <email>pedrorijo91 [at] gmail.com</email>
        <url>https://github.com/pedrorijo91</url>
      </developer>
    </developers>
