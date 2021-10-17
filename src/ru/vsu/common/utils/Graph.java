package ru.vsu.common.utils;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;


public class Graph<T> implements Iterable<T>{
    private class GraphNode<T> {
        public T value;
        public LinkedList<GraphNode<T>> next;

        public GraphNode(T value, LinkedList<GraphNode<T>> next) {
            this.value = value;
            this.next = next;
        }
        public GraphNode(T value) {
            this(value, null);
        }
        public GraphNode() {
            this(null);
        }

    }

    private GraphNode<T> head = null;

    public Graph() {
        head = new GraphNode<>();
    }

    public void addVertex(T vertex) {
        if(head.next == null) {
            head.next = new LinkedList<>();
        }
        GraphNode newNode = new GraphNode(vertex);
        head.next.addLast(newNode);
    }

    public void removeVertex(T vertex) {
        int v = findIndex(vertex);
        head.next.remove(v);
    }

    public void createEdge(T vertex1, T vertex2) {
        int v1 = findIndex(vertex1);
        int v2 = findIndex(vertex2);
        head.next.get(v1).next = new LinkedList<>();
        head.next.get(v2).next = new LinkedList<>();
        head.next.get(v1).next.add(head.next.get(v2));
        head.next.get(v2).next.add(head.next.get(v1));
    }

    private int findIndex(T vertex) {
        for (int i = 0; i <head.next.size(); i++){
            if(vertex.equals(head.next.get(i).value)) {
                return i;
            }
        }
        return -1;
    }

    public boolean isEdge(T vertex1, T vertex2) {

        int v1 = findIndex(vertex1);
        int v2 = findIndex(vertex2);

        if(v1 == -1 || v2 == -1) {
            return false;
        }
        if(head.next.get(v1).next == null || head.next.get(v2).next == null) {
            return false;
        }
        return head.next.get(v1).next.contains(head.next.get(v2));
    }

    public void removeEdge(T vertex1, T vertex2) {
        int v1 = findIndex(vertex1);
        int v2 = findIndex(vertex2);

        head.next.get(v1).next.remove(head.next.get(v2));
        head.next.get(v2).next.remove(head.next.get(v1));
    }


    @Override
    public Iterator<T> iterator() {
        class GraphIterator implements Iterator<T> {
            Queue<GraphNode<T>> queue;

            public GraphIterator(GraphNode<T> head) {
                queue = head.next;
            }
            @Override
            public boolean hasNext() {
                return !queue.isEmpty();
            }

            @Override
            public T next() {
                return queue.poll().value;
            }
        }

        return new GraphIterator(head);
    }
}
