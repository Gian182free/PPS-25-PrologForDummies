val scala3Version = "3.8.1"

lazy val root = project
  .in(file("."))
  .settings(
    name := "PfD - Prolog for Dummies",
    version := "0.1.0-SNAPSHOT",
    scalaVersion := scala3Version,

    libraryDependencies ++= Seq(
      "org.scalatest" %% "scalatest" % "3.2.19" % Test,
      "com.lihaoyi" %% "fansi" % "0.4.0",
      "org.scalafx" %% "scalafx" % "23.0.1-R34",
      "com.lihaoyi" %% "upickle" % "3.1.0",
      "it.unibo.alice.tuprolog" % "tuprolog" % "3.3.0"
    ),

    assembly / assemblyMergeStrategy := {
      case PathList("META-INF", _*) => MergeStrategy.discard
      case _                        => MergeStrategy.first
    }
  )

Compile / doc / scalacOptions ++= Seq(
  "-doc-title", "Prolog For Dummies",
  "-doc-footer", "Prolog For Dummies"
)