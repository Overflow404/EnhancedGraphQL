package predicate.binary.date;

import context.BinaryPredicateContext;
import predicate.binary.BinaryPredicate;

import javax.persistence.criteria.Predicate;
import java.time.LocalDate;

public class AfterBinaryPredicate implements BinaryPredicate {

    private static final String TYPE = "AFTER";

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
                        LocalDate.parse(
                                context
                                        .getRightOperand()
                                        .toString()
                        )
                );
    }
}
