package aaa.template;

import java.util.Map;

public interface ITemplateResolver {

  default String resolveTemplate(String message) {
    return resolveTemplate(message, null);
  }

  String resolveTemplate(String message, Map<String, Object> beans);

  ITemplateResolver DUMMY_RESOLVER = (message, beans) -> message;
}
