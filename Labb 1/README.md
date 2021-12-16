## Konkordans

Målen för labb 1 är att

- öva att programmera efter en funktionsspecifikation,
- bygga en datastruktur som har litet (konstant) primärminnesbehov och som ändå kan söka snabbt i en stor fil på sekundärminne,
- arbeta med texter lagrade med olika teckenkodning,
- testa och utvärdera parprogrammering.

En konkordans är en databas där man kan slå upp ord och då få se alla förekomster av ordet tillsammans med orden närmast före och närmast efter i texten. Detta är ett stort hjälpmedel för lingvister som vill undersöka hur olika ord används i språket.

I denna uppgift ska du skriva ett program som givet en text skapar en konkordansdatabas och ett program som frågar användaren efter ord, slår upp ordet och presenterar alla förekomster av ordet i sitt sammanhang. Det är viktigt att varje sökning går mycket snabbt så det gäller att det första programmet lagrar konkordansen på ett sådant sätt att det går snabbt att göra en sökning.

Exempel på körning av sökprogrammet:
```bash
$ java Konkordans algoritmens
Det finns 12 förekomster av ordet.
let.  Historia  Cooley  Tukey-algoritmens historia börjar kring år 1805
 att jämföra målvariabeln med algoritmens estimering av målvariabeln. V
alsdivision skulle detta vara algoritmens totala komplexitet. Dock kvar
maximala använda minnet under algoritmens gång är det intressanta. Form
använda binär exponentiering. Algoritmens korrekthet förklaras som följ
 rötter kommer att klara båda algoritmens steg. Stage Junior 2006 Stage
r till nod 4 (se nästa bild). Algoritmens steg upprepas, och det är nu
att lära en robothund att gå. Algoritmens förmåga att lösa problemet be
ma problem. Ett annat ord för algoritmens resursberoende är komplexitet
rterad listan är från början. Algoritmens komplexitet blir O (N²).  Ins
2006) har visat att Masreliez-algoritmens prestanda är relativt bättre
 det behövs även en algoritm. Algoritmens uppgifter är att dela in talp 
```
    
### Krav
Följande krav ställs på din lösning:

- Programmet ska vara skrivet i ett riktigt programspråk och inte något operativsystemnära skriptspråk eller liknande.
- Konkordansen ska inte skilja på stora och små bokstäver. Användaren ska alltså kunna skriva in alla sökfrågor med små bokstäver, stora bokstäver eller godtycklig blandning.
- Det givna programmet tokenizer.c på kurskatalogen (se nedan) definierar hur texten ska delas upp i enskilda ord.
- Konstruktionsprogrammet behöver inte vara jättesnabbt eftersom det bara ska köras en gång, men det måste vara någorlunda effektivt så att det kan skapa konkordansen på rimlig tid. Det får inte ta mer än tre minuter att skapa konkordansen på en Ubuntudator (utöver tiden att köra tokenizer och sort).
- Sökprogrammets utmatning ska inledas med en rad som anger antalet förekomster. Därefter ska varje förekomst av ordet presenteras på varje rad med till exempel 30 tecken före och 30 tecken efter. Ersätt radbyten med mellanslag. Om det finns fler än 25 förekomster ska programmet fråga användaren om vederbörande vill ha förekomsterna utskrivna på skärmen.
- Man ska kunna söka efter ett ord, till exempel bil, genom att i terminalfönstret ge kommandot ./konkordans bil (om du använt C, C++ eller liknande), python3 konkordans.py bil (om du använt Python) eller java Konkordans bil (om du använt Java). 
- Svaret (som alltså innehåller antalet förekomster men högst 25 rader med förekomster) måste komma inom en sekund på skolans Ubuntudatorer. Vid redovisningen ska programmet exekveras på en av skolans Ubuntudatorer (via fjärrinloggning, se nedan).
- Sökprogrammet ska inte läsa igenom hela texten och får inte använda speciellt mycket internminne. Internminnesbehovet ska inte växa med antalet distinkta ord i den ursprungliga texten (med andra ord ha konstant internminneskomplexitet). Du ska därför använda latmanshashning (se föreläsning 3) som datastruktur. Vid redovisningen ska du kunna motivera varför internminneskomplexiteten är konstant.