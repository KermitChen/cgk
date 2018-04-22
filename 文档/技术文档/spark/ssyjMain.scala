/**
  * Created by dystSpark on 2016/5/17.
  */

import java.io.{IOException, FileNotFoundException}
import java.text.SimpleDateFormat
import java.util.Properties
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerConfig, ProducerRecord}
import org.apache.spark.sql.SQLContext
import org.apache.spark.SparkConf
import org.apache.spark.rdd.RDD
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.streaming.{Milliseconds, StreamingContext}
import kafka.serializer.StringDecoder
import java.util.HashMap
import org.apache.log4j.Logger
import org.apache.log4j.PropertyConfigurator


class KafkaSink(createProducer: () => KafkaProducer[String, String]) extends Serializable {

  lazy val producer = createProducer()

  def send(topic: String, value: String): Unit = producer.send(new ProducerRecord(topic, value))
}

object KafkaSink {
  def apply(config: HashMap[String, Object]): KafkaSink = {
    val f = () => {
      val producer = new KafkaProducer[String, String](config)

      sys.addShutdownHook {
        producer.close()
      }

      producer
    }
    new KafkaSink(f)
  }
}


object ssyjMain {

  val logger = Logger.getLogger(ssyjMain.getClass.getName)

  def main(args: Array[String]) {
    try {
      //加载日志配置文件,配置文件加载错误记载日志
      PropertyConfigurator.configure("./ssyj_log4j.properties")
      //PropertyConfigurator.configure("D://MyScalas/ssyj/src/main/resources/ssyj/ssyj-bj.properties")

      //加载程序配置文件，配置文件加载错误记载日志
      val ConfFile = getClass.getResourceAsStream("/ssyj/ssyj-bj.properties")
      val prop = new Properties()
      prop.load(ConfFile)

      //mysql连接参数
      val MysqlConnectURL = prop.getProperty("MysqlConnectURL")
      if (null == MysqlConnectURL) {
        logger.error("the mysql connect url is null, exit")
        System.exit(-1)
      }
      val MysqlUser = prop.getProperty("MysqlUser")
      if (null == MysqlUser) {
        logger.error("the mysql user is null, exit")
        System.exit(-1)
      }
      val MysqlPwd = prop.getProperty("MysqlPwd")
      if (null == MysqlPwd) {
        logger.error("the mysql pwd is null, exit")
        System.exit(-1)
      }
      val mysqlprop = new Properties
      mysqlprop.setProperty("user", MysqlUser)
      mysqlprop.setProperty("password", MysqlPwd)

      //输出到kafka的相关配置
      val OutTopic = prop.getProperty("OutTopic")
      if (null == OutTopic) {
        logger.error("the kafka OutTopic is null, exit")
        System.exit(-1)
      }
      val DYTopic = prop.getProperty("DYTopic")
      if (null == DYTopic) {
        logger.error("the kafka DYTopic is null, exit")
        System.exit(-1)
      }

      // 创建kafka接收过车数据流，时间间隔为1秒
      val TimeInterval = prop.getProperty("TimeInterval").toInt
      if (TimeInterval < 0) {
        logger.error("the time interval can not less than zero, exit")
        System.exit(-1)
      }

      val AppName = prop.getProperty("AppName")
      if (null == AppName) {
        logger.error("the app name config is null, exit")
        System.exit(-1)
      }
      val sparkConf = new SparkConf().setAppName(AppName)
      //sparkConf.set("spark.streaming.concurrentJobs", "4")
      //sparkConf.set("spark.streaming.backpressure.enabled", "true")
      //sparkConf.setMaster("local[3]")
      val ssc = new StreamingContext(sparkConf, Milliseconds(TimeInterval))
      val sc = ssc.sparkContext
      val sqlContext = new SQLContext(sc)

      val checkpoint = prop.getProperty("checkpoint")
      if (null == checkpoint) {
        logger.error("checkpoint config is null, exit")
        System.exit(-1)
      }
      //sc.setCheckpointDir(checkpoint)

      //从mysql加载预警数据，并生成临时表
      val MysqlData = prop.getProperty("MysqlData")
      if (null == MysqlData) {
        logger.error("mysql data config is null, exit")
        System.exit(-1)
      }
      val DYTable = prop.getProperty("DYTable")
      if (null == DYTable) {
        logger.error("DYTable config is null, exit")
        System.exit(-1)
      }

      //从mysql加载预警数据，并生成临时表
      val OverTimeConf = prop.getProperty("OverTime")
      if (null == OverTimeConf) {
        logger.error("OverTimeConf is null, exit")
        System.exit(-1)
      }
      val OverTime = OverTimeConf.toString.toInt * 60000

      //时间转换格式
      val sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")


      var YJData = sqlContext.read.jdbc(MysqlConnectURL, MysqlData, Array("YWZT IN ('1', '7') and BY3='0'"), mysqlprop).select("BKID", "HPHM", "BKQSSJ", "BKJZSJ").rdd


      var YJMap : Map[String, (String, Long, Long)] = Map()
      YJData.collect.foreach(w => {
        YJMap += (w.getString(1) -> (w.getInt(0).toString, sdf.parse(w.get(2).toString).getTime, sdf.parse(w.get(3).toString).getTime))
      })

      var DYYJData1 = sqlContext.read.jdbc(MysqlConnectURL, DYTable, Array("JLZT='002'"), mysqlprop).select("id", "qssj", "jzsj")
      var DYXXSPData = sqlContext.read.jdbc(MysqlConnectURL, "DYXXSP", Array("spjg='1'"), mysqlprop).select("dyxxId2")
      var DYXXXQData = sqlContext.read.jdbc(MysqlConnectURL, "DYXX_XQ", Array("dyxxId!=''"), mysqlprop).select("dyxxId", "hphm2")

      var DYXX1 = DYXXSPData.join(DYYJData1, DYYJData1("id")===DYXXSPData("dyxxId2")).drop("dyxxId2")
      var DYXX = DYXX1.join(DYXXXQData, DYXX1("id")===DYXXXQData("dyxxId")).drop("dyxxId").select("id", "hphm2", "qssj", "jzsj")

      var DYYJData = DYXX.rdd

      var DYYJMap : Map[String, (String, Long, Long)] = Map()
      DYYJData.collect.foreach(w => {
        DYYJMap += (w.getString(1) -> (w.getInt(0).toString, sdf.parse(w.get(2).toString).getTime, sdf.parse(w.get(3).toString).getTime))
      })


      //红名单数据加载
      var jjhmd = sqlContext.read.jdbc(MysqlConnectURL, "JJHOMD", Array("zt = '1'"), mysqlprop).coalesce(10).select("cphid").rdd.map(w => w.getString(0)).collect

      //创建kafka消息生产者
      val KafkaBrokers = prop.getProperty("KafkaBrokers")
      if (null == KafkaBrokers) {
        logger.error("kafka brokers config is null, exit")
        System.exit(-1)
      }
      // 连接kafka的相关参数设置
      val kafkaprops = new HashMap[String, Object]()
      kafkaprops.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaBrokers)
      kafkaprops.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer")
      kafkaprops.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer")
      kafkaprops.put(ProducerConfig.CLIENT_ID_CONFIG, AppName)

      //用brokers和topics创建kafka stream
      val InputAndUpdateTopic = prop.getProperty("InputAndUpdateTopic")
      if (null == InputAndUpdateTopic) {
        logger.error("input and update topic config is null, exit")
        System.exit(-1)
      }
      val topicsSet = InputAndUpdateTopic.split(",").toSet
      val kafkaParams = Map[String, String]("metadata.broker.list" -> KafkaBrokers, "auto.offset.reset" -> "largest")


      val kafkaSink = sc.broadcast(KafkaSink(kafkaprops))
      //val producer = new KafkaProducer[String, String](kafkaprops)


      //dstream的开头是个null,实际我们需要的数据从第二个开始，split带第二个参数的时候，如果接收到的字符串中没有指定的分隔符，则会出错
      val messages = KafkaUtils.createDirectStream[String, String, StringDecoder, StringDecoder](ssc, kafkaParams, topicsSet).map(x => x._2.split("#", -1))

      messages.foreachRDD((myrdd: RDD[Array[String]]) => {
        val rdd = myrdd.coalesce(3)
        if (!rdd.isEmpty) {
          var filterdd = rdd
          //基础数据的实时更新,包括布控表的更新和交警红名单表格的更新

          val flagBKSQ = rdd.filter(line => line.contains("BKSQ-update"))
          if (!flagBKSQ.isEmpty) {
            logger.warn("the BKSQ data update")
            YJData = sqlContext.read.jdbc(MysqlConnectURL, MysqlData, Array("YWZT IN ('1', '7') and BY3='0'"), mysqlprop).select("BKID", "HPHM", "BKQSSJ", "BKJZSJ").rdd

            YJMap = YJMap.empty
            YJData.collect.foreach(w => {
              YJMap += (w.getString(1) -> (w.getInt(0).toString, sdf.parse(w.get(2).toString).getTime, sdf.parse(w.get(3).toString).getTime))
            })
            filterdd = rdd.filter(line => !line.contains("BKSQ-update"))
          }

          val flagDYXX = rdd.filter(line => line.contains("DYXX-update"))
          if (!flagDYXX.isEmpty) {
            logger.warn("the DYXX data update")
            DYYJData1 = sqlContext.read.jdbc(MysqlConnectURL, DYTable, Array("JLZT='002'"), mysqlprop).select("id", "qssj", "jzsj")
            DYXXSPData = sqlContext.read.jdbc(MysqlConnectURL, "DYXXSP", Array("spjg='1'"), mysqlprop).select("dyxxId2")
            DYXXXQData = sqlContext.read.jdbc(MysqlConnectURL, "DYXX_XQ", Array("dyxxId!=''"), mysqlprop).select("dyxxId", "hphm2")

            DYXX1 = DYXXSPData.join(DYYJData1, DYYJData1("id")===DYXXSPData("dyxxId2")).drop("dyxxId2")
            DYXX = DYXX1.join(DYXXXQData, DYXX1("id")===DYXXXQData("dyxxId")).drop("dyxxId").select("id", "hphm2", "qssj", "jzsj")
            DYYJData = DYXX.rdd

            DYYJMap = DYYJMap.empty
            DYYJData.collect.foreach(w => {
              DYYJMap += (w.getString(1) -> (w.getInt(0).toString, sdf.parse(w.get(2).toString).getTime, sdf.parse(w.get(3).toString).getTime))
            })
            filterdd = rdd.filter(line => !line.contains("DYXX-update"))
          }

          val flagJJHOMD = rdd.filter(line => line.contains("JJHOMD-update"))
          if (!flagJJHOMD.isEmpty) {
            logger.warn("the JJHOMD data update")
            jjhmd = sqlContext.read.jdbc(MysqlConnectURL, "JJHOMD", Array("zt = '1'"), mysqlprop).coalesce(10).select("cphid").rdd.map(w => w.getString(0)).collect
            filterdd = rdd.filter(line => !line.contains("JJHOMD-update"))
          }

          //此处判断，排除单独更新数据库的情况
          if(!filterdd.isEmpty){
            val logDataFrameTmp = filterdd.filter(w => w.size==16).map(w => (w(4), (w(3), w(6), w(8), w(9), w(10), w(12),
              try{
                sdf.parse(w(8)).getTime
              }catch{
                case ex: java.text.ParseException => 0 // 处理读写异常
              }, w.size)))

            val res = System.currentTimeMillis - OverTime
            val ValidData = logDataFrameTmp.filter(w => (w._2._7 > res)&&(!jjhmd.contains(w._1)))

            val SSYJData = ValidData.map(w => (w._1, (w._2._1, w._2._2, w._2._3, w._2._4, w._2._5, w._2._6, w._2._7, YJMap.get(w._1))))
              .filter(w => w._2._8 != None)
              .map(w => (w._1, (w._2._1, w._2._2, w._2._3, w._2._4, w._2._5, w._2._6, w._2._7, w._2._8.toList.head._1, w._2._8.toList.head._2, w._2._8.toList.head._3)))
              .filter(w => (w._2._7 > w._2._9)&&(w._2._7 < w._2._10))


            val DYXXData = ValidData.map(w => (w._1, (w._2._1, w._2._2, w._2._3, w._2._4, w._2._5, w._2._6, w._2._7,DYYJMap.get(w._1))))
              .filter(w => w._2._8 != None)
              .map(w => (w._1, (w._2._1, w._2._2, w._2._3, w._2._4, w._2._5, w._2._6, w._2._7, w._2._8.toList.head._1, w._2._8.toList.head._2, w._2._8.toList.head._3)))
              .filter(w => (w._2._7 > w._2._9)&&(w._2._7 < w._2._10))

            SSYJData.foreachPartition(Precord => {
              if(!Precord.isEmpty){
                Precord.foreach(IteTmp => {
                  val SendJson = "{\"BKID\":" + IteTmp._2._8 + ",\"hphm\":\"" + IteTmp._1 + "\",\"hpzl\":\"" + IteTmp._2._2 + "\",\"tgsj\":\"" + IteTmp._2._3 + "\",\"jcdid\":\"" + IteTmp._2._4 + "\",\"cdid\":\"" + IteTmp._2._5 + "\",\"tpid\":\"" + IteTmp._2._6 + "\",\"scsj\":\"" + IteTmp._2._1 + "\"}"
                  kafkaSink.value.send(OutTopic,SendJson)
                })
              }
            })

            DYXXData.foreachPartition(Precord => {
              if(!Precord.isEmpty){
                Precord.foreach(IteTmp => {
                  val SendJson = "{\"dyid\":" + IteTmp._2._8 + ",\"hphm\":\"" + IteTmp._1 + "\",\"hpzl\":\"" + IteTmp._2._2 + "\",\"tgsj\":\"" + IteTmp._2._3 + "\",\"jcdid\":\"" + IteTmp._2._4 + "\",\"cdid\":\"" + IteTmp._2._5 + "\",\"tpid\":\"" + IteTmp._2._6 + "\",\"scsj\":\"" + IteTmp._2._1 + "\"}"
                  kafkaSink.value.send(DYTopic,SendJson)
                })
              }
            })

          }
        }
      })
      ssc.start
      ssc.awaitTermination
      //producer.close
      sc.stop
    } catch {
      case ex: FileNotFoundException => logger.error(ex.getMessage) // 处理文件丢失异常
      case ex: IOException => logger.error(ex.getMessage) // 处理读写异常
      case ex: kafka.common.OffsetOutOfRangeException => logger.error(ex.getMessage) //偶尔的kafkaoffset异常
      case ex: java.lang.ArrayIndexOutOfBoundsException => logger.error(ex.getMessage)
    }
  }
}
