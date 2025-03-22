FROM openjdk:17-oracle
COPY esg-0.0.1-SNAPSHOT.jar /home/susansusan1996/esg-0.0.1-SNAPSHOT.jar
WORKDIR /home/susansusan1996
RUN sh -c 'touch esg-0.0.1-SNAPSHOT.jar'
ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-jar", "esg-0.0.1-SNAPSHOT.jar"]
