package predicate.compound;

import context.CompoundPredicateContext;

import javax.persistence.criteria.Predicate;

public class AndCompoundPredicate implements CompoundPredicate {

    private static final String TYPE = "AND";

    @Override
    public boolean support(String aType) {
        return aType.equalsIgnoreCase(TYPE);
    }

    @Override
    public Predicate create(CompoundPredicateContext context) {
        return context
                .getCriteriaBuilder()
                .and(
                        context
                                .getPredicates()
                                .toArray(new Predicate[0])
                );
    }
}
