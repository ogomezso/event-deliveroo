#!/usr/bin/env bash

CURRENT_DIR=$PWD

cd ../order      && mvn clean install && docker build -t orderapp . && cd $CURRENT_DIR
cd ../kitchen    && mvn clean install && docker build -t kitchenapp . && cd $CURRENT_DIR
cd ../delivery   && mvn clean install && docker build -t deliveryapp . && cd $CURRENT_DIR