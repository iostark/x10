# Image with:
# - Oracle Java 8
# - Android SDK 
# - Gradle
# - Jenkins
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
ENV GRADLE_VERSION 3.5
RUN apt-get install -y unzip
ADD https://services.gradle.org/distributions/gradle-$GRADLE_VERSION-all.zip /opt/
RUN unzip /opt/gradle-$GRADLE_VERSION-all.zip -d /opt/gradle
ENV GRADLE_HOME /opt/gradle/gradle-$GRADLE_VERSION-all
ENV PATH $GRADLE_HOME/bin:$PATH

#
# Android SDK
#
RUN dpkg --add-architecture i386
RUN apt-get -y update
RUN apt-get -y install libc6-dev-i386 libc6-i386 zlib1g:i386

ENV ANDROID_SDK_VERSION 24.4.1
ENV ANDROID_SDK_ZIP android-sdk_r$ANDROID_SDK_VERSION-linux.tgz
ENV ANDROID_SDK_ZIP_URL https://dl.google.com/android/$ANDROID_SDK_ZIP
RUN cd /usr/local/ && curl -L -O $ANDROID_SDK_ZIP_URL && tar xf $ANDROID_SDK_ZIP

ENV ANDROID_HOME /usr/local/android-sdk-linux
ENV ANDROID_SDK /usr/local/android-sdk-linux
ENV ANDROID_SDK_TOOLS_COMPONENTS tools,platform-tools
ENV ANDROID_SDK_API_COMPONENTS android-23
ENV ANDROID_SDK_BUILD_TOOLS_COMPONENTS build-tools-23.0.2,build-tools-24.0.0
ENV ANDROID_SDK_EMULATOR_IMAGES_COMPONENTS sys-img-armeabi-v7a-android-23
ENV GOOGLE_SDK_COMPONENTS extra-android-m2repository,extra-google-m2repository
ENV ANDROID_SDK_COMPONENTS $ANDROID_SDK_TOOLS_COMPONENTS,$ANDROID_SDK_API_COMPONENTS,$ANDROID_SDK_BUILD_TOOLS_COMPONENTS,$ANDROID_SDK_EMULATOR_IMAGES_COMPONENTS,$GOOGLE_SDK_COMPONENTS
ENV PATH $ANDROID_HOME/tools:$ANDROID_HOME/platform-tools:$PATH

RUN echo y | /usr/local/android-sdk-linux/tools/android update sdk --filter tools --no-ui --force -a
RUN echo y | /usr/local/android-sdk-linux/tools/android update sdk --filter platform-tools --no-ui --force -a
RUN echo y | /usr/local/android-sdk-linux/tools/android update sdk --no-ui --all --filter "${ANDROID_SDK_COMPONENTS}" --force

# 32-bit compatibility for 64-bit environments
RUN dpkg --add-architecture i386
RUN DEBIAN_FRONTEND=noninteractive apt-get install -y libc6:i386 libncurses5:i386 libstdc++6:i386 zlib1g:i386

#
# Jenkins (Slave mode)
# https://github.com/dockerfile/java/blob/master/oracle-java8/Dockerfile
#
# Add public key for Jenkins login
# Requires to generate SSH private key with passphrase: ssh-keygen -t rsa -b 4096 
RUN mkdir /home/jenkins/.ssh
COPY jenkins@jenkins-slave.id_rsa.pub /home/jenkins/.ssh/jenkins@jenkins-slave.id_rsa.pub
RUN cat jenkins@jenkins-slave.id_rsa.pub >> /home/jenkins/.ssh/authorized_keys
# Instruct Jenkins not to prompt for host key verification 
RUN echo "Host *\n\tStrictHostKeyChecking no\n" >> /var/lib/jenkins/.ssh/config
# Set permissions
RUN chown -R jenkins /home/jenkins
RUN chgrp -R jenkins /home/jenkins
RUN chmod 600 /home/jenkins/.ssh/authorized_keys
RUN chmod 700 /home/jenkins/.ssh

# Add the jenkins user to sudoers
RUN echo "jenkins  ALL=(ALL)  ALL" >> etc/sudoers

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
RUN apt-get clean && rm -rf /var/lib/apt/lists/* /tmp/* /var/tmp/*

# Setup sshd to run as jenkins user
RUN mkdir /var/run/sshd && adduser --quiet jenkins && echo "jenkins:jenkins" | chpasswd
# Set SSH Configuration to allow remote logins without /proc write access
# http://stackoverflow.com/questions/21391142/why-is-it-needed-to-set-pam-loginuid-to-its-optional-value-with-docker
RUN sed -ri 's/^session\s+required\s+pam_loginuid.so$/session optional pam_loginuid.so/' /etc/pam.d/sshd


EXPOSE 22
ENV CI=true
CMD ["/usr/sbin/sshd", "-D"]
