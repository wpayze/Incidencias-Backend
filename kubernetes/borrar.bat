@echo off

echo Eliminando Secrets...
kubectl delete secret mysql-pass
kubectl delete secret auth-jwt-secret

echo Eliminando StatefulSets...
kubectl delete -f mysql-statefulset.yaml
kubectl delete -f mongo-statefulset.yaml

echo Eliminando Persistent Volume Claims...
kubectl delete -f mysql-pvc.yaml
kubectl delete -f mongo-pvc.yaml

echo Eliminando Persistent Volumes...
kubectl delete -f mysql-pv.yaml
kubectl delete -f mongo-pv.yaml

echo Eliminando Servicios para Bases de Datos...
kubectl delete -f mysql-service.yaml
kubectl delete -f mongo-service.yaml

echo Eliminando Deployment de MySQL Service...
kubectl delete -f mysql-service-deployment.yaml
kubectl delete -f mysql-service-service.yaml

echo Eliminando Deployment de MongoDB Service...
kubectl delete -f mongo-service-deployment.yaml
kubectl delete -f mongo-service-service.yaml

echo Eliminando Deployment y Servicio de issue-service...
kubectl delete -f issue-service-deployment.yaml
kubectl delete -f issue-service.yaml

echo Eliminando Deployment y Servicio de file-service...
kubectl delete -f file-service-deployment.yaml
kubectl delete -f file-service.yaml

echo Eliminando Deployment y Servicio de auth-service...
kubectl delete -f auth-service-deployment.yaml
kubectl delete -f auth-service.yaml

echo Eliminando Deployment y Servicio de gateway-api...
kubectl delete -f gateway-deployment.yaml
kubectl delete -f gateway-service.yaml

echo Eliminando ingress...
kubectl delete -f ingress.yaml

kubectl delete -f scaledobject.yaml
kubectl delete -f rabbitJob.yaml

echo Eliminación de Bases de Datos y Microservicios completada exitosamente.
pause
