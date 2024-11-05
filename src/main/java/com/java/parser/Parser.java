/* A Bison parser, made by GNU Bison 3.8.2.  */

/* Skeleton implementation for Bison LALR(1) parsers in Java

   Copyright (C) 2007-2015, 2018-2021 Free Software Foundation, Inc.

   This program is free software: you can redistribute it and/or modify
   it under the terms of the GNU General Public License as published by
   the Free Software Foundation, either version 3 of the License, or
   (at your option) any later version.

   This program is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
   GNU General Public License for more details.

   You should have received a copy of the GNU General Public License
   along with this program.  If not, see <https://www.gnu.org/licenses/>.  */

/* As a special exception, you may create a larger work that contains
   part or all of the Bison parser skeleton and distribute that work
   under terms of your choice, so long as that work isn't itself a
   parser generator using the skeleton or a modified version thereof
   as a parser skeleton.  Alternatively, if you modify or redistribute
   the parser skeleton itself, you may (at your option) remove this
   special exception, which will cause the skeleton and the resulting
   Bison output files to be licensed under the GNU General Public
   License without this special exception.

   This special exception was added by the Free Software Foundation in
   version 2.2 of Bison.  */

/* DO NOT RELY ON FEATURES THAT ARE NOT DOCUMENTED in the manual,
   especially those whose name start with YY_ or yy_.  They are
   private implementation details that can be changed or removed.  */

package com.java.parser;



import java.text.MessageFormat;
import java.util.ArrayList;
/* "%code imports" blocks.  */
/* "Parser.y":1  */

import java.io.IOException;
import com.java.parser.ast.ASTree;
import com.java.parser.ast.node.ephemeral.*;
import com.java.parser.ast.node.real.*;
import com.java.lexer.Token;

@SuppressWarnings("all")

/* "Parser.java":55  */

/**
 * A Bison parser, automatically generated from <tt>Parser.y</tt>.
 *
 * @author LALR (1) parser skeleton written by Paolo Bonzini.
 */
public final class Parser
{
  /** Version number for the Bison executable that generated this parser.  */
  public static final String bisonVersion = "3.8.2";

  /** Name of the skeleton that generated this parser.  */
  public static final String bisonSkeleton = "lalr1.java";



  /**
   * True if verbose error messages are enabled.
   */
  private boolean yyErrorVerbose = true;

  /**
   * Whether verbose error messages are enabled.
   */
  public final boolean getErrorVerbose() { return yyErrorVerbose; }

  /**
   * Set the verbosity of error messages.
   * @param verbose True to request verbose error messages.
   */
  public final void setErrorVerbose(boolean verbose)
  { yyErrorVerbose = verbose; }




  public enum SymbolKind
  {
    S_YYEOF(0),                    /* "end of file"  */
    S_YYerror(1),                  /* error  */
    S_YYUNDEF(2),                  /* "invalid token"  */
    S_Less(3),                     /* Less  */
    S_LessEqual(4),                /* LessEqual  */
    S_Greater(5),                  /* Greater  */
    S_GreaterEqual(6),             /* GreaterEqual  */
    S_Equal(7),                    /* Equal  */
    S_NotEqual(8),                 /* NotEqual  */
    S_Plus(9),                     /* Plus  */
    S_Minus(10),                   /* Minus  */
    S_Slash(11),                   /* Slash  */
    S_Star(12),                    /* Star  */
    S_And(13),                     /* And  */
    S_Or(14),                      /* Or  */
    S_Xor(15),                     /* Xor  */
    S_Not(16),                     /* Not  */
    S_Is(17),                      /* Is  */
    S_Dot(18),                     /* Dot  */
    S_Assignment(19),              /* Assignment  */
    S_Arrow(20),                   /* Arrow  */
    S_Comma(21),                   /* Comma  */
    S_Semicolon(22),               /* Semicolon  */
    S_Int(23),                     /* Int  */
    S_Real(24),                    /* Real  */
    S_Bool(25),                    /* Bool  */
    S_String(26),                  /* String  */
    S_Empty(27),                   /* Empty  */
    S_Func(28),                    /* Func  */
    S_OpenParen(29),               /* OpenParen  */
    S_CloseParen(30),              /* CloseParen  */
    S_OpenBrace(31),               /* OpenBrace  */
    S_CloseBrace(32),              /* CloseBrace  */
    S_OpenBracket(33),             /* OpenBracket  */
    S_CloseBracket(34),            /* CloseBracket  */
    S_Var(35),                     /* Var  */
    S_ReadInt(36),                 /* ReadInt  */
    S_ReadReal(37),                /* ReadReal  */
    S_ReadString(38),              /* ReadString  */
    S_Print(39),                   /* Print  */
    S_Return(40),                  /* Return  */
    S_If(41),                      /* If  */
    S_Then(42),                    /* Then  */
    S_Else(43),                    /* Else  */
    S_End(44),                     /* End  */
    S_While(45),                   /* While  */
    S_For(46),                     /* For  */
    S_In(47),                      /* In  */
    S_Loop(48),                    /* Loop  */
    S_StringLiteral(49),           /* StringLiteral  */
    S_DoubleLiteral(50),           /* DoubleLiteral  */
    S_IntLiteral(51),              /* IntLiteral  */
    S_BooleanLiteral(52),          /* BooleanLiteral  */
    S_Identifier(53),              /* Identifier  */
    S_YYACCEPT(54),                /* $accept  */
    S_program(55),                 /* program  */
    S_statements_list(56),         /* statements_list  */
    S_statement(57),               /* statement  */
    S_expression_statement(58),    /* expression_statement  */
    S_assignment_statement(59),    /* assignment_statement  */
    S_var_declaration_statement(60), /* var_declaration_statement  */
    S_if_statement(61),            /* if_statement  */
    S_loop_statement(62),          /* loop_statement  */
    S_loop_body(63),               /* loop_body  */
    S_return_statement(64),        /* return_statement  */
    S_read_statement(65),          /* read_statement  */
    S_print_statement(66),         /* print_statement  */
    S_expression(67),              /* expression  */
    S_relation(68),                /* relation  */
    S_factor(69),                  /* factor  */
    S_second_order_algebraic(70),  /* second_order_algebraic  */
    S_first_order_algebraic(71),   /* first_order_algebraic  */
    S_unary_expression(72),        /* unary_expression  */
    S_function_literal(73),        /* function_literal  */
    S_term(74),                    /* term  */
    S_reference(75),               /* reference  */
    S_array(76),                   /* array  */
    S_tuple(77),                   /* tuple  */
    S_type_indicator(78),          /* type_indicator  */
    S_read_type(79),               /* read_type  */
    S_tail(80),                    /* tail  */
    S_access_tail(81),             /* access_tail  */
    S_array_tail(82),              /* array_tail  */
    S_tuple_tail(83),              /* tuple_tail  */
    S_func_tail(84),               /* func_tail  */
    S_consecutive_declarations(85), /* consecutive_declarations  */
    S_consecutive_access_tail(86), /* consecutive_access_tail  */
    S_expressions_comma(87),       /* expressions_comma  */
    S_tuple_data(88),              /* tuple_data  */
    S_function_args(89),           /* function_args  */
    S_identifiers_comma(90),       /* identifiers_comma  */
    S_consecutive_statements(91);  /* consecutive_statements  */


    private final int yycode_;

    SymbolKind (int n) {
      this.yycode_ = n;
    }

    private static final SymbolKind[] values_ = {
      SymbolKind.S_YYEOF,
      SymbolKind.S_YYerror,
      SymbolKind.S_YYUNDEF,
      SymbolKind.S_Less,
      SymbolKind.S_LessEqual,
      SymbolKind.S_Greater,
      SymbolKind.S_GreaterEqual,
      SymbolKind.S_Equal,
      SymbolKind.S_NotEqual,
      SymbolKind.S_Plus,
      SymbolKind.S_Minus,
      SymbolKind.S_Slash,
      SymbolKind.S_Star,
      SymbolKind.S_And,
      SymbolKind.S_Or,
      SymbolKind.S_Xor,
      SymbolKind.S_Not,
      SymbolKind.S_Is,
      SymbolKind.S_Dot,
      SymbolKind.S_Assignment,
      SymbolKind.S_Arrow,
      SymbolKind.S_Comma,
      SymbolKind.S_Semicolon,
      SymbolKind.S_Int,
      SymbolKind.S_Real,
      SymbolKind.S_Bool,
      SymbolKind.S_String,
      SymbolKind.S_Empty,
      SymbolKind.S_Func,
      SymbolKind.S_OpenParen,
      SymbolKind.S_CloseParen,
      SymbolKind.S_OpenBrace,
      SymbolKind.S_CloseBrace,
      SymbolKind.S_OpenBracket,
      SymbolKind.S_CloseBracket,
      SymbolKind.S_Var,
      SymbolKind.S_ReadInt,
      SymbolKind.S_ReadReal,
      SymbolKind.S_ReadString,
      SymbolKind.S_Print,
      SymbolKind.S_Return,
      SymbolKind.S_If,
      SymbolKind.S_Then,
      SymbolKind.S_Else,
      SymbolKind.S_End,
      SymbolKind.S_While,
      SymbolKind.S_For,
      SymbolKind.S_In,
      SymbolKind.S_Loop,
      SymbolKind.S_StringLiteral,
      SymbolKind.S_DoubleLiteral,
      SymbolKind.S_IntLiteral,
      SymbolKind.S_BooleanLiteral,
      SymbolKind.S_Identifier,
      SymbolKind.S_YYACCEPT,
      SymbolKind.S_program,
      SymbolKind.S_statements_list,
      SymbolKind.S_statement,
      SymbolKind.S_expression_statement,
      SymbolKind.S_assignment_statement,
      SymbolKind.S_var_declaration_statement,
      SymbolKind.S_if_statement,
      SymbolKind.S_loop_statement,
      SymbolKind.S_loop_body,
      SymbolKind.S_return_statement,
      SymbolKind.S_read_statement,
      SymbolKind.S_print_statement,
      SymbolKind.S_expression,
      SymbolKind.S_relation,
      SymbolKind.S_factor,
      SymbolKind.S_second_order_algebraic,
      SymbolKind.S_first_order_algebraic,
      SymbolKind.S_unary_expression,
      SymbolKind.S_function_literal,
      SymbolKind.S_term,
      SymbolKind.S_reference,
      SymbolKind.S_array,
      SymbolKind.S_tuple,
      SymbolKind.S_type_indicator,
      SymbolKind.S_read_type,
      SymbolKind.S_tail,
      SymbolKind.S_access_tail,
      SymbolKind.S_array_tail,
      SymbolKind.S_tuple_tail,
      SymbolKind.S_func_tail,
      SymbolKind.S_consecutive_declarations,
      SymbolKind.S_consecutive_access_tail,
      SymbolKind.S_expressions_comma,
      SymbolKind.S_tuple_data,
      SymbolKind.S_function_args,
      SymbolKind.S_identifiers_comma,
      SymbolKind.S_consecutive_statements
    };

    static final SymbolKind get(int code) {
      return values_[code];
    }

    public final int getCode() {
      return this.yycode_;
    }

    /* YYNAMES_[SYMBOL-NUM] -- String name of the symbol SYMBOL-NUM.
       First, the terminals, then, starting at \a YYNTOKENS_, nonterminals.  */
    private static final String[] yynames_ = yynames_init();
  private static final String[] yynames_init()
  {
    return new String[]
    {
  "end of file", "error", "invalid token", "Less", "LessEqual", "Greater",
  "GreaterEqual", "Equal", "NotEqual", "Plus", "Minus", "Slash", "Star",
  "And", "Or", "Xor", "Not", "Is", "Dot", "Assignment", "Arrow", "Comma",
  "Semicolon", "Int", "Real", "Bool", "String", "Empty", "Func",
  "OpenParen", "CloseParen", "OpenBrace", "CloseBrace", "OpenBracket",
  "CloseBracket", "Var", "ReadInt", "ReadReal", "ReadString", "Print",
  "Return", "If", "Then", "Else", "End", "While", "For", "In", "Loop",
  "StringLiteral", "DoubleLiteral", "IntLiteral", "BooleanLiteral",
  "Identifier", "$accept", "program", "statements_list", "statement",
  "expression_statement", "assignment_statement",
  "var_declaration_statement", "if_statement", "loop_statement",
  "loop_body", "return_statement", "read_statement", "print_statement",
  "expression", "relation", "factor", "second_order_algebraic",
  "first_order_algebraic", "unary_expression", "function_literal", "term",
  "reference", "array", "tuple", "type_indicator", "read_type", "tail",
  "access_tail", "array_tail", "tuple_tail", "func_tail",
  "consecutive_declarations", "consecutive_access_tail",
  "expressions_comma", "tuple_data", "function_args", "identifiers_comma",
  "consecutive_statements", null
    };
  }

    /* The user-facing name of this symbol.  */
    public final String getName() {
      return yynames_[yycode_];
    }
  };


  /**
   * Communication interface between the scanner and the Bison-generated
   * parser <tt>Parser</tt>.
   */
  public interface Lexer {
    /* Token kinds.  */
    /** Token "end of file", to be returned by the scanner.  */
    static final int YYEOF = 0;
    /** Token error, to be returned by the scanner.  */
    static final int YYerror = 256;
    /** Token "invalid token", to be returned by the scanner.  */
    static final int YYUNDEF = 257;
    /** Token Less, to be returned by the scanner.  */
    static final int Less = 258;
    /** Token LessEqual, to be returned by the scanner.  */
    static final int LessEqual = 259;
    /** Token Greater, to be returned by the scanner.  */
    static final int Greater = 260;
    /** Token GreaterEqual, to be returned by the scanner.  */
    static final int GreaterEqual = 261;
    /** Token Equal, to be returned by the scanner.  */
    static final int Equal = 262;
    /** Token NotEqual, to be returned by the scanner.  */
    static final int NotEqual = 263;
    /** Token Plus, to be returned by the scanner.  */
    static final int Plus = 264;
    /** Token Minus, to be returned by the scanner.  */
    static final int Minus = 265;
    /** Token Slash, to be returned by the scanner.  */
    static final int Slash = 266;
    /** Token Star, to be returned by the scanner.  */
    static final int Star = 267;
    /** Token And, to be returned by the scanner.  */
    static final int And = 268;
    /** Token Or, to be returned by the scanner.  */
    static final int Or = 269;
    /** Token Xor, to be returned by the scanner.  */
    static final int Xor = 270;
    /** Token Not, to be returned by the scanner.  */
    static final int Not = 271;
    /** Token Is, to be returned by the scanner.  */
    static final int Is = 272;
    /** Token Dot, to be returned by the scanner.  */
    static final int Dot = 273;
    /** Token Assignment, to be returned by the scanner.  */
    static final int Assignment = 274;
    /** Token Arrow, to be returned by the scanner.  */
    static final int Arrow = 275;
    /** Token Comma, to be returned by the scanner.  */
    static final int Comma = 276;
    /** Token Semicolon, to be returned by the scanner.  */
    static final int Semicolon = 277;
    /** Token Int, to be returned by the scanner.  */
    static final int Int = 278;
    /** Token Real, to be returned by the scanner.  */
    static final int Real = 279;
    /** Token Bool, to be returned by the scanner.  */
    static final int Bool = 280;
    /** Token String, to be returned by the scanner.  */
    static final int String = 281;
    /** Token Empty, to be returned by the scanner.  */
    static final int Empty = 282;
    /** Token Func, to be returned by the scanner.  */
    static final int Func = 283;
    /** Token OpenParen, to be returned by the scanner.  */
    static final int OpenParen = 284;
    /** Token CloseParen, to be returned by the scanner.  */
    static final int CloseParen = 285;
    /** Token OpenBrace, to be returned by the scanner.  */
    static final int OpenBrace = 286;
    /** Token CloseBrace, to be returned by the scanner.  */
    static final int CloseBrace = 287;
    /** Token OpenBracket, to be returned by the scanner.  */
    static final int OpenBracket = 288;
    /** Token CloseBracket, to be returned by the scanner.  */
    static final int CloseBracket = 289;
    /** Token Var, to be returned by the scanner.  */
    static final int Var = 290;
    /** Token ReadInt, to be returned by the scanner.  */
    static final int ReadInt = 291;
    /** Token ReadReal, to be returned by the scanner.  */
    static final int ReadReal = 292;
    /** Token ReadString, to be returned by the scanner.  */
    static final int ReadString = 293;
    /** Token Print, to be returned by the scanner.  */
    static final int Print = 294;
    /** Token Return, to be returned by the scanner.  */
    static final int Return = 295;
    /** Token If, to be returned by the scanner.  */
    static final int If = 296;
    /** Token Then, to be returned by the scanner.  */
    static final int Then = 297;
    /** Token Else, to be returned by the scanner.  */
    static final int Else = 298;
    /** Token End, to be returned by the scanner.  */
    static final int End = 299;
    /** Token While, to be returned by the scanner.  */
    static final int While = 300;
    /** Token For, to be returned by the scanner.  */
    static final int For = 301;
    /** Token In, to be returned by the scanner.  */
    static final int In = 302;
    /** Token Loop, to be returned by the scanner.  */
    static final int Loop = 303;
    /** Token StringLiteral, to be returned by the scanner.  */
    static final int StringLiteral = 304;
    /** Token DoubleLiteral, to be returned by the scanner.  */
    static final int DoubleLiteral = 305;
    /** Token IntLiteral, to be returned by the scanner.  */
    static final int IntLiteral = 306;
    /** Token BooleanLiteral, to be returned by the scanner.  */
    static final int BooleanLiteral = 307;
    /** Token Identifier, to be returned by the scanner.  */
    static final int Identifier = 308;

    /** Deprecated, use YYEOF instead.  */
    public static final int EOF = YYEOF;


    /**
     * Method to retrieve the semantic value of the last scanned token.
     * @return the semantic value of the last scanned token.
     */
    Object getLVal();

    /**
     * Entry point for the scanner.  Returns the token identifier corresponding
     * to the next token and prepares to return the semantic value
     * of the token.
     * @return the token identifier corresponding to the next token.
     */
    int yylex() throws java.io.IOException;

    /**
     * Emit an errorin a user-defined way.
     *
     *
     * @param msg The string for the error message.
     */
     void yyerror(String msg);


  }


  /**
   * The object doing lexical analysis for us.
   */
  private Lexer yylexer;





  /**
   * Instantiates the Bison-generated parser.
   * @param yylexer The scanner that will supply tokens to the parser.
   */
  public Parser(Lexer yylexer)
  {

    this.yylexer = yylexer;

  }



  private int yynerrs = 0;

  /**
   * The number of syntax errors so far.
   */
  public final int getNumberOfErrors() { return yynerrs; }

  /**
   * Print an error message via the lexer.
   *
   * @param msg The error message.
   */
  public final void yyerror(String msg) {
      yylexer.yyerror(msg);
  }



  private final class YYStack {
    private int[] stateStack = new int[16];
    private Object[] valueStack = new Object[16];

    public int size = 16;
    public int height = -1;

    public final void push(int state, Object value) {
      height++;
      if (size == height) {
        int[] newStateStack = new int[size * 2];
        System.arraycopy(stateStack, 0, newStateStack, 0, height);
        stateStack = newStateStack;

        Object[] newValueStack = new Object[size * 2];
        System.arraycopy(valueStack, 0, newValueStack, 0, height);
        valueStack = newValueStack;

        size *= 2;
      }

      stateStack[height] = state;
      valueStack[height] = value;
    }

    public final void pop() {
      pop(1);
    }

    public final void pop(int num) {
      // Avoid memory leaks... garbage collection is a white lie!
      if (0 < num) {
        java.util.Arrays.fill(valueStack, height - num + 1, height + 1, null);
      }
      height -= num;
    }

    public final int stateAt(int i) {
      return stateStack[height - i];
    }

    public final Object valueAt(int i) {
      return valueStack[height - i];
    }

    // Print the state stack on the debug stream.
    public void print(java.io.PrintStream out) {
      out.print ("Stack now");

      for (int i = 0; i <= height; i++) {
        out.print(' ');
        out.print(stateStack[i]);
      }
      out.println();
    }
  }

  /**
   * Returned by a Bison action in order to stop the parsing process and
   * return success (<tt>true</tt>).
   */
  public static final int YYACCEPT = 0;

  /**
   * Returned by a Bison action in order to stop the parsing process and
   * return failure (<tt>false</tt>).
   */
  public static final int YYABORT = 1;



  /**
   * Returned by a Bison action in order to start error recovery without
   * printing an error message.
   */
  public static final int YYERROR = 2;

  /**
   * Internal return codes that are not supported for user semantic
   * actions.
   */
  private static final int YYERRLAB = 3;
  private static final int YYNEWSTATE = 4;
  private static final int YYDEFAULT = 5;
  private static final int YYREDUCE = 6;
  private static final int YYERRLAB1 = 7;
  private static final int YYRETURN = 8;


  private int yyerrstatus_ = 0;


  /**
   * Whether error recovery is being done.  In this state, the parser
   * reads token until it reaches a known state, and then restarts normal
   * operation.
   */
  public final boolean recovering ()
  {
    return yyerrstatus_ == 0;
  }

  /** Compute post-reduction state.
   * @param yystate   the current state
   * @param yysym     the nonterminal to push on the stack
   */
  private int yyLRGotoState(int yystate, int yysym) {
    int yyr = yypgoto_[yysym - YYNTOKENS_] + yystate;
    if (0 <= yyr && yyr <= YYLAST_ && yycheck_[yyr] == yystate)
      return yytable_[yyr];
    else
      return yydefgoto_[yysym - YYNTOKENS_];
  }

  private int yyaction(int yyn, YYStack yystack, int yylen)
  {
    /* If YYLEN is nonzero, implement the default value of the action:
       '$$ = $1'.  Otherwise, use the top of the stack.

       Otherwise, the following line sets YYVAL to garbage.
       This behavior is undocumented and Bison
       users should not rely upon it.  */
    Object yyval = (0 < yylen) ? yystack.valueAt(yylen - 1) : yystack.valueAt(0);

    switch (yyn)
      {
          case 2: /* program: statements_list  */
  if (yyn == 2)
    /* "Parser.y":82  */
                        {ast = new ASTree(((StatementsList)(yystack.valueAt (0))));};
  break;


  case 3: /* statements_list: %empty  */
  if (yyn == 3)
    /* "Parser.y":88  */
           { yyval = new StatementsList(); };
  break;


  case 4: /* statements_list: statement statements_list  */
  if (yyn == 4)
    /* "Parser.y":89  */
                                {yyval = new StatementsList(((Statement)(yystack.valueAt (1))), ((StatementsList)(yystack.valueAt (0)))); };
  break;


  case 13: /* expression_statement: expression Semicolon  */
  if (yyn == 13)
    /* "Parser.y":106  */
                         {
        yyval = new ExpressionStatement(((ExpressionEphemeral)(yystack.valueAt (1))));
    };
  break;


  case 14: /* assignment_statement: Identifier Assignment expression Semicolon  */
  if (yyn == 14)
    /* "Parser.y":112  */
                                               {
        yyval = new IdentifierAssign(((Token)(yystack.valueAt (3))), ((ExpressionEphemeral)(yystack.valueAt (1))));
    };
  break;


  case 15: /* assignment_statement: Identifier consecutive_access_tail Assignment expression Semicolon  */
  if (yyn == 15)
    /* "Parser.y":115  */
                                                                         {
        yyval = new ReferenceAssignStatement(((Token)(yystack.valueAt (4))), ((AccessTailList)(yystack.valueAt (3))), ((ExpressionEphemeral)(yystack.valueAt (1))));
    };
  break;


  case 16: /* var_declaration_statement: Var consecutive_declarations Semicolon  */
  if (yyn == 16)
    /* "Parser.y":121  */
                                           {
        yyval = new IdentifiersWithValueDeclarationStatement(((DeclarationsCommaList)(yystack.valueAt (1))));
    };
  break;


  case 17: /* var_declaration_statement: Var identifiers_comma Semicolon  */
  if (yyn == 17)
    /* "Parser.y":124  */
                                      {
        yyval = new OnlyIdentifiersDeclarationStatement(((IdentifiersCommaList)(yystack.valueAt (1))));
    };
  break;


  case 18: /* if_statement: If expression Then consecutive_statements Else consecutive_statements End  */
  if (yyn == 18)
    /* "Parser.y":130  */
                                                                              {
        yyval = new IfStatement(((ExpressionEphemeral)(yystack.valueAt (5))), ((StatementsList)(yystack.valueAt (3))), ((StatementsList)(yystack.valueAt (1))));
    };
  break;


  case 19: /* if_statement: If expression Then consecutive_statements End  */
  if (yyn == 19)
    /* "Parser.y":133  */
                                                    {
        yyval = new IfStatement(((ExpressionEphemeral)(yystack.valueAt (3))), ((StatementsList)(yystack.valueAt (1))), null);
    };
  break;


  case 20: /* loop_statement: While expression loop_body  */
  if (yyn == 20)
    /* "Parser.y":139  */
                               {
        yyval = new WhileStatement(((ExpressionEphemeral)(yystack.valueAt (1))), ((LoopBody)(yystack.valueAt (0))));
    };
  break;


  case 21: /* loop_statement: For Identifier In Identifier loop_body  */
  if (yyn == 21)
    /* "Parser.y":142  */
                                             {
        yyval = new ForStatement(((Token)(yystack.valueAt (3))), ((Token)(yystack.valueAt (1))), ((LoopBody)(yystack.valueAt (0))));
    };
  break;


  case 22: /* loop_body: Loop consecutive_statements End  */
  if (yyn == 22)
    /* "Parser.y":148  */
                                    {
        yyval = new LoopBody(((StatementsList)(yystack.valueAt (1))));
    };
  break;


  case 23: /* return_statement: Return expression Semicolon  */
  if (yyn == 23)
    /* "Parser.y":153  */
                                {
        yyval = new ReturnStatement(((ExpressionEphemeral)(yystack.valueAt (1))));
    };
  break;


  case 24: /* return_statement: Return Semicolon  */
  if (yyn == 24)
    /* "Parser.y":156  */
                       {
        yyval = new ReturnStatement(null);
    };
  break;


  case 25: /* read_statement: read_type reference Semicolon  */
  if (yyn == 25)
    /* "Parser.y":162  */
                                  {
        yyval = new ReadStatement(((Token)(yystack.valueAt (2))), ((ReferenceTail)(yystack.valueAt (1))));
    };
  break;


  case 26: /* print_statement: Print expressions_comma Semicolon  */
  if (yyn == 26)
    /* "Parser.y":168  */
                                      {
        yyval = new PrintStatement(((ExpressionsCommaList)(yystack.valueAt (1))));
    };
  break;


  case 28: /* expression: relation Or relation  */
  if (yyn == 28)
    /* "Parser.y":177  */
                           {
        yyval = new LogicalOp(((Token)(yystack.valueAt (1))), ((Relation)(yystack.valueAt (2))), ((Relation)(yystack.valueAt (0))));
    };
  break;


  case 29: /* expression: relation And relation  */
  if (yyn == 29)
    /* "Parser.y":180  */
                            {
        yyval = new LogicalOp(((Token)(yystack.valueAt (1))), ((Relation)(yystack.valueAt (2))), ((Relation)(yystack.valueAt (0))));
    };
  break;


  case 30: /* expression: relation Xor relation  */
  if (yyn == 30)
    /* "Parser.y":183  */
                            {
        yyval = new LogicalOp(((Token)(yystack.valueAt (1))), ((Relation)(yystack.valueAt (2))), ((Relation)(yystack.valueAt (0))));
    };
  break;


  case 33: /* relation: factor Less factor  */
  if (yyn == 33)
    /* "Parser.y":191  */
                         {
        yyval = new ComparisonOp(((Token)(yystack.valueAt (1))), ((Factor)(yystack.valueAt (2))), ((Factor)(yystack.valueAt (0))));
    };
  break;


  case 34: /* relation: factor LessEqual factor  */
  if (yyn == 34)
    /* "Parser.y":194  */
                              {
        yyval = new ComparisonOp(((Token)(yystack.valueAt (1))), ((Factor)(yystack.valueAt (2))), ((Factor)(yystack.valueAt (0))));
    };
  break;


  case 35: /* relation: factor Greater factor  */
  if (yyn == 35)
    /* "Parser.y":197  */
                            {
        yyval = new ComparisonOp(((Token)(yystack.valueAt (1))), ((Factor)(yystack.valueAt (2))), ((Factor)(yystack.valueAt (0))));
    };
  break;


  case 36: /* relation: factor GreaterEqual factor  */
  if (yyn == 36)
    /* "Parser.y":200  */
                                 {
        yyval = new ComparisonOp(((Token)(yystack.valueAt (1))), ((Factor)(yystack.valueAt (2))), ((Factor)(yystack.valueAt (0))));
    };
  break;


  case 37: /* relation: factor Equal factor  */
  if (yyn == 37)
    /* "Parser.y":203  */
                          {
        yyval = new ComparisonOp(((Token)(yystack.valueAt (1))), ((Factor)(yystack.valueAt (2))), ((Factor)(yystack.valueAt (0))));
    };
  break;


  case 38: /* relation: factor NotEqual factor  */
  if (yyn == 38)
    /* "Parser.y":206  */
                             {
        yyval = new ComparisonOp(((Token)(yystack.valueAt (1))), ((Factor)(yystack.valueAt (2))), ((Factor)(yystack.valueAt (0))));
    };
  break;


  case 41: /* second_order_algebraic: second_order_algebraic Plus first_order_algebraic  */
  if (yyn == 41)
    /* "Parser.y":217  */
                                                        {
        yyval = new BinaryOp(((Token)(yystack.valueAt (1))), ((Factor)(yystack.valueAt (2))), ((Factor)(yystack.valueAt (0))));
    };
  break;


  case 42: /* second_order_algebraic: second_order_algebraic Minus first_order_algebraic  */
  if (yyn == 42)
    /* "Parser.y":220  */
                                                         {
        yyval = new BinaryOp(((Token)(yystack.valueAt (1))), ((Factor)(yystack.valueAt (2))), ((Factor)(yystack.valueAt (0))));
    };
  break;


  case 44: /* first_order_algebraic: first_order_algebraic Star unary_expression  */
  if (yyn == 44)
    /* "Parser.y":227  */
                                                  {
        yyval = new BinaryOp(((Token)(yystack.valueAt (1))), ((Factor)(yystack.valueAt (2))), ((UnaryExpression)(yystack.valueAt (0))));
    };
  break;


  case 45: /* first_order_algebraic: first_order_algebraic Slash unary_expression  */
  if (yyn == 45)
    /* "Parser.y":230  */
                                                   {
        yyval = new BinaryOp(((Token)(yystack.valueAt (1))), ((Factor)(yystack.valueAt (2))), ((UnaryExpression)(yystack.valueAt (0))));
    };
  break;


  case 47: /* unary_expression: OpenParen second_order_algebraic CloseParen  */
  if (yyn == 47)
    /* "Parser.y":237  */
                                                  {
        yyval = new UnaryOp(null, ((Factor)(yystack.valueAt (1))));
    };
  break;


  case 48: /* unary_expression: Plus term  */
  if (yyn == 48)
    /* "Parser.y":240  */
                {
        yyval = new UnaryOp(((Token)(yystack.valueAt (1))), ((Term)(yystack.valueAt (0))));
    };
  break;


  case 49: /* unary_expression: Minus term  */
  if (yyn == 49)
    /* "Parser.y":243  */
                 {
        yyval = new UnaryOp(((Token)(yystack.valueAt (1))), ((Term)(yystack.valueAt (0))));
    };
  break;


  case 50: /* unary_expression: Not term  */
  if (yyn == 50)
    /* "Parser.y":246  */
               {
        yyval = new UnaryOp(((Token)(yystack.valueAt (1))), ((Term)(yystack.valueAt (0))));
    };
  break;


  case 51: /* unary_expression: Not OpenParen expression CloseParen  */
  if (yyn == 51)
    /* "Parser.y":249  */
                                          {
        yyval = new UnaryOp(((Token)(yystack.valueAt (3))), ((ExpressionEphemeral)(yystack.valueAt (1))));
    };
  break;


  case 52: /* function_literal: Func OpenParen identifiers_comma CloseParen Is consecutive_statements End  */
  if (yyn == 52)
    /* "Parser.y":255  */
                                                                              {
        yyval = new FunctionLiteral(((IdentifiersCommaList)(yystack.valueAt (4))), ((StatementsList)(yystack.valueAt (1))));
    };
  break;


  case 53: /* function_literal: Func OpenParen identifiers_comma CloseParen Arrow expression  */
  if (yyn == 53)
    /* "Parser.y":258  */
                                                                   {
        yyval = new FunctionLiteral(((IdentifiersCommaList)(yystack.valueAt (3))), ((ExpressionEphemeral)(yystack.valueAt (0))));
    };
  break;


  case 54: /* term: IntLiteral  */
  if (yyn == 54)
    /* "Parser.y":266  */
               {
        yyval = new TokenLiteral(((Token)(yystack.valueAt (0))));
    };
  break;


  case 55: /* term: DoubleLiteral  */
  if (yyn == 55)
    /* "Parser.y":269  */
                    {
        yyval = new TokenLiteral(((Token)(yystack.valueAt (0))));
    };
  break;


  case 56: /* term: StringLiteral  */
  if (yyn == 56)
    /* "Parser.y":272  */
                    {
        yyval = new TokenLiteral(((Token)(yystack.valueAt (0))));
    };
  break;


  case 57: /* term: BooleanLiteral  */
  if (yyn == 57)
    /* "Parser.y":275  */
                     {
        yyval = new TokenLiteral(((Token)(yystack.valueAt (0))));
    };
  break;


  case 59: /* term: reference Is type_indicator  */
  if (yyn == 59)
    /* "Parser.y":279  */
                                  {
        yyval = new ReferenceType(((ReferenceTail)(yystack.valueAt (2))), ((Token)(yystack.valueAt (0))));
    };
  break;


  case 62: /* reference: Identifier tail  */
  if (yyn == 62)
    /* "Parser.y":287  */
                    {
        yyval = new ReferenceTail(((Token)(yystack.valueAt (1))), ((Tail)(yystack.valueAt (0))));
    };
  break;


  case 63: /* reference: Identifier consecutive_access_tail  */
  if (yyn == 63)
    /* "Parser.y":290  */
                                         {
        yyval = new ReferenceTail(((Token)(yystack.valueAt (1))), ((AccessTailList)(yystack.valueAt (0))));
    };
  break;


  case 64: /* array: OpenBracket CloseBracket  */
  if (yyn == 64)
    /* "Parser.y":296  */
                             {
        yyval = new Array(new ExpressionsCommaList());
    };
  break;


  case 65: /* array: OpenBracket expressions_comma CloseBracket  */
  if (yyn == 65)
    /* "Parser.y":299  */
                                                 {
        yyval = new Array(((ExpressionsCommaList)(yystack.valueAt (1))));
    };
  break;


  case 66: /* tuple: OpenBrace CloseBrace  */
  if (yyn == 66)
    /* "Parser.y":305  */
                         {
        yyval = new Tuple(new TupleList());
    };
  break;


  case 67: /* tuple: OpenBrace tuple_data CloseBrace  */
  if (yyn == 67)
    /* "Parser.y":308  */
                                      {
        yyval = new Tuple(((TupleList)(yystack.valueAt (1))));
    };
  break;


  case 77: /* tail: %empty  */
  if (yyn == 77)
    /* "Parser.y":333  */
           {
        yyval = new EmptyTail();
    };
  break;


  case 81: /* array_tail: OpenBracket expression CloseBracket  */
  if (yyn == 81)
    /* "Parser.y":345  */
                                        {
        yyval = new ArrayAccess(((ExpressionEphemeral)(yystack.valueAt (1))));
    };
  break;


  case 82: /* tuple_tail: Dot IntLiteral  */
  if (yyn == 82)
    /* "Parser.y":351  */
                   {
        yyval = new TupleAccess(((Token)(yystack.valueAt (0))), null);
    };
  break;


  case 83: /* tuple_tail: Dot Identifier  */
  if (yyn == 83)
    /* "Parser.y":354  */
                     {
        yyval = new TupleAccess(null, ((Token)(yystack.valueAt (0))));
    };
  break;


  case 84: /* func_tail: OpenParen function_args CloseParen  */
  if (yyn == 84)
    /* "Parser.y":360  */
                                       {
        yyval = new FunctionCall(((ExpressionsCommaList)(yystack.valueAt (1))));
    };
  break;


  case 85: /* consecutive_declarations: Identifier Assignment expression  */
  if (yyn == 85)
    /* "Parser.y":368  */
                                     {
        yyval = new DeclarationsCommaList(new IdentifierWithValue(((Token)(yystack.valueAt (2))), ((ExpressionEphemeral)(yystack.valueAt (0)))));
    };
  break;


  case 86: /* consecutive_declarations: consecutive_declarations Comma Identifier Assignment expression  */
  if (yyn == 86)
    /* "Parser.y":371  */
                                                                      {
        ((DeclarationsCommaList)(yystack.valueAt (4))).append(new IdentifierWithValue(((Token)(yystack.valueAt (2))), ((ExpressionEphemeral)(yystack.valueAt (0)))));
        yyval = ((DeclarationsCommaList)(yystack.valueAt (4)));
    };
  break;


  case 87: /* consecutive_access_tail: access_tail  */
  if (yyn == 87)
    /* "Parser.y":378  */
                {
        yyval = new AccessTailList(((Tail)(yystack.valueAt (0))));
    };
  break;


  case 88: /* consecutive_access_tail: consecutive_access_tail access_tail  */
  if (yyn == 88)
    /* "Parser.y":381  */
                                          {
        ((AccessTailList)(yystack.valueAt (1))).append(((Tail)(yystack.valueAt (0))));
        yyval = ((AccessTailList)(yystack.valueAt (1)));
    };
  break;


  case 89: /* expressions_comma: expression  */
  if (yyn == 89)
    /* "Parser.y":388  */
               {
        yyval = new ExpressionsCommaList(((ExpressionEphemeral)(yystack.valueAt (0))));
    };
  break;


  case 90: /* expressions_comma: expressions_comma Comma expression  */
  if (yyn == 90)
    /* "Parser.y":391  */
                                         {
        ((ExpressionsCommaList)(yystack.valueAt (2))).append(((ExpressionEphemeral)(yystack.valueAt (0))));
        yyval = ((ExpressionsCommaList)(yystack.valueAt (2)));
    };
  break;


  case 91: /* tuple_data: expression  */
  if (yyn == 91)
    /* "Parser.y":398  */
               {
        yyval = new TupleList(((ExpressionEphemeral)(yystack.valueAt (0))));
    };
  break;


  case 92: /* tuple_data: Identifier Assignment expression  */
  if (yyn == 92)
    /* "Parser.y":401  */
                                       {
        yyval = new TupleList(new IdentifierAssign(((Token)(yystack.valueAt (2))), ((ExpressionEphemeral)(yystack.valueAt (0)))));
    };
  break;


  case 93: /* tuple_data: tuple_data Comma expression  */
  if (yyn == 93)
    /* "Parser.y":404  */
                                  {
        ((TupleList)(yystack.valueAt (2))).append(((ExpressionEphemeral)(yystack.valueAt (0))));
        yyval = ((TupleList)(yystack.valueAt (2)));
    };
  break;


  case 94: /* tuple_data: tuple_data Comma Identifier Assignment expression  */
  if (yyn == 94)
    /* "Parser.y":408  */
                                                        {
        ((TupleList)(yystack.valueAt (4))).append(new IdentifierAssign(((Token)(yystack.valueAt (2))), ((ExpressionEphemeral)(yystack.valueAt (0)))));
        yyval = ((TupleList)(yystack.valueAt (4)));
    };
  break;


  case 95: /* function_args: %empty  */
  if (yyn == 95)
    /* "Parser.y":415  */
           {
        yyval = new ExpressionsCommaList();
    };
  break;


  case 96: /* function_args: expression  */
  if (yyn == 96)
    /* "Parser.y":418  */
                 {
        yyval = new ExpressionsCommaList(((ExpressionEphemeral)(yystack.valueAt (0))));
    };
  break;


  case 97: /* function_args: function_args Comma expression  */
  if (yyn == 97)
    /* "Parser.y":421  */
                                     {
        ((ExpressionsCommaList)(yystack.valueAt (2))).append(((ExpressionEphemeral)(yystack.valueAt (0))));
        yyval = ((ExpressionsCommaList)(yystack.valueAt (2)));
    };
  break;


  case 98: /* identifiers_comma: %empty  */
  if (yyn == 98)
    /* "Parser.y":428  */
           {
        yyval = new IdentifiersCommaList();
    };
  break;


  case 99: /* identifiers_comma: Identifier  */
  if (yyn == 99)
    /* "Parser.y":431  */
                 {
        yyval = new IdentifiersCommaList(((Token)(yystack.valueAt (0))));
    };
  break;


  case 100: /* identifiers_comma: identifiers_comma Comma Identifier  */
  if (yyn == 100)
    /* "Parser.y":434  */
                                         {
        ((IdentifiersCommaList)(yystack.valueAt (2))).append(((Token)(yystack.valueAt (0))));
        yyval = ((IdentifiersCommaList)(yystack.valueAt (2)));
    };
  break;


  case 101: /* consecutive_statements: statement  */
  if (yyn == 101)
    /* "Parser.y":441  */
              {
        yyval = new StatementsList(((Statement)(yystack.valueAt (0))));
    };
  break;


  case 102: /* consecutive_statements: consecutive_statements statement  */
  if (yyn == 102)
    /* "Parser.y":444  */
                                       {
        ((StatementsList)(yystack.valueAt (1))).append(((Statement)(yystack.valueAt (0))));
        yyval = ((StatementsList)(yystack.valueAt (1)));
    };
  break;



/* "Parser.java":1286  */

        default: break;
      }

    yystack.pop(yylen);
    yylen = 0;
    /* Shift the result of the reduction.  */
    int yystate = yyLRGotoState(yystack.stateAt(0), yyr1_[yyn]);
    yystack.push(yystate, yyval);
    return YYNEWSTATE;
  }




  /**
   * Parse input from the scanner that was specified at object construction
   * time.  Return whether the end of the input was reached successfully.
   *
   * @return <tt>true</tt> if the parsing succeeds.  Note that this does not
   *          imply that there were no syntax errors.
   */
  public boolean parse() throws java.io.IOException

  {


    /* Lookahead token kind.  */
    int yychar = YYEMPTY_;
    /* Lookahead symbol kind.  */
    SymbolKind yytoken = null;

    /* State.  */
    int yyn = 0;
    int yylen = 0;
    int yystate = 0;
    YYStack yystack = new YYStack ();
    int label = YYNEWSTATE;



    /* Semantic value of the lookahead.  */
    Object yylval = null;



    yyerrstatus_ = 0;
    yynerrs = 0;

    /* Initialize the stack.  */
    yystack.push (yystate, yylval);



    for (;;)
      switch (label)
      {
        /* New state.  Unlike in the C/C++ skeletons, the state is already
           pushed when we come here.  */
      case YYNEWSTATE:

        /* Accept?  */
        if (yystate == YYFINAL_)
          return true;

        /* Take a decision.  First try without lookahead.  */
        yyn = yypact_[yystate];
        if (yyPactValueIsDefault (yyn))
          {
            label = YYDEFAULT;
            break;
          }

        /* Read a lookahead token.  */
        if (yychar == YYEMPTY_)
          {

            yychar = yylexer.yylex ();
            yylval = yylexer.getLVal();

          }

        /* Convert token to internal form.  */
        yytoken = yytranslate_ (yychar);

        if (yytoken == SymbolKind.S_YYerror)
          {
            // The scanner already issued an error message, process directly
            // to error recovery.  But do not keep the error token as
            // lookahead, it is too special and may lead us to an endless
            // loop in error recovery. */
            yychar = Lexer.YYUNDEF;
            yytoken = SymbolKind.S_YYUNDEF;
            label = YYERRLAB1;
          }
        else
          {
            /* If the proper action on seeing token YYTOKEN is to reduce or to
               detect an error, take that action.  */
            yyn += yytoken.getCode();
            if (yyn < 0 || YYLAST_ < yyn || yycheck_[yyn] != yytoken.getCode()) {
              label = YYDEFAULT;
            }

            /* <= 0 means reduce or error.  */
            else if ((yyn = yytable_[yyn]) <= 0)
              {
                if (yyTableValueIsError(yyn)) {
                  label = YYERRLAB;
                } else {
                  yyn = -yyn;
                  label = YYREDUCE;
                }
              }

            else
              {
                /* Shift the lookahead token.  */
                /* Discard the token being shifted.  */
                yychar = YYEMPTY_;

                /* Count tokens shifted since error; after three, turn off error
                   status.  */
                if (yyerrstatus_ > 0)
                  --yyerrstatus_;

                yystate = yyn;
                yystack.push(yystate, yylval);
                label = YYNEWSTATE;
              }
          }
        break;

      /*-----------------------------------------------------------.
      | yydefault -- do the default action for the current state.  |
      `-----------------------------------------------------------*/
      case YYDEFAULT:
        yyn = yydefact_[yystate];
        if (yyn == 0)
          label = YYERRLAB;
        else
          label = YYREDUCE;
        break;

      /*-----------------------------.
      | yyreduce -- Do a reduction.  |
      `-----------------------------*/
      case YYREDUCE:
        yylen = yyr2_[yyn];
        label = yyaction(yyn, yystack, yylen);
        yystate = yystack.stateAt(0);
        break;

      /*------------------------------------.
      | yyerrlab -- here on detecting error |
      `------------------------------------*/
      case YYERRLAB:
        /* If not already recovering from an error, report this error.  */
        if (yyerrstatus_ == 0)
          {
            ++yynerrs;
            if (yychar == YYEMPTY_)
              yytoken = null;
            yyreportSyntaxError(new Context(this, yystack, yytoken));
          }

        if (yyerrstatus_ == 3)
          {
            /* If just tried and failed to reuse lookahead token after an
               error, discard it.  */

            if (yychar <= Lexer.YYEOF)
              {
                /* Return failure if at end of input.  */
                if (yychar == Lexer.YYEOF)
                  return false;
              }
            else
              yychar = YYEMPTY_;
          }

        /* Else will try to reuse lookahead token after shifting the error
           token.  */
        label = YYERRLAB1;
        break;

      /*-------------------------------------------------.
      | errorlab -- error raised explicitly by YYERROR.  |
      `-------------------------------------------------*/
      case YYERROR:
        /* Do not reclaim the symbols of the rule which action triggered
           this YYERROR.  */
        yystack.pop (yylen);
        yylen = 0;
        yystate = yystack.stateAt(0);
        label = YYERRLAB1;
        break;

      /*-------------------------------------------------------------.
      | yyerrlab1 -- common code for both syntax error and YYERROR.  |
      `-------------------------------------------------------------*/
      case YYERRLAB1:
        yyerrstatus_ = 3;       /* Each real token shifted decrements this.  */

        // Pop stack until we find a state that shifts the error token.
        for (;;)
          {
            yyn = yypact_[yystate];
            if (!yyPactValueIsDefault (yyn))
              {
                yyn += SymbolKind.S_YYerror.getCode();
                if (0 <= yyn && yyn <= YYLAST_
                    && yycheck_[yyn] == SymbolKind.S_YYerror.getCode())
                  {
                    yyn = yytable_[yyn];
                    if (0 < yyn)
                      break;
                  }
              }

            /* Pop the current state because it cannot handle the
             * error token.  */
            if (yystack.height == 0)
              return false;


            yystack.pop ();
            yystate = yystack.stateAt(0);
          }

        if (label == YYABORT)
          /* Leave the switch.  */
          break;



        /* Shift the error token.  */

        yystate = yyn;
        yystack.push (yyn, yylval);
        label = YYNEWSTATE;
        break;

        /* Accept.  */
      case YYACCEPT:
        return true;

        /* Abort.  */
      case YYABORT:
        return false;
      }
}




  /**
   * Information needed to get the list of expected tokens and to forge
   * a syntax error diagnostic.
   */
  public static final class Context {
    Context(Parser parser, YYStack stack, SymbolKind token) {
      yyparser = parser;
      yystack = stack;
      yytoken = token;
    }

    private Parser yyparser;
    private YYStack yystack;


    /**
     * The symbol kind of the lookahead token.
     */
    public final SymbolKind getToken() {
      return yytoken;
    }

    private SymbolKind yytoken;
    static final int NTOKENS = Parser.YYNTOKENS_;

    /**
     * Put in YYARG at most YYARGN of the expected tokens given the
     * current YYCTX, and return the number of tokens stored in YYARG.  If
     * YYARG is null, return the number of expected tokens (guaranteed to
     * be less than YYNTOKENS).
     */
    int getExpectedTokens(SymbolKind yyarg[], int yyargn) {
      return getExpectedTokens (yyarg, 0, yyargn);
    }

    int getExpectedTokens(SymbolKind yyarg[], int yyoffset, int yyargn) {
      int yycount = yyoffset;
      int yyn = yypact_[this.yystack.stateAt(0)];
      if (!yyPactValueIsDefault(yyn))
        {
          /* Start YYX at -YYN if negative to avoid negative
             indexes in YYCHECK.  In other words, skip the first
             -YYN actions for this state because they are default
             actions.  */
          int yyxbegin = yyn < 0 ? -yyn : 0;
          /* Stay within bounds of both yycheck and yytname.  */
          int yychecklim = YYLAST_ - yyn + 1;
          int yyxend = yychecklim < NTOKENS ? yychecklim : NTOKENS;
          for (int yyx = yyxbegin; yyx < yyxend; ++yyx)
            if (yycheck_[yyx + yyn] == yyx && yyx != SymbolKind.S_YYerror.getCode()
                && !yyTableValueIsError(yytable_[yyx + yyn]))
              {
                if (yyarg == null)
                  yycount += 1;
                else if (yycount == yyargn)
                  return 0; // FIXME: this is incorrect.
                else
                  yyarg[yycount++] = SymbolKind.get(yyx);
              }
        }
      if (yyarg != null && yycount == yyoffset && yyoffset < yyargn)
        yyarg[yycount] = null;
      return yycount - yyoffset;
    }
  }




  private int yysyntaxErrorArguments(Context yyctx, SymbolKind[] yyarg, int yyargn) {
    /* There are many possibilities here to consider:
       - If this state is a consistent state with a default action,
         then the only way this function was invoked is if the
         default action is an error action.  In that case, don't
         check for expected tokens because there are none.
       - The only way there can be no lookahead present (in tok) is
         if this state is a consistent state with a default action.
         Thus, detecting the absence of a lookahead is sufficient to
         determine that there is no unexpected or expected token to
         report.  In that case, just report a simple "syntax error".
       - Don't assume there isn't a lookahead just because this
         state is a consistent state with a default action.  There
         might have been a previous inconsistent state, consistent
         state with a non-default action, or user semantic action
         that manipulated yychar.  (However, yychar is currently out
         of scope during semantic actions.)
       - Of course, the expected token list depends on states to
         have correct lookahead information, and it depends on the
         parser not to perform extra reductions after fetching a
         lookahead from the scanner and before detecting a syntax
         error.  Thus, state merging (from LALR or IELR) and default
         reductions corrupt the expected token list.  However, the
         list is correct for canonical LR with one exception: it
         will still contain any token that will not be accepted due
         to an error action in a later state.
    */
    int yycount = 0;
    if (yyctx.getToken() != null)
      {
        if (yyarg != null)
          yyarg[yycount] = yyctx.getToken();
        yycount += 1;
        yycount += yyctx.getExpectedTokens(yyarg, 1, yyargn);
      }
    return yycount;
  }


  /**
   * Build and emit a "syntax error" message in a user-defined way.
   *
   * @param ctx  The context of the error.
   */
  private void yyreportSyntaxError(Context yyctx) {
      if (yyErrorVerbose) {
          final int argmax = 5;
          SymbolKind[] yyarg = new SymbolKind[argmax];
          int yycount = yysyntaxErrorArguments(yyctx, yyarg, argmax);
          String[] yystr = new String[yycount];
          for (int yyi = 0; yyi < yycount; ++yyi) {
              yystr[yyi] = yyarg[yyi].getName();
          }
          String yyformat;
          switch (yycount) {
              default:
              case 0: yyformat = "syntax error"; break;
              case 1: yyformat = "syntax error, unexpected {0}"; break;
              case 2: yyformat = "syntax error, unexpected {0}, expecting {1}"; break;
              case 3: yyformat = "syntax error, unexpected {0}, expecting {1} or {2}"; break;
              case 4: yyformat = "syntax error, unexpected {0}, expecting {1} or {2} or {3}"; break;
              case 5: yyformat = "syntax error, unexpected {0}, expecting {1} or {2} or {3} or {4}"; break;
          }
          yyerror(new MessageFormat(yyformat).format(yystr));
      } else {
          yyerror("syntax error");
      }
  }

  /**
   * Whether the given <code>yypact_</code> value indicates a defaulted state.
   * @param yyvalue   the value to check
   */
  private static boolean yyPactValueIsDefault(int yyvalue) {
    return yyvalue == yypact_ninf_;
  }

  /**
   * Whether the given <code>yytable_</code>
   * value indicates a syntax error.
   * @param yyvalue the value to check
   */
  private static boolean yyTableValueIsError(int yyvalue) {
    return yyvalue == yytable_ninf_;
  }

  private static final short yypact_ninf_ = -112;
  private static final short yytable_ninf_ = -1;

/* YYPACT[STATE-NUM] -- Index in YYTABLE of the portion describing
   STATE-NUM.  */
  private static final short[] yypact_ = yypact_init();
  private static final short[] yypact_init()
  {
    return new short[]
    {
     225,   117,   117,   -18,    21,   339,     9,   263,   -31,  -112,
    -112,  -112,   301,   271,   301,   301,   -23,  -112,  -112,  -112,
    -112,   113,    56,  -112,   225,  -112,  -112,  -112,  -112,  -112,
    -112,  -112,  -112,    41,    31,    99,    77,     5,  -112,  -112,
    -112,    52,  -112,  -112,    20,    18,  -112,  -112,   301,  -112,
      28,    -4,  -112,   120,  -112,     7,  -112,  -112,    -7,    92,
     105,   119,   130,  -112,    49,    72,    68,   100,     1,   301,
     301,   301,  -112,  -112,  -112,  -112,  -112,   188,  -112,  -112,
    -112,   339,   339,   339,   339,   339,   339,   339,   339,   339,
     339,   339,   339,   339,   185,   132,    18,   143,  -112,    -9,
    -112,   301,   309,  -112,   301,  -112,   301,   121,  -112,   123,
    -112,  -112,  -112,   225,   225,  -112,   128,  -112,  -112,   161,
    -112,    -1,   157,   301,  -112,  -112,  -112,  -112,  -112,  -112,
    -112,  -112,  -112,  -112,     5,     5,  -112,  -112,  -112,  -112,
    -112,  -112,  -112,  -112,  -112,  -112,  -112,    78,  -112,   226,
    -112,  -112,  -112,   173,  -112,  -112,    39,    84,    68,  -112,
     301,  -112,  -112,   182,   225,   301,   301,   301,   225,  -112,
    -112,  -112,  -112,  -112,  -112,   149,  -112,  -112,  -112,   187,
    -112,  -112
    };
  }

/* YYDEFACT[STATE-NUM] -- Default reduction number in state STATE-NUM.
   Performed when YYTABLE does not specify something else to do.  Zero
   means the default is an error.  */
  private static final byte[] yydefact_ = yydefact_init();
  private static final byte[] yydefact_init()
  {
    return new byte[]
    {
       3,     0,     0,     0,     0,     0,     0,     0,    98,    74,
      75,    76,     0,     0,     0,     0,     0,    56,    55,    54,
      57,    77,     0,     2,     3,     5,     6,     7,     8,     9,
      10,    11,    12,     0,    27,    32,    39,    40,    43,    31,
      46,    58,    60,    61,     0,    77,    48,    49,     0,    50,
      98,     0,    66,    77,    91,     0,    64,    89,     0,    99,
       0,     0,     0,    24,     0,     0,     0,     0,     0,     0,
      95,     0,    62,    87,    78,    79,    80,    63,     1,     4,
      13,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,    63,     0,    99,     0,
      47,     0,     0,    67,     0,    65,     0,     0,    16,     0,
      17,    26,    23,     0,     0,    20,     0,    82,    83,     0,
      96,     0,     0,     0,    88,    29,    28,    30,    33,    34,
      35,    36,    37,    38,    41,    42,    45,    44,    68,    69,
      70,    71,    72,    73,    59,    25,    51,     0,    92,    77,
      93,    90,    85,     0,   100,   101,     0,     0,     0,    14,
       0,    84,    81,     0,     0,     0,     0,     0,     0,    19,
     102,    22,    21,    97,    15,     0,    53,    94,    86,     0,
      52,    18
    };
  }

/* YYPGOTO[NTERM-NUM].  */
  private static final short[] yypgoto_ = yypgoto_init();
  private static final short[] yypgoto_init()
  {
    return new short[]
    {
    -112,  -112,   181,     0,  -112,  -112,  -112,  -112,  -112,    61,
    -112,  -112,  -112,    -5,    27,   162,   209,    73,    79,  -112,
     142,   186,  -112,  -112,  -112,  -112,  -112,   -73,  -112,  -112,
    -112,  -112,   208,   230,  -112,  -112,   193,  -111
    };
  }

/* YYDEFGOTO[NTERM-NUM].  */
  private static final short[] yydefgoto_ = yydefgoto_init();
  private static final short[] yydefgoto_init()
  {
    return new short[]
    {
       0,    22,    23,   155,    25,    26,    27,    28,    29,   115,
      30,    31,    32,    33,    34,    35,    36,    37,    38,    39,
      40,    41,    42,    43,   144,    44,    72,    73,    74,    75,
      76,    60,    96,    58,    55,   121,    61,   156
    };
  }

/* YYTABLE[YYPACT[STATE-NUM]] -- What to do in state STATE-NUM.  If
   positive, shift that token.  If negative, reduce the rule whose
   number is the opposite.  If YYTABLE_NINF, syntax error.  */
  private static final short[] yytable_ = yytable_init();
  private static final short[] yytable_init()
  {
    return new short[]
    {
      24,    54,    57,   157,   124,    90,    91,    57,    64,    65,
      66,    48,   109,     6,   104,     7,    92,    93,     1,     2,
     160,   147,    59,   124,    24,     3,   100,   105,   102,   161,
      67,    17,    18,    19,    20,    45,    68,     4,     5,   103,
       6,    52,     7,    97,    81,    82,    83,    70,     1,     2,
      50,    71,   117,   175,   118,     3,    78,   179,    17,    18,
      19,    20,    53,    80,   119,   120,   122,     4,     5,    94,
       6,   112,     7,    45,     8,     9,    10,    11,    12,    13,
      14,    98,   168,   169,    15,    16,    90,    91,    17,    18,
      19,    20,    21,     1,     2,   164,   148,   150,   165,   151,
       3,   152,    84,    85,    86,    87,    88,    89,   125,   126,
     127,   106,     4,     5,   113,     6,   114,     7,   163,     8,
       9,    10,    11,    12,    13,    14,   107,   108,   171,    15,
      16,    68,    69,    17,    18,    19,    20,    21,    68,   101,
     109,   110,    70,    46,    47,    49,    71,   116,     6,    70,
       7,   104,   111,    71,   145,   173,   170,   170,     1,     2,
     176,   177,   178,   134,   135,     3,    17,    18,    19,    20,
      45,   136,   137,   146,   153,   170,   154,     4,     5,   170,
       6,   158,     7,   159,     8,     9,    10,    11,    12,    13,
      14,   162,   167,   180,    15,    16,     1,     2,    17,    18,
      19,    20,    21,     3,   174,    79,    68,   123,   138,   139,
     140,   141,   142,   143,    51,     4,     5,    70,     6,   172,
       7,    71,     8,     9,    10,    11,    12,    13,    14,    77,
      95,   181,    15,    16,     1,     2,    17,    18,    19,    20,
      21,     3,    62,    99,    68,   166,   128,   129,   130,   131,
     132,   133,     0,     4,     5,    70,     6,     0,     7,    71,
       8,     9,    10,    11,    12,    13,    14,     0,     0,     0,
      15,    16,     1,     2,    17,    18,    19,    20,    21,     3,
       1,     2,     0,     0,     0,     0,     0,     3,     0,     0,
       0,     4,     5,    63,     6,     0,     7,    56,     0,     4,
       5,     0,     6,     0,     7,     0,     0,     0,     0,     0,
       1,     2,    17,    18,    19,    20,    45,     3,     1,     2,
      17,    18,    19,    20,    45,     3,     0,     0,     0,     4,
       5,     0,     6,     0,     7,     0,     0,     4,     5,     0,
       6,     0,     7,     0,     0,     0,     0,     0,     1,     2,
      17,    18,    19,    20,    45,     3,     0,     0,    17,    18,
      19,    20,   149,     0,     0,     0,     0,     0,     5,     0,
       6,     0,     7,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,    17,    18,
      19,    20,    45
    };
  }

private static final short[] yycheck_ = yycheck_init();
  private static final short[] yycheck_init()
  {
    return new short[]
    {
       0,     6,     7,   114,    77,     9,    10,    12,    13,    14,
      15,    29,    21,    31,    21,    33,    11,    12,     9,    10,
      21,    30,    53,    96,    24,    16,    30,    34,    21,    30,
      53,    49,    50,    51,    52,    53,    18,    28,    29,    32,
      31,    32,    33,    48,    13,    14,    15,    29,     9,    10,
      29,    33,    51,   164,    53,    16,     0,   168,    49,    50,
      51,    52,    53,    22,    69,    70,    71,    28,    29,    17,
      31,    22,    33,    53,    35,    36,    37,    38,    39,    40,
      41,    53,    43,    44,    45,    46,     9,    10,    49,    50,
      51,    52,    53,     9,    10,    17,   101,   102,    20,   104,
      16,   106,     3,     4,     5,     6,     7,     8,    81,    82,
      83,    19,    28,    29,    42,    31,    48,    33,   123,    35,
      36,    37,    38,    39,    40,    41,    21,    22,    44,    45,
      46,    18,    19,    49,    50,    51,    52,    53,    18,    19,
      21,    22,    29,     1,     2,     3,    33,    47,    31,    29,
      33,    21,    22,    33,    22,   160,   156,   157,     9,    10,
     165,   166,   167,    90,    91,    16,    49,    50,    51,    52,
      53,    92,    93,    30,    53,   175,    53,    28,    29,   179,
      31,    53,    33,    22,    35,    36,    37,    38,    39,    40,
      41,    34,    19,    44,    45,    46,     9,    10,    49,    50,
      51,    52,    53,    16,    22,    24,    18,    19,    23,    24,
      25,    26,    27,    28,     5,    28,    29,    29,    31,   158,
      33,    33,    35,    36,    37,    38,    39,    40,    41,    21,
      44,    44,    45,    46,     9,    10,    49,    50,    51,    52,
      53,    16,    12,    50,    18,    19,    84,    85,    86,    87,
      88,    89,    -1,    28,    29,    29,    31,    -1,    33,    33,
      35,    36,    37,    38,    39,    40,    41,    -1,    -1,    -1,
      45,    46,     9,    10,    49,    50,    51,    52,    53,    16,
       9,    10,    -1,    -1,    -1,    -1,    -1,    16,    -1,    -1,
      -1,    28,    29,    22,    31,    -1,    33,    34,    -1,    28,
      29,    -1,    31,    -1,    33,    -1,    -1,    -1,    -1,    -1,
       9,    10,    49,    50,    51,    52,    53,    16,     9,    10,
      49,    50,    51,    52,    53,    16,    -1,    -1,    -1,    28,
      29,    -1,    31,    -1,    33,    -1,    -1,    28,    29,    -1,
      31,    -1,    33,    -1,    -1,    -1,    -1,    -1,     9,    10,
      49,    50,    51,    52,    53,    16,    -1,    -1,    49,    50,
      51,    52,    53,    -1,    -1,    -1,    -1,    -1,    29,    -1,
      31,    -1,    33,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    49,    50,
      51,    52,    53
    };
  }

/* YYSTOS[STATE-NUM] -- The symbol kind of the accessing symbol of
   state STATE-NUM.  */
  private static final byte[] yystos_ = yystos_init();
  private static final byte[] yystos_init()
  {
    return new byte[]
    {
       0,     9,    10,    16,    28,    29,    31,    33,    35,    36,
      37,    38,    39,    40,    41,    45,    46,    49,    50,    51,
      52,    53,    55,    56,    57,    58,    59,    60,    61,    62,
      64,    65,    66,    67,    68,    69,    70,    71,    72,    73,
      74,    75,    76,    77,    79,    53,    74,    74,    29,    74,
      29,    70,    32,    53,    67,    88,    34,    67,    87,    53,
      85,    90,    87,    22,    67,    67,    67,    53,    18,    19,
      29,    33,    80,    81,    82,    83,    84,    86,     0,    56,
      22,    13,    14,    15,     3,     4,     5,     6,     7,     8,
       9,    10,    11,    12,    17,    75,    86,    67,    53,    90,
      30,    19,    21,    32,    21,    34,    19,    21,    22,    21,
      22,    22,    22,    42,    48,    63,    47,    51,    53,    67,
      67,    89,    67,    19,    81,    68,    68,    68,    69,    69,
      69,    69,    69,    69,    71,    71,    72,    72,    23,    24,
      25,    26,    27,    28,    78,    22,    30,    30,    67,    53,
      67,    67,    67,    53,    53,    57,    91,    91,    53,    22,
      21,    30,    34,    67,    17,    20,    19,    19,    43,    44,
      57,    44,    63,    67,    22,    91,    67,    67,    67,    91,
      44,    44
    };
  }

/* YYR1[RULE-NUM] -- Symbol kind of the left-hand side of rule RULE-NUM.  */
  private static final byte[] yyr1_ = yyr1_init();
  private static final byte[] yyr1_init()
  {
    return new byte[]
    {
       0,    54,    55,    56,    56,    57,    57,    57,    57,    57,
      57,    57,    57,    58,    59,    59,    60,    60,    61,    61,
      62,    62,    63,    64,    64,    65,    66,    67,    67,    67,
      67,    67,    68,    68,    68,    68,    68,    68,    68,    69,
      70,    70,    70,    71,    71,    71,    72,    72,    72,    72,
      72,    72,    73,    73,    74,    74,    74,    74,    74,    74,
      74,    74,    75,    75,    76,    76,    77,    77,    78,    78,
      78,    78,    78,    78,    79,    79,    79,    80,    81,    81,
      81,    82,    83,    83,    84,    85,    85,    86,    86,    87,
      87,    88,    88,    88,    88,    89,    89,    89,    90,    90,
      90,    91,    91
    };
  }

/* YYR2[RULE-NUM] -- Number of symbols on the right-hand side of rule RULE-NUM.  */
  private static final byte[] yyr2_ = yyr2_init();
  private static final byte[] yyr2_init()
  {
    return new byte[]
    {
       0,     2,     1,     0,     2,     1,     1,     1,     1,     1,
       1,     1,     1,     2,     4,     5,     3,     3,     7,     5,
       3,     5,     3,     3,     2,     3,     3,     1,     3,     3,
       3,     1,     1,     3,     3,     3,     3,     3,     3,     1,
       1,     3,     3,     1,     3,     3,     1,     3,     2,     2,
       2,     4,     7,     6,     1,     1,     1,     1,     1,     3,
       1,     1,     2,     2,     2,     3,     2,     3,     1,     1,
       1,     1,     1,     1,     1,     1,     1,     0,     1,     1,
       1,     3,     2,     2,     3,     3,     5,     1,     2,     1,
       3,     1,     3,     3,     5,     0,     1,     3,     0,     1,
       3,     1,     2
    };
  }




  /* YYTRANSLATE_(TOKEN-NUM) -- Symbol number corresponding to TOKEN-NUM
     as returned by yylex, with out-of-bounds checking.  */
  private static final SymbolKind yytranslate_(int t)
  {
    // Last valid token kind.
    int code_max = 308;
    if (t <= 0)
      return SymbolKind.S_YYEOF;
    else if (t <= code_max)
      return SymbolKind.get(yytranslate_table_[t]);
    else
      return SymbolKind.S_YYUNDEF;
  }
  private static final byte[] yytranslate_table_ = yytranslate_table_init();
  private static final byte[] yytranslate_table_init()
  {
    return new byte[]
    {
       0,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     1,     2,     3,     4,
       5,     6,     7,     8,     9,    10,    11,    12,    13,    14,
      15,    16,    17,    18,    19,    20,    21,    22,    23,    24,
      25,    26,    27,    28,    29,    30,    31,    32,    33,    34,
      35,    36,    37,    38,    39,    40,    41,    42,    43,    44,
      45,    46,    47,    48,    49,    50,    51,    52,    53
    };
  }


  private static final int YYLAST_ = 392;
  private static final int YYEMPTY_ = -2;
  private static final int YYFINAL_ = 78;
  private static final int YYNTOKENS_ = 54;

/* Unqualified %code blocks.  */
/* "Parser.y":11  */

	private static ASTree ast;

    public static ASTree makeAST(com.java.lexer.Lexer lexer) throws IOException {
		LexerAdapter lexerAdapter = new LexerAdapter(lexer);
		Parser p = new Parser(lexerAdapter);
		p.parse();
		return ast;
	}

/* "Parser.java":2027  */

}
/* "Parser.y":450  */

