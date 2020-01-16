

import java.util.*;

/**
 *
 * @author rinzmaria
 */
public class ExpressionCalculator{



    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

       
        String expression = args[0];
        ExpressionCalculator j = new ExpressionCalculator();
        System.out.println(j.Calculator(expression));
		
    }

	    public void ExpressionCalculator() {
       
    }
        public int Calculator(String expr) {
			 int output = 0;
         //output is the final result
        String actionIgnoreCase;
        //check if the input is simple numbers or  expressions
        //if input has brackets, then it is an expression
        if(expr.contains("(") && expr.contains(")") )
        {
            //countBrackets has the count of brackets.
            int countBrackets = getCount(expr,'(');
            //when countBrackets>1, there are nested expressions
            if(countBrackets> 1)
            {
                while(countBrackets > 0)
                {
                    StringBuffer buff = new StringBuffer(expr);
                    int tempResult;
                    //substring between beginBracket and endBracket 
                    //gives the first inner expression
                    int beginBracket = getMaxPosition(expr,'(');
                    int endBracket = (expr.indexOf(")"));
                    String newExpr = expr.substring(beginBracket,
                                                   (endBracket+1));
                    //action is either Add or Multiply
                    String action = newExpr.substring((newExpr.indexOf("(")+1), 
                                                       newExpr.indexOf(" "));
                    actionIgnoreCase = action.toUpperCase();
                    //call ExtractFunction to evaluate the expression.
                    //tempResult has the result after evaluating the expression
                    tempResult = ExtractFunction(newExpr,actionIgnoreCase);
                    //Replace the inner expression with result
                    String res = Integer.toString(tempResult);
                    buff.delete(beginBracket, (endBracket+1));
                    buff = buff.insert(beginBracket, res);
                    countBrackets = countBrackets -1;
                    expr = buff.toString();
                    //continue the loop for next [inner] expression
                }
                output = Integer.parseInt(expr);
            }
            //no nested expressions
            else if (countBrackets == 1)
            {
                String action = expr.substring((expr.indexOf("(")+1), 
                                                expr.indexOf(" "));
                actionIgnoreCase = action.toUpperCase();
                output = ExtractFunction(expr,actionIgnoreCase);
            }
        }
        //input is an integer
        else
        {
          output = Integer.parseInt(expr);
        }
        return output;
               
    }
    private int ExtractFunction(String expr, String actionIgnoreCase) {
        int result = 0;
        //get the count of spaces
        int countSpace = getCount(expr,' ');
        //pos array stores the position/index of spaces
        int pos[] =  new int[countSpace];
        //values array stores the sub-expressions
        int values[] =  new int[countSpace];
        int n=0;
        for(int i=0; i< expr.length(); i++)
        {
            if(expr.charAt(i)==' ')
            {
                pos[n] = i;
                n++;
            }
        }
        n =0;
        String num;
        //iterate through expression based on index from pos[] to extract the 
        //sub-expressions
        for(int i=0; i<(values.length-1); i++)
        {
                num = expr.substring((pos[i]+1),pos[i+1] ); 
                values[n] = Integer.parseInt(num);
                n++;
                countSpace = countSpace-1;
                if(countSpace == 1)
                {
                    num = expr.substring((expr.lastIndexOf(' ')+1),
                                          expr.indexOf(")") );
                    values[n] = Integer.parseInt(num);
                }
        }
        if(actionIgnoreCase.equals("ADD"))
        {

            for(int i=0; i<values.length; i++) 
            {
                   result = result + values[i];
            }
        }
        if(actionIgnoreCase.equals("MULTIPLY"))
        {
            result = 1;
            for(int i=0; i<values.length; i++) 
            {
                
                   result = result * values[i];
            }
        }      
    return result;
    }

    private int getCount(String expr, char bracket) {
        int count = 0;
        for (int i = 0; i<expr.length(); i++)
        {
            if(expr.charAt(i)== bracket)
            {
                count++;
            }
        }

        return count;

    }

    private int getMaxPosition(String expr, char c) {
        int maxPos = 0;
        for (int i = 0; i<expr.length(); i++)
        {
            if(expr.charAt(i)== c && (maxPos<= i ))
            {
                maxPos = i;
            }
        }
        return maxPos;
    }
    
}