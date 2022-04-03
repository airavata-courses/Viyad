pipeline {
   agent {
      label any
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
