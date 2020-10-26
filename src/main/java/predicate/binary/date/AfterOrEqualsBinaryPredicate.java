package predicate.binary.date;

import context.BinaryPredicateContext;
import predicate.binary.BinaryPredicate;

import javax.persistence.criteria.Predicate;
import java.time.LocalDate;

public class AfterOrEqualsBinaryPredicate implements BinaryPredicate {

    private static final String TYPE = "AFTEROREQUALS";

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
                        LocalDate.parse(
                                context
                                        .getRightOperand()
                                        .toString()
                        )
                );
    }
}
