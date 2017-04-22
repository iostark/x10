#!/usr/bin/env groovy
            step([$class: 'PmdPublisher', canComputeNew: false, defaultEncoding: '', healthy: '', pattern: '**/pmd/*.xml', unHealthy: ''])

def BRANCH_STAGING="dev"
def BRANCH_PRODUCTION="master"
def IS_BRANCH_STAGING=false
def IS_BRANCH_PRODUCTION=false

def IS_CODE_QUALITY_STAGE_ENABLED=true
def IS_UNIT_TEST_STAGE_ENABLED=true
def IS_TEST_COVERAGE_STAGE_ENABLED=true
def IS_DEPLOY_STAGE_ENABLED=true

node {
    IS_BRANCH_STAGING=env.BRANCH_NAME.equalsIgnoreCase(BRANCH_STAGING)
    IS_BRANCH_PRODUCTION=env.BRANCH_NAME.equalsIgnoreCase(BRANCH_PRODUCTION)

    stage ("Preparing environment") {
        # Setup Jenkins slave capable of running nodejs
        def image = docker.build("-f ci/Dockerfile")
    }

    stage ("Checkout") { 
        #checkout scm 
    }

    if (IS_CODE_QUALITY_STAGE_ENABLED) {
        stage ("Code Quality") {
        }

        always {
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

