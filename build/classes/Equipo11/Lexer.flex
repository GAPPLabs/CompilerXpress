package Equipo11;
import static Equipo11.Tokens.*;
%%
%class Lexer
%type Tokens
H=[a-z]+
M=[A-Z]+
D=[0-9]+
R=[0-9]*
L=[a-zA-Z]+
C=[a-zA-Z0-9_´¨+"-"*~'`¿?¡!#$%&/°¬ñÑáéíóúÁÉÍÓÚ"("")""[""]""{""}"".",";"":""^""¨""=""<"">""\""]+ 
//espacio=[\t\r]+
espacio=[\r]+
espacio2=[\t]+
espacio3=[ ]+

%{
    public String lexeme;
%}
%%

/* Tipos de datos */
( #Comp | #Desc | #Num ) {lexeme=yytext(); return T_dato;}

/* Palabra reservada While */
( while ) {lexeme=yytext(); return While;}

/* Operador Igual */
( "=" ) {lexeme=yytext(); return Igual;}

/* Operador Suma */
( "+" ) {lexeme=yytext(); return Suma;}

/* Operador Resta */
( "-" ) {lexeme=yytext(); return Resta;}

/* Operador Multiplicacion */
( "*" ) {lexeme=yytext(); return Multiplicacion;}

/* Operador Division */
( "/" ) {lexeme=yytext(); return Division;}

/* Operadores logicos y BOOLEANOS */
( "&&" | "||"  ) {lexeme=yytext(); return Op_logico;}

/*Operadores Relacionales */
( ">" | "<" | "==" | "!=" | ">=" | "<="  ) {lexeme = yytext(); return Op_relacional;}

/*Operadores Booleanos
(true | false)      {lexeme = yytext(); return Op_booleano;}*/

/* Parentesis de apertura */
( "(" ) {lexeme=yytext(); return Parentesis_a;}

/* Parentesis de cierre */
( ")" ) {lexeme=yytext(); return Parentesis_c;}

/* Llave de apertura */
( "{" ) {lexeme=yytext(); return Llave_a;}

/* Llave de cierre */
( "}" ) {lexeme=yytext(); return Llave_c;}

/* Punto y coma */
( ";" ) {lexeme=yytext(); return P_coma;}

 , {lexeme=yytext(); return P_coma;}

/* Identificador */
(PR5{R}" " ) {lexeme=yytext(); return Identificador;}

/* Numero */
(-{R}99| {R}99 ) {lexeme=yytext(); return Numero;}

/* Numero CON DECIMALES */
(-{R}99"."{D})|({R}99"."{D}) {lexeme=yytext(); return NumeroDecimal;}

/* Espacios en blanco */
{espacio} {/*Ignore*/}
{espacio2} {/*Ignore*/}
{espacio3} {/*Ignore*/}

/* Comentarios 
( "//"(.)* ) {/*Ignore*/}*/

/* Salto de linea */
( "\n" ) {return Linea;}

/* Comillas */
( "%" ) {lexeme=yytext(); return Comillas;}


/* DEVOLVER TOKEN ERROR */

 ({R} |{H} |{D} | {M} | {L} | "DO" | "Do" | "dO" | "WHile" | "While" | "WHIle" | "WHILe" | "WHIlE" | "wHILE" | "whILE" | "whiLE"  | "whilE" ) {lexeme=yytext();return ERROR;}

/* DEVOLVER TOKEN ERROR */
 .  {lexeme=yytext();return ERROR;}

/* ERROR */
({L}{D}.) {lexeme=yytext(); return ERROR;}

/* ERROR */
({H}-) {lexeme=yytext(); return ERROR;}

/* ERROR */
({M}-) {lexeme=yytext(); return ERROR;}
/* ERROR */
({H}_) {lexeme=yytext(); return ERROR;}

/* ERROR */
({M}_) {lexeme=yytext(); return ERROR;}
/* Error de analisis */
 ({C}|-{C}) {lexeme=yytext();return ERROR;}