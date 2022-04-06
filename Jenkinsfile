pipeline {
   agent any
   environment {
      DOCKERHUB_CREDENTIALS=credentials('dockerhub-renuka-jenkins')
   }
   stages {
      stage('Docker Image Build') {
         steps {
            sh 'docker build -t dservicetestfinal .'
            sh 'docker tag dservicetestfinal:latest renukasrishti/dservicetestfinal:dservicetestfinal'
            sh 'echo $DOCKERHUB_CREDENTIALS_PSW | docker login -u $DOCKERHUB_CREDENTIALS_USR --password-stdin'
            sh 'docker push renukasrishti/dservicetestfinal:dservicetestfinal'
         }
      }
      stage('Kubernetes Deployment') {
         steps {
            sh 'export KUBECONFIG=/home/exouser/.kube/config && kubectl apply -f data-service-deploymnet.yaml && kubectl rollout restart deployment/dservice-deployment'
         }
      }
   }
   post {
      always {
         sh 'docker logout'
      }
   }
}