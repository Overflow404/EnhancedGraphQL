package predicate.unary;

import context.UnaryPredicateContext;
import predicate.JpaPredicate;

import javax.persistence.criteria.Predicate;

public interface UnaryPredicate extends JpaPredicate {
    Predicate create(UnaryPredicateContext context);
}
