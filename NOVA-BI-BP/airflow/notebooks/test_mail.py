from airflow import DAG
from airflow.operators.python_operator import PythonOperator
from datetime import datetime, timedelta
from email.message import EmailMessage
import smtplib
import ssl
import os
from dotenv import load_dotenv

# Carga las variables de entorno desde el archivo .env
load_dotenv()

# Variables de configuración del correo electrónico
email_sender = os.getenv('EMAIL_SENDER')
email_receiver = os.getenv('EMAIL_RECEIVER')
password = os.getenv('EMAIL_PASSWORD')
imap_server = os.getenv('IMAP_SERVER')

def send_email():
    subject = "Test Email from Airflow"
    body = "This is a test email sent from an Airflow DAG."

    # Create the email message
    msg = EmailMessage()
    msg['From'] = email_sender
    msg['To'] = email_receiver
    msg['Subject'] = subject
    msg.set_content(body)

    # Setup the secure SSL context
    context = ssl.create_default_context()

    # Send the email
    with smtplib.SMTP_SSL('smtp.gmail.com', 465, context=context) as server:
        server.login(email_sender, password)
        server.sendmail(email_sender, email_receiver, msg.as_string())

# Definir los argumentos por defecto de la DAG
default_args = {
    'owner': 'airflow',
    'depends_on_past': False,
    'email_on_failure': False,
    'email_on_retry': False,
    'retries': 1,
    'retry_delay': timedelta(minutes=5),
}

# Definir la DAG
with DAG(
    'send_email_dag',
    default_args=default_args,
    description='A simple DAG to send an email',
    schedule_interval=timedelta(days=1),
    start_date=datetime(2023, 1, 1),
    catchup=False,
) as dag:

    # Definir la tarea para enviar el correo electrónico
    send_email_task = PythonOperator(
        task_id='send_email',
        python_callable=send_email
    )

    send_email_task

if __name__ == "__main__":
    send_email()
