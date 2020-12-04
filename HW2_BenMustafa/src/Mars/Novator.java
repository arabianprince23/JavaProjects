package Mars;

import java.util.List;
import java.util.ArrayList;

public class Novator<T> extends Martian<T> {
    public Martian<T> parent;
    public List<Martian<T>> children;
    public T genCode;
    public GeneticTree<T> treeLink;

    public Novator(T code,GeneticTree<T> tr) {
        this.genCode = code;
        this.treeLink = tr;
    }

    public void setGenCode(T value) {
        this.genCode = value;
    }

    public boolean setParent(Martian<T> anotherParent) {
        if (anotherParent.getClass().getName().equals("Novator") && !(anotherParent.getAllParents().contains(this))) {
            ((Novator<T>)this.parent).DeleteAChild(this);
            this.parent = anotherParent;
            return true;
        }
        return false;
    }

    public void setNewChildrenCollection(List<Martian<T>> newChildren) {
        if (newChildren.size() > 0 && newChildren.get(0).getClass().getName().equals("Novator")) {
            this.children = newChildren;
        }
    }

    public boolean addChild(Martian<T> newChild) {
        if (newChild.getClass().getName().equals("Novator") && !(newChild.getAllDescadants().contains(this))) {
            children.add(newChild);
            return true;
        }
        return false;
    }

    public boolean DeleteAChild(Martian<T> child) {
        try {
            ((Novator<T>)children.get(children.indexOf(child))).parent = null;
            return children.remove(child); // returns true if list contained object
        }
        catch (Exception ex) {
            return false;
        }
    }

    // В ДАЛЬНЕЙШЕМ ВОЗВРАЩАТЬ КОПИИ ДЕТЕЙ И РОДИТЕЛЕЙ (?)
    @Override
    public Martian<T> getParentList() {
        return parent;
    }

    @Override
    public List<Martian<T>> getChildrenList() {
        return children;
    }

    @Override
    public List<Martian<T>> getAllParents() {
        List<Martian<T>> listOfMartians = new ArrayList<Martian<T>>();

        listOfMartians.addAll(this.parent.getAllParents());

        return listOfMartians;
    }

    @Override
    public List<Martian<T>> getAllDescadants() {
        List<Martian<T>> listOfMartians = new ArrayList<Martian<T>>();

        for (int i = 0; i < children.size(); ++i) {
            listOfMartians.addAll(this.children.get(i).getAllDescadants());
        }

        return listOfMartians;
    }

    @Override
    public boolean hasChildWithValue(T value) {
        for (int i = 0; i < children.size(); ++i) {
            if (((Novator<T>)children.get(i)).genCode == value) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean hasDescadantWithValue(T value) {
        List<Martian<T>> martians = this.getAllDescadants();
        for (int i = 0; i < martians.size(); ++i) {
            if (((Novator<T>)martians.get(i)).genCode == value) {
                return true;
            }
        }
        return false;
    }

    // need to be called only for root
    public Conservator<T> novatorToConservator() {

        List<Martian<T>> listOfMartians = new ArrayList<Martian<T>>();

        for (int i = 0; i < children.size(); ++i) {
            listOfMartians.add(((Novator<T>)(this.children.get(i))).novatorToConservator());
        }

        this.children = listOfMartians;
        return new Conservator<T>(this);
    }
}
