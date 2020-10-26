package predicate.binary.decimal;

import context.BinaryPredicateContext;
import predicate.binary.BinaryPredicate;

import javax.persistence.criteria.Predicate;

public class GtBinaryPredicate implements BinaryPredicate {

    private static final String TYPE = "GT";

    @Override
    public boolean support(String aType) {
        return aType.equalsIgnoreCase(TYPE);
    }

    @Override
    public Predicate create(BinaryPredicateContext context) {
        return context
                .getCriteriaBuilder()
                .greaterThan(
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
