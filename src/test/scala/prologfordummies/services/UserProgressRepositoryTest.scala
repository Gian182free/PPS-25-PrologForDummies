package prologfordummies.services

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.BeforeAndAfter
import java.nio.file.Files
import java.nio.file.Paths
import org.scalatest.ConfigMap
import org.scalatest.TestData
import prologfordummies.model.*
import java.time.LocalDateTime

class UserProgressRepositoryTest extends AnyFunSuite with BeforeAndAfter {

  // File apposito per i test per non sporcare i dati reali
  private val testPath = Paths.get(
    System.getProperty("user.home"),
    ".prologfordummies",
    "users_progress_test.json"
  )
  val repo = UserProgressRepositoryImpl(testPath)

  // Pulizia del file di test prima di ogni test
  before {
    if (Files.exists(testPath)) Files.delete(testPath)
  }

  test("Salvataggio e caricamento progresso utente") {
    val userId = User.Id.random
    val levelId = Level.Id.random
    val progress = UserProgress(
      userId,
      List(LevelRecord(levelId, LocalDateTime.now(), 3, 5, 2))
    )

    repo.saveProgress(progress)

    val loaded = repo.findByUserId(userId)
    assert(loaded.isDefined)
    assert(loaded.get.userId == userId)
    assert(loaded.get.history.size == 1)
    assert(loaded.get.history.head.levelId == levelId)
  }

  test("Aggiornamento progresso utente esistente") {
    val userId = User.Id.random
    val levelId1 = Level.Id.random
    val levelId2 = Level.Id.random

    val initialProgress = UserProgress(
      userId,
      List(LevelRecord(levelId1, LocalDateTime.now(), 3, 5, 2))
    )

    repo.saveProgress(initialProgress)

    val updatedProgress = UserProgress(
      userId,
      List(
        LevelRecord(levelId1, LocalDateTime.now(), 4, 5, 1),
        LevelRecord(levelId2, LocalDateTime.now(), 5, 5, 0)
      )
    )

    repo.saveProgress(updatedProgress)

    val loaded = repo.findByUserId(userId)
    assert(loaded.isDefined)
    assert(loaded.get.userId == userId)
    assert(loaded.get.history.size == 2)
    assert(loaded.get.history.exists(_.levelId == levelId1))
    assert(loaded.get.history.exists(_.levelId == levelId2))
  }

  test("Ricerca per userId e levelId") {
    val userId = User.Id.random
    val levelId = Level.Id.random

    val progress = UserProgress(
      userId,
      List(LevelRecord(levelId, LocalDateTime.now(), 3, 5, 2))
    )

    repo.saveProgress(progress)

    val record = repo.findByUserIdAndLevelId(userId, levelId)
    assert(record.isDefined)
    assert(record.get.levelId == levelId)
  }

}
