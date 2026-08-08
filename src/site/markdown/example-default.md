# The Default Configuration

This is the [required configuration](https://docs.junit.org/6.1.2/advanced-topics/junit-platform-reporting.html#open-test-reporting) 
in your project. That wil generate appropriate XML report which are required as input for the OTR4J-Maven-Plugin.

```xml
<project>
  <dependencyManagement>
    <dependencies>
      <dependency>
        <groupId>org.junit</groupId>
        <artifactId>junit-bom</artifactId>
        <version>6.1.2</version>
        <scope>import</scope>
        <type>pom</type>
      </dependency>
      ...
    </dependencies>
  </dependencyManagement>
  ...
  <!-- ... -->
  <dependencies>
    <dependency>
      <groupId>org.junit.platform</groupId>
      <artifactId>junit-platform-reporting</artifactId>
      <scope>test</scope>
    </dependency>
  </dependencies>
  <build>
    <pluginManagement>
      <plugins>
        <plugin>
          <artifactId>maven-surefire-plugin</artifactId>
          <version>3.5.6</version>
          <configuration>
            <properties>
              <configurationParameters>
                junit.platform.reporting.open.xml.enabled = true
              </configurationParameters>
            </properties>
          </configuration>
        </plugin>
        <plugin>
          <groupId>com.soebes.maven.plugins</groupId>
          <artifactId>otr4j-maven-plugin</artifactId>
          <version>0.1.1</version>
          <executions>
            <execution>
              <id>htmlreport</id>
              <goals>
                <goal>htmlreport</goal>
              </goals>
            </execution>
          </executions>
        </plugin>
      </plugins>
    </pluginManagement>
    ...
  </build>
  <!-- ... -->
</project>
```
The above example shows how to use the `otr4j-maven-plugin`. By default it is not required 
to make an configuration (convention over configuration). The OTR4J-Maven-Plugin will pickup the
generated `open-test-report.xml` from the default location (`target` directory)
and will transform it into the HTML report which will be put into
the `target/site` directory.

