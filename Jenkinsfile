pipeline{
    agent any
    tools {jdk "Java17"}
    stages {
        stage('Checkout'){
            steps{
                checkout scm
            }
        }
        stage('build'){
            steps{
                dir('taskapi'){
                    sh 'mvn clean package'
                }
            }
        }
    }
}