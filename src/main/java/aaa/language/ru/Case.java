package aaa.language.ru;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@FieldDefaults(level = AccessLevel.PUBLIC, makeFinal = true)
@Getter
@SuppressWarnings("nls")
public enum Case {

	NOMINATIVE("Именительный", "Кто? Что?"),
	GENITIVE("Родительный", "Кого? Чего?"),
	DATIVE("Дательный", "Кому? Чему?"),
	ACCUSATIVE("Винительный", "Кого? Что?"),
	INSTRUMENTATIVE("Творительный", "Кем? Чем?"),
	PREPOSITIONAL("Предложный", "О ком? О чём?"),
	VOCATIVE("Звательный", ""),
	PARTITIVE("Количественно-отделительный", ""),
	LOCATIVE("Местный", ""),
	ABLATIVE("Исходный", "");

	String name;
	String question;

}
