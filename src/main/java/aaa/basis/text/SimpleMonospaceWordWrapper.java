package aaa.basis.text;

import static java.util.stream.Collectors.joining;
import static org.apache.commons.lang3.StringUtils.split;
import static org.apache.commons.lang3.StringUtils.substring;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.StringUtils;

/** Splits text into lines, with max line length split */
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
@Builder
public class SimpleMonospaceWordWrapper implements Function<String, List<String>> {

  String separatorChars;
  String delimiter;
  int maxLength;

  private String join(List<String> lineBuffer) {
    return lineBuffer.stream().filter(StringUtils::isNotBlank).collect(joining(delimiter));
  }

  @Override
  public List<String> apply(String input) {
    if (input == null) {
      return null;
    }
    List<String> result = new ArrayList<>();
    List<String> lineBuffer = new ArrayList<>();
    int currentLength = 0;
    int delimiterLength = delimiter.length();
    for (String word : split(input, separatorChars)) {
      if (maxLength - currentLength < word.length()) {
        // Уже не помещается в буфер
        if (currentLength == 0) {
          int base = 0;
          // Делим длинное слово на строки максимальной длины
          for (int i = word.length() / maxLength; i > 0; --i) {
            result.add(word.substring(base, base + maxLength));
            base += maxLength;
          }
          // Остаток слова добавляем в буфер
          // CHECKSTYLE:OFF
          word = substring(word, base);
          // CHECKSTYLE:ON
        } else {
          currentLength = 0;
          result.add(String.join(delimiter, lineBuffer));
          lineBuffer.clear();
        }
      }
      // Добавляем к буферу
      currentLength += word.length() + delimiterLength;
      lineBuffer.add(word);
    }
    if (!lineBuffer.isEmpty()) {
      result.add(String.join(delimiter, lineBuffer));
    }
    return result;
  }
}
