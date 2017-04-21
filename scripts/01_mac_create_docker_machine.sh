# Create a Docker Machine to run Dockerized OS (host with Docker Engine on it)
# Docker Machine run on xhyve. xhyve is a hypervisor running on OSX. 
# Documentation: https://github.com/zchee/docker-machine-driver-xhyve
DOCKER_MACHINE_NAME=`cat DOCKER_MACHINE_NAME`
docker-machine create "$DOCKER_MACHINE_NAME" --driver xhyve --xhyve-experimental-nfs-share # Unsupported --xhyve-qcow2 true --xhyve-memory-size 512
