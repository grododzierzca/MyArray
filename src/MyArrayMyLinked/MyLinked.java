package MyArrayMyLinked;

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class MyLinked<T> implements Iterable<T>{

    private Node first = null;
    private Node last = null;
    private int size;
    private Node current = null;
    private MyLinkedIt it = new MyLinkedIt();

    public MyLinked(){
        this.size = 0;
    }

    private class Node{

        private T contains;
        private Node next;
        private Node prev;

        public Node(Node prev, T contains, Node next){
            this.next = next;
            this.contains = contains;
            this.prev = prev;
        }

        public Node nextNode(){
            return next;
        }
        public void setNext(Node n){
            next = n;
        }

        public void setPrev(Node n){
            prev = n;
        }

        public Node prevNode(){
            return prev;
        }

        public T getElement(){
            return contains;
        }

        public void setElement(T t){
            contains = t;
        }
    }

    public void setCurrent(Node n){
        current = n;
    }

    public Node getCurrent(){
        return current;
    }

    public int size(){
        return size;
    }

    public int indexOf(T t){
        for(int i =0; i<size; i++){
            if(getCurrent().getElement().equals(t)){
                return i;
            }else{
                setCurrent(getCurrent().nextNode());
            }
        }
        return -1;
    }

    public void set(int index, T t){
        Node temp = first;
        for(int i = 0; i<index-1; i++){
            temp = temp.nextNode(); /// temp do jednego przed, a potem nextNode i setElement :P
        }
        temp.nextNode().setElement(t);
    }

    public void add(T t){
        if(first == null){
            Node new_first = new Node(null, t, null);
            first = new_first;
            last = new_first;
            current = new_first;
        }else{
            Node new_last = new Node(last, t, null);
            last.setNext(new_last);
            last = new_last;
        }
        size++;
    }

    public void add(int index, T t){
        if(index == 0){
            add(t);
        }else {
            Node temp = first;
            for (int i = 0; i < index - 1; i++) {
                temp = temp.nextNode();
            }
            Node new_node = new Node(temp, t, temp.nextNode());
            temp.setNext(new_node);
            temp.nextNode().setPrev(new_node);
        }
        size++;
    }

    public void remove(int index) throws IndexOutOfBoundsException, NullPointerException{
        if(index == 0){
            Node temp = first;
            temp.nextNode().setPrev(null);
            first = temp.nextNode();
        }else{
            Node temp = first;
            for(int i = 0; i<index-1; i++){
                temp = temp.nextNode();
            }
            if(temp.nextNode().nextNode() == null){
                temp.setNext(null);
            }else {
                temp.setNext(temp.nextNode().nextNode());
                temp.nextNode().nextNode().setPrev(temp.nextNode());
            }
        }
        size--;
        System.gc();
    }

    public void clear(){
        first = null;
        last = null;
        current = null;
        System.gc();
    }

    public void addAll(Collection c){
        for (Object o: c){
            add((T)o);
            }
    }

    public void addAll(int index, Collection c){
        for(Object o: c){
            add(index, (T)o);
        }
    }

    public T get(int index){
        Node temp = first;
        for(int i = 0; i<index; i++){
            temp = temp.nextNode();
        }
        return temp.getElement();
    }

    public String toString(){
        Node temp = first;
        String s = temp.toString();
        for(int i = 1; i<size; i++){
            temp = temp.nextNode();
            s+=temp.toString();
        }
        return s;
    }



    @Override
    public Iterator<T> iterator() {
        return it;
    }

    private class MyLinkedIt<T> implements Iterator<T> {
        private int current;

        public MyLinkedIt() {
            current = 0;
        }


        @Override
        public boolean hasNext() {
            if (current < size) return true;
            else return false;
        }

        @Override
        public T next() {
            if (hasNext()) {
                Node temp = first;
                for(int i = 0; i<current-1; i++){
                    temp = temp.nextNode();
                }
                current++;
                return (T)temp.getElement();
            } else {
                throw new NoSuchElementException();
            }
        }


    }
}
