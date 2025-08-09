# AI News Project

This project is built with Spring Boot and organized into multiple modules: domain, infra, starter, web, etc.

## Externalized Configuration

To avoid committing sensitive data like database credentials to the Git repository, the `ainews-starter` module uses the `config` directory under `ainews-starter` for external config files and an `env.sh` script to load environment variables.

1. Create or edit `application.properties` in `ainews-starter/config` (a sample file is provided).
2. Edit or create the `env.sh` script and set your actual database connection info:
   ```bash
   export DB_USERNAME="<your_username>"
   export DB_PASSWORD="<your_password>"
   export DB_HOST="<your_host>"
   export DB_PORT="<your_port>"
   export DB_NAME="<your_database>"
   ```
3. Before starting the application, run:
   ```bash
   source ainews-starter/config/env.sh
   ```
4. Then start the app using Maven or the JAR:
   ```bash
   mvn spring-boot:run -pl ainews-starter
   # or
   java -jar ainews-starter/target/ainews-starter-0.0.1-SNAPSHOT.jar
   ```

These steps will automatically inject the environment variables into `application.properties`, ensuring sensitive information is not committed to the repository.