FROM maven:3.8.3-openjdk-17 as builder

# Copy the pom.xml file and download project dependencies to cache them
WORKDIR /build
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the remaining source code
COPY src/ ./src/

# Build the application
RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-alpine3.14

# Copy the built JAR file from the builder stage
COPY --from=builder /build/target/unility-0.0.1-SNAPSHOT.jar /app/unility.jar

# Set the work directory to /app
WORKDIR /app

# Start the backend server
CMD java -jar /app/unility.jar