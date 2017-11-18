import sbt.Keys.libraryDependencies

name := "cookie-heaven"

//version := "0.1"
//
//scalaVersion := "2.12.4"

enablePlugins(JavaAppPackaging)

lazy val commonSettings = Seq(
  organization := "pl.edu.agh.iosr",
  version := "0.1.0-SNAPSHOT",
  scalaVersion := "2.12.4",
  libraryDependencies := Seq(
    "com.avsystem.commons" % "commons-shared_2.12" % "1.24.0"
  )
)

lazy val core = project
  .settings(commonSettings)

lazy val administrationService = project
  .settings(commonSettings)
  .dependsOn(core)
  .settings(
    mainClass in Compile := Some("pl.edu.agh.iosr.cookieHeaven.administration.Main$"),
//    assemblyJarName in assembly := "administration.jar",
//    assemblyMergeStrategy in assembly := {
//      case PathList("META-INF", _) => MergeStrategy.first
//      case x => MergeStrategy.defaultMergeStrategy(x)
//    },
    libraryDependencies := Seq(
      "org.springframework.boot" % "spring-boot-starter-web" % "1.5.8.RELEASE",
      "org.mongodb" % "mongo-java-driver" % "3.5.0"
    )
  )

lazy val orderService = project
  .settings(commonSettings)
  .dependsOn(core)
  .settings(
    mainClass in Compile := Some("pl.edu.agh.iosr.cookieHeaven.order.Main$"),
//    assemblyJarName in assembly := "order.jar",
//    assemblyMergeStrategy in assembly := {
//      case PathList("META-INF", _) => MergeStrategy.discard
//      case x => MergeStrategy.defaultMergeStrategy(x)
//    },
    libraryDependencies := Seq(
      "org.springframework.boot" % "spring-boot-starter-web" % "1.5.8.RELEASE",
      "org.mongodb" % "mongo-java-driver" % "3.5.0"
    )
  )
  .enablePlugins(JavaAppPackaging)

lazy val notificationService = project
  .settings(commonSettings)
  .dependsOn(core)
  .settings(
    mainClass in Compile := Some("pl.edu.agh.iosr.cookieHeaven.notification.Main$"),
//    assemblyJarName in assembly := "notification.jar"
  )
