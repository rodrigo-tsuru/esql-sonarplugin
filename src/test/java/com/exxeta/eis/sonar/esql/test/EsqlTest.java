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
import org.sonar.api.config.Settings;

import com.exxeta.eis.sonar.esql.EsqlPlugin;
import com.exxeta.eis.sonar.esql.core.Esql;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertSame;

public class EsqlTest {

  private Settings settings;
  private Esql esql;

  @Before
  public void setUp() {
    settings = new Settings();
    esql = new Esql(settings);
  }

  @Test
  public void defaultSuffixes() {
    settings.setProperty(EsqlPlugin.FILE_SUFFIXES_KEY, "");
    assertArrayEquals(esql.getFileSuffixes(), new String[] {"esql"});
    assertArrayEquals(esql.getFileSuffixes(), new String[] {"esql"});
    assertSame(settings, esql.getSettings());
  }

  @Test
  public void customSuffixes() {
    settings.setProperty(EsqlPlugin.FILE_SUFFIXES_KEY, "esql");
    assertArrayEquals(esql.getFileSuffixes(), new String[] {"esql"});
  }

}
