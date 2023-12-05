pipeline {
    agent any
    tools{
        maven '3.9.6'
    }
    stages{
        stage('Build Maven'){
            steps{
                checkout([$class: 'GitSCM', branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/ShreyaSinha613/game_svc']]])
                sh 'mvn clean install'
            }
        }
        stage('Build docker image'){
            steps{
                script{
                    sh 'docker build -t shreyasinha613/game-svc .'
                }
            }
        }
        stage('Push image to Hub'){
            steps{
                script{
                   withCredentials([string(credentialsId: 'dockerhubpwd', variable: 'dockerhubpwd')]) {
                   sh 'docker login -u shreyasinha613 -p ${dockerhubpwd}'
}
                   sh 'docker push shreyasinha613/game-svc'
                }
            }
        }
    }
}