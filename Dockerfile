FROM node:7.9.0

#
# Server service setup
#

# Run "server" service with user "user1"
RUN useradd --user-group --create-home \
            --shell /bin/false user1

# Install server service's NPM dependencies
USER root
COPY ./server/package.json /opt/x10/server/package.json
RUN chown -R user1:user1 /opt/x10/server
USER user1
RUN cd /opt/x10/server && npm install && npm cache clean

# Install server source code
USER root
COPY ./server/ /opt/x10/server/
RUN chown -R user1:user1 /opt/x10/server

EXPOSE 4000
EXPOSE 4001

#
# DEPRECATED
#

# src: http://bitjudo.com/blog/2014/03/13/building-efficient-dockerfiles-node-dot-js/

# Base image
# Official ubuntu docker image
#FROM ubuntu:16.04

# Install needed packages
# src: https://github.com/nodesource/distributions
#ARG DEBIAN_FRONTEND=noninteractive
#RUN apt-get update
#RUN apt-get -y install software-properties-common
#RUN apt-get -y install git build-essential
#RUN apt-get -y install build-essential
#RUN add-apt-repository -y ppa:chris-lea/node.js
#RUN apt-get update
#RUN apt-get -y install nodejs



# Force Docker not to use the cache when nodejs's package.json is updated
#ADD package.json /tmp/package.json
#RUN cd /tmp && npm install
#RUN mkdir -p /opt/x10/server && cp -a /tmp/node_modules /opt/x10/server/

# Set Working directory
#WORKDIR /opt/x10/server

# Copy the current directory contents into the container
#ADD ./server /opt/x10/server
#COPY . /opt/x10/server/

# Make port 10000 available to the world outside this container
#EXPOSE 10000


