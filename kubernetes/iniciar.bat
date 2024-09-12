@echo off

echo Creando Secret para MySQL...
kubectl create secret generic mysql-pass --from-literal=password=incidenciasdb

echo Creando Secret para JWT...
kubectl create secret generic auth-jwt-secret --from-literal=jwt-secret=966165c2b3afcbedab408c8e49d24c5d887ef6ae1ed7ae0f981f46fd162828fb51f19abd9094739be8c3975fa2ccc777278e458bd8a7da4aa9892d190dc29cf4

echo Aplicando Persistent Volumes...
kubectl apply -f mysql-pv.yaml
kubectl apply -f mongo-pv.yaml

echo Aplicando Persistent Volume Claims...
kubectl apply -f mysql-pvc.yaml
kubectl apply -f mongo-pvc.yaml

echo Aplicando StatefulSets...
kubectl apply -f mysql-statefulset.yaml
kubectl apply -f mongo-statefulset.yaml

echo Aplicando Servicios para Bases de Datos...
kubectl apply -f mysql-service.yaml
kubectl apply -f mongo-service.yaml

echo Aplicando Deployment y Service de MySQL Service...
kubectl apply -f mysql-service-deployment.yaml
kubectl apply -f mysql-service-service.yaml

echo Aplicando Deployment de MongoDB Service...
kubectl apply -f mongo-service-deployment.yaml
kubectl apply -f mongo-service-service.yaml

echo Aplicando Deployment de issue-service...
kubectl apply -f issue-service-deployment.yaml
kubectl apply -f issue-service.yaml

echo Aplicando Deployment de file-service...
kubectl apply -f file-service-deployment.yaml
kubectl apply -f file-service.yaml

echo Aplicando Deployment de auth-service...
kubectl apply -f auth-service-deployment.yaml
kubectl apply -f auth-service.yaml

echo Aplicando Deployment de gateway-api...
kubectl apply -f gateway-deployment.yaml
kubectl apply -f gateway-service.yaml

kubectl apply -f rabbit.yaml
kubectl apply -f scaledobject.yaml
@REM kubectl apply -f rabbitJob.yaml

@REM echo Creando ingress...
@REM kubectl apply -f ingress.yaml

echo Despliegue de Bases de Datos y Microservicios completado exitosamente.
pause
