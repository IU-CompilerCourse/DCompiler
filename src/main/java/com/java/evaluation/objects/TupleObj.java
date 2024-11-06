package com.java.evaluation.objects;

import com.java.evaluation.Errors;
import com.java.evaluation.Evaluator;
import com.java.parser.ast.node.real.Tuple;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
public final class TupleObj implements Obj {
    private final List<Item> items = new ArrayList<>();

    public TupleObj(Tuple tupleNode, Evaluator ev) {
        var list = tupleNode.getElements();
        int index = 0;
        for (var node : list.getAllExpressions()) {
            if (list.getNamedExpressions().containsValue(node)) {
                var entry = list.getNamedExpressions().entrySet().stream()
                    .sorted(Comparator.comparing(o -> o.getKey().lexeme())).toList().get(index++);
                items.add(new NamedItem(entry.getKey().lexeme(), entry.getValue().accept(ev)));
            } else {
                items.add(new Item(node.accept(ev)));
            }
        }
    }

    public void setByInd(Integer idx, Obj value) {
        if (idx < 1 || idx > items.size()) {
            throw Errors.indexOutOfBounds(type(), idx, items.size());
        }
        items.get(idx - 1).setValue(value);
    }

    public Obj getByInd(Integer idx) {
        if (idx < 1 || idx > items.size()) {
            throw Errors.indexOutOfBounds(type(), idx, items.size());
        }
        return items.get(idx - 1).getValue();
    }

    public void setByName(String name, Obj value) {
        for (Item item : items) {
            if (!(item instanceof NamedItem namedItem)) {
                continue;
            }
            if (!namedItem.getName().equals(name)) {
                continue;
            }
            namedItem.setValue(value);
            return;
        }
        throw Errors.noTupleNamedItem(name);
    }

    public Obj getByName(String name) {
        for (Item item : items) {
            if (!(item instanceof NamedItem namedItem)) {
                continue;
            }
            if (!namedItem.getName().equals(name)) {
                continue;
            }
            return namedItem.getValue();
        }
        throw Errors.noTupleNamedItem(name);
    }

    @Override
    public String type() {
        return "Tuple";
    }

    @Override public String toString() {
        var sb = new StringBuilder();
        sb.append("{");
        for (int i = 0; i < items.size(); i++) {
            sb.append(items.get(i).toString());
            if (i + 1 != items.size()) {
                sb.append(", ");
            }
        }
        sb.append("}");
        return sb.toString();
    }

    @Getter
    @Setter
    public class Item {
        protected Obj value;

        public Item(Obj value) {
            this.value = value;
        }

        @Override public String toString() {
            return value.toString();
        }
    }

    @Getter
    @Setter
    public class NamedItem extends Item {
        private final String name;

        public NamedItem(String name, Obj value) {
            super(value);
            this.name = name;
        }

        @Override
        public String toString() {
            return name + "=" + value.toString();
        }
    }
}
