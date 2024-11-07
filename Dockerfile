# Usa una imagen base de Maven para compilar la aplicación
FROM maven:3.9.2-openjdk-17 AS build

# Establece el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia los archivos de configuración de Maven (opcional, si usas settings.xml o similares)
# COPY settings.xml /root/.m2/

# Copia el archivo de pom.xml y resuelve las dependencias
COPY pom.xml .

# Descarga las dependencias, pero no construye la aplicación aún (útil para caché en Docker)
RUN mvn dependency:go-offline -B

# Copia el resto del código de la aplicación al contenedor
COPY src /app/src

# Compila la aplicación y empaqueta como un JAR
RUN mvn package -DskipTests

# Usa una imagen base de OpenJDK para ejecutar la aplicación
FROM openjdk:17-jdk-slim

# Establece el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia el archivo JAR generado desde el contenedor de compilación
COPY --from=build /app/target/mi-app-0.0.1-SNAPSHOT.jar app.jar

# Expone el puerto en el que corre la aplicación Spring Boot
EXPOSE 8080

# Define el comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]