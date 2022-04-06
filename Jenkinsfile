pipeline {
   agent any
   environment {
      DOCKERHUB_CREDENTIALS=credentials('dockerhub-renuka-jenkins')
   }
   stages {
      stage('Docker Image Build') {
         steps {
            dir('app') {
               sh 'docker build -t persistenceservice .'
               sh 'docker tag persistenceservice:latest renukasrishti/persistenceservice:persistenceservice'
               sh 'echo $DOCKERHUB_CREDENTIALS_PSW | docker login -u $DOCKERHUB_CREDENTIALS_USR --password-stdin'
               sh 'docker push renukasrishti/persistenceservice:persistenceservice'
            }
         }
      }
      stage('Kubernetes Deployment') {
         steps {
            dir('app') {
               sh 'export KUBECONFIG=/home/exouser/.kube/config && kubectl apply -f persistence-service-secrets.yaml && kubectl apply -f mysql-pv.yaml && kubectl apply -f mysql-deployment.yaml && kubectl apply -f persistence-service-deployment.yaml && kubectl rollout restart deployment/mysql && kubectl rollout restart deployment/persistenceservice-deployment'
            }
         }
      }
   }
   post {
      always {
         sh 'docker logout'
      }
   }
}