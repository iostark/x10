#!/usr/bin/env groovy

def BRANCH_STAGING="dev"
def BRANCH_PRODUCTION="master"
def IS_BRANCH_STAGING=false
def IS_BRANCH_PRODUCTION=false

def IS_CODE_QUALITY_STAGE_ENABLED=true
def IS_UNIT_TEST_STAGE_ENABLED=true
def IS_TEST_COVERAGE_STAGE_ENABLED=true
def IS_DEPLOY_STAGE_ENABLED=true

node {
    // Clean up workspace
    step([$class: 'WsCleanup'])

    // Keep X builds
    // Discard old builds
    // Make a build every 12h
    properties([buildDiscarder(logRotator(artifactDaysToKeepStr: '', artifactNumToKeepStr: '', daysToKeepStr: '1', numToKeepStr: '2')), pipelineTriggers([[$class: 'PeriodicFolderTrigger', interval: '12h']])])

    IS_BRANCH_STAGING=env.BRANCH_NAME.equalsIgnoreCase(BRANCH_STAGING)
    IS_BRANCH_PRODUCTION=env.BRANCH_NAME.equalsIgnoreCase(BRANCH_PRODUCTION)

    def dockerImage  = null

    stage ("Checkout") { 
        checkout scm 
        sh 'pwd; ls -al .'
    }

    stage ("Preparing Docker Jenkins Slave Image") {
        dir('ci') {
            // Setup Jenkins slave capable of running nodejs
            dockerImage = docker.build("x10-server-ci-jenkins-slave")
        }
    }

    dockerNode (image: dockerImage.id) {
        if (IS_CODE_QUALITY_STAGE_ENABLED) {
            dockerNode(image: dockerImage.id) {
                stage ("Code Quality") {
                    sh "echo FOOBAR"
                }
            }
        }

        if(IS_BRANCH_PRODUCTION) {
            if (IS_DEPLOY_STAGE_ENABLED) {
                stage ("Deploy ${flavor}") {
                    echo "Nothing to deploy for now"
                }
            }
        }
    }
}

