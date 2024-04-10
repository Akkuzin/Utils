package aaa.i18n;

import static com.google.common.base.Strings.nullToEmpty;
import static org.apache.commons.lang3.StringUtils.defaultIfBlank;

import java.util.Locale;

public interface I18NResolver {

  // CHECKSTYLE:OFF
  default String R(String code, String defaultMessage) {
    return resolveCode(code, defaultMessage);
  }

  default String R(String code) {
    return R(code, null);
  }

  // CHECKSTYLE:ON

  String resolveCode(String code, String defaultMessage);

  default String resolveCode(String code) {
    return resolveCode(code, null);
  }

  default String getLocaleCode() {
    return toJavaLocale().getLanguage();
  }

  Locale toJavaLocale();

  I18NResolver DEFAULT_I18N =
      new I18NResolver() {

        public static final Locale RU = new Locale("ru");

        @Override
        public String resolveCode(String code, String defaultMessage) {
          return nullToEmpty(defaultIfBlank(defaultMessage, code));
        }

        @Override
        public Locale toJavaLocale() {
          return RU;
        }
      };
}
