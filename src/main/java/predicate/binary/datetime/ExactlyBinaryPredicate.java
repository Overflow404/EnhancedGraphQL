package predicate.binary.datetime;

import context.BinaryPredicateContext;
import predicate.binary.BinaryPredicate;

import javax.persistence.criteria.Predicate;
import java.time.LocalDateTime;

public class ExactlyBinaryPredicate implements BinaryPredicate {

    private static final String TYPE = "EXACTLY";

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
                        LocalDateTime.parse(
                                context
                                        .getRightOperand()
                                        .toString()
                        )
                );
    }
}
