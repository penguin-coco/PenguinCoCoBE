package com.penguin.penguincoco.lib.model;

import java.text.DecimalFormat;

public class LCSAlgorithm implements SimilarityAlgorithm {

    public LCSAlgorithm() {

    }

    @Override
    public double get(String a, String b) {
        DecimalFormat format = new DecimalFormat("#.##");
        String result = format.format(2.0 * getLCS(a, b) / (a.length() + b.length()));
        return Double.parseDouble(result);
    }

    private int getLCS(String a, String b) {
        int aLength = a.length();
        int bLength = b.length();
        int[][] dp = new int[aLength + 1][bLength + 1];

        for (int i = 1; i <= aLength; i++) {
            for (int j = 1; j <= bLength; j++) {
                if (a.charAt(i - 1) == b.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i][j - 1], dp[i - 1][j]);
                }
            }
        }
        return dp[aLength][bLength];
    }
}
