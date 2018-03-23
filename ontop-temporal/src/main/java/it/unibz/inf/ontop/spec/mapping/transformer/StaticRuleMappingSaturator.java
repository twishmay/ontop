package it.unibz.inf.ontop.spec.mapping.transformer;

import it.unibz.inf.ontop.dbschema.DBMetadata;
import it.unibz.inf.ontop.spec.mapping.Mapping;
import it.unibz.inf.ontop.temporal.model.DatalogMTLProgram;

public interface StaticRuleMappingSaturator {
    Mapping saturate(Mapping mapping, DBMetadata dbMetadata, DatalogMTLProgram datalogMTLProgram);
}