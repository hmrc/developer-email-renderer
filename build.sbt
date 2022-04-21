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

lazy val microservice = (project in file("."))
  .enablePlugins(PlayScala, SbtAutoBuildPlugin, SbtDistributablesPlugin)
  .settings(scalaSettings: _*)
  .settings(publishingSettings: _*)
  .settings(SilencerSettings(): _*)
  .settings(
    targetJvm := "jvm-1.8",
    scalaVersion := "2.12.15",
    name := appName,
    libraryDependencies ++= AppDependencies(),
    retrieveManaged := true,
    evictionWarningOptions in update := EvictionWarningOptions.default.withWarnScalaVersionEviction(false),
    routesGenerator := InjectedRoutesGenerator,
    shellPrompt := (_ => "> "),
    majorVersion := 0,
    Test / testOptions := Seq(Tests.Argument(TestFrameworks.ScalaTest, "-eT")),
    Test / unmanagedSourceDirectories += baseDirectory.value / "testCommon",
    Test / unmanagedSourceDirectories += baseDirectory.value / "test",
    testOptions in Test := Seq(
      Tests.Argument(TestFrameworks.ScalaTest, "-u", "target/test-reports"),
      Tests.Argument(TestFrameworks.ScalaTest, "-h", "target/test-reports/html-report"),
      Tests.Argument("-oD")
    ),
  )
  .configs(IntegrationTest)
  .settings(
    Defaults.itSettings,
    IntegrationTest / Keys.fork := false,
    addTestReportOption(IntegrationTest, "int-test-reports"),
    IntegrationTest / parallelExecution := false,
    IntegrationTest / testOptions := Seq(Tests.Argument(TestFrameworks.ScalaTest, "-eT")),
    IntegrationTest / unmanagedSourceDirectories += baseDirectory.value / "testCommon",
    IntegrationTest / unmanagedSourceDirectories += baseDirectory.value / "it",
    testOptions in IntegrationTest := Seq(
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
  .disablePlugins(sbt.plugins.JUnitXmlReportPlugin)


lazy val appName = "developer-email-renderer"

coverageMinimum := 31
coverageFailOnMinimum := true
coverageExcludedPackages := Seq(
  "<empty>",
  "com.kenshoo.play.metrics",
  ".*definition.*",
  "prod",
  "testOnlyDoNotUseInAppConf",
  "app",
  "uk.gov.hmrc.BuildInfo"
).mkString(";")
