---
layout: default
title: Testing
nav_order: 7
---

#  Testing

---

La strategia di testing adottata si è focalizzata sulla robustezza del **Model** e della **Business Logic**, garantendo che il cuore applicativo (motore Prolog e gestione sessioni) sia resiliente a stati inconsistenti o input errati.

#### Tecnologie e Metodologia

* **Framework**: Abbiamo utilizzato **ScalaTest** adottando la suite **AnyFunSuite** per la sua chiarezza nell'esecuzione di test .
* **Lifecycle Management**: Grazie al trait **BeforeAndAfterEach**, abbiamo garantito l'isolamento dei test resettando lo stato dei componenti singleton, come `LevelSession`, prima di ogni esecuzione.
* **Test Manuali**: Data la natura interattiva della GUI, i Controller e le View sono stati sottoposti a test manuali sistematici per verificare la fluidità della navigazione e la corretta gestione dei feedback visivi.

#### **Coverage e Grado di Copertura**
L'analisi della copertura del codice è stata effettuata tramite lo strumento **Scoverage**:

*** TODO: Aggiungere screen del report

---

#### **Esempi Rilevanti di Testing**

##### **1. Gestione della Sessione di Gioco (LevelSession)**
Il test verifica che la `LevelSession` gestisca correttamente i contatori dei tentativi e che resetti lo stato dopo la chiusura di un livello.

```scala
test("addAttempt dovrebbe aggiornare i contatori dei Quiz tentati e corretti") {
  val levelId = Level.Id.random
  LevelSession.startLevel(levelId)
  LevelSession.addAttempt(isCorrect = true)
  LevelSession.addAttempt(isCorrect = false)
  LevelSession.addAttempt(isCorrect = true)

  val stats = LevelSession.currentStats.getOrElse((0, 0))
  assert(stats._1 == 3) // 3 tentativi totali
  assert(stats._2 == 2) // 2 risposte corrette
}

test("endLevel dovrebbe restituire i dati finali e tornare allo stato Idle") {
  LevelSession.startLevel(Level.Id.random)
  LevelSession.addAttempt(isCorrect = true)

  val finalData = LevelSession.endLevel().getOrElse(fail("Dati mancanti"))
  assert(finalData.quizCorrects == 1)
  assert(LevelSession.currentStats.isEmpty) // Stato resettato correttamente
}
```

<p align="right">
  <a href="retrospettiva.html"> Retrospettiva →</a>
</p>