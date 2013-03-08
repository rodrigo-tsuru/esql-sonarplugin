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


import org.sonar.api.rules.AnnotationRuleParser;
import org.sonar.api.rules.Rule;
import org.sonar.api.rules.RuleRepository;

import com.exxeta.eis.sonar.esql.check.CheckList;
import com.exxeta.eis.sonar.esql.core.Esql;


import java.util.List;

public class EsqlRuleRepository extends RuleRepository {

  private final AnnotationRuleParser annotationRuleParser;

  public EsqlRuleRepository(AnnotationRuleParser annotationRuleParser) {
    super(CheckList.REPOSITORY_KEY, Esql.KEY);
    setName(CheckList.REPOSITORY_NAME);
    this.annotationRuleParser = annotationRuleParser;
  }

  @Override
  public List<Rule> createRules() {
	  List<Rule> rules = annotationRuleParser.parse(CheckList.REPOSITORY_KEY, CheckList.getChecks());
    return rules;
  }

}
