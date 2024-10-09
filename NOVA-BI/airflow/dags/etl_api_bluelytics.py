from airflow import DAG
from airflow.operators.bash import BashOperator
from datetime import datetime

# Ruta al JAR (considera usar variables de entorno o un archivo de configuración)
#JAR_PATH = '/home/diego/Escritorio/NOVA-BI/scala/target/scala-2.12/bi-spark_2.12-0.1.jar'
JAR_PATH = '/home/diego/Escritorio/NOVA-BI/scala/target/scala-2.12/BI-SPARK-assembly-0.1.jar'

# Definir los argumentos del DAG
default_args = {
    'owner': 'airflow',
    'depends_on_past': False,
    'start_date': datetime(2023, 10, 7),
    'retries': 1,
}

# Crear el DAG
with DAG('etl_bluelytics_dag',
         default_args=default_args,
         schedule_interval='@daily',
         catchup=False) as dag:

    # Tarea para ejecutar etl_api_bluelytics.scala
    etl_api_bluelytics = BashOperator(
        task_id='extract_file_bluelytics',
        bash_command=f'spark-submit --class etl_api_bluelytics --master local[*] {JAR_PATH}',
    )

    # Tarea para ejecutar etl_file_bluelytics.scala
    etl_file_bluelytics = BashOperator(
        task_id='load_db_bluelytics', 
        bash_command=f'spark-submit --class etl_file_bluelytics --master local[*] {JAR_PATH}',
    )

    # Definir el orden de ejecución
    etl_api_bluelytics >> etl_file_bluelytics
