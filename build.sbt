import _root_.play.core.PlayVersion
import com.typesafe.sbt.web.Import._
import play.routes.compiler.InjectedRoutesGenerator
import play.sbt.routes.RoutesKeys.routesGenerator
import sbt.Keys.{ baseDirectory, unmanagedSourceDirectories, _ }
import sbt._
import uk.gov.hmrc.DefaultBuildSettings._
import uk.gov.hmrc.sbtdistributables.SbtDistributablesPlugin
import uk.gov.hmrc.sbtdistributables.SbtDistributablesPlugin._
import bloop.integrations.sbt.BloopDefaults

lazy val appName = "developer-email-renderer"

scalaVersion := "2.13.12"

ThisBuild / libraryDependencySchemes += "org.scala-lang.modules" %% "scala-xml" % VersionScheme.Always
ThisBuild / semanticdbEnabled := true
ThisBuild / semanticdbVersion := scalafixSemanticdb.revision

lazy val microservice = Project(appName, file("."))
  .enablePlugins(PlayScala, SbtDistributablesPlugin)
  .settings(scalaSettings: _*)
  .settings(
    name := appName,
    libraryDependencies ++= AppDependencies(),
    retrieveManaged := true,
    routesGenerator := InjectedRoutesGenerator,
    shellPrompt := (_ => "> "),
    majorVersion := 0,
    Test / testOptions := Seq(Tests.Argument(TestFrameworks.ScalaTest, "-eT")),
    Test / unmanagedSourceDirectories += baseDirectory.value / "testCommon",
    Test / unmanagedSourceDirectories += baseDirectory.value / "test",
    Test / testOptions := Seq(
      Tests.Argument(TestFrameworks.ScalaTest, "-u", "target/test-reports"),
      Tests.Argument(TestFrameworks.ScalaTest, "-h", "target/test-reports/html-report"),
      Tests.Argument("-oD")
    ),
  )
  .configs(IntegrationTest)
  .settings(integrationTestSettings(): _*)
  .settings(scalafixConfigSettings(IntegrationTest))
  .settings(
    IntegrationTest / Keys.fork := false,
    addTestReportOption(IntegrationTest, "int-test-reports"),
    IntegrationTest / parallelExecution := false,
    IntegrationTest / testOptions := Seq(Tests.Argument(TestFrameworks.ScalaTest, "-eT")),
    IntegrationTest / unmanagedSourceDirectories += baseDirectory.value / "testCommon",
    IntegrationTest / unmanagedSourceDirectories += baseDirectory.value / "it",
    IntegrationTest / testOptions := Seq(
      Tests.Argument(TestFrameworks.ScalaTest, "-u", "target/int-test-reports"),
      Tests.Argument(TestFrameworks.ScalaTest, "-h", "target/int-test-reports/html-report"),
      Tests.Argument("-oD")
    ),
  )
  .settings(
    resolvers ++= Seq(
      Resolver.typesafeRepo("releases")
    )
  )
  .disablePlugins(JUnitXmlReportPlugin)
  .settings(
    scalacOptions ++= Seq(
      "-Wconf:cat=unused&src=views/.*\\.scala:s",
      "-Wconf:cat=unused&src=templates/.*\\.scala:s",
      "-Wconf:cat=unused&src=.*RoutesPrefix\\.scala:s",
      "-Wconf:cat=unused&src=.*Routes\\.scala:s",
      "-Wconf:cat=unused&src=.*ReverseRoutes\\.scala:s"
    )
  )

coverageFailOnMinimum := true
coverageExcludedPackages := Seq(
  "<empty>",
  "com.kenshoo.play.metrics",
  ".*Reverse.*",
  ".*definition.*",
  ".*(config|testonly).*",
  ".*(BuildInfo|Routes).*",
  "prod",
  "testOnlyDoNotUseInAppConf",
  "app",
  "uk.gov.hmrc.BuildInfo"
).mkString(";")

commands ++= Seq(
  Command.command("run-all-tests") { state => "test" :: "it:test" :: state },

  Command.command("clean-and-test") { state => "clean" :: "compile" :: "run-all-tests" :: state },

  // Coverage does not need compile !
  Command.command("pre-commit") { state => "clean" :: "scalafmtAll" :: "scalafixAll" :: "coverage" :: "run-all-tests" :: "coverageOff" :: "coverageAggregate" :: state }
)