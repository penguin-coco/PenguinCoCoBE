package com.penguin.penguincoco.lib.model;

import org.apache.commons.lang3.StringUtils;

public class CodeSimilarity {

    private SimilarityAlgorithm similarityAlgorithm;
    private Language language;


    public CodeSimilarity(Language language, SimilarityAlgorithm similarityAlgorithm) {
        this.language = language;
        this.similarityAlgorithm = similarityAlgorithm;
    }

    public double get(String a, String b) {

        if (StringUtils.isEmpty(a) || StringUtils.isEmpty(b)) {
            return 0;
        }
        else {
            switch (language) {
                case JAVA:
                    a = CodeUtils.removeJavaCommentsAndBlank(a);
                    b = CodeUtils.removeJavaCommentsAndBlank(b);
                    break;
                case PYTHON:
                    a = CodeUtils.removePythonCommentsAndBlank(a);
                    b = CodeUtils.removePythonCommentsAndBlank(b);
                    break;
            }
        }
        return similarityAlgorithm.get(a, b);
    }

    public void setSimilarityAlgorithm(SimilarityAlgorithm similarityAlgorithm) {
        this.similarityAlgorithm = similarityAlgorithm;
    }
}
