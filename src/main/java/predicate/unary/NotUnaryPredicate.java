package predicate.unary;

import context.UnaryPredicateContext;

import javax.persistence.criteria.Predicate;

public class NotUnaryPredicate implements UnaryPredicate {


    private static final String TYPE = "NOT";

    @Override
    public boolean support(String aType) {
        return aType.equalsIgnoreCase(TYPE);
    }

    @Override
    public Predicate create(UnaryPredicateContext context) {
        return context
                .getCriteriaBuilder()
                .not(
                        context
                                .getPredicate()
                );
    }
}
