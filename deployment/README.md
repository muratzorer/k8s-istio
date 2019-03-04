## (Real) Zero Downtime Deployment
### Using extensions/v1beta1 defaults
Run load test with fortio (which is used heavily in istio tests) and make v1 deployment simultaneously
```bash
$ export INGRESS_HOST=$(minikube ip)
$ export INGRESS_PORT=$(kubectl -n istio-system get service istio-ingressgateway -o jsonpath='{.spec.ports[?(@.name=="http2")].nodePort}')
$ export GATEWAY_URL=$INGRESS_HOST:$INGRESS_PORT
(tab-1) $ docker run -v ~/fortio-client-reports:/var/lib/fortio fortio/fortio load -a -c 8 -qps 500 -t 60s "http://${GATEWAY_URL}/tea"
(tab-2) $ kubectl apply -f zero-downtime-deployment-v1.yaml
```
