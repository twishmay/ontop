package it.unibz.inf.ontop.model.term.functionsymbol.impl;

import com.google.common.collect.ImmutableList;
import it.unibz.inf.ontop.iq.node.VariableNullability;
import it.unibz.inf.ontop.model.term.ImmutableTerm;
import it.unibz.inf.ontop.model.term.TermFactory;
import it.unibz.inf.ontop.model.type.RDFDatatype;
import it.unibz.inf.ontop.model.type.TermTypeInference;
import it.unibz.inf.ontop.model.vocabulary.XPathFunction;

import java.util.Optional;

public class StrlenSPARQLFunctionSymbolImpl extends ReduciblePositiveAritySPARQLFunctionSymbolImpl {
    private final RDFDatatype xsdInteger;

    protected StrlenSPARQLFunctionSymbolImpl(RDFDatatype xsdString, RDFDatatype xsdInteger) {
        super("SP_STRLEN", XPathFunction.STRING_LENGTH, ImmutableList.of(xsdString));
        this.xsdInteger = xsdInteger;
    }

    @Override
    protected ImmutableTerm computeLexicalTerm(ImmutableList<ImmutableTerm> subLexicalTerms, TermFactory termFactory) {
        return termFactory.getDBStrlen(subLexicalTerms.get(0));
    }

    @Override
    protected ImmutableTerm computeTypeTerm(ImmutableList<ImmutableTerm> typeTerms, TermFactory termFactory, VariableNullability variableNullability) {
        return termFactory.getRDFTermTypeConstant(xsdInteger);
    }

    @Override
    protected boolean isAlwaysInjective() {
        return false;
    }

    @Override
    public Optional<TermTypeInference> inferType(ImmutableList<? extends ImmutableTerm> terms) {
        return Optional.of(TermTypeInference.declareTermType(xsdInteger));
    }

    /**
     * Could be supported in the future
     */
    @Override
    public boolean canBePostProcessed(ImmutableList<? extends ImmutableTerm> arguments) {
        return false;
    }
}
