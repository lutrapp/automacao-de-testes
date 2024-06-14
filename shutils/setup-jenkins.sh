#!/bin/bash

# Download image
sudo docker pull jenkins/jenkins:lts-jdk17

# Run image on port 8080 with volume /var/jenkins_home
sudo docker run --name devops-jenkins -p 8080:8080 -p 50000:50000 -v /var/run/docker.sock:/var/run/docker.sock -v jenkins_home:/var/jenkins_home jenkins/jenkins:lts-jdk17