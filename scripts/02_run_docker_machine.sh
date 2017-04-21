source common_set_docker_environment.sh

# List available Docker Machines
docker-machine ls

# Start the docker machine!
docker-machine start $DOCKER_MACHINE_NAME
