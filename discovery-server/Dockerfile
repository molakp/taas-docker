FROM amazoncorretto:11-alpine3.14
COPY /target/discovery-server*.jar discovery-server.jar
EXPOSE 8761
CMD ["java", "-jar", "discovery-server.jar"]