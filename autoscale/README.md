## Autoscaling using resource metrics and custom metrics

### Using minikube
1. List addons
```bash
$ minikube addons list
```  
If metrics server is disabled, enable it
```bash
$ minikube addons enable metrics-server
```

2. Download metrics server
```bash
$ wget -qO- https://github.com/kubernetes-incubator/metrics-server/archive/v0.3.3.tar.gz | tar xvz - -C <your-path-here>
$ kubectl create -f <your-path-here>/metrics-server-0.3.3/deploy/1.8+/
```

3. Check pod and node metrics
```bash
$ minikube top pod
$ minikube top node
```
