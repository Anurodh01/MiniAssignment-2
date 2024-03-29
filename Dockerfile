FROM openjdk:17
EXPOSE 8060
ADD target/miniassignment-2.jar miniassignment-2.jar
ENTRYPOINT ["java","-jar","/miniassignment-2.jar"]
