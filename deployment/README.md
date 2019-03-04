## (Real) Zero Downtime Deployment
### Using extensions/v1beta1 defaults
Run load test with fortio (which is used heavily in istio tests) and make v1 deployment simultaneously
```bash
$ export INGRESS_HOST=$(minikube ip)
$ export INGRESS_PORT=$(kubectl -n istio-system get service istio-ingressgateway -o jsonpath='{.spec.ports[?(@.name=="http2")].nodePort}')
$ export GATEWAY_URL=$INGRESS_HOST:$INGRESS_PORT
(tab-1) $ docker run -v ~/fortio-client-reports:/var/lib/fortio fortio/fortio load -a -c 8 -qps 500 -t 100s "http://${GATEWAY_URL}/tea"
(tab-2) $ kubectl apply -f zero-downtime-deployment-v1.yaml
```
```
Fortio 1.3.2-pre running at 500 queries per second, 4->4 procs, for 1m40s: http://192.168.99.100:31380/tea
22:40:50 I httprunner.go:82> Starting http test for http://192.168.99.100:31380/tea with 8 threads at 500.0 qps
Starting at 500 qps with 8 thread(s) [gomax 4] for 1m40s : 6250 calls each (total 50000)
Code 200 : 2766 (92.1 %)
Code 500 : 238 (7.9 %)
Response Header Sizes : count 3004 avg 153.45972 +/- 45.02 min 0 max 168 sum 460993
Response Body/Total Sizes : count 3004 avg 190.24667 +/- 46.31 min 175 max 351 sum 571501
All done 3004 calls (plus 8 warmup) 266.308 ms avg, 30.0 qps
```
