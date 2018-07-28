package me.ycan

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD

import scala.util.Try

case class RawData(a: String, b: String, c: String)
case class GroupedMyMotherName(mother: String, count: Long){
  override def toString(): String= s"Mother name: $mother, Count: $count"
}

object ExampleSparkApp extends App{

  val conf = new SparkConf()
    .setMaster("local[2]")
    .setAppName("test-app")
  val sparkContext: SparkContext = SparkContext.getOrCreate(conf)

  val rawData: RDD[String] = sparkContext.textFile(path = "/Users/ycan/Desktop/test.txt")

  val readData: RDD[Try[RawData]] =rawData.map{ line =>
    val fields = line.split(",")
    Try(
      RawData(fields(0), fields(1), fields(2))
    )
  }



  val filteredRawData: RDD[RawData] = readData
    .filter(maybeRawData => maybeRawData.isSuccess)
    .map(data => data.get)

//  filteredRawData.map(r => (r.a, r)).reduceByKey{ t =>
//
//  }


  val result: RDD[GroupedMyMotherName] = filteredRawData.groupBy(_.a).map{ case (key, values) =>
    GroupedMyMotherName(key, values.size)
  }

  val collectedResult: Array[GroupedMyMotherName] = result.collect()

  collectedResult.foreach(println)












//    val sparkSession = SparkSession
//      .builder()
//      .appName("my-test-spark-app")
//      .getOrCreate()
//


}
