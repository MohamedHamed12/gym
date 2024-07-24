# Use the official Maven image to build the application with Java 21
FROM maven:3.9.1-openjdk-21 AS build

# Set the working directory in the container
WORKDIR /app

# Copy the pom.xml and source code into the container
COPY pom.xml .
COPY src ./src

# Build the application and package it into a JAR file
RUN mvn clean package -DskipTests

# Use the official OpenJDK 21 image to run the application
FROM openjdk:21-jre-slim

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file from the build stage to the working directory
COPY --from=build /app/target/*.jar app.jar

# Specify the command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
