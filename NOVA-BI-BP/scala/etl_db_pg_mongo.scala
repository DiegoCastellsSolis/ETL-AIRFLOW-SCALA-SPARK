import org.apache.spark.sql.{SparkSession, DataFrame}
import java.util.Properties
import org.apache.log4j.{Level, Logger}
import com.mongodb.spark.config._
import org.apache.spark.sql.{SparkSession, SaveMode}


object etl_db_pg_mongo {
  def main(args: Array[String]): Unit = {
        
    // Crear un SparkSession
    val spark = SparkSession.builder()
      .appName("PostgresToMongoMigration")
      .master("local[*]")  // Esta línea especifica que se ejecuta localmente usando todos los núcleos disponibles
      .config("spark.mongodb.output.uri", "mongodb://bi2024:bi2024@localhost:27017/staging.dolar_value") // Cambia localhost si es necesario
      .getOrCreate()

    // Leer la tabla desde PostgreSQL
    val df = spark.read
      .format("jdbc")
      .option("url", "jdbc:postgresql://localhost:5432/postgres")
      .option("dbtable", "dbo.dolar_value") // Change this if necessary
      .option("user", "bi2024")
      .option("password", "bi2024")
      .load()

    // Mostrar los datos leídos (opcional)
    df.show()

    // Insertar datos en MongoDB
    df.write
      .format("mongo")
      .mode(SaveMode.Append)  // Usar Append para agregar datos
      .save()

    // Detener el SparkSession
    spark.stop() 
  }
}    