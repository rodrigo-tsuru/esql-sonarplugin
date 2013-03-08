/*
 * Sonar ESQL Plugin
 * Copyright (C) 2013 Thomas Pohl and EXXETA AG
 * http://www.exxeta.de
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02
 */
package com.exxeta.eis.sonar.esql.test;

import com.exxeta.eis.sonar.esql.EsqlSquidSensor;
import com.exxeta.eis.sonar.esql.core.Esql;
import com.google.common.collect.ImmutableList;


import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.sonar.api.batch.SensorContext;
import org.sonar.api.measures.CoreMetrics;
import org.sonar.api.measures.FileLinesContext;
import org.sonar.api.measures.FileLinesContextFactory;
import org.sonar.api.profiles.RulesProfile;
import org.sonar.api.resources.*;

import java.io.File;
import java.nio.charset.Charset;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class EsqlSquidSensorTest {

  private EsqlSquidSensor sensor;

  @Before
  public void setUp() {
    FileLinesContextFactory fileLinesContextFactory = mock(FileLinesContextFactory.class);
    FileLinesContext fileLinesContext = mock(FileLinesContext.class);
    when(fileLinesContextFactory.createFor(Mockito.any(Resource.class))).thenReturn(fileLinesContext);
    sensor = new EsqlSquidSensor(mock(RulesProfile.class), fileLinesContextFactory);
  }

  @Test
  public void should_execute_on_esql_project() {
    Project project = new Project("key");
    project.setLanguageKey("java");
    assertThat(sensor.shouldExecuteOnProject(project)).isFalse();
    project.setLanguageKey("esql");
    assertThat(sensor.shouldExecuteOnProject(project)).isTrue();
  }

  @Test
  public void should_analyse() {
    ProjectFileSystem fs = mock(ProjectFileSystem.class);
    when(fs.getSourceCharset()).thenReturn(Charset.forName("UTF-8"));
    InputFile inputFile = InputFileUtils.create(
        new File("src/test/resources"),
        new File("src/test/resources/ifTest.esql"));
    when(fs.mainFiles(Esql.KEY)).thenReturn(ImmutableList.of(inputFile));
    Project project = new Project("key");
    project.setFileSystem(fs);
    SensorContext context = mock(SensorContext.class);

    sensor.analyse(project, context);

    verify(context).saveMeasure(Mockito.any(Resource.class), Mockito.eq(CoreMetrics.FILES), Mockito.eq(1.0));
    verify(context).saveMeasure(Mockito.any(Resource.class), Mockito.eq(CoreMetrics.LINES), Mockito.eq(20.0));
    verify(context).saveMeasure(Mockito.any(Resource.class), Mockito.eq(CoreMetrics.NCLOC), Mockito.eq(20.0));
    verify(context).saveMeasure(Mockito.any(Resource.class), Mockito.eq(CoreMetrics.FUNCTIONS), Mockito.eq(1.0));
    verify(context).saveMeasure(Mockito.any(Resource.class), Mockito.eq(CoreMetrics.STATEMENTS), Mockito.eq(5.0));
    verify(context).saveMeasure(Mockito.any(Resource.class), Mockito.eq(CoreMetrics.COMPLEXITY), Mockito.eq(6.0));
    verify(context).saveMeasure(Mockito.any(Resource.class), Mockito.eq(CoreMetrics.COMMENT_LINES), Mockito.eq(3.0));
  }

}
