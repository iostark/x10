source common_set_docker_environment.sh

CONTAINER_IMAGE_NAME=`cat CONTAINER_IMAGE_NAME`

# Build docker image 
docker build --force-rm --rm -t $CONTAINER_IMAGE_NAME ../ # Path to ../Dockerfile
