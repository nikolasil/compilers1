## Build-in Methods & Fields

### Variables

In order to activate/declare the variables you must define **%char for yychar**, **%line for yyline** and  **%column for yycollumn** at the start of the code

- int yychar
  > Represents the number of characters processed since the start of the input
- int yyline 
  > Represents the number of line breaks processed since the start of input
- int yycollumn
  > Represents the number of characters processed since the start of the current line

### Getters
- String yytext()
  > Returns the text matched by the current rule
- int yylength()
  > Returns the length of the text matched by the current rule
- int yystate()
  > Returns the current state

### Setters
- void yybegin(int lexicalState)
  > Sets the current state

## Execution

jflex filename.jflex