@echo off
kubectl apply -f rabbit.yaml
kubectl apply -f scaledobject.yaml
kubectl apply -f rabbitJob.yaml