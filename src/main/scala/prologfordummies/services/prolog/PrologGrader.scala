package prologfordummies.services.prolog

import alice.tuprolog.*

object PrologGrader:

  /** Valuta la logica dell'utente confrontandola con i fatti del livello.
    * @param levelKnowledge
    *   I fatti del livello (es. genitore(a,b))
    * @param userSolution
    *   Il codice o la regola scritta dall'utente
    * @param testQuery
    *   L'obiettivo da verificare per confermare la soluzione
    */
  def validate(
      levelKnowledge: String,
      userSolution: String,
      testQuery: String
  ): Boolean =
    try
      val prologEngine = new Prolog()

      // Unione della conoscenza del livello con la soluzione proposta dall'utente
      val completeTheorySource = levelKnowledge + "\n" + userSolution
      val runtimeTheory = new Theory(completeTheorySource)

      prologEngine.setTheory(runtimeTheory)

      // Esecuzione della verifica
      val solution: SolveInfo = prologEngine.solve(testQuery)

      solution.isSuccess

    catch
      case error: Exception =>
        println(
          s"DEBUG - Errore durante la validazione Prolog: ${error.getMessage}"
        )
        false

  def validateMultipleChoice(
                              levelKnowledge: String,
                              userAnswer: String,
                              correctAnswer: String
                            ): Boolean =
    try
      val normalizedUser = userAnswer.trim
      val normalizedCorrect = correctAnswer.trim

      // Verifica che entrambe siano codice Prolog corretto
      val userTheory = new Theory(levelKnowledge + "\n" + normalizedUser)
      val correctTheory = new Theory(levelKnowledge + "\n" + normalizedCorrect)

      normalizedUser == normalizedCorrect
    catch
      case error: Exception =>
        println(
          s"DEBUG - Errore durante la validazione MultipleChoice: ${error.getMessage}"
        )
        false
