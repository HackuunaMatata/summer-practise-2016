package forEjb;

import java.util.ArrayList;
import javax.ejb.Stateless;
import java.util.HashSet;

@Stateless
public class MyEJBean {
    private ArrayList<SymbolNumber> symnum;

    public MyEJBean() {symnum = new ArrayList();}

    public ArrayList<SymbolNumber> getSymnum() {
        return symnum;
    }

    public void setSymnum(ArrayList<SymbolNumber> symnum) {
        this.symnum = symnum;
    }

    public void fillIt(String message){

        SymbolNumber tempSymNum = null;

        for(int i = 0; i < message.length(); i++){
            tempSymNum = new SymbolNumber(message.charAt(i),1);
            
            if (symnum.indexOf(tempSymNum) == -1) {
                symnum.add(tempSymNum);
            }
            else {
                symnum.get(symnum.indexOf(tempSymNum)).incNumber();
            }
        }
    }

}
