package predicate.binary.decimal;

import context.BinaryPredicateContext;
import predicate.binary.BinaryPredicate;

import javax.persistence.criteria.Predicate;

public class EqBinaryPredicate implements BinaryPredicate {

    private static final String TYPE = "EQ";

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
                        Float.parseFloat(
                                context
                                        .getRightOperand()
                                        .toString()
                        )
                );
    }
}
