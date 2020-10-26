package predicate.binary.bool;

import context.BinaryPredicateContext;
import predicate.binary.BinaryPredicate;

import javax.persistence.criteria.Predicate;

public class IsBinaryPredicate implements BinaryPredicate {

    private static final String TYPE = "IS";

    @Override
    public boolean support(String aType) {
        return aType.equalsIgnoreCase(TYPE);
    }

    @Override
    public Predicate create(BinaryPredicateContext context) {
        return context
                .getCriteriaBuilder()
                .equal(
                        context
                                .getPath()
                                .get(context
                                        .getLeftOperand()),
                        Boolean.parseBoolean(
                                context
                                        .getRightOperand()
                                        .toString()
                        )
                );
    }
}
