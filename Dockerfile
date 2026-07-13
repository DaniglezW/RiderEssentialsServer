# Etapa de construcción: compila el JAR desde el código fuente
FROM maven:3.9-eclipse-temurin-17-alpine AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Etapa de ejecución
FROM amazoncorretto:17-alpine-jdk
WORKDIR /app
COPY --from=build /app/target/Rider-Essentials-Server-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
