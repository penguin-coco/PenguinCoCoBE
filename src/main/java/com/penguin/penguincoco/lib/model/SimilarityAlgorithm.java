package com.penguin.penguincoco.lib.model;

public interface SimilarityAlgorithm {

    // 取得程式碼之間的相似度
    double get(String a, String b);
}
