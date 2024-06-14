FROM openjdk:18
COPY ./target/user-service-*.jar /usr/local/bin/user-service.jar
RUN chmod +x /usr/local/bin/user-service.jar
ENTRYPOINT ["java", "-jar", "/usr/local/bin/user-service.jar"]
