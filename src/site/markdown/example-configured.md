# Different Locations

If you need to generate the XML file into a different directory (`junit.platform.reporting.output.dir=<path>`) 
you have to change the `xmlFile` property to represent that.
Changing the output directory for the resulting HTML report can be achieved by using the `outputDirectory` property of 
the OTR4J-Maven-Plugin. It is also possible to change the file name of the resulting HTML report by using 
`htmlTestReportFile`.

```xml
<plugin>
  <groupId>com.soebes.maven.plugins</groupId>
  <artifactId>otr4j-maven-plugin</artifactId>
  <version>0.2.0</version>
  <configuration>
    <xmlFile>Location of the open-test-report.xml</xmlFile>
    <outputDirectory>The output directory where the resulting HTML report will be written to</outputDirectory>
    <htmlTestReportFile>The name of the result HTML file</htmlTestReportFile>
  </configuration>
  <executions>
    <execution>
      <id>htmlreport</id>
      <goals>
        <goal>htmlreport</goal>
      </goals>
    </execution>
  </executions>
</plugin>
```