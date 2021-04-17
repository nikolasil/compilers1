# Nikolaos Iliopoulos 1115201800332
## Compilers 1

### Part1

#### Grammar

```c
goal -> term exp

exp -> '+' term exp
      | '-' term exp
      | e 

term -> number termTail

termTail -> '*' '*' number termTail
       | e

number -> digit numTail
        | '(' term exp ')'

numTail -> number
        | e

digit -> '0' | '1' | '2' | '3' | '4' | '5' | '6' | '7' | '8' | '9'
```

### Part2