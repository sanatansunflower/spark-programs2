ThisBuild / version := "0.1.0-SNAPSHOT"

//ThisBuild / scalaVersion := "2.11.8"
ThisBuild / scalaVersion := "2.13.8"


lazy val root = (project in file("."))
  .settings(
    name := "SparkStreamingKafka"
    //name := "SparkStructuredStreaming"
  )


libraryDependencies+="org.apache.spark"%%"spark-core"% "3.2.1"
libraryDependencies+="org.apache.spark"%%"spark-hive"% "3.2.1"


//libraryDependencies+="org.apache.spark"%%"spark-core"% "2.3.0"
//libraryDependencies+="org.apache.spark"%%"spark-streaming"% "2.3.0"
//libraryDependencies+="org.apache.spark"%%"spark-streaming-kafka"% "3.2.0"
//
//libraryDependencies += "org.apache.spark" %% "spark-streaming-kafka" % "1.6.3"
