pipeline {
    agent any

    environment {
        JAVA_HOME = '/opt/java/openjdk'
    }

    stages {

        stage('Checkout') {
            steps {
                echo 'Pulling code from GitLab...'
                checkout scm
            }
        }

        stage('Build') {
            steps {
                echo 'Building Spring Boot app...'
                sh 'chmod +x ./mvnw'
                sh './mvnw clean package -DskipTests'
            }
        }

        stage('Test') {
            steps {
                echo 'Running unit tests...'
                sh './mvnw test'
            }
            post {
                always {
                    junit '**/target/surefire-reports/*.xml'
                }
            }
        }

        stage('Archive') {
            steps {
                echo 'Archiving build artifact...'
                archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
            }
        }

    }

    post {
        success {
            echo 'Pipeline SUCCESS!'
        }
        failure {
            echo 'Pipeline FAILED!'
        }
    }
}