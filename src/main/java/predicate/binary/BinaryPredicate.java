package predicate.binary;

import context.BinaryPredicateContext;
import predicate.JpaPredicate;

import javax.persistence.criteria.Predicate;

public interface BinaryPredicate extends JpaPredicate {
    Predicate create(BinaryPredicateContext context);
}
