# k8s-istio
Kubernetes and Istio walkthrough using dockerized spring services

## Get Started
1. Run your istio installed kubernetes cluster (https://istio.io/docs/setup/kubernetes/platform-setup/minikube/)
```
minikube start --memory=2048 --cpus=2 --kubernetes-version=v1.10.0 \
    --extra-config=apiserver.admission-control="NamespaceLifecycle,LimitRanger,ServiceAccount,PersistentVolumeLabel,DefaultStorageClass,DefaultTolerationSeconds,MutatingAdmissionWebhook,ValidatingAdmissionWebhook,ResourceQuota" \
    --vm-driver=virtualbox
```
2. Create new namespace for the tutorial and apply initial resources declaratively 
```
kubectl create ns tutorial
kubectl label namespace tutorial istio-injection=enabled
kubens tutorial
kubectl apply -f initial-setup/service-and-deployment.yaml
kubectl apply -f initial-setup/gateway.yaml
```

## How to test/debug pods
We can use network tools installed lightweight alpine container to get interaction with pods/services
```
kubectl run --restart=Never --image=raesene/alpine-nettools nettools
kubectl exec -it nettools -- /bin/sh
curl http://tea-service:9080
```
