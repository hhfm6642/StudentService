#!groovy

node {
    def mvnHome

    environment {
        GIT_CREDENTIALS_ID = "5e732875-7d8e-4cc3-b115-cd6c82438f96"
    }

    stage('Preparation') {
        git changelog: false,
            credentialsId: '$GIT_CREDENTIALS_ID',
            poll: false,
            url: 'https://github.com/hhfm6642/StudentService.git'
        mvnHome = tool 'maven3'
    }

    stage('Test') {
        if (isUnix()) {
            sh "'${mvnHome}/bin/mvn' -Dmaven.test.failure.ignore clean test"
        } else {
            bat(/"${mvnHome}\bin\mvn" -Dmaven.test.failure.ignore clean test/)
        }
    }

    stage('IntegrationTest') {
        if (isUnix()) {
            sh "'${mvnHome}/bin/mvn' -Dmaven.test.failure.ignore clean verify -P integration-test"
        } else {
            bat(/"${mvnHome}\bin\mvn" -Dmaven.test.failure.ignore clean verify -P integration-test/)
        }
    }
}
