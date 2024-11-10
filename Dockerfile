FROM maven:3.9.9-openjdk-21 AS build

WORKDIR /app

# Copiar todo el proyecto dentro del contenedor
COPY . .

# Ejecutar Maven para compilar y empaquetar el proyecto
RUN mvn clean package -DskipTests

# Usar una imagen ligera de Amazon Corretto para ejecutar el JAR
FROM amazoncorretto:21-alpine

# Copiar el archivo .jar generado por Maven desde la etapa de construcción
COPY --from=build /app/target/Rider-Essentials-Server-0.0.1-SNAPSHOT.jar app.jar

# Definir el comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]