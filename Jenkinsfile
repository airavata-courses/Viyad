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
               sh "ssh -o StrictHostKeyChecking=no exouser@149.165.159.236"
               sh 'sudo kubectl apply -f auth-deployment.yaml'
            }
         }
      }
   }
}
