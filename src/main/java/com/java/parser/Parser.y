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
%define parse.error detailed
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
%type <ASTNode> expression relation factor term tail type_indicator
%type <ASTNode> loop_body function_literal
%type <ASTNode> expression_statement
%type <ASTListNode> consecutive_statements function_args expressions_comma statements_list
%type <TokenListNode> parameters
%type <TupleListNode> tuple_data

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

statement:
    expression_statement
    | assignment_statement
    | var_declaration_statement
    | if_statement
    | loop_statement
    | return_statement
    | print_statement
    ;

if_statement:
    If expression Then consecutive_statements Else consecutive_statements End {
        $$ = new IfNode($2, $4, $6);
    }
    | If expression Then consecutive_statements End {
        $$ = new IfNode($2, $4, null);
    }
    ;

loop_statement:
    While expression loop_body {
        $$ = new WhileNode($2, $3);
    }
    | For Identifier In Identifier loop_body {
        $$ = new ForNode($2, $4, $5);
    }
    ;

loop_body:
    Loop consecutive_statements End {
        $$ = new LoopBodyNode($2);
    }

var_declaration_statement:
    Var Identifier Assignment expression Semicolon {
        $$ = new VarDeclNode($2, $4);
    }
    | Var Identifier Semicolon {
        $$ = new VarDeclNode($2, null);
    }
    ;

assignment_statement:
    Identifier Assignment expression Semicolon {
        $$ = new AssignNode($1, $3);
    }
    ;

print_statement:
    Print expressions_comma Semicolon {
        $$ = new PrintNode($2);
    }
    ;

expressions_comma:
    expression {
        $$ = new ASTListNode($1);
    }
    | expressions_comma Comma expression {
        $1.append($3);
        $$ = $1;
    }
    ;

expression_statement:
    expression Semicolon {
        $$ = new ExpressionStatementNode($1);
    }
    ;

expression:
    relation {
        $$ = $1;
    }
    | relation Or relation {
        $$ = new BinaryOpNode($2, $1, $3);
    }
    | relation And relation {
        $$ = new BinaryOpNode($2, $1, $3);
    }
    | relation Xor relation {
        $$ = new BinaryOpNode($2, $1, $3);
    }
    | function_literal
    ;

relation:
    factor
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
    add_expression
    ;

function_literal:
    Func OpenParen parameters CloseParen Is consecutive_statements End {
        $$ = new FunctionLiteralNode($3, $6);
    }
    | Func OpenParen parameters CloseParen Arrow expression {
        $$ = new FunctionLiteralNode($3, $6);
    }
    ;

parameters:
    %empty {
        $$ = new TokenListNode();
    }
    | Identifier {
        $$ = new TokenListNode($1);
    }
    | parameters Comma Identifier {
        $1.append($3);
        $$ = $1;
    }
    ;

consecutive_statements:
    statement {
        $$ = new ASTListNode($1);
    }
    | consecutive_statements statement {
        $1.append($2);
        $$ = $1;
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

add_expression:
    multi_expression
    | add_expression Plus multi_expression {
        $$ = new BinaryOpNode($2, $1, $3);
    }
    | add_expression Minus multi_expression {
        $$ = new BinaryOpNode($2, $1, $3);
    }
    ;

multi_expression:
    unary_expression
    | multi_expression Star unary_expression {
        $$ = new BinaryOpNode($2, $1, $3);
    }
    | multi_expression Slash unary_expression {
        $$ = new BinaryOpNode($2, $1, $3);
    }
    ;

unary_expression:
    term
    | OpenParen add_expression CloseParen {
        $$ = new UnaryOpNode(null, $2);
    }
    | Minus term {
        $$ = new UnaryOpNode($1, $2);
    }
    ;

term:
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
    | reference
    | reference Is type_indicator {
        $$ = new TokenTypeIndicator($1, $3);
    }
    | array
    | tuple
    ;

type_indicator:
    Int
    | Real
    | Bool
    | String
    | Empty
    | Func
    ;

reference:
    Identifier tail {
        $$ = new ReferenceTailNode($1, $2);
    }
    ;

tail:
    %empty
    | array_tail
    | tuple_tail
    | func_tail
    ;

array_tail:
    OpenBracket expression CloseBracket {
        $$ = new ArrayAccessNode($2);
    }
    ;

tuple_tail:
    Dot IntLiteral {
        $$ = new TupleAccessNode($2, null);
    }
    | Dot Identifier {
        $$ = new TupleAccessNode(null, $2);
    }
    ;

func_tail:
    OpenParen function_args CloseParen {
        $$ = new FunctionCallNode($2);
    }
    ;

function_args:
    %empty {
        $$ = new ASTListNode();
    }
    | expression {
        $$ = new ASTListNode($1);
    }
    | function_args Comma expression {
        $1.append($3);
        $$ = $1;
    }
    ;

array:
    OpenBracket CloseBracket {
        $$ = new ASTLiteralNode(null);
    }
    | OpenBracket array_data CloseBracket {
        $$ = new ASTLiteralNode($2);
    }
    ;

array_data:
    expression {
        $$ = new ASTListNode($1);
    }
    | array_data Comma expression {
        $1.append($3);
        $$ = $1;
    }
    ;

tuple:
    OpenBrace CloseBrace {
        $$ = new ASTLiteralNode(null);
    }
    | OpenBrace tuple_data CloseBrace {
        $$ = new ASTLiteralNode($2);
    }
    ;

tuple_data:
    expression {
        $$ = new TupleListNode($1);
    }
    | Identifier Assignment expression {
        $$ = new TupleListNode($1, $3);
    }
    | tuple_data Comma expression {
        $1.append($3);
        $$ = $1;
    }
    | tuple_data Comma Identifier expression {
        $1.append($3, $4);
        $$ = $1;
    }
    ;

%%
