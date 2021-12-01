import _root_.play.core.PlayVersion
import com.typesafe.sbt.digest.Import._
import com.typesafe.sbt.uglify.Import._
import com.typesafe.sbt.web.Import._
import net.ground5hark.sbt.concat.Import._
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
  .settings(
    Concat.groups := Seq(
      "javascripts/apis-app.js" -> group(
        (baseDirectory.value / "app" / "assets" / "javascripts") ** "*.js"
      )
    ),
    uglifyCompressOptions := Seq(
      "unused=false",
      "dead_code=true"
    ),
    includeFilter in uglify := GlobFilter("apis-*.js"),
    pipelineStages := Seq(digest),
    pipelineStages in Assets := Seq(
      concat,
      uglify
    ),
    scalacOptions += "-Ypartial-unification",
  )
  .settings(scalaSettings: _*)
  .settings(publishingSettings: _*)
  .settings(SilencerSettings(): _*)
  .settings(
    targetJvm := "jvm-1.8",
    scalaVersion := "2.12.12",
    name := appName,
    libraryDependencies ++= AppDependencies(),
    retrieveManaged := true,
    evictionWarningOptions in update := EvictionWarningOptions.default.withWarnScalaVersionEviction(false),
    routesGenerator := InjectedRoutesGenerator,
    shellPrompt := (_ => "> "),
    majorVersion := 0,
    Test / testOptions := Seq(Tests.Argument(TestFrameworks.ScalaTest, "-eT")),
    Test / unmanagedSourceDirectories += baseDirectory.value / "testCommon",
    Test / unmanagedSourceDirectories += baseDirectory.value / "test"
  )
  .configs(IntegrationTest)
  .settings(
    Defaults.itSettings,
    IntegrationTest / Keys.fork := false,
    IntegrationTest / parallelExecution := false,
    IntegrationTest / testOptions := Seq(Tests.Argument(TestFrameworks.ScalaTest, "-eT")),
    IntegrationTest / unmanagedSourceDirectories += baseDirectory.value / "testCommon",
    IntegrationTest / unmanagedSourceDirectories += baseDirectory.value / "it"
  )
  .settings(
    resolvers ++= Seq(
      Resolver.typesafeRepo("releases")
    )
  )
  .disablePlugins(sbt.plugins.JUnitXmlReportPlugin)


lazy val appName = "developer-email-renderer"

coverageMinimum := 84
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
