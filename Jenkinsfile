pipeline {
   agent any
   tools { 
      maven 'MAVEN_HOME' 
    }
   stages {
      stage('Compile') {
         steps {
            dir('auth') {
               sh 'mvn clean compile'
            }
         }
      }
      stage('Test') {
         steps {
            dir('auth') {
               sh 'mvn test'
            }
         }
      }
      stage('Build') {
         steps {
            dir('auth') {
               sh 'mvn install'
            }
         }
      }
      stage('Docker Image Build') {
         steps {
            dir('auth') {
               sh 'docker build -t authenticationservice .'
               sh 'docker tag authenticationservice:latest renukasrishti/authenticationservice:authservice'
               sh 'docker push renukasrishti/authenticationservice:authservice'
            }
         }
      }
      stage('Kubernetes Deployment') {
         steps {
            dir('auth') {
               sh 'export KUBECONFIG=/home/exouser/.kube/config && kubectl apply -f auth-deployment.yaml'
            }
         }
      }
   }
}
