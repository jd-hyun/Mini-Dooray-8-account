FROM eclipse-temurin:21
RUN mkdir /opt/app
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} /opt/app/app.jar
EXPOSE 8081
CMD ["java", "-jar", "/opt/app/app.jar", "--spring.profiles.active=prod"]
