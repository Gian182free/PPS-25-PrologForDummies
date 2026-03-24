#  Processo di Sviluppo
##  Ruoli e Collaborazione

* **Product Owner – Gianfranco Branco** Si è occupato di definire la visione strategica e l'architettura del sistema, assicurando che lo sviluppo dei requisiti (utenti e livelli) rispondesse agli obiettivi di progetto. Ha coordinato le priorità del backlog e gestito la documentazione tecnica, partecipando attivamente alla parte implementativa e alla persistenza dati.

* **Scrum Master – Francesco Agnoletti** Ha facilitato l'organizzazione dei cicli di sviluppo e rimosso gli ostacoli tecnici, garantendo la fluidità del workflow e la qualità del codice. Si è focalizzato sulla logica d'interazione, curando lo sviluppo dei quiz e delle interfacce grafiche, contribuendo in modo operativo a tutte le fasi del progetto.

##  Metodologia SCRUM-inspired
Il ciclo di vita del software è stato scandito da sprint della durata di 10 giorni, garantendo flessibilità e adattamento ai requisiti.

### Meeting
* **Initial Planning**: Definizione dell'architettura e degli obiettivi a lungo termine.
* **Sprint Planning**: Selezione dei task, cercando di bilanciare i carichi di lavoro.
* **Daily Scrum**: Allineamenti rapidi (15 min) per sincronizzare lo sviluppo e identificare ostacoli.
* **Review & Retrospective**: Analisi del software prodotto e miglioramento delle pratiche di lavoro al termine di ogni sprint.

##  Gestione del Progetto
Per la gestione dei task del **Product Backlog** sono gestiti come segue:
1.  **Task Critici**: Assegnazione diretta durante il planning.
2.  **Task Self-Service**: Scelta autonoma dai membri del team una volta completati gli incarichi primari.

##  Versioning e Convenzioni
Il codice sorgente è gestito tramite **Git**, adottando un approccio basato su branch dedicati per funzionalità:

* **main**: branch dedicato esclusivamente alle release stabili.
* **branch di feature**: questi vengono creati ad hoc per ogni nuova funzionalità o modifica e successivamente integrati in `main` tramite merge.


## Definition of Done (DoD)
* **Superamento Test Automatizzati**: Il codice deve superare con successo la suite di test unitari (ScalaTest) dedicata ai componenti core, assicurando la stabilità delle logiche di business prima del rilascio in main.

* **Verifica della Compilazione Locale**: Il codice deve compilare correttamente nell'ambiente di sviluppo di entrambi i membri del team, garantendo l'assenza di conflitti di dipendenze o errori di sintassi prima del commit.

* **Documentazione Scaladoc**: I componenti core devono essere documentati tramite Scaladoc.

* **Codice Auto-esplicativo**: Il codice deve rispettare gli standard di Self-Documenting Code, utilizzando una nomenclatura chiara per variabili e metodi per minimizzare commenti ridondanti.

* **Standard Stilistici**: La formattazione del codice deve essere coerente con i parametri del progetto (Scalafmt) per garantire la leggibilità collettiva del repository.

##  Strategia di Qualità

### Test Driven Development (TDD)
Abbiamo applicato il paradigma TDD sui componenti core (Model e Controller):
1.  **Red**: Scrittura del test fallimentare (es. validazione login).
2.  **Green**: Implementazione minima per superare il test.
3.  **Refactor**: Ottimizzazione del codice mantenendo i test superati.

### Code Quality

Per l'uniformità del codice sorgente è stato adottato **Scalafmt**, uno strumento di formattazione automatica che assicura il rispetto degli standard stilistici condivisi dal team, migliorando la leggibilità e la manutenibilità del repository.

### Building
Per la gestione del ciclo di vita del software è stato adottato Sbt, utilizzato come strumento principale per la risoluzione automatica delle dipendenze, la compilazione del codice sorgente e l'esecuzione della suite di test.

### Report ###

Il report è stato realizzato con **Jekyll** e il tema Just the Docs, sfruttando l'integrazione nativa con GitHub Pages. I contenuti, redatti in Markdown, vengono poi versionati su Git.


---

<p align="right">
  <a href="requisiti.html"> Analisi dei Requisiti →</a>
</p>