import sbt._

object AppDependencies {

  lazy val bootstrapPlayVersion = "7.21.0"
  lazy val logbackVersion = "1.1.10"
  lazy val jsoupVersion = "1.12.1"
  lazy val scalaCheckVersion = "1.14.0"
  private lazy val akkaVersion = "2.6.14"
  lazy val jacksonVersion = "2.11.1"

  def apply(): Seq[ModuleID] = dependencies ++ testDependencies

  lazy val dependencies =
    Seq(
      "uk.gov.hmrc"                          %% "bootstrap-frontend-play-28" % bootstrapPlayVersion,
      "net.codingwell"                       %% "scala-guice"                % "4.2.6",
      "com.beachape"                         %% "enumeratum"                 % "1.6.1",
      "org.jsoup"                            % "jsoup"                       % "1.13.1",
      "org.commonmark"                       % "commonmark-ext-gfm-tables"   % "0.19.0",
      "com.fasterxml.jackson.module"         %% "jackson-module-scala"       % jacksonVersion,
      "com.fasterxml.jackson.core"           % "jackson-annotations"         % jacksonVersion,
      "com.fasterxml.jackson.core"           % "jackson-databind"            % jacksonVersion,
      "com.fasterxml.jackson.core"           % "jackson-core"                % jacksonVersion,
      "com.fasterxml.jackson.dataformat"     % "jackson-dataformat-yaml"     % jacksonVersion
    )
  lazy val testScopes = Seq(Test.name, IntegrationTest.name).mkString(",")

  lazy val testDependencies: Seq[ModuleID] = Seq(
    "com.github.tomakehurst" %  "wiremock-jre8-standalone" % "2.33.2",
    "org.mockito"            %% "mockito-scala-scalatest"  % "1.17.29",
    "org.scalatest"          %% "scalatest"                % "3.2.17",
    "com.vladsch.flexmark"   %  "flexmark-all"             % "0.62.2",
    "uk.gov.hmrc"            %% "bootstrap-test-play-28"   % bootstrapPlayVersion
  ).map(_ % testScopes)
}
