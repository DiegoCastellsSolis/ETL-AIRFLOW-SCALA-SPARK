import org.apache.spark.sql.{SparkSession, DataFrame}

object df_parquet {
  def main(args: Array[String]): Unit = {
    // Crear una sesión Spark
    val spark = SparkSession.builder()
      .appName("DataFrame Example")
      .master("local[*]")
      .config("spark.sql.debug.maxToString", "1000") // Para obtener más detalles
      .config("spark.sql.parquet.compression.codec", "snappy") // Configurar compresión
      .getOrCreate()
    
    val path_csv: String = "/home/diego/Escritorio/NOVA-BI/_data/input/PatientInfo.csv"
    val path_parquet: String = "/home/diego/Escritorio/NOVA-BI/_data/target/PatientInfo.parquet"


    // Cargar el archivo CSV
    val df = spark.read
      .option("header", "true")
      .option("inferSchema", "true")
      .csv(path_csv)

    // Mostrar el esquema y las primeras filas (para verificación)
    df.printSchema()
    df.show()

    // Escribir el DataFrame en formato Parquet
    df.write
      .mode("overwrite")
      .option("compression", "snappy") // Configurar compresión
      .parquet(path_parquet)

    // Detener la sesión Spark
    spark.stop()
  }
}