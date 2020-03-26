pipeline {
    agent any

    tools {
        jdk 'jdk-12.0.2'
        }
    stages {
        stage('Build') {
            steps {
               sh 'mvn clean test-compile'
            }
        }
        stage('api') {
            steps {
                sh 'cd api && mvn -Dmaven.test.failure.ignore=true test'
            }
        }
        stage('domain') {
            steps {
                sh 'cd domain && mvn -Dmaven.test.failure.ignore=true test'
            }
        }
        stage('infrastructure') {
            steps {
                sh 'cd infrastructure && mvn -Dmaven.test.failure.ignore=true test'
            }
        }
        stage('service') {
            steps {
                sh 'cd service && mvn -Dmaven.test.failure.ignore=true test'
            }
        }
    }

    post {
        always {
            junit '**/target/surefire-reports/*.xml'
        }
    }
}