## Rättstavning

Målen för labb 2 är att du ska

- öva att modifiera ett existerande program så att det fungerar likadant fast effektivare,
- bestämma en beräkningsordning för en dynamiskprogrammeringsalgoritm givet en rekursiv formulering,
- implementera en dynamiskprogrammeringsalgoritm givet en rekursiv formulering,
- ta hänsyn till effektivitet vid utveckling och implementation av en algoritm.

### Problem

Editeringsavståndet mellan två ord är det minimala antalet bokstavsoperationer som krävs för att transformera det ena ordet till det andra. 

Det finns tre tillåtna bokstavsoperationer:

    1. ta bort en av bokstäverna i ordet

    2. lägg till en bokstav någonstans i ordet

    3. byt ut en bokstav i ordet mot en annan bokstav

Till exempel kan ordet alroitm transformeras till algoritm genom att bokstaven r byts ut mot g (regel 3) och bokstaven r skjuts in efter bokstaven o (regel 2). Kedjan

alroitm -> algoitm -> algoritm

visar att editeringsavståndet mellan alroitm och algoritm är högst 2. Eftersom det inte går att transformera alroitm till algoritm i en enda bokstavsoperation så är editeringsavståndet mellan orden precis 2.

Ett vanligt sätt att ta fram rättstavningsförslag till ett felstavat ord är att helt enkelt returnera dom ord i ordlistan som har minst editeringsavstånd till det felstavade ordet. Programmet ska givet en ordlista och ett antal felstavade ord beräkna rättstavningsförslag på detta sätt.

### Specifikation
Indata består av två delar. Den första delen är ordlistan, som består av ett antal ord i utf-8-bokstavsordning, ett ord per rad. Denna del avslutas av en rad som bara innehåller ett '#'-tecken. Den andra delen är ett antal felstavade ord som ska rättstavas, ett ord per rad. Dom felstavade orden ingår inte i ordlistan. Varje ord i indata består bara av små bokstäver i svenska alfabetet (a-z, å, ä, ö), inga mellanslag, skiljetecken eller siffror.

Programmet ska för varje felstavat ord skriva ut en rad bestående av det felstavade ordet följt av det minimala editeringsavståndet inom parentes följt av en lista med alla ord i ordlistan som har minimalt editeringsavstånd till det felstavade ordet. Listan ska vara i bokstavsordning och varje ord i listan ska föregås av mellanslag. Ordlistan har högst en halv miljon ord och antalet felstavade ord i indata är högst 100.

### Exempel på körning


```bash
$ cat testmedordlista.indata | java Main 
labd (1) labb lagd land 
dabbbhud (4) anbud dabba nabbad 
$
```

### Automatisk testning
Det finns två kataloger, test och large som innehåller testfall. Dessa testfall består av par av filer med filändelserna ".indata" och ".utdata". Filerna med ändelsen ".indata" följer formatet som som beskrivs under stycket Specifikation ovan. Filerna med ändelsen ".utdata" följer formatet för utdata för programmet som beskrivs i stycket "Exempel på körning" ovan.

Om programmet startas med väljaren (växeln) -t så kommer det att köra testfallen som ligger i (den lokala) katalogen test.     

Exempelkörning:
```bash
$ java Main -t
Processing folder: ./test
Processing testcase: testmedordlista
CPU time for this test: 18 ms
Processing testcase: testmedordlista2
CPU time for this test: 1 ms
```
När optimeringen är klar kan programmet testas med större testfall i katalogen large med:
```bash
$ java Main -t large
```

När programmet är färdigt så ska det ta mindre än 1 sekund per testfall på en någorlunda modern PC eller Mac. Om det går mycket långsammare än så - avbryt testet genom att trycka Ctrl-C.

Det går naturligtvis bra att lägga till egna testfall i en egen katalog och köra:
```bash
$ java Main -t your_test_dir
```

Flera testkataloger kan köras genom att lista dessa efter "-t", till exempel
```bash
$ java Main -t test large

```

### Uppgift
Det givna Javaprogrammet löser visserligen ovanstående problem, men det tar timmar att få fram svaret. Du ska effektivisera programmet så att det klarar testfallen i large på en sekund och så att testfall klaras inom den tidsgräns som ges. 

Teoriuppgifterna ger uppslag om olika sätt att effektivisera programmet.

Ditt optimerade program ska ha samma in- och utmatning som det givna programmet och det måste fortfarande vara Java.

Algoritmen som beräknar editeringsavståndet ska använda dynamisk programmering. Beräkningsordningen ska väljas så att god minneslokalitet uppnås, det vill säga att läs-, skriv- och uppdateringsoperationer på dynprogmatrisen ska ligga så nära varandra som möjligt i datorns minne.