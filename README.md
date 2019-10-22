## LEVANTAR APP MODO SWARM POR PRIMERA VEZ

1.-  Al levantar nuestra applicacion por primera vez necesitaremos setear nuestra infraestrcutura, para no complicar demasiado la cosa (y de paso practicar lo haremos de la siguiente forma)
   
   *Creamos las redes docker que usaremos:
   
 ````  
  $ docker network create --driver=bridge --subnet=192.168.2.0/24 --gateway=192.168.2.10 event-deliveroo-env-network
  
  $ docker network create --driver=bridge --subnet=192.168.3.0/24 --gateway=192.168.3.10 event-deliveroo-app-network

````
   * Arrancaremos nuestros contenedores de Infraestrcutura

   ````
   $ cd environment
   
   $ ./handle-containers startenv
   ````
   Con esto tendremos en nuestro entorno local:
      * Una red docker (con driver overlay) para nuestra infra. 
      * Un cluster de Kafka listo para recibir peticiones
      * Una instancia de MongoDB que usaremos como persistencia de nuestros servicios
      * Una consola de zipkin que nos permitira mantener la trazabilizad de las peticions a traves de nuestros servicios
      * Un recolector de metricas (Prometheus) que nos permitirá conocer el estado de nuestros servicios en todo momento
      * Una instancia de grafana con la que podremos construir dashboards con las metricas recogidas de Prometheus. 
      
   * **NOTA:** Para aprovisionar correctamente nuestra instancia de Mongo, deberemos correr en nuestro cliente favorito (https://robomongo.org/download) el siguiente comando:
   
   ```
   db.createCollection( "orders", { capped: true, size: 100000 } )
   ```
2.- Debemos comprobar en que ip levantamos cada uno de nuestros servicios de infraestrucuctura, usando los comandos docker apropiados para ello:

````
    $ docker network ls
    $ docker network inspect env-stack_event-deliveroo-env-network

````

3.- Cambiar en los application.yml de los proyectos que lo requieran las referencias a la IP/puerto del servicio que lo requiera. 

4.- Levantar nuestro ecosistema de aplicación, ejecutando el comando:

````
   $ cd environment
   
   $ ./handle-containers startapp

````
- Con este comando:
    - Construiremos los artefactos de nuestros servicios pasando todos los test que tuvieramos configurados (UT, IT).
    - Construiremos la imágenes docker de cada uno de nuestros servicios
    - Levantaremos un nuevo stack de swarm con su propia red para estos.
    
 ## LEVANTAR APP MODO SWARM UNA VEZ CONFIGURADA
 
 Bastara con correr los comandos:
 
````
    $ cd environment
    
    $ ./handle-containers start
````

## PARAR APP 

````
    $ cd environment
    
    $ ./handle-containers stop
````

## VER SLIDES CON REVEAL-MD

Con node:

````
    $ cd slides

    $ reveal-md slides.md --css style.css
````
