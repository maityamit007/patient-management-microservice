FROM eclipse-temurin:24-jdk AS builder

WORKDIR /app

RUN apt-get update \
    && apt-get install -y maven \
    && rm -rf /var/lib/apt/lists/*

COPY pom.xml .
RUN mvn dependency:go-offline -B

COPY src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:24-jre AS runner

WORKDIR /app
COPY --from=builder /app/target/patient-service-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 4000
ENTRYPOINT ["java", "-jar", "app.jar"]
