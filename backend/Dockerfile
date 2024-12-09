# Build stage
FROM maven:3.8.7-jdk-17 AS builder
ARG VERSION=0.0.1-SNAPSHOT

# Set the working directory
WORKDIR /build/

# Copy Maven configuration and source files
COPY pom.xml /build/
COPY src /build/src/

# Build the project
RUN mvn clean package -DskipTests

# Runtime stage
FROM jdk:17-slim
WORKDIR /app/

# Copy the built JAR file from the builder stage
COPY --from=builder /build/target/backend-${VERSION}.jar app.jar

# Expose the application port
EXPOSE 8081

# Run the application
CMD ["java", "-jar", "/app/app.jar"]
