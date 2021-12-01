import sbt._

object AppDependencies {

  lazy val slf4jVersion = "1.7.23"
  lazy val logbackVersion = "1.1.10"
  lazy val jsoupVersion = "1.12.1"
  lazy val scalaCheckVersion = "1.14.0"
  private lazy val akkaVersion = "2.6.14"
  lazy val jacksonVersion = "2.11.1"

  def apply(): Seq[ModuleID] = dependencies ++ testDependencies

  lazy val dependencies =
    Seq(
      "uk.gov.hmrc"                          %% "bootstrap-frontend-play-28" % "5.14.0",
      "uk.gov.hmrc"                          %% "emailaddress"               % "3.5.0",
      "net.codingwell"                       %% "scala-guice"                % "4.2.6",
      "com.beachape"                         %% "enumeratum"                 % "1.6.1",
      "org.jsoup"                            % "jsoup"                       % "1.13.1" % "test",
      "org.commonjava.googlecode.markdown4j" % "markdown4j"                  % "2.2-cj-1.1",
      "com.fasterxml.jackson.module"         %% "jackson-module-scala"       % jacksonVersion,
      "com.fasterxml.jackson.core"           % "jackson-annotations"         % jacksonVersion,
      "com.fasterxml.jackson.core"           % "jackson-databind"            % jacksonVersion,
      "com.fasterxml.jackson.core"           % "jackson-core"                % jacksonVersion,
      "com.fasterxml.jackson.dataformat"     % "jackson-dataformat-yaml"     % jacksonVersion
    )
  lazy val testScopes = Seq(Test.name, IntegrationTest.name, "acceptance").mkString(",")
  lazy val testDependencies: Seq[ModuleID] = Seq(
    "uk.gov.hmrc"            %% "service-integration-test" % "1.2.0-play-28" % "test, it",
    "org.mockito"            % "mockito-core"              % "3.6.0" % "test, it",
    "com.github.tomakehurst" % "wiremock"                  % "2.27.2" % "test,it",
    "org.mockito"            %% "mockito-scala-scalatest"  % "1.16.42",
    "uk.gov.hmrc"            %% "bootstrap-test-play-28"   % "5.14.0"
  )
}
