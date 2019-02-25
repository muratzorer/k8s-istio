# k8s-istio
Kubernetes and Istio walkthrough using dockerized spring services

## Prerequisites
* [hypervisor](https://kubernetes.io/docs/tasks/tools/install-minikube/#install-a-hypervisor)
* [kubectl](https://kubernetes.io/docs/tasks/tools/install-minikube/#install-kubectl)
* [kubens & kubectx](https://github.com/ahmetb/kubectx#installation)
* [minikube](https://kubernetes.io/docs/tasks/tools/install-minikube/#install-minikube)
* [istio](https://istio.io/docs/setup/kubernetes/quick-start/#option-1-install-istio-without-mutual-tls-authentication-between-sidecars)
* [docker](https://www.docker.com/get-started)

## Get Started
1. Run your istio installed kubernetes cluster via [minikube](https://istio.io/docs/setup/kubernetes/platform-setup/minikube/)
```
$ minikube start --memory=2048 --cpus=2 --kubernetes-version=v1.10.0 \
    --extra-config=apiserver.admission-control="NamespaceLifecycle,LimitRanger,ServiceAccount,PersistentVolumeLabel,DefaultStorageClass,DefaultTolerationSeconds,MutatingAdmissionWebhook,ValidatingAdmissionWebhook,ResourceQuota" \
    --vm-driver=virtualbox
```
2. Clone the repository, create new namespace for the tutorial and apply initial resources declaratively 
```
$ git clone https://github.com/muratzorer/k8s-istio.git
$ kubectl create ns tutorial
$ kubectl label namespace tutorial istio-injection=enabled
$ kubens tutorial
$ kubectl apply -f initial-setup/service-and-deployment.yaml
$ kubectl apply -f initial-setup/gateway.yaml
```
3. Test application after setting nodeport to service (external load balancer does not exist in minikube)
```
$ export INGRESS_HOST=$(minikube ip)
$ export INGRESS_PORT=$(kubectl -n istio-system get service istio-ingressgateway -o jsonpath='{.spec.ports[?(@.name=="http2")].nodePort}')
$ export GATEWAY_URL=$INGRESS_HOST:$INGRESS_PORT
$ curl http://${GATEWAY_URL}/tea
```
Now you should see "tea is hot" or "tea is cold" depending on the version of temperature service. DestinationRule resources are not defined in [initial-setup](https://github.com/muratzorer/k8s-istio/tree/master/initial-setup) configuration. So requests will be routed to both temperature service v1 and v2 equally.

## How to test/debug pods
We can create lightweight alpine pod with network tools installed, then get interaction with pods/services
```
$ kubectl run --restart=Never --image=raesene/alpine-nettools nettools
$ kubectl exec -it nettools -- /bin/sh
$ curl http://tea-service:9080
```
