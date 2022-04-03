pipeline {
   agent { docker { image 'maven:3.8.4-openjdk-11-slim' } }
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
   }
}
