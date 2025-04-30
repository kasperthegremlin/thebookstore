# Stage 1: Build with Maven
FROM maven:3.9.5-eclipse-temurin-17-alpine AS builder

# Set working directory in the container
WORKDIR /app

# Copy the Maven project files
COPY pom.xml .
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests

# Stage 2: Create a minimal runtime image
FROM eclipse-temurin:17-jdk-alpine

# Set working directory in the runtime image
WORKDIR /app

# Copy the built jar from the previous stage
COPY --from=builder /app/target/thebookstore-marshalling-1.0-SNAPSHOT.jar app.jar

# Run the jar file
ENTRYPOINT ["java", "-jar", "app.jar"]
