import org.apache.spark.sql.{SparkSession, DataFrame}
import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.{SaveMode, functions => F}
import org.apache.spark.sql.functions.{year, month, col, lit}

object etl_db_file_dolar_month {
  def main(args: Array[String]): Unit = {
    
    // Configurar el nivel de log
    Logger.getLogger("org").setLevel(Level.ERROR)
    Logger.getLogger("com.mongodb.spark").setLevel(Level.INFO)

    // Crear una SparkSession
    val spark = SparkSession.builder()
      .appName("MongoToCSV")
      .master("local[*]")  // Ejecutar localmente usando todos los núcleos disponibles
      .config("spark.mongodb.input.uri", "mongodb://bi2024:bi2024@localhost:27017/staging.dolar_value")
      .getOrCreate()
    // Cargando datos desde MongoDB
    val df = spark.read
      .format("mongo")
      .load()

    // Mostrar los datos cargados para verificar
    df.show()

    // Extraer el año y el mes de la columna 'date'
    val dfConAnoMes = df
      .withColumn("año", year(col("date")))
      .withColumn("mes", month(col("date")))

    // Escribiendo el DataFrame en la estructura de carpetas
    dfConAnoMes
      .write
      .mode("overwrite") // Cambia a "append" si es necesario
      .partitionBy("año", "mes") // Crea carpetas por año y mes
      //.format("csv") // Cambia a "csv" si prefieres
      //.save("/home/diego/Escritorio/ETL-AIRFLOW-SCALA-SPARK/NOVA-BI/_data/target/dolar") // Cambia a tu ruta deseada
      .csv("/home/diego/Escritorio/ETL-AIRFLOW-SCALA-SPARK/NOVA-BI/_data/target/dolar") // Cambia a tu ruta deseada

    spark.stop()
  }
}