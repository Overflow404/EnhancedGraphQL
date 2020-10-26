package factory.type;

import context.BinaryPredicateContext;
import predicate.binary.BinaryPredicate;
import predicate.binary.datetime.*;

import javax.persistence.criteria.Predicate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;

public class DateTimeBinaryTypeFactory implements BinaryFactory {
    private final List<BinaryPredicate> predicates = Arrays.asList(
            new ExactlyBinaryPredicate(),
            new AfterOrEqualsBinaryPredicate(),
            new AfterBinaryPredicate(),
            new BeforeOrEqualsBinaryPredicate(),
            new BeforeBinaryPredicate()
    );

    @Override
    public boolean support(Object aType) {
        try {
            LocalDateTime.parse(aType.toString());
        } catch (DateTimeParseException e) {
            return false;
        }
        return true;
    }

    @Override
    public Predicate create(BinaryPredicateContext context) {
        for (BinaryPredicate binaryPredicate : predicates) {
            if (binaryPredicate.support(context.getOperand())) {
                return binaryPredicate.create(context);
            }
        }

        throw new IllegalArgumentException("No predicate found with name " + context.getOperand() + "!");
    }
}
