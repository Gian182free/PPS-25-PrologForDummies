# Sprint Planning 3
**Periodo:** 23/02/2026 – 04/03/2026


## Sprint Goal
Completare le funzionalità CRUD, introdurre la gestione della sessione utente ed estendere il sistema di persistenza ai livelli di gioco, intervenendo sull’architettura applicativa per garantire una piena conformità al pattern MVC e rafforzare il disaccoppiamento tra View e Controller.


---


## Deadline
La scadenza dello sprint è il **04/03/2026**.


---


## Task pianificati per lo Sprint


### Utente
**Interfaccia modifica utente** — sviluppo della schermata per modificare il nome utente, con validazione e messaggi di errore.


**Interfaccia eliminazione utente** — implementazione della funzionalità per eliminare l'account utente, con conferma e pulizia dei dati.


**Visualizzazione livelli** — interfaccia per mostrare i livelli disponibili all'interno dell'applicazione.


### Persistenza
**Aggiornamento storage** — estensione del sistema di persistenza per supportare modifica ed eliminazione degli utenti e dei livelli.




### Quality Assurance
**Test CRUD completo** — estensione dei test per coprire modifica ed eliminazione utenti.


---


## Sprint Review


### Progresso
Lo sprint ha completato con successo il CRUD utente, includendo interfaccia per modifica ed eliminazione, persistenza aggiornata e test estesi. Inoltre, durante lo sviluppo è emerso che, per gestire la navigazione tra più schermate(Statistiche, Modifica Utente) e mantenere lo stato dell'utente è necessaria una Sessione utente. L'autenticazione è stata perciò integrata con l’implementazione della gestione della sessione utente.


L'introduzione dell'interfaccia di visualizzazione dei livelli, ha posto le basi per lavorare sull'interazione utente-quiz.
Infine, oltre all'implementazione delle nuove funzionalità, una parte significativa dello Sprint è stata dedicata al refactoring strutturale. È stata operata una migliore separazione tra la logica di business e la presentazione.
Grazie alla migrazione di alcune logiche che erano presenti nelle View e che sono state inserite nei rispettivi Controller.


### Architettura
Nessuna modifica sostanziale all'architettura esistente. La persistenza dei dati è stata estesa per supportare le nuove operazioni CRUD e l'introduzione dei livelli.


### Utente
Il CRUD utente è ora completo: registrazione, login, modifica ed eliminazione sono operativi con validazione e salvataggio.


### Qualità
Tutti i test sono passati, inclusi quelli per le nuove operazioni CRUD. L'infrastruttura di testing è robusta e copre i casi limite.
La rifattorizzazione del codice ha migliorato la separazione dei componenti del progetto, rendendolo più chiaro e manutenibile.




---


## Sprint Retrospective


### Cosa è andato bene
- L'estensione del CRUD è stata integrata senza problemi grazie alla struttura MVC consolidata.
- La persistenza si è dimostrata flessibile per aggiungere operazioni di modifica ed eliminazione.
- L’utilizzo di branch specifici per suddividere i diversi lavori si è dimostrato efficace e non ha generato problemi durante le operazioni di merge.


### Difficoltà incontrate
- Gestione della conferma per l'eliminazione utente ha richiesto attenzione per evitare cancellazioni accidentali.
- Validazione dei campi in modifica ha rivelato la necessità di controlli più rigorosi sui dati esistenti.


---


## Miglioramenti previsti per Sprint 4
- Iniziare l'implementazione dei quiz con modellazione delle domande e risposte (risposta multipla e fill-in-the-blank).
- Implementare la valutazione automatica delle risposte e feedback immediato.
- Progettare l'interfaccia grafica per l'interazione Utente-Quiz.
- Consolidare il percorso didattico strutturato per livelli.
- Aggiungere contenuti teorici (Theory) per ciascun livello.
- Completare la rimozione della logica applicativa residua dalle View, trasferendola nei Controller.
- Migliorare la GUI rendendola più responsiva.

