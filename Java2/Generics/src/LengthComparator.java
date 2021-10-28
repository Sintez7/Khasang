import java.util.Comparator;

public class LengthComparator<T extends String> implements Comparator<T> {
    @Override
    public int compare(T o1, T o2) {
        return Integer.compare(o1.length(), o2.length());
    }
}