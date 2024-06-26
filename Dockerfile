FROM eclipse-temurin:17-jre as builder
WORKDIR app
ARG JAR_FILE=./build/libs/*.jar
COPY ${JAR_FILE} app.jar
RUN java -Djarmode=layertools -jar app.jar extract

FROM eclipse-temurin:17-jre
WORKDIR app
COPY --from=builder app/dependencies/ ./
COPY --from=builder app/spring-boot-loader/ ./
COPY --from=builder app/snapshot-dependencies/ ./
COPY --from=builder app/application/ ./
ENTRYPOINT ["java", "-Dspring.profiles.active=${USE_PROFILE}", "org.springframework.boot.loader.launch.JarLauncher"]

