pipeline {
  
          agent any

          tools {
            maven 'maven'
          }

          environment {
            DOCKERHUB_CREDENTIALS = credentials('mezghichdokub-lab-jenkins-spring')
           }


          stages{

           // Create a new .jar file 

            stage('Create a new .jar') {

                steps {
                    
                   sh 'mvn clean install -DskipTests'
                
                      }

          
            }

            // Create a docker image

            stage('Création d"une image docker') {

                steps {
                    
                   sh 'docker build -t spring_app_july2023_amine_mezghich .'
                
                      }

            }

            // Push docker image to dockerhub

            stage('Push image to dockerhub') {

                 steps {

                  sh 'docker tag spring_app_july2023_amine_mezghich mezghichdokub/spring_app_july2023_amine_mezghich'

                  sh 'echo $DOCKERHUB_CREDENTIALS_PSW \
                  | docker login -u $DOCKERHUB_CREDENTIALS_USR \
                  --password-stdin'

                  sh 'docker push mezghichdokub/spring_app_july2023_amine_mezghich'

                       }
                
                post {

                  always {

                  sh 'docker logout'

                         }

                     }

            }

            // Run Docker-compose

             stage('Docker-compose') {

                steps {

                sh 'docker-compose up -d'

                      }

              }

          }
   

     }