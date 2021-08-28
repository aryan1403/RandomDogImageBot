FROM maven:3.6.3-jdk-8

COPY . .

RUN mvn clean package

# set the startup command to execute the jar
CMD ["java", "-jar", "target/rdogimagebot-1.0-jar-with-dependencies.jar"]