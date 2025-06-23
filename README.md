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
Run the tests with mvn
```
[INFO] Changes detected - recompiling the module! :source
[INFO] Compiling 8 source files with javac [debug release 21] to target/classes
[INFO] Annotation processing is enabled because one or more processors were found
  on the class path. A future release of javac may disable annotation processing
  unless at least one processor is specified by name (-processor), or a search
  path is specified (--processor-path, --processor-module-path), or annotation
  processing is enabled explicitly (-proc:only, -proc:full).
  Use -Xlint:-options to suppress this message.
  Use -proc:none to disable annotation processing.
[INFO] 
[INFO] --- maven-resources-plugin:3.3.1:testResources (default-testResources) @ simplebank-java-testng ---
[INFO] skip non existing resourceDirectory /home/viscoustorque/github/simplebank-java-testng/src/test/resources
[INFO] 
[INFO] --- maven-compiler-plugin:3.11.0:testCompile (default-testCompile) @ simplebank-java-testng ---
[INFO] Changes detected - recompiling the module! :dependency
[INFO] Compiling 3 source files with javac [debug release 21] to target/test-classes
[INFO] Annotation processing is enabled because one or more processors were found
  on the class path. A future release of javac may disable annotation processing
  unless at least one processor is specified by name (-processor), or a search
  path is specified (--processor-path, --processor-module-path), or annotation
  processing is enabled explicitly (-proc:only, -proc:full).
  Use -Xlint:-options to suppress this message.
  Use -proc:none to disable annotation processing.
[INFO] 
[INFO] --- maven-surefire-plugin:3.1.2:test (default-test) @ simplebank-java-testng ---
[INFO] Using auto detected provider org.apache.maven.surefire.testng.TestNGProvider
[INFO] 
[INFO] -------------------------------------------------------
[INFO]  T E S T S
[INFO] -------------------------------------------------------
[INFO] Running TestSuite
11:39:54.891 [main] INFO org.testng.internal.Utils -- [XmlSuite] [WARN] 'parallel' value 'false' is no longer valid, defaulting to 'none'.
11:39:55.000 [main] INFO org.springframework.test.context.support.AnnotationConfigContextLoaderUtils -- Could not detect default configuration classes for test class [com.simplebank.api.UserControllerApiTest]: UserControllerApiTest does not declare any static, non-private, non-final, nested classes annotated with @Configuration.
11:39:55.052 [main] INFO org.springframework.boot.test.context.SpringBootTestContextBootstrapper -- Found @SpringBootConfiguration com.simplebank.SimpleBankApplication for test class com.simplebank.api.UserControllerApiTest
OpenJDK 64-Bit Server VM warning: Sharing is only supported for boot loader classes because bootstrap classpath has been appended
WARNING: A Java agent has been loaded dynamically (/home/viscoustorque/.m2/repository/net/bytebuddy/byte-buddy-agent/1.14.13/byte-buddy-agent-1.14.13.jar)
WARNING: If a serviceability tool is in use, please run with -XX:+EnableDynamicAgentLoading to hide this warning
WARNING: If a serviceability tool is not in use, please run with -Djdk.instrument.traceUsage for more information
WARNING: Dynamic loading of agents will be disallowed by default in a future release

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v3.2.5)

2025-06-23T11:39:55.920+01:00  INFO 24577 --- [           main] c.simplebank.api.UserControllerApiTest   : Starting UserControllerApiTest using Java 21.0.7 with PID 24577 (/home/viscoustorque/github/simplebank-java-testng/target/test-classes started by viscoustorque in /home/viscoustorque/github/simplebank-java-testng)

...

[INFO] Tests run: 4, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 2.787 s -- in TestSuite
[INFO] 
[INFO] Results:
[INFO] 
[INFO] Tests run: 4, Failures: 0, Errors: 0, Skipped: 0
[INFO] 
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  4.094 s
[INFO] Finished at: 2025-06-23T11:39:57+01:00
[INFO] ------------------------------------------------------------------------
```