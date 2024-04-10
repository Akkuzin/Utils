package aaa.language.ru.number;

import java.math.BigInteger;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor(staticName = "by")
@Builder
@FieldDefaults(level = AccessLevel.PUBLIC, makeFinal = true)
@Getter
public class PluralVariants {

  String end1;
  String end234;
  String endOther;

  public static PluralVariants by(String end1, String others) {
    return by(end1, others, others);
  }

  public String resolve(long number) {
    String result = endOther;
    int numberMod100 = (int) (Math.abs(number) % 100);
    if (numberMod100 <= 10 || numberMod100 >= 20) {
      int numberMod10 = numberMod100 % 10;
      result =
          switch (numberMod10) {
            case 1 -> end1;
            case 2, 3, 4 -> end234;
            default -> result;
          };
    }
    return result;
  }

  public String resolve(@NonNull BigInteger number) {
    return resolve(number.abs().remainder(BigInteger.valueOf(100L)).longValue());
  }
}
