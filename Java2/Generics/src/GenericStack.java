import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class GenericStack<E> implements Stack<E>{

    private final List<E> list;
    private final int maxSize;

    public GenericStack(int maxSize) {
        list = new ArrayList<>();
        this.maxSize = maxSize;
    }


    // add new element to the top of the stack
    @Override
    public void push(E element) throws StackException {
        if (list.size() < maxSize) {
            list.add(0, element);
        } else {
            throw new StackException("Stack is full");
        }
    }

    // return and remove an element from the top
    @Override
    public E pop() throws StackException {
        if (list.isEmpty()) {
            throw new StackException("Stack is empty");
        }
        E temp = list.get(0);
        list.remove(0);
        return temp;
    }

    // return the top element but doesn't remove
    @Override
    public E peek() {
        return list.get(0);
    }

    @Override
    public int getSize() {
        return list.size();
    }

    @Override
    public boolean isEmpty() {
        return list.size() > 0;
    }

    @Override
    public boolean isFull() {
        return list.size() == maxSize;
    }

    // add all elements from @src to the stack
    @Override
    public void pushAll(Collection<? extends E> src) throws StackException {
        if (list.size() + src.size() > maxSize) {
            throw new StackException("doesn't fit max capacity");
        }
        for (E e : src) {
            push(e);
        }
    }

    // pop all elements from stack to @dst
    @Override
    public void popAll(Collection<? super E> dst) throws StackException {
        int iterations = list.size();
        try{
            for (int i = 0; i < iterations; i++) {
                dst.add(pop());
            }
        } catch (Exception e) {
            throw new StackException(e.getMessage());
        }
    }
}
