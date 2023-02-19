FROM openjdk:18-alpine
COPY /target/*api-service-application*.jar api-service.jar
EXPOSE 8080
CMD ["java", "-jar", "api-service.jar"]