pipeline {
    agent any
    triggers { pollSCM('* * * * *') }
    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/aasheesh001/jgsu-spring-petclinic.git' 
            }
        }
        
        stage('Build') {
            steps {
                // To run Maven on a Windows agent, use
                bat './mvnw clean package'
            }

            post {
                // If Maven was able to run the tests, even if some of the test
                // failed, record the test results and archive the jar file.
                always {
                    junit '**/target/surefire-reports/*.xml'
                    archiveArtifacts 'target/*.jar'
                }
            }
        }
    }
}
