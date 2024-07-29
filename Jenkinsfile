pipeline {
    agent any

    environment {
        DOCKER_CREDENTIALS_ID = 'Docker_sangwookie'
        DOCKER_IMAGE_NAME = 'sangwookie/sidekick-project-service:latest'
        KUBECONFIG_CREDENTIALS_ID = 'jenkins'
        KUBERNETES_NAMESPACE = 'jenkins'
    }


    stages {
        stage('Clone Repository') {
            steps {
                // Clone the repository
                git credentialsId: 'GitHubCred', branch: 'main', url: 'https://github.com/SangWookie/SideKick_Project_Service'
            }
        }

        stage('Add Env') {
            steps {
                    withCredentials([file(credentialsId: 'applicationYML', variable: 'application')]) {
                    //   sh 'cp ${application}  src/main/resources/application.yml'
                       bat 'copy %application% src\\main\\resources\\application.yml'
                    }

            }
        }

        stage('Build Docker Image') {
            steps {
                script{
                    docker.build(DOCKER_IMAGE_NAME)
                }
            }
        }

        stage('Push Docker Image') {
            steps {
                script {
                    // Login to Docker registry
                    docker.withRegistry('https://index.docker.io/v1/', DOCKER_CREDENTIALS_ID) {
                        // Push Docker image
                        docker.image(DOCKER_IMAGE_NAME).push('latest')
                    }
                }
            }
        }
}
