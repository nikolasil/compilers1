# Nikolaos Iliopoulos 1115201800332
## Compilers 1

### Part1

Use the command **make** to clean, compile and execute the code. 
> There are many comments in the code that explain everything.

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

In order have precedence for **. I always first check for ** and if the lookahead dont match i go and look for + and -.
I achieve this in the term where i always have a termTail after a number. termTail is looking for ** and then is calling expr that is looking for + and -.
> I took this logic from the presentations

I treat the () as a number because eventually the () will be evaluated in a number if everything is fine inside them.

In order to have multidigit number i use the logic of the numTail.
I made number type of String in my code and the single digits are beeing concatenated with each other and in the end the number is a long String that is beeing parsed into an Integer to make the +, - and ** operations.

### Part2