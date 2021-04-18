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

Use the command **make** to clean, compile, execute the parser and at the end the output Java code will execute.

I have made in the jflex those macros.

- If = (if){WhiteSpace}*[(]
> finds an if with any space beetween that ends with (
- Variable = [a-zA-Z][a-zA-Z0-9]*
> finds a variable
- Function = {Variable}{WhiteSpace}*[(]
> finds a variable with any space beetween that ends (

In the cup all the functions declarations must be before any calls.

#### **Arguments**

I have separate the cases where the arguments of an function are valid.
- for the arguments in the declarations of a function can be **only variables**.
- inside a functions body there can be **variables**, **Strings**, **function call** and an **IF** as arguments of an function call.
- functions calls in the main can have as arguments **Strings**, **function call** and **IF**.

#### **IF**

- An **IF** can have as operator only a **prefix** or a **suffix**.
- Inside an **IF** can be another **IF**.

#### **Prefix Suffix**

- a prefix b == b.startsWith(a)
- a suffix b == b.endsWith(a)

- left or right from prefix or suffix can be everything that is a string.