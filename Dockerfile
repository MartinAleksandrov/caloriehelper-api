# ============================================
# Stage 1: Build the application
# ============================================
FROM eclipse-temurin:21-jdk AS builder

WORKDIR /app

# Copy Maven wrapper and pom first (for layer caching)
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Pre-download dependencies (cached unless pom.xml changes)
RUN chmod +x mvnw && ./mvnw dependency:go-offline -B

# Copy source code
COPY src src

# Build the JAR (skip tests for faster builds)
RUN ./mvnw clean package -DskipTests -B

# ============================================
# Stage 2: Runtime image (smaller, no build tools)
# ============================================
FROM eclipse-temurin:21-jre

WORKDIR /app

# Copy the built JAR from the builder stage
COPY --from=builder /app/target/*.jar app.jar

# Expose port (documentation; doesn't actually open it)
EXPOSE 8080

# Run as non-root user for security
RUN useradd -m -u 1001 appuser
USER appuser

# Start the app
ENTRYPOINT ["java", "-jar", "/app/app.jar"]