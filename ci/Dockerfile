# Image with:
# - Oracle Java 8
# - Android SDK 
# - Gradle
# - Jenkins
# - Volume with source code repository
FROM ubuntu:16.04

USER root

#
# General dependencies
#
RUN DEBIAN_FRONTEND=noninteractive apt-get update && \
    DEBIAN_FRONTEND=noninteractive apt-get -y install apt-utils apt-transport-https && \
    DEBIAN_FRONTEND=noninteractive apt-get -y upgrade && \
    DEBIAN_FRONTEND=noninteractive apt-get -y install lsb-release && \
    DEBIAN_FRONTEND=noninteractive apt-get -y install software-properties-common && \
    DEBIAN_FRONTEND=noninteractive apt-get -y install curl

#
# Oracle Java 8
#
RUN echo oracle-java8-installer shared/accepted-oracle-license-v1-1 select true | debconf-set-selections && \
    DEBIAN_FRONTEND=noninteractive apt-add-repository ppa:webupd8team/java && \
    DEBIAN_FRONTEND=noninteractive apt-get update && \
    DEBIAN_FRONTEND=noninteractive apt-get install -y oracle-java8-installer && \
    rm -rf /var/cache/oracle-jdk8-installer && \
    DEBIAN_FRONTEND=noninteractive apt-get install -y oracle-java8-set-default && \
    DEBIAN_FRONTEND=noninteractive apt-get install -y git openssh-server

#
# Gradle
#
ARG GRADLE_VERSION=3.5
RUN DEBIAN_FRONTEND=noninteractive apt-get install -y unzip
RUN cd /opt && \
    curl -L -O https://services.gradle.org/distributions/gradle-$GRADLE_VERSION-all.zip && \
    unzip /opt/gradle-$GRADLE_VERSION-all.zip -d /opt/gradle && \
    chmod -R 777 /opt/gradle
ENV GRADLE_HOME /opt/gradle/gradle-$GRADLE_VERSION
ENV PATH $GRADLE_HOME/bin:$PATH

#
# Android SDK
#
ENV ANDROID_SDK_VERSION 25.2.3
#ENV ANDROID_SDK_VERSION 24.4.1
ENV ANDROID_SDK_ZIP tools_r$ANDROID_SDK_VERSION-linux.zip
#ENV ANDROID_SDK_ZIP android-sdk_r$ANDROID_SDK_VERSION-linux.tgz
ENV ANDROID_SDK_ZIP_URL https://dl.google.com/android/repository/$ANDROID_SDK_ZIP
#ENV ANDROID_SDK_ZIP_URL https://dl.google.com/android/$ANDROID_SDK_ZIP
ENV ANDROID_HOME /usr/local/android-sdk-linux
RUN cd /usr/local/ && \
    curl -L -O $ANDROID_SDK_ZIP_URL && \
    unzip $ANDROID_SDK_ZIP && \
    mkdir -pv $ANDROID_HOME && \
    mv -v tools $ANDROID_HOME/
#RUN cd /usr/local/ && curl -L -O $ANDROID_SDK_ZIP_URL && tar xf $ANDROID_SDK_ZIP

ENV ANDROID_SDK $ANDROID_HOME
ENV ANDROID_SDK_TOOLS_COMPONENTS "tools" "platform-tools"
ENV ANDROID_SDK_API_COMPONENTS "platforms;android-23"
ENV ANDROID_SDK_BUILD_TOOLS_COMPONENTS "build-tools;25.0.2"
ENV ANDROID_SDK_EMULATOR_IMAGES_COMPONENTS "system-images;android-23;google_apis;x86"
ENV GOOGLE_SDK_COMPONENTS "extras;android;m2repository" "extras;google;m2repository"
ENV ANDROID_SDK_COMPONENTS "$ANDROID_SDK_TOOLS_COMPONENTS $ANDROID_SDK_API_COMPONENTS $ANDROID_SDK_BUILD_TOOLS_COMPONENTS $ANDROID_SDK_EMULATOR_IMAGES_COMPONENTS $GOOGLE_SDK_COMPONENTS"
ENV PATH $ANDROID_HOME/tools:$ANDROID_HOME/platform-tools:$PATH

RUN echo y | $ANDROID_HOME/tools/bin/sdkmanager --verbose --list --sdk_root="$ANDROID_HOME"
RUN (while sleep 3; do echo "y"; done) | $ANDROID_HOME/tools/bin/sdkmanager ${ANDROID_SDK_COMPONENTS} --verbose --sdk_root="$ANDROID_HOME" && \
    chmod -R 777 $ANDROID_HOME

# 32-bit compatibility for 64-bit environments
RUN dpkg --add-architecture i386
RUN apt-get -y update
RUN DEBIAN_FRONTEND=noninteractive apt-get install -y libc6:i386 libncurses5:i386 libstdc++6:i386 zlib1g:i386

#
# Jenkins (Slave mode)
# https://github.com/jenkinsci/docker-ssh-slave/blob/master/Dockerfile
#
# Create jenkins user
ARG JENKINS_USER=jenkins
ARG JENKINS_GROUP=jenkins
ARG JENKIN_USER_ID=1000
ARG JENKIN_GROUP_ID=1000
ENV JENKINS_AGENT_HOME=/home/${JENKINS_USER}
RUN groupadd -g ${JENKIN_GROUP_ID} ${JENKINS_GROUP} \
    && useradd -d "${JENKINS_AGENT_HOME}" -u "${JENKIN_USER_ID}" -g "${JENKIN_GROUP_ID}" -m -s /bin/bash "${JENKINS_USER}"
#RUN adduser --shell /bin/bash --home $JENKINS_AGENT_HOME $JENKINS_USER && echo "$JENKINS_USER:jenkins" | chpasswd
# Add public key for Jenkins login
# Requires to generate SSH private key with passphrase: ssh-keygen -t rsa -b 4096 
RUN mkdir $JENKINS_AGENT_HOME/.ssh
COPY jenkins@jenkins-slave.id_rsa.pub $JENKINS_AGENT_HOME/.ssh/jenkins@jenkins-slave.id_rsa.pub
RUN cat $JENKINS_AGENT_HOME/.ssh/jenkins@jenkins-slave.id_rsa.pub >> $JENKINS_AGENT_HOME/.ssh/authorized_keys
# Instruct Jenkins not to prompt for host key verification 
RUN echo "Host *\n\tStrictHostKeyChecking no\n" >> $JENKINS_AGENT_HOME/.ssh/config
# Set permissions
RUN chown -R jenkins $JENKINS_AGENT_HOME
RUN chgrp -R jenkins $JENKINS_AGENT_HOME
RUN chmod 600 $JENKINS_AGENT_HOME/.ssh/authorized_keys
RUN chmod 700 $JENKINS_AGENT_HOME/.ssh
# Add the jenkins user to sudoers
RUN echo "jenkins  ALL=(ALL)  ALL" >> etc/sudoers
# Add env variable to jenkins's environment
RUN touch "$JENKINS_AGENT_HOME/.bash_profile"
RUN echo "export ANDROID_HOME=$ANDROID_HOME" >> "$JENKINS_AGENT_HOME/.bash_profile"
RUN echo "export GRADLE_HOME=$GRADLE_HOME" >> "$JENKINS_AGENT_HOME/.bash_profile"
RUN echo "export PATH=$PATH" >> "$JENKINS_AGENT_HOME/.bash_profile"
RUN chown $JENKINS_USER:$JENKINS_GROUP "$JENKINS_AGENT_HOME/.bash_profile"

# Jenkins web
EXPOSE 8080
# Jenkins slave agent
EXPOSE 50000

# Clean apt cache
RUN apt-get clean && rm -rf /var/lib/apt/lists/* /tmp/* /var/tmp/*

# Setup sshd to run as jenkins user
# Set SSH Configuration to allow remote logins without /proc write access
# and also SSH login fix. Otherwise user is kicked off after login
# http://stackoverflow.com/questions/21391142/why-is-it-needed-to-set-pam-loginuid-to-its-optional-value-with-docker
RUN sed -ri 's/^session\s+required\s+pam_loginuid.so$/session optional pam_loginuid.so/' /etc/pam.d/sshd && \
    sed -i 's/#PermitRootLogin.*/PermitRootLogin no/' /etc/ssh/sshd_config && \
    sed -i 's/#RSAAuthentication.*/RSAAuthentication yes/' /etc/ssh/sshd_config && \
    sed -i 's/#PasswordAuthentication.*/PasswordAuthentication no/' /etc/ssh/sshd_config && \
    sed -i 's/#SyslogFacility.*/SyslogFacility AUTH/' /etc/ssh/sshd_config && \
    sed -i 's/#LogLevel.*/LogLevel INFO/' /etc/ssh/sshd_config && \
    mkdir /var/run/sshd

EXPOSE 22
ENV CI=true
CMD ["/usr/sbin/sshd", "-D"]
