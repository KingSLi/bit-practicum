FROM openjdk:8
RUN mkdir rate
COPY target/rate-1.0-SNAPSHOT.jar /rate
WORKDIR /rate
CMD ["java", "-jar", "rate-1.0-SNAPSHOT.jar"]