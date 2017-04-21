# Usage: source thisscript.sh

DOCKER_MACHINE_NAME=`cat DOCKER_MACHINE_NAME`

# Registers a few variables, which allow you to use the docker machine without typing its name each time.
eval $(docker-machine env $DOCKER_MACHINE_NAME)
