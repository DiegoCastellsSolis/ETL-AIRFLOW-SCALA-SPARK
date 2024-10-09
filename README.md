# Proyecto de Business Intelligence con Airflow, Scala y Spark

Este proyecto tiene como objetivo implementar un sistema de Business Intelligence utilizando Apache Airflow para la orquestación de tareas, Scala para el procesamiento de datos y Apache Spark para el análisis de grandes volúmenes de datos.

## Tabla de Contenidos

1. [Descripción del Proyecto](#descripción-del-proyecto)
2. [Tecnologías Utilizadas](#tecnologías-utilizadas)
3. [Requisitos](#requisitos)
4. [Instalación](#instalación)
5. [Estructura del Proyecto](#estructura-del-proyecto)
6. [Uso](#uso)
7. [Contribuciones](#contribuciones)
8. [Licencia](#licencia)

## Descripción del Proyecto

Este proyecto permite la extracción, transformación y carga (ETL) de datos desde diversas fuentes hacia un sistema de almacenamiento, donde se realizarán análisis para obtener información valiosa. Airflow se utiliza para orquestar las tareas ETL, mientras que Spark y Scala se encargan del procesamiento y análisis de datos.

## Tecnologías Utilizadas

- **Apache Airflow**: Herramienta para la programación y orquestación de flujos de trabajo.
- **Apache Spark**: Framework de procesamiento de datos en grandes volúmenes.
- **Scala**: Lenguaje de programación utilizado para implementar las tareas de procesamiento en Spark.
- **Base de Datos**: Especificar la base de datos utilizada (por ejemplo, PostgreSQL, MySQL, etc.).

## Requisitos

- Python 3.6+
- Apache Airflow 2.x
- Scala 2.12+
- Apache Spark 3.x
- Maven (para gestión de dependencias en Scala)
- Java JDK 8+
- Dependencias de Python: especificar en `requirements.txt`
- Dependencias de Scala: especificar en `pom.xml`

## Instalación

### 1. Clonar el repositorio

```bash
git clone https://github.com/tu_usuario/tu_repositorio.git
cd tu_repositorio
```

### 2. Instalar dependencias de Python

```bash
pip install -r requirements.txt
```

### 3. Configurar Apache Airflow

- Configurar el archivo `airflow.cfg` según tus necesidades.
- Iniciar el servidor de Airflow:

```bash
airflow db init
airflow webserver --port 8080
airflow scheduler
```

### 4. Compilar el proyecto de Scala

Navegar a la carpeta del proyecto de Scala y compilar con Maven:

```bash
cd scala_project
mvn clean package
```

## Estructura del Proyecto

```plaintext
tu_repositorio/
│
├── airflow_dags/           # DAGs de Airflow
│   ├── etl_dag.py          # DAG principal de ETL
│   └── ...                 # Otros DAGs
│
├── scala_project/          # Proyecto Scala
│   ├── src/                # Código fuente
│   ├── pom.xml             # Archivo de configuración de Maven
│   └── ...                 # Otros archivos de configuración
│
├── requirements.txt        # Dependencias de Python
└── README.md               # Este archivo
```

## Uso

1. Iniciar los servidores de Airflow.
2. Acceder a la interfaz web de Airflow en `http://localhost:8080`.
3. Activar el DAG de ETL y monitorear el proceso de ejecución.
4. Los resultados del procesamiento se almacenarán en la base de datos especificada.

## Contribuciones

¡Las contribuciones son bienvenidas! Por favor, sigue estos pasos:

1. Haz un fork del proyecto.
2. Crea una nueva rama para tu característica o corrección: `git checkout -b feature/nueva-caracteristica`.
3. Realiza tus cambios y haz commit: `git commit -m 'Añadir nueva característica'`.
4. Sube tus cambios: `git push origin feature/nueva-caracteristica`.
5. Abre un Pull Request.

## Licencia

Este proyecto está licenciado bajo la [Licencia MIT](LICENSE).
