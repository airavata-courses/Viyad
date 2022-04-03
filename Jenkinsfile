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
            script {
               kubernetesDeploy(configs: "auth-deployment.yaml", kubeconfigId: "minikubeconfigjenkins")
            }
         }
      }
   }
}
