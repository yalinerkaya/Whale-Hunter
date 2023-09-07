FROM adoptopenjdk:11-jre-hotspot
WORKDIR /app
COPY build/libs/*.war /app/app.war
CMD ["java", "-jar", "app.war"]
