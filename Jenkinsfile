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
                sh 'ls /var/lib/jenkins/workspace/photostad/photostad-api/build/libs'
                echo '=======================build gradle======================='
                sh 'docker-compose up -d --build'
                sh 'docker ps'
            }
        }
    }
        post {
            success {
                sh """
                curl -s -X POST https://api.telegram.org/bot6811807545:AAGl9wT-cF8niS_JsvqupVcctSliKqoYI4g/sendMessage -d chat_id=-975716027 -d text='
                    🎉 *Deployment Success API* 🎉
Project: PHOTOSTAD
Environment: STAGE
Version: 1.0.0
URL: http://35.185.181.230:8087/api/v1/
Deployed By: CHEA CHENTO

Check out the latest version in action!
                '
                """
            }
            failure {
                sh """
                curl -s -X POST https://api.telegram.org/bot6811807545:AAGl9wT-cF8niS_JsvqupVcctSliKqoYI4g/sendMessage -d chat_id=-975716027 -d text='
                    ⚠️ *Deployment Failure API* ⚠️
Project: PHOTOSTAD
Environment: STAGE
Version: 1.0.0
URL: http://35.185.181.230:8087/api/v1/
Attempted By: CHEA CHENTO

Please check the logs and take the necessary action. Contact the team if the issue persists.
                '
                """
            }
        }
}
