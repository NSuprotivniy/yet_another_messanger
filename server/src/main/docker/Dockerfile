FROM openjdk:8-jre
WORKDIR /messanger
VOLUME ["/messanger"]
COPY maven/messanger-jar-with-dependencies.jar messanger.jar
CMD ["java", "-jar", "messanger.jar"]