
import org.apache.spark.sql.SparkSession

object SparkStructuredStreaming {
  def main(args: Array[String]): Unit ={
    val spark=SparkSession
      .builder
      .appName("StructureNetworkWordCount")
      .master("local[*]").getOrCreate()

    import spark.implicits._

    val lines=spark.readStream
      .format("socket")
      .option("host", "localhost")
      .option("port", 9999)
      .load()

    //Split the lines into words
    val words=lines.as[String].flatMap(_.split(","))

    //Generate running word count
    //val wordCounts=words.groupBy("value").count()

    val query= words.writeStream
      .outputMode("update")
      .format("console")
      .start()

    query.awaitTermination()
  }
}