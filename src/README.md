## Sample Microservices
This folder contains sample microservices. These microservices are located at docker hub:
* muratzorer/tea-service:1.0.0
* muratzorer/temperature-service:1.0.0
* muratzorer/temperature-service:2.0.0

Instructions used to build and push new images to dockerhub:

```
$ docker build -t tea-service:1.0.0 -t tea-service -t muratzorer/tea-service:1.0.0 .
$ docker build -t temperature-service:1.0.0 -t temperature-service -t muratzorer/temperature-service:1.0.0 --build-arg service_version=1.0.0 .
$ docker build -t temperature-service:2.0.0 -t temperature-service -t muratzorer/temperature-service:2.0.0 --build-arg service_version=2.0.0 .
$ docker push muratzorer/tea-service:1.0.0
$ docker push muratzorer/temperature-service:1.0.0
$ docker push muratzorer/temperature-service:2.0.0
```
