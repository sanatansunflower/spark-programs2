//import kafka.serializer.StringDecoder
//import org.apache.spark.{SparkConf, TaskContext}
//import org.apache.spark.streaming.kafka.{HasOffsetRanges, KafkaUtils, OffsetRange}
//import org.apache.spark.streaming.{Seconds, StreamingContext}
//
//
//case class Student(name: String, totalMarks: Int, schoolName: String,
//                   state: String, email: String)
//
//object Student{
//  def parseStringToStudent(line: String): Option[Student]={
//    println("parseStringToStudent(-):"+line)
//    val dataArr=line.split(",")
//    if(dataArr.length==5){
//      Some(Student(dataArr(0),Integer.parseInt(dataArr(1)),dataArr(2),
//        dataArr(3),dataArr(4)))
//    }
//    else None
//  }
//}
//object SparkStreamingKafka{
//  def main(args: Array[String]): Unit={
//    val conf=new SparkConf().setMaster("local[2]").setAppName("Student Processor")
//    val ssc=new StreamingContext(conf, Seconds(30))
//
//    val kafkaParams=Map("metadata.broker.list"->"localhost:9092")
//    val topics=List("student_topic").toSet
//
//    val inputDSStream =KafkaUtils.createDirectStream[String,String,StringDecoder,StringDecoder](ssc,
//        kafkaParams,topics)
//
//
//    //This is to deal with topic,partition,offsets
//    inputDSStream.foreachRDD{rdd=>
//      val offsetRanges=rdd.asInstanceOf[HasOffsetRanges].offsetRanges
//      rdd.foreachPartition{iter=>
//        val o: OffsetRange=offsetRanges(TaskContext.get.partitionId)
//        println(s"${o.topic} ${o.partition} ${o.fromOffset} ${o.untilOffset} $iter")
//      }
//    }
//    val studentDSStream=inputDSStream.flatMap(line=>{
//      println(s"Process student ${line._1} :: ${line._2}")
//      Student.parseStringToStudent(line._2)
//    })
//
//    val studentMarksGT500=studentDSStream.filter(student=> {
//      println("Filtering logic :" +student)
//      student.totalMarks > 500
//    })
//    studentMarksGT500.print
//    studentMarksGT500.saveAsTextFiles("/home/demo/studentDataOutput/output")
//
//    ssc.start()
//    ssc.awaitTermination()
//  }
//}