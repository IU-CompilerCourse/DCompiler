package com.java.parser.ast.node.real;

import com.java.parser.ast.node.ephemeral.Tail;
import com.java.parser.ast.visitor.ASTVisitor;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
public class AccessTailList extends Tail {
    private final List<Tail> tails;

    public AccessTailList() {
        tails = new ArrayList<>();
    }

    public AccessTailList(Tail tail) {
        tails = new ArrayList<>();
        tails.add(tail);
    }

    public AccessTailList(Tail tail, AccessTailList tails) {
        this.tails = new ArrayList<>();
        this.tails.add(tail);
        this.tails.addAll(tails.tails);
    }

    public void append(Tail tail) {
        tails.add(tail);
    }

    @Override
    public <R> R accept(ASTVisitor<R> visitor) {
        return visitor.visitAccessTailList(this);
    }
}
