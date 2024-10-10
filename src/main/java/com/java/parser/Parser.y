%define api.prefix {Parser}
%define api.parser.class {Parser}
%define api.parser.public
%define parse.error verbose
%define api.value.type {Object}
%define api.parser.final
%define api.package {com.java.parser}

%code imports{
  import java.io.InputStream;
  import java.io.InputStreamReader;
  import java.io.Reader;
  import java.io.IOException;
}

%token <String> Less LessEqual Greater GreaterEqual Equal NotEqual Plus Minus Slash
%token <String> And Or Xor Not

%token <String> Is Dot Assignment Arrow Comma Semicolon

%token <String> Int Real Bool String Empty Func

%token <String> OpenParen CloseParen OpenBrace CloseBrace OpenBracket CloseBracket

%token <String> Var ReadInt ReadReal ReadString Print Return If Then Else End While For In Loop

%token <String> StringLiteral
%token <Double> DoubleLiteral
%token <Integer> IntLiteral
%token <Boolean> BooleanLiteral

%token <String> Identifier


%%
/*
Grammatica:
    S -> (S)S | epsilon
*/

prog:
  srule
;

srule:
    OpenParen srule CloseParen srule
    |
;
%%
