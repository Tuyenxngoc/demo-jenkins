pipeline {
    agent any

    environment {
        JAVA_HOME = '/opt/java/openjdk'
    }

    stages {

        stage('Checkout') {
            steps {
                echo "Pulling code from GitLab branch: ${env.BRANCH_NAME}"
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
            when {
                anyOf {
                    branch 'develop'
                    branch 'master'
                    expression { env.BRANCH_NAME.startsWith('release/') }
                }
            }
            steps {
                echo 'Archiving build artifact...'
                archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
            }
        }

        stage('Deploy') {
            when {
                anyOf {
                    branch 'master'
                    expression { env.BRANCH_NAME.startsWith('release/') }
                }
            }
            steps {
                echo "Deploying branch ${env.BRANCH_NAME} to production..."
                // TODO
            }
        }

    }

    post {
        success {
            echo "Pipeline SUCCESS for branch ${env.BRANCH_NAME}!"
        }
        failure {
            echo "Pipeline FAILED for branch ${env.BRANCH_NAME}!"
        }
    }
}