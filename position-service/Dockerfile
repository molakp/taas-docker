FROM amazoncorretto:11-alpine
COPY /target/position-service*.jar position-service.jar
EXPOSE 8081

CMD [ "java", "-jar", "position-service.jar"]