import scalaj.http.{Http, HttpResponse}
import io.circe._
import io.circe.parser._
import java.nio.file.{Files, Paths}
import java.nio.charset.StandardCharsets
import java.time.Instant

object etl_api_file_bluelytics {
  def main(args: Array[String]): Unit = {
    val url = "https://api.bluelytics.com.ar/v2/evolution.json"

    // Método para hacer la solicitud HTTP y obtener la respuesta
    def get(url: String, i: Int): HttpResponse[String] =
      Http(url).param("page", i.toString).asString

    // Lista para almacenar las filas del CSV
    var csvRows = Vector[String]()

    // Encabezados del CSV
    val headers = "date,source,value_sell,value_buy"
    csvRows :+= headers

    // Bucle que itera desde 1 hasta 15
    for (i <- 1 to 12) {
      val response = get(url, i)
      if (response.isError) {
        println(s"Error fetching data from page $i: ${response.statusLine}")
      } else {
        val jsonFile = response.body // Accede al cuerpo de la respuesta
        //println(s"Respuesta de la página $i: $jsonFile")  // Imprime el JSON recibido

        // Parsea el JSON como un array en lugar de buscar un campo específico "data"
        parse(jsonFile) match {
          case Right(json) =>
            json.asArray match {
              case Some(array) =>
                array.foreach { dataPoint =>
                  val cursor = dataPoint.hcursor
                  val date = cursor.get[String]("date").getOrElse("")
                  val source = cursor.get[String]("source").getOrElse("")
                  val valueSell = cursor.get[Double]("value_sell").getOrElse(0.0)
                  val valueBuy = cursor.get[Double]("value_buy").getOrElse(0.0)
                  val row = s"$date,$source,$valueSell,$valueBuy"
                  csvRows :+= row
                }
              case None =>
                println(s"No data found on page $i")
            }
          case Left(error) =>
            println(s"Error al parsear JSON en la página $i: $error")
        }
      }
    }

    // Define la ruta del archivo donde se guardará el CSV
    val path = "/home/diego/Escritorio/NOVA-BI/_data/input/dolar.csv"

    // Escribe el CSV en un archivo
    Files.write(Paths.get(path), csvRows.mkString("\n").getBytes(StandardCharsets.UTF_8))

    println(s"Datos guardados en $path")
  }
}
