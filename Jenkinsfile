pipeline{
    agent any
    tools {jdk "JDK17"}
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
            }
        }
    }
}