# Jenkins slave build node with nodejs installed
# Based from: https://github.com/pcvolkmer/docker-jenkins-slave-nodejs/blob/master/7.8/Dockerfile

FROM node:7.9

# Add backports
RUN echo "deb http://http.debian.net/debian jessie-backports main" >> /etc/apt/sources.list

# Upgrade and Install packages
RUN apt-get update && \ 
    apt-get -y upgrade && \
    apt-get install -y git openssh-server && \
    apt-get -t jessie-backports install -y openjdk-8-jdk

# Prepare container for ssh
RUN mkdir /var/run/sshd && adduser --quiet jenkins && echo "jenkins:jenkins" | chpasswd

ENV CI=true
EXPOSE 22

CMD ["/usr/sbin/sshd", "-D"]
