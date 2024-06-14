FROM openjdk:18
COPY target/web-app-*.jar /usr/local/bin/web-app.jar
RUN chmod +x /usr/local/bin/web-app.jar
ENTRYPOINT ["java", "-jar", "/usr/local/bin/web-app.jar"]