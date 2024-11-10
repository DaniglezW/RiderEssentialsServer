# Etapa de construcción
FROM maven:3.9.9-openjdk-21 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Etapa de ejecución
FROM amazoncorretto:21-alpine
WORKDIR /app
COPY --from=build /app/target/Rider-Essentials-Server-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]