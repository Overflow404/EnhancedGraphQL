package parser;

import lombok.Builder;
import specification.PathSpecification;

@Builder(toBuilder = true)
public class ParserResult<T> {
    private final PathSpecification<T> specification;

    public ParserResult(PathSpecification<T> specification) {
        this.specification = specification;
    }

    public PathSpecification<T> getSpecification() {
        return specification;
    }
}
