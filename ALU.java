class ALU {
	
	public double power(
		double base,
		long exponent
	) {
		double result;
		for (
			result = 1;
			exponent > 0;
			exponent >>= 1
		) {
			if (
				odd(exponent)
			) {
				result *= base;
			}
			base *= base;
		}
		return result;
	}
	
	public double factorial(
		double number
	) {
		if (number < 0 || number != (int) number) {
			return -1;
		}
		double result = 1;
		for (
			int x = 2;
			x <= (int) number;
			x++
		) {
			result *= x;
		}
		return result;
	}
	
	public double exponential(
		double number
	) {
		double result = 1;
		for (
			int x = 1;
			x <= 50;
			x++
		) {
			result += power(
				number, x
			) / factorial((double) x);
		}
		return result;
	}
	
	public double sigmoid(
		double number
	) {
		return 1 / (
			1 + exponential(
				-number
			)
		);
	}
	
	public double dsigmoid(
		double number
	) {
		double s = sigmoid(number);
		return s * (1 - s);
	}
	
	public double pdot(
		double[] a,
		double[] b
	) {
		int length = a.length;
		if (length == b.length) {
			double result = 0;
			for (int x = 0;
				x < length;
				x++
			) {
				result += a[x] * b[x];
			}
			return result;
		}
		return -1;
	}
	
	public double sum(
		double[] numbers
	) {
		int x = 0;
		double result = 0;
		int length = numbers.length;
		int limit = length - (length % 4);
		while (
			x < limit
		) {
			result += numbers[x++] +
				numbers[x++] +
				numbers[x++] +
				numbers[x++];
		}
		while (
			x < length
		) {
			result += numbers[x++];
		}
		return result;
	}
	
	public boolean odd(
		long number
	) {
		return (number & 1) == 1;
	}
	
}