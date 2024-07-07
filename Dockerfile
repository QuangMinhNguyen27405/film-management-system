# Use an official OpenJDK runtime as a parent image
FROM openjdk:17

# Copy the JAR file from the host to the working directory inside the container
COPY target/film-rental-system.jar film-rental-system.jar

# Expose the port your application will run on
EXPOSE 8080

# Command to run the JAR file
ENTRYPOINT ["java", "-jar", "film-rental-system.jar"]