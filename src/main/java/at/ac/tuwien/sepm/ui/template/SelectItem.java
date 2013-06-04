package at.ac.tuwien.sepm.ui.template;

/**
 * Created with IntelliJ IDEA.
 * User: Flo
 * Date: 04.06.13
 * Time: 18:22
 * To change this template use File | Settings | File Templates.
 */
public abstract class SelectItem<E> {
    protected E item;

    public SelectItem(E item) {
        this.item = item;
    }

    public E get () {
        return item;
    }

    public abstract String toString();
}
