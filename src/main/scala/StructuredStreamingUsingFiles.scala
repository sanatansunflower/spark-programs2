import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.streaming.Trigger
import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}

import scala.concurrent.duration.DurationInt

object StructuredStreamingUsingFiles{

  def main(args: Array[String]):Unit={
    val spark=SparkSession
      .builder
      .appName("StructuredStreamingUsingFile")
      .master("local[*]").getOrCreate()

    val studentCsvSchema=StructType(Array(
      StructField("name",StringType,true),
      StructField("marks",IntegerType),
      StructField("schoolName",StringType),
      StructField("state",StringType),
      StructField("email",StringType),
    ))

    val linesDF=spark.readStream
      .format("csv")
      .option("header","false")
      .schema(studentCsvSchema)
      .load("/home/demo/studentDataNew")

    val marksGT500=linesDF.filter("marks>500")

    val query= marksGT500.writeStream
      .outputMode("append")
      //.format("console")
      .format("csv")
      .trigger(Trigger.ProcessingTime(10.seconds))
      .option("checkpointLocation", "/home/demo/student_checkpoint-dir")
      .option("path","/home/demo/studentDataNew_processed")
      .start()

    query.awaitTermination()
  }
}