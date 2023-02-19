FROM amazoncorretto:18-alpine
COPY /target/gateway*.jar gateway.jar
EXPOSE 9191

CMD [ "java", "-jar", "gateway.jar"]