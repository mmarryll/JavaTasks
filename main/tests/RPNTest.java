package MiniTasks;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.StringTokenizer;

public class Parser {
    private static String operators = "+-*/";
    private static String delimiters;
    public static boolean flag;

    public Parser() {
    }

    private static boolean isDelimiter(String token) {
        if (token.length() != 1) {
            return false;
        } else {
            for(int i = 0; i < delimiters.length(); ++i) {
                if (token.charAt(0) == delimiters.charAt(i)) {
                    return true;
                }
            }

            return false;
        }
    }

    private static boolean isOperator(String token) {
        if (token.equals("u-")) {
            return true;
        } else {
            for(int i = 0; i < operators.length(); ++i) {
                if (token.charAt(0) == operators.charAt(i)) {
                    return true;
                }
            }

            return false;
        }
    }

    private static boolean isFunction(String token) {
        return token.equals("sqrt") || token.equals("cube") || token.equals("pow10");
    }

    private static int priority(String token) {
        if (token.equals("(")) {
            return 1;
        } else if (!token.equals("+") && !token.equals("-")) {
            return !token.equals("*") && !token.equals("/") ? 4 : 3;
        } else {
            return 2;
        }
    }

    public static List<String> parse(String infix) {
        List<String> postfix = new ArrayList();
        Deque<String> stack = new ArrayDeque();
        StringTokenizer tokenizer = new StringTokenizer(infix, delimiters, true);
        String prev = "";
        String curr = "";

        while(true) {
            do {
                if (!tokenizer.hasMoreTokens()) {
                    while(!stack.isEmpty()) {
                        if (!isOperator((String)stack.peek())) {
                            System.out.println("Скобки не согласованы.");
                            flag = false;
                            return postfix;
                        }

                        postfix.add((String)stack.pop());
                    }

                    return postfix;
                }

                curr = tokenizer.nextToken();
                if (!tokenizer.hasMoreTokens() && isOperator(curr)) {
                    System.out.println("Некорректное выражение.");
                    flag = false;
                    return postfix;
                }
            } while(curr.equals(" "));

            if (isFunction(curr)) {
                stack.push(curr);
            } else if (isDelimiter(curr)) {
                if (curr.equals("(")) {
                    stack.push(curr);
                } else if (curr.equals(")")) {
                    while(!((String)stack.peek()).equals("(")) {
                        postfix.add((String)stack.pop());
                        if (stack.isEmpty()) {
                            System.out.println("Скобки не согласованы.");
                            flag = false;
                            return postfix;
                        }
                    }

                    stack.pop();
                    if (!stack.isEmpty() && isFunction((String)stack.peek())) {
                        postfix.add((String)stack.pop());
                    }
                } else {
                    if (curr.equals("-") && (prev.equals("") || isDelimiter(prev) && !prev.equals(")"))) {
                        curr = "u-";
                    } else {
                        while(!stack.isEmpty() && priority(curr) <= priority((String)stack.peek())) {
                            postfix.add((String)stack.pop());
                        }
                    }

                    stack.push(curr);
                }
            } else {
                postfix.add(curr);
            }

            prev = curr;
        }
    }

    static {
        delimiters = "() " + operators;
        flag = true;
    }
}

