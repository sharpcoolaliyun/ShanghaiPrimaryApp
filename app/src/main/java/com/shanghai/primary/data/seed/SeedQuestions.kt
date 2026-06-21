package com.shanghai.primary.data.seed

import com.shanghai.primary.data.model.Question
import com.shanghai.primary.data.model.Subject

/**
 * 上海小学 1-2 年级题库种子数据。
 *
 * 涵盖：
 *  - 语文：拼音 + 识字 + 古诗 + 看图选字
 *  - 数学：10/20 以内加减法、凑十法、看图计数、认识钟表
 *  - 英语：人教版/上海牛津版常用单词 + 反义词
 *  - 常识：生活、安全、自然
 *
 * 答案用选项位置 (0..3) 表示；选项不足时剩余填空。
 */
object SeedQuestions {

    fun all(): List<Question> = chinese() + math() + english() + general()

    private fun chinese(): List<Question> = listOf(
        // 一年级 - 识字
        Question(subject = Subject.CHINESE, grade = 1,
            prompt = "下面哪个字和「日」长得像？",
            optionA = "月", optionB = "山", optionC = "水", optionD = "木",
            answerIndex = 0, imageEmoji = "☀️"),
        Question(subject = Subject.CHINESE, grade = 1,
            prompt = "「人」字一撇一捺合起来像什么？",
            optionA = "站立的人", optionB = "山", optionC = "门", optionD = "鸟",
            answerIndex = 0, imageEmoji = "🚶"),
        Question(subject = Subject.CHINESE, grade = 1,
            prompt = "《静夜思》的作者是谁？",
            optionA = "李白", optionB = "杜甫", optionC = "白居易", optionD = "王维",
            answerIndex = 0, imageEmoji = "🌙",
            hint = "唐代大诗人，号青莲居士"),
        Question(subject = Subject.CHINESE, grade = 1,
            prompt = "「床前明月光」的下一句是？",
            optionA = "疑是地上霜", optionB = "举头望明月", optionC = "低头思故乡", optionD = "夜来风雨声",
            answerIndex = 0, imageEmoji = "🌙"),
        Question(subject = Subject.CHINESE, grade = 1,
            prompt = "下面哪个不是水果？",
            optionA = "苹果", optionB = "香蕉", optionC = "椅子", optionD = "葡萄",
            answerIndex = 2, imageEmoji = "🍎"),
        Question(subject = Subject.CHINESE, grade = 1,
            prompt = "「大」的相反字是？",
            optionA = "小", optionB = "多", optionC = "少", optionD = "长",
            answerIndex = 0, imageEmoji = "↔️"),

        // 二年级 - 课文常识
        Question(subject = Subject.CHINESE, grade = 2,
            prompt = "「锄禾日当午」下一句？",
            optionA = "汗滴禾下土", optionB = "粒粒皆辛苦", optionC = "谁知盘中餐", optionD = "春种一粒粟",
            answerIndex = 0, imageEmoji = "🌾"),
        Question(subject = Subject.CHINESE, grade = 2,
            prompt = "「春」的偏旁是？",
            optionA = "日", optionB = "木", optionC = "氵", optionD = "艹",
            answerIndex = 3, imageEmoji = "🌱"),
        Question(subject = Subject.CHINESE, grade = 2,
            prompt = "下列哪个字读音是「yǔ」？",
            optionA = "雨", optionB = "鱼", optionC = "语", optionD = "玉",
            answerIndex = 0, imageEmoji = "🌧️"),
        Question(subject = Subject.CHINESE, grade = 2,
            prompt = "「雪」是什么变成的？",
            optionA = "水", optionB = "云", optionC = "雨", optionD = "冰",
            answerIndex = 0, imageEmoji = "❄️",
            hint = "天上掉下来冷冷的白花花"),
        Question(subject = Subject.CHINESE, grade = 2,
            prompt = "「明亮的反义词是？",
            optionA = "黑暗", optionB = "干净", optionC = "高大", optionD = "快速",
            answerIndex = 0, imageEmoji = "💡"),
        Question(subject = Subject.CHINESE, grade = 2,
            prompt = "下面哪个是象声词？",
            optionA = "叮铃铃", optionB = "快乐", optionC = "跑步", optionD = "思考",
            answerIndex = 0, imageEmoji = "🔔")
    )

    private fun math(): List<Question> = listOf(
        // 一年级 - 10以内
        Question(subject = Subject.MATH, grade = 1,
            prompt = "3 + 5 = ?", imageEmoji = "🍎",
            optionA = "7", optionB = "8", optionC = "9", optionD = "6",
            answerIndex = 1),
        Question(subject = Subject.MATH, grade = 1,
            prompt = "10 - 4 = ?", imageEmoji = "🌟",
            optionA = "5", optionB = "6", optionC = "7", optionD = "4",
            answerIndex = 1),
        Question(subject = Subject.MATH, grade = 1,
            prompt = "2 + 2 + 2 = ?", imageEmoji = "🍐🍐🍐",
            optionA = "4", optionB = "5", optionC = "6", optionD = "7",
            answerIndex = 2),
        Question(subject = Subject.MATH, grade = 1,
            prompt = "看图：🐶🐶🐶🐶 共几只？",
            optionA = "3", optionB = "4", optionC = "5", optionD = "6",
            answerIndex = 1, imageEmoji = "🐶"),
        Question(subject = Subject.MATH, grade = 1,
            prompt = "9 - 6 = ?",
            optionA = "2", optionB = "3", optionC = "4", optionD = "5",
            answerIndex = 1),
        Question(subject = Subject.MATH, grade = 1,
            prompt = "1 只青蛙 2 只眼睛，3 只青蛙几只眼睛？",
            optionA = "4", optionB = "5", optionC = "6", optionD = "7",
            answerIndex = 2, imageEmoji = "🐸"),

        // 一年级 - 20以内
        Question(subject = Subject.MATH, grade = 1,
            prompt = "11 + 7 = ?",
            optionA = "17", optionB = "18", optionC = "19", optionD = "16",
            answerIndex = 1),
        Question(subject = Subject.MATH, grade = 1,
            prompt = "20 - 9 = ?",
            optionA = "10", optionB = "11", optionC = "12", optionD = "13",
            answerIndex = 1),

        // 二年级 - 乘除与钟表
        Question(subject = Subject.MATH, grade = 2,
            prompt = "3 × 4 = ?", imageEmoji = "🎈🎈🎈🎈",
            optionA = "12", optionB = "10", optionC = "7", optionD = "14",
            answerIndex = 0),
        Question(subject = Subject.MATH, grade = 2,
            prompt = "5 × 6 = ?",
            optionA = "30", optionB = "25", optionC = "20", optionD = "35",
            answerIndex = 0),
        Question(subject = Subject.MATH, grade = 2,
            prompt = "16 ÷ 4 = ?",
            optionA = "2", optionB = "3", optionC = "4", optionD = "5",
            answerIndex = 2),
        Question(subject = Subject.MATH, grade = 2,
            prompt = "看钟：短针指向 3，长针指向 12，是几点？",
            optionA = "3 时", optionB = "12 时", optionC = "6 时", optionD = "9 时",
            answerIndex = 0, imageEmoji = "🕒"),
        Question(subject = Subject.MATH, grade = 2,
            prompt = "小明有 8 个苹果，吃掉 3 个，还剩几个？",
            optionA = "4", optionB = "5", optionC = "6", optionD = "7",
            answerIndex = 1, imageEmoji = "🍏"),
        Question(subject = Subject.MATH, grade = 2,
            prompt = "找规律：1、3、5、7、?",
            optionA = "8", optionB = "9", optionC = "10", optionD = "11",
            answerIndex = 1, hint = "每次加 2")
    )

    private fun english(): List<Question> = listOf(
        // 一年级 - 上海牛津 1A/1B
        Question(subject = Subject.ENGLISH, grade = 1,
            prompt = "苹果用英文怎么说？", hint = "/ˈæpəl/",
            optionA = "Apple", optionB = "Banana", optionC = "Orange", optionD = "Grape",
            answerIndex = 0, imageEmoji = "🍎"),
        Question(subject = Subject.ENGLISH, grade = 1,
            prompt = "Cat 的中文是？",
            optionA = "猫", optionB = "狗", optionC = "鸟", optionD = "鱼",
            answerIndex = 0, imageEmoji = "🐱"),
        Question(subject = Subject.ENGLISH, grade = 1,
            prompt = "下面哪个颜色是红色？",
            optionA = "Red", optionB = "Blue", optionC = "Green", optionD = "Black",
            answerIndex = 0, imageEmoji = "🟥"),
        Question(subject = Subject.ENGLISH, grade = 1,
            prompt = "Hello 的中文意思是？",
            optionA = "你好", optionB = "再见", optionC = "谢谢", optionD = "对不起",
            answerIndex = 0),
        Question(subject = Subject.ENGLISH, grade = 1,
            prompt = "哪个是「数字一」？",
            optionA = "One", optionB = "Two", optionC = "Six", optionD = "Ten",
            answerIndex = 0),
        Question(subject = Subject.ENGLISH, grade = 1,
            prompt = "Book 是什么意思？",
            optionA = "书", optionB = "笔", optionC = "本子", optionD = "书包",
            answerIndex = 0, imageEmoji = "📕"),

        // 二年级
        Question(subject = Subject.ENGLISH, grade = 2,
            prompt = "「妈妈」的英文？",
            optionA = "Mother", optionB = "Father", optionC = "Sister", optionD = "Brother",
            answerIndex = 0, imageEmoji = "👩"),
        Question(subject = Subject.ENGLISH, grade = 2,
            prompt = "Dog 的中文是？",
            optionA = "狗", optionB = "猫", optionC = "鸭", optionD = "马",
            answerIndex = 0, imageEmoji = "🐶"),
        Question(subject = Subject.ENGLISH, grade = 2,
            prompt = "Big 的反义词是？",
            optionA = "Small", optionB = "Tall", optionC = "Long", optionD = "Fast",
            answerIndex = 0),
        Question(subject = Subject.ENGLISH, grade = 2,
            prompt = "「学校」的英文？",
            optionA = "School", optionB = "Home", optionC = "Park", optionD = "Shop",
            answerIndex = 0, imageEmoji = "🏫"),
        Question(subject = Subject.ENGLISH, grade = 2,
            prompt = "哪个是「星期三」？",
            optionA = "Wednesday", optionB = "Friday", optionC = "Monday", optionD = "Sunday",
            answerIndex = 0),
        Question(subject = Subject.ENGLISH, grade = 2,
            prompt = "Thank you 的中文？",
            optionA = "谢谢", optionB = "再见", optionC = "你好", optionD = "对不起",
            answerIndex = 0)
    )

    private fun general(): List<Question> = listOf(
        Question(subject = Subject.GENERAL, grade = 1,
            prompt = "过马路我们要看什么灯？",
            optionA = "红绿灯", optionB = "彩灯", optionC = "手电", optionD = "灯笼",
            answerIndex = 0, imageEmoji = "🚦"),
        Question(subject = Subject.GENERAL, grade = 1,
            prompt = "下面哪个动物是水里游的？",
            optionA = "鱼", optionB = "鸟", optionC = "兔", optionD = "马",
            answerIndex = 0, imageEmoji = "🐟"),
        Question(subject = Subject.GENERAL, grade = 1,
            prompt = "打雷闪电应该怎么办？",
            optionA = "待在屋里", optionB = "躲到大树下", optionC = "去空地上玩", optionD = "靠近窗户",
            answerIndex = 0, imageEmoji = "⛈️"),
        Question(subject = Subject.GENERAL, grade = 1,
            prompt = "哪种食物最有营养？",
            optionA = "蔬菜", optionB = "糖果", optionC = "薯片", optionD = "可乐",
            answerIndex = 0, imageEmoji = "🥦"),
        Question(subject = Subject.GENERAL, grade = 2,
            prompt = "中国首都是？",
            optionA = "北京", optionB = "上海", optionC = "广州", optionD = "深圳",
            answerIndex = 0, imageEmoji = "🏛️"),
        Question(subject = Subject.GENERAL, grade = 2,
            prompt = "一天有几小时？",
            optionA = "12", optionB = "24", optionC = "36", optionD = "48",
            answerIndex = 1, hint = "钟表转一圈."),
        Question(subject = Subject.GENERAL, grade = 2,
            prompt = "下面哪一项是垃圾分类中的「可回收」？",
            optionA = "废纸", optionB = "菜叶", optionC = "电池", optionD = "果皮",
            answerIndex = 0, imageEmoji = "♻️"),
        Question(subject = Subject.GENERAL, grade = 2,
            prompt = "拨打哪个电话报警？",
            optionA = "110", optionB = "119", optionC = "120", optionD = "114",
            answerIndex = 0, imageEmoji = "🚓"),
        Question(subject = Subject.GENERAL, grade = 2,
            prompt = "地球的形状像什么？",
            optionA = "球", optionB = "方块", optionC = "圆饼", optionD = "三角",
            answerIndex = 0, imageEmoji = "🌍"),
        Question(subject = Subject.GENERAL, grade = 2,
            prompt = "下面哪个是哺乳动物？",
            optionA = "猫", optionB = "金鱼", optionC = "小鸡", optionD = "蝴蝶",
            answerIndex = 0, imageEmoji = "🐱")
    )
}
