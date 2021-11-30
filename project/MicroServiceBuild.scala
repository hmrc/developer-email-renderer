import AppDependencies.overrides
import sbt._
import play.sbt.PlayImport._
import play.core.PlayVersion

object MicroServiceBuild extends Build with MicroService {

  val appName = "developer-email-renderer"

  override lazy val appDependencies: Seq[ModuleID] = AppDependencies()
  override lazy val dependencyOverrides = AppDependencies.overrides

}

object AppDependencies {
  private lazy val akkaVersion = "2.6.14"

  def apply() = Seq(
    ws,
    "uk.gov.hmrc"                          %% "bootstrap-frontend-play-28" % "5.16.0" exclude ("com.fasterxml.jackson.core", "jackson-databind"),
    "uk.gov.hmrc"                          %% "domain"                     % "6.0.0-play-28",
    "uk.gov.hmrc"                          %% "emailaddress"               % "3.5.0",
    "net.codingwell"                       %% "scala-guice"                % "4.2.6",
    "com.beachape"                         %% "enumeratum"                 % "1.6.1",
    "org.jsoup"                            % "jsoup"                       % "1.13.1" % "test",
    "org.scalatestplus.play"               %% "scalatestplus-play"         % "3.1.3" % "test, it",
    "uk.gov.hmrc"                          %% "service-integration-test"   % "1.2.0-play-28" % "test, it",
    "org.pegdown"                          % "pegdown"                     % "1.6.0" % "test, it",
    "org.mockito"                          % "mockito-core"                % "3.6.0" % "test, it",
    "com.github.tomakehurst"               % "wiremock"                    % "2.27.2" % "test,it",
    "org.commonjava.googlecode.markdown4j" % "markdown4j"                  % "2.2-cj-1.1",
    "org.mockito"                          %% "mockito-scala-scalatest"    % "1.16.42",
    "uk.gov.hmrc"                          %% "bootstrap-test-play-28"     % "5.16.0" exclude ("com.fasterxml.jackson.core", "jackson-databind"),
    "com.fasterxml.jackson.core"           % "jackson-databind"            % "2.10.5",
    "com.fasterxml.jackson.core"           % "jackson-databind"            % "2.10.5" % "test,it"
  )

  lazy val overrides = Seq(
    "com.typesafe.akka"          %% "akka-protobuf"              % akkaVersion,
    "com.typesafe.akka"          %% "akka-actor"                 % akkaVersion,
    "com.typesafe.akka"          %% "akka-slf4j"                 % akkaVersion,
    "com.typesafe.akka"          %% "akka-actor-typed"           % akkaVersion,
    "com.typesafe.akka"          %% "akka-serialization-jackson" % akkaVersion,
    "com.typesafe.akka"          %% "akka-stream"                % akkaVersion,
    "com.fasterxml.jackson.core" % "jackson-databind"            % "2.10.5" % "test,it"
  )
}
