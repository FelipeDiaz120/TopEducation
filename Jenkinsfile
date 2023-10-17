pipeline{
    agent any
    tools{
        maven "maven"
    }
    stages{
        stage("Build JAR File"){
            steps{
                checkout scmGit(branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/FelipeDiaz120/TopEducation']])
                dir("TopEducation"){
                    bat "mvn clean install"
                }
            }
        }
        stage("Test"){
            steps{
                dir("TopEducation"){
                   bat "mvn test"
                }
            }
        }

        stage("Build Docker Image"){
            steps{
                dir("TopEducation"){
                    bat "docker build -t felipediaz1/topeducation ."
                }
            }
        }
        stage("Push Docker Image"){
            steps{
                dir("TopEducation"){
                    withCredentials([string(credentialsId: 'dckrhubpassword', variable: 'dockerpass')]){
                        bat "docker login -u felipediaz1 -p ${dockerpass}"

                    }
                    bat "docker push felipediaz1/topeducation"

                }

            }
        }
    }
    post{
        always{
            dir("TopEducation"){
                bat "docker logout"
            }
        }
    }
}