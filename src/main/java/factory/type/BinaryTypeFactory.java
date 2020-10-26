package factory.type;

import context.BinaryPredicateContext;

import javax.persistence.criteria.Predicate;
import java.util.Arrays;
import java.util.List;

public class BinaryTypeFactory {

    private final List<BinaryFactory> binaryFactories = Arrays.asList(
            new IntegerBinaryTypeFactory(),
            new FloatBinaryTypeFactory(),
            new BooleanBinaryTypeFactory(),
            new DateBinaryTypeFactory(),
            new DateTimeBinaryTypeFactory(),
            new StringBinaryTypeFactory()
    );

    private BinaryTypeFactory() {

    }

    public static BinaryTypeFactory getInstance() {
        return new BinaryTypeFactory();
    }

    public Predicate newPredicate(BinaryPredicateContext context) {

        for (BinaryFactory binaryFactory : binaryFactories) {
            if (binaryFactory.support(context.getRightOperand())) {
                return binaryFactory.create(context);
            }
        }

        throw new IllegalArgumentException("No factory found!");
    }
}
