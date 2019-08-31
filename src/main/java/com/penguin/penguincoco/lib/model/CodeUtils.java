package com.penguin.penguincoco.lib.model;

import org.apache.commons.lang3.StringUtils;

import java.util.stream.Collectors;

public class CodeUtils {

    public static String removeJavaCommentsAndBlank(String code) {
        return removeBlank(removeJavaComments(code));
    }

    public static String removePythonCommentsAndBlank(String code) {
        return removeBlank(removePythonComments(code));
    }

    // 去除Java程式中的所有註解
    public static String removeJavaComments(String code) {
        return CodeUtils.removeJavaSingleLineComments(CodeUtils.removeJavaBlockComments(code));
    }

    // 去除Java程式中的區塊註解
    private static String removeJavaBlockComments(String code) {
        StringBuilder newCode = new StringBuilder();
        int size = code.length();
        int flag = 0;

        for (int i = 0; i < size; i++) {
            if (code.charAt(i) == '/' && code.charAt(i + 1) == '*') {
                flag++;
            } else if (i > 0 && code.charAt(i - 1) == '*' && code.charAt(i) == '/') {
                flag--;
            } else if (flag == 0) {
                newCode.append(code.charAt(i));
            }
        }
        return newCode.toString();
    }

    // 去除Java程式中的單行註解
    private static String removeJavaSingleLineComments(String code) {
        StringBuilder newCode = new StringBuilder();
        int size = code.length();

        for (int i = 0; i < size; i++) {
            if (code.charAt(i) == '/' && code.charAt(i + 1) == '/') {
                for (++i; i < size; i++) {
                    String charString = String.valueOf(code.charAt(i));
                    if (StringUtils.LF.equals(charString) || StringUtils.CR.equals(charString)) {
                        break;
                    }
                }
            } else {
                newCode.append(code.charAt(i));
            }
        }
        return newCode.toString();
    }

    // 去除程式中的所有空白
    public static String removeBlank(String code) {
        if (StringUtils.isEmpty(code)) {
            return code;
        }
        return code.codePoints().filter(x -> !Character.isWhitespace(x)).mapToObj(x -> String.valueOf((char) x))
                .collect(Collectors.joining());
    }

    // 去除Python程式中的所有註解
    public static String removePythonComments(String code) {
        return CodeUtils.removePythonSingleLineComments(CodeUtils.removePythonBlockComments(code));
    }

    // 去除Python程式中的區塊註解
    private static String removePythonBlockComments(String code) {
        StringBuilder newCode = new StringBuilder();
        int size = code.length();
        int flag = 0;

        for (int i = 0; i < size; i++) {
            if (code.charAt(i) == '\'' && code.charAt(i + 1) == '\'' && code.charAt(i + 2) == '\'') {
                flag++;
                for(int j = i + 3; j < size; j++) {
                    if(j < size && code.charAt(j) == '\'' && code.charAt(j - 1) == '\'' && code.charAt(j - 2) == '\'') {
                        flag--;
                        i = j;
                        break;
                    }
                }
            }
            else if (flag == 0) {
                newCode.append(code.charAt(i));
            }
        }
        return newCode.toString();
    }

    // 去除Python程式中的單行註解
    private static String removePythonSingleLineComments(String code) {
        StringBuilder newCode = new StringBuilder();
        int size = code.length();

        for (int i = 0; i < size; i++) {
            if (code.charAt(i) == '#') {
                for (++i; i < size; i++) {
                    String charString = String.valueOf(code.charAt(i));
                    if (StringUtils.LF.equals(charString) || StringUtils.CR.equals(charString)) {
                        break;
                    }
                }
            } else {
                newCode.append(code.charAt(i));
            }
        }
        return newCode.toString();
    }
}
