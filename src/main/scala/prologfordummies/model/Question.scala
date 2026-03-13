package prologfordummies.model

import prologfordummies.model.QuestionType
case class Question(
                     id: Int,
                     question: String,
                     correctAnswer: String,
                     answers: List[String],
                     qType: QuestionType
                   ):
    def isCorrect(answer: String): Boolean =
        answer == correctAnswer

object Question:
    def create(
                id: Int,
                question: String,
                correctAnswer: String,
                answers: List[String],
                qType: QuestionType = QuestionType.MultipleChoice
              ): Either[String, Question] =
        (question, answers, correctAnswer, qType) match
            case (q, _, _, QuestionType.MultipleChoice) if q.isEmpty  => Left("Question cannot be empty")
            case (_, ansList, _, QuestionType.MultipleChoice) if ansList.isEmpty => Left("Answer list cannot be empty")
            case (_, ansList, c, QuestionType.MultipleChoice) if !ansList.contains(c) => Left("Correct answer must be in the list")
            case _ => Right(Question(id, question, correctAnswer, answers, qType))


    

