FROM openjdk:17
ARG JAR_FILE=build/libs/*.jar
ARG USE_PROFILE=prod
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-Dspring.profiles.active=${USE_PROFILE}", "-jar","/app.jar"]