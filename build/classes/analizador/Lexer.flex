package analizador;
import static analizador.Token.*;
%%
%class Lexer
%type Token
L           = [a-zA-ZñÑáéíóúÁÉÍÓÚÃ&³±$/\­\.\^c\^\*\+]
D           = [0-9]
WHITE       = [\r]
SALTO       = [\n]
E           = \s
ESP         = ( [\t]* | [" "]*)
OP          = "operaciones" {ESP}
VARIABLE    = ([a-zA-Z]* | [0-9]*)
DEFINICION  = "definicion"
OPERADOR    = ["&" | "$" | "/" | "*" | "^"]
ALFANUM     = ({D}*|{L}*)
ELEMENT     = {ESP} (("\"")* ({ALFANUM} {ESP})*  ("\"")*) ","{1} {ESP}
ULTIMOELE   = {ESP} (("\"")* ({ALFANUM} {ESP})*  ("\"")*) {ESP}
ASIGNAR     = {VARIABLE}* {ESP} "=" {ESP}
CONJUNTO    = {ASIGNAR} ("\"")*  "{" {ELEMENT}*  {ULTIMOELE}{1} "}" ("\"")*
UNIVERSO    = "universo" {ESP} "=" {ESP} ("\"")* "{" {ELEMENT}+ {ULTIMOELE}{1} "}" ("\"")*
CONJUNTOV   = {ASIGNAR} "{" {ESP} "}"
OPERACION   = {VARIABLE}* {ESP} {OPERADOR}{1} {ESP} {VARIABLE}* ({ESP}{OPERADOR}{1} {ESP} {VARIABLE}*)*
OPERACIONES = "(" {OPERACION} ")" | {OPERACION}

//DEC_DEFINICION = "DEFINICION" {CONJUNTO}* | {CONJUNTOV}
//DEC_OPERACION  = "OPERACION" {OPERACIONES}*
%{
public String lexeme;
%}
%%
{WHITE}          {/*Ignore*/}
"EOF"            {/*Ignore*/}
 EOF             {/*Ignore*/}
//{E}              {/*Ignore*/}
{ESP}            {/*Ignore*/}
{DEFINICION}     {lexeme=yytext(); return DEFINICION;}
{OP}             {lexeme=yytext(); return OPERACION;}
{SALTO}          {lexeme=yytext(); return LINEA;}
{UNIVERSO}       {lexeme=yytext(); return UNIVERSO;}
{CONJUNTO}       {lexeme=yytext(); return CONJUNTO;}
{CONJUNTOV}      {lexeme=yytext(); return CONJUNTO_VACIO;}
{OPERACIONES}    {lexeme=yytext(); return OPERACIONES;}



. {return ERROR;}