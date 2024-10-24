%code imports {
  import java.io.IOException;
  import com.java.parser.ast.ASTree;
  import com.java.parser.ast.node.*;
  import com.java.lexer.Token;
  import com.java.parser.ast.node.type.*;
}

%code {
	private static ASTree ast;
    public static ASTree makeAST(com.java.lexer.Lexer lexer) throws IOException {
		LexerAdapter lexerAdapter = new LexerAdapter(lexer);
		Parser p = new Parser(lexerAdapter);
		p.parse();
		return ast;
	}
}

%define api.prefix {Parser}
%define api.parser.class {Parser}
%define api.parser.public
%define parse.error detailed
%define api.value.type {Object}
%define api.parser.final
%define api.package {com.java.parser}

// Token Declarations

%token <Token> Less LessEqual Greater GreaterEqual Equal NotEqual
%token <Token> Plus Minus Slash Star
%token <Token> And Or Xor Not
%token <Token> Is Dot Assignment Arrow Comma Semicolon
%token <Token> Int Real Bool String Empty Func
%token <Token> OpenParen CloseParen OpenBrace CloseBrace OpenBracket CloseBracket
%token <Token> Var ReadInt ReadReal ReadString Print Return If Then Else End While For In Loop
%token <Token> StringLiteral DoubleLiteral IntLiteral BooleanLiteral
%token <Token> Identifier
%type <Token> type_indicator read_type

// Non-Terminals Declarations

%type <ASTNode> program statement
%type <ASTNode> assignment_statement var_declaration_statement print_statement return_statement if_statement loop_statement
%type <ASTNode> expression relation factor term tail read_statement access_tail
%type <ASTNode> loop_body function_literal array array_tail tuple_tail reference tuple
%type <ASTNode> expression_statement func_tail unary_expression second_order_algebraic first_order_algebraic
%type <ASTList> consecutive_statements function_args expressions_comma statements_list array_data
%type <ASTList> consecutive_declarations consecutive_access_tail
%type <TokenList> parameters
%type <TupleList> tuple_data

%start program

%%

program:
	statements_list {ast = new ASTree($1);}
	;

// Statements

statements_list:
    %empty { $$ = new ASTList(); }
    | statement statements_list {$$ = new ASTList($1, $2); }
    ;

statement:
    expression_statement
    | assignment_statement
    | var_declaration_statement
    | if_statement
    | loop_statement
    | return_statement
    | read_statement
    | print_statement
    ;

// Concrete Statements

expression_statement:
    expression Semicolon {
        $$ = new ExpressionStatement($1);
    }
    ;

assignment_statement:
    Identifier Assignment expression Semicolon {
        $$ = new IdentifierAssign($1, $3);
    }
    | Identifier consecutive_access_tail Assignment expression Semicolon {
        $$ = new ReferenceAssign($1, $2, $4);
    }
    ;

var_declaration_statement:
    Var consecutive_declarations Semicolon {
        $$ = new MultipleDeclaration($2);
    }
    | Var parameters Semicolon {
        $$ = new VarDecl($2, null);
    }
    ;

if_statement:
    If expression Then consecutive_statements Else consecutive_statements End {
        $$ = new If($2, $4, $6);
    }
    | If expression Then consecutive_statements End {
        $$ = new If($2, $4, null);
    }
    ;

loop_statement:
    While expression loop_body {
        $$ = new While($2, $3);
    }
    | For Identifier In Identifier loop_body {
        $$ = new For($2, $4, $5);
    }
    ;

loop_body:
    Loop consecutive_statements End {
        $$ = new LoopBody($2);
    }

return_statement:
    Return expression Semicolon {
        $$ = new Return($2);
    }
    | Return Semicolon {
        $$ = new Return(null);
    }
    ;

read_statement:
    read_type expression Semicolon {
        $$ = new ReadStatement($1, $2);
    }
    ;

print_statement:
    Print expressions_comma Semicolon {
        $$ = new Print($2);
    }
    ;

// Possible Statement Parts

expression:
    relation
    | relation Or relation {
        $$ = new LogicalOp($2, $1, $3);
    }
    | relation And relation {
        $$ = new LogicalOp($2, $1, $3);
    }
    | relation Xor relation {
        $$ = new LogicalOp($2, $1, $3);
    }
    | function_literal
    ;

relation:
    factor
    | factor Less factor {
        $$ = new ComparisonOp($2, $1, $3);
    }
    | factor LessEqual factor {
        $$ = new ComparisonOp($2, $1, $3);
    }
    | factor Greater factor {
        $$ = new ComparisonOp($2, $1, $3);
    }
    | factor GreaterEqual factor {
        $$ = new ComparisonOp($2, $1, $3);
    }
    | factor Equal factor {
        $$ = new ComparisonOp($2, $1, $3);
    }
    | factor NotEqual factor {
        $$ = new ComparisonOp($2, $1, $3);
    }
    ;

factor:
    second_order_algebraic
    ;

second_order_algebraic:
    first_order_algebraic
    | second_order_algebraic Plus first_order_algebraic {
        $$ = new BinaryOp($2, $1, $3);
    }
    | second_order_algebraic Minus first_order_algebraic {
        $$ = new BinaryOp($2, $1, $3);
    }
    ;

first_order_algebraic:
    unary_expression
    | first_order_algebraic Star unary_expression {
        $$ = new BinaryOp($2, $1, $3);
    }
    | first_order_algebraic Slash unary_expression {
        $$ = new BinaryOp($2, $1, $3);
    }
    ;

unary_expression:
    term
    | OpenParen second_order_algebraic CloseParen {
        $$ = new UnaryOp(null, $2);
    }
    | Plus term {
        $$ = new UnaryOp($1, $2);
    }
    | Minus term {
        $$ = new UnaryOp($1, $2);
    }
    | Not term {
        $$ = new UnaryOp($1, $2);
    }
    ;

function_literal:
    Func OpenParen parameters CloseParen Is consecutive_statements End {
        $$ = new FunctionLiteral($3, $6);
    }
    | Func OpenParen parameters CloseParen Arrow expression {
        $$ = new FunctionLiteral($3, $6);
    }
    ;

// Atomic Term

term:
    IntLiteral {
        $$ = new TokenLiteral($1);
    }
    | DoubleLiteral {
        $$ = new TokenLiteral($1);
    }
    | StringLiteral {
        $$ = new TokenLiteral($1);
    }
    | BooleanLiteral {
        $$ = new TokenLiteral($1);
    }
    | reference
    | reference Is type_indicator {
        $$ = new ReferenceType($1, $3);
    }
    | array
    | tuple
    ;

reference:
    Identifier tail {
        $$ = new ReferenceTail($1, $2);
    }
    | Identifier consecutive_access_tail {
        $$ = new ReferenceTail($1, $2);
    }
    ;

array:
    OpenBracket CloseBracket {
        $$ = new Array(null);
    }
    | OpenBracket array_data CloseBracket {
        $$ = new Array($2);
    }
    ;

tuple:
    OpenBrace CloseBrace {
        $$ = new Tuple(null);
    }
    | OpenBrace tuple_data CloseBrace {
        $$ = new Tuple($2);
    }
    ;

// Additional Token Set Declarations

type_indicator:
    Int
    | Real
    | Bool
    | String
    | Empty
    | Func
    ;

read_type:
    ReadInt
    | ReadReal
    | ReadString
    ;

// Tail

tail:
    %empty {
        $$ = new EmptyTail();
    }
    | func_tail
    ;

access_tail:
    array_tail
    | tuple_tail
    ;

array_tail:
    OpenBracket expression CloseBracket {
        $$ = new ArrayAccess($2);
    }
    ;

tuple_tail:
    Dot IntLiteral {
        $$ = new TupleAccess($2, null);
    }
    | Dot Identifier {
        $$ = new TupleAccess(null, $2);
    }
    ;

func_tail:
    OpenParen function_args CloseParen {
        $$ = new FunctionCall($2);
    }
    ;

// Several Objects In One Node

consecutive_declarations:
    Identifier Assignment expression {
        $$ = new ASTList(new VarDecl($1, $3));
    }
    | consecutive_declarations Comma Identifier Assignment expression {
        $1.append(new VarDecl($3, $5));
        $$ = $1;
    }
    ;

consecutive_access_tail:
    access_tail {
        $$ = new ASTList($1);
    }
    | consecutive_access_tail access_tail {
        $1.append($2);
        $$ = $1;
    }
    ;

expressions_comma:
    expression {
        $$ = new ASTList($1);
    }
    | expressions_comma Comma expression {
        $1.append($3);
        $$ = $1;
    }
    ;

array_data:
    expression {
        $$ = new ASTList($1);
    }
    | array_data Comma expression {
        $1.append($3);
        $$ = $1;
    }
    ;

tuple_data:
    expression {
        $$ = new TupleList($1);
    }
    | Identifier Assignment expression {
        $$ = new TupleList($1, $3);
    }
    | tuple_data Comma expression {
        $1.append($3);
        $$ = $1;
    }
    | tuple_data Comma Identifier Assignment expression {
        $1.append($3, $5);
        $$ = $1;
    }
    ;

function_args:
    %empty {
        $$ = new ASTList();
    }
    | expression {
        $$ = new ASTList($1);
    }
    | function_args Comma expression {
        $1.append($3);
        $$ = $1;
    }
    ;

parameters:
    %empty {
        $$ = new TokenList();
    }
    | Identifier {
        $$ = new TokenList($1);
    }
    | parameters Comma Identifier {
        $1.append($3);
        $$ = $1;
    }
    ;

consecutive_statements:
    statement {
        $$ = new ASTList($1);
    }
    | consecutive_statements statement {
        $1.append($2);
        $$ = $1;
    }
    ;

%%
