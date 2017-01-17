package it.unibz.inf.ontop.injection;


public interface OntopMappingConfiguration extends OntopOBDAConfiguration {

    @Override
    OntopMappingSettings getSettings();

    interface OntopMappingBuilderFragment<B extends Builder> {

    }

    interface Builder<B extends Builder> extends OntopMappingBuilderFragment<B>, OntopOBDAConfiguration.Builder<B> {

        @Override
        OntopMappingConfiguration build();
    }

}
