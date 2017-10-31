name := "cookie-heaven"

//version := "0.1"
//
//scalaVersion := "2.12.4"

lazy val commonSettings = Seq(
  organization := "pl.edu.agh.iosr",
  version := "0.1.0-SNAPSHOT",
  scalaVersion := "2.12.4"
)

lazy val core = project.settings(commonSettings)

lazy val administrationService = project
  .settings(commonSettings)
  .dependsOn(core)
  .settings(
    mainClass in assembly := Some("pl.edu.agh.iosr.cookieHeaven.administration.Main"),
    assemblyJarName in assembly := "administration.jar"
  )

lazy val orderService = project
  .settings(commonSettings)
  .dependsOn(core)
  .settings(
    mainClass in assembly := Some("pl.edu.agh.iosr.cookieHeaven.order.Main"),
    assemblyJarName in assembly := "order.jar"
  )

lazy val notificationService = project
  .settings(commonSettings)
  .dependsOn(core)
  .settings(
    mainClass in assembly := Some("pl.edu.agh.iosr.cookieHeaven.notification.Main"),
    assemblyJarName in assembly := "notification.jar"
  )
