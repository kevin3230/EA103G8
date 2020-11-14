package utilities;

public class GetRandom {

	public StringBuilder genRandom(int len) {
		StringBuilder sb = new StringBuilder();
		int ran;
		char code;
		for (int i = 0; i < len; i++) {
			while (true) {
				ran = (int) (Math.random() * 123);
				if ((ran >= 48 && ran <= 57) || (ran >= 65 && ran <= 90) || (ran >= 97 && ran <= 122))
					break;
			}
			code = (char) ran;
			sb.append(code);
		}
		return sb;
	}

	public StringBuilder genRandomnumber(int len) {
		StringBuilder sb = new StringBuilder();
		int ran;
		char code;
		for (int i = 0; i < len; i++) {
			while (true) {
				ran = (int) (Math.random() * 58);
				if ((ran >= 48 && ran <= 57))
					break;
			}
			code = (char) ran;
			sb.append(code);
		}
		return sb;
	}

	public StringBuilder genRandomEn(int len) {
		StringBuilder sb = new StringBuilder();
		int ran;
		char code;
		for (int i = 0; i < len; i++) {
			while (true) {
				ran = (int) (Math.random() * 123);
				if ((ran >= 65 && ran <= 90) || (ran >= 97 && ran <= 122))
					break;
			}
			code = (char) ran;
			sb.append(code);
		}
		return sb;
	}

	public StringBuilder genRandomword(int len) {
		StringBuilder sb = new StringBuilder();
		int ran;
		char code;
		for (int i = 0; i < len; i++) {
			while (true) {
				ran = (int) (Math.random() * 40870);
				if ((ran >= 65 && ran <= 90) || (ran >= 97 && ran <= 122) || (ran >= 19968 && ran <= 40869))
					break;
			}
			code = (char) ran;
			sb.append(code);
		}
		return sb;
	}
}
