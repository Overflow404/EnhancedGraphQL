package parser;

import context.BinaryPredicateContext;
import context.CompoundPredicateContext;
import context.UnaryPredicateContext;
import factory.predicate.CompoundPredicateFactory;
import factory.predicate.UnaryPredicateFactory;
import factory.type.BinaryTypeFactory;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Slf4j
public class AbstractSyntaxTreeParser<T> implements Parser<T> {

    private final BinaryTypeFactory binaryTypeFactory;
    private final CompoundPredicateFactory compoundPredicateFactory;
    private final UnaryPredicateFactory unaryPredicateFactory;

    private AbstractSyntaxTreeParser(BinaryTypeFactory binaryTypeFactory,
                                     CompoundPredicateFactory compoundPredicateFactory,
                                     UnaryPredicateFactory unaryPredicateFactory) {
        this.binaryTypeFactory = binaryTypeFactory;
        this.compoundPredicateFactory = compoundPredicateFactory;
        this.unaryPredicateFactory = unaryPredicateFactory;

    }

    public static <T> Parser<T> getInstance() {
        return new AbstractSyntaxTreeParser<>(
                BinaryTypeFactory.getInstance(),
                CompoundPredicateFactory.getInstance(),
                UnaryPredicateFactory.getInstance());
    }

    @Override
    public ParserResult<T> parse(Map<String, Object> anExpression) {
        if (voidExpression(anExpression)) {
            return ParserResult
                    .<T>builder()
                    .specification((path, criteriaQuery, criteriaBuilder) -> criteriaBuilder.and())
                    .build();
        }
        return new ParserResult<>(
                (path, criteriaQuery, criteriaBuilder)
                        -> buildAbstractSyntaxTree(anExpression, criteriaBuilder, path)
        );
    }

    private Predicate buildAbstractSyntaxTree(Object map, CriteriaBuilder criteriaBuilder, Path<?> path) {
        if (inputIsValid(map)) {
            if (compoundExpression(map)) {
                return buildCompoundPredicate(map, criteriaBuilder, path);
            } else if (binaryExpression(map)) {
                if (isRawType(map)) {
                    return buildBinaryPredicate(map, criteriaBuilder, path);
                } else if (isList(terminalExpression(map))) {
                    return buildBinaryPredicateWithList(map, criteriaBuilder, path);
                } else {
                    return buildUnaryPredicate(map, criteriaBuilder, path);
                }
            } else {
                throw new IllegalArgumentException("Not a valid input!");
            }
        } else {
            throw new IllegalArgumentException("Not a valid input!");
        }
    }


    private Predicate buildUnaryPredicate(Object map, CriteriaBuilder criteriaBuilder, Path<?> root) {
        return unaryPredicateFactory.newPredicate(
                UnaryPredicateContext
                        .builder()
                        .operand(keyOfFirstEntry(map))
                        .predicate(buildAbstractSyntaxTree(valueOfFirstEntry(map), criteriaBuilder, root))
                        .criteriaBuilder(criteriaBuilder)
                        .build()
        );
    }

    private Predicate buildBinaryPredicate(Object map, CriteriaBuilder criteriaBuilder, Path<?> root) {

        return binaryTypeFactory.newPredicate(
                BinaryPredicateContext
                        .builder()
                        .operand(keyOfFirstEntry(valueOfFirstEntry(map)))
                        .leftOperand(keyOfFirstEntry(map))
                        .rightOperand(terminalExpression(map).toString())
                        .criteriaBuilder(criteriaBuilder)
                        .path(root)
                        .build()
        );
    }

    private Predicate buildBinaryPredicateWithList(Object map, CriteriaBuilder criteriaBuilder, Path<?> root) {
        return binaryTypeFactory.newPredicate(
                BinaryPredicateContext
                        .builder()
                        .operand(keyOfFirstEntry(valueOfFirstEntry(map)))
                        .leftOperand(keyOfFirstEntry(map))
                        .rightOperand(terminalExpression(map))
                        .criteriaBuilder(criteriaBuilder)
                        .path(root)
                        .build()
        );
    }

    private Predicate buildCompoundPredicate(Object map, CriteriaBuilder criteriaBuilder, Path<?> root) {
        return compoundPredicateFactory
                .newPredicate(
                        CompoundPredicateContext
                                .builder()
                                .operand(keyOfFirstEntry(map))
                                .predicates(toList(valueOfFirstEntry(map))
                                        .stream()
                                        .map(e -> buildAbstractSyntaxTree(e, criteriaBuilder, root))
                                        .collect(Collectors.toList()))
                                .criteriaBuilder(criteriaBuilder)
                                .build()
                );
    }


    private String key(Map.Entry<String, Object> entry) {
        return entry.getKey();
    }

    private Object value(Map.Entry<String, Object> entry) {
        return entry.getValue();
    }

    @SuppressWarnings("unchecked")
    private Map.Entry<String, Object> firstEntry(Object map) {
        return ((Map<String, Object>) (map)).entrySet().iterator().next();
    }

    private String keyOfFirstEntry(Object map) {
        return key(firstEntry(map));
    }

    private Object valueOfFirstEntry(Object map) {
        return value(firstEntry(map));
    }

    @SuppressWarnings("unchecked")
    private List<Object> toList(Object object) {
        return (List<Object>) object;
    }

    private Object terminalExpression(Object map) {
        return valueOfFirstEntry(valueOfFirstEntry(map));
    }

    private boolean binaryExpression(Object map) {
        return isMap(valueOfFirstEntry(map));
    }

    private boolean compoundExpression(Object map) {
        return isList(valueOfFirstEntry(map));
    }

    private boolean inputIsValid(Object map) {
        return isMap(map);
    }

    private boolean isString(Object object) {
        return object instanceof String;
    }

    private boolean isList(Object object) {
        return object instanceof List;
    }

    private boolean isMap(Object object) {
        return object instanceof Map;
    }

    private boolean isNumber(Object object) {
        return object instanceof Number;
    }

    private boolean isBoolean(Object object) {
        return object instanceof Boolean;
    }

    private boolean isRawType(Object map) {
        return isString(terminalExpression(map)) ||
                isNumber(terminalExpression(map)) ||
                isBoolean(terminalExpression(map));
    }

    private boolean voidExpression(Map<String, Object> anExpression) {
        return anExpression == null || anExpression.isEmpty();
    }
}
