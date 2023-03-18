FROM maven:3.8.3-openjdk-17

# Copy the current directory contents into the container at /backend
COPY . ./backend

# Set the work directory to /backend
WORKDIR /backend

# Download project dependencies and store them in the local repository cache
RUN mvn dependency:go-offline

# Start the backend server
CMD mvn spring-boot:run