#!/bin/bash
sudo docker-compose up --build --force-recreate -d user-service customer-service product-service
sleep 10
sudo docker-compose up --build --force-recreate -d order-service
