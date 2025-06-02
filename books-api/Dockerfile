FROM openjdk:21-jdk-slim

WORKDIR /app

COPY pom.xml .

# Descargar todas las dependencias
RUN ./mvnw dependency:go-offline -B

COPY src ./src

RUN ./mvnw clean package -DskipTests

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "target/books-api.jar"]