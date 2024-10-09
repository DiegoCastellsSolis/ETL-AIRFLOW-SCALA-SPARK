import org.apache.spark.sql.{SparkSession, DataFrame}
import java.util.Properties
import org.apache.log4j.{Level, Logger}

object etl_file_bluelytics {
  def main(args: Array[String]): Unit = {
    // Configurar el logging
    Logger.getLogger("org").setLevel(Level.ERROR)

    val spark = SparkSession.builder()
      .appName("Postgres Connection Example")
      .master("local[*]")
      .config("spark.driver.memory", "4g") // Ajustar memoria del driver
      .config("spark.executor.memory", "2g") // Ajustar memoria del executor
      .getOrCreate()


    // Configurar la conexión a PostgreSQL
    val url = "jdbc:postgresql://localhost:5432/postgres"
    val properties = new Properties()
    properties.setProperty("user", "bi2024")              // Usuario de PostgreSQL
    properties.setProperty("password", "bi2024")          // Contraseña del usuario
    properties.setProperty("driver", "org.postgresql.Driver")  // Driver JDBC

    // Ruta al archivo CSV
    val pathCsv: String = "/home/diego/Escritorio/NOVA-BI/_data/input/dolar.csv"

    try {
      // Cargar un DataFrame desde un archivo CSV
      val df: DataFrame = spark.read
        .option("header", "true") // Indica que la primera fila es el encabezado
        .option("inferSchema", "true") // Inferir el tipo de datos
        .csv(pathCsv) // Especifica la ruta al archivo CSV

      // Mostrar el esquema del DataFrame
      df.printSchema()

      // Mostrar las primeras filas del DataFrame
      df.show()

      // Validar que el DataFrame no esté vacío
      if (df.isEmpty) {
        println("El DataFrame está vacío. No se realizará la carga en PostgreSQL.")
      } else {
        // Escribir el DataFrame a PostgreSQL (crear tabla e insertar datos)
        df.write
          .mode("overwrite") // Cambia a "append" si quieres añadir datos sin borrar la tabla
          .jdbc(url, "dbo.dolar_value", properties)

        println("Datos escritos correctamente en la tabla dbo.dolar_value.")
      }
    } catch {
      case e: Exception =>
        println(s"Se produjo un error: ${e.getMessage}")
    } finally {
      // Detener la sesión de Spark
      spark.stop()
    }
  }
}
