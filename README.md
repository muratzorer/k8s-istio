# k8s-istio
Kubernetes and Istio (v1.1) walkthrough using dockerized spring services

## Prerequisites
* [hypervisor](https://kubernetes.io/docs/tasks/tools/install-minikube/#install-a-hypervisor)
* [kubectl](https://kubernetes.io/docs/tasks/tools/install-minikube/#install-kubectl)
* [kubens & kubectx](https://github.com/ahmetb/kubectx#installation)
* [minikube v1.0.0](https://kubernetes.io/docs/tasks/tools/install-minikube/#install-minikube)
* [istio](https://istio.io/docs/setup/kubernetes/quick-start/#option-1-install-istio-without-mutual-tls-authentication-between-sidecars)
* [docker](https://www.docker.com/get-started)

## Get Started
1. Run your istio installed kubernetes cluster via [minikube](https://istio.io/docs/setup/kubernetes/prepare/platform-setup/minikube)
```bash
$ minikube start --memory=4096 --cpus=2 --kubernetes-version=v1.13.0 --vm-driver=virtualbox
```
2. Clone the repository, create new namespace for the tutorial and apply initial resources declaratively
```bash
$ git clone https://github.com/muratzorer/k8s-istio.git
$ kubectl create ns tutorial
$ kubectl label namespace tutorial istio-injection=enabled
$ kubens tutorial
$ kubectl apply -f initial-setup/service-and-deployment.yaml
$ kubectl apply -f initial-setup/gateway.yaml
```
3. Open new tab in terminal and simulate LoadBalancer. I suggest you to read [how](https://github.com/kubernetes/minikube/blob/master/docs/tunnel.md)
```bash
$ minikube tunnel
```
4. Check if **EXTERNAL-IP** value is set (if not, try [Nodeport](https://github.com/muratzorer/k8s-istio/blob/master/README-istio-1.0.md))
```bash
$ kubectl get svc istio-ingressgateway -n istio-system
```
5. Test application
```bash
$ export GATEWAY_URL=$(kubectl get svc istio-ingressgateway -n istio-system | awk 'NR==2{print $4}')
$ curl http://${GATEWAY_URL}/tea
```
Now you should see "tea is hot" or "tea is cold" depending on the version of temperature service. We did not give any *weight* in VirtualService routes, so requests will be routed to both temperature service v1 and v2 equally.

## How to test/debug pods
We can create lightweight alpine pod with network tools installed, then get interaction with pods/services
```bash
$ kubectl run --restart=Never --image=raesene/alpine-nettools nettools
$ kubectl exec -it nettools -- /bin/sh
$ curl http://tea-service:9080
```
