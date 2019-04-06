name := "intro-higer-kinded-types"

version := "0.1"

scalaVersion := "2.12.8"

val catsVersion = "1.6.0"

libraryDependencies := Seq(
  "org.typelevel"               %% "cats-core"                    % catsVersion
)

scalacOptions ++= Seq(
  "-language:higherKinds",
  "-Ypartial-unification"
)

resolvers += Resolver.sonatypeRepo("releases")

addCompilerPlugin("org.spire-math" %% "kind-projector" % "0.9.10")