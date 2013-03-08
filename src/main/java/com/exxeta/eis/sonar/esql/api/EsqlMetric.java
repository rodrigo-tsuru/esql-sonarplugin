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
package com.exxeta.eis.sonar.esql.api;

import org.sonar.squid.measures.CalculatedMetricFormula;
import org.sonar.squid.measures.MetricDef;

public enum EsqlMetric implements MetricDef{
	FILES,
	  LINES,
	  LINES_OF_CODE,
	  COMMENT_LINES,
	  COMMENT_BLANK_LINES,
	  STATEMENTS,
	  COMPLEXITY,
	  ROUTINES;

	public boolean aggregateIfThereIsAlreadyAValue() {
		return true;
	}

	public CalculatedMetricFormula getCalculatedMetricFormula() {
		return null;
	}

	public String getName() {
		return name();
	}

	public boolean isCalculatedMetric() {
		return false;
	}

	public boolean isThereAggregationFormula() {
		return true;
	}



}
