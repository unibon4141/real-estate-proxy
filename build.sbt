name := """eal-estate-proxy"""
organization := "com.example"

version := "1.0-SNAPSHOT"
val circeVersion = "0.14.7"

lazy val root = (project in file(".")).enablePlugins(PlayScala)
val playV  = "3.0.2"
scalaVersion := "2.13.14"
libraryDependencies ++= Seq(
  "io.circe" %% "circe-core",
  "io.circe" %% "circe-generic",
  "io.circe" %% "circe-parser"
).map(_ % circeVersion)
libraryDependencies += "org.playframework" %% "play"          % playV
libraryDependencies += "org.playframework" %% "play-guice"    % playV
libraryDependencies += "com.dripower" %% "play-circe" % "3014.1"
//libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "7.0.0" % Test
libraryDependencies += ws
libraryDependencies += filters
