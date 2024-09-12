import pika
import requests
from PIL import Image
from io import BytesIO
import os
import json

def process_image(image_name):
    image_url = f"http://file-service:8085/images/{image_name}"
    response = requests.get(image_url)
    if response.status_code != 200:
        raise Exception(f"Error al descargar la imagen: {response.status_code}")
    
    img = Image.open(BytesIO(response.content))

    output = BytesIO()
    img.save(output, format='JPEG', quality=20)
    compressed_image = output.getvalue()

    replace_url = f"http://file-service:8085/images/replaceImage/{image_name}"
    files = {
        'image': ('compressed.jpg', compressed_image, 'image/jpeg')
    }
    
    replace_response = requests.post(replace_url, files=files)

    if replace_response.status_code != 200:
        raise Exception(f"Error al reemplazar la imagen: {replace_response.status_code}")

    print(f"Imagen {image_name} procesada y reemplazada con éxito.")

def consume_from_queue():
    rabbitmq_url = os.getenv('RABBITMQ_URL', 'amqp://guest:guest@rabbitmq.default.svc.cluster.local:5672/')
    
    params = pika.URLParameters(rabbitmq_url)
    connection = pika.BlockingConnection(params)
    channel = connection.channel()

    channel.queue_declare(queue='imagenes', durable=True)

    def callback(ch, method, properties, body):
        print(f" [x] Received {body}")
        message_data = json.loads(body)
        image_name = message_data['imageName']
        process_image(image_name)
        ch.basic_ack(delivery_tag=method.delivery_tag)

    channel.basic_consume(queue='imagenes', on_message_callback=callback)

    print(" [*] Esperando mensajes. Presiona CTRL+C para salir.")
    channel.start_consuming()

if __name__ == '__main__':
    consume_from_queue()
