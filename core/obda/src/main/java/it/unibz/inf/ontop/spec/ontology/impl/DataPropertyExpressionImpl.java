package it.unibz.inf.ontop.spec.ontology.impl;

/*
 * #%L
 * ontop-obdalib-core
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

import it.unibz.inf.ontop.spec.ontology.DataPropertyExpression;
import it.unibz.inf.ontop.spec.ontology.DataPropertyRangeExpression;
import it.unibz.inf.ontop.spec.ontology.DataSomeValuesFrom;
import it.unibz.inf.ontop.spec.ontology.Datatype;
import org.apache.commons.rdf.api.IRI;
import org.apache.commons.rdf.api.RDF;
import org.apache.commons.rdf.simple.SimpleRDF;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents DataPropertyExpression from the OWL 2 QL Specification
 * 
 * DataPropertyExpression := DataProperty
 * 
 * Support for owl:topDataProperty and owl:bottomDataProperty
 * 
 * @author Roman Kontchakov
 *
 */

public class DataPropertyExpressionImpl implements DataPropertyExpression {

	private static final RDF RDF_FACTORY = new SimpleRDF();

	private final String name;
	
	private final boolean isTop, isBottom;
	
	private final Map<Datatype, DataSomeValuesFrom> domains = new HashMap<>();
	private final DataPropertyRangeExpressionImpl range;

	public static final String owlTopDataPropertyIRI = "http://www.w3.org/2002/07/owl#topDataProperty";
	public static final String owlBottomDataPropertyIRI  = "http://www.w3.org/2002/07/owl#bottomDataProperty";
	
    public static final DataPropertyExpression owlTopDataProperty = new DataPropertyExpressionImpl(owlTopDataPropertyIRI);
    public static final DataPropertyExpression owlBottomDataProperty = new DataPropertyExpressionImpl(owlBottomDataPropertyIRI);
	private final IRI iri;

	DataPropertyExpressionImpl(String name) {
		this.name = name;
		this.iri = RDF_FACTORY.createIRI(name);
		this.isTop = name.equals(owlTopDataPropertyIRI);
		this.isBottom = name.equals(owlBottomDataPropertyIRI);

		this.domains.put(DatatypeImpl.rdfsLiteral, new DataSomeValuesFromImpl(this, DatatypeImpl.rdfsLiteral));
		this.range = new DataPropertyRangeExpressionImpl(this);
	}

	@Override
	public IRI getIRI() {
		return iri;
	}

	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public DataSomeValuesFrom getDomainRestriction(Datatype datatype) {
		DataSomeValuesFrom domain = domains.get(datatype);
		if (domain == null) {
			domain = new DataSomeValuesFromImpl(this, datatype);
			domains.put(datatype, domain);
		}
		return domain;
	}

	@Override
	public Collection<DataSomeValuesFrom> getAllDomainRestrictions() {
		return domains.values();
	}

	
	@Override
	public DataPropertyRangeExpression getRange() {
		return range;
	}
	
	@Override
	public boolean isBottom() {
		return isBottom;
	}
	
	@Override
	public boolean isTop() {
		return isTop;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		
		if (obj instanceof DataPropertyExpressionImpl) {
			DataPropertyExpressionImpl other = (DataPropertyExpressionImpl) obj;
			return name.equals(other.name);
		}
		
		// object and data properties share the same name space	
		if (obj instanceof ObjectPropertyExpressionImpl) {
			ObjectPropertyExpressionImpl other = (ObjectPropertyExpressionImpl) obj;
			return (false == other.isInverse()) && name.equals(other.getName());
		}
		return false;
	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}
	
	@Override
	public String toString() {
		return name;
	}
}
