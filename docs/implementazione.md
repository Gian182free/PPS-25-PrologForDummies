---
layout: default
title: Implementazione
nav_order: 6
---

#  Implementazione

---

## Gianfranco Branco

---

## Model


Il mio contributo nel package `model` si è focalizzato sulla creazione di un dominio solido e sulla gestione della persistenza, garantendo una netta separazione tra la modellazione dei dati e i dettagli di archiviazione.

#### **User**
Ho implementato la `case class User` come entità centrale per l'anagrafica del sistema.
L'utilizzo di una `case class` garantisce l'immutabilità dei dati, garantendo che i dati dell'utente non subiscano modifiche accidentali durante l'esecuzione, rendendo il flusso dei dati tra Controller e View estremamente prevedibile.

#### **LevelRecord**
Questa classe modella il singolo evento di completamento di un livello. Ogni volta che un utente termina un livello, vengono registrati diversi KPI insieme al timestamp della sessione. La struttura permette la coesistenza di più occorrenze per lo stesso livello, abilitando così una cronostoria dettagliata dei suoi progressi.
Ho scelto di definire `LevelRecord` all'interno dello stesso file sorgente di `UserProgress` poiché le due entità sono strettamente interdipendenti.

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

All'interno del `object LevelSession`, ho utilizzato il pattern *Matching* per garantire che le operazioni sui KPI vengano eseguite solo se esiste effettivamente una sessione attiva, evitando errori di stato inconsistente.

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

Ho fatto uso del *Single Responsibility Principle* poiché ho isolato i Codec, evitando di "sporcare" le classi di dominio con dettagli implementativi legati al formato JSON. In questo modo, se in futuro si decidesse di cambiare formato di salvataggio, basterebbe modificare il Codec senza toccare la struttura della classe `User`.

Per le classi `UserCodec` e `UserProgressCodec`, ho utilizzato la generazione automatica dei Codec tramite `macroRW`. Questo permette al compilatore di ispezionare la struttura delle classi e generare la logica di parsing a tempo di compilazione, garantendo performance elevate e riducendo gli errori manuali.
Poiché le macro non sanno gestire nativamente tipi come `LocalDateTime` o `UUID`, ho fornito delle istanze `given` personalizzate. Tramite il metodo `.bimap`, ho definito esplicitamente come mappare questi tipi su stringhe JSON, permettendo alle macro di "comporre" i Codec più complessi.

```scala
// Esempio di mapping bidirezionale per un Value Type (User.Id)
implicit val idRW: ReadWriter[User.Id] = 
  readwriter[String].bimap(
    id => id.toString,                         // Da oggetto a Stringa JSON
    s  => User.Id(UUID.fromString(s))           // Da Stringa JSON a oggetto
  )

// Derivazione automatica per l'entità principale
implicit val userRW: ReadWriter[User] = macroRW
```

## Controller

Nella realizzazione dei controller, l'obiettivo primario è stato il disaccoppiamento della logica di controllo dai servizi di persistenza e dalla gestione dei dati.

Per implementare una #Dependency Injection# nativa e pulita, ho sfruttato le potenzialità di Scala 3, come l'utilizzo dei `given` e i parametri contestuali `using` per fornire i repository ai controller. In questo modo i controller non istanziano direttamente i servizi, ma li ricevono implicitamente dal contesto. Questo facilita lo scambio delle implementazioni senza modificare la logica interna del controller.

Ho curato personalmente lo sviluppo dei seguenti componenti:

#### RegistrationController

Gestisce il flusso di creazione di un nuovo account. Si occupa di validare i dati di input e di interagire con lo `UserRepository` per persistere le nuove credenziali, assicurando che non vi siano duplicati nel sistema.

#### EditUserController

Permette all'utente di modificare le proprie informazioni di profilo. Questo controller interagisce direttamente con la `UserSession` per identificare l'utente attivo e aggiornare i dati sia in memoria che su disco tramite i relativi codec.

#### LevelsController

Il `LevelsController` è stato realizzato in collaborazione con il mio collega, adottando un approccio che separa la gestione del flusso applicativo dall'analisi statistica dei dati di gioco.

Per quanto mi riguarda, mi sono occupato della progettazione della struttura portante del controller e della gestione del ciclo di vita dei contenuti, implementando il disaccoppiamento dai servizi di persistenza utilizzando le `given` di Scala 3 per iniettare `LevelRepository` e `UserProgressRepository`. Questo garantisce che il controller non dipenda da una specifica implementazione del file system.

## Service

Il layer dei servizi funge da intermediario tra i Controller e la persistenza, garantendo che le regole di business siano centralizzate e indipendenti dall'interfaccia utente.

#### Repository

Ho implementato il pattern *Repository* per gestire l'accesso ai dati su file system.
Ogni entità `User`, `Level`, `UserProgress` dispone di una *trait* e di un'implementazione concreta `Impl` che utilizza il formato JSON.
In `UserProgressRepositoryImpl`, ho implementato una logica di aggiornamento "read-modify-write" che garantisce l'integrità dei dati: il sistema carica lo stato attuale, aggiorna solo il record interessato e sovrascrive il file atomicamente.
Le ricerche all'interno dei file, come in `findByUserId` sono effettuate in modo funzionale tramite predicati sulle collezioni Scala, garantendo velocità e leggibilità.

#### UserService

Il `UserService` si occupa di validare le operazioni sugli utenti prima di persisterle. Nel metodo `signUp`, ho implementato controlli per impedire la creazione di utenti con nomi vuoti o duplicati, restituendo un tipo `Either[String, User]` per gestire gli errori in modo esplicito e senza eccezioni.

#### PrologGrader
In collaborazione con il mio collega, ho sviluppato il motore di valutazione delle risposte, che integra la libreria **tuProlog** all'interno dell'ecosistema Scala.

Il mio contributo è stato nei quiz di tipo Fill-in-the-blank. La logica non si limita a un confronto testuale, ma verifica la correttezza sintattica del codice inserito dall'utente istanziando una `Theory` Prolog dinamica.
Il metodo `validate` fonde la conoscenza base del livello con la soluzione proposta dall'utente e tenta di risolvere una `testQuery`. Il successo della query determina la correttezza della risposta.

```scala
// Esempio di validazione dinamica Prolog
def validate(levelKnowledge: String, userSolution: String, testQuery: String): Boolean =
  try
    val prologEngine = new Prolog()
    val runtimeTheory = new Theory(levelKnowledge + "\n" + userSolution)
    prologEngine.setTheory(runtimeTheory)
    prologEngine.solve(testQuery).isSuccess // La correttezza è determinata dal successo logico
  catch
    case _: Exception => false
```

## View

L'interfaccia grafica è stata sviluppata utilizzando **ScalaFX**, un wrapper Type-Safe di JavaFX che permette di sfruttare appieno la sintassi dichiarativa di Scala 3. L'obiettivo è stato creare un'esperienza utente coerente, minimizzando la duplicazione del codice tramite l'astrazione dei componenti.

#### RegistrationPage

In questa schermata di registrazione utente, ottengo l'input dell'username e lo passo al `RegistrationController`. La vista gestisce i feedback di errore tramite callback che aggiornano dinamicamente l'interfaccia senza ricaricare l'intera scena.
    
#### EditUserPage

Questa vista permette la gestione della modifica od eliminazione dell'utenza. Prima di procedere all'eliminazione dell'utente richiamo la modale `showCustomConfirm` e una volta confermata l'azione, la vista coordina con il controller il logout e il reindirizzamento alla pagina di registrazione.

#### LevelsPage

In collaborazione con il team, ho curato la struttura a griglia dei livelli. 
Ho implementato il layout principale utilizzando il componente `cardGrid` e la logica di navigazione che permette di selezionare un livello e passare alla fase di gioco. 

#### UIComponents

In collaborazione con il mio collega, ho contribuito allo sviluppo dell'oggetto `UIComponents`. Questo componente funge da "libreria di design" interna, centralizzando lo stile e i layout comuni per garantire uniformità visiva in tutta l'applicazione.

Mi sono occupato del metodo `showCustomConfirm` per permettere la creazione di modali custom. A differenza dei dialoghi standard di sistema, questa soluzione utilizza uno `Stage` con `StageStyle.Undecorated`, permettendo un controllo totale sul design che resta coerente a tutta l'applicazione, non utilizzando la modale standard del SO.

La modale accetta una funzione `onConfirm: () => Unit` come parametro. Questo approccio permette di slegare il componente grafico dalla logica applicativa: la modale si occupa solo della visualizzazione, delegando l'azione al chiamante.

Infine mi sono occupato della funzione `backButton`, per permettere all'utente il ritorno alla pagina precedente.

```scala
// Esempio della logica di gestione della modale da me implementata
def showCustomConfirm(head: String, message: String, onConfirm: () => Unit): Unit = {
  val dialog = new Stage {
    initModality(Modality.ApplicationModal)
    initStyle(StageStyle.Undecorated) 
    // ... setup del layout e degli stili CSS-inline ...
    children = Seq(
      styledButton(confirmButtonMsg, "#d32f2f", "white", {
        onConfirm() // Esecuzione della logica di business
        delegate.getScene.getWindow.hide()
      })
    )
  }
  dialog.showAndWait()
}
```

<p align="right">
  <a href="testing.html"> Testing →</a>
</p>
