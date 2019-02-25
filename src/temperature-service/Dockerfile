# Use an official java runtime as a parent image
FROM openjdk:8-jre-alpine

# Add Maintainer Info
LABEL maintainer="<Murat Zorer> muratzorer35@gmail.com"

# Add a volume pointing to /tmp
VOLUME /tmp

# Set the working directory to /app
WORKDIR /app

# Copy the current directory contents into the container at /app
COPY target/temperature-service-*.jar app.jar

# 12-factor compatible arguments
ARG service_version
ENV SERVICE_VERSION ${service_version:-1.0.0}

# Make port 9080 available to the world outside this container
EXPOSE 9080

# Run app.py when the container launches
CMD ["java","-Djava.security.egd=file:/dev/./urandom","-jar","app.jar"]
