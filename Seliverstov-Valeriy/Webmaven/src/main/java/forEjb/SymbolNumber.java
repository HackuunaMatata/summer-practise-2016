package forEjb;

/**
 * Created by a1 on 05.07.16.
 */
public class SymbolNumber {
    private char symbol;
    private int number;

    public SymbolNumber(char symbol, int number) {
        this.symbol = symbol;
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public char getSymbol() {
        return symbol;
    }

    public void setSymbol(char symbol) {
        this.symbol = symbol;
    }

    public void incNumber(){
        number++;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SymbolNumber that = (SymbolNumber) o;

        if (getSymbol() != that.getSymbol()) return false;
        return true;

    }

    @Override
    public int hashCode() {
        int result = (int) getSymbol();
        result = 31 * result + getNumber();
        return result;
    }
}
