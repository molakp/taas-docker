# Compiliamo con Maven
mvn clean install -DskipTests

# Set variables
$IMAGE_NAME="api-gateway-docker"
$USERNAME="ssilvestro"
$REPO_NAME="api-gateway-docker"
$TAG="1.0.0"

# Build the Docker image
docker build -t $IMAGE_NAME .

# Tag the Docker image
docker tag $IMAGE_NAME ${USERNAME}/${REPO_NAME}:${TAG}

# Push the Docker image to Docker Hub
docker push ${USERNAME}/${REPO_NAME}:${TAG}
