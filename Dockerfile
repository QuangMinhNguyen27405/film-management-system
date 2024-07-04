# Use an official OpenJDK runtime as a parent image
FROM openjdk:11-jre-slim

# Set the working directory inside the container
WORKDIR /usr/src/app

# Copy the JAR file from the host to the working directory inside the container
COPY target/film-rental-system.jar film-rental-system.jar

# Expose the port your application will run on
EXPOSE 8081

# Command to run the JAR file
ENTRYPOINT ["java", "-jar", "film-rental-system.jar"]