#!/bin/bash

echo 'Free project based on docker for test automation course'
echo 'Building Backend Services...'

echo 'Building User-Service...'
cd user-service \
    && ./mvnw clean package -DskipTests \
    && cd - || exit

echo 'Building Customer-Service...'
cd customer-service \
    && ./mvnw clean package -DskipTests\
    && cd -

echo 'Building Product-Service...'
cd product-service \
    && ./mvnw clean package  -DskipTests\
    && cd -

echo 'Building Order-Service...'
cd order-service \
    && ./mvnw clean package  -DskipTests \
    && cd -

echo 'Building Web Application...'
cd web-app \
    && ./mvnw clean package  -DskipTests \
    && cd -
