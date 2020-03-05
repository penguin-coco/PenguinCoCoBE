package com.penguin.penguincoco.lib.model;

public enum JudgeSymbol implements JudgeSymbolMessage {

    AC {
        public String getDescription() {
            return "AC (Accept):即表示通過";
        }
        public String toString() {
            return "AC (Accept)";
        }
    },
    NA {
        public String getDescription() {
            return "NA (Not Accept):在多測資點的題目中若未通過所有測資點則出現NA";
        }
        public String toString() {
            return "NA (Not Accept)";
        }
    },
    WA {
        public String getDescription() {
            return "WA (Wrong Answer):你的程式輸出結果與輸出解答不符呦！\n" +
                    "\n" +
                    "可以遵照以下幾個方法，檢視看看是哪裡錯誤：\n" +
                    "1. 檢查是否有數學公式寫錯\n" +
                    "2. 檢查是否有變數給值錯誤，可以將值打印出來檢查\n" +
                    "3. 檢查是否有程式邏輯錯誤";
        }
        public String toString() {
            return "WA (Wrong Answer)";
        }
    },
    TLE {
        public String getDescription() {
            return "TLE (Time Limit Exceed):表示執行超過時間限制";
        }
        public String toString() {
            return "TLE (Time Limit Exceed)";
        }
    },
    MLE {
        public String getDescription() {
            return "MLE (Memory Limit Exceed):表示程序執行超過記憶體限制";
        }
        public String toString() {
            return "MLE (Memory Limit Exceed)";
        }
    },
    OLE {
        public String getDescription() {
            return "OLE (Output Limit Exceed):表示程序輸出檔超過限制";
        }
        public String toString() {
            return "OLE (Output Limit Exceed)";
        }
    },
    RE {
        public String getDescription() {
            return "RE (Runtime Error):\n" +
                    "你的程式在執行階段發生錯誤！\n" +
                    "\n" +
                    "可以參考以下除錯建議，檢視看看是哪裡錯誤：\n" +
                    "1. 檢查是否有數學錯誤。如除0\n" +
                    "2. 檢查是否有未知符號。如分號\n" +
                    "3. 檢查是否有遺漏括弧\n" +
                    "4. 檢查是否在條件句時，尾末有加上冒號，以執行條件程式\n" +
                    "5. 檢查是否有語法錯誤";
        }
        public String toString() {
            return "RE (Runtime Error)";
        }
    },
    RF {
        public String getDescription() {
            return "RF (Restricted Function):表示使用了被禁止使用的函式";
        }
        public String toString() {
            return "RF (Restricted Function)";
        }
    },
    CE {
        public String getDescription() {
            return "CE (Compile Error):表示編譯錯誤";
        }
        public String toString() {
            return "CE (Compile Error)";
        }
    },
    SE {
        public String getDescription() {
            return "SE (System Error):未定義錯誤均屬於System Error";
        }
        public String toString() {
            return "SE (System Error)";
        }
    }
}
