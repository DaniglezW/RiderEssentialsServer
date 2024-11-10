# Usa una imagen base de Maven para compilar la aplicación
FROM amazoncorretto:22-alpine-jdk AS build
# Copia el archivo JAR generado desde el contenedor de compilación
COPY /target/RiderEssentialsServer-0.0.1-SNAPSHOT.jar app.jar
# Define el comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]