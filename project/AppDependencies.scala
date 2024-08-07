import sbt._

object AppDependencies {

  lazy val bootstrapPlayVersion = "9.1.0"

  def apply(): Seq[ModuleID] = dependencies ++ testDependencies

  lazy val dependencies =
    Seq(
      "uk.gov.hmrc"                          %% "bootstrap-frontend-play-30" % bootstrapPlayVersion,
      "org.commonmark"                       % "commonmark-ext-gfm-tables"   % "0.21.0"
    )


  lazy val testDependencies: Seq[ModuleID] = Seq(
    "uk.gov.hmrc"            %% "bootstrap-test-play-30"   % bootstrapPlayVersion,
    "org.mockito"            %% "mockito-scala-scalatest"  % "1.17.29"
  ).map(_ % "test")
}
