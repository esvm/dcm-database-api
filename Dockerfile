FROM openjdk:8-jdk-alpine

ENV APP_HOME="/app"

RUN mkdir $APP_HOME
RUN apk add --update --no-cache tomcat-native
COPY database-api-1.0-boot.jar $APP_HOME/app.jar

WORKDIR $APP_HOME

EXPOSE 50051

CMD java -XX:+UnlockExperimentalVMOptions \
  -XX:+UseCGroupMemoryLimitForHeap \
  -XX:+UseG1GC \
  $JAVA_OPTS \
  -jar app.jar

