pipeline {

    agent any

    tools{
        gradle '7.5.1'
    }

    stages {
        stage("Build spring gradlew") {
            steps {
                echo '=======================build gradle======================='
                sh 'gradle clean build'
                sh 'pwd'
                echo '=======================build gradle======================='
                sh 'docker-compose up -d --build'
                sh 'docker ps'
            }
        }
    }
}
