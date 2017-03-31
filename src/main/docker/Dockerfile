FROM isuper/java-oracle:jre_latest

# Create WORKDIR
ADD notiflowcate-api.jar /opt/notiflowcate-api/

# Start SpringBoot Jetty Server
EXPOSE 8080
WORKDIR /opt/notiflowcate-api/
CMD ["java", "-Xms512m", "-Xmx1g", "-Dspring.profiles.active=development", "-jar", "notiflowcate-api.jar"]