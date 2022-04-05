pipeline {
   agent any
   tools { 
      maven 'MAVEN_HOME' 
    }
   stages {
      stage('Build') {
         steps {
            sh 'mvn clean compile'
         }
      }
      stage('Test') {
         steps {
            sh 'mvn test'
         }
      }
      stage('Deploy') {
         steps {
            sshagent(["kube"]) {
               sh 'export KUBECONFIG=/home/exouser/.kube/config && kubectl apply -f auth-deployment.yaml'
            }
         }
      }
   }
}
