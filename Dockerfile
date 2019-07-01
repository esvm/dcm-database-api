FROM openjdk:8-jdk-alpine

# Install tools
RUN apt update && apt install unzip

# Install protoc
RUN mkdir /tmp/protobuf
WORKDIR /tmp/protobuf

ENV URL https://github.com/protocolbuffers/protobuf/releases/download/v3.6.1/protoc-3.6.1-linux-x86_64.zip
RUN curl -L -o protobuf.zip $URL
RUN unzip protobuf.zip
RUN mv bin/protoc /usr/local/bin
RUN mv include/google /usr/local/include

WORKDIR /
RUN rm -rf /tmp/protobuf

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

