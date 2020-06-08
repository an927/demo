FROM openjdk:8-jdk-alpine
VOLUME /tmp
EXPOSE 8080
ADD ./target/demo-0.0.1-SNAPSHOT.war demo-0.0.1.war
ENTRYPOINT ["java","-jar","/demo-0.0.1.war"]