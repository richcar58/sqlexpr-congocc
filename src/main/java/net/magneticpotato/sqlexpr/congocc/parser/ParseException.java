/* Generated by: CongoCC Parser Generator. ParseException.java  */
package net.magneticpotato.sqlexpr.congocc.parser;

import java.util.*;


public class ParseException extends RuntimeException {
    // The token we tripped up on.
    private Node.TerminalNode token;
    //We were expecting one of these token types
    private Set<? extends Node.NodeType> expectedTypes;
    private List<NonTerminalCall> callStack;
    private boolean alreadyAdjusted;

    private void setInfo(Node.TerminalNode token, Set<? extends Node.NodeType> expectedTypes, List<NonTerminalCall> callStack) {
        if (token != null && !token.getType().isEOF() && token.getNext() != null) {
            token = token.getNext();
        }
        this.token = token;
        this.expectedTypes = expectedTypes;
        this.callStack = new ArrayList<>(callStack);
    }

    public boolean hitEOF() {
        return token != null && token.getType().isEOF();
    }

    public ParseException(Node.TerminalNode token, Set<? extends Node.NodeType> expectedTypes, List<NonTerminalCall> callStack) {
        setInfo(token, expectedTypes, callStack);
    }

    public ParseException(Node.TerminalNode token) {
        this.token = token;
    }

    public ParseException() {
    }

    // Needed because of inheritance
    public ParseException(String message) {
        super(message);
    }

    public ParseException(String message, List<NonTerminalCall> callStack) {
        super(message);
        this.callStack = callStack;
    }

    public ParseException(String message, Node.TerminalNode token, List<NonTerminalCall> callStack) {
        super(message);
        this.token = token;
        this.callStack = callStack;
    }

    @Override
    public String getMessage() {
        String msg = super.getMessage();
        if (token == null && expectedTypes == null) {
            return msg;
        }
        StringBuilder buf = new StringBuilder();
        if (msg != null) buf.append(msg);
        String location = token != null ? token.getLocation() : "";
        buf.append("\nEncountered an error at (or somewhere around) " + location);
        if (expectedTypes != null && token != null && expectedTypes.contains(token.getType())) {
            return buf.toString();
        }
        if (expectedTypes != null) {
            buf.append("\nWas expecting one of the following:\n");
            boolean isFirst = true;
            for (Node.NodeType type : expectedTypes) {
                if (!isFirst) buf.append(", ");
                isFirst = false;
                buf.append(type);
            }
        }
        String content = token.toString();
        if (content == null) content = "";
        if (content.length() > 32) content = content.substring(0, 32) + "...";
        buf.append("\nFound string \"" + addEscapes(content) + "\" of type " + token.getType());
        return buf.toString();
    }

    @Override
    public StackTraceElement[] getStackTrace() {
        adjustStackTrace();
        return super.getStackTrace();
    }

    @Override
    public void printStackTrace(java.io.PrintStream s) {
        adjustStackTrace();
        super.printStackTrace(s);
    }

    /**
    * Returns the token which causes the parse error and null otherwise.
    * @return the token which causes the parse error and null otherwise.
    */
    public Node.TerminalNode getToken() {
        return token;
    }

    private void adjustStackTrace() {
        if (alreadyAdjusted || callStack == null || callStack.isEmpty()) return;
        List<StackTraceElement> fullTrace = new ArrayList<>();
        List<StackTraceElement> ourCallStack = new ArrayList<>();
        for (NonTerminalCall ntc : callStack) {
            ourCallStack.add(ntc.createStackTraceElement());
        }
        StackTraceElement[] jvmCallStack = super.getStackTrace();
        for (StackTraceElement regularEntry : jvmCallStack) {
            if (ourCallStack.isEmpty()) break;
            String methodName = regularEntry.getMethodName();
            StackTraceElement ourEntry = lastElementWithName(ourCallStack, methodName);
            if (ourEntry != null) {
                fullTrace.add(ourEntry);
            }
            fullTrace.add(regularEntry);
        }
        StackTraceElement[] result = new StackTraceElement[fullTrace.size()];
        setStackTrace(fullTrace.toArray(result));
        alreadyAdjusted = true;
    }

    private StackTraceElement lastElementWithName(List<StackTraceElement> elements, String methodName) {
        for (ListIterator<StackTraceElement> it = elements.listIterator(elements.size()); it.hasPrevious();) {
            StackTraceElement elem = it.previous();
            if (elem.getMethodName().equals(methodName)) {
                it.remove();
                return elem;
            }
        }
        return null;
    }

    private static String addEscapes(String str) {
        StringBuilder retval = new StringBuilder();
        for (int ch : str.codePoints().toArray()) {
            switch(ch) {
                case '\b' : 
                    retval.append("\\b");
                    continue;
                case '\t' : 
                    retval.append("\\t");
                    continue;
                case '\n' : 
                    retval.append("\\n");
                    continue;
                case '\f' : 
                    retval.append("\\f");
                    continue;
                case '\r' : 
                    retval.append("\\r");
                    continue;
                case '\"' : 
                    retval.append("\\\"");
                    continue;
                case '\'' : 
                    retval.append("\\\'");
                    continue;
                case '\\' : 
                    retval.append("\\\\");
                    continue;
                default : 
                    if (Character.isISOControl(ch)) {
                        String s = "0000" + java.lang.Integer.toString(ch, 16);
                        retval.append("\\u" + s.substring(s.length() - 4));
                    } else {
                        retval.appendCodePoint(ch);
                    }
                    continue;
            }
        }
        return retval.toString();
    }

}


