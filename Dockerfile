FROM maven:3.9.12-eclipse-temurin-21 AS builder

WORKDIR /app

COPY pom.xml .
COPY src ./src
COPY .mvn ./.mvn
COPY mvnw .

RUN chmod +x mvnw && ./mvnw clean package -DskipTests

FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=builder /app/target/vulnlab-tech-1.0.0.jar app.jar

EXPOSE 8080

ENV PORT=8080
ENV SPRING_DATASOURCE_URL=jdbc:mysql://localhost:3306/vulnlab_db
ENV SPRING_DATASOURCE_USERNAME=vulnlab
ENV SPRING_DATASOURCE_PASSWORD=vulnlab123

CMD ["java", "-jar", "app.jar"]
