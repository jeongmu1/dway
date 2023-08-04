FROM gradle:8.1.1-jdk17 AS builder
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
ENV JAVA_OPTS="-Xmx1024m -XX:MaxPermSize=512m"
RUN ./gradlew bootJar --no-daemon

FROM amazoncorretto:17
WORKDIR /app
COPY build/libs/*.jar app.jar
CMD ["java", "-jar", "app.jar"]