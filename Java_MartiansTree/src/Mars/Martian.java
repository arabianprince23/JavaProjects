package Mars;

import java.util.List;
import java.util.ArrayList;

public abstract class Martian<T> {
//    protected Martian<T> parent;
//    protected List<Martian<T>> children;
//    public T genCode;
//    public GeneticTree treeLink;

    public T zzz;

//    public Martian() { }

    public abstract Martian<T> getParentList();
//        return parent;

    public abstract List<Martian<T>> getChildrenList();
//        return children;
    
    public abstract List<Martian<T>> getAllParents();
//        List<Martian<T>> listOfMartians = new ArrayList<Martian<T>>();
//
//        listOfMartians.addAll(this.parent.getAllParents());
//
//        return listOfMartians;

    public abstract List<Martian<T>> getAllDescadants();
//        List<Martian<T>> listOfMartians = new ArrayList<Martian<T>>();
//
//        for (int i = 0; i < children.size(); ++i) {
//            listOfMartians.addAll(this.children.get(i).getAllDescadants());
//        }
//
//        return listOfMartians;

    public abstract boolean hasChildWithValue(T value);
//        for (int i = 0; i < children.size(); ++i) {
//            if (children.get(i).genCode == value) {
//                return true;
//            }
//        }
//        return false;

    public abstract boolean hasDescadantWithValue(T value);
//        List<Martian<T>> martians = this.getAllDescadants();
//        for (int i = 0; i < martians.size(); ++i) {
//            if (martians.get(i).genCode == value) {
//                return true;
//            }
//        }
//        return false;

//    public Martian(T genCode, GeneticTree tr) {
//        this.genCode = genCode;
//        this.treeLink = tr;
//    }

//    public abstract void ConvertToNovator();
//        treeLink.novatorToCoservator();

}
