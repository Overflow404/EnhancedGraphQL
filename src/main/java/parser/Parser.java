package parser;

import java.util.Map;

public interface Parser<T> {
    ParserResult<T> parse(Map<String, Object> expression);
}
