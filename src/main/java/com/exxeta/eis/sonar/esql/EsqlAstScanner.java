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

import com.exxeta.eis.sonar.esql.api.EsqlGrammar;
import com.exxeta.eis.sonar.esql.api.EsqlMetric;
import com.exxeta.eis.sonar.esql.metrics.ComplexityVisitor;
import com.exxeta.eis.sonar.esql.parser.EsqlParser;
import com.google.common.base.Charsets;
import com.sonar.sslr.api.AstNode;
import com.sonar.sslr.impl.Parser;
import com.sonar.sslr.squid.*;
import com.sonar.sslr.squid.metrics.CommentsVisitor;
import com.sonar.sslr.squid.metrics.CounterVisitor;
import com.sonar.sslr.squid.metrics.LinesOfCodeVisitor;
import com.sonar.sslr.squid.metrics.LinesVisitor;


import org.sonar.squid.api.SourceCode;
import org.sonar.squid.api.SourceFile;
import org.sonar.squid.api.SourceFunction;
import org.sonar.squid.api.SourceProject;
import org.sonar.squid.indexer.QueryByType;

import java.io.File;
import java.util.Collection;

public final class EsqlAstScanner {

  private EsqlAstScanner() {
  }

  /**
   * Helper method for testing checks without having to deploy them on a Sonar instance.
   */
  public static SourceFile scanSingleFile(File file, SquidAstVisitor<EsqlGrammar>... visitors) {
    if (!file.isFile()) {
      throw new IllegalArgumentException("File '" + file + "' not found.");
    }
    AstScanner<EsqlGrammar> scanner = create(new EsqlConfiguration(Charsets.UTF_8), visitors);
    scanner.scanFile(file);
    
    Collection<SourceCode> sources = scanner.getIndex().search(new QueryByType(SourceFile.class));
    if (sources.size() != 1) {
      throw new IllegalStateException("Only one SourceFile was expected whereas " + sources.size() + " has been returned.");
    }
    return (SourceFile) sources.iterator().next();
  }

  public static AstScanner<EsqlGrammar> create(EsqlConfiguration conf, SquidAstVisitor<EsqlGrammar>... visitors) {
    final SquidAstVisitorContextImpl<EsqlGrammar> context = new SquidAstVisitorContextImpl<EsqlGrammar>(new SourceProject("Esql Project"));
    final Parser<EsqlGrammar> parser = EsqlParser.create(conf);

    AstScanner.Builder<EsqlGrammar> builder = AstScanner.<EsqlGrammar> builder(context).setBaseParser(parser);

    /* Metrics */
    builder.withMetrics(EsqlMetric.values());

    /* Comments */
    builder.setCommentAnalyser(new EsqlCommentAnalyser());

    /* Files */
    builder.setFilesMetric(EsqlMetric.FILES);

    /* Functions */
    builder.withSquidAstVisitor(CounterVisitor.<EsqlGrammar> builder()
        .setMetricDef(EsqlMetric.ROUTINES)
        .subscribeTo(parser.getGrammar().routineDeclaration)
        .build());

    builder.withSquidAstVisitor(new SourceCodeBuilderVisitor<EsqlGrammar>(new SourceCodeBuilderCallback() {
      public SourceCode createSourceCode(SourceCode parentSourceCode, AstNode astNode) {
        AstNode identifier = astNode.findFirstDirectChild(parser.getGrammar().identifier);
        final String functionName = identifier == null ? "anonymous" : identifier.getTokenValue();
        final String fileKey = parentSourceCode.isType(SourceFile.class) ? parentSourceCode.getKey() : parentSourceCode.getParent(SourceFile.class).getKey();
        SourceFunction function = new SourceFunction(fileKey + ":" + functionName + ":" + astNode.getToken().getLine() + ":" + astNode.getToken().getColumn());
        function.setStartAtLine(astNode.getTokenLine());
        return function;
      }
    }, parser.getGrammar().routineDeclaration));

    /* Metrics */
    builder.withSquidAstVisitor(new LinesVisitor<EsqlGrammar>(EsqlMetric.LINES));
    builder.withSquidAstVisitor(new LinesOfCodeVisitor<EsqlGrammar>(EsqlMetric.LINES_OF_CODE));
    builder.withSquidAstVisitor(CommentsVisitor.<EsqlGrammar> builder().withCommentMetric(EsqlMetric.COMMENT_LINES)
        .withBlankCommentMetric(EsqlMetric.COMMENT_BLANK_LINES)
        .withNoSonar(true)
        .withIgnoreHeaderComment(conf.getIgnoreHeaderComments())
        .build());
    builder.withSquidAstVisitor(CounterVisitor.<EsqlGrammar> builder()
        .setMetricDef(EsqlMetric.STATEMENTS)
        .subscribeTo(
            parser.getGrammar().variableStatement,
//            parser.getGrammar().emptyStatement,
//            parser.getGrammar().expressionStatement,
            parser.getGrammar().ifStatement,
//            parser.getGrammar().iterationStatement,
//            parser.getGrammar().continueStatement,
//            parser.getGrammar().breakStatement,
            parser.getGrammar().returnStatement)
//            parser.getGrammar().withStatement,
//            parser.getGrammar().switchStatement,
//            parser.getGrammar().throwStatement,
//            parser.getGrammar().tryStatement,
//            parser.getGrammar().debuggerStatement)
        .build());

    builder.withSquidAstVisitor(new ComplexityVisitor());

    /* External visitors (typically Check ones) */
    for (SquidAstVisitor<EsqlGrammar> visitor : visitors) {
      if (visitor instanceof CharsetAwareVisitor) {
        ((CharsetAwareVisitor) visitor).setCharset(conf.getCharset());
      }
      builder.withSquidAstVisitor(visitor);
    }
    
    return builder.build();
  }

}
