package it.unibz.inf.ontop.identifiers;

/*
 * #%L
 * ontop-test
 * %%
 * Copyright (C) 2009 - 2014 Free University of Bozen-Bolzano
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import it.unibz.inf.ontop.quest.AbstractVirtualModeTest;

/***
 * Tests that oracle identifiers for tables and columns are treated
 * correctly. Especially, that the unquoted identifers are treated as uppercase, and
 * that the case of quoted identifiers is not changed
 */
public class DB2IdentifierTest extends AbstractVirtualModeTest {

	static final String owlfile = "resources/identifiers/identifiers.owl";
	static final String obdafile = "resources/identifiers/identifiers-db2.obda";

	protected DB2IdentifierTest() {
		super(owlfile, obdafile);
	}

	/**
	 * Test use of quoted table and column identifiers
	 * @throws Exception
	 */
	public void testLowercaseQuoted() throws Exception {
		String query = "PREFIX : <http://www.semanticweb.org/ontologies/2013/7/untitled-ontology-150#> SELECT ?x WHERE {?x a :Country} ORDER BY ?x";
		String val = runQueryAndReturnStringX(query);
		assertEquals("<http://www.semanticweb.org/ontologies/2013/7/untitled-ontology-150#Country-991>", val);
	}
			
	
	/**
	 * Test use of lowercase, unquoted table, schema and column identifiers
	 * @throws Exception
	 */
	public void testLowercaseUnquotedSchema() throws Exception {
		String query = "PREFIX : <http://www.semanticweb.org/ontologies/2013/7/untitled-ontology-150#> SELECT ?x WHERE {?x a :Country2} ORDER BY ?x";
		String val = runQueryAndReturnStringX(query);
		assertEquals("<http://www.semanticweb.org/ontologies/2013/7/untitled-ontology-150#Country2-991>", val);
	}
	
	public void testAliasUnquotedColumn() throws Exception {
		String query = "PREFIX : <http://www.semanticweb.org/ontologies/2013/7/untitled-ontology-150#> SELECT ?x WHERE {?x a :Country3} ORDER BY ?x";
		String val = runQueryAndReturnStringX(query);
		assertEquals("<http://www.semanticweb.org/ontologies/2013/7/untitled-ontology-150#Country3-991>", val);
	}
}
