package parser;

import lombok.Builder;
import specification.PathSpecification;

@Builder(toBuilder = true)
public class ParseResult<T> {
    private final PathSpecification<T> specification;

    public ParseResult(PathSpecification<T> specification) {
        this.specification = specification;
    }

    public PathSpecification<T> getSpecification() {
        return specification;
    }
}
