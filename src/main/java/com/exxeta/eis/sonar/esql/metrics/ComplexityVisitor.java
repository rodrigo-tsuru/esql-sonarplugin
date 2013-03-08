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

package com.exxeta.eis.sonar.esql.metrics;


import com.exxeta.eis.sonar.esql.api.EsqlGrammar;
import com.exxeta.eis.sonar.esql.api.EsqlKeyword;
import com.exxeta.eis.sonar.esql.api.EsqlMetric;
import com.sonar.sslr.api.AstNode;
import com.sonar.sslr.squid.SquidAstVisitor;



public class ComplexityVisitor extends SquidAstVisitor<EsqlGrammar> {

  @Override
  public void init() {
    EsqlGrammar grammar = getContext().getGrammar();
    subscribeTo(
        grammar.routineDeclaration,
        // Branching nodes
        grammar.ifStatement,
//        grammar.iterationStatement,
        grammar.whenClause,
        grammar.forStatement,
        grammar.whileStatement,
        grammar.declareHandlerStatement,
        //grammar.catch_,
        grammar.returnStatement,
        grammar.throwStatement,
        // Expressions
        //EsqlPunctuator.QUERY,
        EsqlKeyword.AND,
        EsqlKeyword.OR);
  }

  @Override
  public void visitNode(AstNode astNode) {
    if (astNode.is(getContext().getGrammar().returnStatement) && isLastReturnStatement(astNode)) {
      return;
    }
    getContext().peekSourceCode().add(EsqlMetric.COMPLEXITY, 1);
  }

  private boolean isLastReturnStatement(AstNode astNode) {
    return astNode.nextSibling()==null;
  }

}
