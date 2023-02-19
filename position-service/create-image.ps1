# Compiliamo con Maven
#FARE IL BUILD CON MAVEN NON QUI MA DAL MENU DI INTELLIJ
#mvn clean install
Write Host "***** FARE IL BUILD CON MAVEN NON QUI MA DAL MENU DI INTELLIJ *****"
# Set variables
$IMAGE_NAME="position-service-docker"
$USERNAME="ssilvestro"
$REPO_NAME="position-service-docker"
$TAG="1.0.1"

# Build the Docker image
docker build -t $IMAGE_NAME .

# Tag the Docker image
docker tag ${IMAGE_NAME} ${USERNAME}/${REPO_NAME}:${TAG}

# Push the Docker image to Docker Hub
docker push ${USERNAME}/${REPO_NAME}:${TAG}
