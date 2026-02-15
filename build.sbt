val scala3Version = "3.8.1"

lazy val root = project
  .in(file("."))
  .settings(
    name := "Scala 3 Project Template",
    version := "0.1.0-SNAPSHOT",

    scalaVersion := scala3Version,

    libraryDependencies ++= Seq("org.scalatest" %% "scalatest" % "3.2.19" % Test,
    "com.lihaoyi" %% "fansi" % "0.4.0",
    "org.scalafx" %% "scalafx" % "23.0.1-R34",
    "com.lihaoyi" %% "upickle" % "3.1.0"
    )

  )
