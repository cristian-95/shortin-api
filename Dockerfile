FROM eclipse-temurin:21-jre-alpine
LABEL author="cristian"
VOLUME /tmp
COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]