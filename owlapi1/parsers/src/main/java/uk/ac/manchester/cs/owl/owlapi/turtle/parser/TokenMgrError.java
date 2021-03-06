/* Generated By:JavaCC: Do not edit this line. TokenMgrError.java Version 5.0 */
/* JavaCCOptions: */
package uk.ac.manchester.cs.owl.owlapi.turtle.parser;

import org.semanticweb.owlapi.model.OWLRuntimeException;

@SuppressWarnings("all")
public class TokenMgrError extends OWLRuntimeException {

    private static final long serialVersionUID = 1L;
    static final int LEXICAL_ERROR = 0;
    static final int STATIC_LEXER_ERROR = 1;
    static final int INVALID_LEXICAL_STATE = 2;
    static final int LOOP_DETECTED = 3;
    int errorCode;

    protected static final String addEscapes(String str) {
        StringBuffer retval = new StringBuffer();
        char ch;
        for (int i = 0; i < str.length(); i++) {
            switch (str.charAt(i)) {
                case 0:
                    continue;
                case '\b':
                    retval.append("\\b");
                    continue;
                case '\t':
                    retval.append("\\t");
                    continue;
                case '\n':
                    retval.append("\\n");
                    continue;
                case '\f':
                    retval.append("\\f");
                    continue;
                case '\r':
                    retval.append("\\r");
                    continue;
                case '\"':
                    retval.append("\\\"");
                    continue;
                case '\'':
                    retval.append("\\\'");
                    continue;
                case '\\':
                    retval.append("\\\\");
                    continue;
                default:
                    if ((ch = str.charAt(i)) < 0x20 || ch > 0x7e) {
                        String s = "0000" + Integer.toString(ch, 16);
                        retval.append("\\u"
                                + s.substring(s.length() - 4, s.length()));
                    } else {
                        retval.append(ch);
                    }
                    continue;
            }
        }
        return retval.toString();
    }

    protected static String LexicalError(boolean EOFSeen, int lexState,
            int errorLine, int errorColumn, String errorAfter, char curChar) {
        return "Lexical error at line "
                + errorLine
                + ", column "
                + errorColumn
                + ".  Encountered: "
                + (EOFSeen ? "<EOF> " : "\""
                        + addEscapes(String.valueOf(curChar)) + "\"" + " ("
                        + (int) curChar + "), ") + "after : \""
                + addEscapes(errorAfter) + "\"";
    }

    public String getMessage() {
        return super.getMessage();
    }

    public TokenMgrError() {}

    public TokenMgrError(String message, int reason) {
        super(message);
        errorCode = reason;
    }

    public TokenMgrError(boolean EOFSeen, int lexState, int errorLine,
            int errorColumn, String errorAfter, char curChar, int reason) {
        this(LexicalError(EOFSeen, lexState, errorLine, errorColumn,
                errorAfter, curChar), reason);
    }
}
/* JavaCC - OriginalChecksum=89b7196d3c87f93f27e7844658172253 (do not edit this line) */
