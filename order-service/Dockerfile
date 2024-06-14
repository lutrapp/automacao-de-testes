FROM openjdk:18
COPY target/order-service-*.jar /usr/local/bin/order-service.jar
RUN chmod +x /usr/local/bin/order-service.jar
ENTRYPOINT ["java", "-jar", "/usr/local/bin/order-service.jar"]
