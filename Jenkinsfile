pipeline {
    agent any

    tools {
        maven "jenkin_maven"
        jdk "jdk17"
    }

    stages {
        stage('Checkout') {
            steps {
                git 'https://github.com/shadab-reza/dxcapp.git'
            }
        }

        stage('Clean') {
            steps {
                // bat "mvn -Dmaven.test.failure.ignore=true clean package"
                bat "mvn clean"
            }
        }

         stage('Build') {
            steps {
                bat "mvn install"
            }

            post {
                // If Maven was able to run the tests, even if some of the test
                // failed, record the test results and archive the jar file.
                success {
                    junit '**/target/surefire-reports/TEST-*.xml'
                    archiveArtifacts artifacts:'target/*.war'
                }
            }
         }

        stage('SonarQube analysis') {
            steps{
                bat "echo sonar stage"
    //              withSonarQubeEnv(credentialsId: 'sonar_secret', installationName: 'sonar_server') {
    //   bat 'mvn org.sonarsource.scanner.maven:sonar-maven-plugin:3.8.0.2131:sonar'
    // }
            }
        }

        stage('Deployment'){
            steps{

                echo "deploying..."

                deploy adapters:[
                    tomcat9(url:'http://localhost:8080/manager/',credentialsId: 'tomcat_userid')
                    ],
                    war:'target/*.war'
                    // contextPath:'dxcapp'
            }
        }
    }
}
