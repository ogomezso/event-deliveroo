## LEVANTAR APP MODO SWARM

````
$ docker network create --driver=bridge --subnet=192.168.2.0/24 --gateway=192.168.2.10 event-deliveroo-env-network

$ docker network create --driver=bridge --subnet=192.168.3.0/24 --gateway=192.168.3.10 event-deliveroo-app-network

$ cd environment

$ docker-compose up -d
````
Con esto tendremos en nuestro entorno local:

   * Un cluster de Kafka listo para recibir peticiones
   * Una instancia de MongoDB que usaremos como base de datos de nuestros Servicios Query
   
Para aprovisionar correctamente nuestra instancia de Mongo, deberemos correr en nuestro cliente favorito (https://robomongo.org/download) el siguiente comando:

```
db.createCollection( "orders", { capped: true, size: 100000 } )
```

