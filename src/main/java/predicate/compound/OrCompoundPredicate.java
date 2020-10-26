package predicate.compound;

import context.CompoundPredicateContext;

import javax.persistence.criteria.Predicate;

public class OrCompoundPredicate implements CompoundPredicate {

    private static final String TYPE = "OR";

    @Override
    public boolean support(String aType) {
        return aType.equalsIgnoreCase(TYPE);
    }

    @Override
    public Predicate create(CompoundPredicateContext context) {
        return context
                .getCriteriaBuilder()
                .or(
                        context
                                .getPredicates()
                                .toArray(new Predicate[0])
                );
    }
}
