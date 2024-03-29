/*
 * Copyright (C) 2005 - 2012 Jaspersoft Corporation. All rights reserved.
 * http://www.jaspersoft.com.
 *
 * Unless you have purchased a commercial license agreement from Jaspersoft,
 * the following license terms apply:
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License  as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero  General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package com.jaspersoft.bigquery.query;

import java.util.Map;

import net.sf.jasperreports.engine.JRDataset;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRValueParameter;
import net.sf.jasperreports.engine.query.JRQueryExecuter;
import net.sf.jasperreports.engine.query.JRQueryExecuterFactory;

/**
 * Query executer factory for BigQuery queries. <br/>
 * This factory creates a {@link BigQueryQueryExecuter}
 * 
 * @author Eric Diaz
 * 
 */
public class BigQueryQueryExecuterFactory implements JRQueryExecuterFactory {
	/**
	 * Creates an {@link BigQueryQueryExecuter}.<br/>
	 * <br/>
	 * This method is called at fill time for reports/datasets having a query
	 * supported by this factory (BigQueryQueryExecuter).
	 */
	public JRQueryExecuter createQueryExecuter(JRDataset dataset, Map<String, ? extends JRValueParameter> parameters)
			throws JRException {
		return new BigQueryQueryExecuter(dataset, parameters);
	};

	/**
	 * Method not implemented
	 */
	@Override
	public Object[] getBuiltinParameters() {
		return null;
	}

	@Override
	public boolean supportsQueryParameterType(String queryParameterType) {
		return true;
	}
}