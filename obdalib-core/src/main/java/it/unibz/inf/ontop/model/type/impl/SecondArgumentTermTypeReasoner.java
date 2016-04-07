package it.unibz.inf.ontop.model.type.impl;

import com.google.common.collect.ImmutableList;
import it.unibz.inf.ontop.model.type.TermType;

import java.util.Optional;

public class SecondArgumentTermTypeReasoner extends AbstractTermTypeReasoner {

    @Override
    protected Optional<TermType> deduceType(ImmutableList<Optional<TermType>> argumentTypes) {
        if (argumentTypes.size() < 2) {
            throw new IllegalStateException("At least two arguments is required by the SecondArgumentTermTypeReasoner");
        }
        return argumentTypes.get(1);
    }
}
