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


import org.sonar.api.resources.Project;
import org.sonar.commonrules.api.CommonRulesEngine;
import org.sonar.commonrules.api.CommonRulesEngineProvider;

import com.exxeta.eis.sonar.esql.core.Esql;


public class EsqlCommonRulesEngineProvider extends CommonRulesEngineProvider {

  public EsqlCommonRulesEngineProvider() {
    super();
  }

  public EsqlCommonRulesEngineProvider(Project project) {
    super(project);
  }

  @Override
  protected void doActivation(CommonRulesEngine engine) {
    engine.activateRule("DuplicatedBlocks");
    engine.activateRule("InsufficientCommentDensity");
    engine.activateRule("InsufficientLineCoverage");
    engine.activateRule("InsufficientBranchCoverage");
  }

  @Override
  protected String getLanguageKey() {
    return Esql.KEY;
  }

}
