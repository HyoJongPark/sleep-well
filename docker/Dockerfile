FROM openjdk:17-jdk

WORKDIR /app

ARG JAR_FILE_PATH=./build/libs/*.jar

COPY ${JAR_FILE_PATH} /app/app.jar

ENV TZ=Asia/Seoul

CMD ["java", "-jar", "-Dspring.profiles.active=${USE_PROFILE}", "app.jar"]
