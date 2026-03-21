package prologfordummies.model.prolog

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
