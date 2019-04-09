# HOW TOs

## How to update istio install options
`helm upgrade [RELEASE] [CHART] [flags]`
```bash
helm upgrade istio install/kubernetes/helm/istio \
    --set pilot.traceSampling=100.0 \
    --set grafana.enabled=true \
    --set tracing.enabled=true \
    --set kiali.enabled=true \
    --set grafana.enabled=true \
```

## How to test/debug pods
We can create lightweight alpine pod with network tools installed, then get interaction with pods/services
```bash
$ kubectl run --restart=Never --image=raesene/alpine-nettools nettools
$ kubectl exec -it nettools -- /bin/sh
$ curl http://tea-service:9080
```
