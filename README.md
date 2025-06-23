# Simplebank in Java and using TestNG

Using my other repos, the idea here is to explore a java backend and testng test framework.

## ToDo
- add more APIs
- aad more tests
- add CI docker compose setup for build and test

## Setup

- Ubuntu 24.04
- vs code
    - using MS Extension pack for Java
- sudo apt install maven
- mvn clean install
```
[INFO] Scanning for projects...
[INFO] 
[INFO] -------------------< com.simplebank:simplebank-java >-------------------
[INFO] Building simplebank-java 1.0-SNAPSHOT
[INFO] --------------------------------[ jar ]---------------------------------
Downloading from central: https://repo.maven.apache.org/maven2/org/apache/maven/plugins/maven-clean-plugin/3.3.2/maven-clean-plugin-3.3.2.pom
...
...
[INFO] Replacing main artifact /home/viscoustorque/github/simplebank-java-testng/target/simplebank-java-1.0-SNAPSHOT.jar with repackaged archive, adding nested dependencies in BOOT-INF/.
[INFO] The original artifact has been renamed to /home/viscoustorque/github/simplebank-java-testng/target/simplebank-java-1.0-SNAPSHOT.jar.original
[INFO] 
[INFO] --- maven-install-plugin:3.1.1:install (default-install) @ simplebank-java ---
[INFO] Installing /home/viscoustorque/github/simplebank-java-testng/pom.xml to /home/viscoustorque/.m2/repository/com/simplebank/simplebank-java/1.0-SNAPSHOT/simplebank-java-1.0-SNAPSHOT.pom
[INFO] Installing /home/viscoustorque/github/simplebank-java-testng/target/simplebank-java-1.0-SNAPSHOT.jar to /home/viscoustorque/.m2/repository/com/simplebank/simplebank-java/1.0-SNAPSHOT/simplebank-java-1.0-SNAPSHOT.jar
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  8.602 s
[INFO] Finished at: 2025-06-20T17:02:00+01:00
[INFO] ------------------------------------------------------------------------
```
- Checking that I can run spring boot
    - mvn spring-boot:run
```
[INFO] Scanning for projects...
[INFO] 
[INFO] -------------------< com.simplebank:simplebank-java >-------------------
[INFO] Building simplebank-java 1.0-SNAPSHOT
[INFO] --------------------------------[ jar ]---------------------------------
[INFO] 
[INFO] >>> spring-boot-maven-plugin:3.2.5:run (default-cli) > test-compile @ simplebank-java >>>
[INFO] 
[INFO] --- maven-resources-plugin:3.3.1:resources (default-resources) @ simplebank-java ---
[INFO] skip non existing resourceDirectory /home/viscoustorque/github/simplebank-java-testng/src/main/resources
[INFO] skip non existing resourceDirectory /home/viscoustorque/github/simplebank-java-testng/src/main/resources
[INFO] 
[INFO] --- maven-compiler-plugin:3.11.0:compile (default-compile) @ simplebank-java ---
[INFO] Changes detected - recompiling the module! :source
[INFO] Compiling 1 source file with javac [debug release 21] to target/classes
[INFO] 
[INFO] --- maven-resources-plugin:3.3.1:testResources (default-testResources) @ simplebank-java ---
[INFO] skip non existing resourceDirectory /home/viscoustorque/github/simplebank-java-testng/src/test/resources
[INFO] 
[INFO] --- maven-compiler-plugin:3.11.0:testCompile (default-testCompile) @ simplebank-java ---
[INFO] Changes detected - recompiling the module! :dependency
[INFO] 
[INFO] <<< spring-boot-maven-plugin:3.2.5:run (default-cli) < test-compile @ simplebank-java <<<
[INFO] 
[INFO] 
[INFO] --- spring-boot-maven-plugin:3.2.5:run (default-cli) @ simplebank-java ---
[INFO] Attaching agents: []

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v3.2.5)

2025-06-20T17:04:38.860+01:00  INFO 22157 --- [           main] com.simplebank.SimpleBankApplication     : Starting SimpleBankApplication using Java 21.0.7 with PID 22157 (/home/viscoustorque/github/simplebank-java-testng/target/classes started by viscoustorque in /home/viscoustorque/github/simplebank-java-testng)
2025-06-20T17:04:38.861+01:00  INFO 22157 --- [           main] com.simplebank.SimpleBankApplication     : No active profile set, falling back to 1 default profile: "default"
2025-06-20T17:04:39.126+01:00  INFO 22157 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat initialized with port 8080 (http)
2025-06-20T17:04:39.132+01:00  INFO 22157 --- [           main] o.apache.catalina.core.StandardService   : Starting service [Tomcat]
2025-06-20T17:04:39.132+01:00  INFO 22157 --- [           main] o.apache.catalina.core.StandardEngine    : Starting Servlet engine: [Apache Tomcat/10.1.20]
2025-06-20T17:04:39.153+01:00  INFO 22157 --- [           main] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring embedded WebApplicationContext
2025-06-20T17:04:39.153+01:00  INFO 22157 --- [           main] w.s.c.ServletWebServerApplicationContext : Root WebApplicationContext: initialization completed in 274 ms
2025-06-20T17:04:39.269+01:00  INFO 22157 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port 8080 (http) with context path ''
2025-06-20T17:04:39.271+01:00  INFO 22157 --- [           main] com.simplebank.SimpleBankApplication     : Started SimpleBankApplication in 0.527 seconds (process running for 0.628)
```
Be mindful java dependencies which can by default set Junit as the expected test framework.  I need to exclude some frameworks in the pom.xml
```
mvn dependency:tree -Dscope=test
```
        <!-- Test dependencies -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
            <exclusions>
                <exclusion>
                    <groupId>org.junit.vintage</groupId>
                    <artifactId>junit-vintage-engine</artifactId>
                </exclusion>
                <exclusion>
                    <groupId>org.junit.jupiter</groupId>
                    <artifactId>junit-jupiter</artifactId>
                </exclusion>
                <exclusion>
                    <groupId>org.junit.platform</groupId>
                    <artifactId>junit-platform-launcher</artifactId>
                </exclusion>
                <exclusion>
                    <groupId>org.mockito</groupId>
                    <artifactId>mockito-junit-jupiter</artifactId>
                </exclusion>
            </exclusions>
        </dependency>
```