# Sprint Planning 4
**Periodo:** 05/03/2026 – 15/03/2026


## Sprint Goal
Implementare il sistema completo dei quiz, estendere il modello delle domande per supportare tipologie diverse (scelta multipla e risposta aperta), introdurre la nuova interfaccia dedicata alle domande aperte e rifattorizzare l’architettura per garantire pieno rispetto del pattern MVC.
Completare inoltre l’integrazione dei contenuti teorici e strutturare correttamente il percorso didattico basato sui livelli.


---


## Deadline
La scadenza dello Sprint è il **15/03/2026**.


---


## Task pianificati per lo Sprint


### Quiz Engine
**Introduzione del campo qType nel modello Question** — aggiunta della proprietà che identifica la tipologia di domanda (MultipleChoice o OpenQuestion).


**Creazione delle domande aperte** — estensione del dataset e aggiunta di un nuovo livello dedicato esclusivamente a domande open.


**Routing automatico delle viste** — implementazione della logica nel Controller per selezionare dinamicamente la View corretta (QuizPage o OpenQuizPage) in base al tipo della domanda.


**Feedback utente per la risposta** — integrazione di un feedback all'utente dopo la risposta.


### Interfaccia Grafica
**Implementazione OpenQuizPage** — nuova schermata dedicata alle domande con risposta aperta, dotata di TextArea e pulsante di conferma.


**Aggiornamento QuizPage** — adeguamento della schermata delle domande a scelta multipla per supportare il nuovo flusso gestito dal Controller.


### Architettura e Persistenza
**Aggiornamento levels.json** — conversione di tutte le domande esistenti in MultipleChoice e aggiunta del nuovo livello con domande OpenQuestion.


**Refactoring MVC** — rimozione della logica residua nelle View e centralizzazione della gestione del flusso (prossima domanda, validazione risposta, routing) nel QuizController.


### Quality Assurance
**Test delle nuove tipologie di domande** — verifica del corretto funzionamento di domande a risposta multipla e aperta.


---


## Sprint Review


### Progresso
Lo Sprint ha introdotto il supporto completo ai quiz, permettendo la gestione di due tipologie di domande: multiple choice e open question.
Il modello Question è stato esteso con il campo qType, e l’intero dataset è stato aggiornato di conseguenza, includendo un nuovo livello dedicato alle risposte aperte.


Il Controller ora gestisce interamente il flusso dei quiz: la validazione della risposta, il feedback visivo, il delay e il passaggio alla prossima domanda. La View è diventata completamente passiva e aderente ai principi MVC.


L’implementazione della nuova interfaccia OpenQuizPage ha ampliato notevolmente il potenziale del sistema didattico, consentendo domande più articolate e un’interazione più naturale da parte dell’utente.


Infine, un deciso intervento di refactoring ha permesso di applicare il principio DRY, centralizzando la creazione di elementi comuni (come i pulsanti di navigazione "Indietro") per eliminare ridondanze e migliorare la coerenza estetica dell'applicazione.


### Architettura
Le principali modifiche architetturali riguardano:
- Il QuizController ora è l’unica fonte di logica applicativa per i quiz.
- Le View sono state semplificate e rese stateless.
- Introdotto un routing dinamico basato su qType per la selezione della pagina corretta.
- Esteso il sistema di persistenza dei livelli con nuovi campi e nuove tipologie di domande.
- Implementazione di showCustomConfirm per gestire avvisi e conferme tramite callback, separando la logica di business (cosa succede al click) dalla logica di visualizzazione (come appare la finestra)


### Utente
L’utente ora può:
- Rispondere a domande a scelta multipla con feedback immediato.
- Interagire con domande aperte tramite una TextArea dedicata.
- Navigare fluidamente tra le domande.


L’esperienza utente è risultata più fluida, chiara e coerente grazie alla struttura unificata del flusso dei quiz.


### Qualità
I test effettuati hanno confermato:
- Corretta visualizzazione di ogni tipo di domanda.
- Routing affidabile e privo di regressioni.
- Nessuna interferenza tra i due tipi di quiz.


L’infrastruttura di testing risulta adeguata e pronta per future estensioni (punteggio, risultati, tracking risposte).


---


## Sprint Retrospective


### Cosa è andato bene
- L’estensione del modello Question è stata integrata senza modificare il codice esistente.
- Il refactoring del Controller ha migliorato drasticamente la separazione delle responsabilità.
- Le Views risultano ora semplici, chiare e facilmente estendibili.
- La nuova OpenQuizPage ha ampliato le capacità didattiche dell’app.
- L'aggiornamento dei livelli presenti nel dataset è avvenuta senza problemi.


### Difficoltà incontrate
- Si sono verificati alcuni conflitti durante l’integrazione delle nuove funzionalità, che hanno richiesto tempo per essere risolti correttamente.
- L’aggiunta del campo qType ha richiesto una revisione accurata del modello, del dataset e del routing, comportando un refactoring più complesso del previsto.
- L’aggiornamento coordinato tra più file (model, controller, viste e JSON) ha richiesto test e verifiche aggiuntive per evitare regressioni.


—


## Miglioramenti previsti per Sprint 5
- Introduzione di un sistema di punteggio basato sulle risposte corrette.
- Creazione della schermata finale con riepilogo del quiz.
- Aggiunta della schermata dei Progressi Utenti.
- Miglioramenti all’interfaccia per facilitare l’accessibilità e la responsività.
- Aggiunta della validazione delle risposte aperte con motore Prolog.
- Aggiunta della validazione delle risposte chiuse con motore Prolog.
