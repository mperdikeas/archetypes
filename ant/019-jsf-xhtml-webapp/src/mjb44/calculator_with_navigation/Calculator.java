package mjb44.calculator_with_navigation;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.RequestScoped;
import java.util.Random;

@ManagedBean(name="calculator")
// @RequestScoped // you see bean created every time you call a method
@SessionScoped // you see a bean created anew after the browser has been idling for the number of minutes configured in <session-config>/<session-timeout> in web.xml
public class Calculator {
    private int left;
    private int right;
    private int result = 0;

    public Calculator() {
        Random random = new Random();
        int i = 0;
        int j = 0;
        do {
            i = random.nextInt(10);
        } while (i <= 4);

        do {
            j = random.nextInt(100);
        } while (j <= 20);

        left = i;
        right = j;
    }

    public int getLeft() {
        return left;
    }

    public void setLeft(int left) {
        this.left = left;
    }

    public int getRight() {
        return right;
    }

    public void setRight(int right) {
        this.right = right;
    }

    // Controller

    public String show() {
        result = left * right;
        return null;
    }

    public String clear() {
        result = 0;
        return null;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

}
