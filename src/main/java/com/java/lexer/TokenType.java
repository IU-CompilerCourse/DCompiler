package com.java.lexer;

public enum TokenType {
    // Comparison operators
    Less, // <
    LessEqual, // <=
    Greater, // >
    GreaterEqual, // >=
    Equal, // =
    NotEqual, // /=

    // Arithmetic operators
    Plus, // +
    Minus, // -
    Slash, // /
    Star, // *

    // Logical operators
    And, // and
    Or, // or
    Xor, // xor
    Not, // not

    // Other operators
    Is, // is
    Dot, // .
    Assignment, // :=
    Arrow, // =>
    Comma, // ,
    Semicolon, // ;

    // Type indicators
    Int, // int
    Real, // real
    Bool, // bool
    String, // string
    Empty, // empty
    Func, // func

    // Brackets, parenthesis, braces
    OpenParen, // (
    CloseParen, // )
    OpenBrace, // {
    CloseBrace, // }
    OpenBracket, // [
    CloseBracket, // ]

    // Other keywords
    Var, // var
    ReadInt, // readInt
    ReadReal, // readReal
    ReadString, // readString
    Print, // print
    Return, // return
    If, // if
    Then, // then
    Else, // else
    End, // end
    While, // while
    For, // for
    In, // in
    Loop, // loop

    EOF, // End of file

    // Literals
    StringLiteral, // "...."
    DoubleLiteral, // 1.23
    IntLiteral, // 123
    BooleanLiteral, // true / false

    Identifier, // any other user identifier
}
