package aaa.dictionary.si;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.math.BigInteger;

@FieldDefaults(level = AccessLevel.PUBLIC, makeFinal = true)
@Getter
public enum NumberPrefix {

	y("yocto", -24),
	z("zepto", -21),
	a("atto", -18),
	f("femto", -15),
	p("pico", -12),
	n("nano", -9),
	u("micro", -6),
	m("milli", -3),
	c("centi", -2),
	d("deci", -1),
	da("deca", 1),
	h("hecto", 2),
	k("kilo", 3),
	M("Mega", 6),
	G("Giga", 9),
	T("Tera", 12),
	P("Peta", 15),
	E("Hexa", 18),
	Z("Zetta", 21),
	Y("Yotta", 24);

	String name;
	int power;
	BigInteger valueInteger;
	BigDecimal valueDecimal;

	NumberPrefix(String name, int power) {
		this.name = name;
		this.power = power;
		this.valueInteger = power < 0 ? null : BigInteger.TEN.pow(power);
		this.valueDecimal =
				power > 0 ? BigDecimal.TEN.pow(power) : BigDecimal.ONE.scaleByPowerOfTen(power);
	}

}
