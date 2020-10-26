package predicate.binary.string;

import context.BinaryPredicateContext;
import predicate.binary.BinaryPredicate;

import javax.persistence.criteria.Predicate;

public class LikeBinaryPredicate implements BinaryPredicate {

    private static final String TYPE = "LIKE";

    @Override
    public boolean support(String aType) {
        return aType.equalsIgnoreCase(TYPE);
    }

    @Override
    public Predicate create(BinaryPredicateContext context) {
        return context
                .getCriteriaBuilder()
                .like(
                        context
                                .getPath()
                                .get(context
                                        .getLeftOperand()),
                        context.getRightOperand().toString());
    }
}
