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

package com.exxeta.eis.sonar.esql;

import com.exxeta.eis.sonar.esql.colorizer.EsqlColorizerFormat;
import com.exxeta.eis.sonar.esql.core.Esql;
import com.exxeta.eis.sonar.esql.core.EsqlSourceImporter;
import com.exxeta.eis.sonar.esql.cpd.EsqlCpdMapping;
import com.google.common.collect.ImmutableList;


import org.sonar.api.Extension;
import org.sonar.api.Properties;
import org.sonar.api.Property;
import org.sonar.api.SonarPlugin;

import java.util.List;

@Properties({
  // Global Esql settings
  @Property(
    key = EsqlPlugin.FILE_SUFFIXES_KEY,
    defaultValue = EsqlPlugin.FILE_SUFFIXES_DEFVALUE,
    name = "File suffixes",
    description = "Comma-separated list of suffixes for files to analyze.",
    global = true,
    project = true)
 

})
public class EsqlPlugin extends SonarPlugin {

  public List<Class<? extends Extension>> getExtensions() {
    return ImmutableList.of(
        Esql.class,
        EsqlSourceImporter.class,
        EsqlColorizerFormat.class,
        EsqlProfile.class,
        EsqlCpdMapping.class,
        EsqlRuleRepository.class,
        EsqlCommonRulesEngineProvider.class,
        EsqlSquidSensor.class
);
  }

  // Global ESQL constants
  public static final String FALSE = "false";

  public static final String FILE_SUFFIXES_KEY = "sonar.esql.file.suffixes";
  public static final String FILE_SUFFIXES_DEFVALUE = "esql";

  public static final String PROPERTY_PREFIX = "sonar.esql";
  public static final String TEST_FRAMEWORK_KEY = PROPERTY_PREFIX + ".testframework";
  public static final String TEST_FRAMEWORK_DEFAULT = "";

}
