FROM openjdk:17
EXPOSE 8080
ADD target/game-svc.jar game-svc.jar
ENTRYPOINT ["java","-jar","/game-svc.jar"]
