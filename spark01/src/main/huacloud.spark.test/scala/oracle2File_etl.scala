import com.typesafe.config.ConfigFactory
import org.apache.spark.sql._


/**
 * Created by Wping on 2018/12/28.
 */
object oracle2File_etl {

  val config = ConfigFactory.load()
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      //.master("local[4]")
      .appName("Oracle2File_zwrx_view_xfdjxx")
      .getOrCreate()
    spark.read
      .format("jdbc")
      .option("url",config.getString(Constants.ORACLE_DB_URL))
      .option("user", config.getString(Constants.ORACLE_DB_USRNAME))
      .option("password",config.getString(Constants.ORACLE_DB_PASSWORD))
      .option("driver",config.getString(Constants.ORACLE_DB_DRIVER))
      .option("dbtable","jxszwrx.zwrx_view_xfdjxx")
      .option("query","select * from zwrx_view_xfdjxx WHERE ldsjc >= '2018-09-01' ORDER BY ldsjc")
      .load().createTempView("sourceData")
    registerUdfs(spark);
    val df =spark.sql("SELECT" +
                          " xfjid" +
                          ",xfjbh" +
                          ",djrq" +
                          ",name_rm_sensitive(xm) AS xm" +
                          ",phone_rm_sensitive(lxdh) AS lxdh" +
                          ",phone_rm_sensitive(ldhm) AS ldhm" +
                          ",nrfl" +
                          ",md" +
                          ",nr" +
                          ",updatetime" +
                          ",ldsjc " +
                      "FROM sourceData")
    df.write
      .format("csv")
      .option("header", "true")
      .option("delimiter"," ")
      .mode("overwrite")
      .save("file:///root/export_tmp_files/12345/zwrx_view_xfdjxx")
      //.save("E:\\tmp\\data\\12345\\zwrx_view_xfdjxx.txt")

  }

  def registerUdfs(spark:SparkSession) ={

    spark.udf.register("name_rm_sensitive",(str:String) =>{
      var resultStr :String =""
      if(str!=null && str.length>1){
        resultStr +=str.charAt(0)
        for(chr <-str.substring(1).toCharArray){
          resultStr+="*"
        }
      }else{
        resultStr = str
      }
      resultStr
    })
    spark.udf.register("phone_rm_sensitive",(str:String)=>{
      var resultStr :String =null
      val telephone_pattern = "0?(13|14|15|18|17)[0-9]{9}"
      if(str!=null){
        if(str.matches(telephone_pattern)){
          resultStr = str.substring(0,3)+"****"+str.substring(7)
        }else if(str.length > 3){
          resultStr = str.substring(0,3)
          for(chr <-str.substring(3).toCharArray ){
            if(chr.toString.matches("[0-9]")){
              resultStr+="*"
            }else{
              resultStr+=chr
            }
          }
        }else{
          resultStr = str
        }
      }
      resultStr
    })
    spark.udf.register("cerdID_rm_sensitive",(str:String)=>{
      var resultStr :String =null
      val cerdID_pattern = """\d{17}[\d|x]|\d{15}"""
      if(str!=null){
        if(str.matches(cerdID_pattern)){
          if(str.length==15){
            resultStr=str.substring(0,6)+"******"+str.substring(12)
          }else if(str.length==18){
            resultStr=str.substring(0,6)+"********"+str.substring(14)
          }
        }else{
          resultStr = str
        }
      }
      resultStr
    })

  }
}
