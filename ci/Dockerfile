# Image with:
# - Oracle Java 8
# - Jenkins slave
# - Nodejs
# - Elm
# - Haskell

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
# NodeJS dependencies
#
RUN DEBIAN_FRONTEND=noninteractive apt-get install -y build-essential

# NodeJS
#RUN curl -sL https://deb.nodesource.com/setup_7.x |  -E bash - && \
#     apt-get install -y nodejs && \
#     ln -s /usr/local/bin/node /usr/local/bin/nodejs

RUN export NODEJS_VERSION="node_7.x"; \
    export DISTRO="$(lsb_release -s -c)"; \
    curl --silent https://deb.nodesource.com/gpgkey/nodesource.gpg.key | apt-key add - && \
    echo "deb https://deb.nodesource.com/$NODEJS_VERSION $DISTRO main" |  tee /etc/apt/sources.list.d/nodesource.list && \
    echo "deb-src https://deb.nodesource.com/$NODEJS_VERSION $DISTRO main" |  tee -a /etc/apt/sources.list.d/nodesource.list && \
    apt-get update && apt-get install nodejs && \
    ln -s /usr/local/bin/node /usr/local/bin/nodejs

# Haskell
# https://www.haskell.org/downloads/linux
RUN export HASKELL_GHC_VERSION="8.2.1"; \
    export HASKELL_CABAL_VERSION="2.0";  \
    DEBIAN_FRONTEND=noninteractive add-apt-repository -y ppa:hvr/ghc && \
    DEBIAN_FRONTEND=noninteractive apt-get update && \
    DEBIAN_FRONTEND=noninteractive apt-get install -y ghc-$HASKELL_GHC_VERSION cabal-install-$HASKELL_CABAL_VERSION

#cat >> ~/.bashrc <<EOF
#export PATH="\$HOME/.cabal/bin:/opt/cabal/$HASKELL_CABAL_VERSION/bin:/opt/ghc/$HASKELL_GHC_VERSION/bin:\$PATH"
#EOF
#export PATH=~/.cabal/bin:/opt/cabal/$HASKELL_CABAL_VERSION/bin:/opt/ghc/$HASKELL_GHC_VERSION/bin:$PATH


# Elm
# https://alphydan.svbtle.com/elm-lang-on-ubuntu-14-04
# TODO


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
# Jenkins (Slave mode) Setup
# https://github.com/dockerfile/java/blob/master/oracle-java8/Dockerfile
#
# Create jenkins user
ARG JENKINS_USER=jenkins
RUN adduser --shell /bin/bash --home /home/$JENKINS_USER $JENKINS_USER && echo "$JENKINS_USER:jenkins" | chpasswd
# Add public key for Jenkins login
# Required to generate SSH private key with passphrase: ssh-keygen -t rsa -b 4096 
RUN mkdir /home/$JENKINS_USER/.ssh
COPY jenkins@jenkins-slave.id_rsa.pub /home/$JENKINS_USER/.ssh/jenkins@jenkins-slave.id_rsa.pub
RUN cat /home/$JENKINS_USER/.ssh/jenkins@jenkins-slave.id_rsa.pub >> /home/$JENKINS_USER/.ssh/authorized_keys
# Instruct Jenkins not to prompt for host key verification 
RUN echo "Host *\n\tStrictHostKeyChecking no\n" >> /home/$JENKINS_USER/.ssh/config
# Set permissions
RUN chown -R jenkins /home/$JENKINS_USER
RUN chgrp -R jenkins /home/$JENKINS_USER
RUN chmod 600 /home/$JENKINS_USER/.ssh/authorized_keys
RUN chmod 700 /home/$JENKINS_USER/.ssh
# Add the jenkins user to sudoers
RUN echo "jenkins  ALL=(ALL)  ALL" >> etc/sudoers
# Jenkins web
EXPOSE 8080
# Jenkins slave agent
EXPOSE 50000


# Clean apt chance
RUN apt-get clean && rm -rf /var/lib/apt/lists/* /tmp/* /var/tmp/*

# Setup sshd to run as jenkins user
RUN mkdir /var/run/sshd
# Set SSH Configuration to allow remote logins without /proc write access
# http://stackoverflow.com/questions/21391142/why-is-it-needed-to-set-pam-loginuid-to-its-optional-value-with-docker
RUN sed -ri 's/^session\s+required\s+pam_loginuid.so$/session optional pam_loginuid.so/' /etc/pam.d/sshd

EXPOSE 22
ENV CI=true
CMD ["/usr/sbin/sshd", "-D"]

# Copy source code repository into docker image 
COPY . /tmp/workspace/
