%code imports {
import java.io.IOException;
import com.java.parser.ast.ASTree;
import com.java.parser.ast.node.ephemeral.*;
import com.java.parser.ast.node.real.*;
import com.java.lexer.Token;

@SuppressWarnings("all")
}

%code {
	private static ASTree ast;

    public static ASTree makeAST(com.java.lexer.Lexer lexer) throws IOException {
		LexerAdapter lexerAdapter = new LexerAdapter(lexer);
		Parser p = new Parser(lexerAdapter);
		return p.parse() ? ast : null;
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

%type <ASTree> program
%type <Tail> access_tail
%type <Tuple> tuple
%type <Array> array
%type <ReferenceTail> reference
%type <AccessTailList> consecutive_access_tail
%type <DeclarationsCommaList> consecutive_declarations
%type <FunctionCall> func_tail
%type <TupleAccess> tuple_tail
%type <ArrayAccess> array_tail
%type <Term> term // can be so much types, hard to make typed
%type <Tail> tail
%type <FunctionLiteral> function_literal
%type <Factor> factor first_order_algebraic second_order_algebraic
%type <UnaryExpression> unary_expression
%type <Relation> relation
%type <ExpressionsCommaList> expressions_comma function_args
%type <PrintStatement> print_statement
%type <ReadStatement> read_statement
%type <ReturnStatement> return_statement
%type <StatementsList> statements_list consecutive_statements
%type <LoopStatement> loop_statement
%type <LoopBody> loop_body
%type <IfStatement> if_statement
%type <DeclarationStatement> var_declaration_statement
%type <AssignmentStatement> assignment_statement
%type <ExpressionStatement> expression_statement
%type <Statement> statement
%type <ExpressionEphemeral> expression
%type <IdentifiersCommaList> identifiers_comma
%type <TupleList> tuple_data

%start program

%%

program:
	statements_list {ast = new ASTree($1);}
	;

// Statements

statements_list:
    %empty { $$ = new StatementsList(); }
    | statement statements_list {$$ = new StatementsList($1, $2); }
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
        $$ = new ReferenceAssignStatement($1, $2, $4);
    }
    ;

var_declaration_statement:
    Var consecutive_declarations Semicolon {
        $$ = new IdentifiersWithValueDeclarationStatement($2);
    }
    | Var identifiers_comma Semicolon {
        $$ = new OnlyIdentifiersDeclarationStatement($2);
    }
    ;

if_statement:
    If expression Then consecutive_statements Else consecutive_statements End {
        $$ = new IfStatement($2, $4, $6);
    }
    | If expression Then consecutive_statements End {
        $$ = new IfStatement($2, $4, null);
    }
    ;

loop_statement:
    While expression loop_body {
        $$ = new WhileStatement($2, $3);
    }
    | For Identifier In Identifier loop_body {
        $$ = new ForStatement($2, $4, $5);
    }
    ;

loop_body:
    Loop consecutive_statements End {
        $$ = new LoopBody($2);
    }

return_statement:
    Return expression Semicolon {
        $$ = new ReturnStatement($2);
    }
    | Return Semicolon {
        $$ = new ReturnStatement(null);
    }
    ;

read_statement:
    read_type reference Semicolon {
        $$ = new ReadStatement($1, $2);
    }
    ;

print_statement:
    Print expressions_comma Semicolon {
        $$ = new PrintStatement($2);
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
    | function_literal // to make it easy, in the code hierarchy, we assume that function_literal is relation too
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
    | Not OpenParen expression CloseParen {
        $$ = new UnaryOp($1, $3);
    }
    ;

function_literal:
    Func OpenParen identifiers_comma CloseParen Is consecutive_statements End {
        $$ = new FunctionLiteral($3, $6);
    }
    | Func OpenParen identifiers_comma CloseParen Arrow expression {
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
        $$ = new Array(new ExpressionsCommaList());
    }
    | OpenBracket expressions_comma CloseBracket {
        $$ = new Array($2);
    }
    ;

tuple:
    OpenBrace CloseBrace {
        $$ = new Tuple(new TupleList());
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
    ;

access_tail:
    array_tail
    | tuple_tail
    | func_tail
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
        $$ = new DeclarationsCommaList(new IdentifierWithValue($1, $3));
    }
    | consecutive_declarations Comma Identifier Assignment expression {
        $1.append(new IdentifierWithValue($3, $5));
        $$ = $1;
    }
    ;

consecutive_access_tail:
    access_tail {
        $$ = new AccessTailList($1);
    }
    | consecutive_access_tail access_tail {
        $1.append($2);
        $$ = $1;
    }
    ;

expressions_comma:
    expression {
        $$ = new ExpressionsCommaList($1);
    }
    | expressions_comma Comma expression {
        $1.append($3);
        $$ = $1;
    }
    ;

tuple_data:
    expression {
        $$ = new TupleList($1);
    }
    | Identifier Assignment expression {
        $$ = new TupleList(new IdentifierAssign($1, $3));
    }
    | tuple_data Comma expression {
        $1.append($3);
        $$ = $1;
    }
    | tuple_data Comma Identifier Assignment expression {
        $1.append(new IdentifierAssign($3, $5));
        $$ = $1;
    }
    ;

function_args:
    %empty {
        $$ = new ExpressionsCommaList();
    }
    | expression {
        $$ = new ExpressionsCommaList($1);
    }
    | function_args Comma expression {
        $1.append($3);
        $$ = $1;
    }
    ;

identifiers_comma:
    %empty {
        $$ = new IdentifiersCommaList();
    }
    | Identifier {
        $$ = new IdentifiersCommaList($1);
    }
    | identifiers_comma Comma Identifier {
        $1.append($3);
        $$ = $1;
    }
    ;

consecutive_statements:
    statement {
        $$ = new StatementsList($1);
    }
    | consecutive_statements statement {
        $1.append($2);
        $$ = $1;
    }
    ;

%%
