package com.shanghai.primary.data.model

/**
 * 学科类型；同时作为数据库枚举。
 */
enum class Subject { CHINESE, MATH, ENGLISH, GENERAL;
    val title: String
        get() = when (this) {
            CHINESE -> "语文"
            MATH -> "数学"
            ENGLISH -> "英语"
            GENERAL -> "常识"
        }
}
