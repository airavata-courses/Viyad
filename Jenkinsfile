pipeline {
   agent any
   tools { 
      maven 'MAVEN_HOME' 
    }
   stages {
      stage('Compile') {
         steps {
            sh 'mvn clean compile'
         }
      }
      stage('Test') {
         steps {
            sh 'mvn test'
         }
      }
      stage('Build') {
         steps {
            sh 'docker version'
            sh 'docker build -t authenticationservice .'
         }
      }
      stage('Kubernetes Deployment') {
         steps {
            sh 'export KUBECONFIG=/home/exouser/.kube/config && kubectl apply -f auth-deployment.yaml'
         }
      }
   }
}
