#!/usr/bin/env bash


## Default value PROFILES_ACTIVE = dev
if [ -z "$PROFILES_ACTIVE" ]
then
  export PROFILES_ACTIVE="dev"
fi



COMMAND=$1
if [ "$COMMAND" == "redocker start" ]
then
    echo "*************** Reset SWARM *************"
    docker swarm leave --force
    echo -e "*************************************\n"
fi

# Init swarm
echo "*************** Init SWARM *************"
echo Init Docker Swarm
    docker swarm init
echo -e "*************************************\n"



# Deploy stack envioronment -> db, kafka,...
echo "*********** Stack envioronment ******"
    docker stack deploy -c docker-stack-env.yml env-stack --with-registry-auth
echo -e "*************************************\n"


# Deploy stack Smile
#echo "*********** Stack Smile   ***************"
#    docker stack deploy local-stack -c docker-stack-dev.yml
#echo -e "*************************************\n"


### Deploy others utilities
# # Portainer
##echo "*********** Utilities ***************"
#echo "Deploy portainer in port 9000"
#docker service create \
#    --name portainer \
#    --publish 9000:9000 \
#    --constraint 'node.role == manager' \
#     --mount type=bind,src=/var/run/docker.sock,dst=/var/run/docker.sock \
#    portainer/portainer \
#    -H unix:///var/run/docker.sock