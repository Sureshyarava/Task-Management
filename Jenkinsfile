pipeline{
    agent any
    tools {jdk "JDK21"}
    stages {
        stage('Checkout'){
            steps{
                checkout scm
            }
        }
        stage('build'){
            steps{
                dir('taskapi'){
                    sh './mvnw clean package'
                }
                post {
                    success {
                        archiveArtifacts artifacts: 'taskapi/target/*.jar', fingerprint: true
                    }
                }
            }
        }
    }
}
