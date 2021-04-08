package stack;
import java.util.Comparator;

public class LengthComparator<T extends String> implements Comparator<T> {
    @Override
    public int compare(T o1, T o2) {
//        if (o1.length() == o2.length()) {
//            return 0;
//        } else {
//            if (o1.length() > o2.length()) {
//                return 1;
//            } else {
//                return -1;
//            }
//        }
        return Integer.compare(o1.length(), o2.length());
    }

//    @Override
//    public <T extends String> int compare(Object o1, Object o2) {
//        return 0;
//    }

//    @Override
//    public int compare(Object o1, Object o2) {
//        return 0;
//    }

//    @Override
//    public <T extends String> int compare(T o1, T o2) {
//        return 0;
//    }
}
