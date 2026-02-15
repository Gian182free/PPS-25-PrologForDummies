package prologfordummies.model

case class Quiz(
    id: Int,
    question: String,
    correctAnswer: String,
    answers: List[String]
):
    def isCorrect(answer: String): Boolean =
        answer == correctAnswer

object Quiz:
    def create(
        id: Int,
        question: String,
        correctAnswer: String,
        answers: List[String]
    ): Either[String, Quiz] =
        if question.isEmpty then Left("Question cannot be empty")
        else if answers.isEmpty then Left("Answer list cannot be empty")
        else if !answers.contains(correctAnswer) then Left("Correct answer must be in the list")
        else Right(Quiz(id, question, correctAnswer, answers))

    

