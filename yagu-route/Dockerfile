FROM eclipse-temurin:21-alpine
COPY build/libs/springboot-yaguroute.jar yaguroute.jar

WORKDIR /
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "./yaguroute.jar"]
