
public class Stack<E> {
    
    private final int STEP = 5;
    private Object[] elementData = new Object[STEP];
    
    public Stack() {}
    
    /**
     * A standard operation for "stack" data structure.
     * Places a new element at the head of the stack.
     * Shifts other elements deeper.
     * @param e - element
     */
    public void push(E e) {
        int size = size();
        if (size == 0) {
            elementData[0] = e;
            return;
        }
        
        int capacity = capacity();
        int newCapacity = size == capacity ? capacity + STEP : capacity;
        
        Object[] dest = new Object[newCapacity];
        System.arraycopy(elementData, 0, dest, 1, size);
        dest[0] = e;
        elementData = dest;
    }
    
    @SuppressWarnings("unchecked")
    public E pop() {
        int size = size();
        if (size == 0) {
            return null;
        }
        
        E res = (E) elementData[0];
        
        int capacity = capacity();
        int newCapacity = capacity - size - 1 > STEP ? capacity - STEP : capacity;
        
        Object[] dest = new Object[newCapacity];
        System.arraycopy(elementData, 1, dest, 0, size);
        elementData = dest;
        
        return res;
    }
    
    @SuppressWarnings("unchecked")
    public E peek() {

        if (size() == 0) {
            return null;
        }
        return (E) elementData[0];
    }

    public void clear() {
        elementData = new Object[STEP];
    }
    
    @SuppressWarnings("unchecked")
    public Stack<E> makeACopy() {
        Stack<E> copy = new Stack<>();
        for (int i = elementData.length - 1; i >= 0; i--) {
            copy.push((E)elementData[i]);
        }
        return copy;
    }
    
    @Override
    public String toString() {
        String res = "";
        boolean first = true;
        for (int i = 0; i < elementData.length; i++) {
            if (elementData[i] != null) {
                res += (first ? "" : ",") + elementData[i].toString();
                first = false;
            }
        }
        return String.format("[%s] size: %s, capacity: %s"
        , res, size(), capacity());
    }

    private int size() {
        int count = 0;
        for (int i = 0; i < elementData.length; i++) {
            if (elementData[i] == null) {
                break;
            } else {
                count++;
            }
        }
        return count;
    }
    
    private int capacity() {
        return elementData.length;
    }
}
    
