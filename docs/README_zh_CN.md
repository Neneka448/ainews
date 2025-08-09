# AI News 项目

本项目基于 Spring Boot 构建，包含多个模块：domain、infra、starter、web 等。

## 外部化配置

为了避免将数据库等敏感信息提交到 Git 仓库，`ainews-starter` 模块在 `ainews-starter/config` 目录下存放外部配置文件，并提供 `env.sh` 脚本加载环境变量。

1. 在 `ainews-starter/config` 目录下创建或修改 `application.properties`（示例已提供）。
2. 编辑或创建 `env.sh` 脚本，填写真实的数据库连接信息：
   ```bash
   export DB_USERNAME="<your_username>"
   export DB_PASSWORD="<your_password>"
   export DB_HOST="<your_host>"
   export DB_PORT="<your_port>"
   export DB_NAME="<your_database>"
   ```
3. 启动应用前，执行：
   ```bash
   source ainews-starter/config/env.sh
   ```
4. 然后使用 Maven 或 Jar 启动：
   ```bash
   mvn spring-boot:run -pl ainews-starter
   # 或
   java -jar ainews-starter/target/ainews-starter-0.0.1-SNAPSHOT.jar
   ```

以上步骤会自动将环境变量注入到 `application.properties` 中，确保敏感信息不会被提交到版本库。
