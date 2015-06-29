package org.semanticweb.ontop.pivotalrepr;

public interface UnionNode extends QueryNode {

    @Override
    UnionNode clone();

    @Override
    UnionNode acceptNodeTransformer(QueryNodeTransformer transformer) throws QueryNodeTransformationException;
}