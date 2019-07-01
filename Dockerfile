FROM openjdk:8-jdk-alpine

ENV APP_HOME="/app"

RUN mkdir $APP_HOME
RUN apk add --update --no-cache tomcat-native
COPY . $APP_HOME

WORKDIR $APP_HOME

RUN ./gradlew --build-cache bootJar

EXPOSE 8080

CMD java -XX:+UnlockExperimentalVMOptions \
  -XX:+UseCGroupMemoryLimitForHeap \
  -XX:+UseG1GC \
  $JAVA_OPTS \
  -jar app.jar

