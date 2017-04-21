source common_set_docker_environment.sh

CONTAINER_IMAGE_NAME=`cat CONTAINER_IMAGE_NAME`
MAKER_CONTAINER_IMAGE_SCRIPT="`pwd`/03_make_docker_image.sh"
PROJECT_NAME="x10"

# Remove all dangling images 
# docker image prune -f dangling=true
docker images -f dangling=true -q
docker rmi $(docker images -f dangling=true -q)

# Remove all images
# docker rmi $(docker images -a -q)

# Remove dangling volumes
docker volume ls -f dangling=true
docker volume rm $(docker volume ls -f dangling=true -q)

# Build image from Dockerfile
# bash -x 03_make_docker_image.sh
docker image ls --all

# Show existing docker images
docker images $CONTAINER_IMAGE_NAME

# Remove all exited containers (+associated volumes)
# docker ps -a -f status=exited
# docker rm -v $(docker ps -a -f status=exited -q)

cd ..
    # Validate docker-compose.yml
    docker-compose --project-name $PROJECT_NAME config

    # Remove stopped service containers + anonymous volumes attached to it
    # docker-compose --project-name $PROJECT_NAME rm -f -v

    # Build services image
    # docker-compose --project-name $PROJECT_NAME build --force-rm --no-cache 

    # Build + re/create + starts + attaches containers for a service 
    docker-compose --project-name $PROJECT_NAME up -d 
    #docker-compose --project-name $PROJECT_NAME --verbose up #-d

    # List containers
    docker-compose --project-name $PROJECT_NAME ps

    # List all containers' info
    # docker ps -a --no-trunc -q | xargs docker inspect

    # List all containers' mounts
    docker ps -a --no-trunc -q | xargs docker inspect -f "{{json .Mounts }}" | python -m json.tool 

    # List all containers' volumes
    docker ps -a --no-trunc -q | xargs docker inspect -f "{{json .Config.Volumes }}" | python -m json.tool 

    # List container ip
    docker ps -a --no-trunc -q | xargs docker inspect -f '{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}'

    # View logs of all services
    # use <ctrl-c> to stop viewing
    docker-compose --project-name $PROJECT_NAME logs --follow 

    # take down all containers
    # docker-compose --project-name $PROJECT_NAME down
cd -
