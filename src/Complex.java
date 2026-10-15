public class Complex {
    double re;
    double im;

    Complex(double re, double im) {
        this.re = re;
        this.im = im;
    }

    Complex plus(Complex other) {
        return new Complex(this.re + other.re, this.im + other.im);
    }

    Complex minus(Complex other) {
        return new Complex(this.re - other.re, this.im - other.im);
    }

    Complex times(Complex other) {
        double newRe = this.re * other.re - this.im * other.im;
        double newIm = this.re * other.im + this.im * other.re;
        return new Complex(newRe, newIm);
    }

    Complex div(Complex other) {
        double denom = other.re * other.re + other.im * other.im;
        if (denom == 0) {
            throw new ArithmeticException("Деление на ноль");
        }
        double newRe = (this.re * other.re + this.im * other.im) / denom;
        double newIm = (this.im * other.re - this.re * other.im) / denom;
        return new Complex(newRe, newIm);
    }

    boolean isZero() {
        return re == 0 && im == 0;
    }

    @Override
    public String toString() {
        if (im == 0) return String.format("%.2f", re);
        if (re == 0) return String.format("%.2fi", im);
        if (im > 0)  return String.format("%.2f+%.2fi", re, im);
        else         return String.format("%.2f%.2fi", re, im);
    }
}