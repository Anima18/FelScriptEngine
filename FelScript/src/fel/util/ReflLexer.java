package fel.util;

import fel.script.Field;
import fel.script.ScriptVar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReflLexer {
    private ReflLexer reflLexer;

    private Map<String, String> hitWorldMap = new HashMap<>();
    private static Map<Character, Character> ignoreSymbol;

    static {
        //hitWorldMap = new HashMap<>();
        ignoreSymbol = new HashMap<>();
        /*hitWorldMap.put("G(", "G");
        hitWorldMap.put("H(", "H");
        hitWorldMap.put("Z(", "Z");
        hitWorldMap.put("Y(", "Y");
        hitWorldMap.put("close(", "close");
        hitWorldMap.put("low(", "low");
        hitWorldMap.put("high(", "high");*/

        ignoreSymbol.put('(', '(');
        ignoreSymbol.put(')', ')');
        ignoreSymbol.put('+', '+');
        ignoreSymbol.put('-', '-');
        ignoreSymbol.put('*', '*');
        ignoreSymbol.put('/', '/');
        ignoreSymbol.put('%', '%');
        ignoreSymbol.put(',', ',');
        ignoreSymbol.put('=', '=');
        ignoreSymbol.put('>', '>');
        ignoreSymbol.put('<', '<');
    }
    public ReflLexer() {}

    public ReflLexer(Map<String, Field> dataSource, Map<String, ScriptVar> varMap) {
        for (String key : dataSource.keySet()) {
            hitWorldMap.put(key+"(", key);
        }

        for(String key : varMap.keySet()) {
            hitWorldMap.put(key+"(", key);
        }
    }

    private List<Word> scan(String expression) {
        List<Word> words = new ArrayList<>();
        char[] chars = expression.toCharArray();

        String word = "";
        for(int i = 0; i < chars.length; i++) {

            word += chars[i];
            if (hitWorldMap.containsKey(word)) {
                int lastIndex = expression.indexOf(")", i);
                String param = expression.substring(i+1, lastIndex);
                words.add(new Word(i, lastIndex-i, hitWorldMap.get(word), param));
                word = "";
            } else if (ignoreSymbol.containsKey(chars[i])) {
                word = "";
            }
        }
        return words;
    }

    public String replace(String expression) {
        expression = expression.replaceAll("\\s*", "");
        StringBuilder builder = new StringBuilder();
        List<Word> words = scan(expression);

        int expressionIndex = 0;
        for(int i = 0; i < words.size(); i++) {
            Word word = words.get(i);
            builder.append(expression.substring(expressionIndex, word.index-word.reflCode.length()));
            expressionIndex = word.index+word.offset+1;
            builder.append(String.format("REFL(%s,%s)", word.reflCode, word.reflParam));
            if(i == words.size()-1) {
                builder.append(expression.substring(expressionIndex));
            }
        }
        if("".equals(builder.toString())) {
            return expression;
        }else {
            return builder.toString();
        }
    }


    class Word {
        public int index;
        public int offset;
        public String reflCode;
        public String reflParam;

        public Word(int index, int offset, String reflCode, String reflParam) {
            this.index = index;
            this.offset = offset;
            this.reflCode = reflCode;
            this.reflParam = reflParam;
        }

        @Override
        public String toString() {
            return "Word{" +
                    "index=" + index +
                    ", offset=" + offset +
                    ", reflCode='" + reflCode + '\'' +
                    ", reflParam='" + reflParam + '\'' +
                    '}';
        }
    }

    public static void main(String[] args) {
        ReflLexer lexer = new ReflLexer();
        String expression = "IF(Y(i)=='多', (low(i)-Z(i))/Z(i), IF(Y(i)=='空', (Z(i)-high(i))/Z(i), ''))";
        String newExpression = lexer.replace(expression);
        System.out.println(expression);
        System.out.println(newExpression);
    }
}
