package predicate.binary.string;

import context.BinaryPredicateContext;
import predicate.binary.BinaryPredicate;

import javax.persistence.criteria.Predicate;

public class EqualsBinaryPredicate implements BinaryPredicate {

    private static final String TYPE = "EQUALS";

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
                        context.getRightOperand());
    }
}
