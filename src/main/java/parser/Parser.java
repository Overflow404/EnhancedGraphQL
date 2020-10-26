package parser;

import java.util.Map;

public interface Parser<T> {
    ParseResult<T> parse(Map<String, Object> expression);
}
