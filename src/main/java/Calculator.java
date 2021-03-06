import java.util.Stack;

public class Calculator {

    private String preparingExpression(String expression)
    {
        String preparedExpression = "";

        for (int i = 0; i < expression.length(); i++)
        {
            char symbol = expression.charAt(i);
            if (symbol == '-')
            {
                if(i == 0)
                {
                    preparedExpression += '0';
                } else if (expression.charAt(i-1) == '(')
                {
                    preparedExpression += '0';
                }
            }
            preparedExpression += symbol;
        }
        return preparedExpression;
    }

    public String expressionToRPN(String expression)
    {
        String preparedExpression = preparingExpression(expression);
        String current = "";
        Stack<Character> stack = new Stack<>();

        int priority;

        for (int i = 0; i < preparedExpression.length(); i++)
        {
            priority = getPriority(preparedExpression.charAt(i));

            if (priority == 0) current+= preparedExpression.charAt(i);
            if (priority == 1) stack.push(preparedExpression.charAt(i));
            if (priority > 1)
            {
                current+=' ';
                while (!stack.empty())
                {
                    if(getPriority(stack.peek()) >= priority) current+= stack.pop();
                    else break;
                }
                stack.push(preparedExpression.charAt(i));
            }

            if( priority == -1)
            {
                current += ' ';
                while (getPriority(stack.peek()) != 1)
                {
                    current += stack.pop();
                }
                stack.pop();
            }



        }
        while (!stack.empty()) current+=stack.pop();


        return current;
    }

    public double rpnToAnswer(String rpn)
    {
        String operand = "";
        Stack<Double> stack = new Stack<>();

        for (int i = 0; i < rpn.length(); i++)
        {
            if (rpn.charAt(i) == ' ')continue;

            if (getPriority(rpn.charAt(i)) == 0 )
            {
                while (rpn.charAt(i) != ' ' && getPriority(rpn.charAt(i)) == 0)
                {
                    operand+=rpn.charAt(i++);
                    if (i == rpn.length())break;
                }

                stack.push(Double.parseDouble(operand));
                operand = "";

            }

            if (getPriority(rpn.charAt(i)) > 1)
            {
                double a = stack.pop() , b = stack.pop();

                if (rpn.charAt(i) == '+')stack.push(b+a);
                if (rpn.charAt(i) == '-')stack.push(b-a);
                if (rpn.charAt(i) == '*')stack.push(b*a);
                if (rpn.charAt(i) == '/')stack.push(b/a);

            }
        }

        return  stack.pop();
    }


    private int getPriority(char token){
        if (token == '*' || token == '/') return 3;
        else if (token == '+' || token == '-') return 2;
        else if (token == '(') return 1;
        else if (token == ')') return -1;
        else return 0;
    }


}
