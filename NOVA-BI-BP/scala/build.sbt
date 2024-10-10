name := "BI-SPARK"

version := "0.1"

scalaVersion := "2.12.15"

libraryDependencies ++= Seq(
  "org.typelevel" %% "cats-core" % "2.6.1",
  "io.circe" %% "circe-core" % "0.14.1",
  "io.circe" %% "circe-generic" % "0.14.1",
  "io.circe" %% "circe-parser" % "0.14.1",
  "org.scalaj" %% "scalaj-http" % "2.4.2",
  "org.postgresql" % "postgresql" % "42.3.1",
  "org.apache.spark" %% "spark-core" % "3.4.0" exclude("io.netty", "netty-all"),
  "org.apache.spark" %% "spark-sql" % "3.4.0",
  "org.mongodb.spark" %% "mongo-spark-connector" % "3.0.1"
)

// Configuración para fork y opciones de JVM
fork in run := true

javaOptions in run ++= Seq(
  "--add-exports", "java.base/sun.nio.ch=ALL-UNNAMED"
)

// Repositorios
resolvers += "Maven Central" at "https://repo1.maven.org/maven2/"

// Estrategia de fusión de archivos para el ensamblado
assemblyMergeStrategy in assembly := {
  case "META-INF/MANIFEST.MF" => MergeStrategy.discard
  case "META-INF" => MergeStrategy.discard
  case "application.conf" => MergeStrategy.concat
  case _ => MergeStrategy.first
}

// Especifica la clase principal
mainClass in assembly := Some("etl_api_bluelytics")
