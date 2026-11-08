# Use the Eclipse Temurin image with JDK 21.
# The JDK includes the javac compiler, so this image is used to build the application.
# "AS build" gives this build stage the name "build".
FROM eclipse-temurin:21-jdk AS build

# Set the working directory inside the container.
# The following commands will use /app as their working directory.
WORKDIR /app

# Copy the local src directory into the /app/src directory inside the container.
COPY src/ ./src/

# Create the out directory for compiled .class files.
RUN mkdir out

# Find all Java source files in src and compile them.
# The compiled files are placed in the out directory.
RUN javac -d out $(find src -name "*.java")

# Create the clock.jar file.
# --main-class specifies the main class of the application.
# -C out . means: take the contents of the out directory and add them to the JAR file.
RUN jar --create --file clock.jar --main-class com.aydinov.sergey.Main -C out .

# Start the second build stage.
# Use a smaller Eclipse Temurin image with JRE 21.
# The JRE can run Java applications but does not include the javac compiler.
# "AS runtime" gives this stage the name "runtime".
FROM eclipse-temurin:21-jre AS runtime

# Set the working directory inside the final container.
WORKDIR /app

# Copy the compiled JAR file from the "build" stage
# into the current "runtime" stage.
COPY --from=build /app/clock.jar ./clock.jar

# Define the command that will be executed automatically
# when the container starts.
#
# java starts the Java Virtual Machine.
# --module-path /app/clock.jar specifies the module path.
# --module docker_tutorial/com.aydinov.sergey.Main
# starts the docker_tutorial module and its main class.
ENTRYPOINT ["java", "--module-path", "/app/clock.jar", "--module", "docker_tutorial/com.aydinov.sergey.Main"]