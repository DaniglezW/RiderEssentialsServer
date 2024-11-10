FROM amazoncorretto:21-alpine AS build
COPY /target/Rider-Essentials-Server-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]