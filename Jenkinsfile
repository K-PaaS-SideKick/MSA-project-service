pipeline {
    agent any

    environment {
        DOCKER_CREDENTIALS_ID = 'Docker_sangwookie'
        DOCKER_IMAGE_NAME = 'sidekick-project-service'
        KUBECONFIG_CREDENTIALS_ID = 'jenkins'
        KUBERNETES_NAMESPACE = 'jenkins'

        SPRING_DATASOURCE_URL = credentials('spring-datasource-url')
        SPRING_DATASOURCE_USERNAME = credentials('spring-datasource-username')
        SPRING_DATASOURCE_PASSWORD = credentials('spring-datasource-password')
    }

    stages {
        stage('Clone Repository') {
            steps {
                // Clone the repository
                git credentialsId: 'GitHubCred', branch: 'main', url: 'https://github.com/SangWookie/SideKick_Project_Service'
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    // Build Docker image
                    docker.build(DOCKER_IMAGE_NAME)
                }
            }
//             steps {
//                     container('docker') {
//                       sh 'docker build -t ss69261/testing-image:latest .'
//                     }
//                   }
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

        stage('Deploy to Kubernetes') {
            steps {
                script {
                    // Set up Kubernetes config
                    withKubeConfig(credentialsId: KUBECONFIG_CREDENTIALS_ID) {
                        // Deploy the Docker image to Kubernetes
                        kubectl.apply(file: 'k8s/deployment.yaml')
                        kubectl.apply(file: 'k8s/service.yaml')
                    }
                }
            }
        }
    }

//     post {
//         always {
//             // Clean up actions, such as deleting old images or workspace
//         }
//     }
}
