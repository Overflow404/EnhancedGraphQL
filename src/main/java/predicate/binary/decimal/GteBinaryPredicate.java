package predicate.binary.decimal;

import context.BinaryPredicateContext;
import predicate.binary.BinaryPredicate;

import javax.persistence.criteria.Predicate;

public class GteBinaryPredicate implements BinaryPredicate {

    private static final String TYPE = "GTE";

    @Override
    public boolean support(String aType) {
        return aType.equalsIgnoreCase(TYPE);
    }

    @Override
    public Predicate create(BinaryPredicateContext context) {
        return context
                .getCriteriaBuilder()
                .greaterThanOrEqualTo(
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
