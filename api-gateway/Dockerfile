# Stage 1: Build with Maven + Java 21
FROM maven:3.9.6-eclipse-temurin-21 AS builder

WORKDIR /app

# Copy pom first to cache dependencies
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy source and build
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Runtime with Java 21
FROM eclipse-temurin:21-jre AS runner

WORKDIR /app

# Copy the built JAR from the builder stage
COPY --from=builder /app/target/api-gateway-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 4004

# Run it
ENTRYPOINT ["java", "-jar", "app.jar"]