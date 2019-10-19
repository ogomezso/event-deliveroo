FROM hirokimatsumoto/alpine-openjdk-11

VOLUME /tmp


COPY target/*.jar app.jar
RUN touch /app.jar

ENV JAVA_OPTS="-Xmx512m -Xms256m"

ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -jar /app.jar" ]