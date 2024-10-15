# Step 1: Use an official OpenJDK image as a base image
FROM openjdk:22-jdk-slim

# Step 2: Set the working directory inside the container
WORKDIR /app

# Step 3: Copy the JAR file from the target directory into the container
COPY target/yatzysimulator-0.0.1-SNAPSHOT.jar app.jar

# Step 4: Expose the port the Spring Boot app runs on
EXPOSE 8080

# Step 5: Set the command to run your application when the container starts
ENTRYPOINT ["java", "-jar", "app.jar"]