# Etapa de construcción
FROM openjdk:21-alpine AS build

WORKDIR /app

# Instalar Maven usando apk (el gestor de paquetes de Alpine)
RUN apk update && apk add --no-cache maven

# Copiar todo el proyecto dentro del contenedor
COPY . .

# Ejecutar Maven para compilar y empaquetar el proyecto
RUN mvn clean package -DskipTests

# Etapa de ejecución
FROM amazoncorretto:21-alpine

# Copiar el archivo JAR generado desde la etapa de construcción
COPY --from=build /app/target/Rider-Essentials-Server-0.0.1-SNAPSHOT.jar app.jar

# Definir el comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]