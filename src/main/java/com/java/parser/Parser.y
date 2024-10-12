%code imports {
  import java.io.InputStream;
  import java.io.InputStreamReader;
  import java.io.Reader;
  import java.io.IOException;
  import com.java.parser.ast.node.*;
  import com.java.parser.ast.node.type.*;
  import com.java.lexer.Token;
}

%define api.prefix {Parser}
%define api.parser.class {Parser}
%define api.parser.public
%define parse.error verbose
%define api.value.type {Object}
%define api.parser.final
%define api.package {com.java.parser}

// Token declarations

%token <Token> Less LessEqual Greater GreaterEqual Equal NotEqual
%token <Token> Plus Minus Slash Star
%token <Token> And Or Xor Not
%token <Token> Is Dot Assignment Arrow Comma Semicolon
%token <Token> Int Real Bool String Empty Func
%token <Token> OpenParen CloseParen OpenBrace CloseBrace OpenBracket CloseBracket
%token <Token> Var ReadInt ReadReal ReadString Print Return If Then Else End While For In Loop
%token <Token> StringLiteral DoubleLiteral IntLiteral BooleanLiteral
%token <Token> Identifier

// Declare types for non-terminals
%type <ASTNode> program statement assignment_statement var_declaration_statement print_statement return_statement if_statement loop_statement
%type <ASTNode> expression expression_list relation factor term unary primary tail body type_indicator named_expression_list
%type <ASTNode> identifier_list loop_body tail_list fun_body parameters function_literal array_literal tuple_literal
%type <ASTNode> literal

%start program

%%

// Grammar Rules

program:
    program statement
    | statement
    ;

expression_list:
    /* empty */ {
        $$ = new ExpressionListNode();
    }
    | expression {
        $$ = new ExpressionListNode($1);
    }
    | expression_list Comma expression {
        $1.append($3);
        $$ = $1;
    }

named_expression_list:
    /* empty */ {
        $$ = new NamedExpressionListNode();
    }
    | Identifier Assignment expression {
        $$ = new NamedExpressionListNode($1, $3);
    }
    | named_expression_list Comma Identifier Assignment expression {
        $1.append($3, $5);
        $$ = $1;
    }

identifier_list:
    /* empty */ {
        $$ = new IdentifierListNode();
    }
    | Identifier {
        $$ = new IdentifierListNode($1);
    }
    | identifier_list Comma Identifier {
        $1.append($3);
        $$ = $1;
    }

statement:
    assignment_statement
    | var_declaration_statement
    | if_statement
    | loop_statement
    | return_statement
    | print_statement
    ;

assignment_statement:
    primary Assignment expression Semicolon {
        $$ = new AssignNode($1, $3);
    }
    ;

var_declaration_statement:
    Var Identifier Assignment expression Semicolon {
        $$ = new VarDeclNode($2, $4);
    }
    | Var Identifier Semicolon {
        $$ = new VarDeclNode($2, null);
    }
    ;

print_statement:
    Print expression Semicolon {
        $$ = new PrintNode($2);
    }
    | Print expression Comma expression_list Semicolon {
        $$ = new PrintNode($2, $4);
    }
    ;

return_statement:
    Return expression Semicolon {
        $$ = new ReturnNode($2);
    }
    | Return Semicolon {
        $$ = new ReturnNode(null);
    }
    ;

if_statement:
    If expression Then body Else body End {
        $$ = new IfNode($2, $4, $6);
    }
    | If expression Then body End Semicolon {
        $$ = new IfNode($2, $4, null);
    }
    ;

loop_statement:
    While expression loop_body {
        $$ = new WhileNode($2, $3);
    }
    | For Identifier In type_indicator loop_body {
        $$ = new ForNode($2, $4, $5);
    }
    ;

loop_body:
    Loop body End {
        $$ = new LoopBodyNode($2);
    }

body:
    statement
    | body statement
    ;

expression:
    relation {
        $$ = $1;
    }
    | expression Or relation {
        $$ = new BinaryOpNode("or", $1, $3);
    }
    | expression And relation {
        $$ = new BinaryOpNode("and", $1, $3);
    }
    | expression Xor relation {
        $$ = new BinaryOpNode("xor", $1, $3);
    }
    ;

relation:
    factor {
        $$ = $1;
    }
    | factor Less factor {
        $$ = new BinaryOpNode("<", $1, $3);
    }
    | factor LessEqual factor {
        $$ = new BinaryOpNode("<=", $1, $3);
    }
    | factor Greater factor {
        $$ = new BinaryOpNode(">", $1, $3);
    }
    | factor GreaterEqual factor {
        $$ = new BinaryOpNode(">=", $1, $3);
    }
    | factor Equal factor {
        $$ = new BinaryOpNode("==", $1, $3);
    }
    | factor NotEqual factor {
        $$ = new BinaryOpNode("!=", $1, $3);
    }
    ;

factor:
    term {
        $$ = $1;
    }
    | factor Plus term {
        $$ = new BinaryOpNode(Plus, $1, $3);
    }
    | factor Minus term {
        $$ = new BinaryOpNode(Minus, $1, $3);
    }
    ;

term:
    unary {
        $$ = $1;
    }
    | term Star unary {
        $$ = new BinaryOpNode(Star, $1, $3);
    }
    | term Slash unary {
        $$ = new BinaryOpNode(Slash, $1, $3);
    }
    ;

unary:
    primary {
        $$ = $1;
    }
    | Plus unary {
        $$ = $2;
    }
    | Minus unary {
        $$ = new UnaryOpNode(Minus, $2);
    }
    | Not unary {
        $$ = new UnaryOpNode(Not, $2);
    }
    | unary Is type_indicator {
        $$ = new UnaryOpNode(Is, $1, $3);
    }
    | literal {
        $$ = $1;
    }
    | OpenBracket expression CloseBracket {
        $$ = $2;
    }
    ;

tail_list:
    /* empty */ {
        $$ = new TailAccessListNode();
    }
    | tail {
        $$ = new TailAccessListNode($1);
    }
    | tail_list Comma tail {
        $1.append($3);
        $$ = $1;
    }

primary:
    Identifier {
        $$ = new VarNode($1);
    }
    | primary tail_list {
        $$ = new PrimaryAccessNode($1, $2);
    }
    | ReadInt {
        $$ = new ReadIntNode();
    }
    | ReadReal {
        $$ = new ReadRealNode();
    }
    | ReadString {
        $$ = new ReadStringNode();
    }
    ;

tail:
    Dot IntLiteral {
        $$ = new TupleAccessNode($2);
    }
    | Dot Identifier {
        $$ = new TupleAccessNode($2);
    }
    | OpenBracket expression CloseBracket {
        $$ = new ArrayAccessNode($$2);
    }
    | OpenParen expression CloseParen {
        $$ = new FunctionCallNode($2);
    }
    | OpenParen expression Comma expression_list CloseParen {
        $$ = new FunctionCallNode($2, $4);
    }
    ;

type_indicator:
    Int {
        $$ = new TypeNode($1);
    }
    | Real {
        $$ = new TypeNode($1);
    }
    | Bool {
        $$ = new TypeNode($1);
    }
    | String {
        $$ = new TypeNode($1);
    }
    | Empty {
        $$ = new TypeNode($1);
    }
    | OpenBracket CloseBracket {
        $$ = new TypeNode(Types.VectorType());
    }
    | OpenBrace CloseBrace {
        $$ = new TypeNode(Types.TupleType());
    }
    | Func {
        $$ = new TypeNode($1);
    }
    /* Need one more: Expression .. Expression; I didn't understand what it is */

literal:
    IntLiteral {
            $$ = new LiteralNode($1);
    }
    | DoubleLiteral {
        $$ = new LiteralNode($1);
    }
    | StringLiteral {
        $$ = new LiteralNode($1);
    }
    | BooleanLiteral {
        $$ = new LiteralNode($1);
    }
    | array_literal {
        $$ = new LiteralNode($1);
    }
    | tuple_literal {
        $$ = new LiteralNode($1);
    }

array_literal:
    OpenBracket expression_list CloseBracket {
        $$ = new ArrayLiteralNode($2);
    }

tuple_literal:
    /* Need to handle mixed list */
    OpenBrace named_expression_list CloseBrace {
        $$ = new TupleLiteralNode($2);
    }
    | OpenBrace expression_list CloseBrace {
        $$ = new TupleLiteralNode($2);
    }

function_literal:
    Func fun_body {
        $$ = new FunctionLiteralNode($2);
    }
    | Func parameters fun_body {
        $$ = new FunctionLiteralNode($2, $3);
    }

parameters:
    OpenParen Identifier CloseParen {
        $$ = new ParametersNode($2);
    }
    | OpenParen Identifier Comma identifier_list CloseParen {
        $$ = new ParametersNode($2, $4);
    }

fun_body:
    Is body End {
        $$ = new FunBodyNode($2);
    }
    | Arrow expression {
        $$ = new FunBodyNode($2);
    }

%%
