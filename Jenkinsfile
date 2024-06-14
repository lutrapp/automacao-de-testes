pipeline {
    agent any
    tools {
        maven 'default'
        dockerTool 'default'
        allure 'allure'
    }
    stages {
        stage ('Pre-Build Info') {
            steps {
            	sh 'mvn --version'
                sh 'java --version'
                sh 'docker --version'
                sh 'docker-compose --version'
            }
        }
        stage ('Web Application'){
            steps {
               build job: 'job-build-web-app'
            }
        }
        stage ('User') {
            steps {
                build job: 'job-build-user-service'
            }
        }
        stage ('Customer') {
            steps {
                build job: 'job-build-customer-service'
            }
        }
        stage ('Product') {
            steps {
                build job: 'job-build-product-service'
            }
        }
        stage ('Order') {
            steps {
                build job: 'job-build-order-service'
            }
        }
    }
}