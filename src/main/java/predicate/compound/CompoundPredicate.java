package predicate.compound;


import context.CompoundPredicateContext;
import predicate.JpaPredicate;

import javax.persistence.criteria.Predicate;

public interface CompoundPredicate extends JpaPredicate {
    Predicate create(CompoundPredicateContext context);
}
