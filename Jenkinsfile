pipeline {
    agent {
        kubernetes {
            yaml '''
            apiVersion: v1
            kind: Pod
            metadata:
              labels:
                app: jenkins-pipeline
            spec:
              containers:
              - name: docker
                image: docker:27.3.1-dind
                securityContext:
                  privileged: true
                volumeMounts:
                - name: docker-graph-storage
                  mountPath: /var/lib/docker
                env:
                - name: DOCKER_TLS_CERTDIR
                  value: ""
                ports:
                - containerPort: 2375
                  name: docker
              - name: docker-cli
                image: docker:27.3.1-cli
                command:
                - cat
                tty: true
                env:
                - name: DOCKER_HOST
                  value: "tcp://localhost:2375"
              volumes:
              - name: docker-graph-storage
                emptyDir: {}
            '''
        }
    }

    environment {
        DOCKERHUB_REPO = 'sangwookie/sidekick-project-service'
        GIT_REPO = 'https://github.com/K-PaaS-SideKick/MSA-project-service'
        DOCKERHUB_CREDENTIALS_ID = 'DockerCred'
        GITHUB_CREDENTIALS = credentials('GitHubCred')
    }

    stages {
        stage('Clone Repository') {
            steps {
                git branch: 'main',
                    url: 'https://github.com/K-PaaS-SideKick/MSA-project-service',
                    credentialsId: 'GitHubCred'
            }
        }

        stage('Get Git Tag') {
            steps {
                script {
                    GIT_TAG = sh(script: 'git describe --always', returnStdout: true).trim()
                }
            }
        }

        stage('Wait for Docker Daemon') {
            steps {
                container('docker-cli') {
                    script {
                        // Wait for the Docker daemon to be ready
                        def daemonReady = false
                        for (int i = 0; i < 10; i++) {
                            def result = sh(script: 'docker info > /dev/null 2>&1', returnStatus: true)
                            if (result == 0) {
                                daemonReady = true
                                break
                            }
                            echo 'Waiting for Docker daemon to be ready...'
                            sleep 5
                        }
                        if (!daemonReady) {
                            error "Docker daemon not ready"
                        }
                    }
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                container('docker-cli') {
                    script {
                        withCredentials([string(credentialsId: 'vault-key', variable: 'VAULT_SECRET')]){
                            sh """
                            docker build -t ${DOCKERHUB_REPO}:${GIT_TAG} .
                            """
                        }
                    }
                }
            }
        }

        stage('Push Docker Image') {
            steps {
                container('docker-cli') {
                    withCredentials([usernamePassword(credentialsId: "${DOCKERHUB_CREDENTIALS_ID}", usernameVariable: 'DOCKER_USERNAME', passwordVariable: 'DOCKER_PASSWORD')]) {
                        sh """
                            echo "$DOCKER_PASSWORD" | docker login -u "$DOCKER_USERNAME" --password-stdin
                            docker push ${DOCKERHUB_REPO}:${GIT_TAG}
                        """
                    }
                }
            }
        }

        stage('Checkout Helm Chart Repository') {
            steps {
                script {
                    git branch: 'main',
                        credentialsId: 'GitHubCred',
                        url: 'https://SangWookie@github.com/K-PaaS-SideKick/project-service-chart.git'
                }
            }
        }

        stage("Git Push to trigger Argo CD") {
            steps {
                withCredentials([gitUsernamePassword(credentialsId: 'GitHubCred', gitToolName: 'Default')]) {
                    sh """
                    sed -i "s/^  tag: .*/  tag: ${GIT_TAG}/" ./values.yaml
                    git add ./values.yaml
                    git config user.name "SangWookie"
                    git config user.email "swcube520@gmail.com"
                    git commit --allow-empty -m "trigger argocd: ${GIT_TAG}"
                    git push origin main
                """
                }
            }
        }
    }
}
