---
layout: default
title: Implementazione
nav_order: 6
---

#  Implementazione

---

## Gianfranco Branco

---

### Model


Il mio contributo nel package `model` si è focalizzato sulla creazione di un dominio solido e sulla gestione della persistenza, garantendo una netta separazione tra la modellazione dei dati e i dettagli di archiviazione.

#### **User**
Ho implementato la `case class User` come entità centrale per l'anagrafica del sistema.
L'utilizzo di una `case class` garantisce l'immutabilità dei dati.

#### **LevelRecord**
Questa classe modella il singolo evento di completamento di un livello. Ogni volta che un utente termina un livello, vengono registrati diversi KPI insieme al timestamp della sessione. La struttura permette la coesistenza di più occorrenze per lo stesso livello, abilitando così una cronostoria dettagliata dei suoi progressi.

#### **UserProgress**
Rappresenta l'aggregatore globale dei progressi di un utente, mettendo in relazione l'identità dell'utente con la sua cronologia di LevelRecord.

Ho implementato diversi metodi di utility all'interno della classe per calcolare i KPI globali in tempo reale, partendo dai dati grezzi:

```scala
  // Ore totali di utilizzo (approssimate)
  def totalHours: Double = history.map(_.timeSpentMinutes).sum / 60.0

// Somma totale delle risposte corrette ottenute
def totalCorrects: Int = history.map(_.quizCorrects).sum

// Esempio di calcolo derivato: percentuale globale di successo
def globalAccuracy: Double =
  if (totalAttempts > 0) (totalCorrects.toDouble / totalAttempts) * 100
  else 0.0
```
Questo approccio segue il pattern *Rich Domain Model*, dove la logica di calcolo è incapsulata direttamente nel Model. Questo semplifica notevolmente i Controller, che non devono conoscere i dettagli dei calcoli statistici ma si limitano a interrogare il modello per visualizzare i risultati.

#### **UserSession**
Questa classe agisce come un contenitore globale per l'utente autenticato.
Permettendo di mantenere la persistenza in memoria della sessione attiva. Questo evita di dover passare l'oggetto `User` tra tutti i controller della View, centralizzando l'accesso alle informazioni dell'utente loggato e migliorando la manutenibilità del flusso di navigazione.

#### **LevelSession**
La sessione è gestita tramite una macchina a stati definita da `sealed trait LevelSessionState`. Questo permette di distinguere nettamente tra una sessione Idle (nessun livello in corso) e una sessione ActiveLevel, partita attiva con timestamp e contatori.

All'interno del `object LevelSession`, ho utilizzato il pattern matching per garantire che le operazioni sui KPI vengano eseguite solo se esiste effettivamente una sessione attiva, evitando errori di stato inconsistente.

```scala
    // Registro una risposta
  def addAttempt(isCorrect: Boolean): Unit =
    state match
      case currentState: ActiveLevel =>
        state = currentState.copy(
          quizAttempts = currentState.quizAttempts + 1,
          quizCorrects =
            currentState.quizCorrects + (if isCorrect then 1 else 0)
        )
      case Idle => ()
```

Questa classe separa i progressi "vivi" (temporanei) da quelli definitivi salvati su disco. Solo quando viene chiamato il metodo endLevel(), lo stato viene resettato a Idle e i dati finali vengono passati al sistema di persistenza per essere trasformati in un LevelRecord.

```scala
  // Alla conclusione del livello restituisco i dati finali
  def endLevel(): Option[ActiveLevel] =
    val finalState = state match
      case currentState: ActiveLevel => Some(currentState)
      case Idle                      => None
    state = Idle
    finalState
```

---

#### **Codec**
Per rendere persistenti gli `User` e gli `UserProgress`, ho implementato i relativi Codec nel package `model.codec`.

Per le classi `UserCodec` e `UserProgressCodec` invece di scrivere parser manuali, ho sfruttato la libreria **uPickle** e le macro di Scala 3 per la derivazione automatica delle Type Class.

```scala
  implicit val idRW: ReadWriter[User.Id] =
    readwriter[String].bimap(
      _.toString,
      s => User.Id(UUID.fromString(s))
    )
```

Ho fatto uso del *Single Responsibility Principle* poiché ho isolato i Codec, evitando di "sporcare" le classi di dominio con dettagli implementativi legati al formato JSON. In questo modo, se in futuro si decidesse di cambiare formato di salvataggio, basterebbe modificare il Codec senza toccare la struttura della classe `User`.

<p align="right">
  <a href="testing.html"> Testing →</a>
</p>
