FROM python:3.9-slim

WORKDIR /app

COPY script.py /app/script.py

RUN pip install requests Pillow pika

ENTRYPOINT ["python", "script.py"]
