/*
 * The OTR4J Maven Plugins
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.soebes.maven.plugins.ota4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.opentest4j.reporting.tooling.core.htmlreport.DefaultHtmlReportWriter;

/**
 * @author <a href="mailto:kama@soebes.de">Karl Heinz Marbaise</a>
 */
@Mojo(
    name = "htmlreport",
    requiresProject = true,
    threadSafe = true,
    requiresDependencyCollection = ResolutionScope.TEST,
    defaultPhase = LifecyclePhase.TEST
)
public class GenerateHtmlReportMojo
    extends AbstractOTR4JPlugIn {

  /**
   * Define the default XML source file.
   */
  @Parameter(defaultValue = "${project.build.directory}/open-test-report.xml", required = true)
  private Path xmlFile;

  /**
   * Define the resulting HTML report directory.
   */
  @Parameter(defaultValue = "${project.reporting.outputDirectory}", required = true)
  private Path outputDirectory;

  /**
   * Define the resulting HTML report file.
   */
  @Parameter(defaultValue = "open-test-report.html", required = true)
  private String htmlTestReportFile;

  public void execute()
      throws MojoExecutionException, MojoFailureException {
    if (isSkip()) {
      getLog().info("Skipped due to configuration request.");
      return;
    }

    getLog().info("Checking outputDirectory " + outputDirectory.normalize());
    if (!Files.exists(outputDirectory)) {
      try {
        Files.createDirectories(outputDirectory);
      } catch (IOException e) {
        throw new MojoFailureException("Could not create " + outputDirectory, e);
      }
    }

    var htmlFile = outputDirectory.resolve(htmlTestReportFile);
    getLog().info("Started generating HTML report.");
    try {
      new DefaultHtmlReportWriter().writeHtmlReport(List.of(xmlFile), htmlFile);
    } catch (Exception e) {
      throw new MojoFailureException(e);
    }
    getLog().info("Written HTML report to " + htmlFile.toString());
  }

}
