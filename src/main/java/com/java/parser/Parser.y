%code imports {
  import java.io.InputStream;
  import java.io.InputStreamReader;
  import java.io.Reader;
  import java.io.IOException;
  import com.java.lexer.*;
  import com.java.parser.ast.ASTree;
  import com.java.parser.ast.node.*;
  import com.java.parser.ast.node.type.*;
  import com.java.lexer.Token;
}

%code {
	static ASTree ast;
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
%type <ASTNode> expression relation factor term unary primary tail body type_indicator
%type <ASTNode> loop_body fun_body parameters function_literal
%type <ASTNode> literal
%type <ASTListNode> expression_list named_expression_list tail_list statements_list
%type <TokenListNode> identifier_list

%left Or  // "or" logical operator, left-associative
%left And  // "and" logical operator, left-associative
%left Xor  // "xor" logical operator, left-associative

%right Not  // "not" logical operator, right-associative

%nonassoc Less LessEqual Greater GreaterEqual Equal NotEqual  // Comparison operators, non-associative
%left Is  // "is" type-checking operator, right-associative

%left Plus Minus  // "+" and "-" arithmetic operators, left-associative
%left Star Slash  // "*" and "/" arithmetic operators, left-associative

%right Assignment
%right Arrow  // "=>" lambda arrow, right-associative

%left Dot  // "." for member access, left-associative

%start program

%%

// Grammar Rules

program:
	statements_list {ast = new ASTree($1);}
	;

statements_list:
    /* empty */ { $$ = new ASTListNode(); }
    | statement statements_list {$$ = new ASTListNode($1, $2); }
    ;

expression_list:
    /* empty */ {
        $$ = new ASTListNode();
    }
    | expression {
        $$ = new ASTListNode($1);
    }
    | expression_list Dot expression {
        $1.append($3);
        $$ = $1;
    }

named_expression_list:
    /* empty */ {
        $$ = new ASTListNode();
    }
    | Identifier Assignment expression {
        $$ = new ASTListNode($1, $3);
    }
    | named_expression_list Comma Identifier Assignment expression {
        $1.append($3, $5);
        $$ = $1;
    }

identifier_list:
    /* empty */ {
        $$ = new TokenListNode();
    }
    | Identifier {
        $$ = new TokenListNode($1);
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
    Print expression_list Semicolon {
        $$ = new PrintNode($2);
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
        $$ = new BinaryOpNode($2, $1, $3);
    }
    | expression And relation {
        $$ = new BinaryOpNode($2, $1, $3);
    }
    | expression Xor relation {
        $$ = new BinaryOpNode($2, $1, $3);
    }
    ;

relation:
    factor {
        $$ = $1;
    }
    | factor Less factor {
        $$ = new BinaryOpNode($2, $1, $3);
    }
    | factor LessEqual factor {
        $$ = new BinaryOpNode($2, $1, $3);
    }
    | factor Greater factor {
        $$ = new BinaryOpNode($2, $1, $3);
    }
    | factor GreaterEqual factor {
        $$ = new BinaryOpNode($2, $1, $3);
    }
    | factor Equal factor {
        $$ = new BinaryOpNode($2, $1, $3);
    }
    | factor NotEqual factor {
        $$ = new BinaryOpNode($2, $1, $3);
    }
    ;

factor:
    term {
        $$ = $1;
    }
    | factor Plus term {
        $$ = new BinaryOpNode($2, $1, $3);
    }
    | factor Minus term {
        $$ = new BinaryOpNode($2, $1, $3);
    }
    ;

term:
    unary {
        $$ = $1;
    }
    | term Star unary {
        $$ = new BinaryOpNode($2, $1, $3);
    }
    | term Slash unary {
        $$ = new BinaryOpNode($2, $1, $3);
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
        $$ = new UnaryOpNode($1, $2);
    }
    | Not unary {
        $$ = new UnaryOpNode($1, $2);
    }
    | primary Is type_indicator {
        $$ = new UnaryOpNode($2, $1, $3);
    }
    | literal {
        $$ = $1;
    }
    | OpenParen expression CloseParen {
        $$ = $2;
    }
    ;

tail_list:
    /* empty */ {
        $$ = new ASTListNode();
    }
    | tail {
        $$ = new ASTListNode($1);
    }
    | tail_list Comma tail {
        $1.append($3);
        $$ = $1;
    }

primary:
    Identifier tail_list {
        $$ = new VarNode($1, $2);
    }
    | ReadInt {
        $$ = new ReadNode(ReadType.INT);
    }
    | ReadReal {
        $$ = new ReadNode(ReadType.REAL);
    }
    | ReadString {
        $$ = new ReadNode(ReadType.STRING);
    }
    ;

tail:
    Dot IntLiteral {
        $$ = new TupleAccessNode($2, null);
    }
    | Dot Identifier {
        $$ = new TupleAccessNode(null, $2);
    }
    | OpenBracket expression CloseBracket {
        $$ = new ArrayAccessNode($2);
    }
    | OpenParen expression_list CloseParen {
        $$ = new FunctionCallNode($2);
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
        $$ = new TypeNode(ConstructionType.VectorType);
    }
    | OpenBrace CloseBrace {
        $$ = new TypeNode(ConstructionType.TupleType);
    }
    | Func {
        $$ = new TypeNode($1);
    }
    /* Need one more: Expression .. Expression; I didn't understand what it is */

literal:
    IntLiteral {
        $$ = new TokenLiteralNode($1);
    }
    | DoubleLiteral {
        $$ = new TokenLiteralNode($1);
    }
    | StringLiteral {
        $$ = new TokenLiteralNode($1);
    }
    | BooleanLiteral {
        $$ = new TokenLiteralNode($1);
    }
    | OpenBracket expression_list CloseBracket {
        $$ = new ASTLiteralNode($2);
    }
    | OpenBrace expression_list CloseBrace {
        $$ = new ASTLiteralNode($2);
    }
    | function_literal {
        $$ = new ASTLiteralNode($1);
    }

function_literal:
    Func fun_body {
        $$ = new FunctionLiteralNode(null, $2);
    }
    | Func parameters fun_body {
        $$ = new FunctionLiteralNode($2, $3);
    }

parameters:
    OpenParen Identifier CloseParen {
        $$ = new ParametersNode($2, null);
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
