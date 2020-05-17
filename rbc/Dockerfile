FROM openjdk:8
RUN mkdir rbc
COPY target/rbc-1.0-SNAPSHOT.jar /rbc
WORKDIR /rbc
CMD ["java", "-jar", "rbc-1.0-SNAPSHOT.jar"]