./gradlew shadowJar
java -jar build/libs/app.jar server build/libs/config.yml
