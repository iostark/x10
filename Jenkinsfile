#!/usr/bin/env groovy

// How to Install Android SDK components ?
// 
// ssh ci-server-vm0:
// sudo su - jenkins
// xvfb-run -n 99 --server-args="-screen 0 1024x768x24" /servers/instances/jenkins/installations/sdks/android-sdk/tools/android
// x11vnc -auth /tmp/xvfb-run.CHetmm/Xauthority -display :99
// ssh my laptop:
// ssh -v -N -T -L 5800:ci-server-vm0:5900 ci-server-vm0
// chickenvnc 127.0.0.1:5800
// 
// http://lacostej.blogspot.tw/2012/03/unity3d-from-commit-to-deployment-onto.html

def BRANCH_STAGING="dev"
def BRANCH_PRODUCTION="master"
def IS_BRANCH_STAGING=false
def IS_BRANCH_PRODUCTION=false
def MATRIX_ANDROID_OS_SUPPORTED=["4.4", "5.0", "6.0", "7.0"]

def IS_CODE_QUALITY_STAGE_ENABLED=true
def IS_ASSEMBLE_STAGE_ENABLED=true
def IS_UNIT_TEST_STAGE_ENABLED=true
def IS_INSTRUMENTED_TEST_STAGE_ENABLED=false
def IS_TEST_COVERAGE_STAGE_ENABLED=true
def IS_DEPLOY_STAGE_ENABLED=true

node {
    stage ("Preparing") {
        echo "done"
    }

     // Pulls the android flavor out of the branch name the branch is prepended with /QA_
     def flavorFromBranchName = { String branchName ->
        return BRANCH_PRODUCTION.equalsIgnoreCase(branchName) ? "Release" : "Debug"
     }

     //branch name from Jenkins environment variables
     def flavor = flavorFromBranchName(env.BRANCH_NAME)

     IS_BRANCH_STAGING=env.BRANCH_NAME.equalsIgnoreCase(BRANCH_STAGING)
     IS_BRANCH_PRODUCTION=env.BRANCH_NAME.equalsIgnoreCase(BRANCH_PRODUCTION)

     def jobMaker = { String osVersion ->
	     return {
            echo "Building flavor ${flavor}"

	         node {
                 def rootWorkspace = pwd()
                 def buildUniqueName = osVersion

	             ws("${rootWorkspace}-${buildUniqueName}") {
	                 wrap([$class: 'Xvfb', additionalOptions: '', assignedLabels: '', displayNameOffset: 0, installationName: 'Default Xvfb', screen: '']) {
                         // Keep X builds
                         // Discard old builds
                         // Make a build every 12h
                         properties([buildDiscarder(logRotator(artifactDaysToKeepStr: '', artifactNumToKeepStr: '', daysToKeepStr: '1', numToKeepStr: '2')), pipelineTriggers([[$class: 'PeriodicFolderTrigger', interval: '12h']])])

                         stage ("Checkout") { 
                           // Checkout code from repository and update any submodules
                           //FIXME: Force wipeworkspace: checkout scm [$class: 'GitSCM', extensions: [[$class: 'WipeWorkspace']]]
                           checkout scm 

                           def workspace = pwd() 
                           echo "Root Workspace: ${rootWorkspace}"
                           echo "Parallel Workspace: ${workspace}"
                         }

                        if (IS_CODE_QUALITY_STAGE_ENABLED) {
                             if(IS_BRANCH_STAGING) {
                               stage ("Build (All build types)") {
                                   sh "cd android-x10 && ./gradlew clean -PBUILD_NUMBER=${env.BUILD_NUMBER}"
                                   sh "cd android-x10 && ./gradlew clean build -PBUILD_NUMBER=${env.BUILD_NUMBER}"
                               }
                             }
                             else {
                               stage ("Code Quality") {
                                   sh "cd android-x10 && ./gradlew clean -PBUILD_NUMBER=${env.BUILD_NUMBER}"
                                   sh "cd android-x10 && ./gradlew checkstyle${flavor} -PBUILD_NUMBER=${env.BUILD_NUMBER}"
                                   sh "cd android-x10 && ./gradlew lint${flavor} --continue -PBUILD_NUMBER=${env.BUILD_NUMBER}"
                                   sh "cd android-x10 && ./gradlew findbugs${flavor} --continue -PBUILD_NUMBER=${env.BUILD_NUMBER}"
                                   sh "cd android-x10 && ./gradlew pmd${flavor} -PBUILD_NUMBER=${env.BUILD_NUMBER}"
                                   sh "cd android-x10 && ./gradlew jdepend${flavor} -PBUILD_NUMBER=${env.BUILD_NUMBER}"
                               }
                             }
                    
                            always {
                                androidLint canComputeNew: false, defaultEncoding: '', healthy: '', pattern: '**/lint/lint-results*.xml', unHealthy: ''
                                step([$class: 'CheckStylePublisher', canComputeNew: false, defaultEncoding: '', healthy: '', pattern: '**/checkstyle/*.xml', unHealthy: ''])
                                step([$class: 'FindBugsPublisher', canComputeNew: false, defaultEncoding: '', excludePattern: '', healthy: '', includePattern: '', pattern: '**/findbugs/*.xml', unHealthy: ''])
                                step([$class: 'PmdPublisher', canComputeNew: false, defaultEncoding: '', healthy: '', pattern: '**/pmd/*.xml', unHealthy: ''])
                            }
                         }

                        if (IS_ASSEMBLE_STAGE_ENABLED) {
                             stage ("Assemble ${flavor}") {
                             	sh "cd android-x10 && ./gradlew clean assemble${flavor} -PBUILD_NUMBER=${env.BUILD_NUMBER}"
                             }

                             //step([$class: 'JavadocArchiver', javadocDir: '**/javadoc', keepAll: false])
                         }

                        if (IS_UNIT_TEST_STAGE_ENABLED) {
                             stage ("Unit Test ${flavor}") {
                             	sh "cd android-x10 && ./gradlew test${flavor}UnitTest -PBUILD_NUMBER=${env.BUILD_NUMBER}"
                             }

                            always {
                                junit allowEmptyResults: true, testResults: '**/build/test-results/**/*.xml'
                            }
                         }

                         if (IS_INSTRUMENTED_TEST_STAGE_ENABLED) {
                             stage ("Instrumented Test ${flavor}") {
                             	sh "cd android-x10 && ./gradlew connected${flavor}AndroidTest -PBUILD_NUMBER=${env.BUILD_NUMBER}"
                             }

                            always {
                                junit allowEmptyResults: true, testResults: '**/build/androidTests/**/*.xml'
                            }
                         }

                         if (IS_TEST_COVERAGE_STAGE_ENABLED) {
                             stage ("Code Coverage ${flavor}") {
                                sh "cd android-x10 && ./gradlew jacoco${flavor} -PBUILD_NUMBER=${env.BUILD_NUMBER}"
                                sh "cd android-x10 && ./gradlew jacocoTestReport${flavor} -PBUILD_NUMBER=${env.BUILD_NUMBER}" 
                             }

                            always {
                                step([$class: 'JacocoPublisher', classPattern: '**/classes', execPattern: '**/**.exec, **/**.ec', sourcePattern: '**/src/main/java'])
                            }

                             stage ("Test Coverage ${flavor}") {
                             	sh "cd android-x10 && ./gradlew create${flavor}CoverageReport -PBUILD_NUMBER=${env.BUILD_NUMBER}"
                             }
                         }

                         fingerprint ''
                     }
                 }
            }
        }
    }

    def jobs = [:]
    jobs.failFast = true

    for (int i = 0; i < MATRIX_ANDROID_OS_SUPPORTED.size(); i++) {
         def index = i 
         def androidOSVersion = MATRIX_ANDROID_OS_SUPPORTED[index]
         def jobCommonName = "Android ${androidOSVersion}"
         def jobName = "${jobCommonName}_${index}"
         def job = jobMaker(androidOSVersion)
         jobs[jobName] = job
         assert job != null
    }

    parallel jobs
    
    if(IS_BRANCH_PRODUCTION) {
         if (IS_DEPLOY_STAGE_ENABLED) {
             stage ("Deploy ${flavor}") {
                 echo "Nothing to deploy for now"
             }
         }
    }
}

