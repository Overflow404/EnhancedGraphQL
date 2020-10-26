package factory.type;

import context.BinaryPredicateContext;

import javax.persistence.criteria.Predicate;

public interface BinaryFactory {
    boolean support(Object aType);

    Predicate create(BinaryPredicateContext context);
}
