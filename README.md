# k8s-istio
Kubernetes and Istio tutorial using dockerized spring services

## Prerequisites (With versions that are tested for macOS)
* [hypervisor - hyperkit v0.20180403](https://kubernetes.io/docs/tasks/tools/install-minikube/#install-a-hypervisor)
* [kubectl v1.14.1](https://kubernetes.io/docs/tasks/tools/install-minikube/#install-kubectl)
* [kubens & kubectx](https://github.com/ahmetb/kubectx#installation)
* [minikube v0.34.1](https://kubernetes.io/docs/tasks/tools/install-minikube/#install-minikube)
* [istio v1.1.7](https://istio.io/docs/setup/kubernetes/quick-start/#option-1-install-istio-without-mutual-tls-authentication-between-sidecars)
* [docker v2.0.0.3](https://www.docker.com/get-started)

## Get Started
1. Run your istio installed kubernetes cluster via [minikube](https://istio.io/docs/setup/kubernetes/prepare/platform-setup/minikube)
```bash
$ minikube start --memory=8192 --cpus=4 --kubernetes-version=v1.13.0 --vm-driver=hyperkit
```
2. Clone the repository, create new namespace for the tutorial and apply initial resources declaratively
```bash
$ git clone https://github.com/muratzorer/k8s-istio.git
$ kubectl create ns tutorial
$ kubectl label namespace tutorial istio-injection=enabled
$ kubens tutorial
$ kubectl apply -f initial-setup/service-and-deployment.yaml
$ kubectl apply -f initial-setup/destination-rule.yaml
$ kubectl apply -f initial-setup/virtual-services.yaml
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
5. Test application (Remember we set port 80 in [Gateway](https://github.com/muratzorer/k8s-istio/blob/master/initial-setup/gateway.yaml))
```bash
$ export GATEWAY_URL=$(kubectl get svc istio-ingressgateway -n istio-system | awk 'NR==2{print $4}')
$ curl http://${GATEWAY_URL}/tea
```
Now you should see "tea is hot" or "tea is cold" depending on the version of temperature service. We defined both v1 and v2 temperature-service in [DestinationRule](https://github.com/muratzorer/k8s-istio/blob/master/initial-setup/destination-rule.yaml) and weighted them equally in [VirtualService](https://github.com/muratzorer/k8s-istio/blob/master/initial-setup/virtual-services.yaml)

See [HOW-TOs](https://github.com/muratzorer/k8s-istio/blob/master/HOW-TOs.md) for some cheatsheet info

## kubectl Unauthorized Error - corrupted cluster connection
```bash
$ kubectl cluster-info
```
If the output is:
```
To further debug and diagnose cluster problems, use 'kubectl cluster-info dump'.
error: You must be logged in to the server (Unauthorized)
```
Then kubectl cluster connection is corrupted. Run the following command:
```bash
$ kubectl config unset clusters
```

## Querying etcd
```bash
$ cd /var/lib/minikube/certs
$ etcdctl --cacert="etcd/ca.crt" --key=etcd/peer.key --cert=etcd/peer.crt get / --prefix --keys-only
$ etcdctl --cacert="etcd/ca.crt" --key=etcd/peer.key --cert=etcd/peer.crt get /registry/services/specs/tutorial/tea-service
-w fields
```
