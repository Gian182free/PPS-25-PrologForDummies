package prologfordummies.model

case class Question(
    id: Int,
    question: String,
    correctAnswer: String,
    answers: List[String]
):
    def isCorrect(answer: String): Boolean =
        answer == correctAnswer

object Question:
    def create(
        id: Int,
        question: String,
        correctAnswer: String,
        answers: List[String]
    ): Either[String, Question] =
        (question, answers, correctAnswer) match
            case (q, _, _) if q.isEmpty  => Left("Question cannot be empty")
            case (_, ansList, _) if ansList.isEmpty => Left("Answer list cannot be empty")
            case (_, ansList, c) if !ansList.contains(c) => Left("Correct answer must be in the list")
            case _ => Right(Question(id, question, correctAnswer, answers))


    

