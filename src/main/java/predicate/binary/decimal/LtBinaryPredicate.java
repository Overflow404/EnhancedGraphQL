package predicate.binary.decimal;

import context.BinaryPredicateContext;
import predicate.binary.BinaryPredicate;

import javax.persistence.criteria.Predicate;

public class LtBinaryPredicate implements BinaryPredicate {

    private static final String TYPE = "LT";

    @Override
    public boolean support(String aType) {
        return aType.equalsIgnoreCase(TYPE);
    }

    @Override
    public Predicate create(BinaryPredicateContext context) {
        return context
                .getCriteriaBuilder()
                .lessThan(
                        context
                                .getPath()
                                .get(context
                                        .getLeftOperand()),
                        Float.parseFloat(
                                context
                                        .getRightOperand()
                                        .toString()
                        )
                );
    }
}
