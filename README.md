## INSTALAR MINIKUBE

````
$ curl -Lo minikube https://storage.googleapis.com/minikube/releases/latest/minikube-linux-amd64 && chmod +x minikube && sudo cp minikube /usr/local/bin/ && rm minikube
$ curl -Lo kubectl https://storage.googleapis.com/kubernetes-release/release/$(curl -s https://storage.googleapis.com/kubernetes-release/release/stable.txt)/bin/linux/amd64/kubectl && chmod +x kubectl && sudo cp kubectl /usr/local/bin/ && rm kubectl
````

### Para Testear que todo haya ido bien:

````
$ kubectl run hello-minikube --image=k8s.gcr.io/echoserver:1.4 --port=8080

$ kubectl expose deployment hello-minikube --type=NodePort
service "hello-minikube" exposed

# We have now launched an echoserver pod but we have to wait until the pod is up before curling/accessing it
# via the exposed service.
# To check whether the pod is up and running we can use the following:
$ kubectl get pod
NAME                              READY     STATUS              RESTARTS   AGE
hello-minikube-3383150820-vctvh   1/1       ContainerCreating   0          3s
# We can see that the pod is still being created from the ContainerCreating status
$ kubectl get pod
NAME                              READY     STATUS    RESTARTS   AGE
hello-minikube-3383150820-vctvh   1/1       Running   0          13s
# We can see that the pod is now Running and we will now be able to curl it:
$ curl $(minikube service hello-minikube --url)
CLIENT VALUES:
client_address=192.168.99.1
command=GET
real path=/
...
$ kubectl delete service hello-minikube
service "hello-minikube" deleted
$ kubectl delete deployment hello-minikube
deployment "hello-minikube" deleted
````

### Para parar minikube:

````
$ minikube stop
````

### Minikube Dashboard

````
$ minikube dasboard
````

## CREAR INFRAESTRUCTURA

````
$ docker network create --driver=bridge --subnet=192.168.2.0/24 --gateway=192.168.2.10 event-deliveroo-network

$ cd infrastructure

$ docker-compose up -d
````
Con esto tendremos en nuestro entorno local:

   * Un cluster de Kafka listo para recibir peticiones
   * Una instancia de MongoDB que usaremos como base de datos de nuestros Servicios Query
