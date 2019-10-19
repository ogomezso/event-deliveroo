#!/usr/bin/env bash



COMMAND=$1
if [ "$COMMAND" == "startenv" ]
then
    echo "*************** Reset SWARM *************"
    docker swarm leave --force
    echo -e "*************************************\n"

    # Init swarm
    echo "*************** Init SWARM *************"
    echo Init Docker Swarm
        docker swarm init
    echo -e "*************************************\n"


    # Deploy stack envioronment -> db, kafka,...
    echo "*********** Stack environment ******"
        docker stack deploy env-stack -c docker-stack-env.yml
    echo -e "*************************************\n"
fi

if [ "$COMMAND" == "startapp" ]
then
    # Build APP images
    echo "********** Build APP docker images *******"
        ./build-images.sh
    echo "******************************************\n"

    # Deploy stack APP
    echo "*********** Stack APP   ***************"
        docker stack deploy app-stack -c docker-stack-app.yml
    echo -e "*************************************\n"
fi

if [ "$COMMAND" == "start" ]
then
    echo "*************** Reset SWARM *************"
    docker swarm leave --force
    echo -e "*************************************\n"

    # Init swarm
    echo "*************** Init SWARM *************"
    echo Init Docker Swarm
        docker swarm init
    echo -e "*************************************\n"


    # Deploy stack envioronment -> db, kafka,...
    echo "*********** Stack environment ******"
        docker stack deploy env-stack -c docker-stack-env.yml
    echo -e "*************************************\n"

    # Build APP images
    echo "********** Build APP docker images *******"
        ./build-images.sh
    echo "******************************************\n"

    # Deploy stack APP
    echo "*********** Stack APP   ***************"
        docker stack deploy app-stack -c docker-stack-app.yml
    echo -e "*************************************\n"
fi

if [ "$COMMAND" == "stop" ]
then
    echo "******************* delete services *********"
        docker service rm $(docker service ls -q)
    echo "*********************************************\n"
fi

if [ "$COMMAND" == "destroy" ]
then
    echo "******************* delete images ***********"
        docker rmi -f $(docker images -q)
    echo "*********************************************\n"
fi