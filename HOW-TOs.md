# HOW TOs

## How to resolve minikube VM time drift issue (occurs after sleep)
```bash
DSTR=$(date); minikube ssh "sudo date --set=\"$DSTR\""
```

## How to update istio install options - with tiller
`helm upgrade [RELEASE] [CHART] [flags]`
```bash
helm upgrade istio install/kubernetes/helm/istio \
    --set pilot.traceSampling=100.0 \
    --set grafana.enabled=true \
    --set tracing.enabled=true \
    --set kiali.enabled=true \
    --set grafana.enabled=true \
```

## How to update istio install options - without tiller
```bash
helm template install/kubernetes/helm/istio --name istio --namespace istio-system \
    --values install/kubernetes/helm/istio/values.yaml \
    --set pilot.traceSampling=100.0 \
    --set grafana.enabled=true \
    --set tracing.enabled=true \
    --set kiali.enabled=true \
    --set "kiali.dashboard.jaegerURL=http://localhost:16686/jaeger" \
    --set "kiali.dashboard.grafanaURL=http://localhost:3000" | kubectl apply -f -
```

## How to test/debug pods
We can create lightweight alpine pod with network tools installed, then get interaction with pods/services
```bash
$ kubectl run --restart=Never --image=raesene/alpine-nettools nettools
$ kubectl exec -it nettools -- /bin/sh
$ curl http://tea-service:9080
```

## How to use load test (fortio) - k8s
```bash
$ kubectl run -p 8080:8080 -p 8079:8079 fortio/fortio server & # For the server, optional
$ kubectl run fortio/fortio load -a -c 8 -qps 500 -t 60s "http://tea-service:9080/tea"
```  
Add `-v ~/fortio-client-reports:/var/lib/fortio` to save the report

## How to use load test (fortio) - docker
```bash
$ docker run -p 8080:8080 -p 8079:8079 fortio/fortio server & # For the server, optional
$ docker run fortio/fortio load -a -c 8 -qps 500 -t 60s "http://192.168.99.100:9080/tea"
```  
Add `-v ~/fortio-client-reports:/var/lib/fortio` to save the report
