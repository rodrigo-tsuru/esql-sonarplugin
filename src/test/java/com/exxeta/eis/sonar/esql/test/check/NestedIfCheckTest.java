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
package com.exxeta.eis.sonar.esql.test.check;

import java.io.File;


import org.junit.Test;
import org.sonar.squid.api.SourceFile;

import com.exxeta.eis.sonar.esql.EsqlAstScanner;
import com.exxeta.eis.sonar.esql.check.NestedIfDepthCheck;
import com.sonar.sslr.squid.checks.CheckMessagesVerifier;

public class NestedIfCheckTest {
	 @Test
	  public void test() {
	    NestedIfDepthCheck check = new NestedIfDepthCheck();
	    check.maximumNestingLevel=3;
	    
	    SourceFile file =EsqlAstScanner.scanSingleFile(new File("src/test/resources/ifTest.esql"), check);
	    CheckMessagesVerifier.verify(file.getCheckMessages())
	        .next().atLine(8).withMessage("This if has a nesting level of 4, which is higher than the maximum allowed 3.")
	        .next().atLine(12).withMessage("This if has a nesting level of 4, which is higher than the maximum allowed 3.")
	        .noMore();
	  }
}
