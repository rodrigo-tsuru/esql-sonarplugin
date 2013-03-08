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


import org.junit.Before;
import org.junit.Test;
import org.sonar.api.config.PropertyDefinitions;
import org.sonar.api.config.Settings;

import com.exxeta.eis.sonar.esql.EsqlPlugin;

import static org.fest.assertions.Assertions.assertThat;

public class EsqlPluginTest {

  private EsqlPlugin plugin;

  @Before
  public void setUp() throws Exception {
    plugin = new EsqlPlugin();
  }

  @Test
  public void testGetExtensions() throws Exception {
    assertThat(plugin.getExtensions().size()).isEqualTo(8);
  }

  @Test
  public void testProperties() {
    Settings settings = new Settings(new PropertyDefinitions(plugin));
    // SONARPLUGINS-2524
    assertThat(settings.getString(EsqlPlugin.TEST_FRAMEWORK_KEY)).isNull();
  }

}
