FROM eclipse-temurin:17.0.4.1_1-jre as builder
WORKDIR extracted
ADD target/*.jar app.jar
#? Java supports extracting different layers from the jar files
#? When we look at the target folder, we see our application
#? is divided into different layers. Below command,
#? extract the layers given in the second part and store them inside the extracted folder
RUN java -Djarmode=layertools -jar app.jar extract

FROM eclipse-temurin:17.0.4.1_1-jre
WORKDIR application
#? These are the different layers that our spring boot jar will provide.
#? They are extracted in the first phase and now we copy them into our application folder
COPY --from=builder extracted/dependencies/ ./
COPY --from=builder extracted/spring-boot-loader/ ./
COPY --from=builder extracted/snapshot-dependencies/ ./
COPY --from=builder extracted/application/ ./
EXPOSE 8080
ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]