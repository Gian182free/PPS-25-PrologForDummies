package prologfordummies.services

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.BeforeAndAfterAll
import java.nio.file.{Files, Paths}
import java.util.UUID
import prologfordummies.model.Level
import prologfordummies.model.LevelCodec.given
import upickle.default.*
import prologfordummies.model.Question

class LevelRepositoryTest extends AnyFunSuite with BeforeAndAfterAll {

  // Variabili per i livelli fake da inserire nel file di test
  private val idLivello1 = Level.Id.random
  private val idLivello2 = Level.Id.random
  private val title1 = Level.Title("Livello 1")
  private val title2 = Level.Title("Livello 2")
  private val theory1 = Level.Theory("Teoria 1")
  private val theory2 = Level.Theory("Teoria 2")

  val domanda1 = Question.create(
    id = 1,
    question = "1+1?",
    correctAnswer = "2",
    answers = List("2", "3", "4")
  ).getOrElse(throw new Exception("Errore creazione domanda 1"))

  private val testPath = Paths.get(
    System.getProperty("user.home"),
    ".prologfordummies",
    "levels_test.json"
  )
  
  val repo = LevelRepositoryImpl(testPath)

  override def beforeAll(): Unit = {
    if (!Files.exists(testPath.getParent)) {
      Files.createDirectories(testPath.getParent)
    }

    val fakeLevels = List(
      Level(idLivello1, Level.Title(title1.asString), Level.Theory(theory1.asString), List(domanda1)),
      Level(idLivello2, Level.Title(title2.asString), Level.Theory(theory2.asString), List(domanda1))
    )

    val jsonData = write(fakeLevels)
    Files.write(testPath, jsonData.getBytes)
  }

  test("Caricamento di tutti i livelli da file") {
    val loaded = repo.loadAll()
    
    assert(loaded.size == 2, "Il file dovrebbe contenere esattamente 2 livelli")
    assert(loaded.exists(_.id == idLivello1), "Manca il primo livello")
    assert(loaded.exists(_.id == idLivello2), "Manca il secondo livello")
  }

  test("Ricerca per livello positiva") {
    val all = repo.loadAll()
    assume(all.nonEmpty) 
    
    assert(repo.findById(idLivello1).isDefined, "Dovrebbe trovare il livello 1")
  }

  test("Ricerca per livello negativa") {
    val nonEsistenteId = Level.Id.random
    assert(repo.findById(nonEsistenteId).isEmpty, "Non dovrebbe trovare un livello con ID non esistente")
  }

  test("I dati del livello caricato devono corrispondere a quelli scritti") {
    val level = repo.findById(idLivello1).get
    assert(level.title.asString == title1.asString)
    assert(level.theory.asString == theory1.asString)
    assert(level.questions.size == 1)
  }
}