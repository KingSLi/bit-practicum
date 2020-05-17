FROM openjdk:8
RUN mkdir weather
COPY target/weather-1.0-SNAPSHOT.jar /weather
WORKDIR /weather
CMD ["java", "-jar", "weather-1.0-SNAPSHOT.jar"]