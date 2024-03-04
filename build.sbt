import play.routes.compiler.InjectedRoutesGenerator
import play.sbt.routes.RoutesKeys.routesGenerator
import sbt.Keys.{baseDirectory, unmanagedSourceDirectories, *}
import sbt.*
import uk.gov.hmrc.DefaultBuildSettings
import uk.gov.hmrc.DefaultBuildSettings.*
import uk.gov.hmrc.sbtdistributables.SbtDistributablesPlugin

lazy val appName = "developer-email-renderer"

ThisBuild / scalaVersion := "2.13.12"
ThisBuild / majorVersion := 0
ThisBuild / libraryDependencySchemes += "org.scala-lang.modules" %% "scala-xml" % VersionScheme.Always
ThisBuild / semanticdbEnabled := true
ThisBuild / semanticdbVersion := scalafixSemanticdb.revision

lazy val microservice = Project(appName, file("."))
  .enablePlugins(PlayScala, SbtDistributablesPlugin)
  .disablePlugins(JUnitXmlReportPlugin)
  .settings(scalaSettings: _*)
  .settings(
    name := appName,
    libraryDependencies ++= AppDependencies(),
    retrieveManaged := true,
    shellPrompt := (_ => "> "),
    Test / parallelExecution := false,
    Test / testOptions := Seq(Tests.Argument(TestFrameworks.ScalaTest, "-eT")),
    Test / unmanagedSourceDirectories += baseDirectory.value / "testCommon",
    Test / unmanagedSourceDirectories += baseDirectory.value / "test",
    Test / testOptions := Seq(
      Tests.Argument(TestFrameworks.ScalaTest, "-u", "target/test-reports"),
      Tests.Argument(TestFrameworks.ScalaTest, "-h", "target/test-reports/html-report"),
      Tests.Argument("-oD")
    ),
  )
  .settings(ScoverageSettings())
  .settings(
    resolvers ++= Seq(
      Resolver.typesafeRepo("releases")
    )
  )
  .settings(
    scalacOptions ++= Seq(
      "-Wconf:cat=unused&src=views/.*\\.scala:s",
      "-Wconf:cat=unused&src=templates/.*\\.scala:s",
      "-Wconf:cat=unused&src=.*RoutesPrefix\\.scala:s",
      "-Wconf:cat=unused&src=.*Routes\\.scala:s",
      "-Wconf:cat=unused&src=.*ReverseRoutes\\.scala:s"
    )
  )


lazy val it = (project in file("it"))
  .enablePlugins(PlayScala)
  .dependsOn(microservice % "test->test")
  .settings(DefaultBuildSettings.itSettings())
  .settings(
    name := "integration-tests",
    Test / testOptions += Tests.Argument(TestFrameworks.ScalaTest, "-eT"),
    headerSettings(Test) ++ automateHeaderSettings(Test)
  )


Global / bloopAggregateSourceDependencies := true
Global / bloopExportJarClassifiers := Some(Set("sources"))


commands ++= Seq(
  Command.command("cleanAll") { state => "clean" :: "it/clean" :: state},
  Command.command("fmtAll") { state => "scalafmtAll" :: "it/scalafmtAll" :: state},
  Command.command("fixAll") { state => "scalafixAll" :: "it/scalafixAll" :: state},
  Command.command("testAll") { state => "test" :: "it/test" :: state},
  Command.command("run-all-tests") { state => "testAll" :: state },
  Command.command("clean-and-test") { state => "cleanAll" :: "compile" :: "run-all-tests" :: state },
  Command.command("pre-commit") { state => "cleanAll" :: "fmtAll" :: "fixAll" :: "coverage" :: "run-all-tests" :: "coverageOff" :: "coverageAggregate" :: state }
)
