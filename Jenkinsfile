pipeline {
   agent any
   environment {
      DOCKERHUB_CREDENTIALS=credentials('dockerhub-renuka-jenkins')
   }
   tools { 
      nodejs 'NODE_HOME' 
    }
   stages {
      stage('Docker Image Build') {
         steps {
            dir('api-gateway') {
               sh 'docker build -t api-gateway-test4 .'
               sh 'docker tag api-gateway-test4:latest renukasrishti/api-gateway-test4:api-gateway-test4'
               sh 'echo $DOCKERHUB_CREDENTIALS_PSW | docker login -u $DOCKERHUB_CREDENTIALS_USR --password-stdin'
               sh 'docker push renukasrishti/api-gateway-test4:api-gateway-test4'
            }
         }
      }
      stage('Kubernetes Deployment') {
         steps {
            dir('api-gateway') {
               sh 'export KUBECONFIG=/home/exouser/.kube/config && kubectl apply -f api-gateway-deployment.yaml && kubectl rollout restart deployment/api-gateway-node-deployment'
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
