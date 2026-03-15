package prologfordummies.model

import org.scalatest.BeforeAndAfterEach
import org.scalatest.funsuite.AnyFunSuite
import java.time.LocalDateTime
import prologfordummies.model.LevelSession
import prologfordummies.model.ActiveLevel
import prologfordummies.model.Idle

class LevelSessionTest extends AnyFunSuite with BeforeAndAfterEach {
  // Resetto lo stato prima di ogni test
  override def beforeEach(): Unit = {
    LevelSession.endLevel()
  }

  test("LevelSession dovrebbe iniziare in stato Idle (Nessun livello avviato)") {
    assert(LevelSession.currentStats.isEmpty)
  }

  test("startLevel dovrebbe registrare l'inizio del livello") {
    val levelId = Level.Id.random
    LevelSession.startLevel(levelId)

    val stats = LevelSession.currentStats
    assert(stats.isDefined)
    assert(LevelSession.currentStats.contains((0, 0)))
  }

  test("addAttempt dovrebbe aggiornare i contatori dei Quiz tantati e corretti") {
    val levelId = Level.Id.random
    LevelSession.startLevel(levelId)
    LevelSession.addAttempt(isCorrect = true)
    LevelSession.addAttempt(isCorrect = false)
    LevelSession.addAttempt(isCorrect = true)

    val stats = LevelSession.currentStats.getOrElse((0, 0))
    assert(stats._1 == 3) // 3 tentativi
    assert(stats._2 == 2) // 2 corretti
  }

  test("elapsedMinutes dovrebbe restituire un valore positivo (minimo 1)") {
    val levelId = Level.Id.random
    LevelSession.startLevel(levelId)
    // Dato che .max(1) nel codice, deve essere almeno 1
    assert(LevelSession.elapsedMinutes >= 1)
  }

  test("endLevel dovrebbe restituire i dati finali e tornare a Idle") {
    val levelId = Level.Id.random
    LevelSession.startLevel(levelId)
    LevelSession.addAttempt(isCorrect = true)
    LevelSession.addAttempt(isCorrect = false)

    val finalData =
      LevelSession.endLevel().getOrElse(fail("Dati finali mancanti"))

    assert(finalData.levelId == levelId)
    assert(finalData.quizAttempts == 2)
    assert(finalData.quizCorrects == 1)

    // Verifichiamo che sia tornato allo stato iniziale
    assert(LevelSession.currentStats.isEmpty)
  }
}
