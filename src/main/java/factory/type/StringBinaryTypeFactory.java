package factory.type;

import context.BinaryPredicateContext;
import predicate.binary.BinaryPredicate;
import predicate.binary.string.EqualsBinaryPredicate;
import predicate.binary.string.LikeBinaryPredicate;

import javax.persistence.criteria.Predicate;
import java.util.Arrays;
import java.util.List;

public class StringBinaryTypeFactory implements BinaryFactory {
    private final List<BinaryPredicate> predicates = Arrays.asList(
            new EqualsBinaryPredicate(),
            new LikeBinaryPredicate()
    );

    @Override
    public boolean support(Object aType) {
        return aType instanceof String;
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
