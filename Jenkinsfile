pipeline {
   agent any
    tools {
        jdk 'jdk'
        maven '3.8.3'
       
    }
   stages {
      stage('Build') {
         agent {
            label "maven"
         }
         steps {
            sh 'mvn clean compile'
         }
      }
      stage('Test') {
         agent {
            label "maven"
         }
         steps {
            sh 'mvn test'
         }
      }
   }
}
