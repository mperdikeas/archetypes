package gr.neuropublic.persutil;

import java.util.Stack;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.Logger;

import mutil.base.Pair;

public class JPQLBuilder {
    private Stack<IJPQLQueryComponent> jpqlComponents;
    private final Logger l = Logger.getLogger(this.getClass().getName());

    private String debugInfo() {
        StringBuilder sb = new StringBuilder();
        Iterator it = jpqlComponents.iterator();
        for (IJPQLQueryComponent component : jpqlComponents)
            sb.append("[["+component.queryCompNoParameter()+"]]\n");
        return sb.toString();
    }

    public JPQLBuilder() {
        this.jpqlComponents = new Stack();
    }

    public Class getSelectClass() {
        return ( (SelectComponent) jpqlComponents.firstElement()).getKlass();
    }

    public String getAlias() {
        return ( (SelectComponent) jpqlComponents.firstElement()).getAlias();
    }

    public void push(IJPQLQueryComponent component) {
        if ((component instanceof AndComponent) && ( ! (jpqlComponents.peek() instanceof IIsComponent)))
            ; // do not push an And component if the previous is not an IIsComponent
        else {
            // pop the WhereComponent if there is nothing else between the WhereComponent and the EndComponent
            if ((component instanceof EndComponent)) {
                if (jpqlComponents.peek() instanceof WhereComponent)
                    jpqlComponents.pop();
            }
            if ((jpqlComponents.isEmpty()) && (!(component instanceof SelectComponent))) throw new RuntimeException();
            jpqlComponents.push(component);
            checkForElimination();
        }
    }

    private void checkForElimination() {
        l.info("checkForElimination called");
        l.info(debugInfo());
        IJPQLQueryComponent top = jpqlComponents.peek();
        if (top instanceof IsComponent) {
            IsComponent is = (IsComponent) top;
            if ((is.getValue()==null) || is.getValue().trim().equals("")) {
                jpqlComponents.pop(); // pop the value (that's an IIsComponent)
                jpqlComponents.pop(); // pop the field component
                if (!(jpqlComponents.peek() instanceof WhereComponent)) {
                    if (!(jpqlComponents.peek() instanceof AndComponent)) throw new RuntimeException();
                    else  jpqlComponents.pop(); // pop the 'and'
                } else ; // do nothing now, we'll pop the 'where' in the end - if we have to refid:234lpodas89f2
            }
        }
        else if (top instanceof IsBooleanComponent) {
            IsBooleanComponent is = (IsBooleanComponent) top;
            if (is.getValue() == null) {
                jpqlComponents.pop(); // value
                jpqlComponents.pop(); // field
                if (!(jpqlComponents.peek() instanceof WhereComponent)) {
                    if (!(jpqlComponents.peek() instanceof AndComponent)) throw new RuntimeException();
                    else  jpqlComponents.pop(); // pop the 'and'
                } else ; // see refid:234lpodas89f2
            }
        }
        else if (top instanceof BeforeComponent) {
            BeforeComponent bc = (BeforeComponent) top;
            if (bc.getValue()==null) {
                jpqlComponents.pop(); // value
                jpqlComponents.pop(); // field
                if (!(jpqlComponents.peek() instanceof WhereComponent)) {
                    if (!(jpqlComponents.peek() instanceof AndComponent)) {
                        l.info("information on the offending component:\n");
                        l.info(jpqlComponents.peek().queryCompNoParameter());
                        throw new RuntimeException();
                    }
                    else  jpqlComponents.pop(); // and-joint    
                } else ; // see refid:234lpodas89f2
            }
        }
        else if (top instanceof AfterComponent) {
            AfterComponent bc = (AfterComponent) top;
            if (bc.getValue()==null) {
                jpqlComponents.pop(); // value
                jpqlComponents.pop(); // field
                if (!(jpqlComponents.peek() instanceof WhereComponent)) {
                    if (!(jpqlComponents.peek() instanceof AndComponent)) throw new RuntimeException();
                    else  jpqlComponents.pop(); // and-joint    
                } else ; // see refid:234lpodas89f2
            }
        }
    }

 
    public Pair<String, Map<String, Object>> getQuery() {
        if (!(jpqlComponents.peek() instanceof EndComponent)) throw new RuntimeException();
        else {
            Stack<IJPQLQueryComponent> jpqlComponentsQueryOrder = reverse(jpqlComponents);
            Map<String, Object> parameters = new HashMap<String, Object>();
            StringBuilder sb = new StringBuilder();
            while (!jpqlComponentsQueryOrder.empty()) {
                Pair<String, Pair<String, Object>> queryComponent_and_parameter = jpqlComponentsQueryOrder.pop().queryCompAndParameter();
                sb.append(queryComponent_and_parameter.a);
                if (queryComponent_and_parameter.b != null)
                    parameters.put(queryComponent_and_parameter.b.a, queryComponent_and_parameter.b.b);
            }
            String retValue = sb.toString();
            l.info("JPQL string returned is: "+retValue);
            return Pair.create(retValue, parameters);
        }
    }

    public static <T> Stack<T> reverse(Stack<T> s) {
        Stack<T> retValue = new Stack();
        while (!s.empty())
            retValue.push(s.pop());
        return retValue;
    }

}