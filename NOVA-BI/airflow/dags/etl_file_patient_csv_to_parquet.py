from airflow import DAG
from airflow.operators.bash import BashOperator
from datetime import datetime

# Configuración
default_args = {
    'owner': 'diego castells',
    'depends_on_past': False,
    'start_date': datetime(2023, 10, 1),
    'email_on_failure': False,
    'email_on_retry': False,
    'retries': 1
}

# Ruta al JAR (considera usar variables de entorno o un archivo de configuración)
JAR_PATH = '/home/diego/Escritorio/NOVA-BI/scala/target/scala-2.12/BI-SPARK-assembly-0.1.jar'

# Crear el DAG
with DAG(
    'etl_patient_csv_to_parquet',
    default_args=default_args,
    description='DAG que ejecuta el script df_to_db.scala usando Scala CLI o Spark',
    schedule_interval='@daily',
    catchup=False
) as dag:
    run_scala_script = BashOperator(
        task_id='ORCHESTOR_PATIENT_CSV_TO_PARQUET',
        bash_command=f'spark-submit --class df_parquet --master local[*] {JAR_PATH}',
    )
