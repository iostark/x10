# 
# Description: Install software required to build the environemt to run the server developed in this project
# Script executtion: run as non root 
# Src: https://pilsniak.com/how-to-install-docker-on-mac-os-using-brew/

# Install dockers and associated dependencies
brew install docker docker-compose docker-machine xhyve docker-machine-driver-xhyve

# docker-machine-driver-xhyve requires to execute commands as root
sudo chown root:wheel $(brew --prefix)/opt/docker-machine-driver-xhyve/bin/docker-machine-driver-xhyve
sudo chmod u+s $(brew --prefix)/opt/docker-machine-driver-xhyve/bin/docker-machine-driver-xhyve

# Show docker tools versions
docker --version; 
docker-compose --version; 
docker-machine --version
