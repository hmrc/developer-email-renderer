import sbt.*

object AppDependencies {

  lazy val bootstrapPlayVersion = "10.8.0"

  def apply(): Seq[ModuleID] = dependencies ++ testDependencies

  lazy val dependencies =
    Seq(
      "uk.gov.hmrc"          %% "bootstrap-frontend-play-30" % bootstrapPlayVersion,
      "org.commonmark"       % "commonmark-ext-gfm-tables"   % "0.31.0"
    )


  lazy val testDependencies: Seq[ModuleID] = Seq(
    "uk.gov.hmrc"            %% "bootstrap-test-play-30"     % bootstrapPlayVersion,
    "org.scalatestplus"      %% "mockito-5-18"               % "3.2.19.0"
  ).map(_ % "test")
}
