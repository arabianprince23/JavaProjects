package Mars;

import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;

public class Conservator<T> extends Martian<T> {
    public final Martian<T> parent;
    public final List<Martian<T>> children;
    public final T genCode;
    public final GeneticTree<T> treeLink;

    public Conservator(Novator<T> novator) {
        this.genCode = novator.genCode;
        this.treeLink = novator.treeLink;
        this.children = novator.children;
        this.parent = novator.parent;
    }

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
            if (((Conservator<T>)children.get(i)).genCode == value) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean hasDescadantWithValue(T value) {
        List<Martian<T>> martians = this.getAllDescadants();
        for (int i = 0; i < martians.size(); ++i) {
            if (((Conservator<T>)martians.get(i)).genCode == value) {
                return true;
            }
        }
        return false;
    }

    public void ConvertToNovator() {

    }
}
