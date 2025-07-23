# ---- Stage 1: Build ----
FROM eclipse-temurin:21-jdk-alpine as builder

WORKDIR /app

# Copy the source code
COPY . .

# Build the project
RUN ./mvnw clean package -DskipTests

# ---- Stage 2: Run ----
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Copy the jar from the builder stage
COPY --from=builder /app/target/*.jar app.jar

# Expose port
EXPOSE 8080

# Run the jar
ENTRYPOINT ["java", "-jar", "app.jar"]
