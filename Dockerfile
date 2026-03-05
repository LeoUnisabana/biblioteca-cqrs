# Dockerfile multi-stage para biblioteca-cqrs
# Etapa 1: Build con Maven (usa imagen oficial Maven con JDK 17)
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
# Descarga dependencias y compila el proyecto
RUN mvn clean package -DskipTests=true

# Etapa 2: Imagen final liviana (runtime)
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
# Copia el jar generado desde la etapa de build
COPY --from=build /app/target/*.jar app.jar

# Crea un usuario no root para ejecutar la app
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

# Expone el puerto de la aplicación
EXPOSE 8089

# Variables de entorno para configuración de base de datos
ENV SPRING_DATASOURCE_URL=""
ENV SPRING_DATASOURCE_USERNAME=""
ENV SPRING_DATASOURCE_PASSWORD=""

# Comando de inicio
ENTRYPOINT ["java","-jar","app.jar"]

# Notas:
# - La imagen final solo contiene el jar y el JRE, no incluye Maven ni fuentes.
# - Usa usuario no root para mayor seguridad.
# - Variables de entorno pueden ser sobreescritas al correr el contenedor.
