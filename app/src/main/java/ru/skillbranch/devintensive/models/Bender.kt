package ru.skillbranch.devintensive.models

class Bender(var status:Status = Status.NORMAL, var question: Question = Question.NAME) {

    fun askQuestion(): String = when (question) {
        Question.NAME -> Question.NAME.question
        Question.PROFESSION -> Question.PROFESSION.question
        Question.MATERIAL -> Question.MATERIAL.question
        Question.BDAY -> Question.BDAY.question
        Question.SERIAL -> Question.SERIAL.question
        Question.IDLE -> Question.IDLE.question
    }

    fun listenAnswer(answer: String): Pair<String, Triple<Int,Int,Int>> {
        val check = question.checkFormat(answer)
        if(null != check){
            return "$check\n${question.question}" to status.color
        }

        return if (question.answers.contains(answer.toLowerCase())) {
            question = question.nextQuetion()
            "Отлично - ты справился\n${question.question}" to status.color
        } else{
            status = status.nextStatus()
            "Это неправильный ответ\n${question.question}" to status.color
        }
    }

    enum class Status(var color: Triple<Int, Int, Int>){
        NORMAL(Triple(255, 255, 255)),
        WARNING(Triple(255, 120, 0)),
        DANGER(Triple(255, 60, 60)),
        CRITICAL(Triple(255, 255, 0));

        fun nextStatus(): Status{
            return if (this.ordinal < values().lastIndex){
                values()[this.ordinal + 1]
            } else{
                values()[0]
            }
        }
    }

    enum class Question(val question: String, val answers: List<String>){
        NAME("Как меня зовут?", listOf("бендер", "bender")){
            override fun nextQuetion(): Question = PROFESSION

            override fun checkFormat(answer: String): String? {
                if (answer.getOrNull(0)?.isLowerCase() != false){
                    return "Имя должно начинаться с заглавной буквы"
                }
                return null
            }
        },
        PROFESSION("Назови мою профессию?", listOf("сгибальщик", "bender")){
            override fun nextQuetion(): Question = MATERIAL

            override fun checkFormat(answer: String): String? {
                if (answer.getOrNull(0)?.isUpperCase() != false){
                    return "Профессия должна начинаться со строчной буквы"
                }
                return null
            }
        },
        MATERIAL("Из чего я сделан?", listOf("металл", "дерево", "metal", "iron", "wood")){
            override fun nextQuetion(): Question = BDAY

            override fun checkFormat(answer: String): String? {
                answer.forEach{
                    if(it.isDigit()) {
                        return "Материал не должен содержать цифр"
                    }
                }
                return null
            }
        },
        BDAY("Когда меня сделали?", listOf("2993")){
            override fun nextQuetion(): Question = SERIAL

            override fun checkFormat(answer: String): String? {
                answer.forEach{
                    if(!it.isDigit()) {
                        return "Год моего рождения должен содержать только цифры"
                    }
                }
                return null
            }
        },
        SERIAL("Мой серийный номер?", listOf("2716057")){
            override fun nextQuetion(): Question = IDLE

            override fun checkFormat(answer: String): String? {
                answer.forEach{
                    if(!it.isDigit()) {
                        return "Серийный номер содержит только цифры, и их 7"
                    }
                }
                if (7 == answer.length){
                    return "Серийный номер содержит только цифры, и их 7"
                }
                return null
            }
        },
        IDLE("На этом все, вопросов больше нет", listOf()){
            override fun nextQuetion(): Question = IDLE

            override fun checkFormat(answer: String): String? {
                return null
            }
        };

        abstract fun nextQuetion(): Question

        abstract fun checkFormat(answer: String): String?
    }
}